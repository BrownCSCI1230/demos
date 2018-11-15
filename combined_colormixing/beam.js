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
      if(hasFrontLight || hasBackLight) {
      requestAnimationFrame(animateBeam);
      if(first) {
      if(hasFrontLight) {
        frontBeam.lineStyle(5, currentFrontLightColor);
        frontBeam.moveTo(front_x,front_y);
        if(front_x > x) {
          //console.log("x");
          front_x = front_x - 0.01 * (FRONT_LIGHT_X - x); // WIDTH * 0.005
        } 
        if(front_y < y) {
          //console.log("y");
          front_y = front_y - 0.01 * (FRONT_LIGHT_Y - y); // HEIGHT * 0.005
        }
        frontBeam.lineTo(front_x, front_y);
        app.stage.addChild(frontBeam);
        if(front_x <= x && front_y >= y) {
          //console.log("done!");
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
      renderer.render(app.stage);
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