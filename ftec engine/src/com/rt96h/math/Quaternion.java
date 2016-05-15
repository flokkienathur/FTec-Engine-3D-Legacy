package com.rt96h.math;

import static com.rt96h.math.Mathf.*;

public class Quaternion { // Credit goes to 'thebennybox'
						// (http://www.youtube.com/user/thebennybox)
	public float x;
	public float y;
	public float z;
	public float w;

	public Quaternion(float w, float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public float length() {
		return Mathf.sqrt(x * x + y * y + z * z + w * w);
	}

	public Quaternion normalize() {
		float l = length();

		x /= l;
		y /= l;
		z /= l;
		w /= l;
		
		return this;
	}

	public Quaternion conjugate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		x = x_;
		y = y_;
		z = z_;
		w = w_;
		
		return this;
	}

	public Quaternion mul(Vector3 r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		x = x_;
		y = y_;
		z = z_;
		w = w_;
		
		return this;
	}
	
	public final Vector3 rotate(Vector3 v) {
        float q00 = 2.0f * x * x;
        float q11 = 2.0f * y * y;
        float q22 = 2.0f * z * z;

        float q01 = 2.0f * x * y;
        float q02 = 2.0f * x * z;
        float q03 = 2.0f * x * w;

        float q12 = 2.0f * y * z;
        float q13 = 2.0f * y * w;

        float q23 = 2.0f * z * w;

        return new Vector3((1.0f - q11 - q22) * v.x + (q01 - q23) * v.y
                        + (q02 + q13) * v.z, (q01 + q23) * v.x + (1.0f - q22 - q00) * v.y
                        + (q12 - q03) * v.z, (q02 - q13) * v.x + (q12 + q03) * v.y
                        + (1.0f - q11 - q00) * v.z);
}
	
	public Vector3 eulerAngles() {
        float roll, pitch, yaw;
        float test = x * y + z * w;
        if (test > 0.499) { // singularity at north pole
                pitch = 2 * atan2(x, w);
                yaw = PI / 2;
                roll = 0;
                return new Vector3(roll, pitch, yaw);
        }
        if (test < -0.499) { // singularity at south pole
                pitch = -2 * atan2(x, w);
                yaw = -PI / 2;
                roll = 0;
                return new Vector3(roll, pitch, yaw);
        }
        float sqx = x * x;
        float sqy = y * y;
        float sqz = z * z;
        pitch = atan2(2 * y * w - 2 * x * z, 1 - 2 * sqy - 2 * sqz);
        yaw = asin(2 * test);
        roll = atan2(2 * x * w - 2 * y * z, 1 - 2 * sqx - 2 * sqz);
        return new Vector3(roll, pitch, yaw);
}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
	
	public static Quaternion axisAngle(Vector3 r, float theta){
		return axisAngle(r.x, r.y, r.z, theta);
	}
	
	public static Quaternion axisAngle(float x, float y, float z, float theta){
		float c = cos(toRadians(theta / 2));
		float s = sin(toRadians(theta / 2));
		
		return new Quaternion(
				c,
				s * x,
				s * y,
				s * z
				);
	}
}