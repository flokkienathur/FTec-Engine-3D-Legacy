package com.rt96h.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTFramebufferObject;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL14.*;
import org.lwjgl.opengl.GL11;

public class Buffer {
	
	private int width,height;
	private int bufferID, textureID, depthBufferID;
	private Texture texture,depthTexture;
	
	public Buffer(int width, int height){
		this.width = width;
		this.height = height;
		
		bufferID = EXTFramebufferObject.glGenFramebuffersEXT();
		depthBufferID = GL11.glGenTextures();
		textureID = GL11.glGenTextures();
		
		bind();

		recalculateBufferData();
		
		recalculateDepthData();
		
		EXTFramebufferObject.glFramebufferTexture2DEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
				EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, 
				GL11.GL_TEXTURE_2D, 
				depthBufferID,
				0);
		
		EXTFramebufferObject.glFramebufferTexture2DEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 
				EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, 
				GL11.GL_TEXTURE_2D, 
				textureID, 
				0);
		
		unbind();
	}
	
	public void bind(){
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, bufferID);
		GL11.glPushAttrib(GL_ALL_ATTRIB_BITS);
		GL11.glViewport(0, 0, width, height);
	}
	
	public void unbind(){
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);

		GL11.glPopAttrib();
	}
	
	private void recalculateDepthData(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthBufferID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		//glTextureFilter
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_DEPTH_TEXTURE_MODE, GL_INTENSITY);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, width,height, 0, GL_DEPTH_COMPONENT, GL_INT,(ByteBuffer) null);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		depthTexture = new InverseTexture(width,height,depthBufferID,"");
	}
	
	private void recalculateBufferData(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureID);
		
		ByteBuffer pixels = ByteBuffer.allocateDirect(width * height * 4);
		for(int i = 0; i < width * height * 4; i++){
			pixels.put((byte)140);
		}
		pixels.flip();

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width,height, 0, GL_BGRA, GL_UNSIGNED_BYTE, pixels);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
		
		texture = new InverseTexture(width,height,textureID,"");
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Texture getTexture(){
		return texture;
	}

	public Texture getDepthTexture() {
		return depthTexture;
	}
	
}
