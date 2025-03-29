#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;

in vec2 texCoord0;
in vec3 normal;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a == 0.0) {
        discard;
    }

    vec3 light = vec3(0.,0.0,1.);

    light = normalize(light);
    
    float lightInstensity = 3.;

    float shadow = max(dot(normal, light), 0.0) * lightInstensity;
    
    color.rgb *= shadow;

    fragColor = color * ColorModulator;
}









