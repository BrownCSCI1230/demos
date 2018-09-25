
$(function() {
  var signal = new Graph(document.getElementById("signalGraph"), 400, 200, -8, 8, 1, 0, 1, 0.25, "f(x) [Function to be Convolved]", false);
  var filter = new Graph(document.getElementById("filterGraph"), 400, 200, -8, 8, 1, 0, 1, 0.25, "g(x) [Filter]", false);
  var product = new Graph(document.getElementById("productGraph"), 400, 200, -8, 8, 1, 0, 1, 0.25, "(fg)(x) [Product]", false);
  var result = new Graph(document.getElementById("resultGraph"), 400, 200, -8, 8, 1, 0, 1, 0.25, "(f*g)(x) [Convolution] = sum f(x-t)g(t)", true);

  var currentShift = 0;
  var lastSliderPos = Math.floor(filter.graphData.length / 2);

  /* Convolve signal with filter */
  var updateResult = function(index) {
    var signalValue = signal.graphData[index];
    var filterValue = filter.graphData[index];
    filterValue = filterValue === undefined ? 0 : filterValue;
    product.moveDataPoint(index, signalValue * filterValue);
    product.renderer.render(product.stage);

    var sum = 0;
    for(var resultIndex = 0; resultIndex < result.graphData.length; resultIndex++) {
      var signalValue = signal.graphData[resultIndex];
      var filterValue = filter.graphData[resultIndex];
      filterValue = filterValue === undefined ? 0 : filterValue;

      sum += signalValue * filterValue;
    }

    result.moveDataPoint(Math.floor(filter.graphData.length / 2), sum);
    result.renderer.render(result.stage);
  }

  function updateResultForAll() {

    var sum = 0;
    for(var resultIndex = 0; resultIndex < result.graphData.length; resultIndex++) {
      var signalValue = signal.graphData[resultIndex];
      var filterValue = filter.graphData[resultIndex + filter.totalShift];
      filterValue = filterValue === undefined ? 0 : filterValue;
      product.moveDataPoint(resultIndex, signalValue * filterValue);

      sum += signalValue * filterValue;
    }
    result.moveDataPoint(lastSliderPos, sum);
    result.renderer.render(result.stage);
    product.renderer.render(product.stage);
  }

  $( "#filterSlider" ).slider({
    min: 0,
    max: filter.graphData.length - 1,
    value: Math.floor((filter.graphData.length - 1) / 2),
    animate: "fast",
    slide: sliderDidMove,
    change: sliderDidMove
  });

  function sliderDidMove(eventSlider, uiSlider) {
    filter.shiftEntireLine(lastSliderPos - uiSlider.value);
    filter.renderer.render(filter.stage);
    lastSliderPos = uiSlider.value;
    updateResultForAll();
  }

  /* Hacky way to call previous onMove function, and do something else as well */
  signal.doMove = signal.onMove;
  filter.doMove = filter.onMove;
  // Keep clicks on these graphs from doing anything
  product.onClick = function(){};
  result.onClick = function(){};

  signal.onMove = function(sprite, mousePos) {
    if (signal.isClicking) {
      if (filter.totalShift != 0) {
        result.clearAll();
        $("#filterSlider").slider({
          value: Math.floor(filter.graphData.length / 2),
          change: sliderDidMove
        });
        signal.onUp(sprite, mousePos);
        return;
      }
    }
    var index = signal.doMove(sprite, mousePos);
    if (index !== undefined)
    {
      updateResult(Math.round(index));
    }
  }

  filter.onMove = function(sprite, mousePos) {
    if (filter.isClicking) {
      if (filter.totalShift != 0) {
        result.clearAll();
        $("#filterSlider").slider({
          value: Math.floor(filter.graphData.length / 2),
          change: sliderDidMove
        });
        filter.onUp(sprite, mousePos);
        return;
      }
    }
    var index = filter.doMove(sprite, mousePos);
    if (index !== undefined)
    {
      updateResult(Math.round(index));
    }
  }

  var clearFunctionButton = document.getElementById("clearFunctionButton");
  clearFunctionButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.graphData.length / 2));
    signal.clearAll();
    result.clearAll();
    updateResultForAll();
  });

  var clearFilterButton = document.getElementById("clearFilterButton");
  clearFilterButton.addEventListener("click", function(){
    $("#filterSlider").slider('value', Math.floor(filter.graphData.length / 2));
    filter.clearAll();
    result.clearAll();
    updateResultForAll();
  });

  var normalizeButton = document.getElementById("normalizeButton");
  normalizeButton.addEventListener("click", function(){
    if (filter.totalShift != 0) {
      result.clearAll();
      $("#filterSlider").slider({
        value: Math.floor(filter.graphData.length / 2),
        change: sliderDidMove
      });
    }
    filter.normalizePoints();
  });
});
