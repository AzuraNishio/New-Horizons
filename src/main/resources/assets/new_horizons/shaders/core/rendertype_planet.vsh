#version 150

in vec3 Position;
in vec2 UV0;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform sampler2D Sampler1;
uniform float GameTime;

out vec2 texCoord0;
out vec3 normal;

#define HEIGHTMAP_MULTIPLIER 8

void main() {

vec2 tUV0 = UV0;

tUV0. x += GameTime;

tUV0. x = fract(tUV0.x);

    float height = texture(Sampler1, tUV0).r * HEIGHTMAP_MULTIPLIER;

    vec3 displacedPosition = Position + (Normal * height);

    gl_Position = ProjMat * ModelViewMat * vec4(displacedPosition, 1.0);
    normal = Normal;
    texCoord0 = tUV0;
}






















