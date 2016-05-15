package com.rt96h.graphics;

import org.lwjgl.opengl.GL11;

public class BlendMode {

	public static final BlendMode normal = new BlendMode(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	public static final BlendMode add = new BlendMode(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	public static final BlendMode subtract = new BlendMode(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
	
	private int in, out;
	
	public BlendMode(int in, int out){
		this.in = in;
		this.out = out;
	}
	
	public void apply(){
		GL11.glBlendFunc(in, out);
	}
}
