// TODO: Setting target sprite
// TODO: Win condition check on sprite move
// TODO: Level descriptions
//    - Target sprite matrix / transform
//    - List of transformations
//    - Construct level from description (attach to dom element)
//    - Callback on win to construct new level
// TODO: Transformation block image
// TODO: Transformation queue image
// TODO: animations

PRIMARY_STYLE = 0;
SECONDARY_STYLE = 1;
TARGET_STYLE = 2;

class Sprite extends PIXI.Sprite {
  constructor(style = PRIMARY_STYLE, world = undefined) {
    // Get sprite image
    var texture;
    var alpha = 1;

    if(style === PRIMARY_STYLE) {
      texture = PIXI.Texture.fromImage('res/link.gif');
    }
    else if(style === SECONDARY_STYLE) {
      texture = PIXI.Texture.fromImage('res/link.gif');
      alpha = 0.5;
    }
    else if(style === TARGET_STYLE) {
      texture = PIXI.Texture.fromImage('res/dark_link.png');
    }

    // Call parent class constructor
    super(texture);

    // Set as interactive
    this.interactive = (style === PRIMARY_STYLE);
    this.buttonMode = true;

    // Center image
    this.anchor.set(0.5);

    // Set properties
    this.style = style;
    this.world = world;
    this.alpha = alpha;

    // Set up variables for drag events
    this.lastMousePos = null;
  }

  flagUpdateWorldTransform() {
    this.transform._parentID++;
  }

  setWorldSpaceTransform(transform) {
    // Center and scale sprite 
    var toNormal = new PIXI.Matrix();
    toNormal.scale(0.1, 0.1);
    toNormal.translate(this.world.width / 2, this.world.height / 2);

    // Get transforms to and from world space
    var toWorld = this.world.getWorldSpaceMatrix();
    var toPixel = this.world.getPixelSpaceMatrix();

    // Get cumulative transform
    var total = new PIXI.Matrix(toNormal.a, toNormal.b, toNormal.c, toNormal.d, toNormal.tx, toNormal.ty);
    total = total.prepend(toWorld);
    total = total.prepend(transform);
    total = total.prepend(toPixel);

    // Set cumulative transform
    this.transform.localTransform = total;
    this.flagUpdateWorldTransform();
    this.updateTransform();
  }

  pointerup(mouseData) {
    globalData.draggingSprite = false;
  }

  pointerdown(mouseData) {
    globalData.draggingSprite = true;
    this.lastMousePos = this.world.getWorldMousePosition(mouseData);
    globalData.blockQueue.onSpriteDragStart(this.lastMousePos);
  }

  pointermove(mouseData) {
    if(globalData.draggingSprite) {
      var mousePos = this.world.getWorldMousePosition(mouseData);
      this.pointerdrag(mousePos);
      this.lastMousePos = mousePos;
    }
  }

  pointerdrag(mousePos) {
    globalData.blockQueue.onSpriteDrag(this.lastMousePos, mousePos);
  }

  onClick(mouseData) {
  }

  onDrag(mouseData) {
  }
}

TRANSLATE_TYPE = 0;
SCALE_TYPE = 1;
ROTATE_TYPE = 2;
SHEAR_TYPE = 3;

SUBTYPE_1 = 0;
SUBTYPE_2 = 1;

globalData = {
  'blockContainers': [],
  'blockQueue': null,
  'world': null,
  'draggingSprite': false
}

class SpriteTransformBlock extends PIXI.Container {
  constructor(type = TRANSLATE_TYPE, subtype = SUBTYPE_1, width = 75, height = 75) {
    super();

    // Set as interactive
    this.buttonMode = true;
    this.interactive = true;

    // Set properties
    this.type = type;
    this.subtype = subtype;
    if(this.type === SCALE_TYPE) {
      this._value = 1;
    }
    else {
      this._value = 0;
    }

    // Make UI
    // TODO: have image / animation to go with title for block
    this._width = width;
    this._height = height;
    this._color = 0xFF0000;
    this._makeBackground();
    this._makeTitle();

    // Set up variables for drag events
    this.isMouseDown = false;
    this.lastMousePos = null;
  }

