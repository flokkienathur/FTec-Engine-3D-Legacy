package com.rt96h.graphics.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rt96h.graphics.Color4;
import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.Texture;
import com.rt96h.main.Engine;
import com.rt96h.math.Vector2;
import com.rt96h.math.Vector3;
import com.rt96h.utils.Converter;
import com.rt96h.utils.NodeHelper;

public class Model {
	
	public Vector3[] vertex;

	public Vector3[] normal;
	public Color4[] color;
	public Vector2[] uv;
	public int[] triangles;
	public int[] uvs;
	public int[] normals;
	
	public Model(){
		
	}
	
	public void draw(SpriteBatch batch, Texture t){
		if(batch.getLeft() < triangles.length)
			batch.flush();
		
		batch.setTexture(t);
		for(int i = 0; i < triangles.length; i ++){
			int v = triangles[i];
			int u = uvs[i];
			int w = normals[i];
			batch.vertex(
					vertex[v],
					color[v],
					uv[u],
					normal[w]
					);
		}
	}
	
	public void recalculateNormals(){
		int length = triangles.length / 3;
		
		normal = new Vector3[length];
		normals = new int[length*3];
		
		for(int i = 0; i < length; i++){
			Vector3 p1 = vertex[triangles[i*3]];
			Vector3 p2 = vertex[triangles[i*3+1]];
			Vector3 p3 = vertex[triangles[i*3+2]];
			
			Vector3 u = p2.clone().sub(p1);
			Vector3 v = p3.clone().sub(p1);
			
			Vector3 n = new Vector3();
			n.x = (u.y * v.z) - (u.z * v.y);
			n.y = (u.z * v.x) - (u.x * v.z);
			n.z = (u.x * v.y) - (u.y * v.x);
			
			normal[i] = n.normalize();
			normals[i*3] = i;
			normals[i*3+1] = i;
			normals[i*3+2] = i;
		}
	}
	
	public void recalculateColors(){
		color = new Color4[vertex.length];
		for(int i = 0; i < color.length; i++){
			color[i] = Color4.white;
		}
	}
	
	public int triangleCount(){
		return triangles.length / 3;
	}
	
