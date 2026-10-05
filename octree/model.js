/* Geometry and construction are independent of the presentation. */
(function(root){
const colors=['#ec9560','#568cd5','#a07bc5','#36a58a','#de6e86','#c6a13f','#5aa9ba','#b57c56','#797ec0','#749953'];
function countLabel(count,singular,plural=singular+'s'){return `${count} ${count===1?singular:plural}`;}
function scene(name){
// Eight small objects share a depth-two cell, then separate into its eight
// depth-three children. Two distant objects fix the root bounds at [-1, 1].
const deepCluster=Array.from({length:8},(_,k)=>['sphere',
  [4,2,1].map(bit=>(k&bit)?-.625:-.875),.065
]).concat([['sphere',[-.9,.9,-.9],.1],['sphere',[.9,-.9,.9],.1]]);
let data=name==='deep-cluster'?deepCluster:name==='overlap'?[['box',[-.85,-.18,-.2],[.85,.18,.2]],['sphere',[-.62,.5,.48],.2],['sphere',[.6,-.5,-.48],.2]]:name==='clusters'?[['sphere',[-.65,-.6,-.6],.2],['sphere',[-.38,-.4,-.42],.16],['sphere',[.62,.55,.58],.21],['sphere',[.38,.38,.35],.15]]:[['sphere',[-.48,-.4,.35],.3],['box',[-.15,.16,-.4],[.72,.48,.18]],['box',[-.72,-.7,-.65],[-.4,-.25,-.3]],['sphere',[.5,-.45,-.38],.2],['sphere',[-.4,.52,.5],.19]];
return data.map(([type,a,b],i)=>({id:i,name:String.fromCharCode(65+i),type,color:colors[i],center:type==='sphere'?a:a.map((v,j)=>(v+b[j])/2),radius:type==='sphere'?b:null,min:type==='sphere'?a.map(v=>v-b):a,max:type==='sphere'?a.map(v=>v+b):b}));}
function bounds(ps){return {min:[0,1,2].map(i=>Math.min(...ps.map(p=>p.min[i]))),max:[0,1,2].map(i=>Math.max(...ps.map(p=>p.max[i]))) };}
function intersects(p,b){if(p.type==='sphere')return p.center.reduce((s,v,i)=>s+Math.max(b.min[i]-v,0,v-b.max[i])**2,0)<=p.radius**2+1e-12;return p.min.every((v,i)=>v<=b.max[i]+1e-12&&p.max[i]>=b.min[i]-1e-12);}
function build(ps,maxDepth,capacity){const nodes={},events=[];function emit(phase,id,text){events.push({phase,id,text,nodes:JSON.parse(JSON.stringify(nodes))});}let n=0;const box=bounds(ps);nodes.root={id:'root',label:'Root',depth:0,...box,refs:ps.map(p=>p.id),children:[],status:'pending'};emit('bound','root','Find the axis-aligned bounding box of the entire scene. This root contains every object.');
function visit(id){const node=nodes[id];if(node.depth>=maxDepth||node.refs.length<=capacity){node.status='leaf';node.reason=node.depth>=maxDepth?'maximum depth':'within capacity';emit('leaf',id,`${node.label} becomes a leaf node: ${node.reason==='maximum depth'?`depth ${node.depth} reaches the maximum`:`${countLabel(node.refs.length,'object reference')} ≤ the maximum allowed objects per leaf node`}. Recursion stops.`);return;}
node.status='splitting';emit('split',id,`Bisect ${node.label} at its midpoint on x, y, and z. Three axis-aligned planes create eight equal octants.`);const mid=node.min.map((v,i)=>(v+node.max[i])/2);for(let k=0;k<8;k++){const bits=[(k>>2)&1,(k>>1)&1,k&1],cid='n'+(++n);const child={id:cid,label:node.label==='Root'?bits.join(''):node.label+'/'+bits.join(''),depth:node.depth+1,min:bits.map((b,i)=>b?mid[i]:node.min[i]),max:bits.map((b,i)=>b?node.max[i]:mid[i]),children:[],status:'pending'};child.refs=node.refs.filter(i=>intersects(ps[i],child));nodes[cid]=child;node.children.push(cid);}const unique=node.refs.length;node.refs=[];node.status='internal';emit('assign',id,`Distribute ${countLabel(unique,'object')} among eight children. An object touching multiple octants has an object reference in each; its geometry is not cut.`);node.children.forEach(visit);}
visit('root');emit('done','root','Construction complete. Internal nodes contain child links; only leaf nodes contain object references. Select a leaf node to inspect its cell and object references.');return events;}
function descendantRefs(nodes,id){const n=nodes[id];return [...new Set(n.children.length?n.children.flatMap(c=>descendantRefs(nodes,c)):n.refs)];}
const api={countLabel,scene,bounds,intersects,build,descendantRefs};root.Octree=api;if(typeof module!=='undefined')module.exports=api;
})(typeof window!=='undefined'?window:globalThis);
