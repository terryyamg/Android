package com.manlen.tutorials.pushnotifications;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Fragment {

	/* tab1 */
	private Button wheelWidget, openMap;
	int spinnerNumber = 0; // 初次進map不使用下拉選單

	/* tab2 */
	private TextView info, info2, info3;
	private Button scanner, scanner2;
	private int scannerError = 0;
	List<ParseObject> searchSc;
	String setResult[] = { "99度a", "少那之" }; // QRcode掃描特約商店店名
	/* tab3 */
	private String sn[], tableCommodity[][];
	private ImageButton imgButton[];
	public ProgressDialog dialog = null;
	private int opr[], pr[], id, picNumber[];
	List<ParseObject> Object;

	/* tab4 */
	private TextView lookMyRecommendTV;
	private Button shareButton, myRecommendButton, keyRecommend,
			lookMyRecommend;
	private int recommendFrequency, firstKey;
	private String objectId, myRecommendNumber;

	Typeface fontch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* 字型 */
		fontch = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/wt001.ttf");

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

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView(inflater, container);
	}

	private View initView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.activity_main, container, false);

		// Track app opens.
		ParseAnalytics.trackAppOpened(getActivity().getIntent());

		// Set up our UI member properties.

		/* tab1 */

		openMap = (Button) view.findViewById(R.id.openMap);
		wheelWidget = (Button) view.findViewById(R.id.wheelWidget);
		openMap.setTypeface(fontch);
		wheelWidget.setTypeface(fontch);

		openMap.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				goToGoogleMap();
			}
		});
		wheelWidget.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				wheelWidget();
			}
		});
		
		
		/* tab2 */
		info = (TextView) view.findViewById(R.id.info);
		info2 = (TextView) view.findViewById(R.id.info2);
		info3 = (TextView) view.findViewById(R.id.info3);
		scanner = (Button) view.findViewById(R.id.scanner);
		scanner2 = (Button) view.findViewById(R.id.scanner2);

		info.setTypeface(fontch); // 字型
		info2.setTypeface(fontch);
		info3.setTypeface(fontch);
		scanner.setTypeface(fontch);
		scanner2.setTypeface(fontch);
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // 取出自己的id

		scanner2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				try {
					ParseQuery<ParseObject> query = ParseQuery
							.getQuery("Scanner");
					query.whereEqualTo("installID", objectId);
					searchSc = query.find();// 搜尋物件
					for (ParseObject search : searchSc) {
						// 取得資料
						int scannerNumberInfo0 = (int) search
								.getInt("scannerNumber" + 0);
						int scannerNumberInfo1 = (int) search
								.getInt("scannerNumber" + 1);
						int sumOfUse = scannerNumberInfo0 + scannerNumberInfo1;
						
						info2.setText("您總共已經使用"
								+ Integer.valueOf(sumOfUse).toString()
								+ "次優惠方案 \n");

						String scannerNextTime0 = (String) search
								.getString("scannerTime" + 0);
						String scannerNextTime1 = (String) search
								.getString("scannerTime" + 1);
						info3.setText("您下次可以使用" + setResult[0] + "優惠的時間為"
								+ scannerNextTime0 + "\n" + "您下次可以使用"
								+ setResult[1] + "優惠的時間為" + scannerNextTime1);
					}
				} catch (ParseException e) {
				}

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

		/* tab3 */

		// 列出所有商品
		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // 哪個table
			queryCommodity.orderByAscending("picNumber");
			Object = queryCommodity.find();// 搜尋物件
			int sizeob = Object.size(); // 幾筆資料
			sn = new String[sizeob]; // 商品名稱陣列
			opr = new int[sizeob]; // 原價陣列
			pr = new int[sizeob]; // 團購價陣列
			picNumber = new int[sizeob]; // 第幾張圖
			tableCommodity = new String[sizeob + 1][4];

			int i = 0;
			for (ParseObject search : Object) {
				// 取得資料
				sn[i] = (String) search.get("commodity");
				opr[i] = (int) search.getInt("originalPrice");
				pr[i] = (int) search.getInt("price");
				picNumber[i] = (int) search.getInt("picNumber");

				tableCommodity[i][0] = sn[i];
				tableCommodity[i][1] = Integer.toString(opr[i]);
				tableCommodity[i][2] = Integer.toString(pr[i]);
				tableCommodity[i][3] = Integer.toString(picNumber[i]);

				i++;
			}

		} catch (Exception e) {

		}
		// 排版
		TableLayout t1 = (TableLayout) view.findViewById(R.id.tableSet);
		
		imgButton = new ImageButton[tableCommodity.length];
		// 排版
		for (int i = 0; i < (tableCommodity.length - 1) * 2; i++) { // 列
			TableRow row = new TableRow(getActivity());

			// 第一列 圖片
			if (i % 2 == 0) {
				imgButton[i / 2] = new ImageButton(getActivity());
				imgButton[i / 2].setImageResource(R.drawable.storem
						+ picNumber[i / 2]);
				imgButton[i / 2].setBackgroundDrawable(null);
				imgButton[i / 2].setId(i / 2);
				imgButton[i / 2].setOnClickListener(imgButtonListen);
				row.addView(imgButton[i / 2], 0);
				// 第三列 商品名稱 價格
				TextView tv2 = new TextView(getActivity());
				tv2.setTextSize(15);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.BLACK);

				String namel = tableCommodity[i / 2][0];
				String oprl = tableCommodity[i / 2][1];
				String prl = tableCommodity[i / 2][2];

				String ss = namel + "\n" + "原價:"+ "\n" +"NT$" + oprl + "\n" + "團購價:"+ "\n" +"NT$"
						+ prl;

				namel.length();// 產品名長度
				oprl.length(); // 原價數字長度
				prl.length();// 團購數字長度
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), namel.length() + 8,
						namel.length() + 8 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 刪除線
				msp.setSpan(new RelativeSizeSpan(2.0f), namel.length() + 17
						+ oprl.length(), namel.length() + 17 + oprl.length()
						+ prl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 兩倍大小
				msp.setSpan(
						new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
						namel.length() + 17 + oprl.length(), namel.length()
								+ 17 + oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 斜字體
				tv2.setText(msp);
				row.addView(tv2, 1);
			} else if (i % 2 == 1) {
				ImageView iv = new ImageView(getActivity());
				iv.setImageResource(R.drawable.dividers);
				TableRow.LayoutParams rowSpanLayout = new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.WRAP_CONTENT);
				rowSpanLayout.span = 2;
				row.addView(iv, rowSpanLayout);
			}
			t1.addView(row);
		}
		/* tab4 */

		shareButton = (Button) view.findViewById(R.id.shareButton);
		myRecommendButton = (Button) view.findViewById(R.id.myRecommendButton);
		keyRecommend = (Button) view.findViewById(R.id.keyRecommend);
		lookMyRecommend = (Button) view.findViewById(R.id.lookMyRecommend);
		lookMyRecommendTV = (TextView) view
				.findViewById(R.id.lookMyRecommendTV);

		shareButton.setTypeface(fontch);
		myRecommendButton.setTypeface(fontch);
		keyRecommend.setTypeface(fontch);
		lookMyRecommend.setTypeface(fontch);
		lookMyRecommendTV.setTypeface(fontch);
		lookMyRecommendTV.setTextSize(20);

		firstKey = ParseInstallation.getCurrentInstallation()
				.getInt("firstKey");

		if (firstKey == 0) { // 沒有產生過推薦碼
			int randNumber = (int) (10 * Math.random())
					+ (int) (100 * Math.random())
					+ (int) (1000 * Math.random())
					+ (int) (10000 * Math.random())
					+ (int) (100000 * Math.random())
					+ (int) (1000000 * Math.random());// 六碼推薦碼
			myRecommendNumber = Integer.toString(randNumber);

			ParseObject recommendList = new ParseObject("RecommendList"); // 建立recommendList
																			// table
			recommendList.put("installID", objectId); // 輸入installID
			recommendList.put("myRecommendNumber", myRecommendNumber); // 輸入myRecommendNumber
			recommendList.saveInBackground(); // 存入recommendList
												// table
			ParseInstallation.getCurrentInstallation().put("firstKey",
					firstKey + 1);
			ParseInstallation.getCurrentInstallation().saveInBackground();

		}
		// 取的我的推薦碼
		ParseQuery<ParseObject> queryMyRecommend = ParseQuery
				.getQuery("RecommendList");
		queryMyRecommend.whereEqualTo("installID", objectId);
		queryMyRecommend.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					myRecommendNumber = object.getString("myRecommendNumber");
				} else {

				}
			}
		});

		// 顯示我推薦成功次數
		myRecommendButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				ParseQuery<ParseObject> queryMyRecommend = ParseQuery
						.getQuery("RecommendList");
				queryMyRecommend.whereEqualTo("installID", objectId);
				queryMyRecommend.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							recommendFrequency= object.getInt("recommendFrequency");
						} else {

						}
					}
				});
				lookMyRecommendTV.setText("\n我推薦成功次數: " + recommendFrequency);
			}
		});

		// 分享app
		shareButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				shareDialog();
			}
		});
		// 輸入推薦碼 keyRecommendNumber

		keyRecommend.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				keyRecommendNumber();
			}
		});
		// 看自己的推薦碼
		lookMyRecommend.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				lookMyRecommendTV.setText("\n我的推薦碼: " + myRecommendNumber);
			}
		});

		// checkBox_service.performClick(); //自動按checkBox

		/* 判斷網路是否開啟 */
		ConnectivityManager conManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 先取得此service

		NetworkInfo networInfo = conManager.getActiveNetworkInfo(); // 在取得相關資訊

		if (networInfo == null || !networInfo.isAvailable()) { // 判斷是否有網路
			new AlertDialog.Builder(getActivity())
					.setTitle("網路設定")
					.setMessage("您尚未開啟網路連線")
					.setPositiveButton("請先啟動網路連線",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									getActivity().finish();
								}
							}).show();
		}

		/* tabHost */
		// 從資源類別R中取得介面元件
		TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab2));
		spec.setContent(R.id.tab1);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab3));
		spec.setContent(R.id.tab2);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab3");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab4));
		spec.setContent(R.id.tab3);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab4");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab5));
		spec.setContent(R.id.tab4);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// 設定tab標籤的字體大小
		TabWidget tabWidget = (TabWidget) tabHost
				.findViewById(android.R.id.tabs);
		View tabView = tabWidget.getChildTabViewAt(0);
		TextView tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);

		tabView = tabWidget.getChildTabViewAt(1);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);

		tabView = tabWidget.getChildTabViewAt(2);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);

		tabView = tabWidget.getChildTabViewAt(3);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);
		return view;
	}

	/* tab1 */
	void wheelWidget() {
		progress();
		Intent intent = new Intent(getActivity(), WheelWidget.class);
		startActivity(intent);
	}

	void goToGoogleMap() {
		progress();
		Intent intent = new Intent(getActivity(), GoToGoogleMap.class);
		startActivity(intent);
	}

	/* tab2 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (0 == requestCode && null != data && data.getExtras() != null) {
			// 掃描結果存放在 key 為 la.droid.qr.result 的值中
			String result = data.getExtras().getString("la.droid.qr.result");
			String setResult[] = { "99度a", "donutes" };
			int scannerNumber[] = new int[setResult.length];
			String scannerNextTime[] = new String[setResult.length];
			for (int i = 0; i < setResult.length; i++) {
				if (setResult[i].equals(result)) {
					String messsenger = "歡迎使用 \n" + "本公司優惠方案";

					try {
						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("Scanner");
						query.whereEqualTo("installID", objectId);
						searchSc = query.find();// 搜尋物件
						for (ParseObject search : searchSc) {
							// 取得資料
							scannerNumber[i] = (int) search
									.getInt("scannerNumber" + i); // 取出第i間掃描次數
							scannerNextTime[i] = (String) search
									.getString("scannerTime" + i); // 取出第i間下次可以掃描時間
						}
					} catch (ParseException e) {
						scannerNumber[i] = 0;
						scannerNextTime[i] = null;
						Log.i("eeee", e + "");
					}

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss"); // 取得小時
					String scannerTime = sdf.format(new java.util.Date()); // 取得現在時間
					int waitTime = 1; // 冷卻時間

					Date dt = null; // 現在時間date初始化

					try {

						dt = sdf.parse(scannerTime);// 現在時間

					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dt);
					calendar.add(Calendar.HOUR, waitTime);// +冷卻時間
					Date tdt = calendar.getTime();// 取得加減過後的Date
					String sumTime = sdf.format(tdt);// 依照設定格式取得字串

					if (scannerNextTime[i] == null) { // 初次掃描，scannerNextTime為空值
						ParseObject scannerTable = new ParseObject("Scanner"); // 建立Scanner
																				// table
						Log.i("objectId", objectId + "");
						scannerTable.put("installID", objectId); // 輸入installID
						scannerNumber[i]++;
						scannerTable.put("scannerNumber" + i, scannerNumber[i]); // 輸入scannerNumber
						scannerTable.put("scannerTime" + i, sumTime); // 輸入scannerTime
						scannerTable.saveInBackground(); // 儲存

						info2.setText("您已經使用"
								+ Integer.valueOf(scannerNumber[i]).toString()
								+ "次" + setResult[i] + "優惠方案");

						info3.setText("您下次可以使用" + setResult[i] + "優惠的時間為"
								+ sumTime);
						info.setTextSize(20);
						info.setText(messsenger); // 將結果顯示在 TextVeiw 中
					} else { // scannerNextTime有值
						Date snt = null;// 取出時間
						try {
							snt = sdf.parse(scannerNextTime[i]);
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if (snt.before(dt)) { // 抓取出來可掃描時間(過去)snt before
												// 現在時間dt，可記錄
							scannerNumber[i]++;
							try {
								ParseQuery<ParseObject> query = ParseQuery
										.getQuery("Scanner");
								query.whereEqualTo("installID", objectId);
								searchSc = query.find();// 搜尋物件
								for (ParseObject search : searchSc) {
									// 取得資料
									search.put("scannerNumber" + i,
											scannerNumber[i]); // 輸入scannerNumber
									search.put("scannerTime" + i, sumTime); // 輸入scannerTime
									search.saveInBackground(); // 儲存
								}
							} catch (ParseException e) {
								Log.i("eeee", e + "");
							}

							info2.setText("您已經使用"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "次" + setResult[i]
									+ "優惠方案");

							info3.setText("您下次可以使用" + setResult[i] + "優惠的時間為"
									+ sumTime);
							info.setTextSize(20);
							info.setText(messsenger); // 將結果顯示在 TextVeiw 中
						} else { // 冷卻時間尚未結束，不記錄
							info2.setText("您已經使用"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "次" + setResult[i]
									+ "優惠方案");

							info3.setText("您下次可以使用" + setResult[i] + "優惠的時間為"
									+ scannerNextTime[i]);

							info.setTextSize(20);
							info.setText(messsenger); // 將結果顯示在 TextVeiw 中
						}
					}
					break;
				} else {
					scannerError++;
					if (scannerError <= 3) {
						String messsenger = "抱歉，沒有掃瞄成功; \n" + "您可以再試一次";
						info.setTextSize(20);
						info.setText(messsenger); // 將結果顯示在 TextVeiw 中
					} else {
						String messsenger = "您可以請教門市人員， \n" + "或與我們聯繫";
						info.setTextSize(20);
						info.setText(messsenger); // 將結果顯示在 TextVeiw 中
					}

				}
			}

		}
	}

	/* tab3 */

	private OnClickListener imgButtonListen = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			goToListCommodity();

		}
	};

	void goToListCommodity() {
		progress();
		Intent intent = new Intent(getActivity(), ListCommodity.class);
		intent.putExtra("storeName", sn[id]);
		intent.putExtra("orientPrice", opr[id]);
		intent.putExtra("price", pr[id]);
		intent.putExtra("picNumber", picNumber[id]);

		startActivity(intent);
	}

	void progress() {
		dialog = ProgressDialog.show(getActivity(), "讀取中", "請稍後...", true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dialog.dismiss();
				}
			}
		}).start();
	}

	/* tab4 */

	// 分享app
	void shareDialog() {

		String shareText = "曼聯通優惠，讓您食衣住行育樂都省錢  "
				+ "\n https://play.google.com/store/apps/details?id=com.manlen.tutorials.pushnotifications"
				+ "\n輸入我的推薦碼" + myRecommendNumber + "\n 將可獲得優惠";
		// Uri imageUri = Uri.parse("android.resource://" + getPackageName() +
		// "/drawable/" + "android");
		// Log.i("imageUri:", imageUri + "");
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		// shareIntent.setType("image/png");
		// shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
		startActivity(Intent.createChooser(shareIntent, "分享"));
	}

	void keyRecommendNumber() {
		Intent intent = new Intent(getActivity(), KeyInRecommendNumber.class);
		startActivity(intent);
	}

	/* 離開程式 */
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			closeAll();
			return true;
		}
		return super.getActivity().onKeyDown(keycode, event);
	}

	public void closeAll() {
		new AlertDialog.Builder(getActivity()).setTitle("確定離開本程式？")
				.setMessage("按中間鍵可背景執行")
				.setNegativeButton("離開", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				})
				.setPositiveButton("繼續", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Kill myself
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public void onStart() {
		super.onStart();

		// Display the current values for this user, such as their age and
		// gender.
		// displayUserProfile();
		// refreshUserProfile();
	}

}
