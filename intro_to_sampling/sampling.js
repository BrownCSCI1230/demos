var canvas;
var graphCanvas;

var canvasWidth = 256;
var canvasHeight = 256;

var graphWidth = 562;
var graphHeight = 150;

var canvasCircleRadius = 2;
var graphCircleRadius = 2.5;

var graphPaddingTop = 50;
var graphPaddingBottom = 30;
var graphPaddingLeft = 62;
var graphPaddingRight = 30;

var numSamples = 32;

// Used when the page is first loaded
function setUpWidget() {
  canvas = document.getElementById("theCanvas");
  graphCanvas = document.getElementById("graphCanvas");

  // Draw image and graph
  var scanlineData = makeCanvas(canvasWidth, canvasHeight, canvasCircleRadius, numSamples, canvasHeight / 2, canvas.getContext("2d"));
  makeGraph(graphWidth, graphHeight, graphCircleRadius, numSamples, scanlineData, graphCanvas.getContext("2d"));

  // Set up slider and slider callbacks
  $( "#slider" ).slider({
    orientation: "vertical",
    min: 0,
    max: canvasHeight,
    value: canvasHeight / 2,
    slide: sliderDidMove
  });
}

// Used to make the image of the mandrill with the scanline at scanLinePos
// Returns the pixel intensities under the scanline circles as an array
function makeCanvas(imgWidth, imgHeight, circleRadius, numCircles, scanLinePos, context) {
  drawImage(circleRadius, 0, context);
  var imgData = context.getImageData(circleRadius, 0, imgWidth, imgHeight);
  var scanlineData = getPixelSamples(imgData.data, numCircles, scanLinePos, imgWidth);

  drawLineOverImage(circleRadius, scanLinePos, imgWidth, numCircles, circleRadius, context);
  return scanlineData;
}

function makeGraph(width, height, circleRadius, numSamples, scanlineData, c) {
  // Background 1
  c.beginPath();
  c.fillStyle = '#999999';
  c.fillRect(0, 0, width + graphPaddingLeft + graphPaddingRight, height + graphPaddingTop + graphPaddingBottom);

  // Background 2
  c.beginPath();
  c.fillStyle = '#FFFFFF';
  c.fillRect(graphPaddingLeft, graphPaddingTop, width, height);

  // Grid lines
  var startX = graphPaddingLeft;
  var startY = graphPaddingTop;

  for(var y = 0; y < height; y += height / 4) {
    c.beginPath();
    c.strokeStyle = '#BFBFBF';
    c.moveTo(startX, startY + y);
    c.lineTo(startX + width, startY + y);
    c.stroke();
  }

  var xStep = width / numSamples;

  for(var x = 0; x < width; x += xStep) {
    c.beginPath();
    c.moveTo(startX + x, startY);
    c.lineTo(startX + x, startY + height);
    c.stroke();
  }

  // Text
  for(var y = 0; y <= 1; y += 0.25) {
    c.beginPath();
    c.lineWidth = 1;
    c.fillStyle = '#000000';
    c.font = "15px Arial";
    c.fillText(y.toFixed(2), 10, graphPaddingTop + height - y * height);
  }

  // Outline
  c.beginPath();
  c.lineWidth = 1;
  c.strokeStyle = '#000000';
  c.strokeRect(graphPaddingLeft, graphPaddingTop, width, height);

  // Graph data points
  scanlineData.forEach(function(d, i, arr) {
    c.beginPath();
    c.lineWidth = 1;
    c.strokeStyle = '#0000FF';
    c.moveTo(startX + i * xStep, startY + height);
    c.lineTo(startX + i * xStep, startY + height - d * height);
    c.stroke();

    c.beginPath();
    c.strokeStyle = '#0000FF';
    c.arc(startX + i * xStep, startY + height - d * height, circleRadius, 0, 2 * Math.PI, false);
    c.stroke();
  });
}

// Places the mandrill in the context
function drawImage(x, y, context) {
  var img = document.getElementById("mandrill");
  context.drawImage(img, x, y);
}

// Returns the pixel intensities under the scanline circles as an array
function getPixelSamples(data, numCircles, scanlineY, imgWidth) {
  var scanlineData = [];
  var spacing = imgWidth / numCircles;
  for (i = 0; i <= numCircles; i++) {
    var r = data[scanlineY * imgWidth * 4 + i * spacing * 4];
    var g = data[scanlineY * imgWidth * 4 + i * spacing * 4 + 1];
    var b = data[scanlineY * imgWidth * 4 + i * spacing * 4 + 2];
    if (r != g || r != b || g != b) {
      throw("error: image is not greyscale");
    }
    scanlineData.push(r / 256);
  }
  return scanlineData;
}

// Draws the scanline
function drawLineOverImage(x, y, imgWidth, numCircles, circleRadius, context) {
  var spacing = imgWidth / numCircles;
  for (i = 0; i <= numCircles; i++) {
    drawCircle(x + i * spacing, y, circleRadius, context);
  }
}

// Draws the circles for the scanline
function drawCircle(x, y, radius, context) {
  context.beginPath();
  context.arc(x, y, radius, 0, 2 * Math.PI, false);
  context.fillStyle = 'blue';
  context.fill();
  context.stroke();
}

function setUpSlider(imgWidth, imgHeight, circleRadius, numCircles, canvas) {
  var canvas = document.getElementById("theCanvas");
  var graphCanvas = document.getElementById("graphCanvas");

  $( "#slider" ).slider({
    orientation: "vertical",
    min: 0,
    max: imgHeight,
    value: imgHeight / 2,
    slide: function(event, ui) {
      sliderDidMove(event, ui, imgWidth, imgHeight, circleRadius, numCircles, canvas);
    }
  });
}

function sliderDidMove(eventSlider, uiSlider) {
  var context = canvas.getContext("2d");
  var graphContext = graphCanvas.getContext("2d");

  // Clear image and graph
  context.clearRect(0, 0, canvas.width, canvas.height);
  graphContext.clearRect(0, 0, graphCanvas.width, graphCanvas.height);

  var scanlineData = makeCanvas(canvasWidth, canvasHeight, canvasCircleRadius, numSamples, canvasHeight - uiSlider.value, canvas.getContext("2d"));
  makeGraph(graphWidth, graphHeight, graphCircleRadius, numSamples, scanlineData, graphCanvas.getContext("2d"));
}
