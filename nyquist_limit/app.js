/* Nyquist limit application */
var appWidth = 800;
var appHeight = 600;

// Sampler class
function Sampler(start, end, step, f) {
  this.samples = [];

  // TODO: epsilon here
  for(var x = start; x <= end; x += step) {
    this.samples.push(new PIXI.Point(x, f(x)));
  }
}

/* Graph class */
Graph.constructor = Graph;
Graph.prototype = Object.create(PIXI.Container.prototype);

function Graph(x = 0, y = 0, width = 0, height = 0,
    samples = [], barSamples = [], graphType = 0,
    graphColor = 0x000000, barColor = 0x000000,
    title = 'function', xLabel = 'x axis', yLabel = 'y axis',
    titleSize = 12, labelSize = 12, titleFont = 'Arial', labelFont = 'Arial',
    tickWidth = 2, xGridSize = 5, yGridSize = 4, xRange = [], yRange = []) {
  PIXI.Container.call(this);

  // Properties
  this.x = x;
  this.y = y;
  this.w = width;
  this.h = height;

  this.samples = samples.length === 0 ? barSamples : samples;
  this.barSamples = barSamples;
  this.graphType = graphType;
  this.graphColor = graphColor;
  this.barColor = barColor;

  this.titleText = title;
  this.xLabelText = xLabel;
  this.yLabelText = yLabel;

  this.titleSize = titleSize;
  this.labelSize = labelSize;
  this.titleFont = titleFont;
  this.labelFont = labelFont;

  this.tickWidth = tickWidth;
  this.xGridSize = xGridSize;
  this.yGridSize = yGridSize;
  this.xRange = xRange;
  this.yRange = yRange;

  // Setup
  this.setupRange();
  this.setupGraphics();
  this.setupLabels();
}

Graph.prototype.setupRange = function() {
  if(this.xRange.length === 0 && this.samples.length > 0) {
    this.xRange = [this.samples[0].x, this.samples[this.samples.length - 1].x];
  }

  if(this.yRange.length === 0 && this.samples.length > 0) {
    var ymin = Infinity;
    var ymax = -Infinity;

    for(var i = 0; i < this.samples.length; i += 1) {
      ymin = Math.min(this.samples[i].y, ymin);
      ymax = Math.max(this.samples[i].y, ymax);
    }

    this.yRange = [ymin, ymax];
  }
}

Graph.prototype.setupGraphics = function() {
  // Graphics object
  this.graphics = new PIXI.Graphics();
  this.addChild(this.graphics);
}

Graph.prototype.setupLabels = function() {
  // Labels
  var labelStyle = new PIXI.TextStyle({
    fontFamily: this.labelFont,
    fontSize: this.labelSize
  });

  var titleStyle = new PIXI.TextStyle({
    fontFamily: this.titleFont,
    fontSize: this.titleSize
  });

  // TODO: drawing polygon with negative values
  // TODO: mouse drag event for convolution graph
  // TODO: only add labels once
  // TODO: convolution graph interaction stuff within the class
  // TODO: label offset
  // TODO: bar highlighting
  // TODO: sliders to determine sampling and sine wave frequency
  // TODO: helper methods, adding interactive circles, adding ticks, adding points, converting point locations, etc.
  // TODO: helper methods for drawing polygons

  this.title = new PIXI.Text(this.titleText, titleStyle);
  this.yLabel = new PIXI.Text(this.yLabelText, labelStyle);
  this.xLabel = new PIXI.Text(this.xLabelText, labelStyle);

  this.title.anchor.set(0.5);
  this.title.x = this.yLabel.width + this.w / 2;
  this.title.y = 0;
  this.addChild(this.title);

  this.yLabel.anchor.set(0.5);
  this.yLabel.x = -10;
  this.yLabel.y = this.title.height + this.h / 2;
  this.addChild(this.yLabel);

  this.xLabel.anchor.set(0.5);
  this.xLabel.x = this.yLabel.width + this.w / 2;
  this.xLabel.y = this.title.height + this.h + this.xLabel.height + 10;
  this.addChild(this.xLabel);
}

