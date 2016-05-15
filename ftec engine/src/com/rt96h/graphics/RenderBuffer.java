package com.rt96h.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public class RenderBuffer {
	
	private int width,height;
	private int bufferID, textureID, depthBufferID;
	private Texture texture;
	
	public RenderBuffer(int width, int height){
		this.width = width;
		this.height = height;
		
		bufferID = EXTFramebufferObject.glGenFramebuffersEXT();
		depthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
		textureID = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthBufferID);
		bind();

		recalculateBufferData();
		
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBufferID);
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, width, height);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBufferID);
		
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, textureID, 0);
		
		unbind();
	}
	
	public void bind(){
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, bufferID);
		GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		GL11.glViewport(0, 0, width, height);
	}
	
	public void unbind(){
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);

		GL11.glPopAttrib();
	}
	
	private void recalculateBufferData(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureID);
		
		ByteBuffer pixels = ByteBuffer.allocateDirect(width * height * 4);
		for(int i = 0; i < width * height * 4; i++){
			pixels.put((byte)140);
		}
		pixels.flip();
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D,0,GL11.GL_RGBA,width,height,0,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE,pixels);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

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
	
}