  _makeTitle() {
    // Title
    var textStyle = new PIXI.TextStyle({
      fontFamily: 'Arial',
      fontSize: 14
    });

    var titleText;

    if(this.type === TRANSLATE_TYPE) {
      titleText = 'translate';
    }
    else if(this.type === SCALE_TYPE) {
      titleText = 'scale';
    }
    else if(this.type === ROTATE_TYPE) {
      titleText = 'rotate';
    }
    else if(this.type === SHEAR_TYPE) {
      titleText = 'shear';
    }

    this.title = new PIXI.Text(titleText, textStyle);
    this.title.anchor.set(0.5, 0);
    this.title.x = this.width / 2;
  }

  _makeSubtitle() {
    // Subtitle
    var textStyle = new PIXI.TextStyle({
      fontFamily: 'Arial',
      fontSize: 14
    });

    var subtitleText = '';

    if(this.type === ROTATE_TYPE) {
    }
    else {
      if(this.subtype == SUBTYPE_1) {
	subtitleText = 'x: ';
      }
      else if(this.subtype == SUBTYPE_2) {
	subtitleText = 'y: ';
      }
    }

    subtitleText += this.value.toFixed(3);

    this.subtitle = new PIXI.Text(subtitleText, textStyle);
    if(this.type === ROTATE_TYPE) {
      this.subtitle.anchor.set(0.5, 0.5);
      this.subtitle.x = this.width / 2;
    }
    else {
      this.subtitle.anchor.set(0, 0.5);
      this.subtitle.x = 10;
    }
    this.subtitle.y = this.height / 2;
  }

  _makeBackground() {
    this.background = new PIXI.Graphics();
    this.background.beginFill(this.color)
    this.background.drawRect(0, 0, this._width, this._height);
    this.background.endFill();
  }

  makeContent() {
    this._makeBackground();
    this._makeTitle();

    if(this._subtitle) {
      this._makeSubtitle();
    }
  }

  resetValue() {
    if(this.type === SCALE_TYPE) {
      this.value = 1;
    }
    else {
      this.value = 0;
    }
  }

  get width() {
    return this._width;
  }

  set width(width) {
    this._width = width;

    if(this._background != undefined) {
      this._background.width = width;
    }
  }

  get height() {
    return this._height;
  }

  set height(height) {
    this._height = height;

    if(this._background != undefined) {
      this._background.height = height;
    }
  }

  get color() {
    return this._color;
  }

  set color(c) {
    this._color = c;

    this.makeContent();
  }

  get title() {
    return this._title;
  }

  set title(to) {
    this.removeChild(this._title);
    this._title = to;
    this.addChild(to);
  }

  get subtitle() {
    return this._subtitle;
  }

  set subtitle(to) {
    this.removeChild(this.subtitle);
    this._subtitle = to;
    this.addChild(to);
  }

  get background() {
    return this._background;
  }

  set background(co) {
    this.removeChild(this._background);
    this._background = co;
    this.addChild(co);
  }

  get value() {
    return this._value;
  }

  set value(v) {
    this._value = v;
    this._makeSubtitle();
  }

  getSpriteTransform() {
    var matrix = new PIXI.Matrix();

    switch(this.type) {
      case TRANSLATE_TYPE:
        if(this.subtype === SUBTYPE_1) {
          matrix.translate(this.value, 0);
        }
        else if(this.subtype === SUBTYPE_2) {
          matrix.translate(0, this.value);
        }
        break;
      case SCALE_TYPE:
        if(this.subtype === SUBTYPE_1) {
          matrix.scale(this.value, 1);
        }
        else if(this.subtype === SUBTYPE_2) {
          matrix.scale(1, this.value);
        }
        break;
      case ROTATE_TYPE:
        if(this.subtype === SUBTYPE_1) {
	  matrix.rotate(this.value);
        }
        else if(this.subtype === SUBTYPE_2) {
	  matrix.rotate(-this.value);
        }
        break;
      case SHEAR_TYPE:
        if(this.subtype === SUBTYPE_1) {
          matrix = new PIXI.Matrix(1, this.value, 0, 1, 0, 0)
        }
        else if(this.subtype === SUBTYPE_2) {
          matrix = new PIXI.Matrix(1, 0, this.value, 1, 0, 0)
        }
        break;
    }

    return matrix;
  }

