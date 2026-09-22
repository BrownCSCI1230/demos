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
near(run('optics({f:50,D:60,u:200}).c'),120/11);
for (const f of [43,50,51.5]) for (const D of [2,25,60]) for (const u of [200,300,600,1000]) {
  const o=run(`optics({f:${f},D:${D},u:${u}})`);
  near(1/f,1/u+1/o.v);
  near(o.s, 600/11);
  near(1/f,1/o.focus+1/o.s);
  assert.ok(o.focus >= 200 && o.focus <= 1000);
  const halfHeight=D/2+o.s*(-D/2/o.v);
  near(o.c,2*Math.abs(halfHeight));
  run(`Object.assign(state,{f:${f},D:${D},u:${u}});update()`);
  for(const el of elements.values())assert.ok(!/NaN|Infinity/.test(el.innerHTML));
}
element('reset').handlers.click();assert.equal(element('coc').textContent,'0.00');
element('distance').handlers.input({target:{value:'300'}});assert.equal(element('coc').textContent,'2.27');
element('diameter').handlers.input({target:{value:'50'}});assert.equal(element('coc').textContent,'4.55');
element('object-point').handlers.keydown({key:'ArrowLeft',preventDefault(){}});assert.equal(element('distance').value,310);
element('object-point').handlers.pointerdown({clientX:100,clientY:245,pointerId:1,preventDefault(){}});assert.equal(element('distance').value,1000);
element('object-point').handlers.pointermove({clientX:900,clientY:245});assert.equal(element('distance').value,200);
element('object-point').handlers.pointerup();
element('refocus').handlers.click();assert.equal(element('coc').textContent,'0.00');
// Focal length moves the focus plane while the sensor and object stay fixed.
element('reset').handlers.click();
const originalPlane=element('focus-plane').attrs.d;
const originalSensor=element('sensor').innerHTML.split('<text')[0].split('</line>')[0];
element('focal').handlers.input({target:{value:'43'}});
assert.notEqual(element('focus-plane').attrs.d,originalPlane);
near(run('state.u'),600);
near(run('optics(state).s'),600/11);
assert.notEqual(element('coc').textContent,'0.00');
for(const f of [43,48.2,50,51.5]){
  element('focal').handlers.input({target:{value:String(f)}});
  element('refocus').handlers.click();
  assert.equal(element('coc').textContent,'0.00');
  assert.equal(element('focus-status').textContent,'In focus');
}
// Focusing changes sensor separation, not lens power or object position.
element('reset').handlers.click();
const sensorBefore = element('sensor').innerHTML;
element('focus').handlers.input({target:{value:'60'}});
near(run('state.f'),50); near(run('state.u'),600);
near(run('optics(state).focus'),300);
assert.notEqual(element('sensor').innerHTML,sensorBefore);
assert.equal(element('coc').textContent,'2.50');
element('focus-on-point').handlers.click();
near(run('state.s'),600/11);
assert.equal(element('coc').textContent,'0.00');
for(const f of [43,48.2,50,51.5]) for(const distance of [200,400,600,1000]) {
  element('focal').handlers.input({target:{value:String(f)}});
  const targetSensor = f*distance/(distance-f);
  element('focus').handlers.input({target:{value:String(targetSensor)}});
  near(run('optics(state).focus'),distance);
  near(run('state.f'),f);
  near(run('state.u'),600);
  element('distance').handlers.input({target:{value:String(distance)}});
  assert.equal(element('coc').textContent,'0.00');
  element('distance').handlers.input({target:{value:'600'}});
}
// Changing focal length at either focus limit keeps the control and scene valid.
for(const f of [43,51.5,43]) {
  element('focal').handlers.input({target:{value:String(f)}});
  const {min,max}=run('sensorLimits(state.f)');
  assert.ok(run('state.s')>=min && run('state.s')<=max);
  element('focus').handlers.input({target:{value:String(max)}});
}
element('reset').handlers.click();
near(run('state.s'),600/11);
console.log('Passed: optical equations, moving-sensor focus at 16 settings, independent controls, range limits, dragging, keyboard, and reset.');
