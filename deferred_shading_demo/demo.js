// Demo adapted from WEBGL Tutorial.


const vsSource = `#version 300 es

layout(std140, column_major) uniform;

layout(location=0) in vec4 aPosition;
layout(location=1) in vec3 aNormal;
layout(location=2) in vec4 aUV;

uniform Matrices {
    mat4 uModelMatrix;
    mat4 uMVP;
};

out vec4 vPosition;
out vec4 vNormal;
out vec4 vUV;

void main() {
    vPosition = uModelMatrix * aPosition;
    vNormal = uModelMatrix * vec4(aNormal, 0.0);
    vUV = aUV;
    gl_Position = uMVP * aPosition;
}`;

// Fragment shader program

const fsSource = `#version 300 es
  precision highp float;
  
  in vec4 vPosition;
  in vec4 vNormal; 
  in vec4 vUV;

  layout(location=0) out vec4 fragPosition;
  layout(location=1) out vec4 fragNormal;

  void main() {
      fragPosition = vPosition;
      fragNormal = vec4(normalize(vNormal.xyz), 0.0);
  }`;

const secondPass_vs = `#version 300 es

        layout(std140, column_major) uniform;

        layout(location=0) in vec4 aPosition;
        
        uniform LightUniforms {
            mat4 mvp;
            vec4 position;
            vec4 color;
            vec2 attenuate;
        } uLight; 

        void main() {
            gl_Position = uLight.mvp * aPosition;
        }
`;

const secondPass_fs = `#version 300 es
        precision highp float;

        uniform LightUniforms {
            mat4 mvp;
            vec4 position;
            vec4 color;
            vec2 attenuate;
        } uLight; 

        uniform vec3 uEyePosition;

        uniform sampler2D uPositionBuffer;
        uniform sampler2D uNormalBuffer;
        uniform int uToScreen;

        out vec4 fragColor;

        void main() {
            ivec2 fragCoord = ivec2(gl_FragCoord.xy);
            vec3 position = texelFetch(uPositionBuffer, fragCoord, 0).xyz;
            vec3 normal = normalize(texelFetch(uNormalBuffer, fragCoord, 0).xyz);

            vec4 baseColor = vec4(1.0,1.0,1.0,1.0);

            vec3 eyeDirection = normalize(uEyePosition - position);
            vec3 lightVec = uLight.position.xyz - position;
            float attenuation = 1.0;// - (length(lightVec))/1.2;
            if(uLight.attenuate.x > 0.0){
                attenuation = uLight.attenuate.y - length(lightVec);
            }
            vec3 lightDirection = normalize(lightVec);
            vec3 reflectionDirection = reflect(-lightDirection, normal);
            float nDotL = max(dot(lightDirection, normal), 0.0);
            vec3 diffuse = nDotL * uLight.color.rgb;
            vec3 specular = pow(max(dot(reflectionDirection, eyeDirection), 0.0), 20.0) * uLight.color.rgb;

            if(uToScreen == 0){
                fragColor = vec4(attenuation * (diffuse + specular) * baseColor.rgb, 1.0);
            } else if (uToScreen == 1){
                fragColor = vec4(position, 1.0);
            } else if (uToScreen == 2){
                fragColor = vec4(normal, 1.0);
            } else if (uToScreen == 3){
                fragColor = vec4(uLight.color.r,uLight.color.g,uLight.color.b, 1.0);
            }
        }
`;


function glSetUpBuffer(buffer, data,  index, bindNum){
    gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
    gl.bufferData(gl.ARRAY_BUFFER, data, gl.STATIC_DRAW);
    gl.vertexAttribPointer(index, 3, gl.FLOAT, false, 0, 0);
    gl.enableVertexAttribArray(bindNum);
}

var canvas = document.getElementById("gl-canvas");
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

var gl = canvas.getContext("webgl2");
if (!gl) {
    console.error("WebGL 2 not available");
    document.body.innerHTML = "This example requires WebGL 2 which is unavailable on this system."
}

gl.clearColor(0.0, 0.0, 0.0, 1.0);
gl.enable(gl.DEPTH_TEST);
gl.depthFunc(gl.LEQUAL);
gl.blendFunc(gl.ONE, gl.ONE);

