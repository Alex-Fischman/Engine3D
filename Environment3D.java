package com.example;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

class Environment3D extends GLSurfaceView {
	private Renderer3D mRenderer;

	void translateCamera(float x, float y, float z) {
		mRenderer.xTranslate += x;
		mRenderer.yTranslate += y;
		mRenderer.zTranslate += z;
	}

	void setCameraPosition(float x, float y, float z) {
		mRenderer.xTranslate = x;
		mRenderer.yTranslate = y;
		mRenderer.zTranslate = z;
	}

	void rotateCamera(float x, float y) {
		mRenderer.xAngle += x;
		mRenderer.yAngle += y;
		if (mRenderer.yAngle > 90) {
			mRenderer.yAngle = 90;
		} else if (mRenderer.yAngle < -90) {
			mRenderer.yAngle = -90;
		}
	}

	void setCameraRotation(float x, float y) {
		mRenderer.xAngle = x;
		mRenderer.yAngle = y;
		if (mRenderer.yAngle > 90) {
			mRenderer.yAngle = 90;
		} else if (mRenderer.yAngle < -90) {
			mRenderer.yAngle = -90;
		}
	}

	Environment3D(Context context, Object3D[] object3Ds) {
		super(context);
		setEGLContextClientVersion(2);
		mRenderer = new Renderer3D();
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		mRenderer.addObjects(object3Ds);
		mScaleDetector = new ScaleGestureDetector(context, new PinchListener());
	}

	Environment3D(Context context) {
		this(context, new Object3D[0]);
	}

	void addObject(Object3D object3D) {
		mRenderer.addObject(object3D);
	}

	void addObjects(Object3D... object3Ds) {
		mRenderer.addObjects(object3Ds);
	}

	private ScaleGestureDetector mScaleDetector;
	private float scaleFactor = PinchListener.INVALID;

	private float mPreviousX;
	private float mPreviousY;

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		mScaleDetector.onTouchEvent(e);
		float x = e.getX();
		float y = e.getY();
		switch (e.getActionMasked()) {
			case MotionEvent.ACTION_POINTER_UP:
				break;
			case MotionEvent.ACTION_UP:
				if (e.getPointerCount() > 0) {
					scaleFactor = PinchListener.INVALID;
					x = mPreviousX;
					y = mPreviousY;
				}
			case MotionEvent.ACTION_MOVE:
				if (scaleFactor != PinchListener.INVALID) {
					translateCamera(
					(float) Math.sin(mRenderer.xAngle * Math.PI / 180) * scaleFactor * -1,
					(float) Math.sin(mRenderer.yAngle * Math.PI / 180) * scaleFactor,
					(float) Math.cos(mRenderer.xAngle * Math.PI / 180) * scaleFactor
					);
					scaleFactor = PinchListener.INVALID;
				} else {
					rotateCamera(-(x - mPreviousX) / 50, -(y - mPreviousY) / 50);
				}
		}
		mPreviousX = x;
		mPreviousY = y;
		requestRender();
		return true;
	}

	private class PinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		static final int INVALID = Integer.MAX_VALUE;

		public boolean onScale(ScaleGestureDetector detector) {
			scaleFactor = detector.getScaleFactor() > 1 ? 1 : -1;
			scaleFactor *= detector.getScaleFactor();
			scaleFactor /= 10;
			return true;
		}
	}
}
