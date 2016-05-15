package com.rt96h.hl3.world;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.rt96h.graphics.Buffer;
import com.rt96h.graphics.Renderer;
import com.rt96h.graphics.shader.Shader;
import com.rt96h.math.Matrix4;
import com.rt96h.math.Quaternion;
import com.rt96h.math.Vector3;
import com.rt96h.timer.Time;
import com.rt96h.world.World;

public class IngameWorld extends World{
	
	private Shader shader;
	private Buffer shadowMap;

	public void onInit(){
		super.onInit();
		shader = Shader.loadShaderFromFile(new File(
				"res/shaders/diffuse_vertex.glsl"), new File("res/shaders/diffuse_fragment.glsl"));
		batch.setShader(shader);
		
		shadowMap = new Buffer(4096,4096);
	}

	public void render() {
		float rotationx = 30;
		float rotationy = Time.runTime;
		Quaternion s = Quaternion.axisAngle(0, 1, 0, rotationy).mul(Quaternion.axisAngle(1, 0, 0, rotationx));
		Matrix4 mat = new Matrix4().scale(0.025f, 0.025f, -0.02f).rotateX(rotationx).rotateY(180 - rotationy).translate(batch.getCamera().position.clone().mul(-1));
		Vector3 dir = s.rotate(new Vector3(0,0,1));
		
		shader.use();
		shader.setUniformf("ftecLightDirection", dir);
		shader.setUniformi("ftecRenderType", Shader.SHADER_DEPTH);
		shader.setUniformi("ftecTextureEnabled", GL11.GL_FALSE);
		shader.setUniformi("ftecShadowsEnabled", GL11.GL_FALSE);
		shader.setUniformf("ftecShadowMatrix", mat);

		//render shadow world
		shadowMap.bind();
		Renderer.clear();
		batch.setTexture2(null);
		batch.begin();
		
		int l = objects.size();
		for (int i = 0; i < l; i++) {
			objects.get(i).render(batch);
		}
		
		batch.end();
		shadowMap.unbind();
		
		//setup normal rendering
		
		shader.use();
		
		shader.setUniformi("ftecTextureEnabled", GL11.GL_TRUE);
		shader.setUniformi("ftecShadowsEnabled", GL11.GL_TRUE);
		shader.setUniformi("ftecRenderType", Shader.SHADER_DIFFUSE);
		
		//render world normally
		buffer.bind();
		Renderer.clear();
		batch.setTexture2(shadowMap.getTexture());
		batch.begin();
		
		int l2 = objects.size();
		for (int i = 0; i < l2; i++) {
			objects.get(i).render(batch);
		}
		
		batch.end();
		buffer.unbind();
		
		postBatch.begin();
		postBatch.drawTexture(0, 0, 0.5f,1, buffer.getTexture());
		postBatch.drawTexture(0.5f, 0, 0.5f,1, shadowMap.getTexture());
		postBatch.end();
	}
}
