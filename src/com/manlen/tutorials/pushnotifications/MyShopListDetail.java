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
		// �����ԲӸ��
				Intent intent = getIntent();
				store = intent.getStringExtra("store"); // �ө�
				commodityName = intent.getStringExtra("commodityName"); // �ӫ~
				arrivalDate = intent.getStringExtra("arrivalDate"); // ��f���
				arrivalTime = intent.getStringExtra("arrivalTime"); // ��f�ɬq
				numberIndex = intent.getStringExtra("numberIndex"); // �ƶq
				totalPrice = intent.getStringExtra("totalPrice"); // �`��
				objectId = intent.getStringExtra("objectId"); // ����id			
				
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

				textView1.setTypeface(fontch); // �r��
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
		
	// �����q��
		private OnClickListener cc = new OnClickListener() {
			public void onClick(View v) {
				// ���X�T�{����
				new AlertDialog.Builder(MyShopListDetail.this)
						.setTitle("�T�w�R��?")
						.setMessage("�R���Ħ����q����")
						.setNegativeButton("�R��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										ParseObject.createWithoutData("BuyerInfo",
												objectId)
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
			Intent intent = new Intent(this, NavigationDrawer.class);

			startActivity(intent);
		}
}