if (!gl.getExtension("EXT_color_buffer_float")) {
    console.error("FLOAT color buffer not available");
    document.body.innerHTML = "This example requires EXT_color_buffer_float which is unavailable on this system."
}

////////////////////////////
// GBUFFER PROGRAM SETUP
////////////////////////////


var geoVertexShader = gl.createShader(gl.VERTEX_SHADER);
gl.shaderSource(geoVertexShader, vsSource);
gl.compileShader(geoVertexShader);

if (!gl.getShaderParameter(geoVertexShader, gl.COMPILE_STATUS)) {
    console.error(gl.getShaderInfoLog(geoVertexShader));
}

var geoFragmentShader = gl.createShader(gl.FRAGMENT_SHADER);
gl.shaderSource(geoFragmentShader, fsSource);
gl.compileShader(geoFragmentShader);

if (!gl.getShaderParameter(geoFragmentShader, gl.COMPILE_STATUS)) {
    console.error(gl.getShaderInfoLog(geoFragmentShader));
}

var geoProgram = gl.createProgram();
gl.attachShader(geoProgram, geoVertexShader);
gl.attachShader(geoProgram, geoFragmentShader);
gl.linkProgram(geoProgram);

if (!gl.getProgramParameter(geoProgram, gl.LINK_STATUS)) {
    console.error(gl.getProgramInfoLog(geoProgram));
}

//////////////////////////////////////////
// GET GBUFFFER PROGRAM UNIFORM LOCATIONS
//////////////////////////////////////////

var matrixUniformLocation = gl.getUniformBlockIndex(geoProgram, "Matrices");
gl.uniformBlockBinding(geoProgram, matrixUniformLocation, 0);


////////////////////////////
// GBUFFER SETUP
////////////////////////////

var gBuffer = gl.createFramebuffer();
gl.bindFramebuffer(gl.FRAMEBUFFER, gBuffer);

gl.activeTexture(gl.TEXTURE0);

var positionTarget = gl.createTexture();
make_texture(gl, positionTarget, 0)

var normalTarget = gl.createTexture();
make_texture(gl, normalTarget, 1);

var depthTexture = gl.createTexture();

gl.bindTexture(gl.TEXTURE_2D, depthTexture);

gl.texStorage2D(gl.TEXTURE_2D, 1, gl.DEPTH_COMPONENT16, gl.drawingBufferWidth, gl.drawingBufferHeight);
gl.framebufferTexture2D(gl.FRAMEBUFFER, gl.DEPTH_ATTACHMENT, gl.TEXTURE_2D, depthTexture, 0);

gl.drawBuffers([
    gl.COLOR_ATTACHMENT0,
    gl.COLOR_ATTACHMENT1,
]);


gl.bindFramebuffer(gl.FRAMEBUFFER, null);


var mainVsSource =  secondPass_vs;
var mainFsSource =  secondPass_fs;
var mainVertexShader = gl.createShader(gl.VERTEX_SHADER);
gl.shaderSource(mainVertexShader, mainVsSource);
gl.compileShader(mainVertexShader);

if (!gl.getShaderParameter(mainVertexShader, gl.COMPILE_STATUS)) {
    console.error(gl.getShaderInfoLog(mainVertexShader));
}

var mainFragmentShader = gl.createShader(gl.FRAGMENT_SHADER);
gl.shaderSource(mainFragmentShader, mainFsSource);
gl.compileShader(mainFragmentShader);

if (!gl.getShaderParameter(mainFragmentShader, gl.COMPILE_STATUS)) {
    console.error(gl.getShaderInfoLog(mainFragmentShader));
}

var mainProgram = gl.createProgram();
gl.attachShader(mainProgram, mainVertexShader);
gl.attachShader(mainProgram, mainFragmentShader);
gl.linkProgram(mainProgram);

if (!gl.getProgramParameter(mainProgram, gl.LINK_STATUS)) {
    console.error(gl.getProgramInfoLog(mainProgram));
}

//////////////////////////////////////////////
// GET MAIN PROGRAM UNIFORM LOCATIONS
//////////////////////////////////////////////

var lightUniformsLocation = gl.getUniformBlockIndex(mainProgram, "LightUniforms");
gl.uniformBlockBinding(mainProgram, lightUniformsLocation, 0);

