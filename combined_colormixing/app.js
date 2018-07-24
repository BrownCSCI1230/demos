$(function() {
  PIXI.SCALE_MODES.DEFAULT = PIXI.SCALE_MODES.NEAREST;

  // Label Textures:
  var lightsLabel = PIXI.Texture.fromImage('images/lightsLabel.gif');
  var paintsLabel = PIXI.Texture.fromImage('images/paintsLabel.gif');
  var filtersLabel = PIXI.Texture.fromImage('images/filtersLabel.gif');

  // Light Textures:
  var redLightButton = PIXI.Texture.fromImage('images/rl1.gif');
  var greenLightButton = PIXI.Texture.fromImage('images/gl1.gif');
  var blueLightButton = PIXI.Texture.fromImage('images/bl1.gif');
  var cyanLightButton = PIXI.Texture.fromImage('images/cl1.gif');
  var magentaLightButton = PIXI.Texture.fromImage('images/ml1.gif');
  var yellowLightButton = PIXI.Texture.fromImage('images/yl1.gif');

  // Paint Textures:
  var redPaintButton = PIXI.Texture.fromImage('images/rp1.gif');
  var greenPaintButton = PIXI.Texture.fromImage('images/gp1.gif');
  var bluePaintButton = PIXI.Texture.fromImage('images/bp1.gif');
  var cyanPaintButton = PIXI.Texture.fromImage('images/cp1.gif');
  var magentaPaintButton = PIXI.Texture.fromImage('images/mp1.gif');
  var yellowPaintButton = PIXI.Texture.fromImage('images/yp1.gif');
  var blackPaintButton = PIXI.Texture.fromImage('images/kp1.gif');
  var whitePaintButton = PIXI.Texture.fromImage('images/wp1.gif');

  // Filter Textures:
  var redFilterButton = PIXI.Texture.fromImage('images/rf1.gif');
  var greenFilterButton = PIXI.Texture.fromImage('images/gf1.gif');
  var blueFilterButton = PIXI.Texture.fromImage('images/bf1.gif');
  var cyanFilterButton = PIXI.Texture.fromImage('images/cf1.gif');
  var magentaFilterButton = PIXI.Texture.fromImage('images/mf1.gif');
  var yellowFilterButton = PIXI.Texture.fromImage('images/yf1.gif');

  //Object Textures:
  var gradientEye = PIXI.Texture.fromImage('images/gradientEye.gif');
  var light = PIXI.Texture.fromImage('images/light.gif');
  var lightHalo = PIXI.Texture.fromImage('images/lightHalo.gif');
  var lightOn = PIXI.Texture.fromImage('images/lightFront.gif');
  var surface = PIXI.Texture.fromImage('images/surfaceObj.gif');
  var filterStand = PIXI.Texture.fromImage('images/filterStand.gif');
  var beamButton = PIXI.Texture.fromImage('images/beamButton.gif');

  // Stage:
  PIXI.SCALE_MODES.DEFAULT = PIXI.SCALE_MODES.NEAREST;
  var app = new PIXI.Application(1600, 750, {backgroundColor : 0x000000});
  var renderer = PIXI.autoDetectRenderer(1600, 750, {backgroundColor : 0x000000});
  document.body.appendChild(app.view);

  var stage = new PIXI.Container();

  // CANVAS VALUES:
  var currentColor = 0xFFFFFF;
  var currentMiddleColor = 0xFFFFFF;
  var currentLightColor = 0xFFFFFF;
  var currentPaintColor = 0xFFFFFF;
  var currentFilterColor = 0xFFFFFF;
  var BEAM_BUTTON_X = 1000;
  var BEAM_BUTTON_Y = 25;
  var FRONT_LIGHT_X = 750;
  var FRONT_LIGHT_Y = 25;
  var BACK_LIGHT_X = 800;
  var BACK_LIGHT_Y = 25;
  var SURFACE_X = 400;
  var SURFACE_Y = 200;
  var FILTER_X = SURFACE_X + 250;
  var FILTER_Y = SURFACE_Y + 20;
  var EYE_X = SURFACE_X + 450;
  var EYE_Y = SURFACE_Y + 20;

  var OFFSET_X = 30;
  var OFFSET_Y = 25;

  var allColors = new Map()

  // LIGHT HALO
  var selectedLightHalo = new PIXI.Sprite(lightHalo);
    selectedLightHalo.position.x = FRONT_LIGHT_X - 25;
    selectedLightHalo.position.y = FRONT_LIGHT_Y - 25;
  app.stage.addChild(selectedLightHalo);

  // LIGHT BUTTONS:

  var originalLight = {button: redLightButton, color:"0xff0000", x:50, y:50}
  var currentLight = null
  
  var redLight = {button: redLightButton, color:"0xff0000", x:50, y:50}
  var greenLight = {button: greenLightButton, color:"0x00ff00", x:100, y:50}
  var blueLight = {button: blueLightButton, color:"0x0000ff", x:150, y:50}
  var cyanLight = {button: cyanLightButton, color:"0x00ffff", x:50, y:100}
  var magentaLight = {button: magentaLightButton, color:"0xff00ff", x:100, y:100}
  var yellowLight = {button: yellowLightButton, color:"0xffff00", x:150, y:100}
  var Lights = [redLight, greenLight, blueLight, cyanLight, magentaLight, yellowLight]

  var frontLightSelected = true;
  for (var i = 0; i < Lights.length; i++) {
    var button = new PIXI.Sprite(Lights[i].button);
    button.anchor.set(0.5);
    button.x = Lights[i].x;
    button.y = Lights[i].y;
    button.interactive = true;
    button.buttonMode = true;
    if(frontLightSelected) {
      button.on('pointerdown', addFrontLight);
    } else {
      button.on('pointerdown', addBackLight);
    }
    button.rotate = 0;
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Lights[i].color)
}

// Light label:
    var label = new PIXI.Sprite(lightsLabel);
    label.position.x = Lights[0].x - 50;
    label.position.y = Lights[0].y - 50;
    app.stage.addChild(label);

// Light animation:

  var toRotateLight = true;
  function addFrontLight() {
    this.buttonMode = false;
    frontLightSelected = true;
    if(currentLight != null) {
      returnLight();
    }
    originalLight.x = this.position.x
    originalLight.y = this.position.y
    currentLight = this
    toRotateLight = true;
    requestAnimationFrame(animateLight);

    currentLightColor = allColors.get(this.texture.baseTexture.imageUrl)
    calculateColor();
    colorEyeRectangle();
  }

  function addBackLight() {
    this.buttonMode = false;
    frontLightSelected = false;
    if(currentLight != null) {
      returnLight();
    }
    originalLight.x = this.position.x
    originalLight.y = this.position.y
    currentLight = this
    toRotateLight = true;
    requestAnimationFrame(animateLightBack);

    currentLightColor = allColors.get(this.texture.baseTexture.imageUrl)
    calculateColor();
    colorEyeRectangle();
  }

  function animateLight() {
    requestAnimationFrame(animateLight);
    if (currentLight.position.x <= FRONT_LIGHT_X - 20) {
      currentLight.position.x += 5;
    }
    if (currentLight.position.y >= FRONT_LIGHT_Y + 15) {
      currentLight.position.y -= 0.8;
    }
    if (currentLight.position.x >= (FRONT_LIGHT_X - 20 - 1) && currentLight.position.y >= (FRONT_LIGHT_Y + 15 - 1) && toRotateLight) {
      console.log("woo!");
      currentLight.rotation -= .2
      if(currentLight.rotation <= -2.2) {
        toRotateLight = false;
      }
    }
    if(!toRotateLight) {
      currentLight.rotation = -2.2;
      frontLightObj.texture = lightOn;
    }
    renderer.render(stage);
  }

    function animateLightBack() {
    requestAnimationFrame(animateLightBack);
    if (currentLight.position.x <= BACK_LIGHT_X) {
      currentLight.position.x += 5;
    }
    if (currentLight.position.y >= BACK_LIGHT_Y) {
      currentLight.position.y -= 0.8;
    }
    if (currentLight.position.x >= (BACK_LIGHT_X - 1) && currentLight.position.y >= (BACK_LIGHT_Y - 1) && toRotateLight) {
      console.log("woo!");
      currentLight.rotation -= .2
      if(currentLight.rotation <= -2.2) {
        toRotateLight = false;
      }
    }
    if(!toRotateLight) {
      currentLight.rotation = -2.2;
      backLightObj.texture = lightOn;
    }
    renderer.render(stage);
  }

  function returnLight() {
    currentLight.rotation = 0;
    currentLight.position.x = originalLight.x;
    currentLight.position.y = originalLight.y;
    currentLight.buttonMode = true;
    frontLightObj.texture = light;
    backLightObj.texture = light;
  }

  // PAINT BUTTONS:
  var redPaint = {button: redPaintButton, color:"0xff0000", x:50, y:200}
  var greenPaint = {button: greenPaintButton, color:"0x00ff00", x:100, y:200}
  var bluePaint = {button: bluePaintButton, color:"0x0000ff", x:50, y:250}
  var cyanPaint = {button: cyanPaintButton, color:"0x00ffff", x:100, y:250}
  var magentaPaint = {button: magentaPaintButton, color:"0xff00ff", x:50, y:300}
  var yellowPaint = {button: yellowPaintButton, color:"0xffff00", x:100, y:300}
  var blackPaint = {button: blackPaintButton, color:"0x000000", x:50, y:350}
  var whitePaint = {button: whitePaintButton, color:"0xFFFFFF", x:100, y:350}
  var Paints = [redPaint, greenPaint, bluePaint, cyanPaint, magentaPaint, yellowPaint, blackPaint, whitePaint]

  var originalPaint = {button: whitePaintButton, color:"0xFFFFFF", x:50, y:50}
  var currentPaint = null

  for (var i = 0; i < Paints.length; i++) {
    var button = new PIXI.Sprite(Paints[i].button);
    button.buttonMode = true;
    button.anchor.set(0.5);
    button.x = Paints[i].x;
    button.y = Paints[i].y;
    button.interactive = true;
    button.on('pointerdown', addSurfaceColor);
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Paints[i].color)
  }

    var label = new PIXI.Sprite(paintsLabel);
    label.position.x = Paints[0].x - 50;
    label.position.y = Paints[0].y - 50;
    app.stage.addChild(label);

  // Paint Animation

  var toRotatePaint = true;
  function addSurfaceColor() {
    originalPaint.x = this.position.x
    originalPaint.y = this.position.y
    currentPaint = this
    toRotatePaint = true;
    requestAnimationFrame(animatePaint);
    currentPaintColor = allColors.get(this.texture.baseTexture.imageUrl)
    calculateColor();
    colorEyeRectangle();
  }

  function returnPaint() {
    currentPaint.rotation = 0;
    currentPaint.position.x = originalPaint.x;
    currentPaint.position.y = originalPaint.y;
  }

  function animatePaint() {
    requestAnimationFrame(animatePaint);
    if (currentPaint.position.x <= SURFACE_X + 50) {
      currentPaint.position.x += 5;
    }
    if (currentPaint.position.y >= SURFACE_Y - 25) {
      currentPaint.position.y -= 0.8;
    }
    if (currentPaint.position.x >= (SURFACE_X + 50 - 1) && currentPaint.position.y >= (SURFACE_Y - 25 - 1) && toRotatePaint) {
      currentPaint.rotation -= .2
      if(currentPaint.rotation <= -2.2) {
        toRotatePaint = false;
      }
    }
    if(!toRotatePaint) {
      currentPaint.rotation = -2.2;
      colorSurfaceRectangle(currentPaintColor);
      window.setInterval(function() {
      returnPaint()
      }, 500);
    }
    renderer.render(stage);
  }

  // FILTER BUTTONS:

  var redFilter = {button: redFilterButton, color:"0xff0000", x:50, y:450}
  var greenFilter = {button: greenFilterButton, color:"0x00ff00", x:100, y:450}
  var blueFilter = {button: blueFilterButton, color:"0x0000ff", x:150, y:450}
  var cyanFilter = {button: cyanFilterButton, color:"0x00ffff", x:50, y:500}
  var magentaFilter = {button: magentaFilterButton, color:"0xff00ff", x:100, y:500}
  var yellowFilter = {button: yellowFilterButton, color:"0xffff00", x:150, y:500}
  var Filters = [redFilter, greenFilter, blueFilter, cyanFilter, magentaFilter, yellowFilter]
  var originalFilter = {button: yellowFilter, color:"0xff0000", x:50, y:50}
  var currentFilter = null
  
  for (var i = 0; i < Filters.length; i++) {
    var button = new PIXI.Sprite(Filters[i].button);
    button.buttonMode = true;
    button.anchor.set(0.5);
    button.x = Filters[i].x;
    button.y = Filters[i].y;
    button.interactive = true;
    button.on('pointerdown', addFilter);
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Filters[i].color)
  }

  var label = new PIXI.Sprite(filtersLabel);
  label.position.x = Filters[0].x - 50;
  label.position.y = Filters[0].y - 50;
  app.stage.addChild(label);

  // Animate Filter:
  function addFilter() {
    originalFilter.x = this.position.x
    originalFilter.y = this.position.y
    this.on('pointerdown', returnFilter);
    currentFilter = this
    requestAnimationFrame(animateFilter);
    currentFilterColor = allColors.get(this.texture.baseTexture.imageUrl)
    calculateColor();
    colorEyeRectangle();
  }

  function animateFilter() {
    console.log("animate filter");
    requestAnimationFrame(animateFilter);
    if (currentFilter.position.x <= FILTER_X + 25) {
      currentFilter.position.x += 5;
    }
    if (currentFilter.position.y >= FILTER_Y + 25) {
      currentFilter.position.y -= 2;
    }
    renderer.render(stage);
  }

  function returnFilter() {
    console.log("test")
    currentFilter.position.x = originalFilter.x;
    currentFilter.position.y = originalFilter.y;
    button.on('pointerdown', addFilter);
  }

  // RENDER SCENE:

  renderImage();

  function renderImage()
  {
      requestAnimationFrame(renderImage);
      renderer.render(stage);
  }

  // Objects

  var surfaceObj = new PIXI.Sprite(surface);
    surfaceObj.position.x = SURFACE_X;
    surfaceObj.position.y = SURFACE_Y;