  getPixelMousePosition(mouseData) {
    var data = mouseData.data;
    var pixelMousePos = new PIXI.Point(0, 0);
    data.getLocalPosition(this, pixelMousePos, data.global);

    return pixelMousePos;
  }

  onSpriteDragStart(mousePos) {
    this.startSpriteDragValue = this.value;
    this.startSpriteDragMousePos = mousePos;
    this.transformSubtypeSet = false;
  }

  onSpriteDrag(lastMousePos, curMousePos) {
    if(!this.transformSubtypeSet && this.type !== ROTATE_TYPE) {
      var mouseOffset = new PIXI.Point(Math.abs(curMousePos.x - this.startSpriteDragMousePos.x),
	  Math.abs(curMousePos.y - this.startSpriteDragMousePos.y));

      if(mouseOffset.x > 1 || mouseOffset.y > 1) {
	var subtype;

	if(mouseOffset.x > mouseOffset.y) {
	  if(this.type === SHEAR_TYPE) {
	    subtype = SUBTYPE_2;
	  }
	  else {
	    subtype = SUBTYPE_1;
	  }
	}
	else {
	  if(this.type === SHEAR_TYPE) {
	    subtype = SUBTYPE_1;
	  }
	  else {
	    subtype = SUBTYPE_2;
	  }
	}

	if(this.subtype !== subtype) {
	  this.subtype = subtype;
	  this.resetValue();
	  this.startSpriteDragValue = this.value;
	}

	this.transformSubtypeSet = true;
      }
    }

    if(this.type === TRANSLATE_TYPE) {
      if(this.subtype === SUBTYPE_1) {
	this.value += curMousePos.x - lastMousePos.x;
      }
      else if(this.subtype === SUBTYPE_2) {
	this.value += curMousePos.y - lastMousePos.y;
      }
    }
    else if(this.type === ROTATE_TYPE) {
      var lastAngle = Math.atan2(lastMousePos.y, lastMousePos.x);
      var curAngle = Math.atan2(curMousePos.y, curMousePos.x);
      this.value += curAngle - lastAngle;
    }
    else if(this.type === SCALE_TYPE) {
      if(this.subtype === SUBTYPE_1) {
	if(this.startSpriteDragMousePos.x === 0) {
	  this.value = 1;
	}
	else {
	  var divisor = this.startSpriteDragMousePos.x;
	  var numerator = curMousePos.x;
	  var scaleFactor = (numerator / divisor);

	  this.value = this.startSpriteDragValue * scaleFactor;
	}
      }
      else if(this.subtype === SUBTYPE_2) {
	if(this.startSpriteDragMousePos.y === 0) {
	  this.value = 1;
	}
	else {
	  var divisor = this.startSpriteDragMousePos.y;
	  var numerator = curMousePos.y;
	  var scaleFactor = (numerator / divisor);

	  this.value = this.startSpriteDragValue * scaleFactor;
	}
      }
    }
    else if(this.type === SHEAR_TYPE) {
      if(this.subtype === SUBTYPE_1) {
	if(this.startSpriteDragMousePos.x !== 0) {
	  this.value += (curMousePos.y - lastMousePos.y) / this.startSpriteDragMousePos.x;
	}
      }
      else if(this.subtype === SUBTYPE_2) {
	if(this.startSpriteDragMousePos.y !== 0) {
	  this.value += (curMousePos.x - lastMousePos.x) / this.startSpriteDragMousePos.y;
	}
      }
    }

    this.makeContent();
  }

  pointerup(mouseData) {
    this.isMouseDown = false;

    // Snap to container
    var globalMousePos = mouseData.data.global.clone();
    var newPos;
    var p = null;

    globalData.blockContainers.forEach(function(c) {
      var hitArea = new PIXI.Rectangle(c.x, c.y, c.width, c.height);

      if(hitArea.contains(globalMousePos.x, globalMousePos.y)) {
	p = c;
      }
    });

    p = p || this.lastParent;
    if(this.lastParent && this.lastParent.update) {
      this.lastParent.update();
    }
    var i = p.getBlockIndexGlobal(globalMousePos);
    p.addBlock(i, this);
  }

