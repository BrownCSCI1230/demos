$(function() {
   var cvs = document.getElementById("theCanvas");
   var ctx = cvs.getContext("2d");
   var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: [1, 2, 3, 4, 5, 6],
      datasets: [{
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
              'rgba(0, 0, 255, 1)',
          ],
          borderColor: [
              'rgba(0,0,255,1)',
          ],
          borderWidth: 1
      }]
    },
    options: {
      scales: {
          xAxes: [{
              ticks:{
                  stepSize : 10,

              },
              stacked: true,
            gridLines: {
                    lineWidth: 0,
                    color: "rgba(255,255,255,0)"
                }
          }],
          yAxes: [{

              stacked: true,
               ticks: {
                  min: 0,
                  stepSize: 1,
              }

          }]
      }
    }
  });
});