app.stage.addChild(surfaceObj);

var filterStandObj = new PIXI.Sprite(filterStand);
    filterStandObj.position.x = FILTER_X;
    filterStandObj.position.y = FILTER_Y;

app.stage.addChild(filterStandObj);

var eyeObj = new PIXI.Sprite(gradientEye);
    eyeObj.position.x = EYE_X;
    eyeObj.position.y = EYE_Y;

app.stage.addChild(eyeObj);


var frontLightObj = new PIXI.Sprite(light);
    frontLightObj.position.x = FRONT_LIGHT_X;
    frontLightObj.position.y = FRONT_LIGHT_Y;
    frontLightObj.buttonMode = true;
    frontLightObj.anchor.set(0.5);
    // make the button interactive...
    frontLightObj.interactive = true;
    frontLightObj.buttonMode = true;
    frontLightObj.on('pointerdown', selectFrontLight);
app.stage.addChild(frontLightObj);

var backLightObj = new PIXI.Sprite(light);
    backLightObj.position.x = BACK_LIGHT_X;
    backLightObj.position.y = BACK_LIGHT_Y;
    backLightObj.buttonMode = true;
    backLightObj.anchor.set(0.5);
    // make the button interactive...
    backLightObj.interactive = true;
    backLightObj.buttonMode = true;
    backLightObj.on('pointerdown', selectBackLight);

