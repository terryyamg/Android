package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	private Button close, bc1, bc2;
	private Button cancel[];
	private TextView cd1, cd2, price11, price12;
	private int pr1[], id;
	private String store, userTel, myTel, storeName, s1[], tableData[][];
	public ProgressDialog dialog = null;
	List<ParseObject> searchObject;
	List<ParseObject> ob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_commodity);
		// tab1 ���o�ӫ~���
		Intent intent = getIntent();
		storeName = intent.getStringExtra("storeName"); // ���o�ө��W��
		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // ����table
			queryCommodity.whereEqualTo("store", storeName); // ����
			queryCommodity.orderByAscending("index");// �Ƨ�
			searchObject = queryCommodity.find();// �j�M����
			int sizeob = searchObject.size(); // �X�����
			s1 = new String[sizeob]; // �ӫ~�W�ٰ}�C
			pr1 = new int[sizeob]; // �ӫ~����}�C
			int i = 0;
			for (ParseObject search : searchObject) {
				// ���o���
				s1[i] = (String) search.get("commodity");
				pr1[i] = (int) search.getInt("price");
				i++;
			}

			// tab2 �������X
			SharedPreferences preferences = getApplicationContext()
					.getSharedPreferences("Android",
							android.content.Context.MODE_PRIVATE);

			myTel = preferences.getString("myTel", userTel);
		} catch (Exception e) {
			myTel = "0"; // �줣�������X
		}
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
		close = (Button) findViewById(R.id.close);
		bc1 = (Button) findViewById(R.id.bc1);
		bc2 = (Button) findViewById(R.id.bc2);

		cd1 = (TextView) findViewById(R.id.cd1);
		cd2 = (TextView) findViewById(R.id.cd2);

		price11 = (TextView) findViewById(R.id.price11);
		price12 = (TextView) findViewById(R.id.price12);
		// �ӫ~����
		cd1.setText(s1[0] + "\n�|�󦡤�򥧪a��:�w��3980���S��890�� \n");
		cd2.setText(s1[1] + "\nī�G�P�T�󦡤�򥧪a�˩w��2280���S��1690�� \n");
		// �ӫ~����
		price11.setText("NT." + pr1[0]);
		price12.setText("NT." + pr1[1]);
		// �Ĥ@��ӫ~

		bc1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				progress();
				buy(s1[0]);
			}
		});
		bc2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				progress();
				buy(s1[1]);
			}
		});

		close.setOnClickListener(back); // ��^

		/* tab2 */

		init();

		setupViewComponent();
	}

	/* tab1 */
	// �Ĥ@��
	void buy(String name) {
		Intent intent = new Intent(this, BuyConfirm.class);
		if (name.equals(s1[0])) {
			intent.putExtra("commodityName", s1[0]);
			intent.putExtra("price", pr1[0]);
		} else if (name.equals(s1[1])) {
			intent.putExtra("commodityName", s1[1]);
			intent.putExtra("price", pr1[1]);
		}
		intent.putExtra("store", store);
		startActivity(intent);
	}

	// ��^
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

	/* tab2 */
	public void init() {

		TableLayout t1 = (TableLayout) findViewById(R.id.tablay2);
		cancel = new Button[tableData.length];
		for (int i = 0; i < tableData.length; i++) { // �C

			TableRow row = new TableRow(this);
			for (int j = 0; j < tableData[i].length - 1; j++) { // ��
				if (i == 0) {
					TextView tv = new TextView(this); // �]�w�Ĥ@�Ƥ�r
					tv.setTextSize(20);
					tv.setTextColor(Color.BLACK);
					tv.setText(tableData[i][j] + " ");
					row.addView(tv, j);
				} else {
					if (j < tableData[i].length - 2) { // �]�w ��L�j�M�쪺��r
						TextView tv = new TextView(this);
						tv.setTextSize(20);
						tv.setTextColor(Color.BLACK);
						tv.setText(tableData[i][j] + " ");
						row.addView(tv, j);
					} else if (j == tableData[i].length - 2) { // �]�w���s

						cancel[i] = new Button(this);
						cancel[i].setTextColor(Color.WHITE);
						cancel[i].setBackgroundResource(R.drawable.btn_black);
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
					.setMessage("�R����" + id + "����")
					.setNegativeButton("�R��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									ParseObject.createWithoutData("BuyerInfo",
											tableData[id][7])
											.deleteEventually(); // Parse���O
																	// �R�����w�� row
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

	void progress() {
		dialog = ProgressDialog.show(ListCommodity.this, "Ū����", "�еy��...", true);
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

	private void setupViewComponent() {
		/* tabHost */
		// �q�귽���OR�����o��������
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("�ӫ~�C��",
				getResources().getDrawable(R.drawable.commoditylist));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("�ڪ��M��", getResources().getDrawable(R.drawable.mylist));
		spec.setContent(R.id.tab2);
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
	}
}
