package com.rt96h.graphics;

import java.io.File;
import java.util.Scanner;

import com.rt96h.math.Mathf;
import com.rt96h.utils.Converter;
import com.rt96h.utils.StringHelper;

public class DrawableFont {
	
	protected Texture pages[];
	protected DrawableCharacter[] chars;
	protected int charFirst;
	protected int charLast;
	protected int charCount;
	
	protected String face;
	protected int size;
	protected int fontHeight;
	
	private DrawableFont(){
		
	}
	
	public static DrawableFont loadBitmapFont(String fileName){
		try{
			File f = new File(fileName);
			Scanner s = new Scanner(f);
			String line;
			
			DrawableFont font = new DrawableFont();
			
			font.chars = new DrawableCharacter[256];
			
			while(s.hasNextLine()){
				line = s.nextLine().trim().replaceAll(" +", " ").replace("\t", "");
				
				if(line.startsWith("info")){
					font.face = StringHelper.getArgument(line,"face","Unknown");
					font.size = Converter.toInt(StringHelper.getArgument(line, "size", "16"),16);
				}
				if(line.startsWith("common")){
					font.fontHeight = Converter.toInt(StringHelper.getArgument(line, "lineHeight", "no"), font.size);
					font.pages = new Texture[
					                           Converter.toInt(StringHelper.getArgument(line, "pages", "1"),1)
					                           ];
				}
				if(line.startsWith("page")){
					int id = Converter.toInt(StringHelper.getArgument(line,"id", "0"),0);
					String fontFilePath = StringHelper.getArgument(line, "file", "null");
					//relative path
					font.pages[id] = TextureLoader.loadRaw(f.getPath().replace(f.getName(), "") + fontFilePath);
				}
				
				if(line.startsWith("chars")){
					font.charCount = Converter.toInt(StringHelper.getArgument(line, "count", "0"), 0);
				}
				
				if(line.startsWith("char ")){
					int id = Converter.toInt(StringHelper.getArgument(line,"id",""), 0);
					int page = Converter.toInt(StringHelper.getArgument(line,"page",""),0);
					float x = Converter.toFloat(StringHelper.getArgument(line,"x",""), 0);
					float y = Converter.toFloat(StringHelper.getArgument(line,"y",""), 0);
					float w = Converter.toFloat(StringHelper.getArgument(line,"width",""), 0);
					float h = Converter.toFloat(StringHelper.getArgument(line,"height",""), 0);
					
					float xo = Converter.toFloat(StringHelper.getArgument(line,"xoffset",""), 0);
					float yo = Converter.toFloat(StringHelper.getArgument(line,"yoffset",""), 0);
					float xadvance = Converter.toFloat(StringHelper.getArgument(line,"xadvance",""), 0);
					
					DrawableCharacter d = new DrawableCharacter(
								x,y,w,h,xo,yo,xadvance,font.pages[page]
							);
					if(id > 0 && id < font.chars.length)
						font.chars[id] = d;
					
					
				}
				
			}
			
			
			s.close();
			return font;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public float stringWidth(String s){
		char[] c = s.toCharArray();
		float sum = 0;
		for(char ch : c){
			sum += chars[ch].xadvance;
		}
		return sum;
	}
	
	public float stringHeight(String s){
		return this.fontHeight;
	}
	
	public void drawString(SpriteBatch batch, String s, float x, float y){
		char[] c = s.toCharArray();
		float xoffset = 0;
		float yoffset = 0;
		for(char ch : c){
			if(ch == '\n'){
				xoffset = 0;
				yoffset += this.fontHeight;
			}
			if(ch == '\t'){
				xoffset = Mathf.ceil((xoffset + 1) / 32f ) * 32;
			}
			DrawableCharacter drawable = chars[ch];
			if(drawable != null){
				batch.setTexture(drawable.t);
				drawCharacter(batch,x + xoffset,y + yoffset,drawable);
				xoffset += drawable.xadvance;
			}
		}
	}
	
	public void drawCharacter(SpriteBatch batch, float x, float y, DrawableCharacter character){
		if(batch.getLeft() < 6){
			batch.flush();
		}
		
		batch.setTexture(character.t);
		
		//0 2 1
		batch.vertex(x+character.xoffset, y + character.yoffset, 0, 1,1,1,1, character.startFracX,character.startFracY, 0, 0, -1);
		batch.vertex(x+character.xoffset + character.width, y + character.yoffset + character.height, 0, 1,1,1,1, character.endFracX,character.endFracY, 0, 0, -1);
		batch.vertex(x+character.xoffset + character.width, y + character.yoffset, 0, 1,1,1,1, character.endFracX,character.startFracY, 0, 0, -1);
		
		//0 3 2
		batch.vertex(x+character.xoffset, y + character.yoffset, 0, 1,1,1,1, character.startFracX,character.startFracY, 0, 0, -1);
		batch.vertex(x+character.xoffset, y + character.yoffset + character.height, 0, 1,1,1,1, character.startFracX,character.endFracY, 0, 0, -1);
		batch.vertex(x+character.xoffset + character.width, y + character.yoffset + character.height, 0, 1,1,1,1, character.endFracX,character.endFracY, 0, 0, -1);
		
	}
}
