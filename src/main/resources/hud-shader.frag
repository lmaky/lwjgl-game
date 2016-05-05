#version 330

uniform sampler2D texture_sampler;
uniform vec3 colour;

in vec2 outTexCoord;

out vec4 outputColor;

void main()
{
	outputColor = vec4(colour, 1) * texture(texture_sampler, outTexCoord);
}
