'use strict';
(() => {
  const $ = id => document.getElementById(id);
  const presets = [
    {name:'Soft falloff', c:[1,.07,.07], description:'An art-directed combination of coefficients that emulates the appearance of falloff with indirect illumination.'},
    {name:'Constant only', c:[2,0,0], description:'Constant attenuation: distance does not change f. The ground still responds to the angle of incoming light.'},
    {name:'Linear only', c:[0,1,0], description:'Linear attenuation: brightness falls as 1/d, with the polynomial factor capped at 1.'},
    {name:'Quadratic only', c:[0,0,1], description:'The two models match: both use inverse-square attenuation capped at 1 for LDR display.'},
  ];
  let playing = true, phase = 0, height = 2, coefficients = [...presets[0].c], presetIndex = 0;
  const vertex = `attribute vec2 position; void main(){gl_Position=vec4(position,0.,1.);}`;
  const fragment = `precision highp float;
    uniform vec2 resolution; uniform float height; uniform vec3 coefficients; uniform float polynomial;
    float sphere(vec3 ro,vec3 rd,vec3 center,float radius){vec3 oc=ro-center;float b=dot(oc,rd);float h=b*b-dot(oc,oc)+radius*radius;return h<0.?-1.:-b-sqrt(h);}
    void boxBounds(int i,out vec3 lo,out vec3 hi){
      vec2 center; float h;
      if(i==0){center=vec2(-5.8,-5.8);h=4.;}
      else if(i==1){center=vec2(5.8,-5.8);h=3.;}
      else if(i==2){center=vec2(-5.8,5.8);h=2.;}
      else{center=vec2(5.8,5.8);h=1.;}
      lo=vec3(center.x-.8,0.,center.y-.8);
      hi=vec3(center.x+.8,h,center.y+.8);
    }
    float boxHit(vec3 ro,vec3 rd,vec3 lo,vec3 hi,out vec3 normal){
      vec3 safeDir=vec3(rd.x<0.?-1.:1.,rd.y<0.?-1.:1.,rd.z<0.?-1.:1.)*max(abs(rd),vec3(.000001));
      vec3 a=(lo-ro)/safeDir,b=(hi-ro)/safeDir;
      vec3 nearT=min(a,b),farT=max(a,b);
      float entry=max(nearT.x,max(nearT.y,nearT.z)),leave=min(farT.x,min(farT.y,farT.z));
      normal=vec3(0.);
      if(entry<=.0001 || leave<entry)return -1.;
      if(nearT.x>=nearT.y && nearT.x>=nearT.z)normal.x=-sign(safeDir.x);
      else if(nearT.y>=nearT.z)normal.y=-sign(safeDir.y);
      else normal.z=-sign(safeDir.z);
      return entry;
    }
    void main(){
      vec2 uv=(2.*gl_FragCoord.xy-resolution)/resolution.y;
      vec3 ro=vec3(11.,9.5,16.), target=vec3(0.,1.4,0.);
      vec3 forward=normalize(target-ro),right=normalize(cross(forward,vec3(0.,1.,0.))),up=cross(right,forward);
      vec3 rd=normalize(forward*2.5+right*uv.x+up*uv.y);
      vec3 light=vec3(0.,height,0.); vec3 color=vec3(.006,.009,.015);
      float hitT=10000.; vec3 normal=vec3(0.,1.,0.); bool ground=false;
      if(rd.y<0.){
        float planeT=-ro.y/rd.y;vec3 p=ro+planeT*rd;
        if(planeT>0. && abs(p.x)<7. && abs(p.z)<7.){hitT=planeT;ground=true;}
      }
      for(int i=0;i<4;i++){
        vec3 lo,hi,n;boxBounds(i,lo,hi);float t=boxHit(ro,rd,lo,hi,n);
        if(t>0. && t<hitT){hitT=t;normal=n;ground=false;}
      }
      if(hitT<10000.){
        vec3 p=ro+hitT*rd,delta=light-p;float d=length(delta);vec3 lightDir=delta/d;
        float f=1./max(1.,d*d);
        if(polynomial>.5)f=1./max(1.,coefficients.x+coefficients.y*d+coefficients.z*d*d);
        float albedo=.57;
        if(ground){
          vec2 cell=abs(fract(p.xz+.5)-.5);float line=1.-smoothstep(.018,.04,min(cell.x,cell.y));
          albedo=mix(.57,.43,line);
          float ring=1.-smoothstep(.018,.035,abs(length(p.xz)-.23));albedo*=1.-.5*ring;
        }
        float visible=1.;
        for(int i=0;i<4;i++){
          vec3 lo,hi,n;boxBounds(i,lo,hi);float t=boxHit(p+normal*.002,lightDir,lo,hi,n);
          if(t>0. && t<d)visible=0.;
        }
        color=vec3(1.,.84,.62)*albedo*f*clamp(dot(normal,lightDir),0.,1.)*visible;
      }
      float lightT=sphere(ro,rd,light,.14);
      if(lightT>0. && lightT<hitT)color=vec3(1.,.84,.62);
      // A subtle emissive halo makes the point-light marker visible at every height.
      float t=max(0.,dot(light-ro,rd));float off=length(ro+t*rd-light);
      if(t<hitT){
        float halo=.25*exp(-off*off/ .055);
        color=mix(color,vec3(1.,.84,.62),halo);
      }
      // All lighting factors and halo blending stay in [0,1].
      // Write directly to the normalized LDR framebuffer; no tone or gamma curve.
      gl_FragColor=vec4(color,1.);
    }`;
  function renderer(id, polynomial) {
    const canvas=$(id), gl=canvas.getContext('webgl',{alpha:false,antialias:true});
    if(!gl)throw new Error('WebGL is unavailable. Enable hardware acceleration or open this demo in a WebGL-capable browser.');
    function shader(type, source){const s=gl.createShader(type);gl.shaderSource(s,source);gl.compileShader(s);if(!gl.getShaderParameter(s,gl.COMPILE_STATUS))throw new Error(gl.getShaderInfoLog(s));return s;}
    const program=gl.createProgram();gl.attachShader(program,shader(gl.VERTEX_SHADER,vertex));gl.attachShader(program,shader(gl.FRAGMENT_SHADER,fragment));gl.linkProgram(program);if(!gl.getProgramParameter(program,gl.LINK_STATUS))throw new Error(gl.getProgramInfoLog(program));gl.useProgram(program);
    const buffer=gl.createBuffer();gl.bindBuffer(gl.ARRAY_BUFFER,buffer);gl.bufferData(gl.ARRAY_BUFFER,new Float32Array([-1,-1,3,-1,-1,3]),gl.STATIC_DRAW);const position=gl.getAttribLocation(program,'position');gl.enableVertexAttribArray(position);gl.vertexAttribPointer(position,2,gl.FLOAT,false,0,0);
    const uniforms=Object.fromEntries(['resolution','height','coefficients','polynomial'].map(name=>[name,gl.getUniformLocation(program,name)]));
    canvas.addEventListener('webglcontextlost',event=>{event.preventDefault();$('error').hidden=false;$('error').textContent='Graphics context lost. Reload this page to restart the demo.';});
    return () => {const scale=Math.min(window.devicePixelRatio||1,2),w=Math.round(canvas.clientWidth*scale),h=Math.round(canvas.clientHeight*scale);if(canvas.width!==w||canvas.height!==h){canvas.width=w;canvas.height=h;}gl.viewport(0,0,w,h);gl.uniform2f(uniforms.resolution,w,h);gl.uniform1f(uniforms.height,height);gl.uniform3fv(uniforms.coefficients,coefficients);gl.uniform1f(uniforms.polynomial,polynomial);gl.drawArrays(gl.TRIANGLES,0,3);};
  }
  function setPlaying(value){playing=value;$('play').textContent=value?'Pause':'Play';$('play').setAttribute('aria-pressed',String(value));}
  function sync(){['c1','c2','c3'].forEach((id,i)=>{$(id).value=coefficients[i];$(id+'-value').value=coefficients[i].toFixed(2);});$('height').value=height;$('height-value').value=height.toFixed(2)+' units';$('physical-value').textContent=(1/Math.max(1,height*height)).toFixed(3);$('polynomial-value').textContent=(1/Math.max(1,coefficients[0]+coefficients[1]*height+coefficients[2]*height*height)).toFixed(3);[...$('presets').children].forEach((button,i)=>button.setAttribute('aria-pressed',String(i===presetIndex)));}
  function selectPreset(i){presetIndex=i;coefficients=[...presets[i].c];$('preset-description').textContent=presets[i].description;sync();}
  presets.forEach((preset,i)=>{const button=document.createElement('button');button.textContent=preset.name;button.onclick=()=>selectPreset(i);$('presets').append(button);});
  ['c1','c2','c3'].forEach((id,i)=>$(id).addEventListener('input',()=>{coefficients[i]=Number($(id).value);presetIndex=-1;$('preset-description').textContent='Custom coefficients. Each term changes the denominator; larger values reduce the light reaching the surface.';sync();}));
  $('height').addEventListener('input',()=>{setPlaying(false);height=Number($('height').value);phase=Math.acos(Math.max(-1,Math.min(1,(2.825-height)/2.175)));sync();});
  $('play').onclick=()=>setPlaying(!playing);
  $('reset').onclick=()=>{phase=0;height=.65;setPlaying(true);selectPreset(0);};
  selectPreset(0);
  try {const draw=[renderer('physical',0),renderer('polynomial',1)];let previous;
    function frame(now){const dt=previous===undefined?0:Math.min((now-previous)/1000,.1);previous=now;if(playing){phase=(phase+dt*Math.PI/4)% (2*Math.PI);height=2.825-2.175*Math.cos(phase);}sync();draw.forEach(render=>render());requestAnimationFrame(frame);}requestAnimationFrame(frame);
  }catch(error){$('error').hidden=false;$('error').textContent=error.message;}
})();
