  // CANVAS OBJECTS
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

   // Color Surfaces
var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);

function colorSurfaceRectangle(color) {
	console.log("!!");
    coloredSurface.beginFill(color);
    if(color == 0x000000) {
      coloredSurface.lineStyle(1, 0xFFFFFF, 1);
    } else {
      coloredSurface.lineStyle(2, 0x000000, 1);
    }

	      var sur_x = SURFACE_X - WIDTH * 0.05;
	      var sur_y = SURFACE_Y - HEIGHT * 0.055;
	      coloredSurface.moveTo(sur_x,sur_y);
	      coloredSurface.lineTo(sur_x + 50, sur_y);
	      coloredSurface.lineTo(sur_x + 90, sur_y + 80);
	      coloredSurface.lineTo(sur_x + 50, sur_y + 100);
	      coloredSurface.lineTo(sur_x, sur_y);
        coloredSurface.scale.set(SCALE_VALUE);

    coloredSurface.endFill()
    app.stage.addChild(coloredSurface);
}

var colorEye = new PIXI.Graphics();

var coloredSurface = new PIXI.Graphics();
colorSurfaceRectangle(0xFFFFFF);
colorEyeRectangle();

function colorEyeRectangle() {
      var w = WIDTH
      if (w > 800) {
        w = 800;
      }

      var h = HEIGHT
      if (h > 600) {
        h = 600;
      }

      var rec_x = EYE_X + w * 0.1;
      var rec_y = EYE_Y;
      colorEye.beginFill(currentColor);
      colorEye.lineStyle(2, 0x000000, 1);
      colorEye.moveTo(rec_x,rec_y);
      colorEye.lineTo(rec_x + w * 0.12,rec_y);
      colorEye.lineTo(rec_x + w * 0.12,rec_y + h * 0.12);
      colorEye.lineTo(rec_x,rec_y + h * 0.12);
      colorEye.lineTo(rec_x,rec_y);
      colorEye.endFill();
      app.stage.addChild(colorEye);
}