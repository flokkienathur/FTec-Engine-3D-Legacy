package com.rt96h.world.gui;

import org.w3c.dom.Element;

import com.rt96h.graphics.Color4;
import com.rt96h.graphics.DrawableFont;
import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.Texture;
import com.rt96h.graphics.model.Model;
import com.rt96h.math.Vector3;
import com.rt96h.utils.NodeHelper;
import com.rt96h.world.GameObject;
import com.rt96h.world.World;

public class GuiLabel extends GameObject{
	
	private String text;
	private DrawableFont font;
	private Color4 foreground;
	private float scale = 1;
	
	public GuiLabel(World world, Vector3 position, Model model,
			Texture texture, int layer, String tag) {
		super(world, position, model, texture, layer, tag);
	}
	
	public GameObject load(Element e){
		this.text = NodeHelper.getString(e, "text", "");
		String f = NodeHelper.getString(e, "font", "res/fonts/font.fnt");
		font = DrawableFont.loadBitmapFont(f);

		foreground = NodeHelper.getColor(e, "foreground", Color4.black);
		
		scale = NodeHelper.getFloat(e,"scale",1);
		
		return this;
	}
	
	public void render(SpriteBatch batch){
		batch.setColor(foreground);
		batch.matrix.translate(position.x, position.y, 0);
		batch.matrix.scale(scale, scale, 1);
		
		font.drawString(batch, text, 0,0);
		
		batch.matrix.scale(1f/scale, 1f/scale, 1);
		batch.matrix.translate(-position.x, -position.y, 0);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
