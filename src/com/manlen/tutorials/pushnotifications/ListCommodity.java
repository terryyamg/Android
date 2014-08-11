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
		// tab1 取得商品資料
		Intent intent = getIntent();
		storeName = intent.getStringExtra("storeName"); // 取得商店名稱
		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // 哪個table
			queryCommodity.whereEqualTo("store", storeName); // 條件
			queryCommodity.orderByAscending("index");// 排序
			searchObject = queryCommodity.find();// 搜尋物件
			int sizeob = searchObject.size(); // 幾筆資料
			s1 = new String[sizeob]; // 商品名稱陣列
			pr1 = new int[sizeob]; // 商品價格陣列
			int i = 0;
			for (ParseObject search : searchObject) {
				// 取得資料
				s1[i] = (String) search.get("commodity");
				pr1[i] = (int) search.getInt("price");
				i++;
			}

			// tab2 抓手機號碼
			SharedPreferences preferences = getApplicationContext()
					.getSharedPreferences("Android",
							android.content.Context.MODE_PRIVATE);

			myTel = preferences.getString("myTel", userTel);
		} catch (Exception e) {
			myTel = "0"; // 抓不到手機號碼
		}
		/* tab2 */
		// 搜尋BuyerInfo 使用者的所有購買資訊
		try {
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"BuyerInfo");
			query.whereEqualTo("userTel", myTel);
			query.orderByDescending("arrivalDate");
			ob = query.find();
			int size = ob.size(); // 幾筆資料

			String[] store = new String[size];
			String[] commodityName = new String[size];
			String[] arrivalDate = new String[size];
			String[] arrivalTime = new String[size];
			int[] numberIndex = new int[size];
			int[] totalPrice = new int[size];
			String[] objectId = new String[size];
			// 表格第一列文字
			tableData = new String[size + 1][8];
			tableData[0][0] = "商店";
			tableData[0][1] = "商品";
			tableData[0][2] = "到貨日期";
			tableData[0][3] = "到貨時段";
			tableData[0][4] = "數量";
			tableData[0][5] = "總價";
			tableData[0][6] = "取消";
			tableData[0][7] = "ID";
			int i = 0;
			for (ParseObject search : ob) {
				// 取得資料
				store[i] = (String) search.get("store");
				commodityName[i] = (String) search.get("commodityName");
				arrivalDate[i] = (String) search.get("arrivalDate");
				arrivalTime[i] = (String) search.get("arrivalTime");
				numberIndex[i] = (int) search.getInt("numberIndex");
				totalPrice[i] = (int) search.getInt("totalPrice");
				objectId[i] = search.getObjectId();
				// 放入tableData字串
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
		// 商品說明
		cd1.setText(s1[0] + "\n四件式比基尼泳裝:定價3980元特價890元 \n");
		cd2.setText(s1[1] + "\n蘋果牌三件式比基尼泳裝定價2280元特價1690元 \n");
		// 商品價格
		price11.setText("NT." + pr1[0]);
		price12.setText("NT." + pr1[1]);
		// 第一件商品

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

		close.setOnClickListener(back); // 返回

		/* tab2 */

		init();

		setupViewComponent();
	}

	/* tab1 */
	// 第一件
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

	// 返回
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

	/* tab2 */
	public void init() {

		TableLayout t1 = (TableLayout) findViewById(R.id.tablay2);
		cancel = new Button[tableData.length];
		for (int i = 0; i < tableData.length; i++) { // 列

			TableRow row = new TableRow(this);
			for (int j = 0; j < tableData[i].length - 1; j++) { // 行
				if (i == 0) {
					TextView tv = new TextView(this); // 設定第一排文字
					tv.setTextSize(20);
					tv.setTextColor(Color.BLACK);
					tv.setText(tableData[i][j] + " ");
					row.addView(tv, j);
				} else {
					if (j < tableData[i].length - 2) { // 設定 其他搜尋到的文字
						TextView tv = new TextView(this);
						tv.setTextSize(20);
						tv.setTextColor(Color.BLACK);
						tv.setText(tableData[i][j] + " ");
						row.addView(tv, j);
					} else if (j == tableData[i].length - 2) { // 設定按鈕

						cancel[i] = new Button(this);
						cancel[i].setTextColor(Color.WHITE);
						cancel[i].setBackgroundResource(R.drawable.btn_black);
						cancel[i].setText("取消");
						cancel[i].setId(i);
						cancel[i].setOnClickListener(cc); // 取消訂單
						row.addView(cancel[i], j);
					}
				}

			}
			t1.addView(row);
		}

	}

	// 取消訂單
	private OnClickListener cc = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			// 跳出確認視窗
			new AlertDialog.Builder(ListCommodity.this)
					.setTitle("確定刪除?")
					.setMessage("刪除第" + id + "比資料")
					.setNegativeButton("刪除",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									ParseObject.createWithoutData("BuyerInfo",
											tableData[id][7])
											.deleteEventually(); // Parse指令
																	// 刪除指定的 row
								}
							})
					.setPositiveButton("不刪除",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();

		}
	};

	void progress() {
		dialog = ProgressDialog.show(ListCommodity.this, "讀取中", "請稍後...", true);
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
		// 從資源類別R中取得介面元件
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("商品列表",
				getResources().getDrawable(R.drawable.commoditylist));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("我的清單", getResources().getDrawable(R.drawable.mylist));
		spec.setContent(R.id.tab2);
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
	}
}
