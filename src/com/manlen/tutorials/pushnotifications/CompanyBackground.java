package com.manlen.tutorials.pushnotifications;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class CompanyBackground extends FragmentActivity {
	private String store, scannerName;
	private int number, sum=0;
	private TextView tv1;
	Typeface fontch;
	List<ParseObject> searchObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_background);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");

		tv1 = (TextView) findViewById(R.id.cbtv1);
		tv1.setTypeface(fontch);

		/* 取得商品名稱與價格 */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // 取得團購店名
		scannerName = intent.getStringExtra("scannerName"); // 取得特約商店店名

		Log.i("store", store + "");
		Log.i("scannerName", scannerName + "");
		// tab1
		// 搜尋總共被襖瞄了幾次
		try {
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery("_Installation");
			searchObject = query.find();// 搜尋物件
			for (ParseObject search : searchObject) {
				// 取得資料
				number = (int) search.getInt(scannerName); // scannerNumber
				sum = number++;
				Log.i("number", number+"");
				Log.i("sum", sum+"");
			}
		} catch (ParseException e) {
			Log.i("eeee", e+"");
		}
		tv1.setText(store + "\n" + "優惠總共被掃描了" + sum + "次");
		setupViewComponent();
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
