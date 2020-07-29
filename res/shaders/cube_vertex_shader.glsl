#version 400 core

in vec3 vertex;
in vec3 normal;

out vec3 color;

out vec3 surface_normal;
out vec3 to_light_vector;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

uniform vec3 light_position;

uniform vec3 cube_color;

void main(void) {

    vec4 world_position = transformation_matrix * vec4(vertex, 1.0);
    gl_Position = projection_matrix * view_matrix * world_position;

    surface_normal = normalize((transformation_matrix * vec4(normal, 1.0)).xyz);
    to_light_vector = normalize(light_position - world_position.xyz);

    color = mix(cube_color, cube_color / 2, vertex.y + 0.5);
}