/* Nyquist limit application */
var graphWidth = 800;
var graphHeight = 200;

// Sampler class
function Sampler(start, end, step, f) {
  this.samples = [];

  for(var x = start; x <= end; x += step) {
    this.samples.push(new PIXI.Point(x, f(x)));
  }
}

/* Graph class */
Graph.constructor = Graph;

function Graph({element = undefined, frequency = 0,
  x = 0, y = 0, width = 0, height = 0, xRange = [], yRange = [],
  samples = undefined, barSamples = undefined, graphType = 0,
  tickWidth = 2, xGridSize = 5, yGridSize = 4,
  graphColor = 0x000000, barColor = 0x000000,
  title = 'function', xLabel = 'x axis', yLabel = 'y axis',
  titleSize = 12, labelSize = 12, titleFont = 'Arial', labelFont = 'Arial'} = {})
{
  // TODO: save frequencty elsewhere
  // TODO: label offset
  // TODO: centerd graphs + smaller graphs
  // TODO: figure out how to label sliders
  
  // PIXI
  this.stage = new PIXI.Container;

  // Properties
  this.frequency = frequency;

  this.x = x;
  this.y = y;
  this.width = width;
  this.height = height;

  this.samples = samples;
  this.barSamples = barSamples;
  this.graphType = graphType;
  this.graphColor = graphColor;
  this.barColor = barColor;

  if(this.barSamples !== undefined) {
    this.bars = Array.apply(null, Array(this.barSamples.length)).map(Number.prototype.valueOf,0);
  }
  else {
    this.bars = undefined;
  }

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
  this.setupRange((this.samples === undefined ? this.barSamples : this.samples));
  this.setupGraphics();
  this.setupLabels();
  this.setupAxes();

  // Renderer
  this.element = document.getElementById(element);
  this.renderer = new PIXI.CanvasRenderer(width + this.yLabel.width + 30,
      height + this.title.height + this.xLabel.height + 30, {
    resolution: window.devicePixelRatio || 1,
    autoResize: true
  });
  this.renderer.backgroundColor = 0xFFFFFF;
  this.element.appendChild(this.renderer.view);
}

Graph.prototype.setupRange = function(samples) {
  if(this.xRange.length === 0 && samples.length > 0) {
    this.xRange = [samples[0].x, samples[samples.length - 1].x];
  }

  if(this.yRange.length === 0 && samples.length > 0) {
    var ymin = Infinity;
    var ymax = -Infinity;

    for(var i = 0; i < samples.length; i++) {
      ymin = Math.min(samples[i].y, ymin);
      ymax = Math.max(samples[i].y, ymax);
    }

    this.yRange = [ymin, ymax];
  }

  this.yZero = this.height + this.yRange[0] / (this.yRange[1] - this.yRange[0]) * this.height;
}

Graph.prototype.setupGraphics = function() {
  // Graphics object
  this.graphics = new PIXI.Graphics();
  this.stage.addChild(this.graphics);
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

  this.title.anchor.set(0.5, 0);
  this.title.x = this.yLabel.width + this.width / 2 + 15;
  this.title.y = 0;
  this.stage.addChild(this.title);

  this.yLabel.anchor.set(0, 0.5);
  this.yLabel.x = 0;
  this.yLabel.y = this.title.height + this.height / 2 + 15;
  this.stage.addChild(this.yLabel);

  this.xLabel.anchor.set(0.5, 1);
  this.xLabel.x = this.yLabel.width + this.width / 2 + 15;
  this.xLabel.y = this.title.height + this.height + this.xLabel.height + 15;
  this.stage.addChild(this.xLabel);
}

Graph.prototype.setSamples = function(samples) {
  this.samples = samples;
}

Graph.prototype.setBarSamples = function(barSamples) {
  for(var i = 0; i < this.barSamples.length; i++) {
    this.stage.removeChild(this.bars[i]);
  }

  this.barSamples = barSamples;
  this.bars = Array.apply(null, Array(this.barSamples.length)).map(Number.prototype.valueOf,0);
}

Graph.prototype.setupAxes = function() {
  this.axes = new PIXI.Graphics();
  this.axes.setTransform(this.yLabel.width + 15, this.title.height + 15);
  this.stage.addChild(this.axes);

  // Axis numbering font
  var numberStyle = new PIXI.TextStyle({
    fontFamily: 'Arial',
    fontSize: 10
  });

  // Draw graph lines x-axis
  var step = this.width / this.xGridSize;
  var xDiff = this.xRange[1] - this.xRange[0];
  this.axes.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.xGridSize; i++) {
    var v = step * i;

    if(i > 0) {
      this.axes.moveTo(v, this.height + this.tickWidth);
      this.axes.lineTo(v, this.height - this.tickWidth);
    }

    var text = new PIXI.Text((xDiff / this.xGridSize) * i + this.xRange[0], numberStyle);
    text.setTransform(this.axes.transform);
    text.anchor.set(0.5);
    text.x = v + this.yLabel.width + 15;
    text.y = this.height + this.tickWidth + text.height / 2 + this.title.height + 15;
    this.stage.addChild(text);
  }

  this.axes.moveTo(-1, this.height);
  this.axes.lineTo(this.width, this.height);

  // Draw graph lines y-axis
  var step = this.height / this.yGridSize;
  var yDiff = this.yRange[1] - this.yRange[0];
  this.axes.lineStyle(1, 0x000000, 1);

  for(var i = 0; i <= this.yGridSize; i++) {
    var v = step * i;
    this.axes.moveTo(-this.tickWidth, this.height - v);
    this.axes.lineTo(this.tickWidth, this.height - v);

    var text = new PIXI.Text((((yDiff / this.yGridSize) * i) + this.yRange[0]), numberStyle);
    text.setTransform(this.axes.transform);
    text.anchor.set(0.5);
    text.x = -this.tickWidth - text.width / 2 + this.yLabel.width + 15;
    text.y = this.height - v + this.title.height + 15;
    this.stage.addChild(text);
  }

  this.axes.moveTo(0, 0);
  this.axes.lineTo(0, this.height + 1);

  // Draw graph line 0
  this.axes.moveTo(0, this.yZero);
  this.axes.lineTo(this.width + 1, this.yZero);
}

