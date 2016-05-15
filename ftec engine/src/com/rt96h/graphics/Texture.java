package com.rt96h.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class Texture {
	
	private String fileName = "";
	private int width,height,id;

	protected Texture(int width, int height, ByteBuffer colors, String name){
		this.fileName = (name);
		this.width = width;
		this.height = height;
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		id = GL11.glGenTextures();
		
		bind();
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width,height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, colors);
		
		unbind();
	}
	protected Texture(int width, int height, int id, String name){
		this.fileName = (name);
		
		this.setWidth(width);
		this.setHeight(height);
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getId() {
		return id;
	}
	
	public float getFracX(){
		return 0;
	}
	public float getFracY(){
		return 0;
	}
	public float getFracWidth(){
		return 1;
	}
	public float getFracHeight(){
		return 1;
	}
	
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		GL11.glMatrixMode(GL11.GL_TEXTURE);


		GL11.glOrtho(-1, 1, -1, 1, 1,-1);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void unbind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
	}
	
	public void dispose(){
		
	}
	
}
