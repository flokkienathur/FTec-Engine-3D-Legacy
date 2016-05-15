package com.rt96h.math;

public class Triangle {

	public static final float SMALL_NUM = 0.00000001f;

	public Vector3 tA, tB, tC;

	public Triangle(Vector3 a, Vector3 b, Vector3 c) {
		this.tA = a;
		this.tB = b;
		this.tC = c;
	}

	public Vector3 intersectsRay(Ray ray) {
		Vector3 i = new Vector3();
		Vector3 u, v, n;
		Vector3 dir, w0;
		float r, a, b;

		u = new Vector3(tB);
		u.sub(tA);
		v = new Vector3(tC);
		v.sub(tA);
		n = new Vector3(); // cross product
		n.cross(u, v);

		if (n.length() == 0) {
			return null;
		}

		dir = new Vector3(ray.getDirection());
		w0 = new Vector3(ray.getStart());
		w0.sub(tA);
		a = -(new Vector3(n).dot(w0));
		b = new Vector3(n).dot(dir);

		if ((float) Math.abs(b) < SMALL_NUM) {
			return null;
		}

		r = a / b;

		i = new Vector3(ray.getStart());
		i.x += r * dir.x;
		i.y += r * dir.y;
		i.z += r * dir.z;
		
		
		//check if behind
		Vector3 x = i.clone().sub(ray.getStart()).normalize();
		if(x.dot(ray.getDirection()) < 0)
			return null;
		
		//barycentric stuff
		
		Vector3 v0 = u;
		Vector3 v1 = v;
		Vector3 v2 = i.clone().sub(tA);
		Vector3 bary = getBarycentric(v0,v1,v2);
		
		if(bary.x >= 0 && bary.x <= 1){
			if(bary.y >= 0 && bary.y <= 1){
				if(bary.z >= 0 && bary.z <= 1){
					return i;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static Vector3 getBarycentric(Vector3 v0, Vector3 v1, Vector3 v2){
		float d00 = Vector3.dot(v0, v0);
	    float d01 = Vector3.dot(v0, v1);
	    float d11 = Vector3.dot(v1, v1);
	    float d20 = Vector3.dot(v2, v0);
	    float d21 = Vector3.dot(v2, v1);
	    float denom = d00 * d11 - d01 * d01;
	    float v = (d11 * d20 - d01 * d21) / denom;
	    float w = (d00 * d21 - d01 * d20) / denom;
	    float u = 1.0f - v - w;
	    
	    return new Vector3(v,w,u);
	}

}
