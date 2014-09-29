package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CompanyBackground extends FragmentActivity {
	private String store, scannerName, tableData[][];
	private int number, sum = 0, id;
	private TextView tv1;
	private Button complete[];
	Typeface fontch;
	List<ParseObject> searchObject, ob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_background);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");

		tv1 = (TextView) findViewById(R.id.cbtv1);
		tv1.setTypeface(fontch);
		tv1.setTextSize(20);
		/* 取得商品名稱與價格 */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // 取得團購店名
		scannerName = intent.getStringExtra("scannerName"); // 取得特約商店店名


		Log.i("store", store + "");
		Log.i("scannerName", scannerName + "");

		// tab1
		// 搜尋總共被襖瞄了幾次
		try {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Scanner");
			searchObject = query.find();// 搜尋物件
			for (ParseObject search : searchObject) {
				// 取得資料
				number = (int) search.getInt(scannerName); // scannerNumber
				sum = sum + number;
			}
		} catch (ParseException e) {
			Log.i("eeee", e + "");
		}
		tv1.setText(store + "\n" + "優惠總共被掃描了" + sum + "次");

		/* tab2 */
		// 搜尋BuyerInfo 使用者的所有購買資訊
		try {

			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"BuyerInfo");
			query.whereEqualTo("store", store);
			query.orderByDescending("arrivalDate");
			ob = query.find();

			int size = ob.size(); // 幾筆資料

			String[] store = new String[size];
			String[] userName = new String[size];
			String[] userTel = new String[size];
			String[] userAdd = new String[size];
			String[] commodityName = new String[size];
			int[] numberIndex = new int[size];
			int[] totalPrice = new int[size];
			String[] arrivalDate = new String[size];
			String[] arrivalTime = new String[size];
			int[] ok = new int[size];
			String[] objectId = new String[size];

			// 表格第一列文字
			tableData = new String[size + 1][12];
			tableData[0][0] = "商店";
			tableData[0][1] = "姓名";
			tableData[0][2] = "電話";
			tableData[0][3] = "地址";
			tableData[0][4] = "商品";
			tableData[0][5] = "數量";
			tableData[0][6] = "總價";
			tableData[0][7] = "到貨日期";
			tableData[0][8] = "到貨時段";
			tableData[0][9] = "完成";
			tableData[0][10] = "ok";
			tableData[0][11] = "ID";

			int i = 0;
			for (ParseObject search : ob) {
				// 取得資料
				store[i] = (String) search.get("store");
				userName[i] = (String) search.get("userName");
				userTel[i] = (String) search.get("userTel");
				userAdd[i] = (String) search.get("userAdd");
				commodityName[i] = (String) search.get("commodityName");
				numberIndex[i] = (int) search.getInt("numberIndex");
				totalPrice[i] = (int) search.getInt("totalPrice");
				arrivalDate[i] = (String) search.get("arrivalDate");
				arrivalTime[i] = (String) search.get("arrivalTime");
				ok[i] = (int) search.getInt("complete");
				objectId[i] = search.getObjectId();
				// 放入tableData字串
				tableData[i + 1][0] = store[i];
				tableData[i + 1][1] = userName[i];
				tableData[i + 1][2] = userTel[i];
				tableData[i + 1][3] = userAdd[i];
				tableData[i + 1][4] = commodityName[i];
				tableData[i + 1][5] = Integer.toString(numberIndex[i]);
				tableData[i + 1][6] = Integer.toString(totalPrice[i]);
				tableData[i + 1][7] = arrivalDate[i];
				tableData[i + 1][8] = arrivalTime[i];
				tableData[i + 1][9] = "";
				tableData[i + 1][10] = Integer.toString(ok[i]);
				tableData[i + 1][11] = objectId[i];
				i++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/* tab1 */

		/* tab2 */

		setTable();

		setupViewComponent();
	}

	/* tab2 */
	public void setTable() {

		TableLayout t1 = (TableLayout) findViewById(R.id.tablay2);
		complete = new Button[tableData.length];
		for (int i = 0; i < tableData.length; i++) { // 列

			TableRow row = new TableRow(this);
			for (int j = 0; j < tableData[i].length - 2; j++) { // 行
				if (i == 0) {
					TextView tv = new TextView(this); // 設定第一排文字
					tv.setTypeface(fontch);
					tv.setTextSize(20);
					tv.setTextColor(Color.BLACK);
					tv.setText(tableData[i][j] + " ");
					row.addView(tv, j);
				} else {
					if (j < tableData[i].length - 3) { // 設定 其他搜尋到的文字
						TextView tv = new TextView(this);
						tv.setTypeface(fontch);
						tv.setTextSize(20);
						tv.setTextColor(Color.BLACK);
						tv.setText(tableData[i][j] + " ");
						row.addView(tv, j);
					} else if (j == tableData[i].length - 3) { // 設定按鈕
						if (tableData[i][10].equals("1")) {
							TextView tv = new TextView(this);
							tv.setTypeface(fontch);
							tv.setTextSize(20);
							tv.setTextColor(Color.BLACK);
							tv.setText("已完成");
							row.addView(tv, j);
						} else {// 出現按鈕
							complete[i] = new Button(this);
							complete[i].setTypeface(fontch);
							complete[i].setTextColor(Color.BLACK);
							complete[i]
									.setBackgroundResource(R.drawable.list_commodity);
							complete[i].setText("未完成");
							complete[i].setId(i);
							complete[i].setOnClickListener(cc); // 取消訂單
							row.addView(complete[i], j);
						}

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
			Log.i("tableData[id][4]", tableData[id][4]+"");
			
			// 跳出確認視窗
			new AlertDialog.Builder(CompanyBackground.this)
					.setTitle("確定完成?")
					.setMessage("完成第" + id + "筆資料")
					.setNegativeButton("完成",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									//輸入complete=1  顯示完成
									ParseQuery<ParseObject> query = ParseQuery
											.getQuery("BuyerInfo");
									query.whereEqualTo("objectId",
											tableData[id][11]);
									query.getFirstInBackground(new GetCallback<ParseObject>() {
										public void done(ParseObject object,
												ParseException e) {
											if (e == null) {
												object.put("complete", 1);
												object.saveInBackground();
											}
										}
									});
									//紀錄商品次數
									ParseQuery<ParseObject> queryC = ParseQuery.getQuery("Commodity");
									queryC.whereEqualTo("commodity",tableData[id][4]);
									queryC.getFirstInBackground(new GetCallback<ParseObject>() {
										public void done(ParseObject object,
												ParseException e) {
											if (e == null) {
												int sellNumber = object.getInt("sellNumber");
												Log.i("sellNumber", sellNumber+"");
												sellNumber++;
												object.put("sellNumber", sellNumber);
												object.saveInBackground();
											}
										}
									});
									
									ref();
								}
							})
					.setPositiveButton("未完成",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();

		}
	};

	void ref() {
		Intent intent = new Intent(this, CompanyBackground.class);
		intent.putExtra("store", store);
		intent.putExtra("scannerName", scannerName);
		startActivity(intent);
	}

	private void setupViewComponent() {
		/* tabHost */
		// 從資源類別R中取得介面元件
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
