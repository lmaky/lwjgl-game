#version 330

uniform mat4 projModelMatrix;

layout (location = 0) in vec3 inPosition;
//layout (location = 1) in vec3 inColour;
layout (location = 2) in vec2 inTexCoord;


out vec2 outTexCoord;
//smooth out vec2 outTexCoord;
//out vec3 outColour;

void main()
{
	gl_Position = projModelMatrix * vec4(inPosition, 1.0);
//	gl_Position = vec4(inPosition, 1.0);
	outTexCoord = inTexCoord;
//	outColour = inColour;
}
