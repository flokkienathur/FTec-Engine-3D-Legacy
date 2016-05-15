package com.rt96h.main;

import java.io.File;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.rt96h.audio.AudioSystem;
import com.rt96h.graphics.BlendMode;
import com.rt96h.graphics.Renderer;
import com.rt96h.timer.Time;
import com.rt96h.timer.Timer;

public class Engine {

	public static String PATH_MODEL = "models.xml";
	public static String PATH_TEXTURE = "textures.xml";
	public static String PATH_WORLD = "worlds.xml";
	
	public static void create(final Game g, GameConfig c){
		
		if(c.isDebugMode()){
			System.out.println("[DEBUG] Starting debugger, you can disable this by calling 'GameConfig.setDebugMode(false)'");
		}
		
		Timer debug = new Timer();
		debug.start();
		
		try{
			PATH_MODEL = c.getModelsFile();
			PATH_TEXTURE = c.getTexturesFile();
			PATH_WORLD = c.getWorldsFile();

			if(!new File(PATH_MODEL).exists())
				System.err.println("[WARNING] Model file not found! File: "+PATH_MODEL);
			if(!new File(PATH_TEXTURE).exists())
				System.err.println("[WARNING] Texture file not found! File: "+PATH_TEXTURE);
			if(!new File(PATH_WORLD).exists())
				System.err.println("[WARNING] Worlds file not found! File: "+PATH_WORLD);
			
			if(c.isFullscreen()){
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				
				DisplayMode selected = null;
				
				for(int i = 0; i < modes.length; i++){
					if(modes[i].getWidth() == c.getWidth() && modes[i].getHeight() == c.getHeight()){
						selected = modes[i];
						break;
					}
						
				}
				
				if(selected == null){
					if(modes.length != 0)
						Display.setDisplayMode(new DisplayMode(c.getWidth(),c.getHeight()));
					else
						throw new Exception("No Displays found.");
				}else{
					Display.setDisplayMode(selected);
				}
				Display.setFullscreen(c.isFullscreen());
			}else{
				Display.setDisplayMode(new DisplayMode(c.getWidth(),c.getHeight()));				
			}
			Display.setVSyncEnabled(c.isVsync());
			Display.setResizable(c.isResizeable());
			Display.setTitle(c.getTitle());
			
			Display.create();
			
			AudioSystem.init();
			
			Renderer.alphaBlend(true, 0.5f);
			Renderer.backfaceCulling(true);
			
			BlendMode.normal.apply();
			
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			
			debug.stop();
			
			if(c.isDebugMode())
				System.out.println("[DEBUG] Initializing display in "+ debug.getTimeSeconds() + " seconds.");
			
			debug.start();
			g.onInit(Display.getWidth(),Display.getHeight());
			debug.stop();
			
			if(c.isDebugMode())
				System.out.println("[DEBUG] Initializing game in "+ debug.getTimeSeconds() + " seconds.");

			
			int fps = 0;
			int ups = 0;
			
			long timeCurrent = System.nanoTime();
			long timePrevious = System.nanoTime();
			
			long updateDelta = 0;
			long renderDelta = 0;
			long secondDelta = 0;
			
			final long renderTime = -1;
			final long updateTime = 1000000000 / (c.getFramerate());

			float debugUpdateTimeTotal = 0;
			float debugFrameTimeTotal = 0;
			
			Timer frameDebug = new Timer();
			
			while(g.isRunning()){
				if(Display.isCloseRequested())
					g.onClose();
				if(Display.wasResized())
					g.onResize(Display.getWidth(), Display.getHeight());
				
				timeCurrent = System.nanoTime();
				
				long delta = timeCurrent - timePrevious;
				
				updateDelta += delta;
				renderDelta += delta;
				secondDelta += delta;
				
				if(updateDelta > updateTime){
					Time.deltaTime = updateDelta / 1000000000f;
					Time.runTime += updateDelta / 1000000000f;
					Time.sinTime = (float)Math.sin(Time.runTime);
					Time.cosTime = (float)Math.cos(Time.runTime);
					ups ++;
					
					debug.start();
					
					g.update();
					
					debug.stop();
					
					debugUpdateTimeTotal = debug.getTimeSeconds();
					
					updateDelta = 0;
				}
				
				if(renderDelta > renderTime){
					fps += 1;
					frameDebug.start();
					g.render();
					frameDebug.stop();
					debugFrameTimeTotal += frameDebug.getTimeSeconds();
					renderDelta= 0;
				}
				
				if(secondDelta > 1000000000){
					if(c.isDebugMode()){
						System.out.println("[DEBUG] FPS : "+fps + " || UPS : " + ups);
						System.out.println("[DEBUG] Game update time : "+ debugUpdateTimeTotal / ups);
						System.out.println("[DEBUG] Game frame draw time : "+ debugFrameTimeTotal / fps);
					}
					
					debugUpdateTimeTotal = 0;
					debugFrameTimeTotal = 0;
					
					fps = 0;
					ups = 0;
					secondDelta -= 1000000000;
				}
				
				
				timePrevious = timeCurrent;
				Display.update();
			}
			
			Display.destroy();
			AudioSystem.destroy();
			System.exit(0);
			
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
}
