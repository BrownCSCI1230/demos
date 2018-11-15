
// LIGHT OBJECTS
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
       if(WIDTH <= 500) {
      selectedLightHalo.position.y = FRONT_LIGHT_Y - y25;
    } else {
      selectedLightHalo.position.y = FRONT_LIGHT_Y - y30;
    }
  }
}

function selectBackLight() {
    if(!frontLightSelected) {
      returnBackLight();
    } else {
      frontLightSelected = false;
      selectedLightHalo.position.x = BACK_LIGHT_X - WIDTH * 0.022;
       if(WIDTH <= 500) {
      selectedLightHalo.position.y = FRONT_LIGHT_Y - y25;
    } else {
      selectedLightHalo.position.y = FRONT_LIGHT_Y - y30;
    }
  }
}