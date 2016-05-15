package com.rt96h.hl3.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.rt96h.audio.AudioFX;
import com.rt96h.audio.AudioSource;
import com.rt96h.audio.AudioSystem;
import com.rt96h.graphics.Renderer;
import com.rt96h.graphics.SpriteBatch;
import com.rt96h.graphics.Texture;
import com.rt96h.graphics.camera.Camera;
import com.rt96h.graphics.model.Model;
import com.rt96h.hl3.item.weapon.Weapon;
import com.rt96h.math.Mathf;
import com.rt96h.math.Quaternion;
import com.rt96h.math.Ray;
import com.rt96h.math.RayHit;
import com.rt96h.math.Vector3;
import com.rt96h.timer.Time;
import com.rt96h.world.GameObject;
import com.rt96h.world.World;

public class GameObjectPlayer extends GameObject {

	boolean grounded = true;

	public float gravity = 20f;

	public float gAcceleration = 48f;
	public float gSpeed = 12f;

	public float aAcceleration = 8f;
	public float aSpeed = 16f;

	public float jump = 7f;

	public Weapon weapon;

	public Vector3 motion = Vector3.zero.clone();
	public Vector3 flatMotion = Vector3.zero.clone();

	public AudioSource source;
	public AudioFX jumpSound;

	public boolean keyJump = false;
	public boolean keyJumpDown = false;
	public boolean keyHop = false;

	public GameObjectPlayer(World world, Vector3 position, Model model,
			Texture texture, int layer, String tag) {
		super(world, position, model, texture, layer, tag);

		weapon = Weapon.getWeaponByName("shotgun");

		source = AudioSystem.createSource();
		jumpSound = AudioSystem.getAudioFile("res/sounds/jump.wav");
	}

	public void update() {
		checkInput();
		updateCamera();
		if (grounded) {
			moveGround();
		} else {
			moveAir();
		}
		applyFriction();
		applyGravity();
		move();

		// RESSTART
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			position = Vector3.zero.clone().add(new Vector3(0, 3, 0));
			motion = Vector3.zero.clone();
			flatMotion = Vector3.zero.clone();
		}

	}

	public void render(SpriteBatch batch) {
		if (weapon != null) {
			// weapon.render(batch, world.getCamera().position,
			// world.getCamera().rotation, 1);
		}
	}

	private void checkInput() {
		keyJump = Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !keyJumpDown;
		keyJumpDown = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		keyHop = (keyHop || keyJump) && keyJumpDown;
	}

	private void moveGround() {
		Quaternion rotation = Quaternion.axisAngle(0, 1, 0,
				-world.getCamera().rotation.y);
		Vector3 forward = rotation.rotate(new Vector3(0, 0, 1));
		Vector3 right = rotation.rotate(new Vector3(-1, 0, 0));
		Vector3 t = new Vector3(0, 0, 0);

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			t.add(forward);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			t.add(forward.clone().mul(-1));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			t.add(right.clone().mul(-1));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			t.add(right);
		}
		if (keyJump || keyHop) {
			motion.y = jump;
			keyHop = false;
			source.play(jumpSound);
		}

		t.normalize();

		flatMotion.add(t.mul(gAcceleration * Time.deltaTime));
	}

	private void moveAir() {
		Quaternion rotation = Quaternion.axisAngle(0, 1, 0,
				-world.getCamera().rotation.y);
		Vector3 forward = rotation.rotate(new Vector3(0, 0, 1));
		Vector3 right = rotation.rotate(new Vector3(-1, 0, 0));
		Vector3 t = new Vector3(0, 0, 0);

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			t.add(forward);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			t.add(forward.clone().mul(-1));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			t.add(right.clone().mul(-1));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			t.add(right);
		}

		t.normalize();

		flatMotion.add(t.mul(aAcceleration * Time.deltaTime));
	}

	private void applyFriction() {
		if (grounded) {
			float cSpeed = flatMotion.length();
			float friction = -Mathf.pow(Mathf.pow(gAcceleration, 1f / gSpeed),
					-(cSpeed - gSpeed)) + gAcceleration;
			Vector3 fMotion = flatMotion.clone().normalize().mul(-1);
			flatMotion.add(fMotion.mul(friction * Time.deltaTime));
		} else {
			float cSpeed = flatMotion.length();
			float friction = -Mathf.pow(Mathf.pow(aAcceleration, 1f / aSpeed),
					-(cSpeed - aSpeed)) + aAcceleration;
			Vector3 fMotion = flatMotion.clone().normalize().mul(-1);
			flatMotion.add(fMotion.mul(friction * Time.deltaTime));
		}
	}

	private void applyGravity() {
		motion.y -= gravity * Time.deltaTime;
	}

	private void move() {
		Vector3 m = motion.clone().add(flatMotion).mul(Time.deltaTime);
		RayHit hit = new RayHit();
		hit.setDistance(1f + Mathf.abs(m.y));

		// check down motion
		if (world.rayCast(new Ray(position, new Vector3(0, -1, 0)), hit,
				GameObject.LAYER_STATIC)) {
			if (motion.y < 0) {
				position = hit.getHitPoint().add(new Vector3(0, 1, 0));
				motion.y = 0;
				m.y = 0;
			}
		}
		hit = new RayHit();
		hit.setDistance(-0.7f - m.y);

		// check down motion
		if (world.rayCast(new Ray(position, new Vector3(0, 1, 0)), hit,
				GameObject.LAYER_STATIC)) {
			position = hit.getHitPoint().add(new Vector3(0, -0.7f, 0));
			motion.y = 0;
			m.y = 0;
		}

		boolean xmove = true;
		boolean zmove = true;

		Vector3 v = new Vector3(m.x, 0, m.z);
		float l = v.length();

		for (float height = -0.4f; height < 0.6f; height += 0.1f) {
			hit = new RayHit();
			hit.setDistance(l);
			hit.setDistance(Mathf.abs(m.x) + 0.1f);
			if (world.rayCast(
					new Ray(position.clone().add(new Vector3(0, height, 0)),
							new Vector3(m.x, 0, 0).normalize()), hit,
					GameObject.LAYER_STATIC)) {
				xmove = false;
				flatMotion.x = 0;
			}
			hit = new RayHit();
			hit.setDistance(Mathf.abs(m.z) + 0.1f);
			if (world.rayCast(
					new Ray(position.clone().add(new Vector3(0, height, 0)),
							new Vector3(0, 0, m.z).normalize()), hit,
					GameObject.LAYER_STATIC)) {
				zmove = false;
				flatMotion.z = 0;
			}
		}

		// check if grounded
		hit.setDistance(1.1f);
		if (world.rayCast(new Ray(position, new Vector3(0, -1, 0)), hit,
				GameObject.LAYER_STATIC)) {
			grounded = true;
		} else {
			grounded = false;
		}
		if (xmove)
			position.x += m.x;
		position.y += m.y;
		if (zmove)
			position.z += m.z;
	}

	public void updateCamera() {
		Camera cam = world.getCamera();
		cam.rotation.y += Mouse.getDX() / 10f;
		cam.rotation.x -= Mouse.getDY() / 10f;

		if (cam.rotation.x > 80)
			cam.rotation.x = 80;
		if (cam.rotation.x < -80)
			cam.rotation.x = -80;

		Mouse.setGrabbed(true);
		Mouse.setCursorPosition((int) Renderer.getWidth() / 2,
				(int) Renderer.getHeight() / 2);

		cam.position.set(position.clone().add(new Vector3(0, 0.6f, 0)));
	}

}
