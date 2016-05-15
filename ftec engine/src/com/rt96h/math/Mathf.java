package com.rt96h.math;

public class Mathf {
	
	public static final float PI = 3.141592653589793f;
	
	public static float invSqrt(float x) {
		return (float) ((float) 1.0 / Math.sqrt(x));
	}
	
	public static float sqrt(float x){
		return (float) Math.sqrt(x);
	}
	
	public static float cos(float x){
		return (float)Math.cos(x);
	}
	
	public static float tan(float x){
		return (float) Math.tan(x);
	}
	
	public static float sin(float x){
		return (float)Math.sin(x);
	}
	
	public static float atan2(float y, float x){
		return (float)Math.atan2(y, x);
	}
	
	public static float asin(float x){
		return (float)Math.asin(x);
	}
	
	public static float acos(float x){
		return (float) Math.acos(x);
	}
	
	public static float toRadians(float x){
		return (float) Math.toRadians(x);
	}
	
	public static float ceil(float x){
		return (float) Math.ceil(x);
	}
	
	public static float toDegree(float x){
		return (float) Math.toDegrees(x);
	}
	
	public static float random(){
		return (float) Math.random();
	}
	
	public static float lerp(float a, float b, float f){
		return (b - a) * f + a;
	}
	
	public static float reverseLerp(float a, float b, float x){
		return (x - a) / (b - a);
	}
	
	public static float pow(float a, float b){
		return (float) Math.pow(a,b);
	}
	
	public static float abs(float d) {
		return (float) Math.abs(d);
	}
	
}
