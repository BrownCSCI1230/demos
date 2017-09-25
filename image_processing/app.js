$(function() {
  // HTML elements
  var mandrillCanvas = document.getElementById("mandrillCanvas");
  var pixelCanvas = document.getElementById("pixelCanvas");
  var filteredCanvas1 = document.getElementById("filteredCanvas1");
  var filteredCanvas2 = document.getElementById("filteredCanvas2");
  var image = document.getElementById("mandrill");
  var imageWidth = 256;
  var imageHeight = 256;

  var graph1 = new Graph(document.getElementById("graph1"), 256, 256, -1, 1, 1, 0, 1, 1, "", false);
  var graph2 = new Graph(document.getElementById("graph2"), 256, 256, -1, 1, 1, 0, 1, 1, "", false);

  // Pixi objects
  var renderer = PIXI.autoDetectRenderer({
    width: 1000,
    height: 300,
    view: mandrillCanvas,
    backgroundColor: 0xFFFFFF
  });
  var stage = new PIXI.Container;
  var marquee = new PIXI.Graphics;
  var mandrill = new Image();
  var mandrillSprite = new PIXI.Sprite;
  mandrill.src = 'mandrill.png'
  mandrill.onload = function(){
    setUpSprite();
    setUpMarquee();
    setUpInteractions();
    renderer.render(stage);
  }

  // Interaction variables
  var dragging = false;
  var dragStartPos = new PIXI.Point(0, 0);

  function setUpSprite() {
    var texture = new PIXI.Texture(new PIXI.BaseTexture(mandrill));
    mandrillSprite = new PIXI.Sprite(texture);
    mandrillSprite.width = imageWidth;
    mandrillSprite.height = imageHeight;
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
    renderer.render(stage);

    dragging = true;
    dragStartPos = new PIXI.Point(mousePos.x, mousePos.y);
  }

  function dragImage(eventData) {
    if (dragging) {
      var data = eventData.data;
      var mousePos = new PIXI.Point(0, 0);
      data.getLocalPosition(stage, mousePos, data.global);

      // Stop dragging if mouse outside of image
      if (mousePos.x < stage.x || mousePos.x > stage.x + stage.width ||
        mousePos.y < stage.y || mousePos.y > stage.y + stage.height) {
          mousePos.x = Math.min(stage.x + stage.width, Math.max(stage.x, mousePos.x));
          mousePos.y = Math.min(stage.y + stage.height, Math.max(stage.y, mousePos.y));
        }

        var x, y;
        var width, height;

        if (mousePos.x <= dragStartPos.x) {
          width = dragStartPos.x - mousePos.x;
          x = mousePos.x;
        } else {
          width = mousePos.x - dragStartPos.x;
          x = dragStartPos.x;
        }
        if (mousePos.y <= dragStartPos.y) {
          height = dragStartPos.y - mousePos.y;
          y = mousePos.y;
        } else {
          height = mousePos.y - dragStartPos.y;
          y = dragStartPos.y;
        }

        // Draw marquee
        marquee.clear();
        marquee.lineStyle(2, 0x000000, 1);
        marquee.drawRect(0, 0, width, height);

        // don't know why these need to be set twice, but it doesn't work otherwise
        marquee.x = x;
        marquee.y = y;
        marquee.width = width;
        marquee.height = height;

        // Render stage
        renderer.render(stage);
      }
    }

    function stopDragging(eventData) {
      dragging = false;
      displayFilteredSelection(getPixelsUnderMarquee(), filteredCanvas1, graph1);
      displayFilteredSelection(getPixelsUnderMarquee(), filteredCanvas2, graph2);
    }

    function displayFilteredSelection(imageData, canvas, graph) {
      var scaledData = scaleImageData(imageData, canvas.width, canvas.height, graph);

      canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);
      canvas.getContext('2d').putImageData(scaledData, 0, 0);
    }

    function scaleImageData(imageData, targetWidth, targetHeight, graph) {
      return scaleImageDataY(scaleImageDataX(imageData, targetWidth, graph), targetHeight, graph);
    }

    function scaleImageDataY(imageData, targetHeight, graph) {
      var sourceWidth = imageData.width;
      var sourceHeight = imageData.height;
      var data = imageData.data;
      var scaledData = new Uint8ClampedArray(4 * sourceWidth * targetHeight);

      // Scale the image
      for(var col = 0; col < sourceWidth; col++) {
        for(var row = 0; row < targetHeight; row++) {
          var index = row * sourceWidth + col;

          for(var channel = 0; channel <= 2; channel++) {
            scaledData[index * 4 + channel] =
            getTargetValueFromSourceY(imageData, row, col, channel, targetHeight, sourceHeight, graph);
          }

          scaledData[index * 4 + 3] = 255;
        }
      }

      var scaledImageData = new ImageData(scaledData, sourceWidth, targetHeight);
      return scaledImageData;
    }

    function getTargetValueFromSourceY(imageData, targetRow, targetCol, channel, targetHeight, sourceHeight, graph) {
      var sourceCol = targetCol;
      var sourceWidth = imageData.width;
      var scaleFactor = targetHeight / sourceHeight;
      var centerSourceRow = (targetRow + 0.5) * (1 / scaleFactor) - 0.5;

      var filterRadius = scaleFactor >= 1 ? 1 : 1 / scaleFactor;
      var filterStart = Math.floor(Math.max(centerSourceRow - filterRadius, 0));
      var filterEnd = Math.ceil(Math.min(centerSourceRow + filterRadius, sourceHeight - 1));

      var channelSum = 0;
      var filterSum = 0;

      for(var sourceRow = filterStart; sourceRow <= filterEnd; sourceRow++) {
        var filterValue = Math.max(graph.getGraphData(centerSourceRow - sourceRow), 0);
        var index = (sourceRow * sourceWidth + sourceCol) * 4 + channel;

        channelSum += imageData.data[index] * filterValue;
        filterSum += filterValue;
      }

      return graph.getGraphArea() * channelSum / filterSum;
    }

    function scaleImageDataX(imageData, targetWidth, graph) {
      var sourceWidth = imageData.width;
      var sourceHeight = imageData.height;
      var data = imageData.data;
      var scaledData = new Uint8ClampedArray(4 * targetWidth * sourceHeight);

      // Scale the image
      for(var row = 0; row < sourceHeight; row++) {
        for(var col = 0; col < targetWidth; col++) {
          var index = row * targetWidth + col;

          for(var channel = 0; channel <= 2; channel++) {
            scaledData[index * 4 + channel] =
            getTargetValueFromSourceX(imageData, row, col, channel, targetWidth, sourceWidth, graph);
          }

          scaledData[index * 4 + 3] = 255;
        }
      }

      var scaledImageData = new ImageData(scaledData, targetWidth, sourceHeight);
      return scaledImageData;
    }

    function getTargetValueFromSourceX(imageData, targetRow, targetCol, channel, targetWidth, sourceWidth, graph) {
      var sourceRow = targetRow;
      var scaleFactor = targetWidth / sourceWidth;
      var centerSourceCol = (targetCol + 0.5) * (1 / scaleFactor) - 0.5;

      var filterRadius = scaleFactor >= 1 ? 1 : 1 / scaleFactor;
      var filterStart = Math.floor(Math.max(centerSourceCol - filterRadius, 0));
      var filterEnd = Math.ceil(Math.min(centerSourceCol + filterRadius, sourceWidth - 1));

      var channelSum = 0;
      var filterSum = 0;

      for(var sourceCol = filterStart; sourceCol <= filterEnd; sourceCol++) {
        var filterValue = graph.getGraphData(centerSourceCol - sourceCol);
        var index = (sourceRow * sourceWidth + sourceCol) * 4 + channel;

        channelSum += imageData.data[index] * filterValue;
        filterSum += filterValue;
      }

      return graph.getGraphArea() * channelSum / filterSum;
    }

    function getPixelsUnderMarquee() {
      pixelCanvas.getContext('2d').drawImage(image, 0, 0, imageWidth, imageHeight);
      var imageData = pixelCanvas.getContext('2d').getImageData(marquee.x, marquee.y,
		      marquee.width, marquee.height);
      pixelCanvas.getContext('2d').clearRect(0, 0, pixelCanvas.width, pixelCanvas.height);
      pixelCanvas.getContext('2d').putImageData(imageData, 0, 0);
      return imageData;
    }
  });
