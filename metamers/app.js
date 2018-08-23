
$(function() {

  var powerSpectrum = new Graph(document.getElementById("powerSpectrumGraph"), 500, 300, 400, 700, 50, 0, 1, 0.25, "Power Spectrum P(λ)");
  var metamer = new Graph(document.getElementById("metamerGraph"), 500, 300, 400, 700, 50, 0, 1, 0.25, "Metamer H(λ)");
  var colorbarPrimary = new ColorBar(document.getElementById("colorbarPrimary"), 300, 200, "RGB response for P(λ)");
  var colorbarSecondary = new ColorBar(document.getElementById("colorbarSecondary"), 300, 200, "RGB response for H(λ)");

  /* Hacky way to call previous onMove function, and do something else as well */
  powerSpectrum.doMove = powerSpectrum.onMove;

  powerSpectrum.onMove = function(sprite, mousePos) {
    powerSpectrum.doMove(sprite, mousePos);

    var data = powerSpectrum.getGraphDataRange(400, 700, 1);

    var R = cmf_response(cmf_red, data);
    var G = cmf_response(cmf_green, data);
    var B = cmf_response(cmf_blue, data);

    colorbarPrimary.setBarAt(0, 0xff0000, R);
    colorbarPrimary.setBarAt(1, 0x00ff00, G);
    colorbarPrimary.setBarAt(2, 0x0000ff, B);
  }

  metamer.doMove = metamer.onMove;

  metamer.onMove = function(sprite, mousePos) {
    metamer.doMove(sprite, mousePos);

    var data = metamer.getGraphDataRange(400, 700, 1);

    var R = cmf_response(cmf_red, data);
    var G = cmf_response(cmf_green, data);
    var B = cmf_response(cmf_blue, data);

    colorbarSecondary.setBarAt(0, 0xff0000, R);
    colorbarSecondary.setBarAt(1, 0x00ff00, G);
    colorbarSecondary.setBarAt(2, 0x0000ff, B);
  }

});


