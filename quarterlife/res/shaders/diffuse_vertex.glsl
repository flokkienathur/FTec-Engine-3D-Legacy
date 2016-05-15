

uniform mat4 ftecProjectionMatrix;
uniform mat4 ftecShadowMatrix;


uniform vec3 ftecLightDirection;

uniform int ftecRenderType;
uniform bool ftecTextureEnabled;
uniform bool ftecShadowsEnabled;

varying vec2 vTexCoord;
varying vec3 vNormal;
varying vec4 vColor;
varying vec4 vVertex;
varying float vLightIntensity;


void main(void){
	vTexCoord = gl_MultiTexCoord0;
	vLightIntensity = 1;
	
	if(ftecRenderType == 0){ // no shading
		vColor = gl_Color;
		vNormal = gl_Normal;
		gl_Position = ftecProjectionMatrix * gl_Vertex;
	}
	else if(ftecRenderType == 1){ // diffuse
		float lightIntensity = clamp(-dot(gl_Normal,ftecLightDirection) * 0.9, 0.0, 0.9) + 0.1;
	
		vColor = gl_Color;
		vNormal = gl_Normal;
		vLightIntensity = lightIntensity * 2;
		vColor.a = gl_Color.a;
		
		if(ftecShadowsEnabled)
			vVertex = gl_Vertex;
	
		gl_Position = ftecProjectionMatrix * gl_Vertex;
	}
	
	
	
	else if(ftecRenderType == 1001){ //depth rendering
		vColor = vec4(0.0);
		
		vec4 temp = ftecShadowMatrix * gl_Vertex;
		
		float d = (temp.z + 1) / 2;
		vColor = vec4(d);
		vColor.a = 1.0;
		
		gl_Position = temp;
	}
	
	
	
}