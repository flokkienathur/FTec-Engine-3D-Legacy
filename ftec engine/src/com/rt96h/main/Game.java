package com.rt96h.main;

import com.rt96h.graphics.Renderer;

public class Game {
	
	public boolean running = true;
	
	
	public void onInit(int width, int height){
	}
	
	public void onResize(int width, int height){
		Renderer.setViewPort(width, height);
	}

	public void update(){
		
	}
	public void render(){
		Renderer.clear();
	}
	

	public void onClose(){
		running = false;
	}
	

	public boolean isRunning(){
		return running;
	}
}
