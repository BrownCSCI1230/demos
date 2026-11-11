const assert = require('node:assert/strict');
const {projectDepth} = require('./demo.js');
for (const ratio of [0.0000001,0.001,0.01,0.1,0.5,0.99,0.999999]) {
 const c=-ratio;
 assert.ok(Math.abs(projectDepth(-1,c)+1)<1e-9);
 assert.ok(Math.abs(projectDepth(c,c))<1e-9);
 let last=0;
 for(let i=1;i<=100;i++) {
  const z=c+(-1-c)*i/100, out=projectDepth(z,c);
  assert.ok(Number.isFinite(out)&&out<=last+1e-9&&out>=-1-1e-9);
  assert.ok(Math.abs(out-(c-z)/(z+z*c))<1e-8);
  last=out;
 }
}
// Exercise rendering and event handlers without a browser.
const fs=require('node:fs'),vm=require('node:vm');
const elements={};
function element(id){return elements[id]??=( {value:'',textContent:'',innerHTML:'',handlers:{},addEventListener(type,fn){this.handlers[type]=fn;}});}
const buttons=[{dataset:{near:'0.1',far:'1'}}].map((b,i)=>Object.assign(element('button'+i),b));
vm.runInNewContext(fs.readFileSync(__dirname+'/demo.js','utf8'),{document:{getElementById:element,querySelectorAll:()=>buttons}});
assert.match(element('plot').innerHTML,/<path/);
for(const button of buttons){button.handlers.click();assert.equal(element('near-number').value,button.dataset.near);}
element('near-number').value='2';element('near-number').handlers.input();assert.match(element('error').textContent,/near < far/);
buttons[0].handlers.click();element('near').value='0';element('near').handlers.input();assert.ok(Number(element('near-number').value)<Number(element('far-number').value));
console.log('Passed: endpoint mappings, monotonicity, slide formula, render generation, reset, invalid inputs, slider constraints.');
