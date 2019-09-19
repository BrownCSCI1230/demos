function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

function rgbToHex(r, g, b) {
    return "0x" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

function lerp(x1, x2, y1, y2, x) {
  if(x1 == x2) {
    return y1;
  }

  var m = (y2 - y1) / (x2 - x1);
  return (m * (x - x1) + y1);
}

class Graph {
  constructor(element, width, height, xmin, xmax, xinc, ymin, ymax, yinc, title, isBlank) {
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
    this.isBlank = isBlank;

    this.plot;
    this.graphData = [];
    this.dataPoints = [];
    this.totalShift = 0;

    this.clickedPoint;
    this.isClicking;

    this.renderer = new PIXI.Renderer({
      width: width,
      height: height,
      resolution: window.devicePixelRatio || 1,
      autoResize: true
    });

    this.renderer.backgroundColor = 0xffffff;
    element.appendChild(this.renderer.view);
    this.stage = new PIXI.Container;
    this.draw(isBlank);
  }

  getGraphData() {
    return this.graphData;
  }

  getGraphData(x) {
    // var xdiff = this.xmax - this.xmin;
    // var i = Math.round(((x - this.xmin) / xdiff) * this.graphData.length);
    // i = Math.max(0, Math.min(this.graphData.length - 1, i));
    return this.graphData[x];
  }

  draw(isBlank) {
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

    this.plot.position = new PIXI.Point(plotX, plotY);
    this.stage.addChild(this.plot);

    this.makeLabels(this.plot, textStyle);

    // creates an array of 0s
    // from https://stackoverflow.com/questions/1295584/most-efficient-way-to-create-a-zero-filled-javascript-array
    this.graphData = Array.apply(null, Array(this.xmax - this.xmin + 1)).map(Number.prototype.valueOf,0);

    if (!isBlank) {
      for (var i = 0; i <= this.xmax - this.xmin; i++) {
        var dataPoint = this.makeDataPoint(9, 9);
        this.dataPoints[i] = dataPoint;
        dataPoint.x = this.convertToPlotPixel(this.xmin + i, true) - dataPoint.width / 2;
        dataPoint.y = this.convertToPlotPixel(0, false) - dataPoint.height / 2;
        this.stage.addChild(dataPoint);
      }
    }

    // Callbacks
    var self = this;

    this.plot.interactive = true;

    this.plot.on('pointerdown', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onClick(mousePos);
    });

    this.plot.on('pointermove', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onMove(mousePos);
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

    // Render
    this.renderer.render(this.stage);
  }

  clearAll() {
    for (var i = 0; i < this.dataPoints.length; i++) {
      this.graphData[i] = 0;

      if (this.dataPoints[i] !== null) {
        this.stage.removeChild(this.dataPoints[i]);
        this.dataPoints[i] = null;
      }

      if (!this.isBlank) {
        var dataPoint = this.makeDataPoint(9, 9);
        this.dataPoints[i] = dataPoint;
        dataPoint.x = this.convertToPlotPixel(this.xmin + i, true) - dataPoint.width / 2;
        dataPoint.y = this.convertToPlotPixel(0, false) - dataPoint.height / 2;
        this.stage.addChild(dataPoint);
      }
    }
    this.renderer.render(this.stage);
  }

  onClick(mousePos) {
    /*
    var clientX = Math.max(this.plot.x, Math.min(this.plot.x + this.plot.width, mousePos.x));
    var clientY = Math.max(this.plot.y, Math.min(this.plot.y + this.plot.height, mousePos.y));
    var thisIndex = Math.round(this.convertToPlotCoordinate(clientX, true)) - this.xmin;
    this.clickedPoint = this.dataPoints[thisIndex];
    var width = this.clickedPoint.width;
    var height = this.clickedPoint.height;
    this.clickedPoint.clear();
    this.clickedPoint.beginFill(0x0000ff);
    this.clickedPoint.drawRect(0, 0, width, height);
    this.clickedPoint.endFill();
    this.isClicking = true;
    this.renderer.render(this.stage);

    // hacky way to give index in order to update result
    return thisIndex;
    */

    this.plot.on('pointermove', function(eventData){
      var data = eventData.data;
      var pos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, pos, data.global);
      self.onMove(pos);
    });

    this.currentMouseX = mousePos.x;
    this.currentMouseY = mousePos.y;
    this.isClicking = true;
    this.renderer.render(this.stage);
  }

  onMove(mousePos) {
    /*
    if (this.isClicking) {
      var clientY = Math.max(this.plot.y, Math.min(this.plot.y + this.plot.height, mousePos.y));
      this.clickedPoint.y = clientY - this.clickedPoint.height / 2;
      var graphDataIndex = Math.round(this.convertToPlotCoordinate(this.clickedPoint.x - this.clickedPoint.width / 2, true)) - this.xmin;
      this.graphData[graphDataIndex] = this.convertToPlotCoordinate(this.clickedPoint.y);

      this.renderer.render(this.stage);
      return graphDataIndex;
    }
    return;
    */

    if (this.isClicking) {
      // This is intended to remove spaces between blue lines caused by
      // mouse moves that are faster than the browser can send events.
      // It's a rough solution...
      var clientX = Math.max(this.plot.x, Math.min(this.plot.x + this.plot.width, mousePos.x));
      var clientY = Math.max(this.plot.y, Math.min(this.plot.y + this.plot.height, mousePos.y));

      var start;
      var stop;

      if (clientX > this.currentMouseX) {
        start = this.convertToPlotCoordinate(this.currentMouseX, true) - this.xmin;
        stop = this.convertToPlotCoordinate(clientX, true) - this.xmin;
      } else {
        start = this.convertToPlotCoordinate(clientX, true) - this.xmin;
        stop = this.convertToPlotCoordinate(this.currentMouseX, true) - this.xmin;
      }

      start = Math.round(start);
      stop = Math.round(stop);

      for (var i = start; i <= stop; i += 1) {
      	// make sure we don't go off of either of the sides of the plot
      	var canStillDraw = (i >= 0 && i < this.graphData.length);

      	if (canStillDraw) {
      	  // store the bar we're drawing so we can remove it later if we need to
          var lerpy = lerp(stop, start, clientY, this.currentMouseY, i);
	  this.movePoint(this.dataPoints[i], clientY);
	  this.graphData[i] = this.convertToPlotCoordinate(lerpy);
      	}
      }

      this.currentMouseX = clientX;
      this.currentMouseY = clientY;
      this.renderer.render(this.stage);
    }
  }

  movePoint(point, y) {
    point.y = y - point.height / 2;

    var width = point.width;
    var height = point.height;

    point.clear();
    point.beginFill(0x000000);
    point.drawRect(0, 0, width, height);
    point.endFill();

    this.renderer.render(this.stage);
  }

  onUp(mousePos) {
    if (this.isClicking) {
      this.isClicking = false;
    }

    this.plot.off('pointermove');
  }

  moveDataPoint(index, yCoord) {
    var dataPoint = this.dataPoints[index];

    if (dataPoint == null) {
      dataPoint = this.makeDataPoint(9, 9) // TODO: set constants
      dataPoint.x = this.convertToPlotPixel(index + this.xmin, true) - dataPoint.width / 2;
      this.dataPoints[index] = dataPoint;
      this.stage.addChild(dataPoint);
    }
    dataPoint.y = this.convertToPlotPixel(yCoord, false) - dataPoint.height / 2;
    this.graphData[index] = yCoord;
  }

  shiftEntireLine(shift) {
    this.totalShift += shift;
    var newDataPoints = [];
    for (var i = 0; i < this.dataPoints.length; i++) {
      if (this.dataPoints[i] != null) {
        this.stage.removeChild(this.dataPoints[i]);
      }
      if (this.graphData[i + this.totalShift] !== undefined) {
        var newPoint = this.makeDataPoint(9, 9);
        newPoint.x = this.convertToPlotPixel(i + this.xmin, true) - newPoint.width / 2;
        newPoint.y = this.convertToPlotPixel(this.graphData[i + this.totalShift], false) - newPoint.height / 2;
        this.stage.addChild(newPoint);
        newDataPoints[i] = newPoint;
      } else {
        newDataPoints[i] = null;
      }
    }
    this.dataPoints = newDataPoints;
  }

  normalizePoints() {
    var sum = 0;
    for (var i = 0; i < this.graphData.length; i++) {
      sum += this.graphData[i];
    }

    if (sum != 0) {
      for (var i = 0; i < this.graphData.length; i++) {
        var original = this.graphData[i];
        var newValue = original / sum;
        this.graphData[i] = newValue;
        this.moveDataPoint(i, newValue);
      }

      this.renderer.render(this.stage);
    }
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

    for (var i = 0; i <= numSteps; i++) {
      var xpos = i * stepLength - 1;

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
    labelPadding = 27;

    for (var i = 0; i <= numSteps; i++) {
      var ypos = plot.y + i * stepLength - 1;
      var formattedNumber = (this.ymax - i * this.yinc).toFixed(2);
      var ytitle = new PIXI.Text(formattedNumber, labelTextStyle);
      ytitle.x = plot.x - labelPadding;
      // subtracting textHeight / 2 centers the text around the correct point
      ytitle.y = ypos - ytitle.height / 2;
      this.stage.addChild(ytitle);
    }
  }

  makeDataPoint(width, height) {
    var dataPoint = new PIXI.Graphics;
    dataPoint.beginFill(0x000000, 1); //white
    dataPoint.drawRect(0, 0, width, height);
    dataPoint.endFill();

    dataPoint.interactive = true;
    var self = this;

    dataPoint.on('pointerdown', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onClick(mousePos);
    });

    dataPoint.on('pointermove', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onMove(mousePos);
    });

    dataPoint.on('pointerup', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onUp(mousePos);
    });

    dataPoint.on('pointerupoutside', function(eventData){
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(self.stage, mousePos, data.global);
      self.onUp(mousePos);
    });

    return dataPoint;
  }

// These two functions aren't quite right
  convertToPlotPixel(unit, isX) {
    if (isX) {
      var xrange = this.xmax - this.xmin;
      var value = (((unit - this.xmin) / xrange) * this.plot.width) + this.plot.x
      return Math.max(this.plot.x - 10, Math.min(this.plot.width + this.plot.x + 10, value));
    } else {
      var yrange = this.ymax - this.ymin;
      var value = (((this.ymax - unit) / yrange) * this.plot.height) + this.plot.y;
      return Math.max(this.plot.y, Math.min(this.plot.height + this.plot.y, value));
    }
  }

  convertToPlotCoordinate(unit, isX) {
    if (isX) {
      var xrange = this.xmax - this.xmin;
      var value = (((unit - this.plot.x) / this.plot.width) * xrange) + this.xmin;
      return Math.max(this.xmin, Math.min(this.xmax, value));
    } else {
      var yrange = this.ymax - this.ymin;
      var value =  -1 * ((((unit - this.plot.y) / this.plot.height) * yrange) - this.ymax);
      return Math.max(this.ymin, Math.min(this.ymax, value));
    }
  }

}