app.stage.addChild(backLightObj);

function selectFrontLight() {
    frontLightSelected = true;
    selectedLightHalo.position.x = FRONT_LIGHT_X - 25;
    selectedLightHalo.position.y = FRONT_LIGHT_Y - 25;
}

function selectBackLight() {
    frontLightSelected = false;
    selectedLightHalo.position.x = BACK_LIGHT_X - 25;
    selectedLightHalo.position.y = BACK_LIGHT_Y - 25;
}

  // BEAM

  var beamButtonObj = new PIXI.Sprite(beamButton);
  beamButtonObj.position.x = BEAM_BUTTON_X;
  beamButtonObj.position.y = BEAM_BUTTON_Y;
  beamButtonObj.anchor.set(0.5);
  beamButtonObj.interactive = true;
  beamButtonObj.buttonMode = true;
  beamButtonObj.on('pointerdown', drawBeam);
  app.stage.addChild(beamButtonObj);

    var beam = new PIXI.Graphics();
    // Animate Beam

    var first = true;
    var second = false;
    var third = false;
    var x = FRONT_LIGHT_X - 15;
    var y = FRONT_LIGHT_Y + 15;
    var beamDrawn = false;
    function drawBeam() {
      if(beamDrawn) {
        clearBeam();
      }
      if(!frontLightSelected) {
        x = BACK_LIGHT_X - 15;
        y = BACK_LIGHT_Y + 15;
      }
      requestAnimationFrame(animateBeam);;
    }

    function animateBeam() {
      if(currentLight != null && currentPaint != null) {
      requestAnimationFrame(animateBeam);
      if(first) {
        beam.lineStyle(5, currentLightColor);
        beam.moveTo(x,y);
        if(x > SURFACE_X + 70) {
          if(frontLightSelected) {
            x = x - 5;
          } else {
            x = x - 6;
          }
        }
        if(y < SURFACE_Y + 50) {
          y = y + 4;
        }
        beam.lineTo(x, y);
        app.stage.addChild(beam);
        if(x <= SURFACE_X + 70 && y >= SURFACE_Y + 50) {
          second = true;
          first = false;
        }
      } else if(second) {
          beam.lineStyle(5, currentMiddleColor);
          beam.moveTo(x,y);
          if(x < FILTER_X + 50) {
            x = x + 5;
          }
          beam.lineTo(x, y);
          app.stage.addChild(beam);
          if(x >= FILTER_X + 50) {
          second = false;
          third = true;
        }
      } else if(third) {
          beam.lineStyle(5, currentColor);
          beam.moveTo(x,y);
          if(x < EYE_X + 25) {
            x = x + 5;
          }
          beam.lineTo(x, y);
          app.stage.addChild(beam);
          if(x >= EYE_X + 25) {
            beamDrawn = true;
          }
      }
      renderer.render(stage);
      }
    }

    function clearBeam() {
      beam.clear();
    }

  // Color Surfaces
