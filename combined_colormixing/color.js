// Common

  function calculateMiddleColor() {
    var rgbFrontLight = PIXI.utils.hex2rgb(currentFrontLightColor);
    var rgbBackLight = PIXI.utils.hex2rgb(currentBackLightColor);
    var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
    console.log(rgbFrontLight);
    console.log(rgbBackLight);
    console.log(rgbPaint);
    var r = 0;
    var g = 0;
    var b = 0;
    if(hasBackLight && hasFrontLight) {
    //red
    r = (rgbFrontLight[0] || rgbBackLight[0]) && rgbPaint[0];
    // green
    g = (rgbFrontLight[1] || rgbBackLight[1]) && rgbPaint[1];
    // blue
    b = (rgbFrontLight[2] || rgbBackLight[2]) && rgbPaint[2];
    } else if(hasFrontLight) {
      //red
      r = rgbFrontLight[0] && rgbPaint[0];
      // green
      g = rgbFrontLight[1] && rgbPaint[1];
      // blue
      b = rgbFrontLight[2] && rgbPaint[2];
    } else if(hasBackLight) {
        //red
        r = rgbBackLight[0] && rgbPaint[0];
        // green
        g = rgbBackLight[1] && rgbPaint[1];
        // blue
        b = rgbBackLight[2] && rgbPaint[2];

    }
    var total = [r,g,b];
    currentMiddleColor = PIXI.utils.rgb2hex(total);
    console.log(total); 
}

function calculateColor() {
      calculateMiddleColor();
      var middleColor = PIXI.utils.hex2rgb(currentMiddleColor);
      var rgbFilter = PIXI.utils.hex2rgb(currentFilterColor);
      // red
      var r = middleColor[0] && rgbFilter[0];
      // green
      var g = middleColor[1] && rgbFilter[1];
      // blue
      var b = middleColor[2] && rgbFilter[2];
      var total = [r,g,b];
      currentColor = PIXI.utils.rgb2hex(total); 
}

function clamp(num, min, max) {
  return num <= min ? min : num >= max ? max : num;
}

  PIXI.Sprite.prototype.bringToFront = function() { 
  	if (this.parent) {
  		var parent = this.parent;
  		parent.removeChild(this);
  		parent.addChild(this);  }
  	}