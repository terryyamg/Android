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
	private int serverVersion = 1; // 現在版本
	private int newServerVersion; // 最新版本
	private String downLoadApkUrl = "https://dl.dropbox.com/s/vahfdayyluk151z/Android.apk"; // 放置最新檔案網址

	/* 建立桌面捷徑 */

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
	private String[] type = new String[] { "高雄市", "台中市" };
	private String[] locationName = new String[] { "多那之高雄中正門市", "多那之高雄文化門市" };
	private String[][] type2 = new String[][] { { "多那之高雄中正門市", "多那之高雄文化門市" },
			{ "曼聯實業有限公司" } };
	private Spinner sp1;// 第一個下拉選單
	private Spinner sp2;// 第二個下拉選單
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
		/* 加入StrictMode避免發生 android.os.NetworkOnMainThreadException */
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		/*
		 * StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		 * .penaltyLog().penaltyDeath().build());
		 */

		JSONArray obj = getJson("http://terryyamg.github.io/myweb/update_verson.json"); // 更新版本文件檔位置
		Log.i("obj:", obj + "");
		try {
			for (int i = 0; i < obj.length(); i++) {
				JSONObject data = obj.getJSONObject(i);
				newServerVersion = Integer.parseInt(data.getString("code")); // code為名稱，抓出來newServerVersion為值
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

		/* 建立桌面捷徑 */
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
						.title("多那之高雄中正門市")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.donutes_logo))
						.snippet("咖啡．烘培"));
				map.addMarker(new MarkerOptions()
						.position(p0_2)
						.title("多那之高雄文化門市")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.donutes_logo))
						.snippet("咖啡．烘培"));
				map.addMarker(new MarkerOptions()
						.position(p1_1)
						.title("曼聯實業有限公司")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_launcher))
						.snippet("顧問諮詢"));
				// map.addMarker(new MarkerOptions()
				// .position(p4)
				// .title("建驊科技")
				// .icon(BitmapDescriptorFactory
				// .fromResource(R.drawable.donutes_logo))
				// .snippet("高雄市大寮區大寮路870-12號"));
				// google location
				setUpMap();
			}

		} catch (NullPointerException e) {
			Log.i("myLocation", "NullPointException");
		}

		context = this;

		// 程式剛啟始時載入第一個下拉選單
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1 = (Spinner) findViewById(R.id.type);
		sp1.setAdapter(adapter);
		sp1.setOnItemSelectedListener(selectListener);

		// 因為下拉選單第一個為茶類，所以先載入茶類群組進第二個下拉選單
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
				info2.setText("您已經使用"
						+ Integer.valueOf(scannerNumberInfo).toString()
						+ "次優惠方案");
			}
		});
		scanner.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent iScaner = new Intent("la.droid.qr.scan"); // 使用QRDroid的掃描功能
				iScaner.putExtra("la.droid.qr.complete", true); // 完整回傳，不截掉scheme
				try {
					// 開啟 QRDroid App 的掃描功能，等待 call back onActivityResult()
					startActivityForResult(iScaner, 0);
				} catch (ActivityNotFoundException ex) {
					// 若沒安裝 QRDroid，則開啟 Google Play商店，並顯示 QRDroid App
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("market://details?id=la.droid.qr"));
					startActivity(intent);
				}
			}
		});

		/* tab4 */
		output = (TextView) findViewById(R.id.output);
		checkBox_service = (CheckBox) findViewById(R.id.checkBox_service);
		checkBox_service.setChecked(getFromSP("checkBox1")); // checkBox儲存設定

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
							saveInSp("checkBox1", isChecked); // 執行定位並儲存設定

						} else {
							stop_Click();
							saveInSp("checkBox1", isChecked);// 不執行定位並儲存設定
						}
					}

				});
		// checkBox_service.performClick(); //自動按checkBox

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
		// 若線上資料為陣列，則使用JSONArray
		JSONArray jsonArray = null;
		// 若線上資料為單筆資料，則使用JSONObject
		// JSONObject jsonObj = null;
		// 透過HTTP連線取得回應
		try {
			HttpClient httpclient = new DefaultHttpClient(); // for port 80
			HttpGet httppost = new HttpGet(url); // 要用Get，用Post會出現
													// java.lang.string cannot
													// be converted to jsonarray
			HttpResponse response = httpclient.execute(httppost);
			Log.i("response:", response + ""); // 沒有值會catch錯誤，加入前面StrictMode就可以
			HttpEntity entity = response.getEntity();
			Log.i("entity:", entity + "");
			is = entity.getContent();
			Log.i("is:", is + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 讀取回應
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf8"), 9999999); // 99999為傳流大小，若資料很大，可自行調整
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// 逐行取得資料
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.i("result:", result + ""); // LogCat會印出json ex:[{"code":"1"}]
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 轉換文字為JSONArray
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
				.setTitle("更新提示").setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("最新優惠出來啦，快來下載更新")
				.setPositiveButton("下載", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // 關閉對話框
						downLoadApk();
					}

				}).show();

	}

	protected void downLoadApk() {
		final ProgressDialog pd; // 進度條對話框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下載更新");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(downLoadApkUrl, pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // 結束進度條對話框
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
		/* 如果相等的話表示當前的SDcard掛載在手機上並且是可用的 */
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			pd.setMax(conn.getContentLength()); // 獲取副本文件大小
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
				pd.setProgress(total); // 獲取當前下載量
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	/* 安裝APK */
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW); // 執行動作
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive"); // 執行的數據類型
		startActivity(intent);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case DOWN_ERROR:
				// 下載APK失敗
				Toast.makeText(getApplicationContext(), "下載新版本失敗", 1).show();
				break;
			case CHECK_UPDATE:
				// 檢查更新

				if (serverVersion == 0) {
					serverVersion = newServerVersion;
				}

				if (serverVersion != newServerVersion) {
					Log.i("serverVersion:", serverVersion + ""); // 顯示現在版本
					Log.i("newServerVersion:", newServerVersion + ""); // 顯示最新版本
					showUpdateDialog(); // 執行更新
				}
				break;
			}
		}
	};

	/* 建立桌面捷徑 */
	private void addShortcut() {
		Intent shortcutIntent = new Intent(getApplicationContext(),
				SplashScreen.class); // 啟動捷徑入口，一般用MainActivity，有使用其他入口則填入相對名稱，ex:有使用SplashScreen
		shortcutIntent.setAction(Intent.ACTION_MAIN);
		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // shortcutIntent送入
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name)); // 捷徑app名稱
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(
						getApplicationContext(),// 捷徑app圖
						R.drawable.ic_launcher));
		addIntent.putExtra("duplicate", false); // 指創建一次
		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // 安裝
		getApplicationContext().sendBroadcast(addIntent); // 送出廣播
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
			// longitude)).title("現在位置"));

		} catch (NullPointerException e) {
			Log.i("myLocation", "NullPointException");
		}

		/* GPS開啟跳出確認視窗 */
		try {
			if (!locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Log.i("GPSOpen:",
						locationManager
								.isProviderEnabled(LocationManager.GPS_PROVIDER)
								+ "");
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("GPS設定")
						.setMessage("您尚未啟動GPS，要啟動嗎?")
						.setCancelable(false)
						.setNegativeButton("啟動",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										startActivity(new Intent(
												Settings.ACTION_LOCATION_SOURCE_SETTINGS));
									}
								})
						.setPositiveButton("不啟動",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(MainActivity.this,
												"不啟動GPS將使用網路定位",
												Toast.LENGTH_SHORT).show();
									}
								}).show();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 點選marker顯示 */
	@Override
	public boolean onMarkerClick(Marker marker) {
		Intent intent = new Intent(this, ShowMarkerInfo.class);
		intent.putExtra("MarkTitle", marker.getTitle());
		startActivity(intent);
		return false;
	}

	// 第一個下拉類別的監看式
	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// 讀取第一個下拉選單是選擇第幾個
			int pos = sp1.getSelectedItemPosition();
			// 重新產生新的Adapter，用的是二維陣列type2[pos]
			adapter2 = new ArrayAdapter<String>(context,
					android.R.layout.simple_spinner_item, type2[pos]);
			// 載入第二個下拉選單Spinner
			sp2.setAdapter(adapter2);

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	// 第二個下拉類別的監看式
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
			double dLat = coordinate[iSelect1][iSelect2][1]; // 南北緯
			double dLon = coordinate[iSelect1][iSelect2][2]; // 東西經
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
			// 掃描結果存放在 key 為 la.droid.qr.result 的值中
			String result = data.getExtras().getString("la.droid.qr.result");
			String setResult = "manlen";
			if (setResult.equals(result)) {
				String messsenger = "歡迎使用本公司優惠方案";
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
				info2.setText("您已經使用"
						+ Integer.valueOf(scannerNumber).toString() + "次優惠方案");
				info.setTextSize(30);
				info.setText(messsenger); // 將結果顯示在 TextVeiw 中
			} else {
				String messsenger = "抱歉，您掃描的非本公司優惠";
				info.setTextSize(30);
				info.setText(messsenger); // 將結果顯示在 TextVeiw 中
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
		output.setText("服務啟動中");
	}

	public void stop_Click() {
		Intent intent = new Intent(this, GPSService.class);
		stopService(intent);
		output.setText("服務停止中");
	}

	/* 儲存設定 */
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

		// 設定tab標籤的字體大小
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

	/* 離開程式 */
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			closeAll();
			return true;
		}
		return super.onKeyDown(keycode, event);
	}

	public void closeAll() {
		new AlertDialog.Builder(MainActivity.this).setTitle("確定離開本程式？")
				.setMessage("按Home鍵可背景執行")
				.setNegativeButton("離開", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setPositiveButton("繼續", new DialogInterface.OnClickListener() {
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