	public static Model loadObjectFile(String name){
		try{
			Scanner s = new Scanner(new File(name));
			String[] lines = s.useDelimiter("\\Z").next().split("\n");
			s.close();
			
			ArrayList<Vector3> vertices = new ArrayList<Vector3>();
			ArrayList<Vector2> uvs = new ArrayList<Vector2>();
			ArrayList<Vector3> normals = new ArrayList<Vector3>();
			
			ArrayList<Integer> triangleList = new ArrayList<Integer>();
			ArrayList<Integer> uvList = new ArrayList<Integer>();
			ArrayList<Integer> normalList = new ArrayList<Integer>();
			
			for(String line : lines){
				try{
					line = line.trim().replaceAll(" +", " ");
					//all nice shit and such
					if(line.startsWith("v ")){
						String[] vertex = line.replaceFirst("v ", "").split(" ");
						float x = Float.parseFloat(vertex[0]);
						float y = Float.parseFloat(vertex[1]);
						float z = Float.parseFloat(vertex[2]);
						vertices.add(new Vector3(x,y,z));
					}
					if(line.startsWith("vt ")){
						String[] vertex = line.replaceFirst("vt ", "").split(" ");
						float x = Float.parseFloat(vertex[0]);
						float y = Float.parseFloat(vertex[1]);
						uvs.add(new Vector2(x,y));
					}
					if(line.startsWith("vn ")){
						String[] vertex = line.replaceFirst("vn ", "").split(" ");
						float x = Float.parseFloat(vertex[0]);
						float y = Float.parseFloat(vertex[1]);
						float z = Float.parseFloat(vertex[2]);
						normals.add(new Vector3(x,y,z).normalize());
					}
					
					//faces
					if(line.startsWith("f ")){
						String[] vertex = line.replaceFirst("f ", "").trim().split(" ");
						if(vertex.length >= 3){
							String[] split = vertex[0].split("/");
							int v0 = 0;
							int u0 = 0;
							int w0 = 0;
							if(split.length > 0)
								v0 = Converter.toInt(split[0],1) - 1;
							if(split.length > 1)
								u0 = Converter.toInt(split[1],1) - 1;
							if(split.length > 2)
								w0 = Converter.toInt(split[2],1) - 1;
							
							for(int i = 0; i < vertex.length - 2; i++){
								String[] split2 = vertex[i+1].split("/");
								int v1 = 0;
								int u1 = 0;
								int w1 = 0;
								if(split2.length > 0)
									v1 = Converter.toInt(split2[0],1) - 1;
								if(split2.length > 1)
									u1 = Converter.toInt(split2[1],1) - 1;
								if(split2.length > 2)
									w1 = Converter.toInt(split2[2],1) - 1;

								String[] split3 = vertex[i+2].split("/");
								int v2 = 0;
								int u2 = 0;
								int w2 = 0;
								if(split3.length > 0)
									v2 = Converter.toInt(split3[0],1) - 1;
								if(split3.length > 1)
									u2 = Converter.toInt(split3[1],1) - 1;
								if(split3.length > 2)
									w2 = Converter.toInt(split3[2],1) - 1;

								triangleList.add(v0);
								triangleList.add(v1);
								triangleList.add(v2);

								normalList.add(w0);
								normalList.add(w1);
								normalList.add(w2);

								uvList.add(u0);
								uvList.add(u1);
								uvList.add(u2);
							}
							
						}
					}
					
					
					
				}catch(Exception e){
					e.printStackTrace();
					System.err.println("Malformed OBJ File");
				}
			}
			
			if(uvs.size() < 1){
				uvs.add(new Vector2(0,0));
			}
			if(normals.size() < 1){
				normals.add(new Vector3(0,1,0));
			}
			
			
			
			Model model = new Model();
			model.vertex = new Vector3[vertices.size()];
			model.triangles = new int[triangleList.size()];
			model.uvs = new int[uvList.size()];
			model.normals = new int[normalList.size()];
			model.uv = new Vector2[uvs.size()];
			model.normal = new Vector3[normals.size()];
			
			vertices.toArray(model.vertex);
			uvs.toArray(model.uv);
			normals.toArray(model.normal);
			
			for(int i = 0; i < triangleList.size(); i++){
				model.triangles[i] = triangleList.get(i);
				model.uvs[i] = uvList.get(i);
				model.normals[i] = normalList.get(i);
			}
			
			model.recalculateColors();
			
			//remove this
			model.recalculateNormals();
			
			return model;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Model loadModel(String name){
		if(name == null)
			return null;
		if(name.equalsIgnoreCase("null"))
			return null;
		try{
			File f = new File(Engine.PATH_MODEL);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			
			doc.getDocumentElement().normalize();
			
			NodeList modelList = doc.getElementsByTagName("model");

			ArrayList<Vector3> vertexes = new ArrayList<Vector3>();
			ArrayList<Integer> triangles = new ArrayList<Integer>();
			ArrayList<Vector2> uvs = new ArrayList<Vector2>();
			ArrayList<Color4> colors = new ArrayList<Color4>();
			
			boolean found = false;
			
			//parasing all nodes :)
			for(int i = 0; i < modelList.getLength(); i ++){
				Node node = modelList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element e = (Element) node;
					if(e.getAttribute("name").equalsIgnoreCase(name)){
						Element visual = (Element)e.getElementsByTagName("visual").item(0);
						
						found = true;

						//if it is an extern obj file (in most cases hopefully)
						if(!visual.getAttribute("extern").trim().equalsIgnoreCase("")){
							return Model.loadObjectFile(visual.getAttribute("extern"));
						}
						
						NodeList list = visual.getChildNodes();
						
						for(int ii = 0; ii < list.getLength(); ii ++){
							Node vertex = list.item(ii);
							if(vertex.getNodeType() == Node.ELEMENT_NODE){
								Element v = (Element) vertex;
								if(v.getNodeName().equalsIgnoreCase("vertex")){
									Vector3 pos = new Vector3(
											NodeHelper.getFloat(v, "x", 0),
											NodeHelper.getFloat(v, "y", 0),
											NodeHelper.getFloat(v, "z", 0)
											);
									Vector2 uv = new Vector2(
											NodeHelper.getFloat(v, "u", 0),
											NodeHelper.getFloat(v, "v", 0)
											);
									Color4 col = new Color4(
											NodeHelper.getFloat(v, "r", 1),
											NodeHelper.getFloat(v, "g", 1),
											NodeHelper.getFloat(v, "b", 1),
											NodeHelper.getFloat(v, "a", 1)
											);
									vertexes.add(pos);
									uvs.add(uv);
									colors.add(col);
								}
								if(v.getNodeName().equalsIgnoreCase("triangles")){
									String[] trList = v.getTextContent().replace(" ", "").replace("\t", "").replace("\n","").split(",");
									for(int tr = 0; tr < trList.length; tr++){
										try{
											int triangle = Integer.parseInt(trList[tr]);
											triangles.add(triangle);
										}catch(Exception exception){
											exception.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
			
			Model model = new Model();
			model.vertex = new Vector3[vertexes.size()];
			model.triangles = new int[triangles.size()];
			model.uvs = new int[triangles.size()];
			model.normals = new int[triangles.size()];
			model.color = new Color4[colors.size()];
			model.uv = new Vector2[uvs.size()];

			vertexes.toArray(model.vertex);
			colors.toArray(model.color);
			uvs.toArray(model.uv);
			
			for(int i = 0; i < triangles.size(); i++){
				model.triangles[i] = triangles.get(i);
				model.normals[i] = triangles.get(i) / 3;
				model.uvs[i] = triangles.get(i);
			}
			
			model.recalculateNormals();
			
			if(found)
				return model;
			else
				return null;
		
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Failed to load model.");
			return null;
		}
		
	}
}
