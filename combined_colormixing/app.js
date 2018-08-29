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
  var WIDTH =  window.innerWidth;
  var HEIGHT = window.innerHeight;
  var app = new PIXI.Application(WIDTH, HEIGHT, {backgroundColor : 0x000000});
  var renderer = PIXI.autoDetectRenderer(WIDTH, HEIGHT, {backgroundColor : 0x000000});
  document.body.appendChild(app.view);
  
  var stage = new PIXI.Container();

  var SCALE_VALUE = 0.6 * (WIDTH / HEIGHT); 

  // CANVAS VALUES:
  var currentColor = 0xFFFFFF;
  var currentMiddleColor = 0xFFFFFF;
  var currentLightColor = 0xFFFFFF;
  var currentPaintColor = 0xFFFFFF;
  var currentFilterColor = 0xFFFFFF;
  var BEAM_BUTTON_X = WIDTH * 0.9; 
  var BEAM_BUTTON_Y = HEIGHT * 0.05;
  var FRONT_LIGHT_X = WIDTH * 0.8;
  var FRONT_LIGHT_Y = HEIGHT * 0.05;
  var BACK_LIGHT_X = WIDTH * 0.85;
  var BACK_LIGHT_Y = HEIGHT * 0.05;
  var SURFACE_X = WIDTH * 0.3;
  var SURFACE_Y = HEIGHT * 0.4;
  var FILTER_X = WIDTH * 0.5;
  var FILTER_Y = HEIGHT * 0.4;
  var EYE_X = WIDTH * 0.7;
  var EYE_Y = HEIGHT * 0.4;

  var OFFSET_X = 30;
  var OFFSET_Y = 25;

  var allColors = new Map()

  // positions
  var x50 = WIDTH * 0.05;
  var x100 = WIDTH * 0.1;
  var x125 = WIDTH * 0.125;
  var x150 = WIDTH * 0.15;
  var x200 = WIDTH * 0.2;
  var y50 = HEIGHT * 0.09;
  var y100 = HEIGHT * 0.18;
  var y150 = HEIGHT * 0.27;
  var y200 = HEIGHT * 0.36;
  var y250 = HEIGHT * 0.45;
  var y300 = HEIGHT * 0.54;
  var y350 = HEIGHT * 0.63;
  var y400 = HEIGHT * 0.72;
  var y450 = HEIGHT * 0.81;
  var y500 = HEIGHT * 0.90;
  var y550 = HEIGHT * 0.99;

  // LIGHT HALO
  var selectedLightHalo = new PIXI.Sprite(lightHalo);
  selectedLightHalo.scale.set(SCALE_VALUE);
    selectedLightHalo.position.x = FRONT_LIGHT_X - 25;
    selectedLightHalo.position.y = FRONT_LIGHT_Y - 25;
  app.stage.addChild(selectedLightHalo);

  // LIGHT BUTTONS:

  var originalLight = {button: redLightButton, color:"0xff0000", x:50, y:50}
  var currentLight = null
  
  var redLight = {button: redLightButton, color:"0xff0000", x:x50, y:y50}
  var greenLight = {button: greenLightButton, color:"0x00ff00", x:x100, y:y50}
  var blueLight = {button: blueLightButton, color:"0x0000ff", x:x150, y:y50}
  var cyanLight = {button: cyanLightButton, color:"0x00ffff", x:x50, y:y100}
  var magentaLight = {button: magentaLightButton, color:"0xff00ff", x:x100, y:y100}
  var yellowLight = {button: yellowLightButton, color:"0xffff00", x:x150, y:y100}
  var Lights = [redLight, greenLight, blueLight, cyanLight, magentaLight, yellowLight]

  var frontLightSelected = true;
  for (var i = 0; i < Lights.length; i++) {
    var button = new PIXI.Sprite(Lights[i].button);
    button.scale.set(SCALE_VALUE);
    button.anchor.set(0.5);
    button.x = Lights[i].x;
    button.y = Lights[i].y;
    button.interactive = true;
    button.buttonMode = true;
    button.on('pointerdown', addLight);
    button.rotate = 0;
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Lights[i].color)
}

