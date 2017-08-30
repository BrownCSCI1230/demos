
$(function() {
  var signal = new Graph(document.getElementById("signalGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Function to be Convolved]");
  var filter = new Graph(document.getElementById("filterGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "g(x) [Filter]");
  var product = new Graph(document.getElementById("productGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x)g(x) [Product]");
  var result = new Graph(document.getElementById("resultGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) * g(x) [Convolution]");

  console.log(signal.convertToPixel(signal.convertToPlot(100, false), false));

  /* Convolve signal with filter */
  var updateResult = function() {
    var convolutionData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);
    var productData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);

    var resultXDiff = result.xmax - result.xmin;
    var filterXDiff = filter.xmax - filter.xmin;

    var resultStep = resultXDiff / result.graphData.length;
    var filterStep = filterXDiff / filter.graphData.length;

    // Iterate over all x values in result
    for(var resultX = result.xmin; resultX <= result.xmax; resultX += resultStep) {
      var sum = 0;

      // Iterate over all x values in filter
      for(var filterX = filter.xmin; filterX <= filter.xmax; filterX += filterStep) {
	// Product of values of the filter at (filterX) and the original signal at
	// (resultX - filterX) will contribute to value of result signal at resultX
	var signalX = resultX - filterX;

	// Accumulate result of convolution at result x
	sum += filter.getGraphData(filterX) * signal.getGraphData(signalX) * filterStep;
      }

      // Convolution result
      var i = result.convertToPixel(resultX, true);
      convolutionData[i] = sum;

      // Product result
      productData[i] = filter.getGraphData(resultX) * signal.getGraphData(resultX);
    }

    result.setGraphData(convolutionData);
    console.log(result.graphData);
    product.setGraphData(productData);
  }

  $( "#filterSlider" ).slider({
    min: 0,
    max: filter.graphData.length,
    value: filter.graphData.length / 2,
    animate: "slow",
    slide: sliderDidMove
  });

  function sliderDidMove(eventSlider, uiSlider) {
    updateResultForValue(uiSlider.value);
  }

  var lastSliderPos = filter.graphData.length / 2;

  function updateResultForValue(value) {
    convolutionData = result.graphData;
    productData = product.graphData;

    var resultXDiff = result.xmax - result.xmin;
    var filterXDiff = filter.xmax - filter.xmin;

    var resultStep = resultXDiff / result.graphData.length;
    var filterStep = filterXDiff / filter.graphData.length;

    var start = Math.min(lastSliderPos, value);
    var end = Math.max(lastSliderPos, value);

    for (var i = start; i <= end; i++) {
      var resultX = i * resultStep + result.xmin;
      var sum = 0;

      for(var filterX = filter.xmin; filterX <= filter.xmax; filterX += filterStep) {
        var signalX = resultX - filterX;
        sum += filter.getGraphData(filterX) * signal.getGraphData(signalX) * filterStep;
      }

      convolutionData[i] = sum;
      productData[i] = signal.getGraphData(resultX) * filter.getGraphData(resultX);
    }

    result.setGraphDataAtIndices(convolutionData, start, end);
    product.setGraphDataAtIndices(productData, start, end);

    lastSliderPos = value;
  }

  /* Hacky way to call previous onMove function, and do something else as well */
  signal.doMove = signal.onMove;
  filter.doMove = filter.onMove;

  signal.onMove = function(sprite, mousePos) {
    signal.doMove(sprite, mousePos);
  }

  filter.onMove = function(sprite, mousePos) {
    filter.doMove(sprite, mousePos);
  }

  var clearFunctionButton = document.getElementById("clearFunctionButton");
  clearFunctionButton.addEventListener("click", function(){
    signal.clearAllBars();
    updateResult();
  });

  var clearFilterButton = document.getElementById("clearFilterButton");
  clearFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    updateResult();
  });

  var boxFunctionButton = document.getElementById("boxFunctionButton");
  boxFunctionButton.addEventListener("click", function(){
    signal.clearAllBars();
    signal.drawBox(-0.5, 0.5, 1);
  });

  var boxFilterButton = document.getElementById("boxFilterButton");
  boxFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawBox(-0.5, 0.5, 1);
  });

  var triangleFilterButton = document.getElementById("triangleFilterButton");
  triangleFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawTriangle(-1, 1, 1);
  });

  var gaussianFilterButton = document.getElementById("gaussianFilterButton");
  gaussianFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawGaussian(-4, 4);
  });

  var sincFilterButton = document.getElementById("sincFilterButton");
  sincFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawSinc(-4, 4);
  });

  var convolveButton = document.getElementById("convolveButton");
  convolveButton.addEventListener("click", function(){
    updateResult();
  });
});
