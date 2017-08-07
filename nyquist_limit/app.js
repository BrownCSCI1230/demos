/* Nyquist limit application */
var appWidth = 800;
var appHeight = 600;

// Sampler class
function Sampler(start, end, step, f) {
  this.samples = [];

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

  // TODO: label offset
  // TODO: element to attach to
  // TODO: keyword arguments for constructor
  // TODO: figure out how to deal with range not being correct
  // TODO: figure out how to label sliders
  // TODO: bar highlighting
  // TODO: sliders to determine sampling and sine wave frequency
  // TODO: helper methods, adding interactive circles, adding ticks, adding points, converting point locations, etc.

  // Properties
  this.x = x;
  this.y = y;
  this.w = width;
  this.h = height;

  this.samples = samples.length === 0 ? barSamples : samples;
  this.barSamples = barSamples;
  this.bars = Array.apply(null, Array(this.barSamples.length)).map(Number.prototype.valueOf,0);
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

    for(var i = 0; i < this.samples.length; i++) {
      ymin = Math.min(this.samples[i].y, ymin);
      ymax = Math.max(this.samples[i].y, ymax);
    }

    this.yRange = [ymin, ymax];
  }

  this.yZero = this.h + this.yRange[0] / (this.yRange[1] - this.yRange[0]) * this.h;
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

Graph.prototype.setSamples = function(samples) {
  this.samples = samples;
}

Graph.prototype.setBarSamples = function(barSamples) {
  for(var i = 0; i < this.barSamples.length; i++) {
    this.removeChild(this.bars[i]);
  }

  this.barSamples = barSamples;
  this.bars = Array.apply(null, Array(this.barSamples.length)).map(Number.prototype.valueOf,0);
}

Graph.prototype.setupAxes = function() {
  this.axes = new PIXI.Graphics();
  this.axes.setTransform(this.yLabel.width, this.title.height);
  this.addChild(this.axes);

  // Axis numbering font
  var numberStyle = new PIXI.TextStyle({
    fontFamily: 'Arial',
    fontSize: 10
  });

  // Draw graph lines x-axis
  var step = this.w / this.xGridSize;
  var xDiff = this.xRange[1] - this.xRange[0];
  this.axes.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.xGridSize; i++) {
    var v = step * i;

    if(i > 0) {
      this.axes.moveTo(v, this.h + this.tickWidth);
      this.axes.lineTo(v, this.h - this.tickWidth);
    }

    var text = new PIXI.Text((xDiff / this.xGridSize) * i + this.xRange[0], numberStyle);
    text.setTransform(this.axes.transform);
    text.anchor.set(0.5);
    text.x = v + this.yLabel.width;
    text.y = this.h + this.tickWidth + text.height / 2 + this.title.height;
    this.addChild(text);
  }

  this.axes.moveTo(-1, this.h);
  this.axes.lineTo(this.w, this.h);

  // Draw graph lines y-axis
  var step = this.h / this.yGridSize;
  var yDiff = this.yRange[1] - this.yRange[0];
  this.axes.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.yGridSize; i++) {
    var v = step * i;
    this.axes.moveTo(-this.tickWidth, this.h - v);
    this.axes.lineTo(this.tickWidth, this.h - v);

    var text = new PIXI.Text((((yDiff / this.yGridSize) * i) + this.yRange[0]), numberStyle);
    text.setTransform(this.axes.transform);
    text.anchor.set(0.5);
    text.x = -this.tickWidth - text.width / 2 + this.yLabel.width;
    text.y = this.h - v + this.title.height;
    this.addChild(text);
  }

  this.axes.moveTo(0, 0);
  this.axes.lineTo(0, this.h + 1);

  // Draw graph line 0
  this.axes.moveTo(0, this.yZero);
  this.axes.lineTo(this.w + 1, this.yZero);
}

