  // LIGHT HALO
  var selectedLightHalo = new PIXI.Sprite(lightHalo);
  selectedLightHalo.scale.set(SCALE_VALUE);
  selectedLightHalo.position.x = FRONT_LIGHT_X - x25;
  if(WIDTH <= MINWIDTH) {
    selectedLightHalo.position.y = FRONT_LIGHT_Y - y25;
  } else {
    selectedLightHalo.position.y = FRONT_LIGHT_Y - y30;
  }
  app.stage.addChild(selectedLightHalo);