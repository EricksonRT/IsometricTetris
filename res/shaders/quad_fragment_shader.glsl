#version 400 core

in vec2 texture_coords;

out vec4 color;

uniform sampler2D sampler;

void main(void) {

    color = texture(sampler, texture_coords);

    if(color.a == 0) {
        discard;
    }
}