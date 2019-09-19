// LIGHT BUTTONS:
  var originalFrontLight = {button: redLightButton1, color:"0xff0000", x:50, y:50};
  var originalBackLight = {button: redLightButton1, color:"0xff0000", x:50, y:50};
  currentFrontLightColor = "0xFFFFFF";
  currentBackLightColor = "0xFFFFFF"; 
  var currentFrontLight = null;
  var currentBackLight = null;
  
  var redLight = {button: redLightButton1, color:"0xff0000", x:x50, y:y50}
  var greenLight = {button: greenLightButton1, color:"0x00ff00", x:x100, y:y50}
  var blueLight = {button: blueLightButton1, color:"0x0000ff", x:x150, y:y50}
  var cyanLight = {button: cyanLightButton1, color:"0x00ffff", x:x50, y:y100}
  var magentaLight = {button: magentaLightButton1, color:"0xff00ff", x:x100, y:y100}
  var yellowLight = {button: yellowLightButton1, color:"0xffff00", x:x150, y:y100}
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
  const lightLoader = PIXI.loader;
  var hasFrontLight = false;
  var hasBackLight = false;
  var frontColor = 'r';
  var backColor = 'r';
  function addLight() {
    if(this == currentFrontLight) {
      returnFrontLight();
    } else if(this == currentBackLight) {
      returnBackLight();
    } else {
    if(frontLightSelected) {
      // Add to front
      // Check if has current front light
      if(currentFrontLight != null) {
        // remove current front light
        returnFrontLight();
      }
      var texture = this.texture.baseTexture.imageUrl;
       currentFrontLightColor = allColors.get(texture);
      // store old position
      originalFrontLight.x = this.position.x
      originalFrontLight.y = this.position.y

      // //Set new texture
      frontColor = this.texture.baseTexture.imageUrl.charAt(7);
      lightLoader.add(texture).load(moveFrontLight);

      // set current front light
      currentFrontLight = this
      currentFrontLight.texture = PIXI.Texture.fromImage('');

      // update light obj texture
      frontLightObj.texture = lightOn;

      hasFrontLight = true;
    } else {
      // Add to back
      // Check if has current back light
      if(currentBackLight != null) {
        // remove current back light
        returnBackLight();
      }
      var texture = this.texture.baseTexture.imageUrl;
       currentBackLightColor = allColors.get(texture);

      // store old position
      originalBackLight.x = this.position.x
      originalBackLight.y = this.position.y

      //Set new texture
      backColor = this.texture.baseTexture.imageUrl.charAt(7);
      lightLoader.add(texture).load(moveBackLight);

      // set current back light
      currentBackLight = this
      currentBackLight.texture = PIXI.Texture.fromImage('');

      // update light obj texture
      backLightObj.texture = lightOn;

      hasBackLight = true;
    }
    calculateMiddleColor();
    calculateColor();
  }
  }

  function addFrontLightAnimation(){
      var frames = [];
      for (var i = 1; i < 6; i++) {
              frames.push(PIXI.Texture.fromFrame('images/' + frontColor + 'l' + i + '.gif'));
      } 

      var anim = new PIXI.extras.AnimatedSprite(frames);
      anim.x = FRONT_LIGHT_X - x50; 
      anim.y = FRONT_LIGHT_Y + y15; 
      anim.width = WIDTH * SCALE_VALUE * 0.02;
      anim.height = HEIGHT * SCALE_VALUE * 0.04;
      anim.anchor.set(0.5);
      anim.animationSpeed = 0.08;
      anim.loop = false;
      anim.play();

      app.stage.addChild(anim);

      app.ticker.add(function() {
        if(anim.texture.baseTexture.imageUrl.charAt(9) == 5) {
          if(anim.x < FRONT_LIGHT_X - x20) {
            anim.x += 2.5;
            frontLightObj.bringToFront();
          } else {
            anim.texture = PIXI.Texture.fromImage('');
            lightLoader.reset();
            // set new position
            currentFrontLight.position.x = FRONT_LIGHT_X - x20;
            currentFrontLight.position.y = FRONT_LIGHT_Y + y15;

            currentFrontLight.texture = PIXI.Texture.fromImage('images/' + frontColor + 'l5.gif');
            return;
          }
        }
    });
      
}

  function addBackLightAnimation(){
      var frames = [];
      for (var i = 1; i < 6; i++) {
              frames.push(PIXI.Texture.fromFrame('images/' + backColor + 'l' + i + '.gif'));
      } 

      var anim = new PIXI.extras.AnimatedSprite(frames);
      anim.x = BACK_LIGHT_X - x50; 
      anim.y = BACK_LIGHT_Y + y15; 
      anim.width = WIDTH * SCALE_VALUE * 0.02;
      anim.height = HEIGHT * SCALE_VALUE * 0.04;
      anim.anchor.set(0.5);
      anim.animationSpeed = 0.08;
      anim.loop = false;
      anim.play();

      app.stage.addChild(anim);

      app.ticker.add(function() {
        if(anim.texture.baseTexture.imageUrl.charAt(9) == 5) {
          if(anim.x < BACK_LIGHT_X - x20) {
            anim.x += 2.5;
            backLightObj.bringToFront();
          } else {
            anim.texture = PIXI.Texture.fromImage('');
            lightLoader.reset();
            // set new position
            currentBackLight.position.x = BACK_LIGHT_X - x20;
            currentBackLight.position.y = BACK_LIGHT_Y + y15;

            currentBackLight.texture = PIXI.Texture.fromImage('images/' + backColor + 'l5.gif');
            return;
          }
        }
    });
}

  function moveFrontLight() {
  var frames = [];
  var texture = PIXI.Texture.fromImage('images/' + frontColor + 'l1.gif');
  frames.push(texture);

  var anim = new PIXI.extras.AnimatedSprite(frames);
  anim.width = WIDTH * SCALE_VALUE * 0.02;
  anim.height = HEIGHT * SCALE_VALUE * 0.04;
  anim.anchor.set(0.5);
  anim.animationSpeed = 0.08;
  anim.loop = false;
  anim.play();

  app.stage.addChild(anim);

  anim.x = originalFrontLight.x;
  anim.y = originalFrontLight.y;

  var rotate = true;
  app.ticker.add(function() {
    if(anim.x < FRONT_LIGHT_X - x50) {
      anim.x += 5;
      if(anim.y > FRONT_LIGHT_Y + y15) {
        anim.y -= 1;
      }
    } else {
        if(rotate) {
          anim.texture = PIXI.Texture.fromImage('');
          lightLoader.reset();
          var t = 'images/' + frontColor + 'l1.gif';
          lightLoader.add(t).load(addFrontLightAnimation);
          rotate = false;
      } else {
        return;
      }
  }
  });

  }

  function moveBackLight() {
  var frames = [];
  var texture = PIXI.Texture.fromImage('images/' + backColor + 'l1.gif');
  frames.push(texture);

  var anim = new PIXI.extras.AnimatedSprite(frames);
  anim.width = WIDTH * SCALE_VALUE * 0.02;
  anim.height = HEIGHT * SCALE_VALUE * 0.04;
  anim.anchor.set(0.5);
  anim.animationSpeed = 0.08;
  anim.loop = false;
  anim.play();

  app.stage.addChild(anim);

  anim.x = originalBackLight.x;
  anim.y = originalBackLight.y;

  var rotate = true;
  app.ticker.add(function() {
    if(anim.x < BACK_LIGHT_X - x50) {
      anim.x += 5;
      if(anim.y > BACK_LIGHT_Y + y15) {
        anim.y -= 1;
      }
    } else {
        if(rotate) {
          anim.texture = PIXI.Texture.fromImage('');
          lightLoader.reset();
          var t = 'images/' + frontColor + 'l1.gif';
          lightLoader.add(t).load(addBackLightAnimation);
          rotate = false;
      } else {
        if(anim.x < FRONT_LIGHT_X - x20) {
          anim.x += 5;
        } else {
          return;
        }
      }
    }
  });

  }

  function returnFrontLight() {
  	  currentFrontLight.position.x = originalFrontLight.x;
      currentFrontLight.position.y = originalFrontLight.y;
  	  currentFrontLight.texture = PIXI.Texture.fromImage('images/' + frontColor + 'l1.gif');
      hasFrontLight = false;
      frontLightObj.texture = light;
      currentFrontLight = null;
  }

    function returnBackLight() {
    	currentBackLight.position.x = originalBackLight.x;
	    currentBackLight.position.y = originalBackLight.y;
  	  currentBackLight.texture = PIXI.Texture.fromImage('images/' + backColor + 'l1.gif');
	    hasBackLight = false;
      backLightObj.texture = light;
	    currentBackLight = null;
  }