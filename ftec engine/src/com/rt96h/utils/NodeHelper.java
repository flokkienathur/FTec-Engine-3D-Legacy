package com.rt96h.utils;

import org.w3c.dom.Element;

import com.rt96h.graphics.Color4;

public class NodeHelper {
	
	public static float getFloat(Element e, String attribute, float def){
		String val = getString(e,attribute,""+def);
		try{
			float f = Float.parseFloat(val);
			return f;
		}catch(Exception ex){
			return def;
		}
	}
	
	public static int getInt(Element e, String attribute, int def){
		String val = getString(e,attribute,""+def);
		try{
			int f = Integer.parseInt(val);
			return f;
		}catch(Exception ex){
			return def;
		}
	}

	public static String getString(Element e, String attribute, String def){
		if(e.hasAttribute(attribute)){
			String a = e.getAttribute(attribute);
			try{
				if(a.startsWith("$resource:")){
					a = StringHelper.fileToString(a.replace("$resource:", ""));
				}
				if(a.startsWith("$var-")){
					String param = a.replace("$var-", "").split(":")[0].replace(":","").trim();
					
					System.out.println("param = "+ param);
					
					a = StringHelper.getArgumentLine(StringHelper.fileToString(a.replace("$var-", "").replace(param,"").replaceFirst(":", "")),param,a);
				}
			}catch(Exception exception){
				exception.printStackTrace();
			}
			return a;
		}else{
			return def;
		}
	}
	
	public static Color4 getColor(Element e, String attribute, Color4 def){
		if(e.hasAttribute(attribute)){
			return Converter.toColor(e.getAttribute(attribute),def);
		}else{
			return def;
		}
	}
}