  pointerdown(mouseData) {
    this.isMouseDown = true;

    this.lastParent = this.parent;
    var stage = this.parent.parent;

    var globalPos = new PIXI.Point(this.x + this.parent.x, this.y + this.parent.y);

    this.lastParent.removeBlock(this);
    stage.addChild(this);
    this.position = globalPos;

    this.lastMousePos = mouseData.data.global.clone();
  }

  pointermove(mouseData) {
    if(this.isMouseDown) {
      var curMousePos = mouseData.data.global.clone();
      this.pointerdrag(this.lastMousePos, curMousePos);
      this.lastMousePos = curMousePos;
    }
  }

  pointerdrag(lastMousePos, curMousePos) {
    if(lastMousePos !== null) {
      this.x += curMousePos.x - lastMousePos.x;
      this.y += curMousePos.y - lastMousePos.y;
    }
  }
}

class SpriteTransformBag extends PIXI.Container {
  constructor(append = true, width = 75, height = 600) {
    super();

    // Set as interactive
    this.interactive = true;

    // Properties
    this.blocks = [];

    // UI
    this._width = width;
    this._height = height;
    this._makeBackground();
    this.blockOffsetWidth = this.width;
    this.blockOffsetHeight = this.blockOffsetWidth;

    // Global data
    if(append) {
      globalData.blockContainers.push(this);
    }
  }

  // TODO: update background
  _makeBackground() {
    this.background = new PIXI.Graphics();
    this.background.beginFill(0xFFFFFF)
    this.background.drawRect(0, 0, this._width, this._height);
    this.background.endFill();
  }

  // TODO: maybe reset transform value, or text, active transform
  addBlock(i, block) {
    if(this.blocks.length === 0) {
      i = 0;
    }

    this.blocks.splice(i, 0, block);
    this.addChild(block);
    this.update();
  }

  removeBlock(block) {
    var i = this.blocks.indexOf(block);

    if(this.activeBlockIndex >= i) {
      this.activeBlockIndex--;
    }

    this.blocks.splice(i, 1);
    this.removeChild(block);

    if(this.activeBlockIndex === -1 && this.blocks.length > 0) {
      this.activeBlockIndex = 0;
    }
  }

  update() {
    for(var j = 0; j < this.blocks.length; j++) {
      var currBlock = this.blocks[j];
      currBlock.color = 0xFF0000;
      currBlock.x = 0;
      currBlock.y = this.blockOffsetHeight * j;
    }
  }

  get width() {
    return this._width;
  }

  set width(width) {
    this._width = width;

    if(this._background != undefined) {
      this._background.width = width;
    }
  }

  get height() {
    return this._height;
  }

  set height(height) {
    this._height = height;

    if(this._background != undefined) {
      this._background.height = height;
    }
  }

  get background() {
    return this._background;
  }

  set background(co) {
    this.removeChild(this._background);
    this._background = co;
    this.addChild(co);
  }

  getPixelMousePosition(mouseData) {
    var data = mouseData.data;
    var pixelMousePos = new PIXI.Point(0, 0);
    data.getLocalPosition(this, pixelMousePos, data.global);

    return pixelMousePos;
  }

  getBlockIndexGlobal(pos) {
    var offset = Math.max(Math.min(pos.y - this.y, this.height), 0);
    return Math.min(this.blocks.length, Math.floor(offset / this.blockOffsetHeight));
  }

  getBlockPosition(i) {
    return new PIXI.Point(0, this.height * i);
  }

  onClick(mouseData) {
  }

  onDrag(mouseData) {
  }
}

class SpriteTransformQueue extends SpriteTransformBag {
  constructor(width = 600, height = 75) {
    super(false);

    // Set as interactive
    this.interactive = true;

    // Set properties
    this.activeBlockIndex = -1;

    // UI
    // TODO: way to specify if queue / bag is vertical or horizontal
    this._width = width;
    this._height = height;
    this._makeBackground();
    this.blockOffsetWidth = this.height;
    this.blockOffsetHeight = this.blockOffsetWidth;

    // Global data
    globalData.blockContainers.push(this);
    globalData.blockQueue = this;
  }

