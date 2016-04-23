#version 330

smooth in vec2 theColor;
//smooth in vec3 theColor;
out vec4 outputColor;

uniform sampler2D texture_sampler;

void main()
{
	outputColor = texture(texture_sampler, theColor);
//	outputColor = vec4(theColor, 1.0);
}
