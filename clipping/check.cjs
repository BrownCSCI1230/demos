const assert=require('node:assert/strict');
const {project,divide,clip,geometry,planes}=require('./math.js');
const approx=(a,b)=>assert.ok(Math.abs(a-b)<1e-9,`${a} != ${b}`);
approx(divide(project([0,0,-1]))[2],-1);
approx(divide(project([0,0,-8]))[2],1);
for(const z of [.01,.1,1,5,100])assert.ok(divide(project([0,0,z]))[2]>1);
const crossing=geometry(-3,1.5,1.2).map(p=>project(p));
const proper=clip(crossing);
assert.ok(proper.length>=3);
for(const p of proper){assert.ok(p[3]>0);for(const plane of planes)assert.ok(plane(p)>=-1e-9);}
const premature=crossing.map(p=>[...divide(p),1]);
const falsePolygon=clip(premature);
assert.ok(falsePolygon.length>=3,'Prematurely divided triangle must intersect the NDC cube');
assert.ok(falsePolygon.every(p=>p[1]<-.2),'False image is below the original base');
assert.ok(proper.some(p=>divide(p)[1]>0),'Correct image extends above the original base');
for(const z of [-3,2,-11]){
 const input=geometry(z,z,1.2).map(p=>project(p));
 if(z===-3) assert.equal(clip(input).length,3); else assert.equal(clip(input).length,0);
}
for(const base of [-12,-3,-.5,0,2,5])for(const tip of [-12,-3,-.5,0,2,5])for(const near of [.25,1,2])for(const far of [2.5,8,12]){
 approx(divide(project([0,0,-near],near,far))[2],-1);
 approx(divide(project([0,0,-far],near,far))[2],1);
 const out=clip(geometry(base,tip,1.2).map(p=>project(p,near,far)));
 for(const p of out){assert.ok(p.every(Number.isFinite));assert.ok(p[3]>=near-1e-8);assert.ok(p[3]<=far+1e-8);for(const plane of planes)assert.ok(plane(p)>=-1e-8);}
}
console.log('Passed: near/far mapping, behind-camera bounds, false triangle intersection, all presets, and 324 near/far and crossing/singularity configurations.');
