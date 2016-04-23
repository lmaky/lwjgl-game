#version 330

//smooth in vec2 outTexCoord;
in vec2 outTexCoord;
//in vec3 outColour;
//in vec3 mvPos;

out vec4 outputColor;

uniform sampler2D texture_sampler;
uniform vec3 colour;

void main()
{
	outputColor = vec4(colour, 1) * texture(texture_sampler, outTexCoord);
//	outputColor = vec4(outColour, 1.0);
}
