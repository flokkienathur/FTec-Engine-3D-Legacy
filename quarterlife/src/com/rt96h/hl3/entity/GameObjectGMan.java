package com.rt96h.hl3.entity;

import org.w3c.dom.Element;

import com.rt96h.graphics.Color4;
import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.Texture;
import com.rt96h.graphics.TextureLoader;
import com.rt96h.graphics.model.Model;
import com.rt96h.hl3.main.Quarterlife;
import com.rt96h.math.Mathf;
import com.rt96h.math.Vector3;
import com.rt96h.timer.Time;
import com.rt96h.utils.NodeHelper;
import com.rt96h.world.GameObject;
import com.rt96h.world.World;

public class GameObjectGMan extends GameObject{
	
	private Texture texture2;
	private String text = "";
	
	private float width = 0;
	private float height = 0;
	
	private float time = 0;
	private float maxTime = 0;
	
	private float fadeIn = 0;
	private float fadeOut = 0;
	
	private float alpha = 0;
	
	private Vector3 offset = Vector3.zero.clone();
	
	//private TextToSpeach speach;
	
	public String worldTo;

	public GameObjectGMan(World world, Vector3 position, Model model,
			Texture texture, int layer, String tag) {
		super(world, position, model, texture, layer, tag);
	}
	
	public void update(){
		if(alpha > 0.5)
			offset.y = Mathf.sin(time * 16) * 8 + 8;
		time += Time.deltaTime;
		if(time > maxTime){
			if(!worldTo.equalsIgnoreCase(""))
				Quarterlife.instance.world = World.loadWorld(worldTo);
		}
		
		if(time < fadeIn){
			alpha = Mathf.reverseLerp(0, fadeIn, time);
		}else if(time > fadeOut){
			alpha = Mathf.reverseLerp(maxTime, fadeOut, time);
		}else{
			alpha = 1;
		}
		
		world.getCamera().position.x = -(time / maxTime) * 128;
	}
	
	public void render(SpriteBatch batch){
		batch.setColor(new Color4(1,1,1,alpha));
		batch.drawTexture(position.x, position.y, width, height, texture);
		batch.drawTexture(position.x + offset.x, position.y+offset.y, width, height, texture2);
		batch.flush();
		batch.setColor(Color4.white);
	}
	
	@Override
	public GameObject load(Element e) {
		String s = NodeHelper.getString(e, "texture2", "null");
		if(s == null)
			texture2 = texture;
		else
			texture2 = TextureLoader.loadSprite(s);
		
		text = NodeHelper.getString(e, "text", "");
		
		width = NodeHelper.getFloat(e, "width", 32);
		height = NodeHelper.getFloat(e,"height", 32);

		maxTime = NodeHelper.getFloat(e,"time", 5);
		
		fadeIn = NodeHelper.getFloat(e,"fade-in", 0);
		fadeOut = NodeHelper.getFloat(e,"fade-out", 0);
		
		worldTo = NodeHelper.getString(e, "world", "");
		
		world.getCamera().position.x = -128;
		
		System.out.println("GMAN TEXT : "+ text);
		
		return this;
	}
}
