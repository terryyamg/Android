package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ListCommodity extends FragmentActivity {
	private Button buyButton, recommend;
	private Button cancel[];
	private ImageView img;
	private int opr, pr, pn, id;
	private String store, userTel, myTel, sn, si, storeClass, tableData[][];
	public ProgressDialog dialog = null;
	List<ParseObject> searchObject;
	List<ParseObject> ob;
	Typeface fontch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_commodity);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		try {
			// tab1 ���o�ӫ~���
			Intent intent = getIntent();
			sn = intent.getStringExtra("storeName"); // ���o�ӫ~�W��
			opr = intent.getIntExtra("orientPrice", 0); // ���o��l����
			pr = intent.getIntExtra("price", 0); // ���o����
			pn = intent.getIntExtra("picNumber", 0); // ���o�Ϥ����X
			// tab2 �������X
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // ����table
			queryCommodity.whereEqualTo("commodity", sn); // ����
			searchObject = queryCommodity.find();// �j�M����
			for (ParseObject search : searchObject) {
				// ���o���
				si = (String) search.get("introduction"); // �ӫ~����
				store = (String) search.get("store"); // ���W
				storeClass = (String) search.get("storeClass"); // ���a���O
			}

			SharedPreferences preferences = getApplicationContext()
					.getSharedPreferences("Android",
							android.content.Context.MODE_PRIVATE);

			myTel = preferences.getString("myTel", userTel);
			if (myTel == null) {
				myTel = "0"; // �줣�������X
			}
		} catch (Exception e) {
			myTel = "0"; // �줣�������X
		}

		// �ƪ�
		setTable();

		/* tab2 */
		// �j�MBuyerInfo �ϥΪ̪��Ҧ��ʶR��T
		try {

			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"BuyerInfo");
			query.whereEqualTo("userTel", myTel);
			query.orderByDescending("arrivalDate");
			ob = query.find();

			int size = ob.size(); // �X�����

			String[] store = new String[size];
			String[] commodityName = new String[size];
			String[] arrivalDate = new String[size];
			String[] arrivalTime = new String[size];
			int[] numberIndex = new int[size];
			int[] totalPrice = new int[size];
			String[] objectId = new String[size];
			// ���Ĥ@�C��r
			tableData = new String[size + 1][8];
			tableData[0][0] = "�ө�";
			tableData[0][1] = "�ӫ~";
			tableData[0][2] = "��f���";
			tableData[0][3] = "��f�ɬq";
			tableData[0][4] = "�ƶq";
			tableData[0][5] = "�`��";
			tableData[0][6] = "����";
			tableData[0][7] = "ID";
			int i = 0;
			for (ParseObject search : ob) {
				// ���o���
				store[i] = (String) search.get("store");
				commodityName[i] = (String) search.get("commodityName");
				arrivalDate[i] = (String) search.get("arrivalDate");
				arrivalTime[i] = (String) search.get("arrivalTime");
				numberIndex[i] = (int) search.getInt("numberIndex");
				totalPrice[i] = (int) search.getInt("totalPrice");
				objectId[i] = search.getObjectId();
				// ��JtableData�r��
				tableData[i + 1][0] = store[i];
				tableData[i + 1][1] = commodityName[i];
				tableData[i + 1][2] = arrivalDate[i];
				tableData[i + 1][3] = arrivalTime[i];
				tableData[i + 1][4] = Integer.toString(numberIndex[i]);
				tableData[i + 1][5] = Integer.toString(totalPrice[i]);
				tableData[i + 1][6] = "";
				tableData[i + 1][7] = objectId[i];

				i++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/* tab1 */

		/* tab2 */

		init();

		setupViewComponent();
	}

	/* tab1 */

	void setTable() {
		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);

		// �ƪ�
		buyButton = new Button(this);
		recommend = new Button(this);
		for (int i = 0; i < 6; i++) { // �C
			TableRow row = new TableRow(this);

			// �Ĥ@�C �Ϥ�
			switch (i) {
			case 0: // �Ĥ@�C �ӫ~�Ϥ�
				img = new ImageView(this);
				img.setImageResource(R.drawable.store + pn);				
				row.addView(img, 0);
				break;
			case 1: // �ĤG�C �ӫ~�W��
				TextView tv2 = new TextView(this);
				tv2.setTextSize(20);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.BLACK);
				tv2.setText(store + "\n" + sn + " ");
				row.addView(tv2, 0);
				break;
			case 2:
				TextView tv3 = new TextView(this);
				tv3.setTextSize(20);
				tv3.setTypeface(fontch);
				tv3.setTextColor(Color.BLACK);
				String showSi = "";
				String[] siSplit = si.split(":");
				for (int k = 0; k < siSplit.length; k++) {
					showSi = showSi + siSplit[k] + "\n";
				}
				tv3.setText(showSi);
				row.addView(tv3, 0);
				break;
			case 3:
				TextView tv4 = new TextView(this);
				tv4.setTextSize(20);
				tv4.setTypeface(fontch);
				tv4.setTextColor(Color.BLACK);
				String oprl = Integer.toString(opr);
				String prl = Integer.toString(pr);
				String ss = "���:$" + oprl + "  ���ʻ�:$" + prl;
				oprl.length(); // ����Ʀr����
				prl.length();// ���ʼƦr����
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), 4, 4 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �R���u
				msp.setSpan(new RelativeSizeSpan(2.0f), 11 + oprl.length(), 11
						+ oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �⭿�j�p
				msp.setSpan(
						new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
						11 + oprl.length(), 11 + oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //�צr��
				tv4.setText(msp);
				row.addView(tv4, 0);
				break;
			case 4:
				buyButton = new Button(this);
				buyButton.setTypeface(fontch);
				buyButton.setTextColor(Color.BLACK);
				buyButton
						.setBackgroundResource(R.drawable.btn_lightblue_glossy);
				buyButton.setText("�ʶR");
				buyButton.setId(i);
				buyButton.setOnClickListener(bb); // �ʶR�ʧ@
				row.addView(buyButton, 0);
				break;
			case 5:
				recommend = new Button(this);
				recommend.setTypeface(fontch);
				recommend.setTextColor(Color.BLACK);
				recommend
						.setBackgroundResource(R.drawable.btn_lightblue_glossy);
				recommend.setText("����");
				recommend.setId(i);
				recommend.setOnClickListener(rr); // ���˰ʧ@
				row.addView(recommend, 0);
				break;

			}
			t1.addView(row);
		}

	}

	// �ʶR
	private OnClickListener bb = new OnClickListener() {
		public void onClick(View v) {
			progress();
			buy();
		}
	};

	void buy() {
		Intent intent = new Intent(this, BuyConfirm.class);
		intent.putExtra("commodityName", sn);
		intent.putExtra("price", pr);
		intent.putExtra("store", store);
		intent.putExtra("storeClass", storeClass);
		startActivity(intent);
	}

	private OnClickListener rr = new OnClickListener() {
		public void onClick(View v) {
			shareDialog();
		}
	};

	// ����app
	void shareDialog() {

		String shareText = "�n�K�y!" + store + "-" + sn + "�u��NT." + pr + "���˵��A ";
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		startActivity(Intent.createChooser(shareIntent, "����"));
	}

	void progress() {
		dialog = ProgressDialog.show(this, "Ū����", "�еy��...", true);
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

	/* tab2 */
	public void init() {

		TableLayout t1 = (TableLayout) findViewById(R.id.tablay2);
		cancel = new Button[tableData.length];
		for (int i = 0; i < tableData.length; i++) { // �C

			TableRow row = new TableRow(this);
			for (int j = 0; j < tableData[i].length - 1; j++) { // ��
				if (i == 0) {
					TextView tv = new TextView(this); // �]�w�Ĥ@�Ƥ�r
					tv.setTypeface(fontch);
					tv.setTextSize(20);
					tv.setTextColor(Color.BLACK);
					tv.setText(tableData[i][j] + " ");
					row.addView(tv, j);
				} else {
					if (j < tableData[i].length - 2) { // �]�w ��L�j�M�쪺��r
						TextView tv = new TextView(this);
						tv.setTypeface(fontch);
						tv.setTextSize(20);
						tv.setTextColor(Color.BLACK);
						tv.setText(tableData[i][j] + " ");
						row.addView(tv, j);
					} else if (j == tableData[i].length - 2) { // �]�w���s

						cancel[i] = new Button(this);
						cancel[i].setTypeface(fontch);
						cancel[i].setTextColor(Color.BLACK);
						cancel[i]
								.setBackgroundResource(R.drawable.btn_lightblue_glossy);
						cancel[i].setText("����");
						cancel[i].setId(i);
						cancel[i].setOnClickListener(cc); // �����q��
						row.addView(cancel[i], j);
					}
				}

			}
			t1.addView(row);
		}

	}

	// �����q��
	private OnClickListener cc = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			// ���X�T�{����
			new AlertDialog.Builder(ListCommodity.this)
					.setTitle("�T�w�R��?")
					.setMessage("�R����" + id + "�����")
					.setNegativeButton("�R��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									ParseObject.createWithoutData("BuyerInfo",
											tableData[id][7])
											.deleteEventually(); // Parse���O
																	// �R�����w�� row
									ref();
								}
							})
					.setPositiveButton("���R��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();

		}
	};

	void ref() {
		Intent intent = new Intent(this, ListCommodity.class);
		intent.putExtra("storeName", sn); // ���o�ӫ~�W��
		intent.putExtra("orientPrice", opr); // ���o��l����
		intent.putExtra("price", pr); // ���o����
		intent.putExtra("picNumber", pn); // ���o�Ϥ����X
		startActivity(intent);
	}

	private void setupViewComponent() {
		/* tabHost */
		// �q�귽���OR�����o��������
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("",
				getResources().getDrawable(R.drawable.commoditylist));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("", getResources().getDrawable(R.drawable.mylist));
		spec.setContent(R.id.tab2);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// �]�wtab���Ҫ��r��j�p
		TabWidget tabWidget = (TabWidget) tabHost
				.findViewById(android.R.id.tabs);
		View tabView = tabWidget.getChildTabViewAt(0);
		TextView tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(20);

		tabView = tabWidget.getChildTabViewAt(1);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(20);
	}
}
