package com.rt96h.graphics.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.File;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.rt96h.math.Matrix4;
import com.rt96h.math.Vector3;
import com.rt96h.utils.Converter;

public class Shader {

	public static final int SHADER_NOSHADE = 0;
	public static final int SHADER_DIFFUSE = 1;
	public static final int SHADER_SPECULAR = 2;
	public static final int SHADER_DEPTH = 1001;

	private int shaderProgram = -1;
	private int vertexShader = -1;
	private int fragmentShader = -1;

	public Shader(String vertexShaderSource, String fragmentShaderSource) {
		shaderProgram = glCreateProgram();
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err
					.println("Vertex shader wasn't able to be compiled correctly. "
							+ vertexShaderSource);
		}
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err
					.println("Fragment shader wasn't able to be compiled correctly. "
							+ fragmentShaderSource);
		}
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
	}

	public void setUniformi(String uniform, int value) {
		int l = glGetUniformLocation(shaderProgram, uniform);
		GL20.glUniform1i(l, value);
	}

	public void setUniformf(String uniform, Vector3 v) {
		int l = glGetUniformLocation(shaderProgram, uniform);
		GL20.glUniform3f(l, v.x, v.y, v.z);
	}

	public void setUniformf(String uniform, Matrix4 value) {
		int l = glGetUniformLocation(shaderProgram, uniform);
		GL20.glUniformMatrix4(l, true, Converter.toFloatBuffer(value));
	}
	
	public void setUniformb(String uniform, boolean value){
		int l = glGetUniformLocation(shaderProgram, uniform);
		if(value)
			GL20.glUniform1i(l, GL11.GL_TRUE);			
		else
			GL20.glUniform1i(l, GL11.GL_FALSE);
			
	}
	
	public void use() {
		GL20.glUseProgram(shaderProgram);
	}

	public void reset() {
		GL20.glUseProgram(0);
	}

	public static Shader loadShaderFromFile(File vert, File frag) {
		try {
			Scanner s = new Scanner(vert);
			String vertex = s.useDelimiter("\\Z").next();
			s.close();
			s = new Scanner(frag);
			String fragment = s.useDelimiter("\\Z").next();
			s.close();

			return new Shader(vertex, fragment);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getID() {
		return shaderProgram;
	}
}
