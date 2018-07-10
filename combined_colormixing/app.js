$(function() {
  var app = new PIXI.Application(1600, 750, {backgroundColor : 0x000000});
  document.body.appendChild(app.view);

var BEAM_BUTTON_X = 1000;
var BEAM_BUTTON_Y = 0;
var FRONT_LIGHT_X = 750;
var FRONT_LIGHT_Y = 0;
var BACK_LIGHT_X = 800;
var BACK_LIGHT_Y = 0;
var SURFACE_X = 400;
var SURFACE_Y = 200;
var FILTER_X = SURFACE_X + 250;
var FILTER_Y = SURFACE_Y;
var EYE_X = SURFACE_X + 450;
var EYE_Y = SURFACE_Y;

var OFFSET_X = 30;
var OFFSET_Y = 25;

var frontLightSelected = true;

var redLightButton = PIXI.Texture.fromImage('images/rl1.gif');
var greenLightButton = PIXI.Texture.fromImage('images/gl1.gif');
var blueLightButton = PIXI.Texture.fromImage('images/bl1.gif');
var cylonLightButton = PIXI.Texture.fromImage('images/cl1.gif');
var magentaLightButton = PIXI.Texture.fromImage('images/ml1.gif');
var yellowLightButton = PIXI.Texture.fromImage('images/yl1.gif');

var redPaintButton = PIXI.Texture.fromImage('images/rp1.gif');
var greenPaintButton = PIXI.Texture.fromImage('images/gp1.gif');
var bluePaintButton = PIXI.Texture.fromImage('images/bp1.gif');
var cylonPaintButton = PIXI.Texture.fromImage('images/cp1.gif');
var magentaPaintButton = PIXI.Texture.fromImage('images/mp1.gif');
var yellowPaintButton = PIXI.Texture.fromImage('images/yp1.gif');
var blackPaintButton = PIXI.Texture.fromImage('images/kp1.gif');
var whitePaintButton = PIXI.Texture.fromImage('images/wp1.gif');

var redFilterButton = PIXI.Texture.fromImage('images/rf1.gif');
var greenFilterButton = PIXI.Texture.fromImage('images/gf1.gif');
var blueFilterButton = PIXI.Texture.fromImage('images/bf1.gif');
var cylonFilterButton = PIXI.Texture.fromImage('images/cf1.gif');
var magentaFilterButton = PIXI.Texture.fromImage('images/mf1.gif');
var yellowFilterButton = PIXI.Texture.fromImage('images/yf1.gif');

var lightsLabel = PIXI.Texture.fromImage('images/lightsLabel.gif');
var paintsLabel = PIXI.Texture.fromImage('images/paintsLabel.gif');
var filtersLabel = PIXI.Texture.fromImage('images/filtersLabel.gif');

var gradientEye = PIXI.Texture.fromImage('images/gradientEye.gif');
var light = PIXI.Texture.fromImage('images/light.gif');
var surface = PIXI.Texture.fromImage('images/surfaceObj.gif');
var filterStand = PIXI.Texture.fromImage('images/filterStand.gif');
var beamButton = PIXI.Texture.fromImage('images/beamButton.gif');

var lightButtons = [redLightButton, greenLightButton, blueLightButton, cylonLightButton, magentaLightButton, yellowLightButton]
var paintButtons = [redPaintButton, greenPaintButton, bluePaintButton, cylonPaintButton, magentaPaintButton, yellowPaintButton, blackPaintButton, whitePaintButton]
var filterButtons = [redFilterButton, greenFilterButton, blueFilterButton, cylonFilterButton, magentaFilterButton, yellowFilterButton];

var colorsHex = [0xFF0000,0x00FF00,0x0000FF,0xFF0000,0xF0000,0xF00000,0xFFFFFF,0x000000];
var paintColors = new Map()
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

for (var i = 0; i < lightButtons.length; i++) {
    var button = new PIXI.Sprite(lightButtons[i]);
    button.buttonMode = true;

    button.anchor.set(0.5);
    button.x = lightButtonPositions[i*2];
    button.y = lightButtonPositions[i*2 + 1];

    // make the button interactive...
    button.interactive = true;
    button.buttonMode = true;

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

    paintColors.set(button.texture.baseTexture.imageUrl, colorsHex[i])
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

    button.on('pointerdown', addFilter);

    // add it to the stage
    app.stage.addChild(button);
  }

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

app.stage.addChild(frontLightObj);

var backLightObj = new PIXI.Sprite(light);
    backLightObj.position.x = BACK_LIGHT_X;
    backLightObj.position.y = BACK_LIGHT_Y;

app.stage.addChild(backLightObj);

var beamButtonObj = new PIXI.Sprite(beamButton);
    beamButtonObj.position.x = BEAM_BUTTON_X;
    beamButtonObj.position.y = BEAM_BUTTON_Y;

app.stage.addChild(beamButtonObj);

function addFilter() {
  while(this.position.x < FILTER_X + OFFSET_X) {
    this.position.x += 1;
    if(this.position.y > FILTER_Y + OFFSET_Y) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
    }
}

function addFrontLight() {
  while(this.position.x < FRONT_LIGHT_X + OFFSET_X) {
    this.position.x += 1;
    if(this.position.y > FRONT_LIGHT_Y + OFFSET_Y) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
    }
}

function addBackLight() {
  while(this.position.x < BACK_LIGHT_X + OFFSET_X) {
    this.position.x += 1;
    if(this.position.y > BACK_LIGHT_Y + OFFSET_Y) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
  }
}

function addSurfaceColor() {
  while(this.position.x < SURFACE_X + OFFSET_X) {
    this.position.x += 1;
    if(this.position.y > SURFACE_Y + OFFSET_Y) {
      this.position.y -= 1;
    } else {
      this.position.y += 1;
    }
  }

  colorSurfaceRectangle(paintColors.get(this.texture.baseTexture.imageUrl));

}

function onButtonDown() {
    this.isdown = true;
    this.texture = redLightButton;
    this.alpha = 1;
}

function onButtonUp() {
    this.isdown = false;
    if (this.isOver) {
        this.texture = redLightButton;
    }
    else {
        this.texture = redLightButton;
    }
}

function onButtonOver() {
    this.isOver = true;
    if (this.isdown) {
        return;
    }
    this.texture = redLightButton;
}

function onButtonOut() {
    this.isOver = false;
    if (this.isdown) {
        return;
    }
    this.texture = redLightButton;
}

var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFF3300);

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
colorEyeRectangle(0xFF3300);

function colorEyeRectangle(color) {
var rec_x = EYE_X + 75;
var rec_y = EYE_Y;
colorEye.beginFill(color);
colorEye.lineStyle(4, color, 1);
colorEye.moveTo(rec_x,rec_y);
colorEye.lineTo(rec_x + 100,rec_y);
colorEye.lineTo(rec_x + 100,rec_y + 50);
colorEye.lineTo(rec_x,rec_y + 50);
colorEye.lineTo(rec_x,rec_y);
colorEye.endFill();
app.stage.addChild(colorEye);
}

});
