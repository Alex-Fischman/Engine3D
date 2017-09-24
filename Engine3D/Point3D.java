package com.example;

public class Point3D implements Object3D {
	public float x;
	public float y;
	public float z;
	public float[] mColor = new float[]{1, 1, 1, 1};

	public Point3D(float ix, float iy, float iz, float[] iColor) {
		this(ix, iy, iz);
		mColor = iColor;
	}

	public Point3D(float ix, float iy, float iz) {
		x = ix;
		y = iy;
		z = iz;
	}

	public Point3D() {
		this(0, 0, 0);
	}

	public float[] getVertexData() {
		return new float[]{x, y, z};
	}

	public float[] getColorData() {
		return mColor;
	}

	public void translate(float ix, float iy, float iz) {
		x += ix;
		y += iy;
		z += iz;
	}
}
