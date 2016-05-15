package com.rt96h.audio;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import com.rt96h.math.Vector3;
import com.rt96h.utils.Converter;

public class AudioListener {
	
	private Vector3 position;
	private Vector3 velocity;
	private Vector3 orientation;
	
	protected AudioListener(){
		setPosition(Vector3.zero.clone());
		setVelocity(Vector3.zero.clone());
		setOrientation(new Vector3(0, 0,-1.0f));
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
		AL10.alListener(AL10.AL_POSITION, Converter.toFloatBuffer(position));
	}

	public Vector3 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
		AL10.alListener(AL10.AL_VELOCITY, Converter.toFloatBuffer(velocity));
	}

	public Vector3 getOrientation() {
		return orientation;
	}

	public void setOrientation(Vector3 orientation) {
		this.orientation = orientation;
		FloatBuffer b = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(orientation.x).put(orientation.y).put(orientation.z).put(0).put(1).put(0);
		b.flip();
		AL10.alListener(AL10.AL_ORIENTATION, b);
	}
}
