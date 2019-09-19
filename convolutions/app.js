
$(function() {
  var signal = new Graph(document.getElementById("signalGraph"), 400, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Function to be Convolved]");
  var filter = new Graph(document.getElementById("filterGraph"), 400, 200, -4, 4, 1, -1, 1, 0.5, "g(x) [Filter]");
  var product = new Graph(document.getElementById("productGraph"), 400, 200, -4, 4, 1, -1, 1, 0.5, "(fg)(x) [Product]");
  var result = new Graph(document.getElementById("resultGraph"), 400, 200, -4, 4, 1, -1, 1, 0.5, "(f*g)(x) [Convolution] = integral f(x-t)g(t)");

  var lastSliderPos = Math.floor(filter.drawingIndices.length / 2);

  /* Convolve signal with filter */
  var updateResult = function() {
    var convolutionData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);
    var productData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);

    var resultXDiff = result.xmax - result.xmin;
    var filterXDiff = filter.xmax - filter.xmin;

    var resultStep = resultXDiff / result.drawingIndices.length;
    var filterStep = filterXDiff / filter.drawingIndices.length;

    // Iterate over all x values in result
    for(var resultX = result.xmin; resultX <= result.xmax; resultX += resultStep) {
      var sum = 0;

      // Iterate over all visible x values in filter
      for(var filterX = filter.xmin; filterX <= filter.xmax; filterX += filterStep) {
      	// Product of values of the filter at (filterX) and the original signal at
      	// (resultX - filterX) will contribute to value of result signal at resultX
      	var signalX = resultX - filterX;

      	// Accumulate result of convolution at result x
      	sum += filter.getGraphData(filterX) * signal.getGraphData(signalX) * filterStep;
      }

      // Convolution result
      var i = result.convertToPixel(resultX, true) + Math.floor(result.drawingIndices.length / 2);
      convolutionData[i + result.drawingIndices.length / 2] = sum;

      // Product result
      productData[i + result.drawingIndices.length / 2] = filter.getGraphData(resultX) * signal.getGraphData(resultX);
    }

    result.setGraphData(convolutionData);
    product.setGraphData(productData);
  }

  // This function could be seriously cleaned up / combined with the previous
  var updateResultForValue = function(value) {
    var convolutionData = result.graphData;
    var productData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);

    var productXDiff = product.xmax - product.xmin;
    var filterXDiff = filter.xmax - filter.xmin;

    var productStep = productXDiff / product.drawingIndices.length;
    var filterStep = filterXDiff / filter.drawingIndices.length;

    // Iterate over all x values in product
    for(var productX = product.xmin; productX <= product.xmax; productX += productStep) {
      // Product result
      var i = value - product.convertToPixel(productX, true) + Math.floor(product.drawingIndices.length / 2);
      productData[i + result.drawingIndices.length] = filter.getGraphData(productX) * signal.getGraphData(productX);
    }
    product.setGraphData(productData);

    var start = Math.min(lastSliderPos, value);
    var end = Math.max(lastSliderPos, value);

    var resultXDiff = result.xmax - result.xmin;
    var resultStep = resultXDiff / result.drawingIndices.length;

    var startX = result.xmin

    for (var i = start; i <= end; i++) {
      var resultX = i * resultStep + result.xmin;
      var shift = (value - i) * filterStep;
      var sum = 0;

      for(var filterX = filter.xmin; filterX <= filter.xmax; filterX += filterStep) {
        sum += filter.getGraphData(filterX + shift) * signal.getGraphData(filterX) * filterStep;
      }

      convolutionData[i + result.drawingIndices.length] = sum;
    }

    start += result.drawingIndices.length;
    end += result.drawingIndices.length;
    result.setGraphDataAtIndices(convolutionData, start, end);
  }

  $( "#filterSlider" ).slider({
    min: 0,
    max: filter.drawingIndices.length,
    value: filter.drawingIndices.length / 2,
    animate: "slow",
    slide: sliderDidMove,
    change: sliderDidMove
  });

  function sliderDidMove(eventSlider, uiSlider) {
    filter.shiftEntireLine(lastSliderPos - uiSlider.value);
    updateResultForValue(uiSlider.value);
    lastSliderPos = uiSlider.value;
  }

  /* Hacky way to call previous onMove function, and do something else as well */
  signal.doMove = signal.onMove;
  filter.doMove = filter.onMove;
  // Keep clicks on these graphs from doing anything
  product.onClick = function() {};
  result.onClick = function() {};
  product.onMove = function() {};
  result.onMove = function() {};

  signal.onMove = function(sprite, mousePos) {
    if (signal.isClicking) {
      if (filter.totalShift != 0) {
        signal.clearAllBars();
        result.clearAllBars();
        $("#filterSlider").slider({
          value: Math.floor(filter.drawingIndices.length / 2)
        });
        signal.onUp(sprite, mousePos);
        return;
      }
    }
    signal.doMove(sprite, mousePos);
  }

  filter.onMove = function(sprite, mousePos) {
    if (filter.isClicking) {
      if (filter.totalShift != 0) {
        filter.clearAllBars();
        result.clearAllBars();
        $("#filterSlider").slider({
          value: Math.floor(filter.drawingIndices.length / 2)
        });
        filter.onUp(sprite, mousePos);
        return;
      }
    }
    filter.doMove(sprite, mousePos);
  }

  var clearFunctionButton = document.getElementById("clearFunctionButton");
  clearFunctionButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    signal.clearAllBars();
    updateResult();
  });

  var clearFilterButton = document.getElementById("clearFilterButton");
  clearFilterButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    filter.clearAllBars();
    updateResult();
  });

  var boxFunctionButton = document.getElementById("boxFunctionButton");
  boxFunctionButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    signal.clearAllBars();
    result.clearAllBars();
    signal.drawBox(-0.5, 0.5, 1);
  });

  var boxFilterButton = document.getElementById("boxFilterButton");
  boxFilterButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    filter.clearAllBars();
    result.clearAllBars();
    filter.drawBox(-0.5, 0.5, 1);
  });

  var triangleFilterButton = document.getElementById("triangleFilterButton");
  triangleFilterButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    filter.clearAllBars();
    result.clearAllBars();
    filter.drawTriangle(-1, 1, 1);
  });

  var gaussianFilterButton = document.getElementById("gaussianFilterButton");
  gaussianFilterButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    filter.clearAllBars();
    result.clearAllBars();
    filter.drawGaussian(-4, 4);
  });

  var sincFilterButton = document.getElementById("sincFilterButton");
  sincFilterButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    filter.clearAllBars();
    result.clearAllBars();
    filter.drawSinc(-4, 4);
  });

  var convolveButton = document.getElementById("convolveButton");
  convolveButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.drawingIndices.length / 2));
    updateResult();
  });
});
