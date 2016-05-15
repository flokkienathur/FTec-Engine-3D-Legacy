package com.rt96h.audio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

public class AudioSystem {
	
	private static AudioListener listener;
	private static boolean initialized = false;
	
	protected static float globalVolume = 0.025f;
	
	public static void init(){
		try {
			AL.create();
			initialized = true;
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		listener = new AudioListener();
	}
	
	public static void destroy(){
		AL.destroy();
	}
	
	public static AudioListener getListener(){
		return listener;
	}

	public static boolean isInitialized() {
		return initialized;
	}
	
	public static AudioSource createSource(){
		return new AudioSource();
	}
	
	public static AudioFX getAudioFile(String f){
		try{
			File file = new File(f);
			FileInputStream stream = new FileInputStream(file.getAbsoluteFile());
			
			WaveData data = WaveData.create(new BufferedInputStream(stream));
			
			AudioFX fx = new AudioFX(data.format,data.data,data.samplerate);
			
			data.dispose();
			
			return fx;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
