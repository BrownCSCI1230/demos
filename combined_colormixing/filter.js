// FILTER BUTTONS:

  var redFilter = {button: redFilterButton, color:"0xff0000", x:x50, y:y450}
  var greenFilter = {button: greenFilterButton, color:"0x00ff00", x:x100, y:y450}
  var blueFilter = {button: blueFilterButton, color:"0x0000ff", x:x150, y:y450}
  var cyanFilter = {button: cyanFilterButton, color:"0x00ffff", x:x50, y:y500}
  var magentaFilter = {button: magentaFilterButton, color:"0xff00ff", x:x100, y:y500}
  var yellowFilter = {button: yellowFilterButton, color:"0xffff00", x:x150, y:y500}
  var Filters = [redFilter, greenFilter, blueFilter, cyanFilter, magentaFilter, yellowFilter]
  var originalFilter = {button: yellowFilter, color:"0xff0000", x:50, y:50}
  var currentFilter = null;
  
  for (var i = 0; i < Filters.length; i++) {
    var button = new PIXI.Sprite(Filters[i].button);
    button.scale.set(SCALE_VALUE);
    button.buttonMode = true;
    button.anchor.set(0.5);
    button.x = Filters[i].x;
    button.y = Filters[i].y;
    button.interactive = true;
    button.on('pointerdown', addFilter);
    app.stage.addChild(button);
    allColors.set(button.texture.baseTexture.imageUrl, Filters[i].color)
  }

  var fLabel = new PIXI.Sprite(filtersLabel);
    fLabel.scale.set(SCALE_VALUE);
    fLabel.position.x = Filters[0].x - WIDTH * 0.05;
    fLabel.position.y = Filters[0].y - HEIGHT * 0.08;
    app.stage.addChild(fLabel);

    var filterOutline = new PIXI.Graphics();
    filterOutline.lineStyle(2, 0xFFFFFF, 1);
    filterOutline.moveTo(1,y150);
    filterOutline.lineTo(x200,y150);
    filterOutline.lineTo(x200,y550);
    filterOutline.lineTo(1,y550);
    filterOutline.lineTo(1,y150);
    app.stage.addChild(filterOutline);

  // Add Filter:
  var return_x;
  var return_y;
  const filterLoader = PIXI.loader;
  function addFilter() {
    if(currentFilter != null) {
      returnFilter();
    }
    this.bringToFront();
    if(return_x == null || return_y == null) {
      return_x = this.position.x;
      return_y = this.position.y;
    }
    currentFilter = this;
    this.on('pointerdown', returnFilter);
    currentFilterColor = allColors.get(this.texture.baseTexture.imageUrl)
    currentFilter.position.x = FILTER_X + WIDTH * 0.03;
    currentFilter.position.y = FILTER_Y + y30;
    calculateColor();
  }

  function returnFilter() {
    if(currentFilter != null) {
      currentFilter.position.x = return_x;
      currentFilter.position.y = return_y;
      currentFilter.on('pointerdown', addFilter);
    }
    return_x = null;
    return_y = null;
    currentFilter = null;
    currentFilterColor = 0xFFFFFF;
    calculateColor();
  }