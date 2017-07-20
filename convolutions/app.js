
$(function() {
  var signal = new Graph(document.getElementById("signalGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) [Function to be Convolved]");
  var filter = new Graph(document.getElementById("filterGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "g(x) [Filter]");
  var product = new Graph(document.getElementById("productGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x)g(x) [Product]");
  var result = new Graph(document.getElementById("resultGraph"), 600, 200, -4, 4, 1, -1, 1, 0.5, "f(x) * g(x) [Convolution]");

  /* Convolve signal with filter */
  var updateResult = function() {
    var resultData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);
    var productData = Array.apply(null, Array(result.graphData.length)).map(Number.prototype.valueOf, 0);

    var resultXDiff = result.xmax - result.xmin;
    var productXDiff = product.xmax - product.xmin;
    var filterXDiff = filter.xmax - filter.xmin;

    var resultStep = resultXDiff / result.graphData.length;
    var productStep = productXDiff / product.graphData.length;
    var filterStep = filterXDiff / filter.graphData.length;

    for(var i = 0; i < result.graphData.length; i++) {
      var xProd = i * productStep + product.xmin;
      var xRes = i * resultStep + result.xmin;
      var sum = 0;

      for(var filterX = filter.xmin; filterX <= filter.xmax; filterX += filterStep) {
        var signalX = xRes - filterX;
	      sum += filter.getGraphData(filterX) * signal.getGraphData(signalX) * filterStep;
      }

      resultData[i] = sum;
      productData[i] = signal.getGraphData(xProd) * filter.getGraphData(xRes);
    }

    result.setGraphData(resultData);
    product.setGraphData(productData);
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

});
