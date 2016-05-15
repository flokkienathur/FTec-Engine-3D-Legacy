package com.rt96h.graphics;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Renderer {

	public static void setViewPort(int w, int h){
		GL11.glViewport(0, 0, w, h);
	}
	
	public static void clearColor(float r, float g, float b, float a){
		GL11.glClearColor(r,g,b,a);
	}
	
	public static void clear(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void clearColor(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static void clearDepth(){
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void alphaBlend(boolean enable, float cuttoff){
		if(enable){
			GL11.glEnable(GL11.GL_ALPHA);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}else{
			GL11.glEnable(GL11.GL_ALPHA);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.5f);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	public static void blendMode(BlendMode mode){
		mode.apply();
	}
	
	public static void backfaceCulling(boolean cul){
		if(cul){
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		}else{
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_NONE);
		}
	}
	
	public static float getWidth(){
		return Display.getWidth();
	}
	
	public static float getHeight(){
		return Display.getHeight();
	}
	
	public static void enableTexture(boolean enable){
		if(enable)
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		else
			GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public static SpriteBatch createBatch(int vertexcount){
		return new SpriteBatch(vertexcount);
	}
}
