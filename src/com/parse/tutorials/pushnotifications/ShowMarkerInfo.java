package com.parse.tutorials.pushnotifications;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ShowMarkerInfo extends FragmentActivity {
	private String markerTitle,market1, market2, market3;
	private Button close;

	public static final String TAG = "ImgDisplayActivity";

	private ImageView imgDisPlay;
	private LinearLayout lLayoutDisplay;
	private FrameLayout fLayoutDisplay;

	public static final int NONE = 0;
	public static final int DRAG = 1;
	public static final int ZOOM = 2;
	private int mode = NONE;
	private Matrix matrix;
	private Matrix currMatrix;
	private PointF starPoint;
	private PointF midPoint;
	private float startDistance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		markerTitle = intent.getStringExtra("MarkTitle");
		market1 =  "85度c";
		market2 =  "多那之";
		market3 =  "通霄";
		if (markerTitle.equals(market1)) {
			setContentView(R.layout.marker_info);
		}
		if (markerTitle.equals(market2)) {
			setContentView(R.layout.marker2_info);
		}
		if (markerTitle.equals(market3)) {
			setContentView(R.layout.marker3_info);
		}
		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(back);

		fLayoutDisplay = (FrameLayout) findViewById(R.id.tab1);
		lLayoutDisplay = (LinearLayout) findViewById(R.id.linearLayout_img_display);
		imgDisPlay = (ImageView) findViewById(R.id.img_display);
		matrix = new Matrix();
		currMatrix = new Matrix();
		starPoint = new PointF();
		imgDisPlay.setOnTouchListener(new ImageViewOnTouchListener());
	}

	final class ImageViewOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				// Log.i(TAG, "一隻手指");
				currMatrix.set(matrix);
				starPoint.set(event.getX(), event.getY());
				mode = DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				// Log.i(TAG, "兩隻手指");
				startDistance = distance(event);
				// Log.i(TAG, startDistance + "");
				if (startDistance > 5f) {
					mode = ZOOM;
					currMatrix.set(matrix);
					midPoint = getMidPoint(event);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					// Log.i(TAG, "一隻手指拖曳");
					float dx = event.getX() - starPoint.x;
					float dy = event.getY() - starPoint.y;
					matrix.set(currMatrix);
					matrix.postTranslate(dx, dy);
				} else if (mode == ZOOM) {
					// Log.i(TAG, "正在縮放");
					float distance = distance(event);
					if (distance > 5f) {
						matrix.set(currMatrix);
						float cale = distance / startDistance;
						matrix.preScale(cale, cale, midPoint.x, midPoint.y);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
				break;
			default:
				break;
			}
			imgDisPlay.setImageMatrix(matrix);
			return true;
		}
	}

	private float distance(MotionEvent e) {
		float eX = e.getX(1) - e.getX(0);
		float eY = e.getY(1) - e.getY(0);
		return FloatMath.sqrt(eX * eX + eY * eY);
	}

	private PointF getMidPoint(MotionEvent event) {
		float x = (event.getX(1) - event.getX(0)) / 2;
		float y = (event.getY(1) - event.getY(0)) / 2;
		return new PointF(x, y);
	}

	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

}