var eyePositionLocation = gl.getUniformLocation(mainProgram, "uEyePosition");
var positionBufferLocation = gl.getUniformLocation(mainProgram, "uPositionBuffer");
var normalBufferLocation = gl.getUniformLocation(mainProgram, "uNormalBuffer");
var toScreenLocation = gl.getUniformLocation(mainProgram, "uToScreen");

///////////////////////
// GEOMETRY SET UP
///////////////////////

var cubeVertexArray = gl.createVertexArray();
gl.bindVertexArray(cubeVertexArray);

var box = utils.createBox();

var positionBuffer = gl.createBuffer();
glSetUpBuffer(positionBuffer, box.positions, 0, 0);

var normalBuffer = gl.createBuffer();
glSetUpBuffer(normalBuffer, box.normals, 1, 1);

var sphereVertexArray = gl.createVertexArray();
gl.bindVertexArray(sphereVertexArray);

var numCubeVertices = box.positions.length / 3;

var sphere = utils.createSphere();

positionBuffer = gl.createBuffer();
glSetUpBuffer(positionBuffer, sphere.positions, 0,0);

var indexBuffer = gl.createBuffer();
gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, indexBuffer);
gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, sphere.indices, gl.STATIC_DRAW);

var numSphereElements = sphere.indices.length;

gl.bindVertexArray(null);

var projMatrix = mat4.create();
mat4.perspective(projMatrix, Math.PI / 2, canvas.width / canvas.height, 0.1, 10.0);

var viewMatrix = mat4.create();
var eyePosition = vec3.fromValues(0, 0, 2);
mat4.lookAt(viewMatrix, eyePosition, vec3.fromValues(0, 0, 0), vec3.fromValues(0, 1, 0));

var viewProjMatrix = mat4.create();
mat4.multiply(viewProjMatrix, projMatrix, viewMatrix);

  

var boxes = [
    {
        scale: [1.0, 2.5, 1.0],
        rotate: [0, 65, 0],
        translate: [-0.6, -1, -0.75],
        modelMatrix: mat4.create(),
        mvpMatrix: mat4.create(),
    },
    {
        scale: [0.65, 0.65, 0.65],
        rotate: [0, 45, 0],
        translate: [0.35, -1.0, 0.25],
        modelMatrix: mat4.create(),
        mvpMatrix: mat4.create(),
    }, 
    // room walls
    {
        scale: [7, 7, 7],
        rotate: [0,0,0],
        translate: [0.0, 0, -5.0],
        modelMatrix: mat4.create(),
        mvpMatrix: mat4.create(),
    },
    {
        scale: [7, 7, 7],
        rotate: [0,0,0],
        translate: [-5.0, 0, 0.0],
        modelMatrix: mat4.create(),
        mvpMatrix: mat4.create(),
    },
    {
        scale: [7, 7, 7],
        rotate: [0,0,0],
        translate: [0.0, -5.0, 0.0],
        modelMatrix: mat4.create(),
        mvpMatrix: mat4.create(),
    },
    {
        scale: [7, 7, 7],
        rotate: [0,0,0],
        translate: [0.0, 5.5, 0.0],
        modelMatrix: mat4.create(),
        mvpMatrix: mat4.create(),
    },
    {
        scale: [7, 7, 7],
        rotate: [0,0,0],
        translate: [5.0, 0.0, 0.0],
        modelMatrix: mat4.create(),
        mvpMatrix: mat4.create(),
    },

];

var matrixUniformData = new Float32Array(32);
var matrixUniformBuffer = gl.createBuffer();
gl.bindBufferBase(gl.UNIFORM_BUFFER, 0, matrixUniformBuffer);
gl.bufferData(gl.UNIFORM_BUFFER, 128, gl.DYNAMIC_DRAW);

scaleBy = 0.1

var numLights = 0;
var lights = [
    {
        position: vec3.fromValues(0.0, 0, -1.0),
        color:    vec3.fromValues(0.1, 0.1, 0.1),
        uniformData: new Float32Array(32),
        uniformBuffer: gl.createBuffer(),
        scale: vec3.fromValues(3.0, 3.0, 3.0), 
        attenuate: vec2.fromValues(0.0,0.0)
    },
];

