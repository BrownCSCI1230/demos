var appWidth = 800;
var appHeight = 600;

$(function() {
  var graph = new Graph(10, 10, 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Function to be Convolved]");
  graph.draw();
})
