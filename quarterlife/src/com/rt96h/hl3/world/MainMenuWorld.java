package com.rt96h.hl3.world;

import com.rt96h.hl3.main.Quarterlife;
import com.rt96h.world.GameObject;
import com.rt96h.world.GameObjectList;
import com.rt96h.world.World;
import com.rt96h.world.gui.GuiButton;
import com.rt96h.world.gui.GuiButtonListener;

public class MainMenuWorld extends World implements GuiButtonListener{
	
	public void onInit(){
		super.onInit();
		GameObjectList list = this.getGameObjectsByClass(GuiButton.class);
		for(GameObject b : list){
			GuiButton button = (GuiButton)b;
			button.addListener(this);
		}
	}

	@Override
	public void onClick(GuiButton b) {
		if(b.tag.equalsIgnoreCase("exit")){
			Quarterlife.instance.running = false;			
		}if(b.tag.equalsIgnoreCase("startGame")){
			Quarterlife.instance.world = World.loadWorld("ch1_world1");
		}if(b.tag.equalsIgnoreCase("options")){
			
		}
	}
	
}
