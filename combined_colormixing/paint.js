// PAINT BUTTONS:
  var redPaint = {button: redPaintButton1, color:"0xff0000", x:x50, y:y200}
  var greenPaint = {button: greenPaintButton1, color:"0x00ff00", x:x125, y:y200}
  var bluePaint = {button: bluePaintButton1, color:"0x0000ff", x:x50, y:y250}
  var cyanPaint = {button: cyanPaintButton1, color:"0x00ffff", x:x125, y:y250}
  var magentaPaint = {button: magentaPaintButton1, color:"0xff00ff", x:x50, y:y300}
  var yellowPaint = {button: yellowPaintButton1, color:"0xffff00", x:x125, y:y300}
  var blackPaint = {button: blackPaintButton1, color:"0x000000", x:x50, y:y350}
  var whitePaint = {button: whitePaintButton1, color:"0xFFFFFF", x:x125, y:y350}
  var Paints = [redPaint, greenPaint, bluePaint, cyanPaint, magentaPaint, yellowPaint, blackPaint, whitePaint]

  var originalPaint = {button: whitePaintButton1, color:"0xFFFFFF", x:50, y:50}
  var currentPaint = null;

  for (var i = 0; i < Paints.length; i++) {
    var button = new PIXI.Sprite(Paints[i].button);
    button.scale.set(SCALE_VALUE);
    button.buttonMode = true;
    button.anchor.set(0.5);
    button.x = Paints[i].x;
    button.y = Paints[i].y;
    button.interactive = true;
    button.on('pointerdown', addPaintColor);
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Paints[i].color)
  }

    var pLabel = new PIXI.Sprite(paintsLabel);
    pLabel.scale.set(SCALE_VALUE);
    pLabel.position.x = Paints[0].x - WIDTH * 0.05;
    pLabel.position.y = Paints[0].y - HEIGHT * 0.08;
    app.stage.addChild(pLabel);

    var paintOutline = new PIXI.Graphics();
    paintOutline.lineStyle(2, 0xFFFFFF, 1);
    paintOutline.moveTo(1,y150);
    paintOutline.lineTo(x200,y150);
    paintOutline.lineTo(x200,y400);
    paintOutline.lineTo(1,y400);
    paintOutline.lineTo(1,1);
    app.stage.addChild(paintOutline);

  // Paint
  const paintLoader = PIXI.loader;
  var paint_x;
  var paint_y;
  var paintColor = "r";
  function addPaintColor() {
    this.bringToFront();
    paint_x = this.position.x;
    paint_y = this.position.y;
    currentPaintColor = 0xFFFFFF; //reset paint color
    currentPaint = this;
    var texture = this.texture.baseTexture.imageUrl;
    paintColor = texture.charAt(7);
    paintLoader.add(texture).load(movePaint);
    currentPaintColor = allColors.get(texture)
    calculateColor();
  }

    function pourPaintAnimation(){
      var frames = [];
      for (var i = 1; i < 6; i++) {
              frames.push(PIXI.Texture.fromFrame('images/' + paintColor + 'p' + i + '.gif'));
      } 

      var anim = new PIXI.extras.AnimatedSprite(frames);
      anim.x = SURFACE_X + x50; 
      anim.y = SURFACE_Y - y30; 
      anim.width = WIDTH * SCALE_VALUE * 0.03;
      anim.height = HEIGHT * SCALE_VALUE * 0.06;
      anim.anchor.set(0.5);
      anim.animationSpeed = 0.08;
      anim.loop = false;
      anim.play();

      app.stage.addChild(anim);

      app.ticker.add(function() {
        if(anim.texture.baseTexture.imageUrl.charAt(9) == 5) {
            setTimeout(function(){
              anim.texture = PIXI.Texture.fromImage('');
              colorSurfaceRectangle(currentPaintColor);
              return;
            }, 500);
          }
        });
      
}

  function movePaint() {
  var frames = [];
  var texture = PIXI.Texture.fromImage('images/' + paintColor + 'p1.gif');
  frames.push(texture);

  var anim = new PIXI.extras.AnimatedSprite(frames);
  anim.width = WIDTH * SCALE_VALUE * 0.03;
  anim.height = HEIGHT * SCALE_VALUE * 0.06;
  anim.anchor.set(0.5);
  anim.animationSpeed = 0.08;
  anim.loop = false;
  anim.play();

  app.stage.addChild(anim);

  anim.x = paint_x;
  anim.y = paint_y;

  var rotate = true;
  app.ticker.add(function() {
    if(anim.x < SURFACE_X + x50) {
      anim.x += 5;
      if(anim.y > SURFACE_Y - y30) {
        anim.y -= 2;
      } 
    } else {
        if(rotate) {
          anim.texture = PIXI.Texture.fromImage('');
          paintLoader.reset();
          var t = 'images/' + paintColor + 'p1.gif';
          paintLoader.add(t).load(pourPaintAnimation);
          rotate = false;
      } else {
        return;
      }
  }
  });

  }

  function returnPaint() {
    if(currentPaint != null) {
    currentPaint.position.x = paint_x;
    currentPaint.position.y = paint_y;
    currentPaint.rotation = 0;
    }
    paint_x = null;
    paint_y = null;
    currentPaint = null;
    renderer.render(stage);
  }