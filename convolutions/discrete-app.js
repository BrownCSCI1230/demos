
$(function() {
  var signal = new Graph(document.getElementById("signalGraph"), 600, 200, -8, 8, 1, 0, 1, 0.25, "f(x) [Function to be Convolved]", false);
  var filter = new Graph(document.getElementById("filterGraph"), 600, 200, -8, 8, 1, 0, 1, 0.25, "g(x) [Filter]", false);
  var product = new Graph(document.getElementById("productGraph"), 600, 200, -8, 8, 1, 0, 1, 0.25, "f(x)g(x) [Product]", false);
  var result = new Graph(document.getElementById("resultGraph"), 600, 200, -8, 8, 1, 0, 1, 0.25, "f(x) * g(x) [Convolution]", true);

  var currentShift = 0;

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
      var filterValue = filter.graphData[resultIndex]; // add current shift
      filterValue = filterValue === undefined ? 0 : filterValue;

      sum += signalValue * filterValue;
    }

    result.moveDataPoint(index, sum);
    result.renderer.render(result.stage);
  }

  $( "#filterSlider" ).slider({
    min: 0,
    max: filter.graphData.length - 1,
    value: Math.floor((filter.graphData.length - 1) / 2),
    animate: "slow",
    slide: sliderDidMove
  });

  var lastSliderPos = Math.floor(filter.graphData.length / 2);

  function sliderDidMove(eventSlider, uiSlider) {
    filter.shiftEntireLine(lastSliderPos - uiSlider.value);
    filter.renderer.render(filter.stage);
    lastSliderPos = uiSlider.value;
  }

  /* Hacky way to call previous onMove function, and do something else as well */
  signal.doMove = signal.onMove;
  filter.doMove = filter.onMove;

  signal.onMove = function(sprite, mousePos) {
    var index = signal.doMove(sprite, mousePos);
    if (index !== undefined)
    {
      updateResult(Math.round(index));
    }
  }

  filter.onMove = function(sprite, mousePos) {
    var index = filter.doMove(sprite, mousePos);
    if (index !== undefined)
    {
      updateResult(Math.round(index));
    }
  }

  var clearFunctionButton = document.getElementById("clearFunctionButton");
  clearFunctionButton.addEventListener("click", function(){
    signal.clearAllBars();
    // updateResult();
  });

  var clearFilterButton = document.getElementById("clearFilterButton");
  clearFilterButton.addEventListener("click", function(){
    filter.clearAllBars();
    // updateResult();
  });

});
