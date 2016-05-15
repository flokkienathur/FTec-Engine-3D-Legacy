package com.rt96h.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.rt96h.graphics.camera.Camera;
import com.rt96h.graphics.camera.OrthagonalCamera;
import com.rt96h.graphics.shader.Shader;
import com.rt96h.math.Matrix4;
import com.rt96h.math.Vector2;
import com.rt96h.math.Vector3;

public class SpriteBatch {
	private boolean drawing = false;
	private Camera c;
	public Matrix4 matrix;
	public Color4 color;
	private Texture texture, texture2, texture3;
	private Shader shader;
	private FloatBuffer vertexBuffer, colorBuffer, textureBuffer, normalBuffer;

	private int idx = 0;
	private final int vertexCount;

	public SpriteBatch(int vertexCount) {
		c = new OrthagonalCamera(1, 1);
		this.vertexCount = vertexCount;
		vertexBuffer = BufferUtils.createFloatBuffer(vertexCount * 3);
		colorBuffer = BufferUtils.createFloatBuffer(vertexCount * 4);
		textureBuffer = BufferUtils.createFloatBuffer(vertexCount * 2);
		normalBuffer = BufferUtils.createFloatBuffer(vertexCount * 3);

		color = Color4.white.clone();

		matrix = new Matrix4();
	}

	public void begin() {
		if (drawing)
			return;
		vertexBuffer.clear();
		colorBuffer.clear();
		textureBuffer.clear();
		normalBuffer.clear();
		matrix.identity();

		drawing = true;
	}

	public void vertex(Vector3 p, Color4 c, Vector2 u, Vector3 n) {
		vertex(p.x, p.y, p.z, c.r, c.g, c.b, c.a, u.x, u.y, n.x, n.y, n.z);
	}

	public void vertex(float x, float y, float z, float r, float g, float b,
			float a, float u, float v, float nx, float ny, float nz) {
		// put extra vertex in
		vertexBuffer.put(matrix.transformX(x, y, z))
				.put(matrix.transformY(x, y, z))
				.put(matrix.transformZ(x, y, z));

		colorBuffer.put(r * color.r).put(g * color.g).put(b * color.b)
				.put(a * color.a);

		// texture transforming
		if (texture != null)
			textureBuffer.put(texture.getFracX() + texture.getFracWidth() * u)
					.put(texture.getFracY() + texture.getFracHeight() * v);
		else
			textureBuffer.put(u).put(v);

		// normalBuffer.put(matrix.transformX(nx,ny,nz)).put(matrix.transformY(nx,ny,nz)).put(matrix.transformZ(nx,ny,nz));
		normalBuffer.put(nx).put(ny).put(nz);

		idx++;
	}

	public void drawTexture(float x, float y, float w, float h, Texture t) {
		this.setTexture(t);

		if (getLeft() < 6)
			flush();

		vertex(x, y, 0, 1, 1, 1, 1, 0, 0, 0, 0, -1);
		vertex(x + w, y + h, 0, 1, 1, 1, 1, 1, 1, 0, 0, -1);
		vertex(x + w, y, 0, 1, 1, 1, 1, 1, 0, 0, 0, -1);

		vertex(x, y, 0, 1, 1, 1, 1, 0, 0, 0, 0, -1);
		vertex(x, y + h, 0, 1, 1, 1, 1, 0, 1, 0, 0, -1);
		vertex(x + w, y + h, 0, 1, 1, 1, 1, 1, 1, 0, 0, -1);

	}

	public void drawBuffer(float x, float y, float w, float h, Texture t) {
		this.setTexture(t);

		if (getLeft() < 6)
			flush();

		vertex(x, y, 0, 1, 1, 1, 1, 0, 1, 0, 0, -1);
		vertex(x + w, y + h, 0, 1, 1, 1, 1, 1, 0, 0, 0, -1);
		vertex(x + w, y, 0, 1, 1, 1, 1, 1, 1, 0, 0, -1);

		vertex(x, y, 0, 1, 1, 1, 1, 0, 1, 0, 0, -1);
		vertex(x, y + h, 0, 1, 1, 1, 1, 0, 0, 0, 0, -1);
		vertex(x + w, y + h, 0, 1, 1, 1, 1, 1, 0, 0, 0, -1);

	}

	public void flush() {
		vertexBuffer.flip();
		colorBuffer.flip();
		textureBuffer.flip();
		normalBuffer.flip();

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);

		GL11.glVertexPointer(3, 0, vertexBuffer);
		GL11.glColorPointer(4, 0, colorBuffer);
		GL11.glTexCoordPointer(2, 0, textureBuffer);
		GL11.glNormalPointer(0, normalBuffer);

		if (texture != null) {
			texture.bind();
			Renderer.enableTexture(true);
		} else {
			Renderer.enableTexture(false);
		}
		if (shader != null) {
			shader.use();
			
			shader.setUniformf("ftecProjectionMatrix", c.getProjectionMatrix());
			if (texture != null) {
				shader.setUniformi("ftecTexture0", 0);
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				texture.bind();
			}else{
				shader.setUniformi("ftecTexture0", -1);
			}
			if (texture2 != null) {
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				shader.setUniformi("ftecTexture1", 1);
				texture2.bind();
			}else{
				shader.setUniformi("ftecTexture1", -1);
			}
			if (texture3 != null) {
				GL13.glActiveTexture(GL13.GL_TEXTURE2);
				shader.setUniformi("ftecTexture2", 2);
				texture3.bind();
			}else{
				shader.setUniformi("ftecTexture2", -1);
			}
		}
		
		c.apply(this);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, idx);

		if (texture != null) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			texture.unbind();
		}
		if (texture2 != null) {
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			texture2.unbind();
		}
		if (texture3 != null) {
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			texture3.unbind();
		}
		if (shader != null)
			shader.reset();

		/*
		 * GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		 * GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		 * GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		 * GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		 */

		idx = 0;

		vertexBuffer.clear();
		colorBuffer.clear();
		textureBuffer.clear();
		normalBuffer.clear();
	}

	public int getLeft() {
		return vertexCount - idx;
	}

	public void setColor(Color4 color) {
		this.color = color;
	}

	public Color4 getColor() {
		return color;
	}

	public void end() {
		if (!drawing)
			return;
		drawing = false;

		if (idx > 0)
			flush();
	}

	public boolean isDrawing() {
		return drawing;
	}

	public Camera getCamera() {
		return c;
	}

	public void setCamera(Camera camera) {
		c = camera;
	}

	public Texture getTexture() {
		return texture;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		if (this.shader != shader && drawing)
			flush();

		this.shader = shader;
	}

	public void setTexture(Texture t) {
		if (texture == null || t == null) {
			if (drawing)
				flush();

			texture = t;
			return;
		}

		if (t.getId() != texture.getId() && drawing)
			flush();

		texture = t;
	}

	public void setTexture2(Texture t) {
		if (texture2 == null || t == null) {
			if (drawing)
				flush();

			texture2 = t;
			return;
		}

		if (t.getId() != texture2.getId() && drawing)
			flush();

		texture2 = t;
	}

	public void setTexture3(Texture t) {
		if (texture3 == null || t == null) {
			if (drawing)
				flush();

			texture3 = t;
			return;
		}

		if (t.getId() != texture3.getId() && drawing)
			flush();

		texture3 = t;
	}
	
	
}