var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);

function colorSurfaceRectangle(color) {
    var sur_x = SURFACE_X + 15;
    var sur_y = SURFACE_Y + 12;
    coloredSurface.beginFill(color);
    coloredSurface.lineStyle(4, color, 1);
    coloredSurface.moveTo(sur_x,sur_y);
    coloredSurface.lineTo(sur_x + 40, sur_y);
    coloredSurface.lineTo(sur_x + 75, sur_y + 80);
    coloredSurface.lineTo(sur_x + 45, sur_y + 100);
    coloredSurface.lineTo(sur_x, sur_y);
    coloredSurface.endFill();
    app.stage.addChild(coloredSurface);
}

var colorEye = new PIXI.Graphics();
colorEyeRectangle();

var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);

function colorEyeRectangle() {
  if(currentLight != null && currentPaint != null) {
    var rec_x = EYE_X + 75;
    var rec_y = EYE_Y;
    colorEye.beginFill(currentColor);
    colorEye.lineStyle(4, currentColor, 1);
    colorEye.moveTo(rec_x,rec_y);
    colorEye.lineTo(rec_x + 100,rec_y);
    colorEye.lineTo(rec_x + 100,rec_y + 50);
    colorEye.lineTo(rec_x,rec_y + 50);
    colorEye.lineTo(rec_x,rec_y);
    colorEye.endFill();
    app.stage.addChild(colorEye);
  }
}

