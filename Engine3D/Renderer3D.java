package com.example;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class Renderer3D implements GLSurfaceView.Renderer {
	private int mProgram;
	private static final String vertexShaderCode =
	"uniform mat4 uMVPMatrix;" +
	"attribute vec4 vPosition;" +
	"attribute vec4 vColor;" +
	"varying vec4 aColor;" +
	"void main() {" +
	"  aColor = vColor;" +
	"  gl_Position = uMVPMatrix * vPosition;" +
	"}";
	private static final String fragmentShaderCode =
	"varying vec4 aColor;" +
	"void main() {" +
	"  gl_FragColor = aColor;" +
	"}";

	private void getShader() {
		int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		GLES20.glShaderSource(vertexShader, vertexShaderCode);
		GLES20.glCompileShader(vertexShader);
		int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		GLES20.glShaderSource(fragmentShader, fragmentShaderCode);
		GLES20.glCompileShader(fragmentShader);
		mProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(mProgram, vertexShader);
		GLES20.glAttachShader(mProgram, fragmentShader);
		GLES20.glLinkProgram(mProgram);
	}

	private Object3DHolder mObjects = new Object3DHolder();

	void addObject(Object3D object3D) {
		mObjects.addObject(object3D);
	}

	void addObjects(Object3D[] object3Ds) {
		mObjects.addObjects(object3Ds);
	}

	float xTranslate;
	float yTranslate;
	float zTranslate;
	float xAngle;
	float yAngle;
	private float[] backgroundColor = new float[]{0, 1, 1, 1};

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3]);

		getShader();
	}

	public void onDrawFrame(GL10 gl) {
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		//Background is brighter the farther you look up
		backgroundColor[1] = (float) (Math.sin(-yAngle * Math.PI / 180) / 2 + 0.5);
		backgroundColor[2] = (float) (Math.sin(-yAngle * Math.PI / 180) / 2 + 0.5);
		GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3]);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		float[] mViewMatrix = new float[16];
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0);
		Matrix.rotateM(mViewMatrix, 0, xAngle, 0, 1, 0);
		Matrix.rotateM(mViewMatrix, 0, yAngle, 1, 0, 0);
		Matrix.translateM(mViewMatrix, 0, xTranslate, yTranslate, zTranslate);
		float[] mMVPMatrix = new float[16];
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		GLES20.glUseProgram(mProgram);
		int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 12, mObjects.getObjectData());
		int mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
		GLES20.glEnableVertexAttribArray(mColorHandle);
		GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, 16, mObjects.getColorData());
		int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mObjects.getDataSize() / 3 / 4);
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mColorHandle);
	}

	private float[] mProjectionMatrix = new float[16];

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);
	}
}
