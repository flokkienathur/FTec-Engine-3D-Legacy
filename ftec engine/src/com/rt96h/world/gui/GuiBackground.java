package com.rt96h.world.gui;

import org.w3c.dom.Element;

import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.Texture;
import com.rt96h.graphics.model.Model;
import com.rt96h.math.Vector3;
import com.rt96h.utils.NodeHelper;
import com.rt96h.world.GameObject;
import com.rt96h.world.World;

public class GuiBackground extends GameObject{
	
	private float width, height;

	public GuiBackground(World world, Vector3 position, Model model,
			Texture texture, int layer, String tag) {
		super(world, position, model, texture, layer, tag);
	}
	
	public GameObject load(Element e){
		width = NodeHelper.getFloat(e, "width", 32);
		height = NodeHelper.getFloat(e, "height", 32);
		
		return this;
	}
	
	public void render(SpriteBatch batch){
		batch.drawTexture(position.x, position.y,width,height,texture);
	}
	
}
