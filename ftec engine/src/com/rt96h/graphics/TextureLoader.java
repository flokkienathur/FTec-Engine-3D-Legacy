package com.rt96h.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rt96h.main.Engine;
import com.rt96h.utils.NodeHelper;

public final class TextureLoader {
	
	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	
	public static TextureRegion loadSprite(String name){
		if(name == null)
			return null;
		if(name.equalsIgnoreCase("null"))
			return null;
		
		try{
			File f = new File(Engine.PATH_TEXTURE);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			
			doc.getDocumentElement().normalize();
			
			NodeList textureList = doc.getElementsByTagName("texture");
			
			for(int i = 0; i < textureList.getLength(); i++){
				Node n = textureList.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE){
					Element textureElement = (Element)n;
					
					String fileName = textureElement.getAttribute("path");
					float w = NodeHelper.getFloat(textureElement, "width", 1);
					float h = NodeHelper.getFloat(textureElement, "height", 1);


					NodeList sprites = n.getChildNodes();
					for(int x = 0; x < sprites.getLength(); x++){
						Node sprite = sprites.item(x);
						if(sprite.getNodeType() == Node.ELEMENT_NODE){
							Element e = (Element)sprite;
							String s = e.getAttribute("name");
							if(s.equalsIgnoreCase(name)){
								float xx = NodeHelper.getFloat(e, "x", 0);
								float yy = NodeHelper.getFloat(e, "y", 0);
								float width = NodeHelper.getFloat(e, "width", 1);
								float height = NodeHelper.getFloat(e, "height", 1);
								
								Texture t = loadRaw(fileName);
								
								TextureRegion r = new TextureRegion(t,
										xx / w,yy / h,
										width / w, height / h
										);
								
								return r;
							}
						}
					}
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Texture loadRaw(String img){
		for(int i = 0; i < textures.size(); i++){
			if(textures.get(i).getFileName().equalsIgnoreCase(img)){
				return textures.get(i);
			}
		}
		try {
			BufferedImage raw = ImageIO.read(new File(img));
			
			BufferedImage buffer = new BufferedImage(raw.getWidth(),raw.getHeight(),BufferedImage.TYPE_INT_ARGB);
			
			buffer.getGraphics().drawImage(raw, 0, 0, null);
			
			int[] pixels = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
			
			ByteBuffer bb = ByteBuffer.allocateDirect(buffer.getWidth() * buffer.getHeight() * 4);
			
			for(int i = 0; i < pixels.length; i ++){
				int argb = pixels[i];
				
				byte r = (byte) ((argb >> 16) & 0xFF);
				byte g = (byte) ((argb >> 8) & 0xFF);
				byte b = (byte) ((argb) & 0xFF);
				byte a = (byte) ((argb >> 24) & 0xFF);
				
				bb.put(r).put(g).put(b).put(a);
			}
			
			bb.flip();
			Texture t = new Texture(buffer.getWidth(),buffer.getHeight(),bb,img);
			textures.add(t);
			return t;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}
