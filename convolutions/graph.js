function makeSpriteFromGraphics(graphics, renderer) {
  var texture = renderer.generateTexture(graphics, new PIXI.Point(1, 1), 2);

  // create sprite and set it's texture to the graphics object
  var sprite = new PIXI.Sprite();
  sprite.texture = texture;

  return sprite;
}

function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

function rgbToHex(r, g, b) {
    return "0x" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

class Graph {
  constructor(x, y, width, height, xmin, xmax, xinc, ymin, ymax, yinc, title) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.xmin = xmin;
    this.xmax = xmax;
    this.xinc = xinc;
    this.ymin = ymin;
    this.ymax = ymax;
    this.yinc = yinc;
    this.title = title;
    this.xtitle = title;
    this.ytitle = title;

    this.currentMouseX = 0;
    this.plotDrawingZero = 0;
    this.drawingIndices = [];
    this.graphData = [];

    this.renderer = new PIXI.CanvasRenderer(window.innerWidth, window.innerHeight, {
      resolution: window.devicePixelRatio || 1,
      autoResize: true
    });
    this.renderer.backgroundColor = 0xffffff;
    document.body.appendChild(this.renderer.view);
    this.stage = new PIXI.Container;
  }

  getGraphData() {
    return this.graphData;
  }

  draw() {

    var textStyle = new PIXI.TextStyle({
      fontSize: 12
    });

    var background = this.makeBackground();
    this.stage.addChild(background);
    var title = this.makeTitle(textStyle);
    this.stage.addChild(title);

    // probably want to factor all of this plot making code out
    var bottomPadding = 30;
    var topPadding = 10;
    var rightPadding = 20;
    var leftPadding = 30;
    var plotX = background.x + leftPadding;
    var plotY = title.y + title.height + topPadding;
    var plotWidth = background.width - leftPadding - rightPadding;
    var plotHeight = (background.height + background.y) - bottomPadding - plotY;

    var plotBackground = this.makePlotBackground(plotWidth, plotHeight);

    this.makeXAxis(plotBackground, textStyle);
    this.makeYAxis(plotBackground, textStyle);
    var spritePlot = makeSpriteFromGraphics(plotBackground, this.renderer);
    spritePlot.position = new PIXI.Point(plotX, plotY);
    // creates an array of 0s
    // from https://stackoverflow.com/questions/1295584/most-efficient-way-to-create-a-zero-filled-javascript-array
    this.graphData = Array.apply(null, Array(spritePlot.width)).map(Number.prototype.valueOf,0);
    this.drawingIndices = Array.apply(null, Array(spritePlot.width)).map(Number.prototype.valueOf,0);

    spritePlot.interactive = true;
    var self = this;
    spritePlot.on('pointerdown', function(){self.onClick(spritePlot);});
    spritePlot.on('pointerup', function(){self.onUp(spritePlot);});
    spritePlot.on('pointerupoutside', function(){self.onUp(spritePlot);});

    // adjust the zero line for drawing
    this.plotDrawingZero += plotY;
    this.stage.addChild(spritePlot);

    this.makeLabels(spritePlot, textStyle);

    this.renderer.render(this.stage);
  }

  onClick(sprite) {
    var clickDraw = new PIXI.Graphics;
    clickDraw.lineStyle(1, 0x0000ff, 1);
    clickDraw.moveTo(event.clientX, event.clientY);
    clickDraw.lineTo(event.clientX, this.plotDrawingZero);
    this.currentMouseX = event.clientX;
    this.stage.addChild(clickDraw);
    var self = this;
    sprite.on('pointermove', function(){self.onMove(sprite);});
    this.renderer.render(this.stage);
  }

  onMove(sprite) {
    // This is intended to remove spaces between blue lines caused by
    // mouse moves that are faster than the browser can send events.
    // It's a rough solution...
    var canDraw = (event.clientX > sprite.x && event.clientX < sprite.x + sprite.width);
    var willDrawToUpperEdge = event.clientY > sprite.y + sprite.height;
    var willDrawToLowerEdge = event.clientY < sprite.y;
    if (canDraw) {

      var dx = Math.abs(this.currentMouseX - event.clientX);

      for (var i = 0; i < 2 * dx; i++) {

        // make sure we don't go off of either of the sides of the plot
        var canStillDraw = (event.clientX + i > sprite.x && event.clientX + i < sprite.x + sprite.width);

        if (canStillDraw) {

          // remove any bar that's already at this point
          var oldClickDraw = this.drawingIndices[event.clientX + i - sprite.x];
          if (oldClickDraw != 0) {
            this.stage.removeChild(oldClickDraw);
          }

          var clickDraw = new PIXI.Graphics;
          clickDraw.lineStyle(1, 0x0000ff, 1);
          clickDraw.moveTo(event.clientX + i, this.plotDrawingZero);

          if (willDrawToUpperEdge) {
            clickDraw.lineTo(event.clientX + i, sprite.y + sprite.height);
          } else if (willDrawToLowerEdge) {
            clickDraw.lineTo(event.clientX + i, sprite.y)
          } else {
            clickDraw.lineTo(event.clientX + i, event.clientY);
          }

          // store the bar we're drawing so we can remove it later if we need to
          this.drawingIndices[event.clientX + i - sprite.x] = clickDraw;

          //data
          var positionOnGraph = event.clientY - sprite.y;
          var yrange = this.ymax - this.ymin;
          var valueOfGraph = (-1 * ((positionOnGraph * yrange) / sprite.height) - this.ymin);
          this.graphData[event.clientX + i - sprite.x] = valueOfGraph;

          this.stage.addChild(clickDraw);
        }
      }

      this.currentMouseX = event.clientX;
      var self = this;
      this.renderer.render(this.stage);
    }
  }

  onUp(sprite) {
    sprite.off('pointermove');
    console.log(this.graphData);
  }

  makeBackground() {
    var background = new PIXI.Graphics;
    background.lineStyle(3, 0x000000, 1); //black
    background.beginFill(0xd3d3d3, 1); //grey
    background.drawRect(0, 0, this.width, this.height);
    background.endFill();
    var spriteBackground = makeSpriteFromGraphics(background, this.renderer);
    spriteBackground.position = new PIXI.Point(this.x, this.y);
    return background;
  }

  makeTitle(style) {
    var titleInset = new PIXI.Point(10, 10);
    var pixiTitle = new PIXI.Text(this.title, style);
    var spritePixiTitle = makeSpriteFromGraphics(pixiTitle, this.renderer);
    spritePixiTitle.x = this.x + titleInset.x;
    spritePixiTitle.y = this.y + titleInset.y;
    return spritePixiTitle;
  }

  makePlotBackground(width, height) {
    var plot = new PIXI.Graphics;
    plot.lineStyle(1, 0x000000, 1); //black
    plot.beginFill(0xffffff, 1); //white
    plot.drawRect(0, 0, width, height);
    plot.endFill();
    return plot;
  }

  makeXAxis(plot, labelTextStyle, stageForLabel) {
    // x-axis and axis Label
    var axisThickness = 1;
    var xrange = this.xmax - this.xmin;
    var numSteps = xrange / this.xinc;
    var stepLength = plot.width / numSteps;
    var labelPadding = 5;

    for (var i = 0; i <= numSteps; i++) {
      var xpos = labelPadding + i * stepLength - 1;

      if (i > 0 && i < numSteps) {
        var xaxis = new PIXI.Graphics;
        var axisColor = 0xd3d3d3; //grey

        if (this.xmin + i * this.xinc == 0) {
          axisColor = 0x000000; //black
        }

        xaxis.lineStyle(axisThickness, axisColor);
        xaxis.moveTo(xpos, 1);
        xaxis.lineTo(xpos, plot.height - 1);
        plot.addChild(xaxis);
      }
    }
  }

  makeYAxis(plot, labelTextStyle, stageForLabel) {
    // x-axis and axis Label
    var axisThickness = 1;
    var yrange = this.ymax - this.ymin;
    var numSteps = yrange / this.yinc;
    var stepLength = plot.height / numSteps;
    var labelPadding = 25;

    for (var i = 0; i <= numSteps; i++) {
      var ypos = i * stepLength - 1;

      if (i > 0 && i < numSteps) {
        var yaxis = new PIXI.Graphics;
        var axisColor = 0xd3d3d3; //grey

        if (this.ymin + i * this.yinc == 0) {
          this.plotDrawingZero = ypos;
          axisColor = 0x000000; //black
        }

        yaxis.lineStyle(axisThickness, axisColor);

        // the +/-1s are to keep the grey lines from running into the border
        yaxis.moveTo(1, ypos);
        yaxis.lineTo(plot.width - 1, ypos);
        plot.addChild(yaxis);
      }
    }
  }

  makeLabels(plot, labelTextStyle) {
    var xrange = this.xmax - this.xmin;
    var numSteps = xrange / this.xinc;
    var stepLength = plot.width / numSteps;
    var labelPadding = 5;

    for (var i = 0; i <= numSteps; i++) {
      var xpos = plot.x + labelPadding + i * stepLength - 1;
      var formattedNumber = (this.xmin + i * this.xinc).toFixed(1);
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
