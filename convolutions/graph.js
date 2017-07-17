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

    this.renderer = new PIXI.CanvasRenderer(width, height, {
      resolution: window.devicePixelRatio || 1,
      autoResize: true
    });
    this.renderer.backgroundColor = 0xffffff;
    document.body.appendChild(this.renderer.view);
    this.stage = new PIXI.Container;
    this.draw();
  }

  getGraphData() {
    return this.graphData;
  }

  getGraphData(x) {
    var xdiff = this.xmax - this.xmin;
    var i = Math.round(((x - this.xmin) / xdiff) * this.graphData.length);
    i = Math.max(0, Math.min(this.graphData.length - 1, i));

    return this.graphData[i];
  }

  setGraphData(graphData) {
    this.graphData = graphData;

    for (var i = 0; i < graphData.length; i ++) {
      var y = graphData[i];
      var ydiff = this.ymax - this.ymin;
      var canvasY = Math.round(((y - this.ymin) / ydiff) * this.spritePlot.height);
      canvasY = Math.max(0, Math.min(this.spritePlot.height, canvasY))

      var clickDraw = new PIXI.Graphics;
      clickDraw.lineStyle(1, 0x0000ff, 1);
      clickDraw.moveTo(this.spritePlot.x + i, this.plotDrawingZero);
      clickDraw.lineTo(this.spritePlot.x + i, this.spritePlot.y + this.spritePlot.height - canvasY);

      // store the bar we're drawing so we can remove it later if we need to
      this.addBarAt(i, clickDraw);
      this.stage.addChild(clickDraw);
    }

    this.renderer.render(this.stage);
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
    spritePlot.on('pointerdown', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onClick(spritePlot, mousePos);
    });
    spritePlot.on('pointerup', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onUp(spritePlot, mousePos);
    });
    spritePlot.on('pointerupoutside', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onUp(spritePlot, mousePos);
    });
    this.spritePlot = spritePlot;

    // adjust the zero line for drawing
    this.plotDrawingZero += plotY;
    this.stage.addChild(spritePlot);

    this.makeLabels(spritePlot, textStyle);

    this.renderer.render(this.stage);
  }

  addBarAt(i, bar) {
    // Remove old element and add new element if we need to remove later
    var oldBar = this.drawingIndices[i];
    if (oldBar != 0) {
      this.stage.removeChild(oldBar);
    }
    this.drawingIndices[i] = bar;
    this.stage.addChild(bar);
  }

  onClick(sprite, mousePos) {
    var clickDraw = new PIXI.Graphics;
    clickDraw.lineStyle(1, 0x0000ff, 1);
    clickDraw.moveTo(mousePos.x, mousePos.y);
    clickDraw.lineTo(mousePos.x, this.plotDrawingZero);
    this.addBarAt(mousePos.x - sprite.x, clickDraw);

    var self = this;
    sprite.on('pointermove', function(eventData){
      var data = eventData.data;
      var pos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, pos, data.global);
      self.onMove(sprite, pos);
    });

    this.currentMouseX = mousePos.x;
    this.renderer.render(this.stage);
  }

  onMove(sprite, mousePos) {
    // This is intended to remove spaces between blue lines caused by
    // mouse moves that are faster than the browser can send events.
    // It's a rough solution...
    var clientX = Math.max(sprite.x, Math.min(sprite.x + sprite.width, mousePos.x));
    var clientY = Math.max(sprite.y, Math.min(sprite.y + sprite.height, mousePos.y));
    var willDrawToUpperEdge = clientY > sprite.y + sprite.height;
    var willDrawToLowerEdge = clientY < sprite.y;

    var dx = this.currentMouseX - clientX;
    var step = Math.sign(dx);

    if(step !== 0) {
      for (var i = 0; i !== (dx + step); i += step) {
	// make sure we don't go off of either of the sides of the plot
	var canStillDraw = (clientX + i > sprite.x && clientX + i < sprite.x + sprite.width);

	if (canStillDraw) {
	  var clickDraw = new PIXI.Graphics;
	  clickDraw.lineStyle(1, 0x0000ff, 1);
	  clickDraw.moveTo(clientX + i, this.plotDrawingZero);
	  clickDraw.lineTo(clientX + i, clientY);

	  // store the bar we're drawing so we can remove it later if we need to
	  this.addBarAt(clientX + i - sprite.x, clickDraw);

	  //data
	  var positionOnGraph = clientY - sprite.y;
	  var yrange = this.ymax - this.ymin;
	  var valueOfGraph = (-1 * ((positionOnGraph * yrange) / sprite.height) - this.ymin);
	  this.graphData[clientX + i - sprite.x] = valueOfGraph;

	  this.stage.addChild(clickDraw);
	}
      }
    }

    this.currentMouseX = clientX;
    var self = this;
    this.renderer.render(this.stage);
  }

  onUp(sprite, mousePos) {
    sprite.off('pointermove');
    //console.log(this.graphData);
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