// Light label:
    var lLabel = new PIXI.Sprite(lightsLabel);
    lLabel.scale.set(SCALE_VALUE);
    lLabel.position.x = Lights[0].x - 50;
    lLabel.position.y = Lights[0].y - 50;
    app.stage.addChild(lLabel);

    var lightOutline = new PIXI.Graphics();
    lightOutline.lineStyle(2, 0x000000, 1);
    lightOutline.moveTo(0,0);
    lightOutline.lineTo(x200,0);
    lightOutline.lineTo(x200,y150);
    lightOutline.lineTo(0,y150);
    lightOutline.lineTo(0,0);
    app.stage.addChild(lightOutline);

// Light animation:

  var hasLight = false;
  function addLight() {
    hasLight = true;
    if(currentLight != null) {
      returnLight();
    } 
    originalLight.x = this.position.x
    originalLight.y = this.position.y
    currentLight = this
    this.buttonMode = false;
    if(frontLightSelected) {
      addFrontLight(this);
    } else {
      addBackLight(this);
    }
  }

  var toRotateLight = true;
  var runLightAnimation = false;
  function addFrontLight(light) {
    frontLightSelected = true;
    toRotateLight = true;
    runLightAnimation = true;
    requestAnimationFrame(animateLight);

    currentLightColor = allColors.get(light.texture.baseTexture.imageUrl)
    calculateColor();
  }

  function addBackLight(light) {
    frontLightSelected = false;
    toRotateLight = true;
    runLightAnimation = true;
    requestAnimationFrame(animateLightBack);

    currentLightColor = allColors.get(light.texture.baseTexture.imageUrl)
    calculateColor();
  }

  function animateLight() {
    if(runLightAnimation) {
    requestAnimationFrame(animateLight);
    if (currentLight.position.x <= FRONT_LIGHT_X - 20) {
      currentLight.position.x += 5;
    }
    if (currentLight.position.y >= FRONT_LIGHT_Y + 15) {
      currentLight.position.y -= 0.8;
    }
    if (currentLight.position.x >= (FRONT_LIGHT_X - 20 - 1) && currentLight.position.y >= (FRONT_LIGHT_Y + 15 - 1) && toRotateLight) {
      currentLight.rotation -= .2
      if(currentLight.rotation <= -2.2) {
        toRotateLight = false;
      }
    }
    if(!toRotateLight) {
      currentLight.rotation = -2.2;
      frontLightObj.texture = lightOn;
      runLightAnimation = false;
    }
    renderer.render(stage);
    }
  }

    function animateLightBack() {
    if(runLightAnimation) {
    requestAnimationFrame(animateLightBack);
    if (currentLight.position.x <= BACK_LIGHT_X) {
      currentLight.position.x = 5 + currentLight.position.x ;
    }
    if (currentLight.position.y >= BACK_LIGHT_Y) {
      currentLight.position.y = currentLight.position.y  - 0.8;
    }
    if (currentLight.position.x >= (BACK_LIGHT_X - 1) && currentLight.position.y >= (BACK_LIGHT_Y - 1) && toRotateLight) {
      currentLight.rotation -= .2
      if(currentLight.rotation <= -2.2) {
        toRotateLight = false;
      }
    }
    if(!toRotateLight) {
      currentLight.rotation = -2.2;
      backLightObj.texture = lightOn;
      runLightAnimation = false;
    }
    renderer.render(stage);
  }
  }

  function returnLight() {
    if(!runLightAnimation) {
    currentLight.rotation = 0;
    currentLight.position.x = originalLight.x;
    currentLight.position.y = originalLight.y;
    currentLight.buttonMode = true;
    currentLight = null;
    runLightAnimation = false;
    frontLightObj.texture = light;
    backLightObj.texture = light;
    currentLight = 0xFFFFFF;
    }
  }

  // PAINT BUTTONS:
  var redPaint = {button: redPaintButton, color:"0xff0000", x:x50, y:y200}
  var greenPaint = {button: greenPaintButton, color:"0x00ff00", x:x125, y:y200}
  var bluePaint = {button: bluePaintButton, color:"0x0000ff", x:x50, y:y250}
  var cyanPaint = {button: cyanPaintButton, color:"0x00ffff", x:x125, y:y250}
  var magentaPaint = {button: magentaPaintButton, color:"0xff00ff", x:x50, y:y300}
  var yellowPaint = {button: yellowPaintButton, color:"0xffff00", x:x125, y:y300}
  var blackPaint = {button: blackPaintButton, color:"0x000000", x:x50, y:y350}
  var whitePaint = {button: whitePaintButton, color:"0xFFFFFF", x:x125, y:y350}
  var Paints = [redPaint, greenPaint, bluePaint, cyanPaint, magentaPaint, yellowPaint, blackPaint, whitePaint]

  var originalPaint = {button: whitePaintButton, color:"0xFFFFFF", x:50, y:50}
  var currentPaint = null

  for (var i = 0; i < Paints.length; i++) {
    var button = new PIXI.Sprite(Paints[i].button);
    button.scale.set(SCALE_VALUE);
    button.buttonMode = true;
    button.anchor.set(0.5);
    button.x = Paints[i].x;
    button.y = Paints[i].y;
    button.interactive = true;
    button.on('pointerdown', addSurfaceColor);
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Paints[i].color)
  }

    var pLabel = new PIXI.Sprite(paintsLabel);
    pLabel.scale.set(SCALE_VALUE);
    pLabel.position.x = Paints[0].x - WIDTH * 0.05;
    pLabel.position.y = Paints[0].y - HEIGHT * 0.08;
    app.stage.addChild(pLabel);

    var paintOutline = new PIXI.Graphics();
    paintOutline.lineStyle(2, 0x000000, 1);
    paintOutline.moveTo(0,y150);
    paintOutline.lineTo(x200,y150);
    paintOutline.lineTo(x200,y400);
    paintOutline.lineTo(0,y400);
    paintOutline.lineTo(0,0);
    app.stage.addChild(paintOutline);

  // Paint Animation

  var toRotatePaint = true;
  var paint_x;
  var paint_y;
  var stopPaintAnimation = true;
  var hasPaint = false;
  function addSurfaceColor() {
    hasPaint = true;
    paint_x = this.position.x;
    paint_y = this.position.y;
    currentPaint = this
    toRotatePaint = true;
    stopPaintAnimation = false;
    requestAnimationFrame(animatePaint);
    currentPaintColor = allColors.get(this.texture.baseTexture.imageUrl)
    calculateColor();
    //colorEyeRectangle();
  }

  function returnPaint() {
    stopPaintAnimation = true;
    if(currentPaint != null) {
    currentPaint.position.x = paint_x;
    currentPaint.position.y = paint_y;
    currentPaint.rotation = 0;
    }
    paint_x = null;
    paint_y = null;
    currentPaint = null;
    currentPaintColor = 0xFFFFFF;
    renderer.render(stage);
  }

  function animatePaint() {
    if(!stopPaintAnimation) {
    requestAnimationFrame(animatePaint);
    if (currentPaint.position.x <= SURFACE_X + 50) {
      currentPaint.position.x += 5;
    }
    if (currentPaint.position.y >= SURFACE_Y - 25) {
      currentPaint.position.y -= 0.8;
    }
    if (currentPaint.position.x >= (SURFACE_X + 50 - 1) && currentPaint.position.y >= (SURFACE_Y - 25 - 1) && toRotatePaint) {
      currentPaint.rotation -= .2;
      if(currentPaint.rotation <= -2.2) {
        toRotatePaint = false;
      }
    }
    if(!toRotatePaint) {
      currentPaint.rotation = -2.2;
      window.setInterval(function() {
        if(currentPaint != null) {
          colorSurfaceRectangle(currentPaintColor);
          currentPaint.rotation = 0;
        }
      }, 500);
      window.setInterval(function() {
        if(currentPaint != null) {
        currentPaint.position.x = paint_x;
        currentPaint.position.y = paint_y;
        }
        stopPaintAnimation = true;
        returnPaint();
      }, 500);
      toRotatePaint = true;
    }
    renderer.render(stage);
  } else {
    returnPaint();
  }
  }

  // FILTER BUTTONS:

  var redFilter = {button: redFilterButton, color:"0xff0000", x:x50, y:y450}
  var greenFilter = {button: greenFilterButton, color:"0x00ff00", x:x100, y:y450}
  var blueFilter = {button: blueFilterButton, color:"0x0000ff", x:x150, y:y450}
  var cyanFilter = {button: cyanFilterButton, color:"0x00ffff", x:x50, y:y500}
  var magentaFilter = {button: magentaFilterButton, color:"0xff00ff", x:x100, y:y500}
  var yellowFilter = {button: yellowFilterButton, color:"0xffff00", x:x150, y:y500}
  var Filters = [redFilter, greenFilter, blueFilter, cyanFilter, magentaFilter, yellowFilter]
  var originalFilter = {button: yellowFilter, color:"0xff0000", x:50, y:50}
  var currentFilter = null;
  
  for (var i = 0; i < Filters.length; i++) {
    var button = new PIXI.Sprite(Filters[i].button);
    button.scale.set(SCALE_VALUE);
    button.buttonMode = true;
    button.anchor.set(0.5);
    button.x = Filters[i].x;
    button.y = Filters[i].y;
    button.interactive = true;
    button.on('pointerdown', addFilter);
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Filters[i].color)
  }

  var fLabel = new PIXI.Sprite(filtersLabel);
    fLabel.scale.set(SCALE_VALUE);
    fLabel.position.x = Filters[0].x - 50;
    fLabel.position.y = Filters[0].y - 50;
    app.stage.addChild(fLabel);

    var filterOutline = new PIXI.Graphics();
    filterOutline.lineStyle(2, 0x000000, 1);
    filterOutline.moveTo(0,y150);
    filterOutline.lineTo(x200,y150);
    filterOutline.lineTo(x200,y550);
    filterOutline.lineTo(0,y550);
    filterOutline.lineTo(0,0);
    app.stage.addChild(filterOutline);

  // Animate Filter:
  var stopFilterAnimation = true;
  var return_x;
  var return_y;
  function addFilter() {
    if(currentFilter != null) {
      returnFilter();
    }
    stopFilterAnimation = false;
    if(return_x == null || return_y == null) {
      return_x = this.position.x;
      return_y = this.position.y;
    }
    currentFilter = this;
    //this.on('pointerdown', returnFilter);
    requestAnimationFrame(animateFilter);
    currentFilterColor = allColors.get(this.texture.baseTexture.imageUrl)
    console.log(currentFilterColor);
    calculateColor();
  }

  function animateFilter() {
    if(!stopFilterAnimation) {
      console.log("animate filter");
    requestAnimationFrame(animateFilter);
    if (currentFilter.position.x <= FILTER_X + WIDTH * 0.03) {
      currentFilter.position.x += 5;
    }
    if (currentFilter.position.y >= FILTER_Y + HEIGHT * 0.05) {
      currentFilter.position.y -= 2;
    }
    if(currentFilter.position.x >= FILTER_X + WIDTH * 0.03 && currentFilter.position.y <= FILTER_Y + HEIGHT * 0.05) {
      stopFilterAnimation = true;
    }
    renderer.render(stage);
    }
  }

  function returnFilter() {
    if(currentFilter != null) {
      currentFilter.position.x = return_x;
      currentFilter.position.y = return_y;
    }
    return_x = null;
    return_y = null;
    currentFilter = null;
    currentFilterColor = 0xFFFFFF;
    //button.on('pointerdown', addFilter);
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
    surfaceObj.scale.set(SCALE_VALUE);

app.stage.addChild(surfaceObj);

var filterStandObj = new PIXI.Sprite(filterStand);
    filterStandObj.position.x = FILTER_X;
    filterStandObj.position.y = FILTER_Y;
    filterStandObj.scale.set(SCALE_VALUE);

app.stage.addChild(filterStandObj);

var eyeObj = new PIXI.Sprite(gradientEye);
  eyeObj.scale.set(SCALE_VALUE);
    eyeObj.position.x = EYE_X;
    eyeObj.position.y = EYE_Y;

app.stage.addChild(eyeObj);


var frontLightObj = new PIXI.Sprite(light);
  frontLightObj.scale.set(SCALE_VALUE);
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
backLightObj.scale.set(SCALE_VALUE);
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
  beamButtonObj.scale.set(SCALE_VALUE);
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
      } else {
        x = FRONT_LIGHT_X - 15;
        y = FRONT_LIGHT_Y + 15;
      }
      requestAnimationFrame(animateBeam);;
    }

    function animateBeam() {
      if(!beamDrawn) {
      if(hasLight && hasPaint) {
      requestAnimationFrame(animateBeam);
      if(first) {
        beam.lineStyle(5, currentLightColor);
        beam.moveTo(x,y);
        if(x > SURFACE_X + 70) {
          if(frontLightSelected) {
            x = x - WIDTH * 0.005;
          } else {
            x = x - WIDTH * 0.006;
          }
        }
        if(y < SURFACE_Y + 50) {
          //y = y + 2.2;
          y = y + HEIGHT * 0.005;
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
            colorEyeRectangle();
            beamDrawn = true;
            third = false;
            first = true;
            window.setInterval(function() {
              clearBeam();
      }, 1000);
          }
      }
      renderer.render(stage);
      }
    }
    }

    function clearBeam() {
      beam.clear();
      beamDrawn = false;
    }

  // Color Surfaces