  addBlock(i, block) {
    if(this.blocks.length === 0) {
      i = 0;
    }

    this.activeBlockIndex = i;
    this.blocks.splice(i, 0, block);
    this.addChild(block);
    this.update();
  }

  update() {
    for(var j = 0; j < this.blocks.length; j++) {
      var currBlock = this.blocks[j];
      currBlock.color = 0xFF0000;
      currBlock.x = this.width - this.blockOffsetWidth * (j + 1);
      currBlock.y = 0;
    }

    if(this.activeBlockIndex !== -1) {
      this.blocks[this.activeBlockIndex].color = 0x00FF00;

      var curTransform = this.getSpriteTransformUpToIndex(this.activeBlockIndex);
      globalData.world.primarySprite.setWorldSpaceTransform(curTransform);

      if(this.activeBlockIndex !== this.blocks.length - 1) {
	var totalTransform = this.getTotalSpriteTransform();

	globalData.world.secondarySprite = new Sprite(SECONDARY_STYLE, globalData.world);
	globalData.world.secondarySprite.setWorldSpaceTransform(totalTransform);
      }
      else {
	globalData.world.secondarySprite = null;
      }
    }
  }

  getTotalSpriteTransform() {
    var totalTransform = new PIXI.Matrix();

    this.blocks.forEach(function(block) {
      totalTransform.prepend(block.getSpriteTransform());
    });

    return totalTransform;
  }

  getSpriteTransformUpToIndex(i) {
    var totalTransform = new PIXI.Matrix();

    for(var j = 0; j <= i; j++) {
      totalTransform.prepend(this.blocks[j].getSpriteTransform());
    }

    return totalTransform;
  }

  getBlockIndexGlobal(pos) {
    var offset = Math.max(Math.min(pos.x - this.x, this.width), 0);
    return Math.min(this.blocks.length, Math.floor((this.width - offset) / this.blockOffsetWidth));
  }

  getBlockPosition(i) {
    return new PIXI.Point(this.width - (i + 1) * this.blockOffsetWidth, 0);
  }

  onSpriteDragStart(mousePos) {
    if(this.activeBlockIndex !== -1) {
      this.blocks[this.activeBlockIndex].onSpriteDragStart(mousePos);
    }
  }

  onSpriteDrag(lastMousePos, curMousePos) {
    if(this.activeBlockIndex !== -1) {
      this.blocks[this.activeBlockIndex].onSpriteDrag(lastMousePos, curMousePos);
      this.update();
    }
  }

  onClick(mouseData) {
  }

  onDrag(mouseData) {
  }
}

class World extends PIXI.Container {
  constructor(width = 0, height = 0, worldSpaceWidth = 0, worldSpaceHeight = 0,
      backgroundColor = 0xFFFFFF) {
    super();

    // Set as interactive
    this.interactive = true;

    // Set properties
    this.worldSpaceWidth = worldSpaceWidth;
    this.worldSpaceHeight = worldSpaceHeight;
    this._backgroundColor = backgroundColor;
    this._makeBackground(width, height);

    // UI
    this._primarySprite = undefined;
    this._secondarySprite = undefined;
    this._targetSprite = undefined;

    // Set up variables for drag events
    this.lastMousePos = null;

    // Global data
    globalData.world = this;
  }

  _makeBackground(width, height) {
    if(this._background !== undefined) {
      this.removeChild(this._background);
      this._background.clearRect(0, 0, this._background.width, this._background.height);
    }

    if(this._axes !== undefined) {
      this.removeChild(this._axes);
      this._axes.clearRect(0, 0, this._axes.width, this._axes.height);
    }

    /* Make background */
    this._background = new PIXI.Graphics();
    this.addChild(this._background)

    this._background.beginFill(this._backgroundColor)
    this._background.drawRect(0, 0, width, height);
    this._background.endFill();

    /* Make axes */
    this._axes = new PIXI.Graphics();
    this.addChild(this._axes);
    this._axes.lineStyle(1, 0x000000, 1);

    // Setup
    var numberStyle = new PIXI.TextStyle({
      fontFamily: 'Arial',
      fontSize: 12
    });

    // X Axis
    var text = new PIXI.Text('X', numberStyle);
    text.setTransform(this._axes.transform);
    text.anchor.set(0.5);
    text.x = width - 10;
    text.y = height / 2 + 10;
    this.addChild(text);

    this._axes.moveTo(0, height / 2);
    this._axes.lineTo(width, height / 2);

    // Y Axis
    var text = new PIXI.Text('Y', numberStyle);
    text.setTransform(this._axes.transform);
    text.anchor.set(0.5);
    text.x = width / 2 - 10;
    text.y = 10;
    this.addChild(text);

    this._axes.moveTo(width / 2, 0);
    this._axes.lineTo(width / 2, height);
  }

