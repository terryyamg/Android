package com.parse.tutorials.pushnotifications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

public class MainActivity extends FragmentActivity implements
		OnMarkerClickListener {
	private static final int LOAD_DISPLAY_TIME = 1500;
	/* update */
	protected static final int UPDATA_CLIENT = 0;
	protected static final int CHECK_UPDATE = 1;
	protected static final int DOWN_ERROR = 0;
	/** Called when the activity is first created. */
	private int serverVersion = 1; // �{�b����
	private int newServerVersion; // �̷s����
	private String downLoadApkUrl = "https://dl.dropbox.com/s/vahfdayyluk151z/Android.apk"; // ��m�̷s�ɮ׺��}

	/* �إ߮ୱ���| */

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
	private float coordinate[][][] = {
			{ { 0, (float) 22.6297370, (float) 120.3278820 },
					{ 0, (float) 22.6271340, (float) 120.3193380 } },
			{ { 1, (float) 24.1260010, (float) 120.6628070 } } };
	private String[] type = new String[] { "������", "�x����" };
	private String[] locationName = new String[] { "�h����������������", "�h����������ƪ���" };
	private String[][] type2 = new String[][] { { "�h����������������", "�h����������ƪ���" },
			{ "���p��~�������q" } };
	private Spinner sp1;// �Ĥ@�ӤU�Կ��
	private Spinner sp2;// �ĤG�ӤU�Կ��
	private Context context;

	ArrayAdapter<String> adapter;

	ArrayAdapter<String> adapter2;

	/* tab3 */
	private TextView info, info2;
	private Button scanner, scanner2;

	/* tab4 */
	private TextView output;
	private CheckBox checkBox_service;

	/* tab5 */
	private RadioButton genderFemaleButton;
	private RadioButton genderMaleButton;
	private EditText ageEditText, myNumber;
	private RadioGroup genderRadioGroup;
	public static final String GENDER_MALE = "male";
	public static final String GENDER_FEMALE = "female";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/* update */
		/* �[�JStrictMode�קK�o�� android.os.NetworkOnMainThreadException */
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		/*
		 * StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		 * .penaltyLog().penaltyDeath().build());
		 */

		JSONArray obj = getJson("http://terryyamg.github.io/myweb/update_verson.json"); // ��s��������ɦ�m
		Log.i("obj:", obj + "");
		try {
			for (int i = 0; i < obj.length(); i++) {
				JSONObject data = obj.getJSONObject(i);
				newServerVersion = Integer.parseInt(data.getString("code")); // code���W�١A��X��newServerVersion����
			}
		} catch (JSONException e) {

		} catch (NullPointerException e) {
			Log.i("data", "NullPointException");
		}

		new Thread(new Runnable() {
			public void run() {
				try {
					Message msg = new Message();
					msg.what = CHECK_UPDATE;
					handler.sendMessage(msg);

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block

				} catch (Exception e) {
					// TODO Auto-generated catch block

				}

			}
		}).start();

		/* �إ߮ୱ���| */
		addShortcut();
		// Track app opens.
		ParseAnalytics.trackAppOpened(getIntent());

		// Set up our UI member properties.

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
		try {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			LatLng p0_1 = new LatLng(coordinate[0][0][1], coordinate[0][0][2]);
			LatLng p0_2 = new LatLng(coordinate[0][1][1], coordinate[0][1][2]);
			LatLng p1_1 = new LatLng(coordinate[1][0][1], coordinate[1][0][2]);
			
			if (map != null) {
				map.setOnMarkerClickListener(this);
				// google mark
				map.addMarker(new MarkerOptions()
						.position(p0_1)
						.title("�h����������������")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.donutes_logo))
						.snippet("�@�ءD�M��"));
				map.addMarker(new MarkerOptions()
						.position(p0_2)
						.title("�h����������ƪ���")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.donutes_logo))
						.snippet("�@�ءD�M��"));
				map.addMarker(new MarkerOptions()
						.position(p1_1)
						.title("���p��~�������q")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_launcher))
						.snippet("�U�ݿԸ�"));
				// map.addMarker(new MarkerOptions()
				// .position(p4)
				// .title("���~���")
				// .icon(BitmapDescriptorFactory
				// .fromResource(R.drawable.donutes_logo))
				// .snippet("�������j�d�Ϥj�d��870-12��"));
				// google location
				setUpMap();
			}

		} catch (NullPointerException e) {
			Log.i("myLocation", "NullPointException");
		}

		context = this;

		// �{����ҩl�ɸ��J�Ĥ@�ӤU�Կ��
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1 = (Spinner) findViewById(R.id.type);
		sp1.setAdapter(adapter);
		sp1.setOnItemSelectedListener(selectListener);

		// �]���U�Կ��Ĥ@�Ӭ������A�ҥH�����J�����s�նi�ĤG�ӤU�Կ��
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, locationName);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2 = (Spinner) findViewById(R.id.type2);
		sp2.setAdapter(adapter2);
		sp2.setOnItemSelectedListener(selectListener2);

		/* tab3 */
		info = (TextView) findViewById(R.id.info);
		info2 = (TextView) findViewById(R.id.info2);
		scanner = (Button) findViewById(R.id.scanner);
		scanner2 = (Button) findViewById(R.id.scanner2);
		scanner2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				int scannerNumberInfo = ParseInstallation
						.getCurrentInstallation().getInt("scannerNumber");
				info2.setText("�z�w�g�ϥ�"
						+ Integer.valueOf(scannerNumberInfo).toString()
						+ "���u�f���");
			}
		});
		scanner.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent iScaner = new Intent("la.droid.qr.scan"); // �ϥ�QRDroid�����y�\��
				iScaner.putExtra("la.droid.qr.complete", true); // ����^�ǡA���I��scheme
				try {
					// �}�� QRDroid App �����y�\��A���� call back onActivityResult()
					startActivityForResult(iScaner, 0);
				} catch (ActivityNotFoundException ex) {
					// �Y�S�w�� QRDroid�A�h�}�� Google Play�ө��A����� QRDroid App
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("market://details?id=la.droid.qr"));
					startActivity(intent);
				}
			}
		});

		/* tab4 */
		output = (TextView) findViewById(R.id.output);
		checkBox_service = (CheckBox) findViewById(R.id.checkBox_service);
		checkBox_service.setChecked(getFromSP("checkBox1")); // checkBox�x�s�]�w

		if (checkBox_service.isChecked()) {
			start_Click();
		} else {
			stop_Click();
		}
		checkBox_service
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (checkBox_service.isChecked()) {
							start_Click();
							saveInSp("checkBox1", isChecked); // ����w����x�s�]�w

						} else {
							stop_Click();
							saveInSp("checkBox1", isChecked);// ������w����x�s�]�w
						}
					}

				});
		// checkBox_service.performClick(); //�۰ʫ�checkBox

		/* tab5 */
		// this.genderFemaleButton = (RadioButton)
		// findViewById(R.id.gender_female_button);
		// this.genderMaleButton = (RadioButton)
		// findViewById(R.id.gender_male_button);
		// this.ageEditText = (EditText) findViewById(R.id.age_edit_text);
		// this.myNumber = (EditText) findViewById(R.id.my_number);
		// this.genderRadioGroup = (RadioGroup)
		// findViewById(R.id.gender_radio_group);

		setupViewComponent();
	}

	/* update */
	public static JSONArray getJson(String url) {
		InputStream is = null;
		String result = "";
		// �Y�u�W��Ƭ��}�C�A�h�ϥ�JSONArray
		JSONArray jsonArray = null;
		// �Y�u�W��Ƭ��浧��ơA�h�ϥ�JSONObject
		// JSONObject jsonObj = null;
		// �z�LHTTP�s�u���o�^��
		try {
			HttpClient httpclient = new DefaultHttpClient(); // for port 80
			HttpGet httppost = new HttpGet(url); // �n��Get�A��Post�|�X�{
													// java.lang.string cannot
													// be converted to jsonarray
			HttpResponse response = httpclient.execute(httppost);
			Log.i("response:", response + ""); // �S���ȷ|catch���~�A�[�J�e��StrictMode�N�i�H
			HttpEntity entity = response.getEntity();
			Log.i("entity:", entity + "");
			is = entity.getContent();
			Log.i("is:", is + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Ū���^��
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf8"), 9999999); // 99999���Ǭy�j�p�A�Y��ƫܤj�A�i�ۦ�վ�
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// �v����o���
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.i("result:", result + ""); // LogCat�|�L�Xjson ex:[{"code":"1"}]
		} catch (Exception e) {
			e.printStackTrace();
		}
		// �ഫ��r��JSONArray
		try {
			jsonArray = new JSONArray(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	public void showUpdateDialog() {

		@SuppressWarnings("unused")
		AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
				.setTitle("��s����").setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("�̷s�u�f�X�ӰաA�֨ӤU����s")
				.setPositiveButton("�U��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // ������ܮ�
						downLoadApk();
					}

				}).show();

	}

	protected void downLoadApk() {
		final ProgressDialog pd; // �i�ױ���ܮ�
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("���b�U����s");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(downLoadApkUrl, pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // �����i�ױ���ܮ�
				} catch (Exception e) {
					pd.dismiss();
					Message msg = new Message();
					msg.what = DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		/* �p�G�۵����ܪ�ܷ�e��SDcard�����b����W�åB�O�i�Ϊ� */
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			pd.setMax(conn.getContentLength()); // ����ƥ����j�p
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(),
					"update.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				pd.setProgress(total); // �����e�U���q
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	/* �w��APK */
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW); // ����ʧ@
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive"); // ���檺�ƾ�����
		startActivity(intent);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case DOWN_ERROR:
				// �U��APK����
				Toast.makeText(getApplicationContext(), "�U���s��������", 1).show();
				break;
			case CHECK_UPDATE:
				// �ˬd��s

				if (serverVersion == 0) {
					serverVersion = newServerVersion;
				}

				if (serverVersion != newServerVersion) {
					Log.i("serverVersion:", serverVersion + ""); // ��ܲ{�b����
					Log.i("newServerVersion:", newServerVersion + ""); // ��̷ܳs����
					showUpdateDialog(); // �����s
				}
				break;
			}
		}
	};

	/* �إ߮ୱ���| */
	private void addShortcut() {
		Intent shortcutIntent = new Intent(getApplicationContext(),
				SplashScreen.class); // �Ұʱ��|�J�f�A�@���MainActivity�A���ϥΨ�L�J�f�h��J�۹�W�١Aex:���ϥ�SplashScreen
		shortcutIntent.setAction(Intent.ACTION_MAIN);
		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // shortcutIntent�e�J
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name)); // ���|app�W��
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(
						getApplicationContext(),// ���|app��
						R.drawable.ic_launcher));
		addIntent.putExtra("duplicate", false); // ���Ыؤ@��
		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // �w��
		getApplicationContext().sendBroadcast(addIntent); // �e�X�s��
	}

	/* tab1 */
	final class ImageViewOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				// Log.i(TAG, "�@�����");
				currMatrix.set(matrix);
				starPoint.set(event.getX(), event.getY());
				mode = DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				// Log.i(TAG, "�Ⱖ���");
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
					// Log.i(TAG, "�@������즲");
					float dx = event.getX() - starPoint.x;
					float dy = event.getY() - starPoint.y;
					matrix.set(currMatrix);
					matrix.postTranslate(dx, dy);
				} else if (mode == ZOOM) {
					// Log.i(TAG, "���b�Y��");
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

		try {

			// Get Current Location
			Location myLocation = locationManager
					.getLastKnownLocation(provider);

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
			// map.addMarker(new MarkerOptions().position(new LatLng(latitude,
			// longitude)).title("�{�b��m"));

		} catch (NullPointerException e) {
			Log.i("myLocation", "NullPointException");
		}

		/* GPS�}�Ҹ��X�T�{���� */
		try {
			if (!locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Log.i("GPSOpen:",
						locationManager
								.isProviderEnabled(LocationManager.GPS_PROVIDER)
								+ "");
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("GPS�]�w")
						.setMessage("�z�|���Ұ�GPS�A�n�Ұʶ�?")
						.setCancelable(false)
						.setNegativeButton("�Ұ�",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										startActivity(new Intent(
												Settings.ACTION_LOCATION_SOURCE_SETTINGS));
									}
								})
						.setPositiveButton("���Ұ�",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(MainActivity.this,
												"���Ұ�GPS�N�ϥκ����w��",
												Toast.LENGTH_SHORT).show();
									}
								}).show();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* �I��marker��� */
	@Override
	public boolean onMarkerClick(Marker marker) {
		Intent intent = new Intent(this, ShowMarkerInfo.class);
		intent.putExtra("MarkTitle", marker.getTitle());
		startActivity(intent);
		return false;
	}

	// �Ĥ@�ӤU�����O���ʬݦ�
	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// Ū���Ĥ@�ӤU�Կ��O��ܲĴX��
			int pos = sp1.getSelectedItemPosition();
			// ���s���ͷs��Adapter�A�Ϊ��O�G���}�Ctype2[pos]
			adapter2 = new ArrayAdapter<String>(context,
					android.R.layout.simple_spinner_item, type2[pos]);
			// ���J�ĤG�ӤU�Կ��Spinner
			sp2.setAdapter(adapter2);

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	// �ĤG�ӤU�����O���ʬݦ�
	private OnItemSelectedListener selectListener2 = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			setMapLocation();
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	public void setMapLocation() {
		try {
			int iSelect1 = sp1.getSelectedItemPosition();
			int iSelect2 = sp2.getSelectedItemPosition();
			double dLat = coordinate[iSelect1][iSelect2][1]; // �n�_�n
			double dLon = coordinate[iSelect1][iSelect2][2]; // �F��g
			// GeoPoint gp = new GeoPoint((int)(dLat * 1e6), (int)(dLon * 1e6));
			LatLng gg = new LatLng(dLat, dLon);
			map.moveCamera(CameraUpdateFactory.newLatLng(gg));
			map.animateCamera(CameraUpdateFactory.zoomTo(15));
		} catch (NullPointerException e) {
			Log.i("myLocation", "NullPointException");
		}
	}

	/* tab3 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (0 == requestCode && null != data && data.getExtras() != null) {
			// ���y���G�s��b key �� la.droid.qr.result ���Ȥ�
			String result = data.getExtras().getString("la.droid.qr.result");
			String setResult = "manlen";
			if (setResult.equals(result)) {
				String messsenger = "�w��ϥΥ����q�u�f���";
				int scannerNumber = ParseInstallation.getCurrentInstallation()
						.getInt("scannerNumber");
				scannerNumber++;
				ParseInstallation.getCurrentInstallation().put("scannerNumber",
						scannerNumber);
				ParseInstallation.getCurrentInstallation().saveInBackground(
						new SaveCallback() {
							@Override
							public void done(ParseException e) {
								if (e == null) {
									Toast toast = Toast.makeText(
											getApplicationContext(),
											R.string.scanner_success,
											Toast.LENGTH_SHORT);
									toast.show();
								} else {
									e.printStackTrace();

									Toast toast = Toast.makeText(
											getApplicationContext(),
											R.string.scanner_failed,
											Toast.LENGTH_SHORT);
									toast.show();
								}
							}
						});
				info2.setText("�z�w�g�ϥ�"
						+ Integer.valueOf(scannerNumber).toString() + "���u�f���");
				info.setTextSize(30);
				info.setText(messsenger); // �N���G��ܦb TextVeiw ��
			} else {
				String messsenger = "��p�A�z���y���D�����q�u�f";
				info.setTextSize(30);
				info.setText(messsenger); // �N���G��ܦb TextVeiw ��
			}

		}
	}

	/* tab4 */
	public void start_Click() {
		float latitude1 = coordinate[0][0][1];
		float longitude1 = coordinate[0][0][2];
		float latitude2 = coordinate[0][1][1];
		float longitude2 = coordinate[0][1][2];
		Intent intent = new Intent(this, GPSService.class);
		intent.putExtra("LATITUDE1", latitude1);
		intent.putExtra("LONGITUDE1", longitude1);
		intent.putExtra("LATITUDE2", latitude2);
		intent.putExtra("LONGITUDE2", longitude2);
		startService(intent);
		output.setText("�A�ȱҰʤ�");
	}

	public void stop_Click() {
		Intent intent = new Intent(this, GPSService.class);
		stopService(intent);
		output.setText("�A�Ȱ��");
	}

	/* �x�s�]�w */
	private boolean getFromSP(String key) {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("Android",
						android.content.Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	private void saveInSp(String key, boolean value) {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("Android",
						android.content.Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	private void setupViewComponent() {
		/* tabHost */
		// �q�귽���OR�����o��������
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

		spec = tabHost.newTabSpec("tab3");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab3));
		spec.setContent(R.id.tab3);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab4");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab4));
		spec.setContent(R.id.tab4);
		tabHost.addTab(spec);

		// spec = tabHost.newTabSpec("tab5");
		// spec.setIndicator("", getResources().getDrawable(R.drawable.tab5));
		// spec.setContent(R.id.tab5);
		// tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// �]�wtab���Ҫ��r��j�p
		TabWidget tabWidget = (TabWidget) tabHost
				.findViewById(android.R.id.tabs);
		View tabView = tabWidget.getChildTabViewAt(0);
		TextView tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);

		tabView = tabWidget.getChildTabViewAt(1);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);

		tabView = tabWidget.getChildTabViewAt(2);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);

		tabView = tabWidget.getChildTabViewAt(3);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);

		// tabView = tabWidget.getChildTabViewAt(4);
		// tab = (TextView) tabView.findViewById(android.R.id.title);
		// tab.setTextSize(10);

	}

	/* ���}�{�� */
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			closeAll();
			return true;
		}
		return super.onKeyDown(keycode, event);
	}

	public void closeAll() {
		new AlertDialog.Builder(MainActivity.this).setTitle("�T�w���}���{���H")
				.setMessage("��Home��i�I������")
				.setNegativeButton("���}", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setPositiveButton("�~��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Kill myself
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/* tab3 */

	@Override
	public void onStart() {
		super.onStart();

		// Display the current values for this user, such as their age and
		// gender.
		// displayUserProfile();
		// refreshUserProfile();
	}

	/* tab5 */
	// Save the user's profile to their installation.
	public void saveUserProfile(View view) {
		String ageTextString = ageEditText.getText().toString();
		String myNumberTextString = myNumber.getText().toString();

		if (ageTextString.length() > 0) {
			ParseInstallation.getCurrentInstallation().put("age",
					Integer.valueOf(ageTextString));
		}
		if (myNumberTextString.length() > 0) {
			ParseInstallation.getCurrentInstallation().put("mynumber",
					Integer.valueOf(myNumberTextString));
		}

		if (genderRadioGroup.getCheckedRadioButtonId() == genderFemaleButton
				.getId()) {
			ParseInstallation.getCurrentInstallation().put("gender",
					GENDER_FEMALE);
		} else if (genderRadioGroup.getCheckedRadioButtonId() == genderMaleButton
				.getId()) {
			ParseInstallation.getCurrentInstallation().put("gender",
					GENDER_MALE);
		} else {
			ParseInstallation.getCurrentInstallation().remove("gender");
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(ageEditText.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(myNumber.getWindowToken(), 0);

		ParseInstallation.getCurrentInstallation().saveInBackground(
				new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e == null) {
							Toast toast = Toast.makeText(
									getApplicationContext(),
									R.string.alert_dialog_success,
									Toast.LENGTH_SHORT);
							toast.show();
						} else {
							e.printStackTrace();

							Toast toast = Toast.makeText(
									getApplicationContext(),
									R.string.alert_dialog_failed,
									Toast.LENGTH_SHORT);
							toast.show();
						}
					}
				});
	}

	// Refresh the UI with the data obtained from the current ParseInstallation
	// object.
	private void displayUserProfile() {
		String gender = ParseInstallation.getCurrentInstallation().getString(
				"gender");
		int age = ParseInstallation.getCurrentInstallation().getInt("age");

		if (gender != null) {
			genderMaleButton.setChecked(gender.equalsIgnoreCase(GENDER_MALE));
			genderFemaleButton.setChecked(gender
					.equalsIgnoreCase(GENDER_FEMALE));
		} else {
			genderMaleButton.setChecked(false);
			genderFemaleButton.setChecked(false);
		}

		if (age > 0) {
			ageEditText.setText(Integer.valueOf(age).toString());
		}

	}

	// Get the latest values from the ParseInstallation object.
	private void refreshUserProfile() {
		ParseInstallation.getCurrentInstallation().refreshInBackground(
				new RefreshCallback() {

					@Override
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							// displayUserProfile();
						}
					}
				});
	}
}
