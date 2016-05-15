package com.rt96h.world;

import java.lang.reflect.Constructor;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.Texture;
import com.rt96h.graphics.TextureLoader;
import com.rt96h.graphics.model.Model;
import com.rt96h.math.Vector3;
import com.rt96h.utils.NodeHelper;

public class GameObject {

	public static final int LAYER_STATIC = 1;
	public static final int LAYER_PLAYER = 2;
	public static final int LAYER_DEFAULT = 4;
	public static final int LAYER_ALL = -1;
	public static final int LAYER_NONE = 0;

	public Vector3 position;
	public Model model;
	public Texture texture;
	public int layer;
	
	public String tag;

	public World world;

	public GameObject(World world, Vector3 position, Model model,
			Texture texture, int layer, String tag) {
		this.position = position;
		this.model = model;
		this.texture = texture;
		this.layer = layer;
		this.world = world;
		this.tag = tag;
	}

	public void update() {

	}

	public void render(SpriteBatch batch) {
		GL11.glColor4f(1,1,1,1);
		batch.matrix.translate(position.x, position.y, position.z);
		Model m = getRenderModel();
		if (m != null)
			m.draw(batch, texture);
		batch.matrix.translate(-position.x, -position.y, -position.z);
	}

	public Model getRenderModel() {
		return model;
	}

	public Model getCollisionModel() {
		return model;
	}
	
	public GameObject load(Element e){
		return this;
	}

	public static final GameObject loadObject(World w, Element e) {
		try {
			String cls = NodeHelper.getString(e, "class",
					"com.rt96h.world.GameObject");
			Class<?> cl = Class.forName(cls);
			Constructor<?> cons = cl.getConstructor(World.class, Vector3.class,Model.class,Texture.class,Integer.TYPE, String.class);
		
			Vector3 pos = new Vector3(
					NodeHelper.getFloat(e, "x", 0),
					NodeHelper.getFloat(e, "y", 0),
					NodeHelper.getFloat(e, "z", 0)
					);
			int layer = NodeHelper.getInt(e, "layer", GameObject.LAYER_STATIC);
			Model m = Model.loadModel(NodeHelper.getString(e, "model", "null"));
			Texture t = TextureLoader.loadSprite(NodeHelper.getString(e,"texture","null"));
			String tag = NodeHelper.getString(e, "tag", "");
			
			return ((GameObject) cons.newInstance(
					w,
					pos,
					m,
					t,
					layer,
					tag
					)).load(e);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
