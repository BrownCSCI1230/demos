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
  if(WIDTH < 500) {
    WIDTH = 500;
  }
  if(HEIGHT < 550) {
    HEIGHT = 550;
  }
  var app = new PIXI.Application(WIDTH, HEIGHT, {backgroundColor : 0x000000});
  var renderer = PIXI.autoDetectRenderer(WIDTH, HEIGHT, {backgroundColor : 0x000000});
  document.body.appendChild(app.view);
  
  var stage = new PIXI.Container();

  var SCALE_VALUE = 0.6 * (WIDTH / HEIGHT); 

  // CANVAS VALUES:
  var currentColor = 0xd3d3d3;
  var currentMiddleColor = 0xFFFFFF;
  var currentFrontLightColor = 0xFFFFFF;
  var currentBackLightColor = 0xFFFFFF;
  var currentPaintColor = 0xFFFFFF;
  var currentFilterColor = 0xFFFFFF;
  var BEAM_BUTTON_X = WIDTH * 0.92; 
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

  var x25 = WIDTH * 0.025;
  var y30 = HEIGHT * 0.05;

  // OBJECTS

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


  // LIGHT HALO
  var selectedLightHalo = new PIXI.Sprite(lightHalo);
  selectedLightHalo.scale.set(SCALE_VALUE);
    selectedLightHalo.position.x = FRONT_LIGHT_X - x25;
    selectedLightHalo.position.y = FRONT_LIGHT_Y - y30;
  app.stage.addChild(selectedLightHalo);

  // LIGHT BUTTONS:

  var originalFrontLight = {button: redLightButton, color:"0xff0000", x:50, y:50};
  var originalBackLight = {button: redLightButton, color:"0xff0000", x:50, y:50};
  var currentFrontLight = null;
  var currentBackLight = null;
  
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
    lLabel.position.x = Lights[0].x - WIDTH * 0.05;
    lLabel.position.y = Lights[0].y - HEIGHT * 0.08;
    app.stage.addChild(lLabel);

    var lightOutline = new PIXI.Graphics();
    lightOutline.lineStyle(2, 0xFFFFFF, 1);
    lightOutline.moveTo(1,1);
    lightOutline.lineTo(x200,1);
    lightOutline.lineTo(x200,y150 - 1);
    lightOutline.lineTo(1,y150 - 1);
    lightOutline.lineTo(1,1);
    app.stage.addChild(lightOutline);