Graph.prototype.draw = function() {
  this.graphics.clear();
  this.graphics.setTransform(this.yLabel.width, this.title.height);

  // Axis numbering font
  var numberStyle = new PIXI.TextStyle({
    fontFamily: 'Arial',
    fontSize: 10
  });

  // Draw graph lines x-axis
  var step = this.w / this.xGridSize;
  var xDiff = this.xRange[1] - this.xRange[0];
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.xGridSize; i++) {
    var v = step * i;

    if(i > 0) {
      this.graphics.moveTo(v, this.h + this.tickWidth);
      this.graphics.lineTo(v, this.h - this.tickWidth);
    }

    var text = new PIXI.Text((xDiff / this.xGridSize) * i + this.xRange[0], numberStyle);
    text.setTransform(this.graphics.transform);
    text.anchor.set(0.5);
    text.x = v + this.yLabel.width;
    text.y = this.h + this.tickWidth + text.height / 2 + this.title.height;
    this.addChild(text);
  }

  this.graphics.moveTo(-1, this.h);
  this.graphics.lineTo(this.w, this.h);

  // Draw graph lines y-axis
  var step = this.h / this.yGridSize;
  var yDiff = this.yRange[1] - this.yRange[0];
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.yGridSize; i++) {
    var v = step * i;
    this.graphics.moveTo(-this.tickWidth, this.h - v);
    this.graphics.lineTo(this.tickWidth, this.h - v);

    var text = new PIXI.Text((((yDiff / this.yGridSize) * i) + this.yRange[0]), numberStyle);
    text.setTransform(this.graphics.transform);
    text.anchor.set(0.5);
    text.x = -this.tickWidth - text.width / 2 + this.yLabel.width;
    text.y = this.h - v + this.title.height;
    this.addChild(text);
  }

  this.graphics.moveTo(0, 0);
  this.graphics.lineTo(0, this.h + 1);

  // Draw graph line 0
  var yZero = this.h + this.yRange[0] / (this.yRange[1] - this.yRange[0]) * this.h;
  this.graphics.moveTo(0, yZero);
  this.graphics.lineTo(this.w + 1, yZero);

  // Draw graph
  if(this.graphType === 0 || this.graphType === 2) {
    this.graphics.lineStyle(1, this.graphColor, 1);

    for(var i = 0; i < this.samples.length; i += 1) {
      var s = this.samples[i];
      var px = (s.x - this.xRange[0]) / (this.xRange[1] - this.xRange[0]) * this.w;
      var py = this.h - (s.y - this.yRange[0]) / (this.yRange[1] - this.yRange[0]) * this.h;

      if(i == 0) {
	this.graphics.moveTo(px, py);
      }
      else {
	this.graphics.lineTo(px, py);
      }

      this.graphics.moveTo(px, py);
    }
  }

  if(this.graphType == 1 || this.graphType === 2) {
    this.graphics.lineStyle(1, this.barColor, 1);

    for(var i = 0; i < this.barSamples.length; i += 1) {
      var s = this.barSamples[i];
      var px = (s.x - this.xRange[0]) / (this.xRange[1] - this.xRange[0]) * this.w;
      var py = this.h - (s.y - this.yRange[0]) / (this.yRange[1] - this.yRange[0]) * this.h;

      this.graphics.moveTo(px, yZero);
      this.graphics.lineTo(px, py);

      var barHead = new PIXI.Graphics();
      barHead.setTransform(this.yLabel.width, this.title.height);
      barHead.beginFill(0x0000FF);
      barHead.drawCircle(px, py, 4);
      barHead.endFill();
      barHead.alpha = 0.5;
      this.addChild(barHead);
      barHead.interactive = true;

      // make circle non-transparent when mouse is over it
      barHead.mouseover = function(mouseData) {
	this.alpha = 1;
      }

      // make circle half-transparent when mouse leaves
      barHead.mouseout = function(mouseData) {
	this.alpha = 0.5;
      }
    }
  }
}

