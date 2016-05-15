package com.rt96h.math;

public class Ray {
	
	protected Vector3 start;
	protected Vector3 direction;
	
	public Ray(Vector3 s, Vector3 dir){
		start = s;
		direction = dir;
	}
	
	public Vector3 getDirection(){
		return direction;
	}
	
	public Vector3 getStart(){
		return start;
	}
}
