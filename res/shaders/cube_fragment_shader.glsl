#version 400 core

in vec3 color;

in vec3 surface_normal;
in vec3 to_light_vector;

out vec4 final_color;

uniform float light_intensity;

void main(void) {

    float dot_product = dot(surface_normal, to_light_vector);
    float brightness = max(dot_product, 0.2) * light_intensity;

    final_color = vec4(color, 1.0) * brightness;
}