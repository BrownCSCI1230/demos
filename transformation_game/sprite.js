// Two types of methods: updating things it contains, returning things using what it contains
// Two more: event, draw (really fall into category of above two)
// Process: outline -> make draw -> make work

// 1) Updating child parameters based on width and height set
// 2) Get sprite transform based on type and value
// 3) Sprite transformation bag
//    - list of transformations (like queue)
//    - update events
// 4) World axes
// 5) World background and size
// 6) World draw
// 7) Sprite image
// 8) Sprite draw method (coloring image)
// 9) Sprite and world draw (different types as well)
// 10) Transformation block image
// 11) Transformation block draw (with bag)
// 12) Transformation queue image
// 13) Transformation queue and block draw
// 14) event methods
//    - set everything as interactive
//    - move transformation block, and update sprite transform (drag block, bag and queue)
//    - set active transform, and set visible sprite (click block, queue)
//    - update active transform, depends on whether or not secondary (move sprite, queue)
// 15) Win condition check on sprite move
// 16) Level Descriptions
//    - Target sprite matrix / transform
//    - List of transformations
//    - Construct level from description (attach to dom element)
//    - Callback on win to construct new level

PRIMARY_STYLE = 0;
SECONDARY_STYLE = 1;
TARGET_STYLE = 2;

class Sprite extends PIXI.Sprite {
  constructor({world = null, style = PRIMARY_STYLE}) {
    super();

    this.world = world;
    this.style = style;

    // TODO: overloaded draw method
  }

  setWorldSpaceTransform(transform) {
    // Get transforms to and from world space
    var toWorld = this.world.toWorldSpaceTransform();
    var toPixel = this.world.toPixelSpaceTransform();

    // Get cumulative transform
    toWorld.updateTransform(transform);
    toWorld.updateTransform(toPixel);
    transform = toWorld;
    matrix = transform.worldTransform();

    // Set cumulative transform
    this.setTransform(matrix.a, matrix.b, matrix.c, matrix.d, matrix.tx, matrix.ty);
  }

  setWorldSpaceTransform(a, b, c, d, tx, t) {
    // Get world space transform based on parameters
    var transform = new PIXI.Transform();
    transform.setTransformMatrix(a, b, c, d, tx, ty);

    // Call into existing method
    this.setWorldSpaceTransform(transform);
  }

  /*
  getWorldSpacePosition() {
    var world = this.world;
    var pixelSpaceSize = new PIXI.Point(world.width, world.height);
    var worldSpaceSize = world.worldSpaceSize;

    var pos = new PIXI.Point();
    pos.x = (this.position.x / world.width) * worldSpaceSize.x - worldSpaceSize.x / 2;
    pos.y = (this.position.y / world.height) * worldSpaceSize.y - worldSpaceSize.y / 2;
    return pos;
  }
  */

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

class SpriteTransformBlock extends PIXI.Container {
  constructor() {
    super();

    this.type = TRANSLATE_TYPE;
    this.subtype = SUBTYPE_1;
    this.value = 0;

    this.textObject = null;
    this.containerObject = null;
  }

  // TODO: update transformation
  set value(v) {
    this.value = v;
  }

  // TODO: have these setters modify display children
  set x(x) {
    this.x = x;
  }

  set y(y) {
    this.y = y;
  }

  set width(width) {
    this.width = width;
  }

  set height(height) {
    this.height = height;
  }

  set textObject(to) {
    this.removeChild(this.containerObject);
    this.containerObject = to;
    this.addChild(to);
  }

  set containerObject(co) {
    this.removeChild(this.containerObject);
    this.containerObject = co;
    this.addChild(co);
  }

  // TODO: populate based on type and parameters
  getSpriteTransformation() {
  }

  onSpriteDrag(curMouseData, lastMouseData) {
  }

  onClick(mouseData) {
  }

  onDrag(mouseData) {
  }
}

class SpriteTransformQueue extends PIXI.Container {
  constructor() {
    super();

    this.queue = [];
    this.activeTransform = null;
    this.textObject = null;
    this.containerObject = null;
  }

  // TODO: update position of transform blocks
  addSpriteTransform(i, tBlock) {
    this.queue.insert(i, tBlock);
    this.addChild(tBlock);
  }

  getTotalSpriteTransform() {
    var totalTransform = new PIXI.Transform();

    for(var tBlock : this.queue) {
      totalTransform.updateTransform(tBlock.getSpriteTransform());
    }

    return totalTransform;
  }

  getSpriteTransformUpToIndex(i) {
    var totalTransform = new PIXI.Transform();

    for(int i = 0; i <= i; i++) {
      totalTransform.updateTransform(this.queue[i].getSpriteTransform());
    }

    return totalTransform;
  }

  // TODO: have these setters modify display children
  set x(x) {
    this.x = x;
  }

  set y(y) {
    this.y = y;
  }

  set width(width) {
    this.width = width;
  }

  set height(height) {
    this.height = height;
  }

  set textObject(to) {
    this.removeChild(this.containerObject);
    this.containerObject = to;
    this.addChild(to);
  }

  set containerObject(co) {
    this.removeChild(this.containerObject);
    this.containerObject = co;
    this.addChild(co);
  }

  onSpriteDrag(curMouseData, lastMouseData) {
  }

  onClick(mouseData) {
  }

  onDrag(mouseData) {
  }
}

class World extends PIXI.Container {
  constructor() {
    super();

    // TODO: set world space size, possibly from arguments
    this.worldSpaceSize = new PIXI.Point(0, 0);

    // TODO: build axes and background (possibly from arguments)
    this.axes = null;
    this.background = null;

    this.primarySprite = null;
    this.secondarySprite = null;
    this.targetSprite = null;
  }

  set axes(a) {
    this.removeChild(this.axes);
    this.axes = a;
    this.addChild(a);
  }

  set background(b) {
    this.removeChild(this.background);
    this.axes = b;
    this.addChild(b);
  }

  set primarySprite(s) {
    this.removeChild(this.primarySprite);
    this.primarySprite = s;
    this.addChild(s);
  }

  set secondarySprite() {
    this.removeChild(this.secondarySprite);
    this.secondarySprite = s;
    this.addChild(s);
  }

  set targetSprite() {
    this.removeChild(this.targetSprite);
    this.targetSprite = s;
    this.addChild(s);
  }

  // TODO: have these setters modify display children
  set x(x) {
    this.x = x;
  }

  set y(y) {
    this.y = y;
  }

  set width(width) {
    this.width = width;
  }

  set height(height) {
    this.height = height;
  }

  getWorldSpaceTransform() {
    var xScale = this.worldSpaceSize.x / this.width;
    var yScale = -this.worldSpaceSize.y / this.height;
    var xTrans = -this.worldSpaceSize.x / 2;
    var yTrans = this.worldSpaceSize.y / 2;

    var toWorld = new PIXI.Transform();
    toWorld.setFromMatrix(new PIXI.Matrix(xScale, 0, 0, yScale, xTrans, yTrans));
    return toWorld;
  }

  getPixelSpaceTransform() {
    var xScale = this.width / this.worldSpaceSize.x;
    var yScale = -this.height / this.worldSpaceSize.y;
    var xTrans = this.width / 2;
    var yTrans = this.height / 2;

    var toPixel = new PIXI.Transform();
    toPixel.setFromMatrix(new PIXI.Matrix(xScale, 0, 0, yScale, xTrans, yTrans));
    return toPixel;
  }

  onClick(mouseData) {
  }

  onDrag(mouseData) {
  }
}
