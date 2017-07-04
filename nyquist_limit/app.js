// TODO: Graph object -> inherits display object
//     Samples
//     xRange (default sample range)
//     rRange (default sample range)
//     Title, XY labels
//     Children
//     Draw method [Graphics object, time] (only called repeatedly if necessary)
//     Tick method [Children, time] (only called repeatedly if necessary)
//     Update method (tick and draw)

// TODO: interactive bars

// TODO: labels

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
function Graph(x = 0, y = 0, width = 0, height = 0, samples = [], isBar = false, xrange, yrange) {
  PIXI.Container.call(this);

  // Properties
  this.x = x;
  this.y = y;
  this.w = width;
  this.h = height;
  this.isBar = isBar;
  this.samples = samples;

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

  // Graphics object
  this.graphics = new PIXI.Graphics();
  this.addChild(this.graphics);
}

Graph.constructor = Graph;
Graph.prototype = Object.create(PIXI.Container.prototype);

Graph.prototype.draw = function() {
  var sy = this.h + this.yrange[0] / (this.yrange[1] - this.yrange[0]) * this.h;

  this.graphics.clear();
  this.graphics.lineStyle(1, 0x000000, 1);

  for(var i = 0; i < this.samples.length; i += 1) {
    var s = this.samples[i];
    var px = (s.x - this.xrange[0]) / (this.xrange[1] - this.xrange[0]) * this.w;
    var py = this.h - (s.y - this.yrange[0]) / (this.yrange[1] - this.yrange[0]) * this.h;

    if(!this.isBar) {
      if(i == 0) {
	this.graphics.moveTo(px, py);
      }

      this.graphics.lineTo(px, py);
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
  var app = new PIXI.Application(appWidth, appHeight + 4 * padding, {backgroundColor : 0xffffff});
  app.view.classList.add('centered');
  document.body.appendChild(app.view);

  // Sample points for graphs
  var s1 = new Sampler(0, 100, 0.5, function(x) {
    return Math.sin(x / 10 * 2 * Math.PI);
  });

  var s2 = new Sampler(0, 100, 5, function(x) {
    return Math.sin(x / 10 * 2 * Math.PI);
  });

  var s3 = new Sampler(0, 100, 0.5, function(x) {
    var val = (x / 100) * 10;
    var start = Math.floor((x / 100) * 10);
    var end = Math.ceil((x / 100) * 10);
    var frac = val - start;

    if(Math.abs(frac) < 0.001) {
      return s2.samples[start];
    }
    else {
      return (1 - frac) * s2.samples[start].y + frac * s2.samples[end].y;
    }
  });

  // Add graphs
  var graph1 = new Graph(0, padding, appWidth, appHeight / 4, s1.samples, false);
  var graph2 = new Graph(0, appHeight / 4 + 2 * padding, appWidth, appHeight / 4, s2.samples, true);
  var graph3 = new Graph(0, appHeight / 2 + 3 * padding, appWidth, appHeight / 4, s2.samples, false);

  graph1.draw();
  graph2.draw();
  graph3.draw();

  app.stage.addChild(graph1);
  app.stage.addChild(graph2);
  app.stage.addChild(graph3);
});
