/* Ideal paraxial thin lens. All physical distances are in millimeters. */
'use strict';
const $ = id => document.getElementById(id);
// Default sensor position: the 50 mm lens focuses at 600 mm.
const DEFAULT_SENSOR_DISTANCE = 50 * 600 / (600 - 50);
const state = { f: 50, D: 25, u: 600, s: DEFAULT_SENSOR_DISTANCE };
const lensX = 600, axisY = 245, objectScale = 0.5, imageScale = 2.5, heightScale = 3;
function optics({ f, D, u, s = DEFAULT_SENSOR_DISTANCE }) {
  const focus = f * s / (s - f);
  const v = f * u / (u - f);
  return { s, v, focus, c: D * Math.abs(1 - s / v) };
}
// Keep the focus plane within the illustrated object-distance range.
function sensorLimits(f) {
  return { min: f * 1000 / (1000 - f), max: f * 200 / (200 - f) };
}
const line = (x1,y1,x2,y2,color,width=1.5,extra='') => `<line x1="${x1}" y1="${y1}" x2="${x2}" y2="${y2}" stroke="${color}" stroke-width="${width}" ${extra}/>`;
function update() {
  const { f, D, u } = state, { s, v, focus, c } = optics(state);
  const px = lensX - u * objectScale, sx = lensX + s * imageScale, vx = lensX + v * imageScale;
  const radius = D / 2 * heightScale, inFocus = c < 1e-9;
  const focusX = lensX - focus * objectScale;
  $('focus-plane').setAttribute('d', `M${focusX} 78V373`);
  $('focus-label').setAttribute('x', focusX);
  $('focus-label').textContent = `Plane in focus (${focus.toFixed(1)} mm)`;
  const limits = sensorLimits(f);
  $('focus').min = limits.min; $('focus').max = limits.max; $('focus').value = s;
  $('focus-value').textContent = `${s.toFixed(2)} mm`;
  $('focus-min').textContent = `${limits.min.toFixed(2)} mm · farther focus`;
  $('focus-max').textContent = `${limits.max.toFixed(2)} mm · nearer focus`;
  $('focal').value=f; $('diameter').value=D; $('distance').value=u;
  // Optical power is proportional to 1/f. Shading is a symbolic cue,
  // not a simulation of absorption or a separately specified refractive index.
  const strength = Math.max(0, Math.min(1, (1/f - 1/51.5) / (1/43 - 1/51.5)));
  $('glass-left').setAttribute('stop-color', `hsl(177, 45%, ${80 - 48*strength}%)`);
  $('glass-center').setAttribute('stop-color', `hsl(177, 48%, ${94 - 40*strength}%)`);
  $('glass-right').setAttribute('stop-color', `hsl(177, 45%, ${74 - 48*strength}%)`);
  $('focal-value').textContent=`${f} mm`; $('diameter-value').textContent=`${D} mm`;
  $('distance-value').textContent=`${Number(u.toFixed(1))} mm`; $('f-number').textContent=`f/${(f/D).toFixed(1)}`;
  $('object-point').setAttribute('transform',`translate(${px} ${axisY})`);
  $('object-point').setAttribute('aria-valuenow',u);
  $('object-point').setAttribute('aria-valuetext',`${u} millimeters from lens`);
  $('object-label').setAttribute('x',px);
  // Continue rays beyond their convergence to make front/back focus visible.
  const endX = Math.min(1155, Math.max(sx,vx)+60);
  const endY = h => axisY + h * (1 - (endX-lensX)/(v*imageScale));
  let rays = `<path d="M${px} ${axisY}L${lensX} ${axisY-radius}L${endX} ${endY(-radius)}L${endX} ${endY(radius)}L${lensX} ${axisY+radius}Z" fill="#7773cb" opacity=".07"/>`;
  for(let i=-4;i<=4;i++) {
    const h = radius*i/4, color = i===0 ? '#ce9b42' : '#7773cb';
    rays += line(px,axisY,lensX,axisY+h,color,i===0?2:1.4);
    rays += line(lensX,axisY+h,endX,endY(h),color,i===0?2:1.4);
  }
  $('rays').innerHTML=rays;
  $('aperture').innerHTML=line(lensX,100,lensX,axisY-radius,'#36535c',7)+line(lensX,axisY+radius,lensX,390,'#36535c',7)+line(642,axisY-radius,642,axisY+radius,'#418b91',1.5)+line(636,axisY-radius,648,axisY-radius,'#418b91')+line(636,axisY+radius,648,axisY+radius,'#418b91');
  $('sensor').innerHTML=line(sx,85,sx,350,'#34464e',7)+line(sx,axisY-c/2*heightScale,sx,axisY+c/2*heightScale,'#f2ad4c',9)+`<text x="${sx}" y="72" text-anchor="middle" class="label">Sensor</text>`;
  $('image-point').innerHTML=`<circle cx="${vx}" cy="${axisY}" r="4.5" fill="${inFocus?'#21806a':'#7773cb'}"/><text x="${vx>990?vx-9:vx+9}" y="${axisY+23}" text-anchor="${vx>990?'end':'start'}" class="muted">Ray convergence</text>`;
  const fx=lensX+f*imageScale;
  $('focal-mark').innerHTML=line(fx,axisY-6,fx,axisY+6,'#70838a')+`<text x="${fx}" y="${axisY-14}" text-anchor="middle" class="muted">F</text>`;
  $('distances').innerHTML=line(px,445,lensX,445,'#a7b8b2',1)+line(px,439,px,451,'#a7b8b2')+line(lensX,439,lensX,451,'#a7b8b2')+`<text x="${(px+lensX)/2}" y="467" text-anchor="middle">u = ${Number(u.toFixed(1))} mm</text>`+line(lensX,445,sx,445,'#a7b8b2')+line(sx,439,sx,451,'#a7b8b2')+`<text x="${(lensX+sx)/2}" y="435" text-anchor="middle" class="muted">s = ${s.toFixed(1)} mm</text>`;
  $('focus-status').textContent=inFocus?'In focus':u<focus?'Object nearer':'Object farther';
  $('focus-status').classList.toggle('defocused',!inFocus);
  $('coc').textContent=c.toFixed(2);
  $('spot-description').textContent=inFocus?'All rays meet at a single point.':'The sensor cuts through the ray cone, recording a disk.';
  drawSpot(c,inFocus);
}
function drawSpot(c,inFocus) {
  const canvas=$('spot'),ctx=canvas.getContext('2d'),n=600;
  ctx.fillStyle='#14252b';ctx.fillRect(0,0,n,n);
  ctx.strokeStyle='#273940';ctx.lineWidth=1;
  for(let i=0;i<=n;i+=75){ctx.beginPath();ctx.moveTo(i,0);ctx.lineTo(i,n);ctx.moveTo(0,i);ctx.lineTo(n,i);ctx.stroke();}
  ctx.strokeStyle='#56716e';ctx.setLineDash([5,8]);ctx.beginPath();ctx.moveTo(300,30);ctx.lineTo(300,570);ctx.moveTo(30,300);ctx.lineTo(570,300);ctx.stroke();ctx.setLineDash([]);
  const r=Math.max(2,c/40*n/2);
  ctx.beginPath();ctx.arc(300,300,r,0,2*Math.PI);ctx.fillStyle=inFocus?'#d7ffe9':'#f4c879';ctx.fill();
  if(!inFocus){ctx.strokeStyle='#ffe6b5';ctx.lineWidth=2;ctx.stroke();}
  ctx.strokeStyle='#a2b7b8';ctx.lineWidth=2;ctx.beginPath();ctx.moveTo(435,550);ctx.lineTo(510,550);ctx.moveTo(435,545);ctx.lineTo(435,555);ctx.moveTo(510,545);ctx.lineTo(510,555);ctx.stroke();ctx.fillStyle='#a2b7b8';ctx.font='20px system-ui';ctx.textAlign='center';ctx.fillText('5 mm',472,535);
  canvas.setAttribute('aria-label',`Sensor footprint: ${c.toFixed(2)} millimeter diameter${inFocus?', in focus':''}.`);
}
for(const [id,key] of [['focal','f'],['focus','s'],['diameter','D'],['distance','u']]) $(id).addEventListener('input',e=>{state[key]=Number(e.target.value);
  if(key==='f') {
    const {min,max}=sensorLimits(state.f);
    state.s=Math.max(min,Math.min(max,state.s));
  }
  update();});
