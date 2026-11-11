'use strict';
// z is already in the canonical view volume. Include homogenization by w = -z.
function projectDepth(z, c) { return ((z - c) / (1 + c)) / (-z); }
if (typeof module !== 'undefined') module.exports = { projectDepth };
if (typeof document !== 'undefined') {
const $ = id => document.getElementById(id);
const plot = $('plot');
let near = 0.1, far = 1;
const X = z => 76 + (z + 1) * 748;
const Y = z => 54 - z * 408;
const fmt = n => Number(n.toPrecision(5)).toString();
const svg = (tag, attrs, text = '') => `<${tag} ${Object.entries(attrs).map(([k,v])=>`${k}="${v}"`).join(' ')}>${text}</${tag}>`;
function render() {
 const c = -near / far;
 $('c').textContent = fmt(c);
 let s = '<title id="plot-title">Depth after perspective division</title><desc id="plot-desc">Equal input depths compress near the far plane at minus one.</desc>';
 for(let i=0;i<=5;i++) {
  const z = -1+i/5;
  s += svg('line',{x1:76,y1:Y(z),x2:824,y2:Y(z),stroke:'#e2e8f0'});
  s += svg('line',{x1:X(z),y1:54,x2:X(z),y2:462,stroke:'#edf1f6'});
  s += svg('text',{x:X(z),y:490,'text-anchor':'middle'},fmt(z));
  s += svg('text',{x:61,y:Y(z)+5,'text-anchor':'end'},fmt(z));
 }
 s += svg('path',{d:'M76 54V462H824',fill:'none',stroke:'#9aaabd','stroke-width':1.5});
 s += svg('line',{x1:76,y1:462,x2:824,y2:54,stroke:'#a1afc0','stroke-dasharray':'6 6','stroke-width':1.5});
 // Log-spaced samples resolve the high curvature even for extreme far/near ratios.
 let path = '';
 for(let i=0;i<=1000;i++) {
  const z = c * Math.pow(-1/c,i/1000);
  path += `${i?'L':'M'}${X(z)},${Y(projectDepth(z,c))}`;
 }
 s += svg('path',{d:path,fill:'none',stroke:'#2869bc','stroke-width':3.5,'stroke-linejoin':'round'});
 s += svg('text',{x:450,y:526,'text-anchor':'middle',class:'axis-title'},'z');
 s += svg('text',{x:20,y:258,transform:'rotate(-90 20 258)','text-anchor':'middle',class:'axis-title'},'z′ after division by w');
 s += svg('text',{x:84,y:447,fill:'#2869bc'},'Far');
 s += svg('text',{x:Math.max(110,Math.min(790,X(c)-12)),y:35,'text-anchor':'end'},`Near (${fmt(c)}, 0)`);
 s += '<g id="cursor"></g>';
 plot.innerHTML=s;
}
function sync() {
 for(const [name,value] of [['near',near],['far',far]]) {
  $(name).value=Math.log10(value); $(name+'-number').value=fmt(value);
 }
 $('error').textContent=''; render();
}
for(const name of ['near','far']) {
 $(name).addEventListener('input',()=>{
  let value=10**Number($(name).value);
  if(name==='near') near=Math.min(value,far/1.01); else far=Math.max(value,near*1.01);
  sync();
 });
 $(name+'-number').addEventListener('input',()=>{
  const n=Number($('near-number').value), f=Number($('far-number').value);
  if(!Number.isFinite(n)||!Number.isFinite(f)||n<0.001||f>10000||n>=f) {
   $('error').textContent='Use 0.001 ≤ near < far ≤ 10000.'; return;
  }
  near=n;far=f; $('near').value=Math.log10(n);$('far').value=Math.log10(f);$('error').textContent='';render();
 });
}
document.querySelectorAll('[data-near]').forEach(button=>button.addEventListener('click',()=>{
 near=Number(button.dataset.near);far=Number(button.dataset.far);sync();
}));
plot.addEventListener('pointermove',event=>{
 const p=plot.createSVGPoint();p.x=event.clientX;p.y=event.clientY;
 const local=p.matrixTransform(plot.getScreenCTM().inverse());
 const z=Math.max(-1,Math.min(-near/far,(local.x-76)/748-1));
 const out=projectDepth(z,-near/far);
 $('cursor').innerHTML=svg('circle',{cx:X(z),cy:Y(out),r:7,fill:'white',stroke:'#2869bc','stroke-width':3});
 $('probe').textContent=`z = ${fmt(z)} · z′ = ${fmt(out)}`;
});
plot.addEventListener('pointerleave',()=>{$('cursor').innerHTML='';$('probe').textContent='Move over the curve to inspect a depth.';});
sync();
}
