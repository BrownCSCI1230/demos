$(function() {

  var mandrillCanvas = document.getElementById("mandrillCanvas");
  var image = document.getElementById("mandrill");
  var renderer;
  var stage;

  setUpWidget(256, 256);

  function setUpWidget(width, height) {
    renderer = PIXI.autoDetectRenderer({
      width: width,
      height: height,
      view: mandrillCanvas
    });
    stage = new PIXI.Container;

    var mandrill = new Image()
    mandrill.src = 'mandrill.png'
    mandrill.onload = function(){
      var texture = new PIXI.Texture(new PIXI.BaseTexture(mandrill));
      var sprite = new PIXI.Sprite(texture);
      sprite.width = renderer.width;
      sprite.height = renderer.height;
      stage.addChild(sprite);
      renderer.render(stage);
    }
  }

})