for(var i = 0; i < numLights; i++){
    var scale_light = Math.random() * 0.5 + 0.3;
    var light = {
        position: vec3.fromValues(Math.random() * 5 - 2,Math.random() * 5 - 2,Math.random()* 5 - 2),
        color: vec3.fromValues(Math.random() * 0.5,Math.random() * 0.5,Math.random() * 0.5),
        uniformData: new Float32Array(32),
        uniformBuffer: gl.createBuffer(),
        scale: vec3.fromValues(scale_light, scale_light, scale_light),
        attenuate: vec2.fromValues(1.0,scale_light)
    }
    lights.push(light);
}

var mvpMatrix = mat4.create();
for (var i = 0, len = lights.length; i < len; ++i) {
    
    utils.xformMatrix(mvpMatrix, lights[i].position, vec3.fromValues(0,0,0),lights[i].scale);
    mat4.multiply(mvpMatrix, viewProjMatrix, mvpMatrix);
    lights[i].uniformData.set(mvpMatrix);
    lights[i].uniformData.set(lights[i].position, 16); // offset is 16 for size of mvp
    lights[i].uniformData.set(lights[i].color, 20); // offset is 20 for size of mvp + position
    lights[i].uniformData.set(lights[i].attenuate, 24);

    gl.bindBufferBase(gl.UNIFORM_BUFFER, 0, lights[i].uniformBuffer);        
    gl.bufferData(gl.UNIFORM_BUFFER, lights[i].uniformData, gl.STATIC_DRAW);
}

var display_step = 0;
document.addEventListener('keydown', function(event) {
    console.log(event.keyCode)
    event.preventDefault();
    if(event.keyCode == 76){
        numLights += 1

        var scale_light = Math.random() * 0.5 + 0.3;
        var light = {
            position: vec3.fromValues(Math.random() * 5 - 2,Math.random() * 5 - 2,Math.random()* 5 - 2),
            color: vec3.fromValues(Math.random() * 0.5,Math.random() * 0.5,Math.random() * 0.5),
            uniformData: new Float32Array(32),
            uniformBuffer: gl.createBuffer(),
            scale: vec3.fromValues(scale_light, scale_light, scale_light),
            attenuate: vec2.fromValues(1.0,scale_light)
        }
        lights.push(light);
        i = lights.length - 1
        var mvpMatrix = mat4.create();
        utils.xformMatrix(mvpMatrix, lights[i].position, vec3.fromValues(0,0,0),lights[i].scale);
        mat4.multiply(mvpMatrix, viewProjMatrix, mvpMatrix);
        lights[i].uniformData.set(mvpMatrix);
        lights[i].uniformData.set(lights[i].position, 16); // offset is 16 for size of mvp
        lights[i].uniformData.set(lights[i].color, 20); // offset is 20 for size of mvp + position
        lights[i].uniformData.set(lights[i].attenuate, 24);

        gl.bindBufferBase(gl.UNIFORM_BUFFER, 0, lights[i].uniformBuffer);        
        gl.bufferData(gl.UNIFORM_BUFFER, lights[i].uniformData, gl.STATIC_DRAW);
    } else if (event.keyCode == 49){
        display_step = 0
        
    } else if (event.keyCode == 50){
        display_step = 1
        
    } else if (event.keyCode == 51){
        display_step = 2
        
    } else if (event.keyCode == 52){
        display_step = 3
        
    }
});


gl.activeTexture(gl.TEXTURE0);
gl.bindTexture(gl.TEXTURE_2D, positionTarget);
gl.activeTexture(gl.TEXTURE1);
gl.bindTexture(gl.TEXTURE_2D, normalTarget);

gl.useProgram(mainProgram);
gl.uniform3fv(eyePositionLocation, eyePosition);
gl.uniform1i(positionBufferLocation, 0);
gl.uniform1i(normalBufferLocation, 1);
var time = 0;