  set axes(a) {
    this.removeChild(this._axes);
    this._axes = a;
    this.addChild(a);
  }

  get background() {
    return this._background;
  }

  set background(b) {
    this.removeChild(this._background);
    this._background = b;
    this.addChild(b);
  }

  get primarySprite() {
    return this._primarySprite;
  }

  set primarySprite(s) {
    if(s === null) {
      if(this._primarySprite !== null) {
	this.removeChild(this._primarySprite);
      }

      this._primarySprite = null;
    }
    else {
      this.removeChild(this._primarySprite);
      this._primarySprite = s;
      this.addChild(s);
    }
  }

  get secondarySprite() {
    return this._secondarySprite;
  }

  set secondarySprite(s) {
    if(s === null) {
      if(this._secondarySprite !== null) {
	this.removeChild(this._secondarySprite);
      }

      this._secondarySprite = null;
    }
    else {
      this.removeChild(this._secondarySprite);
      this._secondarySprite = s;
      this.addChild(s);

      this.primarySprite = this.primarySprite;
    }
  }

  get targetSprite() {
    return this._targetSprite;
  }

  set targetSprite(s) {
    if(s === null) {
      if(this._targetSprite !== null) {
	this.removeChild(this._targetSprite);
      }

      this._targetSprite = null;
    }
    else {
      this.removeChild(this._targetSprite);
      this._targetSprite = s;
      this.addChild(s);

      this.secondarySprite = this.secondarySprite;
      this.primarySprite = this.primarySprite;
    }
  }

  get width() {
    return this._background.width;
  }

  set width(width) {
    if(this._background != undefined) {
      this._background.width = width;
    }
  }

  get height() {
    return this._background.height;
  }

  set height(height) {
    if(this._background != undefined) {
      this._background.height = height;
    }
  }

  getWorldSpaceMatrix() {
    var xScale = this.worldSpaceWidth / this.width;
    var yScale = -this.worldSpaceHeight / this.height;
    var xTrans = -this.worldSpaceWidth / 2;
    var yTrans = this.worldSpaceHeight / 2;

    return new PIXI.Matrix(xScale, 0, 0, yScale, xTrans, yTrans);
  }

  getPixelSpaceMatrix() {
    var xScale = this.width / this.worldSpaceWidth;
    var yScale = -this.height / this.worldSpaceHeight;
    var xTrans = this.width / 2;
    var yTrans = this.height / 2;

    return new PIXI.Matrix(xScale, 0, 0, yScale, xTrans, yTrans);
  }

  getPixelMousePosition(mouseData) {
    var data = mouseData.data;
    var pixelMousePos = new PIXI.Point(0, 0);
    data.getLocalPosition(this, pixelMousePos, data.global);

    return pixelMousePos;
  }

  getWorldMousePosition(mouseData) {
    var pixelMousePos = this.getPixelMousePosition(mouseData);
    var worldMousePos = new PIXI.Point(0, 0);
    this.getWorldSpaceMatrix().apply(pixelMousePos, worldMousePos);
    worldMousePos.x = Math.min(Math.max(-this.worldSpaceWidth / 2, worldMousePos.x), this.worldSpaceWidth / 2)
    worldMousePos.y = Math.min(Math.max(-this.worldSpaceHeight / 2, worldMousePos.y), this.worldSpaceHeight / 2)
    return worldMousePos;
  }

  pointerup(mouseData) {
    globalData.draggingSprite = false;
  }
}
