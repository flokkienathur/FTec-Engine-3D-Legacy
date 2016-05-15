package com.rt96h.math;

public class Vector3 {
	
	public static Vector3 zero = new Vector3(0,0,0);
	
	public float x, y, z;
	
	public Vector3(){
		x = 0;
		y = 0;
		z = 0;
	}

	public Vector3(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 a){
		this.x = a.x;
		this.y = a.y;
		this.z = a.z;
	}

	public Vector3 sub(Vector3 other){
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}
	
	public Vector3 set(Vector3 s){
		this.x = s.x;
		this.y = s.y;
		this.z = s.z;
		return this;
	}
	
	public Vector3 add(Vector3 other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	public Vector3 normalize(){
		float l = length();

		if(l <= 0)
			return this;
		
		x /= l;
		y /= l;
		z /= l;
		
		return this;
	}
	
	public Vector3 mul(float l){
		x *= l;
		y *= l;
		z *= l;
		return this;
	}
	
	public Vector3 cross(Vector3 a, Vector3 b){
		x = a.y * b.z - a.z * b.y;
		y = a.z * b.x - a.x * b.z;
		z = a.x * b.y - a.y * b.x;
		return this;
	}
	
	public float dot(Vector3 o){
		return x * o.x + y * o.y + z * o.z;
	}
	
	public float length(){
		return Mathf.sqrt(x*x + y*y + z*z);
	}
	
	public float invLength(){
		return Mathf.invSqrt(x*x + y*y + z*z);
	}
	
	public Vector3 clone(){
		return new Vector3(x,y,z);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getZ(){
		return z;
	}
	
	public String toString(){
		return x + " | " + y + " | " + z;
	}
	
	public static float dot(Vector3 a, Vector3 b){
		return a.dot(b);
	}
}
