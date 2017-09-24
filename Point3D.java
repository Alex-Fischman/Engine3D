package com.example;

class Point3D implements Object3D {
	float x;
	float y;
	float z;
	float[] mColor = new float[]{1, 1, 1, 1};

	Point3D(float ix, float iy, float iz, float[] iColor) {
		this(ix, iy, iz);
		mColor = iColor;
	}

	Point3D(float ix, float iy, float iz) {
		x = ix;
		y = iy;
		z = iz;
	}

	Point3D() {
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
