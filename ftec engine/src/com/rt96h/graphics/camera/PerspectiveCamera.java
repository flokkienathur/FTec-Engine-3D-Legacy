package com.rt96h.graphics.camera;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.rt96h.graphics.SpriteBatch;
import com.rt96h.math.Matrix4;
import com.rt96h.math.Vector3;

public class PerspectiveCamera extends Camera{
	
	private float fov, asp;
	
	public PerspectiveCamera(float fov, float asp){
		System.out.println(fov + " " + asp);
		this.fov = fov;
		this.asp = asp;
		position = new Vector3(0,0,0);
	}
	
	public void apply(SpriteBatch batch){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(fov, asp, 0.01f, 1000);
		
		GL11.glRotatef(rotation.z, 0, 0, 1);
		
		GL11.glRotatef(rotation.x, 1, 0, 0);
		GL11.glRotatef(rotation.y + 180, 0, 1, 0);
		
		GL11.glTranslatef(-position.x, -position.y, -position.z);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
	}

	public Matrix4 getProjectionMatrix() {
		return Matrix4.getProjectionMatrix(fov, asp, 0.01f,1000).rotateX(-rotation.x).rotateY(rotation.y).translate(-position.x, -position.y, -position.z);
	}
}
