// Two types of methods: updating things it contains, returning things using what it contains
// Two more: event, draw (really fall into category of above two)
// Process: outline -> make draw -> make work

// 15) Win condition check on sprite move
// 16) Level Descriptions
//    - Target sprite matrix / transform
//    - List of transformations
//    - Construct level from description (attach to dom element)
//    - Callback on win to construct new level
// 8) Sprite different images (correct sizes) and alphas
// 10) Transformation block image
// 12) Transformation queue image
// 13) Transformation queue and block draw
//
// TODO: animations
// TODO: switch between x and y translations / scales

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
    }
    else if(style === TARGET_STYLE) {
      texture = PIXI.Texture.fromImage('res/link.gif');
    }

    // Call parent class constructor
    super(texture);

    // Set as interactive
    this.interactive = true;
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
  constructor(type = TRANSLATE_TYPE, subtype = SUBTYPE_1) {
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
    this._color = 0xFF0000;
    this._makeBackground();
    this._makeTitle();
    this._makeSubtitle();

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
    this.subtitle.anchor.set(0.5, 0.5);
    this.subtitle.x = this.width / 2;
    this.subtitle.y = this.height / 2;
  }

  _makeBackground() {
    this.background = new PIXI.Graphics();
    this.background.beginFill(this.color)
    this.background.drawRect(0, 0, 75, 75);
    this.background.endFill();
  }

  makeContent() {
    this._makeTitle();
    this._makeSubtitle();
    this._makeBackground();
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

  get color() {
    return this._color;
  }

  set color(c) {
    this._color = c;
    this._makeBackground();
    this._makeTitle();
    this._makeSubtitle();
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
  }

  onSpriteDrag(lastMousePos, curMousePos) {
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
	if(lastMousePos.x === 0) {
	  this.value = 0;
	}
	else {
	  this.value = curMousePos.x / this.startSpriteDragMousePos.x;
	}
      }
      else if(this.subtype === SUBTYPE_2) {
	if(lastMousePos.x === 0) {
	  this.value = 0;
	}
	else {
	  this.value = curMousePos.y / this.startSpriteDragMousePos.y;
	}
      }
    }
    else if(this.type === SHEAR_TYPE) {
      if(this.subtype === SUBTYPE_1) {
	if(this.startSpriteDragMousePos.x !== 0) {
	  this.value = (curMousePos.y - this.startSpriteDragMousePos.y) / this.startSpriteDragMousePos.x;
	}
      }
      else if(this.subtype === SUBTYPE_2) {
	if(this.startSpriteDragMousePos.y !== 0) {
	  this.value = (curMousePos.x - this.startSpriteDragMousePos.x) / this.startSpriteDragMousePos.y;
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
  constructor(append = true) {
    super();

    // Set as interactive
    this.interactive = true;

    // Properties
    this.blocks = [];

    // UI
    this._makeBackground();
    this.blockWidth = this.width;
    this.blockHeight = this.blockWidth;

    // Global data
    if(append) {
      globalData.blockContainers.push(this);
    }
  }

  // TODO: update background
  _makeBackground() {
    this.background = new PIXI.Graphics();
    this.background.beginFill(0xFFFFFF)
    this.background.drawRect(0, 0, 75, 525);
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

    if(this.activeBlockIndex === -1 && this.blocks.length > 0) {
      this.activeBlockIndex = 0;
    }

    this.blocks.splice(i, 1);
    this.removeChild(block);
  }

  update() {
    for(var j = 0; j < this.blocks.length; j++) {
      var currBlock = this.blocks[j];
      currBlock.color = 0xFF0000;
      currBlock.x = 0;
      currBlock.y = this.blockHeight * j;
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
    return Math.min(this.blocks.length, Math.floor((pos.y - this.y) / this.blockHeight));
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
  constructor() {
    super(false);

    // Set as interactive
    this.interactive = true;

    // Set properties
    this.activeBlockIndex = -1;

    // UI
    this._makeBackground();
    this.blockWidth = this.height;
    this.blockHeight = this.blockWidth;

    // Global data
    globalData.blockContainers.push(this);
    globalData.blockQueue = this;
  }

  _makeBackground() {
    this.background = new PIXI.Graphics();
    this.background.beginFill(0xFFFFFF)
    this.background.drawRect(0, 0, 600, 75);
    this.background.endFill();
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
      currBlock.x = this.width - this.blockWidth * (j + 1);
      currBlock.y = 0;
    }

    if(this.activeBlockIndex !== -1) {
      this.blocks[this.activeBlockIndex].color = 0x00FF00;

      var transform = this.getSpriteTransformUpToIndex(this.activeBlockIndex);
      globalData.world.primarySprite.setWorldSpaceTransform(transform);
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
    return Math.min(this.blocks.length, Math.floor((this.width - (pos.x - this.x)) / this.blockWidth));
  }

  getBlockPosition(i) {
    return new PIXI.Point(this.width - (i + 1) * this.blockWidth, 0);
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
    this.removeChild(this._primarySprite);
    this._primarySprite = s;
    this.addChild(s);
  }

  get secondarySprite() {
    return this._secondarySprite;
  }

  set secondarySprite(s) {
    this.removeChild(this._secondarySprite);
    this._secondarySprite = s;
    this.addChild(s);
  }

  get targetSprite() {
    return this._targetSprite;
  }

  set targetSprite(s) {
    this.removeChild(this._targetSprite);
    this._targetSprite = s;
    this.addChild(s);
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
    return worldMousePos;
  }

  pointerup(mouseData) {
    globalData.draggingSprite = false;
  }
}