function draw() {



    gl.bindFramebuffer(gl.FRAMEBUFFER, gBuffer);
    gl.useProgram(geoProgram);
    gl.bindVertexArray(cubeVertexArray);
    gl.depthMask(true);
    gl.disable(gl.BLEND);

    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    for (var i = 0; i < boxes.length; ++i) {

        utils.xformMatrix(boxes[i].modelMatrix, boxes[i].translate, boxes[i].rotate, boxes[i].scale);
        mat4.multiply(boxes[i].mvpMatrix, viewProjMatrix, boxes[i].modelMatrix);
    
        matrixUniformData.set(boxes[i].modelMatrix);
        matrixUniformData.set(boxes[i].mvpMatrix, 16);

        gl.bindBufferBase(gl.UNIFORM_BUFFER, 0, matrixUniformBuffer);
        gl.bufferSubData(gl.UNIFORM_BUFFER, 0, matrixUniformData);

        gl.drawArrays(gl.TRIANGLES, 0, numCubeVertices);
    }

    draw_to_screen(gl, mainProgram);
    gl.bindVertexArray(sphereVertexArray);
    gl.depthMask(false);
    gl.enable(gl.BLEND);

    gl.uniform1i(toScreenLocation, display_step);

    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    var mvpMatrix = mat4.create();
    if(display_step == 2 || display_step == 1){
        for (var i = 0; i < 1; ++i) {

            gl.bindBufferBase(gl.UNIFORM_BUFFER, 0, lights[i].uniformBuffer);
            gl.bufferData(gl.UNIFORM_BUFFER, lights[i].uniformData, gl.STATIC_DRAW);
            gl.drawElements(gl.TRIANGLES, numSphereElements, gl.UNSIGNED_SHORT, 0);
        }
    } else {
        var show_lights = lights.length;
        if(display_step == 3){
            // only show the first 100 light volumes. Otherwise there's too many and it looks gross.
            show_lights = Math.min(lights.length, 100);
            for (var i = 0; i < show_lights; ++i) {
                if(i > 0){
                    lights[i].position[2] += 0.01 * Math.cos(time);
                    utils.xformMatrix(mvpMatrix, lights[i].position, vec3.fromValues(0,0,0),lights[i].scale);
                    mat4.multiply(mvpMatrix, viewProjMatrix, mvpMatrix);
                    lights[i].uniformData.set(mvpMatrix);
                    lights[i].uniformData.set(lights[i].position, 16); // offset is 16 for size of mvp
                    lights[i].uniformData.set(lights[i].color, 20);
                    lights[i].uniformData.set(lights[i].attenuate, 24); 
                
                    gl.bindBufferBase(gl.UNIFORM_BUFFER, 0, lights[i].uniformBuffer);
                    gl.bufferData(gl.UNIFORM_BUFFER, lights[i].uniformData, gl.STATIC_DRAW);
                    gl.drawElements(gl.TRIANGLES, numSphereElements, gl.UNSIGNED_SHORT, 0);
                }
            }
        } else {
            for (var i = 0; i < show_lights; ++i) {
                if(i > 0){
                    lights[i].position[2] += 0.01 * Math.cos(time);
                    utils.xformMatrix(mvpMatrix, lights[i].position, vec3.fromValues(0,0,0),lights[i].scale);
                    mat4.multiply(mvpMatrix, viewProjMatrix, mvpMatrix);
                    lights[i].uniformData.set(mvpMatrix);
                    lights[i].uniformData.set(lights[i].position, 16); // offset is 16 for size of mvp
                    lights[i].uniformData.set(lights[i].color, 20);
                    lights[i].uniformData.set(lights[i].attenuate, 24); 
                }
                gl.bindBufferBase(gl.UNIFORM_BUFFER, 0, lights[i].uniformBuffer);
                gl.bufferData(gl.UNIFORM_BUFFER, lights[i].uniformData, gl.STATIC_DRAW);
                gl.drawElements(gl.TRIANGLES, numSphereElements, gl.UNSIGNED_SHORT, 0);
                
            }
        }
        time += 0.01;
    }
    requestAnimationFrame(draw);
}

function draw_to_screen(gl, program){
    gl.bindFramebuffer(gl.FRAMEBUFFER, null);
    gl.useProgram(program);
}

function make_texture(gl, target, i){
    gl.bindTexture(gl.TEXTURE_2D, target);
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
    gl.texStorage2D(gl.TEXTURE_2D, 1, gl.RGBA16F, gl.drawingBufferWidth, gl.drawingBufferHeight);
    gl.framebufferTexture2D(gl.FRAMEBUFFER, gl.COLOR_ATTACHMENT0 + i, gl.TEXTURE_2D, target, 0);
}

requestAnimationFrame(draw);