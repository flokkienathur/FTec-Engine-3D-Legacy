package com.rt96h.graphics;

import org.lwjgl.opengl.GL13;

import com.rt96h.graphics.shader.Shader;

public class Material {
	
	private Shader shader;
	private int textureCount;
	private Texture[] texture;			//ftecTexture0-32
	
	private boolean enableTexture;		//ftecTextureEnabled
	private boolean enableLighting;		//ftecLightingEnabled
	private boolean enableShadows;		//ftecShadowsEnabled

	private int renderType;				//ftecRenderType
	
	
	public Material(Shader shader, Texture... texture){
		this.texture = texture;
		this.shader = shader;
		textureCount = texture.length;
		setup();
	}
	
	
	public Shader getShader() {
		return shader;
	}


	public Material setShader(Shader shader) {
		this.shader = shader;
		return this;
	}


	public Texture[] getTexture() {
		return texture;
	}


	public Material setTexture(Texture[] texture) {
		this.texture = texture;
		return this;
	}


	public boolean isEnableTexture() {
		return enableTexture;
	}


	public Material setEnableTexture(boolean enableTexture) {
		this.enableTexture = enableTexture;
		return this;
	}


	public boolean isEnableLighting() {
		return enableLighting;
	}


	public Material setEnableLighting(boolean enableLighting) {
		this.enableLighting = enableLighting;
		return this;
	}


	public boolean isEnableShadows() {
		return enableShadows;
	}


	public Material setEnableShadows(boolean enableShadows) {
		this.enableShadows = enableShadows;
		return this;
	}


	public int getRenderType() {
		return renderType;
	}


	public Material setRenderType(int renderType) {
		this.renderType = renderType;
		return this;
	}


	public void use(SpriteBatch batch){
		//use the shader program and pass the boolean data
		setup();
		
		//send matrices to the shader
		shader.setUniformf("ftecModelMatrix",batch.matrix);
		shader.setUniformf("ftecViewMatrix", batch.getCamera().getViewMatrix());
		shader.setUniformf("ftecProjectionMatrix", batch.getCamera().getProjectionMatrix());
		
		//send textures to the shader
		for(int i = 0; i < textureCount; i++){
			//set the active texture, bind it, and send it to the shader
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			texture[i].bind();
			shader.setUniformi("ftecTexture"+i,i);
		}
		
	}
	
	public void setup(){
		shader.use();
		shader.setUniformb("ftecTextureEnabled", enableTexture);
		shader.setUniformb("ftecLightingEnabled", enableLighting);
		shader.setUniformb("ftecShadowsEnabled", enableShadows);

		shader.setUniformi("ftecRenderType", renderType);
	}
	
	
	public void reset(){
		shader.reset();
		for(int i = textureCount - 1; i >= 0; i--){
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			texture[i].unbind();
		}
	}
	
}
