function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

function rgbToHex(r, g, b) {
    return "0x" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

function lerp(x1, x2, y1, y2, x) {
  var m = (y2 - y1) / (x2 - x1);
  return (m * (x - x1) + y1);
}

class ColorBar {
  constructor(element, width, height, title) {
    this.x = 0;
    this.y = 0;
    this.width = width;
    this.height = height;
    this.title = title;

    this.currentMouseX = 0;
    this.currentMouseY = 0;
    this.plotDrawingZero = 0;
    this.totalShift = 0;
    this.bars = [0, 0, 0];
    this.values = [0, 0, 0];
    this.colorCircle = 0;
    this.isClicking = false;

    this.renderer = new PIXI.Renderer(width * 1.5, height, {
      resolution: window.devicePixelRatio || 1,
      autoResize: true
    });
    this.renderer.backgroundColor = 0xffffff;
    element.appendChild(this.renderer.view);
    this.stage = new PIXI.Container;
    this.draw();
  }

  draw() {
    var textStyle = new PIXI.TextStyle({
      fontSize: 12
    });

    // Title and background
    var background = this.makeBackground();
    this.stage.addChild(background);
    var title = this.makeTitle(textStyle);
    this.stage.addChild(title);

    // Padding
    var bottomPadding = 10;
    var topPadding = 5;
    var rightPadding = 10;
    var leftPadding = 10;
    var plotX = background.x + leftPadding;
    var plotY = title.y + title.height + topPadding;
    var plotWidth = this.width - leftPadding - rightPadding;
    var plotHeight = (this.height + background.y) - bottomPadding - plotY;

    // Plot
    this.plot = this.makePlotBackground(plotWidth, plotHeight);
    this.plotDrawingZero = this.plot.height - 1;
    this.plot.position = new PIXI.Point(plotX, plotY);
    this.plotDrawingZero += plotY;

    // Render
    this.stage.addChild(this.plot);
    this.setBarAt(0, 0xff0000, 0);
    this.setBarAt(1, 0x00ff00, 0);
    this.setBarAt(2, 0x0000ff, 0);
    this.updateColorCircle();
    this.renderer.render(this.stage);
  }

  setBarAt(i, color, val) {
    // Remove old bar
    var oldBar = this.bars[i];

    if (oldBar != 0) {
      this.stage.removeChild(oldBar);
    }

    // Create new bar
    var barStart = (this.plot.width / 3) * i + 10;
    var barWidth = this.plot.width / 3 - 20;
    var barHeight = (val * 0.85 + 0.1) * this.plot.height;
    var bar = new PIXI.Graphics;

    bar.beginFill(color)
    bar.lineStyle(1, color, 1);
    bar.drawRect(this.plot.x + barStart, this.plotDrawingZero - barHeight, barWidth, barHeight);

    // Store bar
    this.bars[i] = bar;
    this.values[i] = val;
    this.stage.addChild(bar);

    // Update and draw
    this.updateColorCircle();
    this.renderer.render(this.stage);
  }

  updateColorCircle() {
    if(this.colorCircle != 0) {
      this.stage.removeChild(this.colorCircle);
    }

    // Create color circle
    var R = Math.max(Math.min(Math.floor(this.values[0] * 255), 255), 0);
    var G = Math.max(Math.min(Math.floor(this.values[1] * 255), 255), 0);
    var B = Math.max(Math.min(Math.floor(this.values[2] * 255), 255), 0);
    var color = h2d("" + d2h (R,2) + d2h (G,2) + d2h (B,2));
    var circle = new PIXI.Graphics;

    circle.beginFill(color);
    circle.lineStyle(1, color, 1);
    circle.drawCircle(this.width * 1.25, this.height / 2, this.width * 0.2);

    // Draw color circle
    this.stage.addChild(circle);
    this.colorCircle = circle;
  }

  makeBackground() {
    var background = new PIXI.Graphics;
    background.lineStyle(3, 0x000000, 1); //black
    background.beginFill(0xd3d3d3, 1); //grey
    background.drawRect(0, 0, this.width * 1.5, this.height);
    background.endFill();
    background.position = new PIXI.Point(this.x, this.y);
    return background;
  }

  makeTitle(style) {
    var titleInset = new PIXI.Point(10, 10);
    var pixiTitle = new PIXI.Text(this.title, style);
    pixiTitle.x = this.x + titleInset.x;
    pixiTitle.y = this.y + titleInset.y;
    return pixiTitle;
  }

  makePlotBackground(width, height) {
    var plot = new PIXI.Graphics;
    plot.lineStyle(1, 0x000000, 1); //black
    plot.beginFill(0xffffff, 1); //white
    plot.drawRect(0, 0, width, height);
    plot.endFill();
    return plot;
  }

  makeLabels(plot, labelTextStyle) {
    var xrange = this.xmax - this.xmin;
    var numSteps = xrange / this.xinc;
    var stepLength = plot.width / numSteps;
    var labelPadding = 5;

    for (var i = 0; i <= numSteps; i++) {
      var xpos = plot.x + labelPadding + i * stepLength - 1;
      var formattedNumber = (this.xmin + i * this.xinc).toFixed(0);
      var xtitle = new PIXI.Text(formattedNumber, labelTextStyle);
      // subtracting textWidth / 2 centers the text around the correct point
      xtitle.x = xpos - xtitle.width / 2;
      xtitle.y = plot.height + labelPadding + plot.y;
      this.stage.addChild(xtitle);
    }

    var yrange = this.ymax - this.ymin;
    numSteps = yrange / this.yinc;
    stepLength = plot.height / numSteps;
    labelPadding = 25;

    for (var i = 0; i <= numSteps; i++) {
      var ypos = plot.y + i * stepLength - 1;
      var formattedNumber = (this.ymax - i * this.yinc).toFixed(1);
      var ytitle = new PIXI.Text(formattedNumber, labelTextStyle);
      ytitle.x = plot.x - labelPadding;
      // subtracting textHeight / 2 centers the text around the correct point
      ytitle.y = ypos - ytitle.height / 2;
      this.stage.addChild(ytitle);
    }
  }
}