/* ConvolutionGraph class */
/*
ConvolutionGraph.constructor = ConvolutionGraph;
ConvolutionGraph.prototype = Object.create(PIXI.Container.prototype);

function ConvolutionGraph(x = 0, y = 0, width = 0, height = 0,
    xRange = [], yRange = [], resolution = 100,
    color = 0x000000,
    title = 'function', xLabel = 'x axis', yLabel = 'y axis',
    titleSize = 12, labelSize = 12, titleFont = 'Arial', labelFont = 'Arial',
    tickWidth = 2, xGridSize = 5, yGridSize = 4) {

  PIXI.Container.call(this);

  // Properties
  this.x = x;
  this.y = y;
  this.w = width;
  this.h = height;
  this.xRange = xRange;
  this.yRange = yRange;
  this.resolution = resolution;
  this.samples = new Array(this.resolution + 1);

  for(var i = 0; i <= this.resolution; i++) {
    this.samples[i] = new PIXI.Point(xRange[0] + ((xRange[1] - xRange[0]) / resolution) * i, 1);
  }

  this.color = color;

  this.titleText = title;
  this.xLabelText = xLabel;
  this.yLabelText = yLabel;

  this.titleSize = titleSize;
  this.labelSize = labelSize;
  this.titleFont = titleFont;
  this.labelFont = labelFont;

  this.tickWidth = tickWidth;
  this.xGridSize = xGridSize;
  this.yGridSize = yGridSize;

  // Setup
  this.setupGraphics();
  this.setupLabels();
}

ConvolutionGraph.prototype.setupGraphics = function() {
  // Graphics object
  this.graphics = new PIXI.Graphics();
  this.addChild(this.graphics);
}

ConvolutionGraph.prototype.setupLabels = function() {
  // Labels
  var labelStyle = new PIXI.TextStyle({
    fontFamily: this.labelFont,
    fontSize: this.labelSize
  });

  var titleStyle = new PIXI.TextStyle({
    fontFamily: this.titleFont,
    fontSize: this.titleSize
  });

  // TODO: label offset
  // TODO: bar highlighting
  // TODO: sliders to determine sampling and sine wave frequency
  // TODO: helper methods, adding interactive circles, adding ticks, etc.

  this.title = new PIXI.Text(this.titleText, titleStyle);
  this.yLabel = new PIXI.Text(this.yLabelText, labelStyle);
  this.xLabel = new PIXI.Text(this.xLabelText, labelStyle);

  this.title.anchor.set(0.5);
  this.title.x = this.yLabel.width + this.w / 2;
  this.title.y = 0;
  this.addChild(this.title);

  this.yLabel.anchor.set(0.5);
  this.yLabel.x = -10;
  this.yLabel.y = this.title.height + this.h / 2;
  this.addChild(this.yLabel);

  this.xLabel.anchor.set(0.5);
  this.xLabel.x = this.yLabel.width + this.w / 2;
  this.xLabel.y = this.title.height + this.h + this.xLabel.height + 10;
  this.addChild(this.xLabel);
}

ConvolutionGraph.prototype.draw = function() {
  this.graphics.clear();
  this.graphics.setTransform(this.yLabel.width, this.title.height);

  // Axis numbering font
  var numberStyle = new PIXI.TextStyle({
    fontFamily: 'Arial',
    fontSize: 10
  });

  // Draw graph lines x-axis
  var step = this.w / this.xGridSize;
  var xDiff = this.xRange[1] - this.xRange[0];
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.xGridSize; i++) {
    var v = step * i;

    if(i > 0) {
      this.graphics.moveTo(v, this.h + this.tickWidth);
      this.graphics.lineTo(v, this.h - this.tickWidth);
    }

    var text = new PIXI.Text((xDiff / this.xGridSize) * i + this.xRange[0], numberStyle);
    text.setTransform(this.graphics.transform);
    text.anchor.set(0.5);
    text.x = v + this.yLabel.width;
    text.y = this.h + this.tickWidth + text.height / 2 + this.title.height;
    this.addChild(text);
  }

  this.graphics.moveTo(-1, this.h);
  this.graphics.lineTo(this.w, this.h);

  // Draw graph lines y-axis
  var step = this.h / this.yGridSize;
  var yDiff = this.yRange[1] - this.yRange[0];
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.yGridSize; i++) {
    var v = step * i;
    this.graphics.moveTo(-this.tickWidth, this.h - v);
    this.graphics.lineTo(this.tickWidth, this.h - v);

    var text = new PIXI.Text((((yDiff / this.yGridSize) * i) + this.yRange[0]), numberStyle);
    text.setTransform(this.graphics.transform);
    text.anchor.set(0.5);
    text.x = -this.tickWidth - text.width / 2 + this.yLabel.width;
    text.y = this.h - v + this.title.height;
    this.addChild(text);
  }

  this.graphics.moveTo(0, 0);
  this.graphics.lineTo(0, this.h + 1);

  // Draw graph line 0
  var yZero = this.h + this.yRange[0] / (this.yRange[1] - this.yRange[0]) * this.h;
  this.graphics.moveTo(0, yZero);
  this.graphics.lineTo(this.w + 1, yZero);

  // Draw graph
  var path = [];
  var lastSign = 1;
  var startPoint = new PIXI.Point(0, yZero);
  var lastPoint = startPoint;
  path.push(startPoint);

  for(var i = 0; i < this.samples.length; i += 1) {
    var s = this.samples[i];
    var px = (s.x - this.xRange[0]) / (this.xRange[1] - this.xRange[0]) * this.w;
    var py = this.h - (s.y - this.yRange[0]) / (this.yRange[1] - this.yRange[0]) * this.h;

    var curSign = Math.sign(s.y);
    var curPoint = new PIXI.Point(px, py);

    if(curSign != lastSign) {
      var newStartPoint = new PIXI.Point((lastPoint.x + curPoint.x) / 2, yZero);
      path.push(newStartPoint);
      path.push(startPoint);

      this.graphics.beginFill(this.color);
      this.graphics.drawPolygon(path);
      this.graphics.endFill();

      startPoint = newStartPoint;
      path = [];
      path.push(startPoint);
    }

    path.push(curPoint);
    lastSign = Math.sign(s.y);
    lastPoint = curPoint;
  }

  i = this.samples.length - 1;
  var s = this.samples[i];
  var px = (s.x - this.xRange[0]) / (this.xRange[1] - this.xRange[0]) * this.w;
  path.push(new PIXI.Point(px, yZero));
  path.push(startPoint);

  this.graphics.beginFill(this.color);
  this.graphics.drawPolygon(path);
  this.graphics.endFill();
}
*/