Graph.prototype.draw = function() {
  this.graphics.clear();
  this.graphics.setTransform(this.yLabel.width, this.title.height);

  // Draw graph
  if(this.graphType === 0 || this.graphType === 2) {
    this.graphics.lineStyle(1, this.graphColor, 1);

    for(var i = 0; i < this.samples.length; i++) {
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

    for(var i = 0; i < this.barSamples.length; i++) {
      var s = this.barSamples[i];
      var px = (s.x - this.xRange[0]) / (this.xRange[1] - this.xRange[0]) * this.w;
      var py = this.h - (s.y - this.yRange[0]) / (this.yRange[1] - this.yRange[0]) * this.h;
      this.addBar(px, py, i);
    }
  }
}

Graph.prototype.addBar = function(px, py, i) {
  // Create bar
  var bar = new PIXI.Graphics();
  bar.interactive = true;
  bar.alpha = 0.5;

  bar.setTransform(this.yLabel.width, this.title.height);

  bar.beginFill(this.barColor);
  bar.drawRect(px - 1, this.yZero, 2, py - this.yZero);
  bar.endFill();

  bar.beginFill(0x0000FF);
  bar.lineStyle(0, this.barColor, 0);
  bar.drawCircle(px, py, 4);
  bar.endFill();

  // make circle non-transparent when mouse is over it
  bar.mouseover = (function(mouseData) {
    this.bars[i].alpha = 1;
    this.pairGraph.bars[i].alpha = 1;
  }).bind(this);

  // make circle half-transparent when mouse leaves
  bar.mouseout = (function(mouseData) {
    this.bars[i].alpha = 0.5;
    this.pairGraph.bars[i].alpha = 0.5;
  }).bind(this);

  this.addChild(bar);
  this.bars[i] = bar;
}

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
  graph1.setupAxes();

  var graph2 = new Graph(padding, appHeight / 4 + 2 * padding, appWidth, appHeight / 4, s2.samples, s2.samples, 1);
  graph2.xRange = graph1.xRange;
  graph2.yRange = graph1.yRange;
  graph2.setupAxes();

  var graph3 = new Graph(padding, appHeight / 2 + 3 * padding, appWidth, appHeight / 4, s2.samples, [], 0);
  graph3.xRange = graph1.xRange;
  graph3.yRange = graph1.yRange;
  graph3.setupAxes();

  // Set up pair graphs
  graph1.pairGraph = graph2;
  graph2.pairGraph = graph1;

  graph1.draw();
  graph2.draw();
  graph3.draw();

  app.stage.addChild(graph1);
  app.stage.addChild(graph2);
  app.stage.addChild(graph3);

  // Add slider
  $("#frequencySlider").slider({
    min: 0,
    max: 20,
    value: 10,
    animate: "slow",
    slide: frequencySliderMoved
  });

  $("#sampleSlider").slider({
    min: 0,
    max: 40,
    value: 20,
    animate: "slow",
    slide: sampleSliderMoved
  });

  graph1.frequency = 10;
  graph2.frequency = 20;

  function frequencySliderMoved(eventSlider, uiSlider) {
    graph1.frequency = uiSlider.value;

    var s1 = new Sampler(0, 100, 0.1, function(x) {
      return Math.sin(x / 100 * 2 * Math.PI * graph1.frequency);
    });

    var s2 = new Sampler(0, 100, 100 / graph2.frequency, function(x) {
      return Math.sin(x / 100 * 2 * Math.PI * graph1.frequency);
    });

    graph1.setSamples(s1.samples);
    graph1.setBarSamples(s2.samples);
    graph2.setBarSamples(s2.samples);
    graph3.setSamples(s2.samples);

    graph1.draw();
    graph2.draw();
    graph3.draw();
  }

  function sampleSliderMoved(eventSlider, uiSlider) {
    graph2.frequency = uiSlider.value;

    var s2 = new Sampler(0, 100, 100 / graph2.frequency, function(x) {
      return Math.sin(x / 100 * 2 * Math.PI * graph1.frequency);
    });

    graph1.setBarSamples(s2.samples);
    graph2.setBarSamples(s2.samples);
    graph3.setSamples(s2.samples);

    graph1.draw();
    graph2.draw();
    graph3.draw();
  }
});
