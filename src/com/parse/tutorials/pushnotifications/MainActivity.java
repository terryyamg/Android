package com.parse.tutorials.pushnotifications;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.TabHost.TabSpec;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

public class MainActivity extends FragmentActivity {
	private static final int LOAD_DISPLAY_TIME = 1500;

	/* tab1 */
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

	/* tab2 */
	private GoogleMap map;
	private float coordinate[][] = {
			{ (float) 22.6297370, (float) 120.3278820 },
			{ (float) 22.6271340, (float) 120.3193380 },
			{ (float) 22.6736570, (float) 120.3121400 },
			{ (float) 22.6609120, (float) 120.3063850 },
			{ (float) 23.5648240, (float) 119.5653820 } };

	/* tab3 */
	private RadioButton genderFemaleButton;
	private RadioButton genderMaleButton;
	private EditText ageEditText;
	private RadioGroup genderRadioGroup;
	public static final String GENDER_MALE = "male";
	public static final String GENDER_FEMALE = "female";

	/* tab4 */
	private TextView output;
	private Button start_button, stop_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Track app opens.
		ParseAnalytics.trackAppOpened(getIntent());

		// Set up our UI member properties.
		/*
		 * this.genderFemaleButton = (RadioButton)
		 * findViewById(R.id.gender_female_button); this.genderMaleButton =
		 * (RadioButton) findViewById(R.id.gender_male_button); this.ageEditText
		 * = (EditText) findViewById(R.id.age_edit_text); this.genderRadioGroup
		 * = (RadioGroup) findViewById(R.id.gender_radio_group);
		 */
		/* tab1 */
		fLayoutDisplay = (FrameLayout) findViewById(R.id.tab1);
		lLayoutDisplay = (LinearLayout) findViewById(R.id.linearLayout_img_display);
		imgDisPlay = (ImageView) findViewById(R.id.img_display);
		matrix = new Matrix();
		currMatrix = new Matrix();
		starPoint = new PointF();
		imgDisPlay.setOnTouchListener(new ImageViewOnTouchListener());

		/* tab2 */
		// google map
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		LatLng p1 = new LatLng(coordinate[0][0], coordinate[0][1]);
		LatLng p2 = new LatLng(coordinate[1][0], coordinate[1][1]);
		LatLng p3 = new LatLng(coordinate[2][0], coordinate[2][1]);
		LatLng p4 = new LatLng(coordinate[3][0], coordinate[3][1]);
		LatLng p5 = new LatLng(coordinate[4][0], coordinate[4][1]);
		if (map != null) {
			// google mark
			map.addMarker(new MarkerOptions().position(p1).title("多那之高雄中正門市")
					.snippet("咖啡．烘培"));
			map.addMarker(new MarkerOptions().position(p2).title("多那之高雄文化門市 ")
					.snippet("咖啡．烘培"));
			map.addMarker(new MarkerOptions().position(p3).title("多那之高雄自由門市")
					.snippet("咖啡．烘培"));
			map.addMarker(new MarkerOptions().position(p4).title("多那之高雄明誠門市")
					.snippet("咖啡．烘培"));
			map.addMarker(new MarkerOptions().position(p5).title("多那之澎湖馬公門市")
					.snippet("咖啡．烘培"));
			// google location
			setUpMap();
		}
		/* tab4 */
		output = (TextView) findViewById(R.id.output);
		start_button = (Button) findViewById(R.id.start_button);
		stop_button = (Button) findViewById(R.id.stop_button);
		
