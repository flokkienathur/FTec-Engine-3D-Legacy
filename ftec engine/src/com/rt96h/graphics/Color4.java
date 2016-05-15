package com.rt96h.graphics;

public class Color4 {

	public static final Color4 white = new Color4(1,1,1,1);
	public static final Color4 black = new Color4(0,0,0,1);
	
	public static final Color4 red = new Color4(1,0,0,1);
	public static final Color4 green = new Color4(0,1,0,1);
	public static final Color4 blue = new Color4(0,0,1,1);
	
	public static final Color4 pink = new Color4(1,0,1,1);
	public static final Color4 yellow = new Color4(1,1,0,1);

	public static final Color4 lightGray = new Color4(0.7f,0.7f,0.7f,1);
	public static final Color4 gray = new Color4(0.5f,0.5f,0.5f,1);
	public static final Color4 darkGray = new Color4(0.3f,0.3f,0.3f,1);
	
	public float r, g,b,a;
	
	public Color4(float r, float g, float b, float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color4 clone(){
		return new Color4(r,g,b,a);
	}
}
