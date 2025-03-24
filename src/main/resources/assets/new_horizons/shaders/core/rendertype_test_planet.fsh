#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int EndPortalLayers;

in vec4 texProj0;
out vec4 fragColor;

void main() {
fragColor = textureProj(Sampler0,texProj0);
}

