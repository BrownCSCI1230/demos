'use strict';
const {project,divide,clip,geometry,planes}=ClipMath;
const $=id=>document.getElementById(id);
const inputs=['tip','base','height','near','far'];
let yaw=-.58,pitch=.28, drag=null;
const presets={crossing:[1.5,-3,1.2,1,8],front:[-3,-3,1.2,1,8],behind:[2,2,1.2,1,8],far:[-11,-11,1.2,1,8]};
const fmt=n=>Math.abs(n)<.00005?'0.00':n.toFixed(2);
const vec=p=>'('+p.map(fmt).join(', ')+')';
function makeRenderer(id) {
  const canvas=$(id),gl=canvas.getContext('webgl',{antialias:true,alpha:false,preserveDrawingBuffer:true});
  if(!gl) throw Error('WebGL is unavailable. Enable hardware acceleration or try a browser that supports WebGL.');
  const vs=gl.createShader(gl.VERTEX_SHADER);gl.shaderSource(vs,`attribute vec4 position; uniform float divideFirst; void main(){gl_Position=divideFirst>.5?vec4(position.xyz/position.w,1.0):position;gl_PointSize=6.0;}`);gl.compileShader(vs);
  const fs=gl.createShader(gl.FRAGMENT_SHADER);gl.shaderSource(fs,'precision mediump float; uniform vec4 color; void main(){gl_FragColor=color;}');gl.compileShader(fs);
  for(const s of [vs,fs]) if(!gl.getShaderParameter(s,gl.COMPILE_STATUS)) throw Error(gl.getShaderInfoLog(s));
  const program=gl.createProgram();gl.attachShader(program,vs);gl.attachShader(program,fs);gl.linkProgram(program);
  if(!gl.getProgramParameter(program,gl.LINK_STATUS)) throw Error(gl.getProgramInfoLog(program));
  const buf=gl.createBuffer(),pos=gl.getAttribLocation(program,'position'),col=gl.getUniformLocation(program,'color'),early=gl.getUniformLocation(program,'divideFirst');
  function clear(){const dpr=Math.min(devicePixelRatio||1,2);canvas.width=Math.round(canvas.clientWidth*dpr);canvas.height=Math.round(canvas.clientHeight*dpr);gl.viewport(0,0,canvas.width,canvas.height);gl.clearColor(.975,.982,.992,1);gl.clear(gl.COLOR_BUFFER_BIT);gl.disable(gl.DEPTH_TEST);gl.disable(gl.CULL_FACE);gl.enable(gl.BLEND);gl.blendFunc(gl.SRC_ALPHA,gl.ONE_MINUS_SRC_ALPHA);gl.useProgram(program);gl.bindBuffer(gl.ARRAY_BUFFER,buf);gl.enableVertexAttribArray(pos);gl.vertexAttribPointer(pos,4,gl.FLOAT,false,0,0);}
  function draw(points,color,mode=gl.TRIANGLE_FAN,divideFirst=false){if(!points.length)return;gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(points.flat()),gl.DYNAMIC_DRAW);gl.uniform4fv(col,color);gl.uniform1f(early,divideFirst?1:0);gl.drawArrays(mode,0,points.length);}
  return {canvas,gl,clear,draw};
}
let naive,correct,cube;
try {naive=makeRenderer('naive');correct=makeRenderer('correct');cube=makeRenderer('cube');}catch(e){$('gl-error').textContent=e.message;}
function image(renderer,points,color,early,singular){
  if(!renderer)return;
  const {gl,draw}=renderer;renderer.clear();
  // Keep the projected image square, even when its containing panel is wide.
  const size=Math.min(renderer.canvas.width,renderer.canvas.height);
  gl.viewport((renderer.canvas.width-size)/2,(renderer.canvas.height-size)/2,size,size);
  const grid=[];for(let t=-1;t<=1.01;t+=.25)grid.push([-1,t,0,1],[1,t,0,1],[t,-1,0,1],[t,1,0,1]);
  draw(grid,[.84,.88,.93,1],gl.LINES);
  if(!singular||!early){draw(points,[...color,.36],gl.TRIANGLES,early);draw(points,[...color,1],gl.LINE_LOOP,early);}
  draw([[-.995,-.995,0,1],[.995,-.995,0,1],[.995,.995,0,1],[-.995,.995,0,1]],[.57,.65,.75,1],gl.LINE_LOOP);
}
function observer(p) {
  const c=Math.cos(yaw),s=Math.sin(yaw),cp=Math.cos(pitch),sp=Math.sin(pitch);
  const x=c*p[0]+s*p[2],z=-s*p[0]+c*p[2];
  const aspect=cube.canvas.clientWidth/cube.canvas.clientHeight;
  return [.50*x/aspect,.50*(cp*p[1]-sp*z),.05*(sp*p[1]+cp*z),1];
}
function inspection(ndc,clipped,singular){
  if(!cube)return;const {gl,draw}=cube;cube.clear();
  const corners=[];for(let z=-1;z<=1;z+=2)for(let y=-1;y<=1;y+=2)for(let x=-1;x<=1;x+=2)corners.push([x,y,z]);
  const edges=[];for(let i=0;i<8;i++)for(let j=i+1;j<8;j++)if(corners[i].filter((v,k)=>v!==corners[j][k]).length===1)edges.push(observer(corners[i]),observer(corners[j]));
  draw(edges,[.58,.66,.77,.65],gl.LINES);
  if($('show-naive').checked&&!singular){const pts=ndc.map(observer);draw(pts,[.85,.45,.22,.25]);draw(pts,[.85,.45,.22,1],gl.LINE_LOOP);draw(pts,[.85,.45,.22,1],gl.POINTS);}
  if($('show-correct').checked&&clipped.length){const pts=clipped.map(divide).map(observer);draw(pts,[.07,.51,.42,.45]);draw(pts,[.07,.51,.42,1],gl.LINE_LOOP);draw(pts,[.07,.51,.42,1],gl.POINTS);}
  const labels=[['z = +1 · far',[0,-1,1]],['z = −1 · near',[0,-1,-1]],['x',[1.25,0,0]],['y',[0,1.25,0]]];
  $('cube-labels').innerHTML=labels.map(([name,p])=>{const q=observer(p);return `<span class="cube-label" style="left:${(q[0]+1)*50}%;top:${(1-q[1])*50}%">${name}</span>`;}).join('');
}
function eyeView(points,near,far){
  const x=z=>418+z*25,y=v=>145-v*18;
  const poly=ps=>ps.map(p=>`${x(p[2])},${y(p[1])}`).join(' ');
  $('eye').innerHTML=`<defs><clipPath id="plot"><rect x="20" y="36" width="580" height="221"/></clipPath><pattern id="eye-grid" width="25" height="18" patternUnits="userSpaceOnUse"><path d="M25 0H0V18" fill="none" stroke="#e9edf3" stroke-width=".6"/></pattern></defs>
  <rect x="20" y="36" width="580" height="221" fill="url(#eye-grid)"/>
  <g clip-path="url(#plot)"><path d="M${x(-near)} ${y(near)}L${x(-far)} ${y(far)}V${y(-far)}L${x(-near)} ${y(-near)}Z" fill="#e1e4e8" stroke="#9ba3af"/>
  <path d="M${x(0)} 36V257" stroke="#8594a9" stroke-dasharray="4 4"/><path d="M20 ${y(0)}H600" stroke="#bec9d7"/>
  <path d="M${x(-near)} 36V257 M${x(-far)} 36V257" stroke="#9ba3af" stroke-dasharray="4 4"/>
  <path d="M${x(0)} ${y(0)}L${x(-far)} ${y(far)} M${x(0)} ${y(0)}L${x(-far)} ${y(-far)}" stroke="#b7c9d3" stroke-dasharray="3 5"/>
  <polyline points="${poly([points[0],points[2]])}" stroke="#da7438" stroke-width="3"/>
  ${[points[0],points[2]].map((p,i)=>`<circle cx="${x(p[2])}" cy="${y(p[1])}" r="5" fill="${p[2]>0?'#da7438':'#334f70'}"/><text x="${x(p[2])+9}" y="${y(p[1])-10}" font-size="12" font-weight="700" fill="#45566e">${i?'C':'A, B'}</text>`).join('')}</g>
  <g font-family="system-ui" font-size="11" fill="#6e8096"><text x="24" y="22">IN FRONT · z &lt; 0</text><text x="475" y="22">BEHIND · z &gt; 0</text><text x="${x(-far)}" y="279" text-anchor="middle">far · −${fmt(far)}</text><text x="${x(-near)}" y="279" text-anchor="middle">near · −${fmt(near)}</text><text x="${x(0)}" y="297" text-anchor="middle">camera · z = 0</text><text x="105" y="157">← viewing direction</text></g><path d="M${x(0)-8} ${y(0)}l16 -8v16Z" fill="#334f70"/>`;
}
function update(){
  const vals=Object.fromEntries(inputs.map(id=>[id,Number($(id).value)]));
  inputs.forEach(id=>$(id+'-value').textContent=fmt(vals[id]));
  const points=geometry(vals.base,vals.tip,vals.height),clips=points.map(p=>project(p,vals.near,vals.far)),clipped=clip(clips);
  const singular=clips.some(p=>Math.abs(p[3])<1e-7),ndc=singular?[]:clips.map(divide);
  eyeView(points,vals.near,vals.far);image(naive,clips,[.85,.45,.22],true,singular);image(correct,clips,[.07,.51,.42],false,singular);inspection(ndc,clipped,singular);
  $('coordinates').innerHTML=points.map((p,i)=>{const c=clips[i],inside=planes.every(plane=>plane(c)>=-1e-9);return `<tr><td>${'ABC'[i]}</td><td>${vec(p)}</td><td>${vec(c)}</td><td>${Math.abs(c[3])<1e-7?'undefined · w = 0':vec(divide(c))}</td><td class="${inside?'pass':'fail'}">${inside?'Inside':c[3]<0?'Outside · w < 0':'Outside'}</td></tr>`;}).join('');
  $('singularity').textContent=singular?'At w = 0 the perspective divide is undefined. The divide-first drawing is paused; the correct pipeline still clips normally.':`Clipping the original triangle produces ${clipped.length?`a polygon with ${clipped.length} vertices`:'no visible polygon'}. All coordinates use OpenGL’s −1 to +1 depth convention.`;
  const behind=points.filter(p=>p[2]>0).length,crosses=Math.min(...points.map(p=>p[2]))<0&&Math.max(...points.map(p=>p[2]))>0;
  let title,text;
  if(singular){title='At the singularity';text='A vertex lies on the camera plane. Its w is zero, so division has no finite result. Clipping first removes the invalid region before division.';}
  else if(crosses){title='The triangle crosses w = 0';text='The orange triangle reconnects divided endpoints across infinity. Its false finite interior can enter the cube. The green polygon keeps only the original triangle’s intersection with the frustum.';}
  else if(behind===3){title='Triangle entirely behind the camera';text='All three vertices have negative clip-space w and NDC z > 1. Both paths reject the entire triangle, so both image panels are empty.';}
  else if(!clipped.length){title='Outside the view volume';text='This triangle does not intersect the frustum. With all w positive, division preserves the relevant half-space tests; both images are empty.';}
  else{title='Triangle remains in front of the camera';text='With positive w throughout the triangle, projecting and clipping its divided polygon gives the same shape here. Move C behind the camera to expose the failure.';}
  $('status-title').textContent=title;$('status').textContent=text;
}
function preset(name){inputs.forEach((id,i)=>$(id).value=presets[name][i]);document.querySelectorAll('[data-preset]').forEach(b=>b.classList.toggle('active',b.dataset.preset===name));update();}
inputs.forEach(id=>$(id).addEventListener('input',()=>{document.querySelectorAll('[data-preset]').forEach(b=>b.classList.remove('active'));update();}));
document.querySelectorAll('[data-preset]').forEach(b=>b.addEventListener('click',()=>preset(b.dataset.preset)));
$('reset').addEventListener('click',()=>preset('crossing'));
for(const id of ['show-naive','show-correct'])$(id).addEventListener('change',update);
$('reset-view').addEventListener('click',()=>{yaw=-.58;pitch=.28;update();});
$('cube').addEventListener('pointerdown',e=>{drag=[e.clientX,e.clientY];e.currentTarget.setPointerCapture(e.pointerId);});
$('cube').addEventListener('pointermove',e=>{if(!drag)return;yaw+=(e.clientX-drag[0])*.009;pitch=Math.max(-1.4,Math.min(1.4,pitch+(e.clientY-drag[1])*.009));drag=[e.clientX,e.clientY];update();});
for(const event of ['pointerup','pointercancel','lostpointercapture'])$('cube').addEventListener(event,()=>drag=null);
window.addEventListener('resize',update);update();
