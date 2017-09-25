function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

function rgbToHex(r, g, b) {
    return "0x" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

class Graph {
  constructor(element, width, height, xmin, xmax, xinc, ymin, ymax, yinc, title) {
    //TODO: Change these
    this.x = 0;
    this.y = 0;
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

    this.plot;
    this.currentMouseX = 0;
    this.currentMouseY = 0;
    this.plotDrawingZero = 0;
    this.drawingIndices = [];
    this.graphData = [];

    this.renderer = new PIXI.CanvasRenderer(width, height, {
      resolution: window.devicePixelRatio || 1,
      autoResize: true
    });
    this.renderer.backgroundColor = 0xffffff;
    element.appendChild(this.renderer.view);
    this.stage = new PIXI.Container;
    this.draw();
  }

  getGraphArea() {
    var sum = 0;

    for(var i = 0; i < this.graphData.length; i++) {
      sum += this.graphData[i];
    }

    var width = this.xmax - this.xmin;
    return width * sum / this.graphData.length;
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
      var canvasY = this.convertToPixel(y, false);

      // store the bar we're drawing so we can remove it later if we need to
      this.addBarAt(i, canvasY);
    }

    this.renderer.render(this.stage);
  }

  setGraphDataAtIndices(graphData, start, end) {
    this.graphData = graphData;

    for (var i = start; i < end; i ++) {
      var y = graphData[i];
      var canvasY = this.convertToPixel(y, false);

      // store the bar we're drawing so we can remove it later if we need to
      this.addBarAt(i, canvasY);
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

    this.plot = this.makePlotBackground(plotWidth, plotHeight);

    this.makeXAxis(this.plot, textStyle);
    this.makeYAxis(this.plot, textStyle);
    // var this.plot = makeSpriteFromGraphics(plotBackground, this.renderer);
    this.plot.position = new PIXI.Point(plotX, plotY);
    // creates an array of 0s
    // from https://stackoverflow.com/questions/1295584/most-efficient-way-to-create-a-zero-filled-javascript-array
    this.graphData = Array.apply(null, Array(this.plot.width)).map(Number.prototype.valueOf,0);
    this.drawingIndices = Array.apply(null, Array(this.plot.width)).map(Number.prototype.valueOf,0);

    this.plot.interactive = true;
    var self = this;
    this.plot.on('pointerdown', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onClick(mousePos);
    });
    this.plot.on('pointerup', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onUp(mousePos);
    });
    this.plot.on('pointerupoutside', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onUp(mousePos);
    });

    // adjust the zero line for drawing
    this.plotDrawingZero += plotY;
    this.stage.addChild(this.plot);

    this.makeLabels(this.plot, textStyle);

    this.renderer.render(this.stage);
  }

  addBarAt(i, j) {
    // Remove old element and add new element if we might need to remove later
    var oldBar = this.drawingIndices[i];

    if (oldBar != 0) {
      this.stage.removeChild(oldBar);
    }

    // Add new bar
    var bar = new PIXI.Graphics;
    bar.lineStyle(1, 0x0000ff, 1);
    bar.moveTo(this.plot.x + i, this.plotDrawingZero);
    bar.lineTo(this.plot.x + i, this.plot.y + j);

    this.drawingIndices[i] = bar;
    this.stage.addChild(bar);

    // Graph Data
    this.graphData[i] = this.convertToPlot(j, false);
  }

  clearAllBars() {
    for (var i = 0; i < this.drawingIndices.length; i++) {
      var bar = this.drawingIndices[i];
      if (bar != 0) {
        this.stage.removeChild(bar);
        this.drawingIndices[i] = 0;
        this.graphData[i] = 0;
      }
    }
    this.renderer.render(this.stage);
  }

  onClick(mousePos) {
    this.addBarAt(mousePos.x - this.plot.x, mousePos.y - this.plot.y);

    var self = this;
    this.plot.on('pointermove', function(eventData){
      var data = eventData.data;
      var pos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, pos, data.global);
      self.onMove(pos);
    });

    this.currentMouseX = mousePos.x;
    this.currentMouseY = mousePos.y;
    this.renderer.render(this.stage);
  }

  onMove(mousePos) {
    // This is intended to remove spaces between blue lines caused by
    // mouse moves that are faster than the browser can send events.
    // It's a rough solution...
    var clientX = Math.max(this.plot.x, Math.min(this.plot.x + this.plot.width, mousePos.x));
    var clientY = Math.max(this.plot.y, Math.min(this.plot.y + this.plot.height, mousePos.y));

    var dx = this.currentMouseX - clientX;
    var step = Math.sign(dx);

    if(step !== 0) {
      for (var i = 0; i !== (dx + step); i += step) {
      	// make sure we don't go off of either of the sides of the plot
      	var canStillDraw = (clientX + i > this.plot.x && clientX + i < this.plot.x + this.plot.width);

      	if (canStillDraw) {
	  var startY = this.currentMouseY - this.plot.y;
	  var lambda = Math.abs(i / dx);

      	  // store the bar we're drawing so we can remove it later if we need to
      	  this.addBarAt(clientX + i - this.plot.x, (1 - lambda) * (clientY - this.plot.y) + lambda * startY);
      	}
      }
    }

    this.currentMouseX = clientX;
    this.currentMouseY = clientY;
    this.renderer.render(this.stage);
  }

  onUp(mousePos) {
    this.plot.off('pointermove');
  }

  makeBackground() {
    var background = new PIXI.Graphics;
    background.lineStyle(3, 0x000000, 1); //black
    background.beginFill(0xd3d3d3, 1); //grey
    background.drawRect(0, 0, this.width, this.height);
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

  makeXAxis(plot, labelTextStyle, stageForLabel) {
    // x-axis and axis Label
    var axisThickness = 1;
    var xrange = this.xmax - this.xmin;
    var numSteps = xrange / this.xinc;
    var stepLength = plot.width / numSteps;
    var labelPadding = 5;

    for (var i = 0; i <= numSteps; i++) {
      var xpos = plot.x + i * stepLength;

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

      if (this.ymin + i * this.yinc == 0) {
        this.plotDrawingZero = stepLength - 1 ;
        axisColor = 0x000000; //black
      }

      if (i > 0 && i < numSteps) {
        var yaxis = new PIXI.Graphics;
        var axisColor = 0xd3d3d3; //grey

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

  drawBox(start, end, height) {
    var startPix = this.convertToPixel(start, true);
    var endPix = this.convertToPixel(end, true);
    var heightPix = this.convertToPixel(height, false);

    for (var i = startPix; i <= endPix; i++) {
      this.addBarAt(i, heightPix);
    }

    this.renderer.render(this.stage);
  }

  drawTriangle(start, end, height) {
    var startPix = this.convertToPixel(start, true);
    var endPix = this.convertToPixel(end, true);
    var slope = 2 * height / (end - start);
    var mid = (end + start) / 2;

    for (var i = startPix; i <= endPix; i++) {
      var sampleX = this.convertToPlot(i, true) - mid;
      var triangleHeight = (-slope * Math.abs(sampleX - mid) + height);
      var triangleHeightPixel = this.convertToPixel(triangleHeight, false);

      this.addBarAt(i, triangleHeightPixel);
    }

    this.renderer.render(this.stage);
  }

  drawGaussian(start, end) {
    var startPix = this.convertToPlotPixel(start, true);
    var endPix = this.convertToPlotPixel(end, true);

    for (var i = startPix; i <= endPix; i++) {
      var sampleX = this.convertToPlotCoordinate(i, true);
      var gaussianCoeff = (1 / Math.sqrt(2 * Math.PI));
      var gaussianExp = - (1 / 2) * Math.pow(sampleX, 2);
      var gaussianVal = gaussianCoeff * Math.pow(Math.E, gaussianExp);
      var pixGaussianVal = this.convertToPlotPixel(gaussianVal, false);

      this.addBarAt(i, pixGaussianVal);
    }

    this.renderer.render(this.stage);
  }

  drawGaussian(start, end) {
    var startPix = this.convertToPixel(start, true);
    var endPix = this.convertToPixel(end, true);

    for (var i = startPix; i < endPix; i++) {
      var sampleX = this.convertToPlot(i, true);
      var gaussianCoeff = (1 / Math.sqrt(2 * Math.PI));
      var gaussianExp = - (1 / 2) * Math.pow(sampleX, 2);
      var gaussianVal = gaussianCoeff * Math.pow(Math.E, gaussianExp);
      var pixGaussianVal = this.convertToPixel(gaussianVal, false);

      this.addBarAt(i, pixGaussianVal);
    }

    this.renderer.render(this.stage);
  }

  drawSinc(start, end) {
    var startPix = this.convertToPixel(start, true);
    var endPix = this.convertToPixel(end, true);

    for (var i = startPix; i < endPix; i++) {
      var sampleX = this.convertToPlot(i, true);

      if (sampleX == 0) {
        continue;
      }

      var sincNumerator = Math.sin(Math.PI * sampleX);
      var sincDenominator = Math.PI * sampleX;
      var sincVal = sincNumerator / sincDenominator;
      var pixSincVal = this.convertToPixel(sincVal, false);

      this.addBarAt(i, pixSincVal);
    }

    this.renderer.render(this.stage);
  }

  convertToPixel(unit, isX) {
    if (isX) {
      var xrange = this.xmax - this.xmin;
      var i = Math.floor(((unit - this.xmin) / xrange) * this.plot.width);
      return Math.max(0, Math.min(this.plot.width - 1, i));
    } else {
      var yrange = this.ymax - this.ymin;
      var i = Math.floor(((this.ymax - unit) / yrange) * this.plot.height);
      return Math.max(0, Math.min(this.plot.height - 1, i));
    }
  }

  convertToPlot(unit, isX) {
    if (isX) {
      var xrange = this.xmax - this.xmin;
      var x = ((unit / this.plot.width) * xrange) + this.xmin;
      return Math.max(this.xmin, Math.min(this.xmax, x));
    } else {
      var yrange = this.ymax - this.ymin;
      var y = (((this.plot.height - unit) / this.plot.height) * yrange) + this.ymin;
      return Math.max(this.ymin, Math.min(this.ymax, y));
    }
  }

  convertToXIndex(x) {
      var i = Math.round(((x - this.xmin) / (this.xmax - this.xmin)) * this.graphData.length);
      return Math.max(0, Math.min(this.plot.width - 1, i));
  }

  convertToYIndex(y) {
      var i = Math.round(((y - this.ymin) / (this.ymax - this.ymin)) * this.graphData.length);
      return Math.max(0, Math.min(this.plot.height, i));
  }
}
