
$(function() {

  var powerSpectrum = new Graph(document.getElementById("powerSpectrumGraph"), 500, 300, 400, 700, 50, 0, 3, 0.5, "Power Spectrum P(λ)");
  var metamer = new Graph(document.getElementById("metamerGraph"), 500, 300, 400, 700, 50, 0, 3, 0.5, "Metamer H(λ)");

  /* Hacky way to call previous onMove function, and do something else as well */
  powerSpectrum.doUp = powerSpectrum.onUp;

  powerSpectrum.onUp = function(sprite, mousePos) {
    powerSpectrum.doUp(sprite, mousePos);
  }

  // Keep clicks on this graphs from doing anything
  //metamer.onClick = function() {};
  //metamer.onMove = function() {};

});