// Light animation:

  var hasLight = false;
  var hasFrontLight = false;
  var hasBackLight = false;
  function addLight() {
    hasLight = true;
    this.buttonMode = false;
    if(frontLightSelected) {
      if(currentFrontLight != null) {
        returnFrontLight();
      } 
      originalFrontLight.x = this.position.x
      originalFrontLight.y = this.position.y
      currentFrontLight = this
      hasFrontLight = true;
      addFrontLight(this);
    } else {
      if(currentBackLight != null) {
      returnBackLight();
      } 
      originalBackLight.x = this.position.x
      originalBackLight.y = this.position.y
      currentBackLight = this
      hasBackLight = true;
      addBackLight(this);
    }
  }

  var toRotateFrontLight = true;
  var toRotateBackLight = true;
  var runFrontLightAnimation = false;
  var runBackLightAnimation = false;
  function addFrontLight(light) {
    frontLightSelected = true;
    toRotateFrontLight = true;
    runFrontLightAnimation = true;
    requestAnimationFrame(animateFrontLight);

    currentFrontLightColor = allColors.get(light.texture.baseTexture.imageUrl)
    calculateColor();
  }

  function addBackLight(light) {
    frontLightSelected = false;
    toRotateBackLight = true;
    runBackLightAnimation = true;
    requestAnimationFrame(animateBackLight);

    currentBackLightColor = allColors.get(light.texture.baseTexture.imageUrl)
    calculateColor();
  }

  var x20 = (WIDTH * 0.015);
  var y15 = (HEIGHT * 0.025);

  function animateFrontLight() {
    if(runFrontLightAnimation) {
    requestAnimationFrame(animateFrontLight);
    if (currentFrontLight.position.x <= FRONT_LIGHT_X - x20) { 
      currentFrontLight.position.x += 5;
    }
    if (currentFrontLight.position.y >= FRONT_LIGHT_Y + y15) { 
      currentFrontLight.position.y -= 0.8;
    }
    if (currentFrontLight.position.x >= (FRONT_LIGHT_X - x20 - 1) && currentFrontLight.position.y >= (FRONT_LIGHT_Y + y15 - 1) && toRotateFrontLight) {
      currentFrontLight.rotation -= .2
      if(currentFrontLight.rotation <= -2.2) {
        toRotateFrontLight = false;
      }
    }
    if(!toRotateFrontLight) {
      currentFrontLight.rotation = -2.2;
      frontLightObj.texture = lightOn;
      runFrontLightAnimation = false;
    }
    renderer.render(stage);
    }
  }

    function animateBackLight() {
    if(runBackLightAnimation) {
    requestAnimationFrame(animateBackLight);
    if (currentBackLight.position.x <= BACK_LIGHT_X - x20) {
      currentBackLight.position.x = 5 + currentBackLight.position.x ;
    }
    if (currentBackLight.position.y >= BACK_LIGHT_Y + y15) {
      currentBackLight.position.y = currentBackLight.position.y  - 0.8;
    }
    if (currentBackLight.position.x >= (BACK_LIGHT_X - x20 - 1) && currentBackLight.position.y >= (BACK_LIGHT_Y + y15 - 1) && toRotateBackLight) {
      currentBackLight.rotation -= .2
      if(currentBackLight.rotation <= -2.2) {
        toRotateBackLight = false;
      }
    }
    if(!toRotateBackLight) {
      currentBackLight.rotation = -2.2;
      backLightObj.texture = lightOn;
      runBackLightAnimation = false;
    }
    renderer.render(stage);
  }
  }

  function returnFrontLight() {
    if(!runFrontLightAnimation) {
      hasFrontLight = false;
      currentFrontLight.rotation = 0;
      currentFrontLight.position.x = originalFrontLight.x;
      currentFrontLight.position.y = originalFrontLight.y;
      currentFrontLight.buttonMode = true;
      currentFrontLight = null;
      runFrontLightAnimation = true;
      frontLightObj.texture = light;
      backLightObj.texture = light;
      currentFrontLight = 0xFFFFFF;
    }
  }

    function returnBackLight() {
    if(!runBackLightAnimation) {
      hasBackLight = false;
    currentBackLight.rotation = 0;
    currentBackLight.position.x = originalBackLight.x;
    currentBackLight.position.y = originalBackLight.y;
    currentBackLight.buttonMode = true;
    currentBackLight = null;
    frontLightObj.texture = light;
    backLightObj.texture = light;
    currentBackLight = 0xFFFFFF;
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
    button.on('pointerdown', addPaintColor);
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Paints[i].color)
  }

    var pLabel = new PIXI.Sprite(paintsLabel);
    pLabel.scale.set(SCALE_VALUE);
    pLabel.position.x = Paints[0].x - WIDTH * 0.05;
    pLabel.position.y = Paints[0].y - HEIGHT * 0.08;
    app.stage.addChild(pLabel);

    var paintOutline = new PIXI.Graphics();
    paintOutline.lineStyle(2, 0xFFFFFF, 1);
    paintOutline.moveTo(1,y150);
    paintOutline.lineTo(x200,y150);
    paintOutline.lineTo(x200,y400);
    paintOutline.lineTo(1,y400);
    paintOutline.lineTo(1,1);
    app.stage.addChild(paintOutline);

  // Paint Animation

  var toRotatePaint = true;
  var paint_x;
  var paint_y;
  var stopPaintAnimation = true;
  function addPaintColor() {
    this.bringToFront();
    paint_x = this.position.x;
    paint_y = this.position.y;
    currentPaintColor = 0xFFFFFF; //reset paint color
    currentPaint = this
    toRotatePaint = true;
    stopPaintAnimation = false;
    requestAnimationFrame(animatePaint);
    currentPaintColor = allColors.get(this.texture.baseTexture.imageUrl)
    calculateColor();
    //colorEyeRectangle();
  }

  function returnPaint() {
    if(currentPaint != null) {
    currentPaint.position.x = paint_x;
    currentPaint.position.y = paint_y;
    currentPaint.rotation = 0;
    }
    paint_x = null;
    paint_y = null;
    currentPaint = null;
    renderer.render(stage);
  }

  function animatePaint() {
    if(!stopPaintAnimation) {
    requestAnimationFrame(animatePaint);
    if (currentPaint.position.x <= SURFACE_X + x25) {
      currentPaint.position.x += 3;
    }
    if(paint_y <= SURFACE_Y + 2 * y30) {
    if(currentPaint.position.y <= SURFACE_Y + 2 * y30) {
        currentPaint.position.y -= 0.8;
      } 
    } else {
        currentPaint.position.y -= 1.6;
    }
    if (currentPaint.position.x >= (SURFACE_X + (x25) - 1) && currentPaint.position.y <= (SURFACE_Y + 2 * y30 - 1) && toRotatePaint) {
      currentPaint.rotation -= .2;
      if(currentPaint.rotation <= -2.2) {
        toRotatePaint = false;
      }
    }
    if(!toRotatePaint) {
      currentPaint.rotation = -2.2;
      window.setInterval(function() {
        if(currentPaint != null && !toRotatePaint) {
          colorSurfaceRectangle(currentPaintColor);
          currentPaint.rotation = 0;
        }
      }, 500);
      window.setInterval(function() {
        if(!toRotatePaint) {
        if(currentPaint != null) {
        currentPaint.position.x = paint_x;
        currentPaint.position.y = paint_y;
        }
        stopPaintAnimation = true;
        returnPaint();
      }
      }, 500);
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
    fLabel.position.x = Filters[0].x - WIDTH * 0.05;
    fLabel.position.y = Filters[0].y - HEIGHT * 0.08;
    app.stage.addChild(fLabel);

    var filterOutline = new PIXI.Graphics();
    filterOutline.lineStyle(2, 0xFFFFFF, 1);
    filterOutline.moveTo(1,y150);
    filterOutline.lineTo(x200,y150);
    filterOutline.lineTo(x200,y550);
    filterOutline.lineTo(1,y550);
    filterOutline.lineTo(1,y150);
    app.stage.addChild(filterOutline);

  // Animate Filter:
  var stopFilterAnimation = true;
  var return_x;
  var return_y;
  function addFilter() {
    if(currentFilter != null) {
      returnFilter();
    }
    this.bringToFront();
    stopFilterAnimation = false;
    if(return_x == null || return_y == null) {
      return_x = this.position.x;
      return_y = this.position.y;
    }
    currentFilter = this;
    this.on('pointerdown', returnFilter);
    requestAnimationFrame(animateFilter);
    currentFilterColor = allColors.get(this.texture.baseTexture.imageUrl)
    calculateColor();
  }

  function animateFilter() {
    if(!stopFilterAnimation && currentFilter != null) {
    requestAnimationFrame(animateFilter);
    if (currentFilter.position.x <= FILTER_X + WIDTH * 0.03) {
      currentFilter.position.x += 5;
    }
    if (currentFilter.position.y >= FILTER_Y + y30) {
      currentFilter.position.y -= 4;
    }
    if(currentFilter.position.x >= FILTER_X + WIDTH * 0.03 && currentFilter.position.y <= FILTER_Y + y30) {
      stopFilterAnimation = true;
    }
    renderer.render(stage);
    }
  }

  function returnFilter() {
    if(currentFilter != null) {
      currentFilter.position.x = return_x;
      currentFilter.position.y = return_y;
      currentFilter.on('pointerdown', addFilter);
    }
    return_x = null;
    return_y = null;
    currentFilter = null;
    currentFilterColor = 0xFFFFFF;
    calculateColor();
  }

  // RENDER SCENE:

  renderImage();

  function renderImage()
  {
      requestAnimationFrame(renderImage);
      renderer.render(stage);
  }

var frontLightObj = new PIXI.Sprite(light);
  frontLightObj.scale.set(SCALE_VALUE);
    frontLightObj.position.x = FRONT_LIGHT_X;
    frontLightObj.position.y = FRONT_LIGHT_Y;
    frontLightObj.buttonMode = true;
    frontLightObj.anchor.set(0.5);
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
    backLightObj.interactive = true;
    backLightObj.buttonMode = true;
    backLightObj.on('pointerdown', selectBackLight);

app.stage.addChild(backLightObj);

function selectFrontLight() {
    if(frontLightSelected) {
      // return light
      returnFrontLight();
    } else {
      frontLightSelected = true;
      selectedLightHalo.position.x = FRONT_LIGHT_X - x25;
      selectedLightHalo.position.y = FRONT_LIGHT_Y - y30;
  }
}

function selectBackLight() {
    if(!frontLightSelected) {
      returnBackLight();
    } else {
      frontLightSelected = false;
      selectedLightHalo.position.x = BACK_LIGHT_X - WIDTH * 0.022;
      selectedLightHalo.position.y = 0;
  }
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

    var frontBeam = new PIXI.Graphics();
    var backBeam = new PIXI.Graphics();
    var mergedBeam = new PIXI.Graphics();

    // Animate Beam

    var first = true;
    var second = false;
    var third = false;
    var front_x = FRONT_LIGHT_X - 15;
    var front_y = FRONT_LIGHT_Y + 15;
    var back_x = BACK_LIGHT_X - 15;
    var back_y = BACK_LIGHT_Y + 15
    var beamDrawn = false;
    function drawBeam() {
      if(beamDrawn) {
        clearBeam();
      }
      requestAnimationFrame(animateBeam);
    }

    var x = SURFACE_X + WIDTH * 0.05;
    var y = SURFACE_Y + HEIGHT * 0.08;
    // HEIGHT fix
    if(WIDTH <= 800) {
      y = SURFACE_Y + HEIGHT * 0.04;
    }

    function animateBeam() {
      if(!beamDrawn) {
      if(hasLight) {
      requestAnimationFrame(animateBeam);
      if(first) {
      if(hasFrontLight) {
        frontBeam.lineStyle(5, currentFrontLightColor);
        frontBeam.moveTo(front_x,front_y);
        if(front_x > x) {
          console.log("x");
          front_x = front_x - 0.01 * (FRONT_LIGHT_X - x); // WIDTH * 0.005
        } 
        if(front_y < y) {
          console.log("y");
          front_y = front_y - 0.01 * (FRONT_LIGHT_Y - y); // HEIGHT * 0.005
        }
        frontBeam.lineTo(front_x, front_y);
        app.stage.addChild(frontBeam);
        if(front_x <= x && front_y >= y) {
          console.log("done!");
          second = true;
          first = false;
        }
      }
      if(hasBackLight) {
        backBeam.lineStyle(5, currentBackLightColor);
        backBeam.moveTo(back_x,back_y);
        if(back_x > x) {
          back_x = back_x - 0.01 * (BACK_LIGHT_X - x); //WIDTH * 0.006 
        }
        if(back_y < y) {
          back_y = back_y - 0.01 * (BACK_LIGHT_Y - y); // HEIGHT * 0.005
        }
        backBeam.lineTo(back_x, back_y);
        app.stage.addChild(backBeam);
         if(back_x <= x && back_y >= y) {
          second = true;
          first = false;
        }
      }
    } else if(second) {
          mergedBeam.lineStyle(5, currentMiddleColor);
          mergedBeam.moveTo(x,y);
          if(x < FILTER_X + 50) {
            x = x + 5;
          }
          mergedBeam.lineTo(x, y);
          app.stage.addChild(mergedBeam);
          if(x >= FILTER_X + 50) {
          second = false;
          third = true;
        }
      } else if(third) {
          mergedBeam.lineStyle(5, currentColor);
          mergedBeam.moveTo(x,y);
          if(x < EYE_X + 25) {
            x = x + 5;
          }
          mergedBeam.lineTo(x, y);
          app.stage.addChild(mergedBeam);
          if(x >= EYE_X + 25) {
            colorEyeRectangle();
            beamDrawn = true;
            third = false;
            first = true;
            setTimeout(function () { clearBeam();}, 2000);
          }
      }
      renderer.render(stage);
      }
    }
    }

    function clearBeam() {
      mergedBeam.clear();
      frontBeam.clear();
      backBeam.clear();
       first = true;
       second = false;
       third = false;
       front_x = FRONT_LIGHT_X - 15;
       front_y = FRONT_LIGHT_Y + 15;
       back_x = BACK_LIGHT_X - 15;
       back_y = BACK_LIGHT_Y + 15;
       x = SURFACE_X + 70;
       y = SURFACE_Y + 50;
       beamDrawn = false;
    }

  // Color Surfaces
var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);

function colorSurfaceRectangle(color) {
    coloredSurface.beginFill(color);
    if(color == 0x000000) {
      coloredSurface.lineStyle(1, 0xFFFFFF, 1);
    } else {
      coloredSurface.lineStyle(2, 0x000000, 1);
    }
    if(WIDTH <= 800) {
    var sur_x = WIDTH * 0.31;
    var sur_y = HEIGHT * 0.41;
    coloredSurface.moveTo(sur_x,sur_y);
    coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y);
    coloredSurface.lineTo(sur_x + WIDTH * 0.09, sur_y + HEIGHT * 0.10);
    coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y + HEIGHT * 0.15);
    coloredSurface.lineTo(sur_x, sur_y);
  } else if(WIDTH <= 1000 && HEIGHT <= 600) {
      var sur_x = WIDTH * 0.31;
      var sur_y = HEIGHT * 0.412;
      coloredSurface.moveTo(sur_x,sur_y);
      coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y);
      coloredSurface.lineTo(sur_x + WIDTH * 0.09, sur_y + HEIGHT * 0.16);
      coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y + HEIGHT * 0.21);
      coloredSurface.lineTo(sur_x, sur_y);
    } else if(HEIGHT <= 700) {
      var sur_x = WIDTH * 0.31;
      var sur_y = HEIGHT * 0.412;
      coloredSurface.moveTo(sur_x,sur_y);
      coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y);
      coloredSurface.lineTo(sur_x + WIDTH * 0.09, sur_y + HEIGHT * 0.2);
      coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y + HEIGHT * 0.25);
      coloredSurface.lineTo(sur_x, sur_y);
  } else {
       var sur_x = SURFACE_X + WIDTH * 0.01;
       var sur_y = SURFACE_Y + HEIGHT * 0.02;
       coloredSurface.moveTo(sur_x,sur_y);
       coloredSurface.lineTo(sur_x + WIDTH * 0.04, sur_y);
       coloredSurface.lineTo(sur_x + WIDTH * 0.08, sur_y + HEIGHT * 0.14);
       coloredSurface.lineTo(sur_x + WIDTH * 0.05, sur_y + HEIGHT * 0.21);
       coloredSurface.lineTo(sur_x, sur_y);
  }
    coloredSurface.endFill()
    app.stage.addChild(coloredSurface);
}

