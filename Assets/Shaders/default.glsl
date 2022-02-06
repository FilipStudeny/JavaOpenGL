#type vertex
#version 330 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 textureCoords;


in vec3 normal;

out vec4 fColor;
out vec2 ftextureCoords;
out vec3 fragNormal;
out vec3 fragPos;

uniform mat4 worldMatrix;
uniform mat4 projectionMatrix;

void main(){

    vec4 worldPosition =  worldMatrix * vec4(aPos, 1.0);
    fColor = aColor;

    ftextureCoords = textureCoords;
    gl_Position = projectionMatrix  * worldPosition;

    fragNormal = normalize(worldPosition).xyz;
    fragPos = worldPosition.xyz;
}

    #type fragment
    #version 330 core

in vec4 fColor;
in vec2 ftextureCoords;

in vec3 fragNormal;
in vec3 fragPos;

out vec4 Color;

uniform float uTime;
uniform sampler2D textureSampler;

uniform vec4 ambient;
uniform vec4 specular;
uniform vec4 diffuse;
uniform float reflectance;

uniform vec3 ambientLight;
uniform int hasTexture;
uniform float specularPower;

//DIRECTIONALlight
uniform vec3 directionalLightColour;
uniform vec3 directionalLightDirection;
uniform float directionalLightIntensity;

//POINTLIGHT
uniform vec3 pointLightColour;
uniform vec3 pointLightPosition;
uniform float pointLightIntensity;
uniform float constant;
uniform float linear;
uniform float exponent;

//SPOTLIGHT
uniform vec3 coneDirection;
uniform float cutOff;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void SetupColours(){
    if(hasTexture == 1){
        //Color = texture(textureSampler,ftextureCoords);
        ambientC = texture(textureSampler,ftextureCoords);
        diffuseC = ambientC;
        specularC = ambientC;
    }else{
        ambientC = ambient;
        specularC = specular;
        diffuseC = diffuse;
    }
}

vec4 CalcLight(vec3 lightColour, float intensity, vec3 position, vec3 toLightDir, vec3 normal){
    vec4 diffuseColour = vec4(0,0,0,0);
    vec4 specColour = vec4(0,0,0,0);

    //DiffuseLight
    float diffuseFactor = max(dot(normal, toLightDir), 0.0);
    diffuseColour = diffuseC * vec4(lightColour, 1.0) * intensity * diffuseFactor;

    //Specular colour
    vec3 cameraDir = normalize(-position);
    vec3 fromLightDir = -toLightDir;
    vec3 reflectedLight = normalize(reflect(fromLightDir, normal));
    float specularFactor = max(dot(cameraDir, reflectedLight), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specColour = specularC * intensity * specularFactor * reflectance * vec4(lightColour, 1.0);

    return (diffuseColour + specColour);
}

vec4 CalcDirectionalLight(vec3 lightColour, float intensity, vec3 position, vec3 toLightDir, vec3 normal){
    return CalcLight(lightColour,intensity,position,normalize(toLightDir),normal);
}

vec4 CalcPointLight(vec3 lightColour,float intensity,vec3 lightPosition, vec3 position, vec3 normal){
    vec3 lightDir = lightPosition - position;
    vec3 toLightDir = normalize(lightDir);
    vec4 lightColor = CalcLight(lightColour, intensity, position, toLightDir, normal);

    float distance = length(lightDir);
    float attenuation = constant + linear * distance + exponent * distance * distance;

    return lightColor / attenuation;
}

vec4 CalcSpotlight(vec3 pointLightPosition,vec3 position, vec3 normal){
    vec3 lightDir = pointLightPosition - position;
    vec3 toLightDir = normalize(lightDir);
    vec3 fromLightDir = -toLightDir;
    float spotAlfa = dot(fromLightDir, normalize(coneDirection));

    vec4 colour = vec4(0,0,0,0);

    if(spotAlfa > cutOff){
        colour = CalcPointLight(pointLightColour, pointLightIntensity, position, fragPos,normal);
        colour *= (1.0 - (1.0 - spotAlfa) / (1.0 - cutOff));
    }

    return colour;
}

void main(){

    SetupColours();

    vec4 diffuseSpecComp = CalcDirectionalLight(directionalLightColour, directionalLightIntensity, fragPos, directionalLightDirection, fragNormal);
    diffuseSpecComp += CalcPointLight(pointLightColour, pointLightIntensity, pointLightPosition, fragPos,fragNormal);
    diffuseSpecComp += CalcSpotlight(pointLightPosition, pointLightPosition, fragNormal);
    Color = ambientC * vec4(ambientLight, 1) + diffuseSpecComp;

    //TEXTURE    Color = texture(textureSampler,ftextureCoords);


    /* NOISE
    float noise = fract(sin(dot(fColor.xy, vec2(12.9898,78.233))) * 43758.5453);
    Color = fColor * noise;
    BLACK AND WHITE
    float avrg = (fColor.r + fColor.b + fColor.g) / 3;
    Color = vec4(avrg,avrg,avrg,1);
    */

    // Color = sin(uTime) * fColor; - CHANGE COLOR
}