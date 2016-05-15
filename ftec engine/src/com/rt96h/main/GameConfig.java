package com.rt96h.main;

import java.awt.Toolkit;

public final class GameConfig {
	private int width,height,framerate;
	private boolean fullscreen, vsync, resizeable,debugMode = true;
	private String title;
	
	private String modelsFile = "models.xml";
	private String texturesFile = "textures.xml";
	private String worldsFile = "worlds.xml";
	

	public GameConfig(){
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		init(w,h,60,true,true,false);
	}
	public GameConfig(int w, int h){
		init(w,h,60,false,false,true);
	}
	
	public void init(int w, int h, int fps, boolean full, boolean vsync,boolean size){
		setWidth(w);
		setHeight(h);
		setFramerate(fps);
		setFullscreen(full);
		setVsync(vsync);
		setResizeable(true);
	}
	public int getFramerate() {
		return framerate;
	}
	public void setFramerate(int framerate) {
		this.framerate = framerate;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	public boolean isVsync() {
		return vsync;
	}
	public void setVsync(boolean vsync) {
		this.vsync = vsync;
	}
	public boolean isResizeable() {
		return resizeable;
	}
	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModelsFile() {
		return modelsFile;
	}
	public void setModelsFile(String modelsFile) {
		this.modelsFile = modelsFile;
	}
	public String getTexturesFile() {
		return texturesFile;
	}
	public void setTexturesFile(String texturesFile) {
		this.texturesFile = texturesFile;
	}
	public String getWorldsFile() {
		return worldsFile;
	}
	public void setWorldsFile(String worldsFile) {
		this.worldsFile = worldsFile;
	}
	public boolean isDebugMode() {
		return debugMode;
	}
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
}
