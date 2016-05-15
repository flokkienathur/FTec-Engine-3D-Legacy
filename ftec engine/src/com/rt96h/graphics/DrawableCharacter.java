package com.rt96h.graphics;

import com.rt96h.math.Mathf;

public class DrawableCharacter {
	
	protected final Texture t;
	protected final float x,y,width,height,xoffset,yoffset,startFracX,startFracY,endFracX,endFracY,xadvance;
	
	protected DrawableCharacter(float x, float y, float w, float h, float xo, float yo, float xadvance, Texture base){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.t = base;
		this.xoffset = xo;
		this.yoffset = yo;
		this.startFracX = Mathf.reverseLerp(0,base.getWidth(),x);
		this.endFracX = Mathf.reverseLerp(0,base.getWidth(),x+w);
		this.startFracY = Mathf.reverseLerp(0,base.getHeight(),y);
		this.endFracY = Mathf.reverseLerp(0,base.getHeight(),y+h);
		this.xadvance = xadvance;
	}
	
}
