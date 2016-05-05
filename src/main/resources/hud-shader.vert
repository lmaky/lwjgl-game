#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec2 inTexCoord;

out vec2 outTexCoord;

void main()
{
	gl_Position = projectionMatrix * modelMatrix * vec4(inPosition, 1.0);
	outTexCoord = inTexCoord;
}
