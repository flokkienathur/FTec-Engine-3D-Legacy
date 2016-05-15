package com.rt96h.audio;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class AudioFX {
	
	private int id;
	
	protected AudioFX(int format, ByteBuffer data, int samplerate){
		id = AL10.alGenBuffers();
		setData(format,data,samplerate);
	}
	
	public void setData(int format, byte[] data, int samplerate){
		setData(format, (ByteBuffer)BufferUtils.createByteBuffer(data.length).put(data),samplerate);
	}
	
	public void setData(int format, ByteBuffer data, int samplerate){
		AL10.alBufferData(id, format, data, samplerate);
	}

	public int getID() {
		return id;
	}
	
	
}
