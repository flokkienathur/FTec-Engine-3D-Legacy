package com.rt96h.graphics.camera;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.rt96h.graphics.SpriteBatch;
import com.rt96h.math.Vector3;

public class OrthagonalPerspectiveCamera extends Camera{
	
	//projection standard
	public static final float FOV = 53.1301023f;
	
	private float width, height;
	
	public OrthagonalPerspectiveCamera(float w, float h){
		width = w;
		height = h;
		position = new Vector3(0,0,0);
	}
	
	public void apply(SpriteBatch batch){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(FOV, width/height, 0.01f, 1000);
		
		GL11.glRotatef(rotation.z, 0, 0, 1);
		
		GL11.glRotatef(rotation.x, 1, 0, 0);
		GL11.glRotatef(rotation.y + 180, 0, 1, 0);
		
		GL11.glScalef(1f/height, 1f/height, 1);
		GL11.glTranslatef(-position.x, -position.y, -position.z);
		
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
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
