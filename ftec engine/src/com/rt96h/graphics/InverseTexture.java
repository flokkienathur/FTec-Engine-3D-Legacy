package com.rt96h.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class InverseTexture extends Texture {

	protected InverseTexture(int width, int height, ByteBuffer colors,
			String name) {
		super(width, height, colors, name);
	}
	
	public InverseTexture(int width, int height, int textureID, String name) {
		super(width,height,textureID,name);
	}

	public void bind(){
		super.bind();
		GL11.glMatrixMode(GL11.GL_TEXTURE);


		GL11.glOrtho(-1, 1, 1, -1, 1,-1);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void unbind(){
		super.unbind();
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		
		GL11.glLoadIdentity();
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

}
