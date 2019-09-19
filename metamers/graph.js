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

class Graph {
  constructor(element, width, height, xmin, xmax, xinc, ymin, ymax, yinc, title) {
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

    this.currentMouseX = 0;
    this.currentMouseY = 0;
    this.plotDrawingZero = 0;
    this.totalShift = 0;
    this.drawingIndices = [];
    this.graphData = [];
    this.isClicking = false;

    this.renderer = new PIXI.Renderer(width, height, {
      resolution: window.devicePixelRatio || 1,
      autoResize: true
    });
    this.renderer.backgroundColor = 0xffffff;
    element.appendChild(this.renderer.view);
    this.stage = new PIXI.Container;
    this.draw();
  }

  getGraphData(x) {
    var xdiff = this.xmax - this.xmin;
    var i = Math.round(((x - this.xmin) / xdiff) * this.drawingIndices.length);
    i = Math.max(0, Math.min(this.drawingIndices.length - 1, i));
    return this.graphData[i + this.drawingIndices.length];
  }

  getGraphDataRange(start, end, step) {
    var data = [];

    for(var i = start; i != end + step; i += step) {
      data.push(this.getGraphData(i));
    }

    return data;
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
    var rightPadding = 30;
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
    this.graphData = Array.apply(null, Array(3 * this.plot.width)).map(Number.prototype.valueOf,0);
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

    if (i >= this.drawingIndices.length && i < 2 * this.drawingIndices.length) {
      // Remove old element and add new element if we might need to remove later
      var drawingIndex = i - this.drawingIndices.length;
      var oldBar = this.drawingIndices[drawingIndex];

      if (oldBar != 0) {
        this.stage.removeChild(oldBar);
      }

      // Add new bar
      var bar = new PIXI.Graphics;

      bar.lineStyle(1, wavelengthToColor(this.convertToPlot(drawingIndex, true)), 1);
      bar.moveTo(this.plot.x + drawingIndex, this.plotDrawingZero);
      bar.lineTo(this.plot.x + drawingIndex, this.plot.y + j);

      this.drawingIndices[drawingIndex] = bar;
      this.stage.addChild(bar);
    }

    // Graph Data
    this.graphData[i] = this.convertToPlot(j, false);
  }

  clearAllBars() {
    for (var i = 0; i < this.graphData.length; i++) {
      var dataPoint = this.graphData[i];
      if (dataPoint != 0) {
        this.graphData[i] = 0
      }
      if (i >= this.drawingIndices.length && i < 2 * this.drawingIndices.length) {
        var bar = this.drawingIndices[i - this.drawingIndices.length]
        if (bar != 0) {
          this.stage.removeChild(bar);
          this.drawingIndices[i - this.drawingIndices.length] = 0;
        }
      }
    }
    this.renderer.render(this.stage);
  }

  onClick(mousePos) {
    this.addBarAt(Math.round(mousePos.x - this.plot.x) + this.drawingIndices.length, mousePos.y - this.plot.y);

    var self = this;
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
    if (this.isClicking) {
      // This is intended to remove spaces between blue lines caused by
      // mouse moves that are faster than the browser can send events.
      // It's a rough solution...
      var clientX = Math.max(this.plot.x, Math.min(this.plot.x + this.plot.width, mousePos.x));
      var clientY = Math.max(this.plot.y, Math.min(this.plot.y + this.plot.height, mousePos.y));

      var start;
      var stop;

      if (clientX > this.currentMouseX) {
        start = Math.round(this.currentMouseX - this.plot.x);
        stop = Math.round(clientX - this.plot.x);
      } else {
        start = Math.round(clientX - this.plot.x);
        stop = Math.round(this.currentMouseX - this.plot.x);
      }

      for (var i = start; i < stop; i += 1) {
      	// make sure we don't go off of either of the sides of the plot
      	var canStillDraw = (i > 0 && i < this.plot.width);

      	if (canStillDraw) {
      	  // store the bar we're drawing so we can remove it later if we need to
          var lerpy = lerp(clientX - this.plot.x, this.currentMouseX - this.plot.x, clientY - this.plot.y, this.currentMouseY - this.plot.y, i);
      	  this.addBarAt(i + this.drawingIndices.length, lerpy);
      	}
      }

      this.currentMouseX = clientX;
      this.currentMouseY = clientY;
      this.renderer.render(this.stage);
    }
  }

  onUp(mousePos) {
    if (this.isClicking) {
      this.isClicking = false;
    }
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

      if (i > 0 && i < numSteps) {
        var yaxis = new PIXI.Graphics;
        var axisColor = 0xd3d3d3; //grey

        if (this.ymax - i * this.yinc == 0) {
          axisColor = 0x000000; //black
        }

        yaxis.lineStyle(axisThickness, axisColor);

        // the +/-1s are to keep the grey lines from running into the border
        yaxis.moveTo(1, ypos);
        yaxis.lineTo(plot.width - 1, ypos);
        plot.addChild(yaxis);
      }
    }

    this.plotDrawingZero = (this.ymax / this.yinc) * stepLength - 1;
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
}
