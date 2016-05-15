package com.rt96h.graphics;

public class TextureRegion extends Texture{
	
	private float x, y, w, h;
	
	public TextureRegion(Texture t, float x, float y, float w, float h){
		super(t.getWidth(),t.getHeight(),t.getId(),t.getFileName());
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public float getFracX(){
		return x;
	}
	public float getFracY(){
		return y;
	}
	public float getFracWidth(){
		return w;
	}
	public float getFracHeight(){
		return h;
	}

}
