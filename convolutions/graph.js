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

    this.renderer = new PIXI.CanvasRenderer(window.innerWidth, window.innerHeight, {
      resolution: window.devicePixelRatio || 1,
      autoResize: true
    });
    this.renderer.backgroundColor = 0xffffff;
    document.body.appendChild(this.renderer.view);
    this.stage = new PIXI.Container;
  }

  draw() {

    var textStyle = new PIXI.TextStyle({
      fontSize: 12
    });
    // stage.interactive = true;

    var background = this.makeBackground();
    this.stage.addChild(background);
    var pixiTitle = this.makeTitle(textStyle);
    this.stage.addChild(pixiTitle);
    var plotBackground = this.makePlotBackground(background, pixiTitle);
    plotBackground.interactive = true;
    this.stage.addChild(plotBackground);
    var self = this;
    plotBackground.on('pointerdown', function(){
      self.onClick();
    });
    // stage.on('pointerdown', this.onClick);
    this.makeXAxis(plotBackground, textStyle);
    this.makeYAxis(plotBackground, textStyle);

    this.renderer.render(this.stage);
  }

  onClick() {
    console.log("clicked");
  }

  makeBackground() {
    var background = new PIXI.Graphics;
    background.lineStyle(3, 0x000000, 1);
    background.beginFill(0xd3d3d3, 1);
    background.drawRect(0, 0, this.width, this.height);
    background.position = new PIXI.Point(this.x, this.y);
    background.endFill();
    return background;
  }

  makeTitle(style) {
    var titleInset = new PIXI.Point(10, 10);
    var pixiTitle = new PIXI.Text(this.title, style);
    pixiTitle.x = this.x + titleInset.x;
    pixiTitle.y = this.y + titleInset.y;
    return pixiTitle;
  }

  makePlotBackground(background, title) {
    var rightPadding = 20;
    var leftPadding = 30;
    var bottomPadding = 30;
    var topPadding = 10;
    var plot = new PIXI.Graphics;
    plot.lineStyle(1, 0x000000, 1);
    plot.beginFill(0xffffff, 1);
    var plotX = background.x + leftPadding;
    var plotY = title.y + title.height + topPadding;
    var plotWidth = background.width - leftPadding - rightPadding;
    var plotHeight = (background.height + background.y) - bottomPadding - plotY;
    plot.drawRect(0, 0, plotWidth, plotHeight);
    plot.position = new PIXI.Point(plotX, plotY);
    plot.endFill();
    return plot;
  }

  makeXAxis(plot, labelTextStyle) {
    // x-axis and axis Label
    var axisThickness = 1;
    var xrange = this.xmax - this.xmin;
    var numSteps = xrange / this.xinc;
    var stepLength = plot.width / numSteps;
    var labelPadding = 5;

    for (var i = 0; i <= numSteps; i++) {
      var xpos = plot.x + i * stepLength - 1;

      if (i > 0 && i < numSteps) {
        var xaxis = new PIXI.Graphics;
        var axisColor = 0xd3d3d3;

        if (this.xmin + i * this.xinc == 0) {
          axisColor = 0x000000;
        }

        xaxis.lineStyle(axisThickness, axisColor);
        xaxis.moveTo(xpos, plot.y + 1);
        xaxis.lineTo(xpos, plot.y + plot.height - 1);
        this.stage.addChild(xaxis);
      }

      var formattedNumber = (this.xmin + i * this.xinc).toFixed(1);
      var xtitle = new PIXI.Text(formattedNumber, labelTextStyle);
      // subtracting textWidth / 2 centers the text around the correct point
      xtitle.x = xpos - xtitle.width / 2;
      xtitle.y = plot.y + plot.height + labelPadding;
      this.stage.addChild(xtitle);
    }
  }

  makeYAxis(plot, labelTextStyle) {
    // x-axis and axis Label
    var axisThickness = 1;
    var yrange = this.ymax - this.ymin;
    var numSteps = yrange / this.yinc;
    var stepLength = plot.height / numSteps;
    var labelPadding = 25;

    for (var i = 0; i <= numSteps; i++) {
      var ypos = plot.y + i * stepLength - 1;

      if (i > 0 && i < numSteps) {
        var yaxis = new PIXI.Graphics;
        var axisColor = 0xd3d3d3;

        if (this.ymin + i * this.yinc == 0) {
          axisColor = 0x000000;
        }

        yaxis.lineStyle(axisThickness, axisColor);

        // the +/-1s are to keep the grey lines from running into the border
        yaxis.moveTo(plot.x + 1, ypos);
        yaxis.lineTo(plot.x + plot.width - 1, ypos);
        this.stage.addChild(yaxis);
      }

      var formattedNumber = (this.ymax - i * this.yinc).toFixed(1);
      var ytitle = new PIXI.Text(formattedNumber, labelTextStyle);
      ytitle.x = plot.x - labelPadding;
      // subtracting textHeight / 2 centers the text around the correct point
      ytitle.y = ypos - ytitle.height / 2;
      this.stage.addChild(ytitle);
    }
  }
}