var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);

function colorSurfaceRectangle(color) {
    var sur_x = SURFACE_X + WIDTH * 0.01;
    var sur_y = SURFACE_Y + HEIGHT * 0.02;
    coloredSurface.beginFill(color);
    coloredSurface.lineStyle(2, 0x000000, 1);
    coloredSurface.moveTo(sur_x,sur_y);
    coloredSurface.lineTo(sur_x + WIDTH * 0.04, sur_y);
    coloredSurface.lineTo(sur_x + WIDTH * 0.08, sur_y + HEIGHT * 0.14);
    coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y + HEIGHT * 0.21);
    coloredSurface.lineTo(sur_x, sur_y);
    coloredSurface.endFill()
    app.stage.addChild(coloredSurface);
}

var colorEye = new PIXI.Graphics();

var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);
colorEyeRectangle();

function colorEyeRectangle() {
    var rec_x = EYE_X + WIDTH * 0.075;
    var rec_y = EYE_Y;
    colorEye.beginFill(currentColor);
    colorEye.lineStyle(2, 0x000000, 1);
    colorEye.moveTo(rec_x,rec_y);
    colorEye.lineTo(rec_x + WIDTH * 0.08,rec_y);
    colorEye.lineTo(rec_x + WIDTH * 0.08,rec_y + HEIGHT * 0.08);
    colorEye.lineTo(rec_x,rec_y + HEIGHT * 0.08);
    colorEye.lineTo(rec_x,rec_y);
    colorEye.endFill();
    app.stage.addChild(colorEye);
}

