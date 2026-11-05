/* Shared by the browser demo and the numerical checks. OpenGL eye space: -Z forward. */
(function (root) {
  const planes = [p=>p[3]+p[0],p=>p[3]-p[0],p=>p[3]+p[1],p=>p[3]-p[1],p=>p[3]+p[2],p=>p[3]-p[2]];
  function project(p, near=1, far=8) { return [p[0],p[1],-(far+near)/(far-near)*p[2]-2*far*near/(far-near),-p[2]]; }
  function divide(p) { return p.slice(0,3).map(v=>v/p[3]); }
  function clip(poly) {
    for (const plane of planes) {
      const out=[];
      for(let i=0;i<poly.length;i++) {
        const a=poly[i],b=poly[(i+1)%poly.length], da=plane(a),db=plane(b);
        if(da>=0) out.push(a);
        if((da>=0)!==(db>=0)) { const t=da/(da-db); out.push(a.map((v,j)=>v+t*(b[j]-v))); }
      }
      poly=out;
    }
    return poly;
  }
  function geometry(base, tip, height) { return [[-1.5,-.8,base],[1.5,-.8,base],[0,height,tip]]; }
  const api={project,divide,clip,geometry,planes};
  if(typeof module!=='undefined') module.exports=api; else root.ClipMath=api;
})(typeof window!=='undefined'?window:globalThis);
