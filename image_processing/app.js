$(function() {

  var marquee = document.getElementById("marquee");
  marquee.style.position = "absolute";
  var marqueeClicked = false;
  var image = document.getElementById("mandrill");
  var canvas = document.getElementById("mandrillCanvas");

  image.onload = function() {
      canvas.getContext("2d").drawImage(image, 0, 0, canvas.width, canvas.height);
  }

  window.onload = function(){
    setMarquee(canvas.getBoundingClientRect().left, canvas.getBoundingClientRect().top);
    changeMarquee(256, 256);
  }

  function setMarquee(x, y) {
    console.log(x + ", " + y);
    marquee.style.left = x;
    marquee.style.top = y;
    marquee.width = 0;
    marquee.height = 0;
  }

  function changeMarquee(x, y) {
    if (marquee !== undefined) {
      marquee.style.right = marquee.getBoundingClientRect().left + Math.abs(x - marquee.getBoundingClientRect().left);
      marquee.style.bottom = marquee.getBoundingClientRect().top + Math.abs(y - marquee.getBoundingClientRect().top);
      if (x < marquee.getBoundingClientRect().left) {
        marquee.style.left = x;
      }

      if (y < marquee.getBoundingClientRect().top) {
        marquee.style.top = y;
      }
    }
    console.log("x: " + marquee.x + " y: " + marquee.y + " w: " + marquee.width + " h: " + marquee.height);
  }

  canvas.onmousedown = function() {
    x = MouseEvent.x;
    y = MouseEvent.y;
    if (x >= canvas.getBoundingClientRect().left &&
      x <= canvas.getBoundingClientRect().left + canvas.width &&
      y >= canvas.getBoundingClientRect().top &&
      y <= canvas.getBoundingClientRect().top + canvas.height) {
      setMarquee(x, y);
      marqueeClicked = true;
    }
  }

  canvas.onmousemove = function() {
    x = MouseEvent.x;
    y = MouseEvent.y;
    if (x >= canvas.getBoundingClientRect().left &&
      x <= canvas.getBoundingClientRect().left + canvas.width &&
      y >= canvas.getBoundingClientRect().top &&
      y <= canvas.getBoundingClientRect().top + canvas.height) {
      changeMarquee(x, y);
    }
  }

  canvas.onmouseup = function() {
    marqueeClicked = false;
  }
})
