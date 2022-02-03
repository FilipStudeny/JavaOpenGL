#type vertex
#version 330 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 textureCoords;

out vec4 fColor;
out vec2 ftextureCoords;

uniform mat4 worldMatrix;
uniform mat4 projectionMatrix;

void main(){
    fColor = aColor;
    ftextureCoords = textureCoords;
    gl_Position = projectionMatrix  * worldMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 ftextureCoords;

out vec4 Color;

uniform float uTime;
uniform sampler2D textureSampler;

void main(){

    Color = texture(textureSampler,ftextureCoords);

    /* NOISE
    float noise = fract(sin(dot(fColor.xy, vec2(12.9898,78.233))) * 43758.5453);
    Color = fColor * noise;

    BLACK AND WHITE
    float avrg = (fColor.r + fColor.b + fColor.g) / 3;
    Color = vec4(avrg,avrg,avrg,1);
    */

   // Color = sin(uTime) * fColor; - CHANGE COLOR
}
