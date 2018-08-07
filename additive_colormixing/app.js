$(function() {
  PIXI.SCALE_MODES.DEFAULT = PIXI.SCALE_MODES.NEAREST;
  var app = new PIXI.Application(1600, 750, {backgroundColor : 0x000000});
  document.body.appendChild(app.view);
  var renderer = PIXI.autoDetectRenderer(1600, 750, {backgroundColor : 0x000000});

  var stage = new PIXI.Container();

  var currentColor = "0xff0000";

  var redBulbOn = PIXI.Texture.fromImage('images/redBulbOn.gif');
  var greenBulbOn = PIXI.Texture.fromImage('images/greenBulbOn.gif');
  var blueBulbOn = PIXI.Texture.fromImage('images/blueBulbOn.gif');
  var redBulbOff = PIXI.Texture.fromImage('images/redBulbOff.gif');
  var greenBulbOff = PIXI.Texture.fromImage('images/greenBulbOff.gif');
  var blueBulbOff = PIXI.Texture.fromImage('images/blueBulbOff.gif');

  var canvasOutline = new PIXI.Graphics();
    canvasOutline.lineStyle(2, 0xFFFFFF, 1);
    canvasOutline.moveTo(0,0);
    canvasOutline.lineTo(800,0);
    canvasOutline.lineTo(800,500);
    canvasOutline.lineTo(0,500);
    canvasOutline.lineTo(0,0);
  app.stage.addChild(canvasOutline);

  var boxOutline = new PIXI.Graphics();
    boxOutline.lineStyle(2, 0xFFFFFF, 1);
    boxOutline.moveTo(800,0);
    boxOutline.lineTo(1000,0);
    boxOutline.lineTo(1000,500);
    boxOutline.lineTo(800,500);
    boxOutline.lineTo(800,0);
  app.stage.addChild(boxOutline);

    var redBulbOnSprite = new PIXI.Sprite(redBulbOn);
    redBulbOnSprite.anchor.set(0.5);
    redBulbOnSprite.x = 50;
    redBulbOnSprite.y = 50;

    var greenBulbOnSprite = new PIXI.Sprite(greenBulbOn);
    greenBulbOnSprite.anchor.set(0.5);
    greenBulbOnSprite.x = 100;
    greenBulbOnSprite.y = 50;

    var blueBulbOnSprite = new PIXI.Sprite(blueBulbOn);
    blueBulbOnSprite.anchor.set(0.5);
    blueBulbOnSprite.x = 200;
    blueBulbOnSprite.y = 50;

  var triangleTexture = PIXI.Texture.fromImage('images/rgbColorfieldCanvas.jpg');
  var triangleSprite = new PIXI.Sprite(triangleTexture);
    triangleSprite.anchor.set(0.5);
    triangleSprite.x = 100;
    triangleSprite.y = 100;
    triangleSprite.interactive = true;
    triangleSprite.buttonMode = true;
      triangleSprite.on('pointerdown', setColor);
    // add it to the stage
    app.stage.addChild(triangleSprite);

    function setColor() {
      currentColor = "0x00ff00";
    }

    var pixels = renderer.extract.pixels(stage)
    //var pixels = PIXI.extract.webGL.pixels(renderTex);

});
