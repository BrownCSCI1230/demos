$(function() {
  PIXI.SCALE_MODES.DEFAULT = PIXI.SCALE_MODES.NEAREST;
<<<<<<< HEAD
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
=======
  var offset = 100;
  var width = window.innerWidth - 4 * offset;
  var height = window.innerHeight - offset;
  var app = new PIXI.Application(width, height, {backgroundColor : 0x000000});
  document.getElementById("app").appendChild(app.view);

  var stage = new PIXI.Container();

var textSize = width/50;
if(textSize < 16) {
  textSize = 16
}

  var canvasY = textSize + textSize/2;
  var canvas = new PIXI.Graphics();
  canvas.lineStyle(4, 0xFFFFFF, 1);
  canvas.moveTo(0,0);
  canvas.lineTo(width,0);
  canvas.lineTo(width,height);
  canvas.lineTo(0,height);
  canvas.lineTo(0,0);
  canvas.endFill();
  app.stage.addChild(canvas);

  var currentColor = "0xff0000";
>>>>>>> eeed7dc7d2aab6878655ace7774c8d4e92949311

  var triangleTexture = PIXI.Texture.fromImage('images/rgbColorfieldCanvas.jpg');
  var triangleSprite = new PIXI.Sprite(triangleTexture);
    triangleSprite.anchor.set(0.5);
    triangleSprite.x = width / 3;
    triangleSprite.y = height / 3;
    triangleSprite.interactive = true;
    triangleSprite.buttonMode = true;
    if(window.innerHeight < window.innerWidth) {
       triangleSprite.width = window.innerWidth/4;
       triangleSprite.height = window.innerWidth/5;
    } else {
      triangleSprite.width = window.innerWidth/4;
      triangleSprite.height = window.innerWidth/5;
    }

      triangleSprite.on('pointerdown', setColor);
    // add it to the stage
    app.stage.addChild(triangleSprite);


    function setColor() {
      currentColor = "0x00ff00";
    }

<<<<<<< HEAD
    var pixels = renderer.extract.pixels(stage)
    //var pixels = PIXI.extract.webGL.pixels(renderTex);
=======
>>>>>>> eeed7dc7d2aab6878655ace7774c8d4e92949311

});
