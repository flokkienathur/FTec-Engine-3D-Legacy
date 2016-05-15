package com.rt96h.hl3.item.weapon;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.Texture;
import com.rt96h.graphics.TextureLoader;
import com.rt96h.graphics.model.Model;
import com.rt96h.math.Vector3;
import com.rt96h.utils.NodeHelper;
import com.rt96h.world.World;

public class Weapon {
	
	private static ArrayList<Weapon> weapons = new ArrayList<Weapon>();

	private float range = -1;
	
	private float damage = 0;
	
	private float rateOfFire = 1;
	
	private String name;
	
	private Model model;
	private Texture texture;
	
	public Weapon(Model m, Texture t){
		model = m;
		texture = t;
	}
	
	public boolean fire(World w, Vector3 pos, Vector3 rotation, float secondsSinceLastFire){
		return false;
	}
	
	public void render(SpriteBatch batch, Vector3 position, Vector3 rotation, float secondsSinceLastFire){
		batch.matrix.translate(position.x,position.y,position.z);
		batch.matrix.rotateY(-rotation.y);
		batch.matrix.rotateX(rotation.x);
		batch.matrix.translate(-0.2f, -0.2f, 0);

		if(model != null)
			model.draw(batch, texture);

		batch.matrix.translate(0.2f, 0.4f, 0);
		batch.matrix.rotateX(-rotation.x);
		batch.matrix.rotateY(rotation.y);
		batch.matrix.translate(-position.x,-position.y,-position.z);
	}
	
	public static ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public static void setWeapons(ArrayList<Weapon> weapons) {
		Weapon.weapons = weapons;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getRateOfFire() {
		return rateOfFire;
	}

	public void setRateOfFire(float rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	private void load(Element e){
		setDamage(NodeHelper.getFloat(e, "damage", 0));
		setRange(NodeHelper.getFloat(e, "range", -1));
		setRateOfFire(NodeHelper.getFloat(e, "rateoffire", 0));
	}

	public static Weapon getWeaponByName(String n){
		for(Weapon w : weapons){
			if(w.getName().equalsIgnoreCase(n)){
				return w;
			}
		}
		
		return loadWeapon(n);
	}
	
	private static Weapon loadWeapon(String name){
		try {
			File f = new File("res/weapons.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);

			NodeList objectList = doc.getElementsByTagName("weapon");

			for (int i = 0; i < objectList.getLength(); i++) {
				Node n = objectList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					if(e.getAttribute("name").equalsIgnoreCase(name)){
						Model m = Model.loadModel(NodeHelper.getString(e, "model", "null"));
						Texture s = TextureLoader.loadSprite(NodeHelper.getString(e, "texture", "null"));
						Weapon w = new Weapon(m,s);
						w.setName(name);
						w.load(e);
						weapons.add(w);
						return w;
					}
				}
			}

			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
