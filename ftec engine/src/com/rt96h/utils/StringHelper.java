package com.rt96h.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StringHelper {
	public static String getArgument(String string, String argument, String defaultValue){
		String[] s = string.split(" ");
		for(String ss : s){
			if(ss.trim().startsWith(argument + "=")){
				return ss.replaceFirst(argument + "=","").replaceAll("\"", "");
			}
		}
		return defaultValue;
	}
	
	public static String getArgumentRaw(String string, String argument, String defaultValue){
		String[] s = string.split(" ");
		for(String ss : s){
			if(ss.trim().startsWith(argument + "=")){
				return ss.replaceFirst(argument + "=","");
			}
		}
		return defaultValue;
	}
	
	public static String getArgumentLine(String string, String arg, String def){
		String s[] = string.split("\n");
		
		for(String ss : s){
			if(ss.startsWith(arg+"=")){
				return ss.replace(arg+"=", "");
			}
		}
		
		return def;
	}
	
	public static String fileToString(String filename){
		Scanner s;
		try {
			s = new Scanner(new File(filename));
			String content = s.useDelimiter("\\Z").next();
			s.close();
			return content;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
