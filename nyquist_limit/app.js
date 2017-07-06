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

// Graph class
function Graph(x = 0, y = 0, width = 0, height = 0, samples = [], isBar = false,
		xrange, yrange, title, xlabel, ylabel) {
  PIXI.Container.call(this);

  // Properties
  this.x = x;
  this.y = y;
  this.w = width;
  this.h = height;
  this.samples = samples;
  this.isBar = isBar;

  if(xrange === undefined && samples.length > 0) {
    this.xrange = [samples[0].x, samples[samples.length - 1].x];
  }
  else {
    this.xrange = xrange;
  }

  if(yrange === undefined && samples.length > 0) {
    var ymin = Infinity;
    var ymax = -Infinity;

    for(var i = 0; i < samples.length; i += 1) {
      ymin = Math.min(samples[i].y, ymin);
      ymax = Math.max(samples[i].y, ymax);
    }

    this.yrange = [ymin, ymax];
  }
  else {
    this.yrange = yrange;
  }

  this.titleText = (title !== undefined) ? title : 'function';
  this.xlabelText = (xlabel !== undefined) ? xlabel : 'x axis';
  this.ylabelText = (ylabel !== undefined) ? ylabel : 'y axis';

  // Graphics object
  this.graphics = new PIXI.Graphics();
  this.addChild(this.graphics);

  // Labels
  var style = new PIXI.TextStyle({
    fontFamily: 'Arial',
    fontSize: 10
  });

  // TODO: axis padding
  // TODO: axis tick number
  // TODO: x and y axis buffer
  // TODO: text size for labels and title
  // TODO: tick numbers
  // TODO: both bars and regular graph
  // TODO: bar highlighting

  this.title = new PIXI.Text(this.titleText, style);
  this.ylabel = new PIXI.Text(this.ylabelText, style);
  this.xlabel = new PIXI.Text(this.xlabelText, style);

  this.title.anchor.set(0.5);
  this.title.x = this.ylabel.width + this.w / 2;
  this.title.y = 0;
  this.addChild(this.title);


  this.ylabel.anchor.set(0.5);
  this.ylabel.x = 0;
  this.ylabel.y = this.title.height + this.h / 2;
  this.addChild(this.ylabel);

  this.xlabel.anchor.set(0.5);
  this.xlabel.x = this.ylabel.width + this.w / 2;
  this.xlabel.y = this.title.height + this.h + 15;
  this.addChild(this.xlabel);
}

// TODO: setter and getter for samples

Graph.constructor = Graph;
Graph.prototype = Object.create(PIXI.Container.prototype);

Graph.prototype.draw = function() {
  this.graphics.clear();
  this.graphics.setTransform(this.ylabel.width, this.title.height);

  // Draw graph lines x-axis
  var step = this.w / 20;
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var v = 0; v < this.w; v += step) {
    this.graphics.moveTo(v, this.h + 2 + 5);
    this.graphics.lineTo(v, this.h - 2 + 5);
  }

  this.graphics.moveTo(-5, this.h + 5);
  this.graphics.lineTo(this.w, this.h + 5);

  // Draw graph lines y-axis
  var step = this.h / 20;
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var v = 0; v < this.h; v += step) {
    this.graphics.moveTo(-5 - 2, v);
    this.graphics.lineTo(-5 + 2, v);
  }

  this.graphics.moveTo(-5, 0);
  this.graphics.lineTo(-5, this.h + 5);

  // Draw graph
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var i = 0; i < this.samples.length; i += 1) {
    var s = this.samples[i];
    var px = (s.x - this.xrange[0]) / (this.xrange[1] - this.xrange[0]) * this.w;
    var py = this.h - (s.y - this.yrange[0]) / (this.yrange[1] - this.yrange[0]) * this.h;
    var sy = this.h + this.yrange[0] / (this.yrange[1] - this.yrange[0]) * this.h;

    if(!this.isBar) {
      if(i == 0) {
	this.graphics.moveTo(px, py);
      }
      else {
	this.graphics.lineTo(px, py);
      }
    }
    else {
      this.graphics.moveTo(px, sy);
      this.graphics.lineTo(px, py);
      this.graphics.beginFill();
      this.graphics.drawCircle(px, py, 2);
      this.graphics.endFill();
    }
  }
}

$(function() {
  var padding = 50;

  // Add application to DOM
  var app = new PIXI.Application(appWidth + 4 * padding, appHeight + 4 * padding, {backgroundColor : 0xffffff});
  app.view.classList.add('centered');
  document.body.appendChild(app.view);

  // TODO: sliders to determine sampling and sine wave frequency

  // Sample points for graphs
  var s1 = new Sampler(0, 100, 0.5, function(x) {
    return Math.sin(x / 10 * 2 * Math.PI);
  });

  var s2 = new Sampler(0, 100, 4, function(x) {
    return Math.sin(x / 10 * 2 * Math.PI);
  });

  // Add graphs
  var graph1 = new Graph(padding, padding, appWidth, appHeight / 4, s1.samples, false);
  var graph2 = new Graph(padding, appHeight / 4 + 2 * padding, appWidth, appHeight / 4, s2.samples, true, graph1.xrange, graph1.yrange);
  var graph3 = new Graph(padding, appHeight / 2 + 3 * padding, appWidth, appHeight / 4, s2.samples, false, graph1.xrange, graph1.yrange);

  graph1.draw();
  graph2.draw();
  graph3.draw();

  app.stage.addChild(graph1);
  app.stage.addChild(graph2);
  app.stage.addChild(graph3);
});
