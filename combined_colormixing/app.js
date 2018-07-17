$(function() {
  var app = new PIXI.Application(1600, 750, {backgroundColor : 0x000000});
  document.body.appendChild(app.view);

  var stage = new PIXI.Container();

var BEAM_BUTTON_X = 1000;
var BEAM_BUTTON_Y = 25;
var FRONT_LIGHT_X = 750;
var FRONT_LIGHT_Y = 25;
var BACK_LIGHT_X = 800;
var BACK_LIGHT_Y = 25;
var SURFACE_X = 400;
var SURFACE_Y = 200;
var FILTER_X = SURFACE_X + 250;
var FILTER_Y = SURFACE_Y;
var EYE_X = SURFACE_X + 450;
var EYE_Y = SURFACE_Y;

var OFFSET_X = 30;
var OFFSET_Y = 25;

var frontLightSelected = true;
var currentColor = 0xFFFFFF;
var currentLightColor = 0xFFFFFF;
var currentPaintColor = 0xFFFFFF;
var currentFilterColor = 0xFFFFFF;

var currentFilter;

var redLightButton = PIXI.Texture.fromImage('images/rl1.gif');
var greenLightButton = PIXI.Texture.fromImage('images/gl1.gif');
var blueLightButton = PIXI.Texture.fromImage('images/bl1.gif');
var cyanLightButton = PIXI.Texture.fromImage('images/cl1.gif');
var magentaLightButton = PIXI.Texture.fromImage('images/ml1.gif');
var yellowLightButton = PIXI.Texture.fromImage('images/yl1.gif');

var redLightButton5 = PIXI.Texture.fromImage('images/rl5.gif');
var greenLightButton5 = PIXI.Texture.fromImage('images/gl5.gif');
var blueLightButton5 = PIXI.Texture.fromImage('images/bl5.gif');
var cyanLightButton5 = PIXI.Texture.fromImage('images/cl5.gif');
var magentaLightButton5 = PIXI.Texture.fromImage('images/ml5.gif');
var yellowLightButton5 = PIXI.Texture.fromImage('images/yl5.gif');

var redPaintButton = PIXI.Texture.fromImage('images/rp1.gif');
var greenPaintButton = PIXI.Texture.fromImage('images/gp1.gif');
var bluePaintButton = PIXI.Texture.fromImage('images/bp1.gif');
var cyanPaintButton = PIXI.Texture.fromImage('images/cp1.gif');
var magentaPaintButton = PIXI.Texture.fromImage('images/mp1.gif');
var yellowPaintButton = PIXI.Texture.fromImage('images/yp1.gif');
var blackPaintButton = PIXI.Texture.fromImage('images/kp1.gif');
var whitePaintButton = PIXI.Texture.fromImage('images/wp1.gif');

var redFilterButton = PIXI.Texture.fromImage('images/rf1.gif');
var greenFilterButton = PIXI.Texture.fromImage('images/gf1.gif');
var blueFilterButton = PIXI.Texture.fromImage('images/bf1.gif');
var cyanFilterButton = PIXI.Texture.fromImage('images/cf1.gif');
var magentaFilterButton = PIXI.Texture.fromImage('images/mf1.gif');
var yellowFilterButton = PIXI.Texture.fromImage('images/yf1.gif');

var lightsLabel = PIXI.Texture.fromImage('images/lightsLabel.gif');
var paintsLabel = PIXI.Texture.fromImage('images/paintsLabel.gif');
var filtersLabel = PIXI.Texture.fromImage('images/filtersLabel.gif');

var gradientEye = PIXI.Texture.fromImage('images/gradientEye.gif');
var light = PIXI.Texture.fromImage('images/light.gif');
var lightHalo = PIXI.Texture.fromImage('images/lightHalo.gif');
var lightOn = PIXI.Texture.fromImage('images/lightFront.gif');
var surface = PIXI.Texture.fromImage('images/surfaceObj.gif');
var filterStand = PIXI.Texture.fromImage('images/filterStand.gif');
var beamButton = PIXI.Texture.fromImage('images/beamButton.gif');

var redLight = {button: redLightButton, color:"0xff0000", x:50, y:50}
var greenLight = {button: greenLightButton, color:"0x00ff00", x:100, y:50}
var blueLight = {button: blueLightButton, color:"0x0000ff", x:150, y:50}
var cyanLight = {button: cyanLightButton, color:"0x00ffff", x:50, y:100}
var magentaLight = {button: magentaLightButton, color:"0xff00ff", x:100, y:100}
var yellowLight = {button: yellowLightButton, color:"0xffff00", x:150, y:100}
var Lights = [redLight, greenLight, blueLight, cyanLight, magentaLight, yellowLight]

var redFilter = {button: redFilterButton, color:"0xff0000", x:50, y:450}
var greenFilter = {button: greenFilterButton, color:"0x00ff00", x:100, y:450}
var blueFilter = {button: blueFilterButton, color:"0x0000ff", x:150, y:450}
var cyanFilter = {button: cyanFilterButton, color:"0x00ffff", x:50, y:500}
var magentaFilter = {button: magentaFilterButton, color:"0xff00ff", x:100, y:500}
var yellowFilter = {button: yellowFilterButton, color:"0xffff00", x:150, y:500}
var Filters = [redFilter, greenFilter, blueFilter, cyanFilter, magentaFilter, yellowFilter]

var paintButtons = [redPaintButton, greenPaintButton, bluePaintButton, cyanPaintButton, magentaPaintButton, yellowPaintButton, blackPaintButton, whitePaintButton]
var filterButtons = [redFilterButton, greenFilterButton, blueFilterButton, cyanFilterButton, magentaFilterButton, yellowFilterButton];

var colorsHex = ["0xff0000","0x00ff00", "0x0000ff","0x00ffff","0xff00ff","0xffff00","0x000000","0xFFFFFF"];
var allColors = new Map()
var lightButtonPositions = [
    50, 50,
    100, 50,
    150, 50,
    50, 100,
    100, 100,
    150, 100
];

var paintButtonPositions = [
    50, 200,
    100, 200,
    50, 250,
    100, 250,
    50, 300,
    100, 300,
    50, 350,
    100, 350
];

var filterButtonPositions = [
    50, 450,
    100, 450,
    150, 450,
    50, 500,
    100, 500,
    150, 500
];

    var label = new PIXI.Sprite(lightsLabel);
    label.position.x = lightButtonPositions[0] - 50;
    label.position.y = lightButtonPositions[1] - 50;
    app.stage.addChild(label);

for (var i = 0; i < Lights.length; i++) {
    var button = new PIXI.Sprite(Lights[i].button);
    button.anchor.set(0.5);
    button.x = Lights[i].x;
    button.y = Lights[i].y;
    button.interactive = true;
    button.buttonMode = true;
    allColors.set(button.texture.baseTexture.imageUrl, Lights[i].color)
    if(frontLightSelected) {
      button.on('pointerdown', addFrontLight);
    } else {
      button.on('pointerdown', addBackLight);
    }
    // add it to the stage
    app.stage.addChild(button);
}

    var label = new PIXI.Sprite(paintsLabel);
    label.position.x = paintButtonPositions[0] - 50;
    label.position.y = paintButtonPositions[1] - 50;
    app.stage.addChild(label);

for (var i = 0; i < paintButtons.length; i++) {
    var button = new PIXI.Sprite(paintButtons[i]);
    button.buttonMode = true;

    button.anchor.set(0.5);
    button.x = paintButtonPositions[i*2];
    button.y = paintButtonPositions[i*2 + 1];

    // make the button interactive...
    button.interactive = true;
    button.buttonMode = true;

    allColors.set(button.texture.baseTexture.imageUrl, colorsHex[i])
    button.on('pointerdown', addSurfaceColor);

    // add it to the stage
    app.stage.addChild(button);
  }

    var label = new PIXI.Sprite(filtersLabel);
    label.position.x = filterButtonPositions[0] - 50;
    label.position.y = filterButtonPositions[1] - 50;

    app.stage.addChild(label);

for (var i = 0; i < filterButtons.length; i++) {
    var button = new PIXI.Sprite(filterButtons[i]);
    button.buttonMode = true;

    button.anchor.set(0.5);
    button.x = filterButtonPositions[i*2];
    button.y = filterButtonPositions[i*2 + 1];

    // make the button interactive...
    button.interactive = true;
    button.buttonMode = true;

    allColors.set(button.texture.baseTexture.imageUrl, colorsHex[i])
    button.on('pointerdown', addFilter);

    // add it to the stage
    app.stage.addChild(button);
  }

// Canvas objects:
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

var selectedLightHalo = new PIXI.Sprite(lightHalo);
    selectedLightHalo.position.x = FRONT_LIGHT_X - 25;
    selectedLightHalo.position.y = FRONT_LIGHT_Y - 25;
app.stage.addChild(selectedLightHalo);

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

// Draw beams:
var beamButtonObj = new PIXI.Sprite(beamButton);
    beamButtonObj.position.x = BEAM_BUTTON_X;
    beamButtonObj.position.y = BEAM_BUTTON_Y;

    beamButtonObj.buttonMode = true;

    beamButtonObj.anchor.set(0.5);

    // make the button interactive...
    beamButtonObj.interactive = true;
    beamButtonObj.buttonMode = true;

    beamButtonObj.on('pointerdown', drawBeam);

    app.stage.addChild(beamButtonObj);

    var beam1 = new PIXI.Graphics();
    var beam2 = new PIXI.Graphics();
    var beam3 = new PIXI.Graphics();

function drawBeam() {
    beam1.lineStyle(5, currentLightColor);
    beam1.moveTo(FRONT_LIGHT_X,FRONT_LIGHT_Y);
    beam1.lineTo(SURFACE_X + 50, SURFACE_Y + 50);
    app.stage.addChild(beam1);

    var rgbLight = PIXI.utils.hex2rgb(currentLightColor);
    var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
    var total = [clamp(rgbLight[0] + rgbPaint[0], 0.0, 1.0), clamp(rgbLight[1] + rgbPaint[1], 0.0, 1.0), clamp(rgbLight[2] + rgbPaint[2],0.0,1.0)];
    lightAndPaintColor = PIXI.utils.rgb2hex(total); 

    beam2.lineStyle(5, lightAndPaintColor);
    beam2.moveTo(SURFACE_X + 50, SURFACE_Y + 50);
    beam2.lineTo(FILTER_X + 50, FILTER_Y + 50);
    app.stage.addChild(beam2);

    beam3.lineStyle(5, currentLightColor);
    beam3.moveTo(FILTER_X + 50, FILTER_Y + 50);
    beam3.lineTo(EYE_X, EYE_Y + 50);
    app.stage.addChild(beam3);

    calculateColor();
}

function addFilter() {
  while(this.position.x < FILTER_X + OFFSET_X) {
    this.position.x += 1;
    if(this.position.y > FILTER_Y + OFFSET_Y) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
    }

    currentFilterColor = allColors.get(this.texture.baseTexture.imageUrl)
    colorEyeRectangle();
    currentFilter = this;
}

function removeFilter() {
  currentFilter.position.x
}

function addFrontLight() {
  var x = this.position.x;
  var y = this.position.y;
  while(this.position.x < FRONT_LIGHT_X - 8) {
    this.position.x += 1;
    if(this.position.y > FRONT_LIGHT_Y + 12) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
    }
    currentLightColor = allColors.get(this.texture.baseTexture.imageUrl)
    colorEyeRectangle();

    this.buttonMode = false;
    this.texture = redLightButton5;
    if(frontLightSelected) {
      frontLightObj.texture = lightOn;
    } else {
      backLightObj.texture = lightOn;
    }


}

function addBackLight() {
  var x = this.position.x;
  var y = this.position.y;
  while(this.position.x < BACK_LIGHT_X - 8) {
    this.position.x += 1;
    if(this.position.y > BACK_LIGHT_Y + 12) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
  }
  currentLightColor = allColors.get(this.texture.baseTexture.imageUrl)
  colorEyeRectangle();

    this.buttonMode = false;
    this.texture = redLightButton5;
    if(frontLightSelected) {
      frontLightObj.texture = lightOn;
    } else {
      backLightObj.texture = lightOn;
    }

}

function addSurfaceColor() {
  var x = this.position.x;
  var y = this.position.y; 
  while(this.position.x < SURFACE_X + OFFSET_X) {
    this.position.x += 1;
    if(this.position.y > SURFACE_Y + OFFSET_Y) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
  }

  currentPaintColor = allColors.get(this.texture.baseTexture.imageUrl)
  colorSurfaceRectangle(currentPaintColor);
  colorEyeRectangle();

    while(this.position.x > x) {
    this.position.x -= 1;
    if(this.position.y > y) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
  }

}

var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);

function colorSurfaceRectangle(color) {
    console.log(color);
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

function colorEyeRectangle() {
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

function calculateColor() {
    var rgbLight = PIXI.utils.hex2rgb(currentLightColor);
    var rgbPaint = PIXI.utils.hex2rgb(currentPaintColor);
    var rgbFilter = PIXI.utils.hex2rgb(currentPaintColor);
    var total = [clamp(rgbLight[0] + rgbPaint[0] + rgbFilter[0], 0.0, 1.0), clamp(rgbLight[1] + rgbPaint[1] + rgbFilter[1], 0.0, 1.0), clamp(rgbLight[2] + rgbPaint[2] + rgbFilter[2],0.0,1.0)];
    console.log(total);
    currentColor = PIXI.utils.rgb2hex(total); 
}

function clamp(num, min, max) {
  return num <= min ? min : num >= max ? max : num;
}

// requestAnimationFrame(animate);

// function animate()
// {
//     requestAnimationFrame(animate);
//     renderer.render(stage);
// }

});
