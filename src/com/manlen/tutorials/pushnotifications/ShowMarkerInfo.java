package com.manlen.tutorials.pushnotifications;

import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ShowMarkerInfo extends FragmentActivity {
	private String markerTitle, tableData[][];
	private int id;
	private ImageButton imgButton;
	public ProgressDialog dialog = null;
	List<ParseObject> ob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_info);
		Intent intent = getIntent();
		markerTitle = intent.getStringExtra("MarkTitle");
		try {
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"MarkerInfo");
			ob = query.find();
			int size = ob.size(); // 幾筆資料
			String[] storeName = new String[size];
			String[] storeLocation = new String[size];
			String[] storeDetail = new String[size];
			int[] picNumber = new int[size];
			
			tableData = new String[size + 1][4];
			int i = 0;
			for (ParseObject search : ob) {
				// 取得資料
				storeName[i] = (String) search.get("storeName");
				storeLocation[i] = (String) search.get("storeLocation");
				storeDetail[i] = (String) search.get("storeDetail");
				picNumber[i] = (int) search.getInt("picNumber");
				// 放入tableData字串
				tableData[i][0] = storeName[i];
				tableData[i][1] = storeLocation[i];
				tableData[i][2] = storeDetail[i];
				tableData[i][3] = Integer.toString(picNumber[i]);
				Log.i("storeName", storeName[i]+"");
				Log.i("storeLoaction", storeLocation[i]+"");
				Log.i("storeDetail", storeDetail[i]+"");
				Log.i("picNumber", picNumber[i]+"");
				i++;
			}
		} catch (Exception e) {
		}

		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);

		// 排版
		for (int i = 0; i < (tableData.length - 1); i++) { // 列
			TableRow row = new TableRow(this);
			if (markerTitle.equals(tableData[i][0])) { //比對傳送商店名稱
				imgButton = new ImageButton(this);
				imgButton.setImageResource(R.drawable.market +Integer.parseInt(tableData[i][3]));
				imgButton.setId(i);
				imgButton.setBackgroundDrawable(null);
				imgButton.setOnClickListener(imgButtonListen);
				row.addView(imgButton, 0);
			}
			t1.addView(row);
		}
	}

	private OnClickListener imgButtonListen = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			detail();
		}
	};

	void detail() {
		progress();
		Intent intent = new Intent(this, DetailMarkerInfo.class);
		intent.putExtra("storeName", tableData[id][0]);
		intent.putExtra("storeLocation", tableData[id][1]);
		intent.putExtra("storeDetail", tableData[id][2]);
		intent.putExtra("picNumber", tableData[id][3]);
		startActivity(intent);
	}

	void progress() {
		dialog = ProgressDialog.show(this, "讀取中", "請稍後...", true);
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
}
