package com.rt96h.graphics.camera;

import java.lang.reflect.Constructor;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

import com.rt96h.graphics.Renderer;
import com.rt96h.graphics.SpriteBatch;
import com.rt96h.math.Matrix4;
import com.rt96h.math.Vector3;
import com.rt96h.utils.NodeHelper;

public abstract class Camera {
	
	public Vector3 position = Vector3.zero.clone();
	public Vector3 rotation = Vector3.zero.clone();
	
	public float mouseX, mouseY;
	
	public Camera(){
		
	}
	
	public void apply(SpriteBatch batch){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1, 1, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public void update(){
		mouseX = Mouse.getX();
		mouseY = Renderer.getHeight() - Mouse.getY();
	}
	
	public static Camera loadCamera(Element e) {
		try {
			String cls = NodeHelper.getString(e, "class",
					"com.rt96h.graphics.camera.PerspectiveCamera");
			Class<?> cl = Class.forName(cls);
			Constructor<?> cons = cl.getConstructor(Float.TYPE,Float.TYPE);
			
			Camera cam = null;
			
			float fov = NodeHelper.getFloat(e, "fov", 60);
			float aspect = NodeHelper.getFloat(e, "aspect", 16f/9f);

			float width = NodeHelper.getFloat(e, "width", 0);
			float height = NodeHelper.getFloat(e, "height", 0);
			
			if(width <= 0 || height <= 0){
				cam = (Camera) cons.newInstance(fov, aspect);
			}else{
				cam = (Camera) cons.newInstance(width,height);
			}

			Vector3 pos = new Vector3(
					NodeHelper.getFloat(e, "x", 0),
					NodeHelper.getFloat(e, "y", 0),
					NodeHelper.getFloat(e, "z", 0)
					);

			Vector3 rot = new Vector3(
					NodeHelper.getFloat(e, "rotation-x", 0),
					NodeHelper.getFloat(e, "rotation-y", 0),
					NodeHelper.getFloat(e, "rotation-z", 0)
					);
			
			cam.position = pos;
			cam.rotation = rot;
			return cam;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public Matrix4 getViewMatrix(){
		return new Matrix4();
	}
	

	public Matrix4 getProjectionMatrix() {
		return new Matrix4();
	}
	
}
