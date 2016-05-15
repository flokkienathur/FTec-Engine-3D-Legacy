package com.rt96h.math;

public class Vector2 {
	
	public static final Vector2 zero = new Vector2(0,0);
	
	public float x, y;
	
	public Vector2(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2 clone(){
		return new Vector2(x,y);
	}
	
	public Vector2 mul(float f){
		x *= f;
		y *= f;
		return this;
	}
	
	public Vector2 normalize(){
		float l = length();
		if(l <= 0)
			return this;
		x /= l;
		y /= l;
		return this;
	}
	
	public float length(){
		return Mathf.sqrt(x*x + y*y);
	}
	
	
	public String toString(){
		return x + " | " + y;
	}
}