$(function() {
  var padding = 100;

  // Add application to DOM
  var app = new PIXI.Application(appWidth + 4 * padding, appHeight + 4 * padding, {backgroundColor : 0xffffff});
  app.view.classList.add('centered');
  document.body.appendChild(app.view);

  // Sample points for graphs
  var s1 = new Sampler(0, 100, 0.5, function(x) {
    return Math.sin(x / 10 * 2 * Math.PI);
  });

  var s2 = new Sampler(0, 100, 4, function(x) {
    return Math.sin(x / 10 * 2 * Math.PI);
  });

  // Add graphs
  var graph1 = new Graph(padding, padding, appWidth, appHeight / 4, s1.samples, s2.samples, 2, 0x000000, 0xFF0000);
  var graph2 = new Graph(padding, appHeight / 4 + 2 * padding, appWidth, appHeight / 4, s2.samples, s2.samples, 1);
  graph2.xRange = graph1.xRange;
  graph2.yRange = graph1.yRange;
  var graph3 = new Graph(padding, appHeight / 2 + 3 * padding, appWidth, appHeight / 4, s2.samples, [], 0);
  graph3.xRange = graph1.xRange;
  graph3.yRange = graph1.yRange;

  /*
  var graph3 = new ConvolutionGraph(padding, appHeight / 2 + 3 * padding, appWidth, appHeight / 4, graph1.xRange, graph1.yRange);
  graph3.interactive = true;
  graph3.hitArea = new PIXI.Rectangle(graph3.yLabel.width, graph3.title.height, graph3.w, graph3.h);

  app.stage.interactive = true;
  app.stage.mousedown = function(mouseData) {
    this.isMouseDown = true;
  };
  app.stage.mouseup = function(mouseData) {
    this.isMouseDown = false;
  };
  graph3.mousemove = function(mouseData) {
    if(!this.isMouseDown || this.isMouseDown === undefined) {
    }
    else {
      var data = mouseData.data;
      var pt = new PIXI.Point(0, 0);
      data.getLocalPosition(this, pt, data.global);

      if(this.lastClickPoint !== undefined) {
	pt.x = Math.max(this.yLabel.width, Math.min(this.w + this.yLabel.width, pt.x));
	pt.y = Math.max(this.title.height, Math.min(this.h + this.title.height, pt.y));

	var startI = Math.round(((this.lastClickPoint.x - this.yLabel.width) / this.w) * this.samples.length);
	var stopI = Math.round(((pt.x - this.yLabel.width) / this.w) * this.samples.length);
	var step = Math.sign(stopI - startI);

	if(step !== 0) {
	  for(var i = startI; i !== (stopI + step); i += step) {
	    this.samples[i] = new PIXI.Point(this.xRange[0] + ((this.xRange[1] - this.xRange[0]) / this.resolution) * i,
		((this.h - (pt.y - this.title.height)) / this.h) * (this.yRange[1] - this.yRange[0]) + this.yRange[0]);
	  }

	  this.draw();
	}

	this.lastClickPoint = pt;
      }
      else {
	this.lastClickPoint = pt;
      }
    }
  };
  */

  graph1.draw();
  graph2.draw();
  graph3.draw();

  app.stage.addChild(graph1);
  app.stage.addChild(graph2);
  app.stage.addChild(graph3);
});
