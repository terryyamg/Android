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
	int spinnerNumber = 0; // �즸�imap���ϥΤU�Կ��

	/* tab2 */
	private TextView info, info2, info3;
	private Button scanner, scanner2;
	private int scannerError = 0;
	List<ParseObject> searchSc;
	String setResult[] = { "99��a", "�֨���" }; // QRcode���y�S���ө����W
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

		/* �r�� */
		fontch = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/wt001.ttf");

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

		info.setTypeface(fontch); // �r��
		info2.setTypeface(fontch);
		info3.setTypeface(fontch);
		scanner.setTypeface(fontch);
		scanner2.setTypeface(fontch);
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // ���X�ۤv��id

		scanner2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				try {
					ParseQuery<ParseObject> query = ParseQuery
							.getQuery("Scanner");
					query.whereEqualTo("installID", objectId);
					searchSc = query.find();// �j�M����
					for (ParseObject search : searchSc) {
						// ���o���
						int scannerNumberInfo0 = (int) search
								.getInt("scannerNumber" + 0);
						int scannerNumberInfo1 = (int) search
								.getInt("scannerNumber" + 1);
						int sumOfUse = scannerNumberInfo0 + scannerNumberInfo1;
						
						info2.setText("�z�`�@�w�g�ϥ�"
								+ Integer.valueOf(sumOfUse).toString()
								+ "���u�f��� \n");

						String scannerNextTime0 = (String) search
								.getString("scannerTime" + 0);
						String scannerNextTime1 = (String) search
								.getString("scannerTime" + 1);
						info3.setText("�z�U���i�H�ϥ�" + setResult[0] + "�u�f���ɶ���"
								+ scannerNextTime0 + "\n" + "�z�U���i�H�ϥ�"
								+ setResult[1] + "�u�f���ɶ���" + scannerNextTime1);
					}
				} catch (ParseException e) {
				}

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

		/* tab3 */

		// �C�X�Ҧ��ӫ~
		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // ����table
			queryCommodity.orderByAscending("picNumber");
			Object = queryCommodity.find();// �j�M����
			int sizeob = Object.size(); // �X�����
			sn = new String[sizeob]; // �ӫ~�W�ٰ}�C
			opr = new int[sizeob]; // ����}�C
			pr = new int[sizeob]; // ���ʻ��}�C
			picNumber = new int[sizeob]; // �ĴX�i��
			tableCommodity = new String[sizeob + 1][4];

			int i = 0;
			for (ParseObject search : Object) {
				// ���o���
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
		// �ƪ�
		TableLayout t1 = (TableLayout) view.findViewById(R.id.tableSet);
		
		imgButton = new ImageButton[tableCommodity.length];
		// �ƪ�
		for (int i = 0; i < (tableCommodity.length - 1) * 2; i++) { // �C
			TableRow row = new TableRow(getActivity());

			// �Ĥ@�C �Ϥ�
			if (i % 2 == 0) {
				imgButton[i / 2] = new ImageButton(getActivity());
				imgButton[i / 2].setImageResource(R.drawable.storem
						+ picNumber[i / 2]);
				imgButton[i / 2].setBackgroundDrawable(null);
				imgButton[i / 2].setId(i / 2);
				imgButton[i / 2].setOnClickListener(imgButtonListen);
				row.addView(imgButton[i / 2], 0);
				// �ĤT�C �ӫ~�W�� ����
				TextView tv2 = new TextView(getActivity());
				tv2.setTextSize(15);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.BLACK);

				String namel = tableCommodity[i / 2][0];
				String oprl = tableCommodity[i / 2][1];
				String prl = tableCommodity[i / 2][2];

				String ss = namel + "\n" + "���:"+ "\n" +"NT$" + oprl + "\n" + "���ʻ�:"+ "\n" +"NT$"
						+ prl;

				namel.length();// ���~�W����
				oprl.length(); // ����Ʀr����
				prl.length();// ���ʼƦr����
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), namel.length() + 8,
						namel.length() + 8 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �R���u
				msp.setSpan(new RelativeSizeSpan(2.0f), namel.length() + 17
						+ oprl.length(), namel.length() + 17 + oprl.length()
						+ prl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �⭿�j�p
				msp.setSpan(
						new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
						namel.length() + 17 + oprl.length(), namel.length()
								+ 17 + oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �צr��
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

		if (firstKey == 0) { // �S�����͹L���˽X
			int randNumber = (int) (10 * Math.random())
					+ (int) (100 * Math.random())
					+ (int) (1000 * Math.random())
					+ (int) (10000 * Math.random())
					+ (int) (100000 * Math.random())
					+ (int) (1000000 * Math.random());// ���X���˽X
			myRecommendNumber = Integer.toString(randNumber);

			ParseObject recommendList = new ParseObject("RecommendList"); // �إ�recommendList
																			// table
			recommendList.put("installID", objectId); // ��JinstallID
			recommendList.put("myRecommendNumber", myRecommendNumber); // ��JmyRecommendNumber
			recommendList.saveInBackground(); // �s�JrecommendList
												// table
			ParseInstallation.getCurrentInstallation().put("firstKey",
					firstKey + 1);
			ParseInstallation.getCurrentInstallation().saveInBackground();

		}
		// �����ڪ����˽X
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

		// ��ܧڱ��˦��\����
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
				lookMyRecommendTV.setText("\n�ڱ��˦��\����: " + recommendFrequency);
			}
		});

		// ����app
		shareButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				shareDialog();
			}
		});
		// ��J���˽X keyRecommendNumber

		keyRecommend.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				keyRecommendNumber();
			}
		});
		// �ݦۤv�����˽X
		lookMyRecommend.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				lookMyRecommendTV.setText("\n�ڪ����˽X: " + myRecommendNumber);
			}
		});

		// checkBox_service.performClick(); //�۰ʫ�checkBox

		/* �P�_�����O�_�}�� */
		ConnectivityManager conManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);// �����o��service

		NetworkInfo networInfo = conManager.getActiveNetworkInfo(); // �b���o������T

		if (networInfo == null || !networInfo.isAvailable()) { // �P�_�O�_������
			new AlertDialog.Builder(getActivity())
					.setTitle("�����]�w")
					.setMessage("�z�|���}�Һ����s�u")
					.setPositiveButton("�Х��Ұʺ����s�u",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									getActivity().finish();
								}
							}).show();
		}

		/* tabHost */
		// �q�귽���OR�����o��������
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

		// �]�wtab���Ҫ��r��j�p
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
			// ���y���G�s��b key �� la.droid.qr.result ���Ȥ�
			String result = data.getExtras().getString("la.droid.qr.result");
			String setResult[] = { "99��a", "donutes" };
			int scannerNumber[] = new int[setResult.length];
			String scannerNextTime[] = new String[setResult.length];
			for (int i = 0; i < setResult.length; i++) {
				if (setResult[i].equals(result)) {
					String messsenger = "�w��ϥ� \n" + "�����q�u�f���";

					try {
						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("Scanner");
						query.whereEqualTo("installID", objectId);
						searchSc = query.find();// �j�M����
						for (ParseObject search : searchSc) {
							// ���o���
							scannerNumber[i] = (int) search
									.getInt("scannerNumber" + i); // ���X��i�����y����
							scannerNextTime[i] = (String) search
									.getString("scannerTime" + i); // ���X��i���U���i�H���y�ɶ�
						}
					} catch (ParseException e) {
						scannerNumber[i] = 0;
						scannerNextTime[i] = null;
						Log.i("eeee", e + "");
					}

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss"); // ���o�p��
					String scannerTime = sdf.format(new java.util.Date()); // ���o�{�b�ɶ�
					int waitTime = 1; // �N�o�ɶ�

					Date dt = null; // �{�b�ɶ�date��l��

					try {

						dt = sdf.parse(scannerTime);// �{�b�ɶ�

					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dt);
					calendar.add(Calendar.HOUR, waitTime);// +�N�o�ɶ�
					Date tdt = calendar.getTime();// ���o�[��L�᪺Date
					String sumTime = sdf.format(tdt);// �̷ӳ]�w�榡���o�r��

					if (scannerNextTime[i] == null) { // �즸���y�AscannerNextTime���ŭ�
						ParseObject scannerTable = new ParseObject("Scanner"); // �إ�Scanner
																				// table
						Log.i("objectId", objectId + "");
						scannerTable.put("installID", objectId); // ��JinstallID
						scannerNumber[i]++;
						scannerTable.put("scannerNumber" + i, scannerNumber[i]); // ��JscannerNumber
						scannerTable.put("scannerTime" + i, sumTime); // ��JscannerTime
						scannerTable.saveInBackground(); // �x�s

						info2.setText("�z�w�g�ϥ�"
								+ Integer.valueOf(scannerNumber[i]).toString()
								+ "��" + setResult[i] + "�u�f���");

						info3.setText("�z�U���i�H�ϥ�" + setResult[i] + "�u�f���ɶ���"
								+ sumTime);
						info.setTextSize(20);
						info.setText(messsenger); // �N���G��ܦb TextVeiw ��
					} else { // scannerNextTime����
						Date snt = null;// ���X�ɶ�
						try {
							snt = sdf.parse(scannerNextTime[i]);
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if (snt.before(dt)) { // ����X�ӥi���y�ɶ�(�L�h)snt before
												// �{�b�ɶ�dt�A�i�O��
							scannerNumber[i]++;
							try {
								ParseQuery<ParseObject> query = ParseQuery
										.getQuery("Scanner");
								query.whereEqualTo("installID", objectId);
								searchSc = query.find();// �j�M����
								for (ParseObject search : searchSc) {
									// ���o���
									search.put("scannerNumber" + i,
											scannerNumber[i]); // ��JscannerNumber
									search.put("scannerTime" + i, sumTime); // ��JscannerTime
									search.saveInBackground(); // �x�s
								}
							} catch (ParseException e) {
								Log.i("eeee", e + "");
							}

							info2.setText("�z�w�g�ϥ�"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "��" + setResult[i]
									+ "�u�f���");

							info3.setText("�z�U���i�H�ϥ�" + setResult[i] + "�u�f���ɶ���"
									+ sumTime);
							info.setTextSize(20);
							info.setText(messsenger); // �N���G��ܦb TextVeiw ��
						} else { // �N�o�ɶ��|�������A���O��
							info2.setText("�z�w�g�ϥ�"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "��" + setResult[i]
									+ "�u�f���");

							info3.setText("�z�U���i�H�ϥ�" + setResult[i] + "�u�f���ɶ���"
									+ scannerNextTime[i]);

							info.setTextSize(20);
							info.setText(messsenger); // �N���G��ܦb TextVeiw ��
						}
					}
					break;
				} else {
					scannerError++;
					if (scannerError <= 3) {
						String messsenger = "��p�A�S�����˦��\; \n" + "�z�i�H�A�դ@��";
						info.setTextSize(20);
						info.setText(messsenger); // �N���G��ܦb TextVeiw ��
					} else {
						String messsenger = "�z�i�H�бЪ����H���A \n" + "�λP�ڭ��pô";
						info.setTextSize(20);
						info.setText(messsenger); // �N���G��ܦb TextVeiw ��
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
		dialog = ProgressDialog.show(getActivity(), "Ū����", "�еy��...", true);
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

	// ����app
	void shareDialog() {

		String shareText = "���p�q�u�f�A���z������|�ֳ��ٿ�  "
				+ "\n https://play.google.com/store/apps/details?id=com.manlen.tutorials.pushnotifications"
				+ "\n��J�ڪ����˽X" + myRecommendNumber + "\n �N�i��o�u�f";
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
		startActivity(Intent.createChooser(shareIntent, "����"));
	}

	void keyRecommendNumber() {
		Intent intent = new Intent(getActivity(), KeyInRecommendNumber.class);
		startActivity(intent);
	}

	/* ���}�{�� */
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			closeAll();
			return true;
		}
		return super.getActivity().onKeyDown(keycode, event);
	}

	public void closeAll() {
		new AlertDialog.Builder(getActivity()).setTitle("�T�w���}���{���H")
				.setMessage("��������i�I������")
				.setNegativeButton("���}", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				})
				.setPositiveButton("�~��", new DialogInterface.OnClickListener() {
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
