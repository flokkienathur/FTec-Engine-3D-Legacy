package com.rt96h.math;

import com.rt96h.graphics.model.Model;
import com.rt96h.world.GameObject;

public class RayHit {
	private Ray ray;
	private Vector3 hitPoint;
	private GameObject gameObject;
	private float distance = -1;
	private Model model;
	
	public RayHit(){
		
	}

	public Ray getRay() {
		return ray;
	}

	public RayHit setRay(Ray ray) {
		this.ray = ray;
		return this;
	}

	public Vector3 getHitPoint() {
		return hitPoint;
	}

	public RayHit setHitPoint(Vector3 hitPoint) {
		this.hitPoint = hitPoint;
		return this;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public RayHit setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
		return this;
	}

	public Model getModel() {
		return model;
	}

	public RayHit setModel(Model model) {
		this.model = model;
		return this;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = Mathf.abs(distance);
	}
}
