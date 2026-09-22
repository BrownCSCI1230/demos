// Run with `node check.cjs`. No installed dependencies required.
const fs = require('node:fs');
const vm = require('node:vm');
const assert = require('node:assert/strict');
const elements = new Map();
const context = new Proxy({}, { get: (target,key) => target[key] || (()=>{}) });
function element(id) {
  if (!elements.has(id)) elements.set(id, {value:'',textContent:'',innerHTML:'',attrs:{},handlers:{},
    setAttribute(k,v){this.attrs[k]=v;},addEventListener(k,v){this.handlers[k]=v;},classList:{toggle(){}},
    getContext(){return context;},setPointerCapture(){},focus(){},getScreenCTM(){return {inverse(){return {};}};}});
  return elements.get(id);
}
const sandbox = { document:{getElementById:element}, DOMPoint:class {constructor(x,y){this.x=x;this.y=y;}matrixTransform(){return this;}} };
vm.createContext(sandbox);
vm.runInContext(fs.readFileSync(__dirname+'/demo.js','utf8'),sandbox);
const run = source=>vm.runInContext(source,sandbox);
const near = (a,b)=>assert.ok(Math.abs(a-b)<1e-9,`${a} != ${b}`);
near(run('optics({f:50,D:25,u:600}).c'),0);
near(run('optics({f:50,D:25,u:300}).c'),25/11);
let configurations=0;
for(const f of [43,50,51.5]) for(const focus of [200,600,1000]) for(const D of [2,25,60]) for(const u of [200,600,1000]) {
  run(`Object.assign(state,{f:${f},s:${f*focus/(focus-f)},D:${D},u:${u}});update()`);
  const o=run('optics(state)');
  near(1/f,1/u+1/o.v);near(1/f,1/focus+1/o.s);
  near(o.c,D*o.s*Math.abs(1/u-1/focus));
  // Diagram positions must use fixed physical scales, independent of focus.
  near(Number(element('focus-plane').attrs.d.match(/^M([^ ]+)/)[1]),600-focus*0.5);
  near(Number(element('object-point').attrs.transform.match(/translate\(([^ ]+)/)[1]),600-u*0.5);
  near(Number(element('sensor').innerHTML.match(/x1="([^"]+)/)[1]),600+o.s*2.5);
  near(Number(element('focal-mark').innerHTML.match(/x1="([^"]+)/)[1]),600+f*2.5);
  for(const el of elements.values())assert.ok(!/NaN|Infinity/.test(el.innerHTML));
  configurations++;
}
element('reset').handlers.click();
const oldPlane=element('focus-plane').attrs.d;
const oldObject=element('object-point').attrs.transform;
const oldSensor=element('sensor').innerHTML;
element('focus').handlers.input({target:{value:'60'}});
near(run('optics(state).focus'),300);
assert.notEqual(element('focus-plane').attrs.d,oldPlane);
assert.notEqual(element('sensor').innerHTML,oldSensor);
assert.equal(element('object-point').attrs.transform,oldObject);
near(run('state.u'),600);
element('focus-on-point').handlers.click();
assert.equal(element('coc').textContent,'0.00');
element('distance').handlers.input({target:{value:'300'}});
assert.equal(element('coc').textContent,'2.27');
element('diameter').handlers.input({target:{value:'50'}});
assert.equal(element('coc').textContent,'4.55');
element('object-point').handlers.keydown({key:'ArrowLeft',preventDefault(){}});
near(run('state.u'),310);
element('object-point').handlers.pointerdown({clientX:0,clientY:245,pointerId:1,preventDefault(){}});
near(run('state.u'),1000);
element('object-point').handlers.pointermove({clientX:900,clientY:245});
near(run('state.u'),200);
element('object-point').handlers.pointerup();
element('refocus').handlers.click();assert.equal(element('coc').textContent,'0.00');
element('reset').handlers.click();
const initialPlane=element('focus-plane').attrs.d;
element('focal').handlers.input({target:{value:'43'}});
assert.notEqual(element('focus-plane').attrs.d,initialPlane);
near(run('state.u'),600);near(run('state.s'),600/11);
near(run('apertureLight(60)'),1);near(run('apertureLight(30)'),0.25);
for(const focused of [true,false]) {
  const colors=[2,15,30,60].map(D=>run(`spotColor(${D},${focused})`).match(/\d+/g).map(Number));
  for(let i=1;i<colors.length;i++) for(let channel=0;channel<3;channel++)assert.ok(colors[i][channel]>colors[i-1][channel]);
}
element('reset').handlers.click();
console.log(`Passed: ${configurations} fixed-scale optical configurations, visible focus/sensor shifts, independent object position, controls, and brightness.`);
