package org.fischman.alex.simpleworld.Engine3D;

public class Triangle3D implements Object3D {
	public Point3D v1;
	public Point3D v2;
	public Point3D v3;

	public Triangle3D(Point3D i1, Point3D i2, Point3D i3, float[] iColor) {
		this(i1, i2, i3);
		v1.mColor = iColor;
		v2.mColor = iColor;
		v3.mColor = iColor;
	}

	public Triangle3D(Point3D i1, Point3D i2, Point3D i3) {
		v1 = i1;
		v2 = i2;
		v3 = i3;
	}

	public Triangle3D() {
		this(new Point3D(), new Point3D(), new Point3D());
	}

	public float[] getVertexData() {
		return new float[]{v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z};
	}

	public float[] getColorData() {
		return new float[]{
		v1.mColor[0], v1.mColor[1], v1.mColor[2], v1.mColor[3],
		v2.mColor[0], v2.mColor[1], v2.mColor[2], v2.mColor[3],
		v3.mColor[0], v3.mColor[1], v3.mColor[2], v3.mColor[3]
		};
	}

	public void translate(float x, float y, float z) {
		v1.translate(x, y, z);
		v2.translate(x, y, z);
		v3.translate(x, y, z);
	}
}