Graph.prototype.draw = function() {
  this.graphics.clear();
  this.graphics.setTransform(this.yLabel.width + 15, this.title.height + 15);

  // Draw graph
  if(this.graphType === 0 || this.graphType === 2) {
    this.graphics.lineStyle(1, this.graphColor, 1);

    for(var i = 0; i < this.samples.length; i++) {
      var s = this.samples[i];
      var px = (s.x - this.xRange[0]) / (this.xRange[1] - this.xRange[0]) * this.width;
      var py = this.height - (s.y - this.yRange[0]) / (this.yRange[1] - this.yRange[0]) * this.height;

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
      var px = (s.x - this.xRange[0]) / (this.xRange[1] - this.xRange[0]) * this.width;
      var py = this.height - (s.y - this.yRange[0]) / (this.yRange[1] - this.yRange[0]) * this.height;
      this.addBar(px, py, i);
    }
  }

  // Render
  this.renderer.render(this.stage);
}

Graph.prototype.addBar = function(px, py, i) {
  // Create bar
  var bar = new PIXI.Graphics();
  var barWidth = this.width / (this.barSamples.length - 1);

  bar.interactive = true;
  bar.setTransform(this.yLabel.width + 15, this.title.height + 15);
  bar.hitArea = new PIXI.Rectangle(px - barWidth / 2, 0, barWidth, this.height);

  bar.alpha = 0.3;
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
    this.renderer.render(this.stage);
    this.pairGraph.renderer.render(this.pairGraph.stage);
  }).bind(this);

  // make circle half-transparent when mouse leaves
  bar.mouseout = (function(mouseData) {
    this.bars[i].alpha = 0.3;
    this.pairGraph.bars[i].alpha = 0.3;
    this.renderer.render(this.stage);
    this.pairGraph.renderer.render(this.pairGraph.stage);
  }).bind(this);

  this.stage.addChild(bar);
  this.bars[i] = bar;
}

$(function() {
  var padding = 100;
  var numPeriods = 10;
  var numSamples = 20;

  // Sample points for graphs
  var s1 = new Sampler(0, 100, 0.5, function(x) {
    return Math.sin(x / 100 * 2 * Math.PI * numPeriods);
  });

  var s2 = new Sampler(0, 100, 100 / numSamples, function(x) {
    return Math.sin(x / 100 * 2 * Math.PI * numPeriods);
  });

  // Add graphs
  var graph1 = new Graph({'x': 0, 'y': 0, 'width': graphWidth, 'height': graphHeight,
    'graphType': 2, 'samples': s1.samples, 'barSamples': s2.samples, 'barColor': 0xFF0000,
    'element': 'graph1', 'frequency': 10, 'title': 'f(x) [Orignal Function]', 'xLabel': '', 'yLabel': ''});

  var graph2 = new Graph({'x': 0, 'y': 0, 'width': graphWidth, 'height': graphHeight, 
    'graphType': 1, 'barSamples': s2.samples, 'xRange': graph1.xRange, 'yRange': graph1.yRange,
    'element': 'graph2', 'frequency': 20, 'title': 'f(X) [Sampled f(x)]', 'xLabel': '', 'yLabel': ''});

  var graph3 = new Graph({'x': 0, 'y': 0, 'width': graphWidth, 'height': graphHeight,
    'graphType': 0, 'samples': s2.samples, 'xRange': graph1.xRange, 'yRange': graph1.yRange,
    'element': 'graph3', 'title': 'f\'(x) [Reconstruction of f(x) with Triangle Filter]', 'xLabel': '', 'yLabel': ''});

  // Set up pair graphs
  graph1.pairGraph = graph2;
  graph2.pairGraph = graph1;

  // Draw graphs
  graph1.draw();
  graph2.draw();
  graph3.draw();

  // Add sliders
  var frequencySlider = $("#frequencySlider").slider({
    min: 0,
    max: 20,
    value: numPeriods,
    animate: "slow",
    slide: frequencySliderMoved
  }).slider("pips");

  var sampleSlider = $("#sampleSlider").slider({
    min: 0,
    max: 40,
    value: numSamples,
    animate: "slow",
    slide: sampleSliderMoved
  }).slider("pips");

  function frequencySliderMoved(eventSlider, uiSlider) {
    numPeriods = uiSlider.value;

    var s1 = new Sampler(0, 100, 0.1, function(x) {
      return Math.sin(x / 100 * 2 * Math.PI * numPeriods);
    });

    var s2 = new Sampler(0, 100, 100 / numSamples, function(x) {
      return Math.sin(x / 100 * 2 * Math.PI * numPeriods);
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
    numSamples = uiSlider.value;

    var s2 = new Sampler(0, 100, 100 / numSamples, function(x) {
      return Math.sin(x / 100 * 2 * Math.PI * numPeriods);
    });

    graph1.setBarSamples(s2.samples);
    graph2.setBarSamples(s2.samples);
    graph3.setSamples(s2.samples);

    graph1.draw();
    graph2.draw();
    graph3.draw();
  }
});
