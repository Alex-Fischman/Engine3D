package org.fischman.alex.simpleworld.Engine3D;

public interface Object3D {
	float[] getVertexData();

	float[] getColorData();

	void translate(float x, float y, float z);
}