		start_button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start_Click(v);
			}

		});
		start_button.performClick(); //自動按按鈕
		stop_button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stop_Click(v);
			}

		});

		setupViewComponent();
	}

	/* tab1 */
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

	/* tab2 */
	private void setUpMap() {
		// Enable MyLocation Layer of Google Map
		map.setMyLocationEnabled(true);

		// Get LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		// Create a criteria object to resrieve provider
		Criteria criteria = new Criteria();

		// Get the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);

		// Get Current Location
		Location myLocation = locationManager.getLastKnownLocation(provider);

		// set map type
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// Get latitude of the current location
		double latitude = myLocation.getLatitude();

		// Get longitude of the current location
		double longitude = myLocation.getLongitude();

		// Create a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// Show the current location in Google Map
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		map.animateCamera(CameraUpdateFactory.zoomTo(15));
		map.addMarker(new MarkerOptions()
				.position(new LatLng(latitude, longitude))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.walk))
				.title("現在位置"));
	}

	/* tab4 */
	public void start_Click(View view) {
		float latitude = coordinate[0][0];
		float longitude = coordinate[0][1];
		Intent intent = new Intent(this, GPSService.class);
		intent.putExtra("LATITUDE", latitude);
		intent.putExtra("LONGITUDE", longitude);
		startService(intent);
		output.setText("服務啟動中");
	}

	public void stop_Click(View view) {
		Intent intent = new Intent(this, GPSService.class);
		stopService(intent);
		output.setText("服務停止中");
	}

	private void setupViewComponent() {
		/* tabHost */
		// 從資源類別R中取得介面元件
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab1));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab2));
		spec.setContent(R.id.tab2);
		tabHost.addTab(spec);

		/*
		 * spec = tabHost.newTabSpec("tab3"); spec.setIndicator("",
		 * getResources().getDrawable(R.drawable.tab3));
		 * spec.setContent(R.id.tab3); tabHost.addTab(spec);
		 */

		spec = tabHost.newTabSpec("tab4");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab4));
		spec.setContent(R.id.tab4);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab5");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab5));
		spec.setContent(R.id.tab5);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// 設定tab標籤的字體大小
		TabWidget tabWidget = (TabWidget) tabHost
				.findViewById(android.R.id.tabs);
		View tabView = tabWidget.getChildTabViewAt(0);
		TextView tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);

		tabView = tabWidget.getChildTabViewAt(1);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);

		/*
		 * tabView = tabWidget.getChildTabViewAt(2); tab = (TextView)
		 * tabView.findViewById(android.R.id.title); tab.setTextSize(10);
		 */

		tabView = tabWidget.getChildTabViewAt(2);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);

		tabView = tabWidget.getChildTabViewAt(3);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);
	}

	/* tab3 */
	/*
	 * @Override public void onStart() { super.onStart();
	 * 
	 * // Display the current values for this user, such as their age and //
	 * gender. displayUserProfile(); refreshUserProfile(); }
	 * 
	 * // Save the user's profile to their installation. public void
	 * saveUserProfile(View view) { String ageTextString =
	 * ageEditText.getText().toString();
	 * 
	 * if (ageTextString.length() > 0) {
	 * ParseInstallation.getCurrentInstallation().put("age",
	 * Integer.valueOf(ageTextString)); }
	 * 
	 * if (genderRadioGroup.getCheckedRadioButtonId() == genderFemaleButton
	 * .getId()) { ParseInstallation.getCurrentInstallation().put("gender",
	 * GENDER_FEMALE); } else if (genderRadioGroup.getCheckedRadioButtonId() ==
	 * genderMaleButton .getId()) {
	 * ParseInstallation.getCurrentInstallation().put("gender", GENDER_MALE); }
	 * else { ParseInstallation.getCurrentInstallation().remove("gender"); }
	 * 
	 * InputMethodManager imm = (InputMethodManager)
	 * getSystemService(Context.INPUT_METHOD_SERVICE);
	 * imm.hideSoftInputFromWindow(ageEditText.getWindowToken(), 0);
	 * 
	 * ParseInstallation.getCurrentInstallation().saveInBackground( new
	 * SaveCallback() {
	 * 
	 * @Override public void done(ParseException e) { if (e == null) { Toast
	 * toast = Toast.makeText( getApplicationContext(),
	 * R.string.alert_dialog_success, Toast.LENGTH_SHORT); toast.show(); } else
	 * { e.printStackTrace();
	 * 
	 * Toast toast = Toast.makeText( getApplicationContext(),
	 * R.string.alert_dialog_failed, Toast.LENGTH_SHORT); toast.show(); } } });
	 * }
	 * 
	 * // Refresh the UI with the data obtained from the current
	 * ParseInstallation // object. private void displayUserProfile() { String
	 * gender = ParseInstallation.getCurrentInstallation().getString( "gender");
	 * int age = ParseInstallation.getCurrentInstallation().getInt("age");
	 * 
	 * if (gender != null) {
	 * genderMaleButton.setChecked(gender.equalsIgnoreCase(GENDER_MALE));
	 * genderFemaleButton.setChecked(gender .equalsIgnoreCase(GENDER_FEMALE)); }
	 * else { genderMaleButton.setChecked(false);
	 * genderFemaleButton.setChecked(false); }
	 * 
	 * if (age > 0) { ageEditText.setText(Integer.valueOf(age).toString()); }
	 * 
	 * }
	 * 
	 * // Get the latest values from the ParseInstallation object. private void
	 * refreshUserProfile() {
	 * ParseInstallation.getCurrentInstallation().refreshInBackground( new
	 * RefreshCallback() {
	 * 
	 * @Override public void done(ParseObject object, ParseException e) { if (e
	 * == null) { displayUserProfile(); } } }); }
	 */
}