function calculateMiddleColor() {
    var rgbLight = PIXI.utils.hex2rgb(currentLightColor);
    var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
    var total = [clamp(rgbLight[0] + rgbPaint[0], 0.0, 1.0), clamp(rgbLight[1] + rgbPaint[1], 0.0, 1.0), clamp(rgbLight[2] + rgbPaint[2],0.0,1.0)];
    console.log(total);
    currentMiddleColor = PIXI.utils.rgb2hex(total); 
}

function calculateColor() {
    if(currentFilter == null) {
      calculateMiddleColor();
      currentColor = currentMiddleColor;
    } else {
      var rgbLight = PIXI.utils.hex2rgb(currentLightColor);
      var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
      var rgbFilter = PIXI.utils.hex2rgb(currentPaintColor);
      var total = [clamp(rgbLight[0] + rgbPaint[0] + rgbFilter[0], 0.0, 1.0), clamp(rgbLight[1] + rgbPaint[1] + rgbFilter[1], 0.0, 1.0), clamp(rgbLight[2] + rgbPaint[2] + rgbFilter[2],0.0,1.0)];
      console.log(total);
      currentColor = PIXI.utils.rgb2hex(total); 
  }
}

function clamp(num, min, max) {
  return num <= min ? min : num >= max ? max : num;
}

function resize() {
    var w = window.innerWidth;
    var h = window.innerHeight
    renderer.resize(w, h);
}
window.onresize = resize;

  });