function calculateMiddleColor() {
    var rgbLight = PIXI.utils.hex2rgb(currentLightColor);
    var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
    //red
    var r = rgbLight[0];
    if(r == 1) {
      r = rgbPaint[0];
    }
    // green
    var g = rgbLight[1];
    if(g == 1) {
      g = rgbPaint[1];
    }
    // blue
    var b = rgbLight[2];
    if(b == 1) {
      b = rgbPaint[2];
    }
    //var total = [clamp(rgbLight[0] + rgbPaint[0], 0.0, 1.0), clamp(rgbLight[1] + rgbPaint[1], 0.0, 1.0), clamp(rgbLight[2] + rgbPaint[2],0.0,1.0)];
    var total = [r,g,b];
    currentMiddleColor = PIXI.utils.rgb2hex(total); 
}

function calculateColor() {
      calculateMiddleColor();
      var rgbLight = PIXI.utils.hex2rgb(currentLightColor);
      var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
      var rgbFilter = PIXI.utils.hex2rgb(currentFilterColor);
      console.log(rgbFilter);
      var r = rgbLight[0];
      if(r == 1) {
        r = rgbPaint[0];
        if(r == 1) {
          r = rgbFilter[0];
        }
      }
      // green
      var g = rgbLight[1];
      if(g == 1) {
        g = rgbPaint[1];
        if(g == 1) {
          g = rgbFilter[1];
        }
      }
      // blue
      var b = rgbLight[2];
      if(b == 1) {
        b = rgbPaint[2];
        if(b == 1) {
          b = rgbFilter[2];
        }
      }
      var total = [r,g,b];
      currentColor = PIXI.utils.rgb2hex(total); 
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