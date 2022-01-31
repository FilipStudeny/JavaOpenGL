#type vertex
#version 330 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;

out vec4 fColor;

uniform mat4 worldMatrix;
uniform mat4 projectionMatrix;

void main(){
    fColor = aColor;
    gl_Position = projectionMatrix * worldMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;

out vec4 Color;

void main(){
    Color = fColor;
}
