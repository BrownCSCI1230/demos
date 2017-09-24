$(function() {

  var mandrillCanvas = document.getElementById("mandrillCanvas");
  var image = document.getElementById("mandrill");
  var renderer = PIXI.autoDetectRenderer({
    width: 256,
    height: 256,
    view: mandrillCanvas
  });
  var stage = new PIXI.Container;
  var marquee = new PIXI.Graphics;
  var mandrill = new Image();
  var mandrillSprite = new PIXI.Sprite;
  var dragging = false;
  mandrill.src = 'mandrill.png'
  mandrill.onload = function(){
    setUpSprite();
    setUpMarquee();
    setUpInteractions();
    renderer.render(stage);
  }

  function setUpSprite() {
    var texture = new PIXI.Texture(new PIXI.BaseTexture(mandrill));
    mandrillSprite = new PIXI.Sprite(texture);
    mandrillSprite.width = renderer.width;
    mandrillSprite.height = renderer.height;
    stage.addChild(mandrillSprite);
    setUpMarquee();
  }

  function setUpMarquee() {
    marquee.lineStyle(3, 0x000000, 1); //black
    var rectX = stage.x + 1.5;
    var rectY = stage.y + 1.5;
    marquee.drawRect(rectX, rectY, stage.width - 3, stage.height - 3);
    stage.addChild(marquee);
  }

  function setUpInteractions() {
    stage.interactive = true;
    stage.on('pointerdown', clickImage);
    stage.on('pointermove', dragImage);
    stage.on('pointerup', stopDragging);
    stage.on('pointerupoutside', stopDragging);
  }

  function clickImage(eventData) {
    var data = eventData.data;
    var mousePos = new PIXI.Point(0, 0);
    data.getLocalPosition(stage, mousePos, data.global);
    marquee.position = mousePos;
    marquee.width = 0;
    marquee.height = 0;
    dragging = true;
    renderer.render(stage);
  }

  function dragImage(eventData) {
    if (dragging) {
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(stage, mousePos, data.global);
      var oldX = marquee.x;
      var oldY = marquee.y;
      var oldWidth = marquee.width;
      var oldHeight = marquee.height;
      var newX;
      var newY;
      var newWidth;
      var newHeight;
      if (mousePos.x <= oldX) {
        newWidth = oldWidth + Math.abs(mousePos.x - oldX);
        newX = mousePos.x;
      } else {
        newWidth = Math.abs(mousePos.x - oldX);
        newX = oldX;
      }
      if (mousePos.y <= oldY) {
        newHeight = oldHeight + Math.abs(mousePos.y - oldY);
        newY = mousePos.y;
      } else {
        newHeight = Math.abs(mousePos.y - oldY);
        newY = oldY;
      }
      marquee.clear();
      marquee.lineStyle(2, 0x000000, 1);
      marquee.drawRect(0, 0, newWidth, newHeight);
      // don't know why these need to be set twice, but it doesn't work otherwise
      marquee.x = newX;
      marquee.y = newY;
      marquee.width = newWidth;
      marquee.height = newHeight;
      renderer.render(stage);
    }
  }

  function stopDragging(eventData) {
    dragging = false;
  }

})
