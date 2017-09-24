package org.fischman.alex.simpleworld.Engine3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;

class Object3DHolder {
	private ArrayList<Object3D> mObjects = new ArrayList<>();

	void addObject(Object3D object3D) {
		mObjects.add(object3D);
	}

	void addObjects(Object3D[] object3Ds) {
		mObjects.addAll(Arrays.asList(object3Ds));
	}

	int getDataSize() {
		int outputSize = 0;
		for (Object3D object3D : mObjects) {
			outputSize += object3D.getVertexData().length * 4;
		}
		return outputSize;
	}

	FloatBuffer getObjectData() {
		FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(getDataSize()).order(ByteOrder.nativeOrder()).asFloatBuffer();
		float[] objectData;
		for (Object3D object3D : mObjects) {
			objectData = object3D.getVertexData();
			for (float f : objectData) {
				vertexBuffer.put(f);
			}
		}
		vertexBuffer.position(0);
		return vertexBuffer;
	}

	FloatBuffer getColorData() {
		FloatBuffer colorBuffer = ByteBuffer.allocateDirect(getDataSize() * 4 / 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
		float[] colorData;
		for (Object3D object3D : mObjects) {
			colorData = object3D.getColorData();
			for (float f : colorData) {
				colorBuffer.put(f);
			}
		}
		colorBuffer.position(0);
		return colorBuffer;
	}
}
