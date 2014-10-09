package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CompanyBackground extends FragmentActivity {
	private String channels,store, scannerName, tableData[][];
	private int logoNumber,number, sum = 0;
	private TextView tv1;
	private ImageView imageView1;
	Typeface fontch;
	List<ParseObject> searchObject, ob;
	ArrayAdapter<String> MyArrayAdapter;
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_background);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");

		imageView1 = (ImageView) findViewById(R.id.imageView1);
		tv1 = (TextView) findViewById(R.id.cbtv1);
		tv1.setTypeface(fontch);
		tv1.setTextSize(20);
		/* 取得商品名稱與價格 */
		Intent intent = getIntent();
		logoNumber = intent.getIntExtra("logoNumber",1); // 取得pic號碼
		store = intent.getStringExtra("store"); // 取得團購店名
		scannerName = intent.getStringExtra("scannerName"); // 取得特約商店店名

		imageView1.setImageResource(R.drawable.logo+logoNumber);
		
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

		}
		tv1.setText(store + "\n" + "優惠總共被掃描了" + sum + "次");

		/* tab2 */
		// 搜尋BuyerInfo 使用者的所有購買資訊
		listview = (ListView) findViewById(R.id.list);
		MyArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		listview.setAdapter(MyArrayAdapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				listViewClick((int)(arg3+1));
				
			}
		});

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
			tableData[0][9] = "ok";
			tableData[0][10] = "ID";

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
				tableData[i + 1][9] = Integer.toString(ok[i]);
				tableData[i + 1][10] = objectId[i];
				String info = tableData[i + 1][1] + "  " + tableData[i + 1][2]
						+ "  " + tableData[i + 1][4] + "  總價NT." + tableData[i + 1][6];

				MyArrayAdapter.add(info);
				MyArrayAdapter.notifyDataSetChanged();
				i++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		setupViewComponent();
	}

	/* tab2 */
	//
	void listViewClick(int id){
		Intent intent = new Intent(this, CompanyBackgroundDetail.class);
		//intent.putExtra("store", tableData[id][0]); //商店
		intent.putExtra("userName", tableData[id][1]); //姓名
		intent.putExtra("userTel", tableData[id][2]); //電話
		intent.putExtra("userAdd", tableData[id][3]); //地址
		intent.putExtra("commodityName", tableData[id][4]); //商品
		intent.putExtra("numberIndex", tableData[id][5]); //數量
		intent.putExtra("totalPrice", tableData[id][6]); //總價
		intent.putExtra("arrivalDate", tableData[id][7]); //到貨日期
		intent.putExtra("arrivalTime", tableData[id][8]); //到貨時段
		intent.putExtra("checkOk", tableData[id][9]); //完成否
		intent.putExtra("objectId", tableData[id][10]); //物件id
		
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
