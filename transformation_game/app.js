$(function() {
  var app = new PIXI.Application(1600, 750, {backgroundColor : 0x1099bb});
  document.body.appendChild(app.view);

  var world = new World(600, 525, 60, 40);
  world.x = 25;
  world.y = 25;

  var queue = new SpriteTransformQueue();
  queue.x = 25;
  queue.y = 600;

  var bag = new SpriteTransformBag();
  bag.x = 700;
  bag.y = 25;

  var block1 = new SpriteTransformBlock(ROTATE_TYPE);
  bag.addBlock(0, block1);

  var block2 = new SpriteTransformBlock(TRANSLATE_TYPE, SUBTYPE_1);
  bag.addBlock(0, block2);

  var block3 = new SpriteTransformBlock(TRANSLATE_TYPE, SUBTYPE_2);
  bag.addBlock(0, block3);

  var block4 = new SpriteTransformBlock(SCALE_TYPE, SUBTYPE_1);
  bag.addBlock(0, block4);

  var block5 = new SpriteTransformBlock(SCALE_TYPE, SUBTYPE_2);
  bag.addBlock(0, block5);

  var block6 = new SpriteTransformBlock(SHEAR_TYPE, SUBTYPE_1);
  bag.addBlock(0, block6);

  var block7 = new SpriteTransformBlock(SHEAR_TYPE, SUBTYPE_2);
  bag.addBlock(0, block7);

  var block8 = new SpriteTransformBlock(SHEAR_TYPE, SUBTYPE_1);
  bag.addBlock(0, block8);

  var sprite = new Sprite(PRIMARY_STYLE, world);
  world.primarySprite = sprite;
  sprite.setWorldSpaceTransform(new PIXI.Matrix());

  app.stage.addChild(bag);
  app.stage.addChild(queue);
  app.stage.addChild(world);
});
