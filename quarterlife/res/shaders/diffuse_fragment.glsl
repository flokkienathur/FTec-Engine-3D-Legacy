
varying vec4 vColor;
varying vec2 vTexCoord;
varying vec4 vVertex;
varying float vLightIntensity;

uniform mat4 ftecShadowMatrix;

uniform int type;

uniform bool ftecTextureEnabled;
uniform bool ftecShadowsEnabled;

uniform sampler2D ftecTexture0;
uniform sampler2D ftecTexture1; //shadowmap

void main(void){
	vec4 c = vColor;
	
	if(ftecTextureEnabled)
		c = texture2D(ftecTexture0, vTexCoord) * vColor;
		
	if(ftecShadowsEnabled){
		vec4 temp = ftecShadowMatrix * vVertex;
		vec2 shadowCoord = vec2((temp.x + 1.0) / 2.0, (temp.y + 1.0) / 2.0);
		float d = (temp.z + 1.0) / 2.0;
		float current = texture2D(ftecTexture1, shadowCoord);
		if(shadowCoord.x <= 0.0 || shadowCoord.x >= 1.0 || shadowCoord.y <= 0.0 || shadowCoord.y >= 1.0){
			c = c;
		} else {
			if(d > current + 0.005){
				vLightIntensity = 0.2;
			}
			c *= vLightIntensity;
			c.a = vColor.a;
		}
	}
	
	gl_FragColor = c;
}