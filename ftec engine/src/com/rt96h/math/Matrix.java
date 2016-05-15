package com.rt96h.math;

public class Matrix {
	public final int width,height;
	protected float data[];
	
	public Matrix(int width, int height, float data[]){
		this.width =  width;
		this.height = height;
		this.data = data;
	}
	
	public Matrix combine(Matrix other){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x ++){
				
				int sum = 0;
				
				for(int i = 0; i < width; i++){
					sum += data[i + y * width] * other.data[x + i * width];
				}
				
				data[x + y * width] = sum;
			}
		}
		
		return this;
	}
	
	public float[] getData(){
		return data;
	}
	
	public String toString(){
		String s = "";
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x ++){
				
				s += "| " + data[x + y * width]+ " |";
			}
			s += "\n";
		}
		
		return s;
	}
}
