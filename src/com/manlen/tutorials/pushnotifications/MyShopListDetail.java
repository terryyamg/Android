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

import com.parse.ParseObject;

public class MyShopListDetail extends FragmentActivity {
	private String store, commodityName, numberIndex,
			totalPrice, arrivalDate, arrivalTime, objectId;

	private TextView textView1, textView2, textView3, textView4, textView5,
			textView6, textView7, textView8, textView9, textView10, textView11,
			textView12, textView13, textView14;
	private Button cancel;
	
	Typeface fontch;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_shop_list_detail);
		fontch = Typeface.createFromAsset(this.getAssets(), "fonts/wt001.ttf");
		// 取的詳細資料
				Intent intent = getIntent();
				store = intent.getStringExtra("store"); // 商店
				commodityName = intent.getStringExtra("commodityName"); // 商品
				arrivalDate = intent.getStringExtra("arrivalDate"); // 到貨日期
				arrivalTime = intent.getStringExtra("arrivalTime"); // 到貨時段
				numberIndex = intent.getStringExtra("numberIndex"); // 數量
				totalPrice = intent.getStringExtra("totalPrice"); // 總價
				objectId = intent.getStringExtra("objectId"); // 物件id			
				
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
				cancel = (Button) findViewById(R.id.cancel);

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
				cancel.setTypeface(fontch);


				textView2.setText(store);
				textView4.setText(commodityName);
				textView6.setText(arrivalDate);
				textView8.setText(arrivalTime);
				textView10.setText(numberIndex);
				textView12.setText(totalPrice);
				
				cancel.setOnClickListener(cc);
				
	}
		
	// 取消訂單
		private OnClickListener cc = new OnClickListener() {
			public void onClick(View v) {
				// 跳出確認視窗
				new AlertDialog.Builder(MyShopListDetail.this)
						.setTitle("確定刪除?")
						.setMessage("刪除第此筆訂單資料")
						.setNegativeButton("刪除",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										ParseObject.createWithoutData("BuyerInfo",
												objectId)
												.deleteEventually(); // Parse指令
																		// 刪除指定的 row
										ref();
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

		void ref() {
			Intent intent = new Intent(this, NavigationDrawer.class);

			startActivity(intent);
		}
}
