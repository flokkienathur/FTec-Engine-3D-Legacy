package com.rt96h.graphics.camera;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.rt96h.graphics.Renderer;
import com.rt96h.graphics.SpriteBatch;
import com.rt96h.math.Vector3;


public class OrthagonalCamera extends Camera{
	
	private float width, height;
	
	public float near = 512, far = -512;
	
	public OrthagonalCamera(float width, float height){
		this.width = width;
		this.height = height;
		position = new Vector3(0,0,0);
	}
	
	public void apply(SpriteBatch batch){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		GL11.glOrtho(-width/2,width/2,height/2,-height/2, near, far);
		
		GL11.glTranslatef(-width/2,-height/2,0);

		GL11.glRotatef(rotation.z, 0, 0, 1);
		
		GL11.glRotatef(rotation.x, 1, 0, 0);
		GL11.glRotatef(rotation.y, 0, 1, 0);
		
		GL11.glTranslatef(-position.x, -position.y, -position.z);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public void update(){
		mouseX = (Mouse.getX() / Renderer.getWidth()) * width;
		mouseY = height - (Mouse.getY() / Renderer.getHeight()) * height;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
}