$('refocus').addEventListener('click',()=>{state.u=optics(state).focus;update();});
$('focus-on-point').addEventListener('click',()=>{state.s=optics(state).v;update();});
$('reset').addEventListener('click',()=>{Object.assign(state,{f:50,D:25,u:600,s:DEFAULT_SENSOR_DISTANCE});update();});
const point=$('object-point'),svg=$('diagram');let dragging=false;
function drag(e){const p=new DOMPoint(e.clientX,e.clientY).matrixTransform(svg.getScreenCTM().inverse());state.u=Math.round(Math.max(200,Math.min(1000,(lensX-p.x)/objectScale)));update();}
point.addEventListener('pointerdown',e=>{e.preventDefault();dragging=true;point.setPointerCapture(e.pointerId);point.focus();drag(e);});
point.addEventListener('pointermove',e=>{if(dragging)drag(e);});
point.addEventListener('pointerup',()=>{dragging=false;});point.addEventListener('pointercancel',()=>{dragging=false;});point.addEventListener('lostpointercapture',()=>{dragging=false;});
point.addEventListener('keydown',e=>{const delta={ArrowLeft:10,ArrowRight:-10,ArrowUp:10,ArrowDown:-10}[e.key];if(delta!==undefined||e.key==='Home'||e.key==='End'){e.preventDefault();state.u=e.key==='Home'?200:e.key==='End'?1000:Math.max(200,Math.min(1000,state.u+delta*(e.shiftKey?10:1)));update();}});
update();
