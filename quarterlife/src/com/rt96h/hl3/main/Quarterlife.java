package com.rt96h.hl3.main;

import com.rt96h.graphics.Renderer;
import com.rt96h.main.Engine;
import com.rt96h.main.Game;
import com.rt96h.main.GameConfig;
import com.rt96h.world.World;

public class Quarterlife extends Game{
	
	public static Quarterlife instance;
	
	public World world;
	
	public static void main(String[] args){
		GameConfig config = new GameConfig(1280,720);
		
		config.setTexturesFile("res/textures.xml");
		config.setModelsFile("res/models.xml");
		config.setWorldsFile("res/worlds.xml");
		config.setResizeable(false);
		config.setFramerate(120);
		config.setTitle("Quarterlife 3");
		
		instance = new Quarterlife();
		
		Engine.create(instance, config);
	}
	
	public void onInit(int width, int height){
		Renderer.alphaBlend(true, 0.5f);
		world = World.loadWorld("menu");
	}
	
	public void update(){
		world.update();
	}
	
	public void render(){
		Renderer.clearColor(0,0,0, 1);
		Renderer.clear();
		world.render();
	}
}
