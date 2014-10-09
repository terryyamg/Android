package com.manlen.tutorials.pushnotifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CompanyBackgroundDetail extends FragmentActivity {
	Typeface fontch;
	private String userName, userTel, userAdd, commodityName, numberIndex,
			totalPrice, arrivalDate, arrivalTime, checkOk, objectId, store,
			scannerName;

	private TextView textView1, textView2, textView3, textView4, textView5,
			textView6, textView7, textView8, textView9, textView10, textView11,
			textView12, textView13, textView14, textView15, textView16,
			textView17, textView18;
	private Button complete;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_background_detail);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		// 取的詳細資料
		Intent intent = getIntent();
		userName = intent.getStringExtra("userName"); // 姓名
		userTel = intent.getStringExtra("userTel"); // 電話
		userAdd = intent.getStringExtra("userAdd"); // 地址
		commodityName = intent.getStringExtra("commodityName"); // 商品
		numberIndex = intent.getStringExtra("numberIndex"); // 數量
		totalPrice = intent.getStringExtra("totalPrice"); // 總價
		arrivalDate = intent.getStringExtra("arrivalDate"); // 到貨日期
		arrivalTime = intent.getStringExtra("arrivalTime"); // 到貨時段
		checkOk = intent.getStringExtra("checkOk"); // 完成否
		objectId = intent.getStringExtra("objectId"); // 物件id
		store = intent.getStringExtra("store");
		scannerName = intent.getStringExtra("scannerName");

		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);
		textView5 = (TextView) findViewById(R.id.textView5);
		textView6 = (TextView) findViewById(R.id.textView6);
		textView7 = (TextView) findViewById(R.id.textView7);
		textView8 = (TextView) findViewById(R.id.textView8);
		textView9 = (TextView) findViewById(R.id.textView9);
		textView10 = (TextView) findViewById(R.id.textView10);
		textView11 = (TextView) findViewById(R.id.textView11);
		textView12 = (TextView) findViewById(R.id.textView12);
		textView13 = (TextView) findViewById(R.id.textView13);
		textView14 = (TextView) findViewById(R.id.textView14);
		textView15 = (TextView) findViewById(R.id.textView15);
		textView16 = (TextView) findViewById(R.id.textView16);
		textView17 = (TextView) findViewById(R.id.textView17);
		textView18 = (TextView) findViewById(R.id.textView18);
		complete = (Button) findViewById(R.id.complete);

		textView1.setTypeface(fontch); // 字型
		textView2.setTypeface(fontch);
		textView3.setTypeface(fontch);
		textView4.setTypeface(fontch);
		textView5.setTypeface(fontch);
		textView6.setTypeface(fontch);
		textView7.setTypeface(fontch);
		textView8.setTypeface(fontch);
		textView9.setTypeface(fontch);
		textView10.setTypeface(fontch);
		textView11.setTypeface(fontch);
		textView12.setTypeface(fontch);
		textView13.setTypeface(fontch);
		textView14.setTypeface(fontch);
		textView15.setTypeface(fontch);
		textView16.setTypeface(fontch);
		textView17.setTypeface(fontch);
		textView18.setTypeface(fontch);
		complete.setTypeface(fontch);

		textView2.setText(userName);
		textView4.setText(userTel);
		textView6.setText(userAdd);
		textView8.setText(commodityName);
		textView10.setText(numberIndex);
		textView12.setText(totalPrice);
		textView14.setText(arrivalDate);
		textView16.setText(arrivalTime);

		if (checkOk.equals("1")) {
			complete.setVisibility(View.GONE);
			textView18.setText("已完成");
		}

		complete.setOnClickListener(cc);
	}

	// 取消訂單
	private OnClickListener cc = new OnClickListener() {
		public void onClick(View v) {
			// 跳出確認視窗
			new AlertDialog.Builder(CompanyBackgroundDetail.this)
					.setTitle("確定完成?")
					.setMessage("完成第這筆交易?")
					.setNegativeButton("完成",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// 輸入complete=1 顯示完成
									ParseQuery<ParseObject> query = ParseQuery
											.getQuery("BuyerInfo");
									query.whereEqualTo("objectId", objectId);
									query.getFirstInBackground(new GetCallback<ParseObject>() {
										public void done(ParseObject object,
												ParseException e) {
											if (e == null) {
												object.put("complete", 1);
												object.saveInBackground();
											}
										}
									});
									// 紀錄商品次數
									ParseQuery<ParseObject> queryC = ParseQuery
											.getQuery("Commodity");
									queryC.whereEqualTo("commodity",
											commodityName);
									queryC.getFirstInBackground(new GetCallback<ParseObject>() {
										public void done(ParseObject object,
												ParseException e) {
											if (e == null) {
												int sellNumber = object
														.getInt("sellNumber");
												sellNumber++;
												object.put("sellNumber",
														sellNumber);
												object.saveInBackground();
											}
										}
									});
									go();
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

	void go() {
		Intent intent = new Intent(this, CompanyBackground.class);
		intent.putExtra("store", store);
		intent.putExtra("scannerName", scannerName);
		startActivity(intent);
	}

}
