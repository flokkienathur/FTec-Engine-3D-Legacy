package com.rt96h.utils;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.rt96h.graphics.Color4;
import com.rt96h.math.Matrix;
import com.rt96h.math.Vector3;

public class Converter {
	
	public static int toInt(String s, int def){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){
			return def;
		}
	}
	
	public static float toFloat(String s, float def){
		try{
			return Float.parseFloat(s);
		}catch(Exception e){
			return def;
		}
	}
	
	public static Color4 toColor(String s, Color4 def){
		if(s.equalsIgnoreCase("red"))
			return Color4.red;
		else if(s.equalsIgnoreCase("blue"))
			return Color4.blue;
		else if(s.equalsIgnoreCase("green"))
			return Color4.green;
		else if(s.equalsIgnoreCase("yellow"))
			return Color4.yellow;
		else if(s.equalsIgnoreCase("pink"))
			return Color4.pink;
		else if(s.equalsIgnoreCase("black"))
			return Color4.black;
		else if(s.equalsIgnoreCase("white"))
			return Color4.white;
		else if(s.equalsIgnoreCase("gray"))
			return Color4.gray;
		else if(s.startsWith("#") || s.startsWith("0x")){
			String hex = s.replaceFirst("0x", "").replaceFirst("#","");
			int color = Integer.parseInt(hex,16);
			return toColor(color, def);
		}
		return def;
		
	}
	
	public static Color4 toColor(int argb, Color4 def){
		float r = ((argb >> 16) & 0xFF);
		float g = ((argb >> 8) & 0xFF);
		float b = ((argb) & 0xFF);
		float a = ((argb >> 24) & 0xFF);
		
		return new Color4(r/255f, g/255f, b/255f, a/255f);
	}
	
	public static FloatBuffer toFloatBuffer(Matrix mat){
		return (FloatBuffer)BufferUtils.createFloatBuffer(mat.getData().length).put(mat.getData()).flip();
	}
	
	public static FloatBuffer toFloatBuffer(Vector3 vec){
		return (FloatBuffer)BufferUtils.createFloatBuffer(3).put(vec.x).put(vec.y).put(vec.z).flip();
	}
	
	public static void printFloatBuffer(FloatBuffer b, int length){
		for(int i = 0; i < length; i ++){
			System.out.print(b.get() + " ");
		}
		System.out.println(" |");
	}
}
