package com.manlen.tutorials.pushnotifications;

import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SearchCommodity extends FragmentActivity {
	private String searchName, sn[];
	private int pr[],sc[], id;
	List<ParseObject> searchObject;
	private Button close,searchBuy[], recommend[];
	private String tableSearch[][];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_commodity);
		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(back); // 返回
		Intent intent = getIntent();
		searchName = intent.getStringExtra("searchName"); // 取得搜尋名稱
		Log.i("search", searchName + "");

		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // 哪個table
			queryCommodity.whereContains("commodity", searchName);
			searchObject = queryCommodity.find();// 搜尋物件
			int sizeob = searchObject.size(); // 幾筆資料
			sc = new int[sizeob]; // 商品類別陣列
			sn = new String[sizeob]; // 商品名稱陣列
			pr = new int[sizeob]; // 商品價格陣列
			tableSearch = new String[sizeob + 1][6];
			tableSearch[0][0] = "商品圖片";
			tableSearch[0][1] = "商品介紹";
			tableSearch[0][2] = "價格";
			tableSearch[0][3] = "購買";
			tableSearch[0][4] = "推薦";
			tableSearch[0][5] = "";
			
			int i = 0;
			for (ParseObject search : searchObject) {
				// 取得資料
				sc[i] = (int) search.getInt("storeClass");
				sn[i] = (String) search.get("commodity");
				pr[i] = (int) search.getInt("price");
				tableSearch[i + 1][1] = sn[i];
				tableSearch[i + 1][2] = Integer.toString(pr[i]);
				tableSearch[i + 1][5] = Integer.toString(sc[i]);
				Log.i("s1", sn[i] + "");
				Log.i("pr1", pr[i] + "");
				i++;
			}

		} catch (Exception e) {
			Log.i("error", "error");
		}
		init();
	}

	public void init() {

		TableLayout t1 = (TableLayout) findViewById(R.id.tableSearch);
		
		searchBuy = new Button[tableSearch.length];
		recommend = new Button[tableSearch.length];
		

		
		for (int i = 0; i < tableSearch.length; i++) { // 列

			TableRow row = new TableRow(this);
			for (int j = 0; j < tableSearch[i].length; j++) { // 行
				if (i == 0) {
					TextView tv = new TextView(this); // 設定第一排文字
					tv.setTextSize(20);
					tv.setTextColor(Color.BLACK);
					tv.setText(tableSearch[i][j] + " ");
					row.addView(tv, j);
				} else {
					if (j == 0) { // 抓圖片
						int k =Integer.parseInt(tableSearch[i][5]);
						ImageView iv = new ImageView(this);
						iv.setImageResource(R.drawable.store+k+ i);
						row.addView(iv, j);
					} else if (j > 0 && j < tableSearch[i].length - 3) { // 設定其他搜尋到的文字
						TextView tv = new TextView(this);
						tv.setTextSize(20);
						tv.setTextColor(Color.BLACK);
						tv.setText(tableSearch[i][j] + " ");
						row.addView(tv, j);
					} else if (j == tableSearch[i].length - 3) { // 設定購買按鈕

						searchBuy[i] = new Button(this);
						searchBuy[i].setTextColor(Color.WHITE);
						searchBuy[i].setBackgroundResource(R.drawable.btn_black);
						searchBuy[i].setText("購買");
						searchBuy[i].setId(i);
						searchBuy[i].setOnClickListener(bb); // 購買動作
						row.addView(searchBuy[i], j);
					} else if (j == tableSearch[i].length - 2) { // 設定推薦按鈕

						recommend[i] = new Button(this);
						recommend[i].setTextColor(Color.WHITE);
						recommend[i]
								.setBackgroundResource(R.drawable.btn_black);
						recommend[i].setText("推薦");
						recommend[i].setId(i);
						recommend[i].setOnClickListener(rr); // 推薦動作
						row.addView(recommend[i], j);
					}
				}

			}
			t1.addView(row);
		}

	}

	// 購買
	private OnClickListener bb = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			Log.i("id", id+"");
			buy();

		}
	};

	void buy() {
		Intent intent = new Intent(this, BuyConfirm.class);
		intent.putExtra("commodityName", sn[id-1]);
		intent.putExtra("price", pr[id-1]);
		startActivity(intent);
	}
	
	private OnClickListener rr = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			shareDialog();
		}
	};
	// 分享app
		void shareDialog() {

			String shareText = tableSearch[id][1]+"只賣"+tableSearch[id][2]+"推薦給你 ";
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
	// 返回
		private OnClickListener back = new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
}
