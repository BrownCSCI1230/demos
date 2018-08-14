$(function() {
  PIXI.SCALE_MODES.DEFAULT = PIXI.SCALE_MODES.NEAREST;
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


});