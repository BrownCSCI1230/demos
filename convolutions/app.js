
$(function() {
  var signal = new Graph(document.getElementById("signalGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Function to be Convolved]");
  var filter = new Graph(document.getElementById("filterGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "g(x) [Filter]");
  var product = new Graph(document.getElementById("productGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x)g(x) [Product]");
  var result = new Graph(document.getElementById("resultGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) * g(x) [Convolution]");

  console.log(signal.convertToPlotPixel(signal.convertToPlotCoordinate(100, false), false));

  /* Convolve signal with filter */
  // Q: Why convert to plot coordinates, seems as if already in plot coordinates
  //    If new in plot coordinates, why compare to something that is not

  // Q: what are plot coordinates, what are not?
  // NOTE: looks like convert to plot coordinates is trying to convert index of graph
  // (from 0 to length - 1 of signal) into corresponding x value, but then why is it
  // being compared to index, and why is it used as index in getGraphData?

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

      // Get index of convolutionData that corresponds to resultX
      var i = Math.round(((resultX - result.xmin) / (result.xmax - result.xmin)) * convolutionData.length);
      i = Math.max(0, Math.min(convolutionData.length - 1, i));

      // Convolution result
      convolutionData[i] = sum;

      // Product result
      productData[i] = filter.getGraphData(resultX) * signal.getGraphData(resultX);
    }

    result.setGraphData(convolutionData);
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
    updateResult();
  });

  var boxFilterButton = document.getElementById("boxFilterButton");
  boxFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawBox(-0.5, 0.5, 1);
    updateResult();
  });

  var triangleFilterButton = document.getElementById("triangleFilterButton");
  triangleFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawTriangle(-1, 1, 1);
    updateResult();
  });

  var gaussianFilterButton = document.getElementById("gaussianFilterButton");
  gaussianFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawGaussian(-4, 4);
    updateResult();
  });

  var sincFilterButton = document.getElementById("sincFilterButton");
  sincFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    filter.drawSinc(-4, 4);
    updateResult();
  });

  var convolveButton = document.getElementById("convolveButton");
  convolveButton.addEventListener("click", function(){
    updateResult();
  });
});
