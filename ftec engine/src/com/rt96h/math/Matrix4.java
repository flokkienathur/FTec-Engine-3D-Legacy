package com.rt96h.math;

public class Matrix4 extends Matrix{
	
	public Matrix4(float[] data){
		super(4,4,data);
	}
	
	public Matrix4(){
		super(4,4,new float[]{
				1,0,0,0,
				0,1,0,0,
				0,0,1,0,
				0,0,0,1
		});
	}
	
	public Matrix4 scale(float x, float y, float z){
		return combine(new Matrix4(new float[]{
				x,0,0,0,
				0,y,0,0,
				0,0,z,0,
				0,0,0,1
		}));
	}
	
	public Matrix4 translate(float x, float y, float z){
		return combine(new Matrix4(new float[]{
				1,0,0,x,
				0,1,0,y,
				0,0,1,z,
				0,0,0,1
		}));
	}
	
	public Matrix4 translate(Vector3 t){
		return translate(t.x,t.y,t.z);
	}
	
	public Vector3 transform(Vector3 vector){
		Vector3 result = Vector3.zero.clone();
		result.x = transformX(vector);
		result.y = transformY(vector);
		result.z = transformZ(vector);
		return result;
	}
	
	public Matrix4 rotateX(float rotation){
		float c = (float)Math.cos(Math.toRadians(rotation));
		float s = (float)Math.sin(Math.toRadians(rotation));
		return combine(new Matrix4(new float[]{
				1,0,0,0,
				0,c,-s,0,
				0,s,c,0,
				0,0,0,1
		}));
	}
	
	public Matrix4 rotateY(float rotation){
		float c = (float)Math.cos(Math.toRadians(rotation));
		float s = (float)Math.sin(Math.toRadians(rotation));
		return combine(new Matrix4(new float[]{
				c,0,s,0,
				0,1,0,0,
				-s,0,c,0,
				0,0,0,1
		}));
	}
	
	public Matrix4 rotateZ(float rotation){
		float c = (float)Math.cos(Math.toRadians(rotation));
		float s = (float)Math.sin(Math.toRadians(rotation));
		return combine(new Matrix4(new float[]{
				c,-s,0,0,
				s,c,0,0,
				0,0,1,0,
				0,0,0,1
		}));
	}
	
	//Vector transformation
	public float transformX(Vector3 vector){
		return vector.x * data[0 + 0 * width] + vector.y * data[1 + 0 * width] +  vector.z * data[2 + 0 * width] +  data[3 + 0 * width];
	}
	
	public float transformY(Vector3 vector){
		return vector.x * data[0 + 1 * width] + vector.y * data[1 + 1 * width] +  vector.z * data[2 + 1 * width] +  data[3 + 1 * width];
	}
	
	public float transformZ(Vector3 vector){
		return vector.x * data[0 + 2 * width] + vector.y * data[1 + 2 * width] +  vector.z * data[2 + 2 * width] +  data[3 + 2 * width];
	}
	
	public float transformX(float x, float y, float z){
		return x * data[0 + 0 * width] + y * data[1 + 0 * width] +  z * data[2 + 0 * width] +  data[3 + 0 * width];
	}
	
	public float transformY(float x, float y, float z){
		return x * data[0 + 1 * width] + y * data[1 + 1 * width] +  z * data[2 + 1 * width] +  data[3 + 1 * width];
	}
	
	public float transformZ(float x, float y, float z){
		return x * data[0 + 2 * width] + y * data[1 + 2 * width] +  z * data[2 + 2 * width] +  data[3 + 2 * width];
	}
	
	public Matrix4 combine(Matrix4 other){
		Matrix4 temp = clone();
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x ++){
				
				float sum = 0;
				
				for(int i = 0; i < width; i++){
					sum += data[i + y * width] * other.data[x + i * width];
				}
				
				temp.data[x + y * width] = sum;
			}
		}
		
		this.data = temp.data;
		return this;
	}
	
	public Matrix4 clone(){
		float[] data = new float[width * height];
		for(int i = 0; i < data.length; i ++){
			data[i] = this.data[i];
		}
		return new Matrix4(data);
	}

	public void identity() {
		data = new float[]{
				1,0,0,0,
				0,1,0,0,
				0,0,1,0,
				0,0,0,1
		};
	}

	public static Matrix4 getProjectionMatrix(float fov, float asp, float n,
			float f) {
		float uh = 1f / Mathf.tan(Mathf.toRadians(fov / 2));
		float uw = uh;
		
		return new Matrix4(new float[]{
				-uh / asp, 0, 0, 0,
				0, uw , 0, 0,
				0 , 0, (f + n) / (f - n), -2.0f * f * n / (f - n),
				0, 0, 1, 0
				
		});
	}
	
}