var colorEye = new PIXI.Graphics();

var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);
colorEyeRectangle();

function colorEyeRectangle() {
    if(WIDTH <= 600) {
      var rec_x = EYE_X + WIDTH * 0.075;
      var rec_y = EYE_Y;
      colorEye.beginFill(currentColor);
      colorEye.lineStyle(2, 0x000000, 1);
      colorEye.moveTo(rec_x,rec_y);
      colorEye.lineTo(rec_x + WIDTH * 0.08,rec_y);
      colorEye.lineTo(rec_x + WIDTH * 0.08,rec_y + HEIGHT * 0.06);
      colorEye.lineTo(rec_x,rec_y + HEIGHT * 0.06);
      colorEye.lineTo(rec_x,rec_y);
      colorEye.endFill();
      app.stage.addChild(colorEye);
    } else {
      var rec_x = EYE_X + WIDTH * 0.075;
      var rec_y = EYE_Y;
      colorEye.beginFill(currentColor);
      colorEye.lineStyle(2, 0x000000, 1);
      colorEye.moveTo(rec_x,rec_y);
      colorEye.lineTo(rec_x + WIDTH * 0.08,rec_y);
      colorEye.lineTo(rec_x + WIDTH * 0.08,rec_y + HEIGHT * 0.1);
      colorEye.lineTo(rec_x,rec_y + HEIGHT * 0.1);
      colorEye.lineTo(rec_x,rec_y);
      colorEye.endFill();
      app.stage.addChild(colorEye);
  }
}

function calculateMiddleColor() {
    var rgbFrontLight = PIXI.utils.hex2rgb(currentFrontLightColor);
    var rgbBackLight = PIXI.utils.hex2rgb(currentBackLightColor);
    var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
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
}

function calculateColor() {
      calculateMiddleColor();
      var middleColor = PIXI.utils.hex2rgb(currentMiddleColor);
      var rgbFilter = PIXI.utils.hex2rgb(currentFilterColor);
      // red
      console.log(rgbFilter);
      console.log(middleColor);
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

PIXI.Sprite.prototype.bringToFront = function() { if (this.parent) {    var parent = this.parent;   parent.removeChild(this);   parent.addChild(this);  }}


  });