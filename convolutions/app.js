var appWidth = 800;
var appHeight = 600;

$(function() {
  var signal = new Graph(10, 10, 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Function to be Convolved]");
  var filter = new Graph(10, 10, 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Filter]");
  var result = new Graph(10, 10, 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Result]");

  /* Convolve signal with filter */
  var updateResult = function() {
    var resultData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);

    var resultXDiff = result.xmax - result.xmin;
    var filterXDiff = filter.xmax - filter.xmin;

    var resultStep = resultXDiff / result.graphData.length;
    var filterStep = filterXDiff / filter.graphData.length;

    for(var i = 0; i < result.graphData.length; i++) {
      var x = i * resultStep + result.xmin;
      var sum = 0;

      for(var filterX = filter.xmin; filterX <= filter.xmax; filterX += filterStep) {
	var signalX = x - filterX;
	sum += filter.getGraphData(filterX) * signal.getGraphData(signalX) * filterStep;
      }

      resultData[i] = sum;
    }

    result.setGraphData(resultData);
  }

  /* Hacky way to call previous onMove function, and do something else as well */
  signal.doMove = signal.onMove;
  filter.doMove = filter.onMove;

  signal.onMove = function(sprite, mousePos) {
    signal.doMove(sprite, mousePos);
    updateResult();
  }

  filter.onMove = function(sprite, mousePos) {
    filter.doMove(sprite, mousePos);
    updateResult();
  }
});
