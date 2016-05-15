package com.rt96h.world;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rt96h.graphics.Buffer;
import com.rt96h.graphics.Renderer;
import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.camera.Camera;
import com.rt96h.graphics.camera.OrthagonalCamera;
import com.rt96h.graphics.model.Model;
import com.rt96h.main.Engine;
import com.rt96h.math.Ray;
import com.rt96h.math.RayHit;
import com.rt96h.math.Triangle;
import com.rt96h.math.Vector3;
import com.rt96h.utils.NodeHelper;

public class World {

	protected SpriteBatch batch;
	protected GameObjectList objects;
	protected SpriteBatch postBatch;
	protected Buffer buffer;

	public World() {
		batch = Renderer.createBatch(1024*32);
		objects = new GameObjectList();
	}
	
	public void onInit(){
		buffer = new Buffer((int)Renderer.getWidth(),(int)Renderer.getHeight());
		postBatch = new SpriteBatch(8);
		postBatch.setCamera(new OrthagonalCamera(1,1));
	}

	public void update() {
		int l = objects.size();
		batch.getCamera().update();
		for (int i = 0; i < l; i++) {
			objects.get(i).update();
		}
	}

	public void render() {
		//buffer.bind();
		Renderer.clear();
		batch.begin();
		int l = objects.size();
		for (int i = 0; i < l; i++) {
			objects.get(i).render(batch);
		}
		batch.end();
		/*buffer.unbind();
		postBatch.begin();
		postBatch.drawTexture(0, 0, 1,1, buffer.getTexture());
		postBatch.end();*/
	}
	
	public void rawRender(SpriteBatch b){
		int l = objects.size();
		for (int i = 0; i < l; i++) {
			objects.get(i).render(b);
		}
	}

	public void addGameObject(GameObject object) {
		objects.add(object);
	}

	public void removeGameObject(GameObject object) {
		objects.remove(object);
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public Camera getCamera() {
		return batch.getCamera();
	}

	public void setCamera(Camera c) {
		batch.setCamera(c);
	}

	public boolean rayCast(Ray ray, RayHit hit, int layer) {
		if (ray == null)
			return false;
		if (hit == null)
			return false;

		int l = objects.size();
		for (int i = 0; i < l; i++) {
			GameObject gameObject = objects.get(i);
			if ((gameObject.layer & layer) > 0 && gameObject.getCollisionModel() != null) {
				// Found gameobject to have a collision with.

				Model model = gameObject.getCollisionModel();

				int triangles = model.triangleCount();

				for (int tr = 0; tr < triangles; tr++) {
					Triangle t = new Triangle(
							model.vertex[model.triangles[tr * 3]].clone().add(
									gameObject.position),
							model.vertex[model.triangles[tr * 3 + 1]].clone()
									.add(gameObject.position),
							model.vertex[model.triangles[tr * 3 + 2]].clone()
									.add(gameObject.position));

					Vector3 intersection = t.intersectsRay(ray);
					if (intersection != null) {
						float length = ray.getStart().clone().sub(intersection)
								.length();
						if ((hit.getDistance() == -1
								|| (length < hit.getDistance())) && hit.getDistance() > 0.0001f) {
							hit.setDistance(length);
							hit.setGameObject(gameObject);
							hit.setHitPoint(intersection);
							hit.setModel(gameObject.getCollisionModel());
							hit.setRay(ray);
						}
					}
				}

			}
		}

		return hit.getGameObject() != null;
	}

	public GameObject getGameObjectByClass(Class<?> c){
		int l = objects.size();
		
		for(int i = 0; i < l; i++){
			if(objects.get(i).getClass() == c){
				return objects.get(i);
			}
		}
		return null;
	}
	
	public GameObjectList getGameObjectsByClass(Class<?> c){
		int l = objects.size();
		
		GameObjectList list = new GameObjectList();
		
		for(int i = 0; i < l; i++){
			if(objects.get(i).getClass() == c){
				list.add(objects.get(i));
			}
		}
		return list;
	}

	public GameObject getGameObjectByTag(String tag){
		int l = objects.size();
		
		for(int i = 0; i < l; i++){
			if(objects.get(i).tag.equalsIgnoreCase(tag)){
				return objects.get(i);
			}
		}
		return null;
	}

	public GameObjectList getGameObjectsByTag(String tag){
		int l = objects.size();
		
		GameObjectList list = new GameObjectList();
		
		for(int i = 0; i < l; i++){
			if(objects.get(i).tag.equalsIgnoreCase(tag)){
				list.add(objects.get(i));
			}
		}
		return list;
	}

	// World world, Vector3 position, Model model,
	// Texture texture, int layer

	public static World loadWorld(String name) {
		try {
			File f = new File(Engine.PATH_WORLD);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);

			doc.getDocumentElement().normalize();

			NodeList worldList = doc.getElementsByTagName("world");

			for (int i = 0; i < worldList.getLength(); i++) {
				Node n = worldList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element world = (Element) n;
					if (world.getAttribute("name").equalsIgnoreCase(name)) {
						return loadWorldFile(world.getAttribute("file"));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static World loadWorldFile(String file) {
		try {
			File f = new File(file);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			String cls = NodeHelper.getString(doc.getDocumentElement(),
					"class", "com.rt96h.world.World");

			Class<?> cl = Class.forName(cls);
			World world = (World) cl.newInstance();

			NodeList objectList = doc.getElementsByTagName("object");

			for (int i = 0; i < objectList.getLength(); i++) {
				Node n = objectList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					GameObject object = GameObject.loadObject(world, e);
					if (object != null)
						world.addGameObject(object);
				}
			}

			NodeList cameraList = doc.getElementsByTagName("camera");

			for (int i = 0; i < cameraList.getLength(); i++) {
				Node n = cameraList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					Camera object = Camera.loadCamera(e);
					if (object != null)
						world.setCamera(object);
				}
			}
			
			world.onInit();

			return world;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
