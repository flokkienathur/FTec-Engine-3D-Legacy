package com.rt96h.audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

import com.rt96h.math.Vector3;
import com.rt96h.utils.Converter;

public class AudioSource {
	
	private AudioFX current;
	
	private Vector3 position;
	private Vector3 velocity;
	
	private float pitch, gain;
	
	private int id;
	
	protected AudioSource(){
		id = AL10.alGenSources();
		setPosition(Vector3.zero.clone());
		setVelocity(Vector3.zero.clone());
		setPitch(1);
		setGain(1);
	}
	
	public void play(AudioFX a){
		setCurrent(a);
		play();
	}
	
	public void play(){
		AL10.alSourcePlay(id);
	}
	
	public void stop(){
		AL10.alSourceStop(id);
	}
	
	public void pause(){
		AL10.alSourcePause(id);
	}
	
	public boolean isPlaying(){
		return (AL10.alGetSourcei(id, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING);
	}
	
	public void setCurrent(AudioFX fx){
		current = fx;
		AL10.alSourcei(id, AL10.AL_BUFFER, current.getID());
	}
	
	public float getSecondOffset(){
		return AL10.alGetSourcef(id, AL11.AL_SEC_OFFSET);
	}
	
	public void setSecondOffset(float f){
		AL10.alSourcef(id, AL11.AL_SEC_OFFSET, f);
	}
	
	public AudioFX getCurrent(){
		return current;
	}
	
	public int getID(){
		return id;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
		AL10.alSource(id, AL10.AL_POSITION, Converter.toFloatBuffer(position));
	}

	public Vector3 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
		AL10.alSource(id, AL10.AL_VELOCITY, Converter.toFloatBuffer(velocity));
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
		AL10.alSourcef(id, AL10.AL_PITCH, pitch);
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		this.gain = gain;
		AL10.alSourcef(id, AL10.AL_GAIN, gain * AudioSystem.globalVolume);
	}
	
}
