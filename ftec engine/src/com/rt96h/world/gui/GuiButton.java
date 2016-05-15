package com.rt96h.world.gui;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
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

public class GuiButton extends GameObject{
	
	private String text;
	private DrawableFont font;
	private float width, height;
	private float xoffset, yoffset;
	private Color4 outline;
	private Color4 background;
	private Color4 foreground;
	
	private ArrayList<GuiButtonListener> listeners;
	
	private boolean hover = false;
	private boolean pressed = false;
	
	public GuiButton(World world, Vector3 position, Model model,
			Texture texture, int layer, String tag) {
		super(world, position, model, texture, layer, tag);
		listeners = new ArrayList<GuiButtonListener>();
	}
	
	public GameObject load(Element e){
		this.text = NodeHelper.getString(e, "text", "");
		String f = NodeHelper.getString(e, "font", "res/fonts/font.fnt");
		font = DrawableFont.loadBitmapFont(f);
		width = NodeHelper.getFloat(e, "width", 32);
		height = NodeHelper.getFloat(e, "height", 32);

		foreground = NodeHelper.getColor(e, "foreground", Color4.white);
		background = NodeHelper.getColor(e, "background", Color4.gray);
		outline = NodeHelper.getColor(e, "outline", Color4.darkGray);
		
		recalculate();
		return this;
	}
	
	public void update(){
		float mx = world.getCamera().mouseX;
		float my = world.getCamera().mouseY;
		if(mx >= position.x && mx < position.x + width && my >= position.y && my <= position.y + height){
			hover = true;
		}else{
			hover = false;
		}
		
		if(hover && Mouse.isButtonDown(0)){
			pressed = true;
		}else{
			if(pressed){
				int l = listeners.size();
				for(int i = 0; i < l; i ++){
					listeners.get(i).onClick(this);
				}
			}
			pressed = false;
		}
	}
	
	private void recalculate(){
		xoffset = width / 2 - (font.stringWidth(text) / 2);
		yoffset = height / 2 - (font.stringHeight(text) / 2);
	}
	
	public void render(SpriteBatch batch){
		batch.setColor(outline);
		batch.drawTexture(position.x, position.y, width, height, null);
		if(!hover){
			batch.setColor(background);
			batch.drawTexture(position.x+1, position.y+1, width-2, height-2, null);
		}
		batch.setColor(foreground);
		font.drawString(batch, text, position.x + xoffset, position.y + yoffset);
	}
	
	public void addListener(GuiButtonListener l){
		listeners.add(l);
	}
	
	public void removeListeners(GuiButtonListener l) {
		listeners.remove(l);
	}

	public String getText() {
		return text;
	}
	
	public Color4 getOutline() {
		return outline;
	}
	
	public Color4 getForeground() {
		return foreground;
	}
	
	public DrawableFont getFont() {
		return font;
	}
	
	public Color4 getBackground() {
		return background;
	}
	
	public void setFont(DrawableFont font) {
		this.font = font;
		recalculate();
	}
	
	public void setBackground(Color4 background) {
		this.background = background;
	}
	
	public void setForeground(Color4 foreground) {
		this.foreground = foreground;
	}
	
	public void setOutline(Color4 outline) {
		this.outline = outline;
	}

	public void setText(String text) {
		this.text = text;
		recalculate();
	}
	
}
