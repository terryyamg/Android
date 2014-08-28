package com.manlen.tutorials.pushnotifications;

import java.util.Calendar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BuyConfirm extends FragmentActivity {
	private Button close, confirm;
	private ImageButton plus, minus;
	private EditText number;
	private TextView name, money,tv1,tv2,tv3,tv4,tv5;
	private DatePicker DatePicker;
	private String Year, Mon, Day, arrivalTime, commodityName, store;
	private RadioGroup timeChose;
	private RadioButton morning,afternoon,night;
	private int price, numberIndex, totalPrice, nowDate, choseDate;
	Typeface fontch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_confirm);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");
		
		close = (Button) findViewById(R.id.close);
		confirm = (Button) findViewById(R.id.confirm);
		plus = (ImageButton) findViewById(R.id.plus);
		minus = (ImageButton) findViewById(R.id.minus);
		number = (EditText) findViewById(R.id.number);
		name = (TextView) findViewById(R.id.name);
		money = (TextView) findViewById(R.id.money);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		timeChose = (RadioGroup) findViewById(R.id.timeChose);
		morning = (RadioButton) findViewById(R.id.morning);
		afternoon = (RadioButton) findViewById(R.id.afternoon);
		night = (RadioButton) findViewById(R.id.night);
		
		close.setTypeface(fontch);
		confirm.setTypeface(fontch);
		name.setTypeface(fontch);
		money.setTypeface(fontch);
		tv1.setTypeface(fontch);
		tv2.setTypeface(fontch);
		tv3.setTypeface(fontch);
		tv4.setTypeface(fontch);
		tv5.setTypeface(fontch);
		morning.setTypeface(fontch);
		afternoon.setTypeface(fontch);
		night.setTypeface(fontch);
		/* 取得商品名稱與價格 */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // 取得商店名稱
		commodityName = intent.getStringExtra("commodityName"); // 取得商品名稱
		price = intent.getIntExtra("price", 1); // 取得單一價格
		
		name.setText(commodityName); // 商品名稱

		number.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				/* 計算總價 */
				try {
					if (numberIndex < 0) {
						numberIndex = Integer.parseInt(number.getText()
								.toString());
						numberIndex = 0;
						money.setText(String.valueOf(numberIndex));
					} else if (numberIndex >= 0) {
						numberIndex = Integer.parseInt(number.getText()
								.toString());
						totalPrice = numberIndex * price; // 總價=數量*單一價格
						money.setText(String.valueOf(totalPrice));
					}
				} catch (Exception e) {

				}

			}
		});

		/* 數量 */

		plus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				numberIndex++;
				number.setText(String.valueOf(numberIndex));
				/* 計算總價 */
				totalPrice = numberIndex * price; // 總價=數量*單一價格
				money.setText(String.valueOf(totalPrice));
			}
		});

		minus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				numberIndex--;
				if (numberIndex < 0) { // 數量值不能為負
					numberIndex = 0;
					number.setText(String.valueOf(numberIndex));
				}
				number.setText(String.valueOf(numberIndex));
				/* 計算總價 */
				totalPrice = numberIndex * price; // 總價=數量*單一價格
				money.setText(String.valueOf(totalPrice));
			}
		});

		/* 到貨日期 */
		Calendar TodayDate = Calendar.getInstance(); // 透過Calendar取的資料
		int sYear = TodayDate.get(Calendar.YEAR); // 一開啟軟體即取得年的數值
		int sMon = TodayDate.get(Calendar.MONTH) + 1; // 一開啟軟體即取得月的數值
														// 月的起始是0，所以+1.
		int sDay = TodayDate.get(Calendar.DAY_OF_MONTH);// 一開啟軟體即取得日的數值
		// 將取得的數字轉成String.
		Year = DateFix(sYear);
		Mon = DateFix(sMon);
		Day = DateFix(sDay);
		nowDate = Integer.parseInt(Year + Mon + Day);// 現在日期
		choseDate = nowDate;
		DatePicker = (DatePicker) findViewById(R.id.DatePicker);
		DatePicker.init(TodayDate.get(Calendar.YEAR),
				TodayDate.get(Calendar.MONTH),
				TodayDate.get(Calendar.DAY_OF_MONTH),
				// DatePicker年月日更改後，會觸發作以下的事情。
				new DatePicker.OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Year = DateFix(year);
						Mon = DateFix(monthOfYear + 1); // 月的初始是0，所以先加 1。
						Day = DateFix(dayOfMonth);
						choseDate = Integer.parseInt(Year + Mon + Day);
					}
				});

		// 選擇時段
		timeChose.setOnCheckedChangeListener(listener);

		// 確認
		confirm.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				sentToLogin();
			}
		});
		// 返回
		close.setOnClickListener(back);
	}

	// 方法DateFix:將傳送進來的年月日轉成String，在判斷是否前面需要加0。
	private static String DateFix(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	// 選擇時段
	private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.morning:
				arrivalTime = "早上";
				break;
			case R.id.afternoon:
				arrivalTime = "下午";
				break;
			case R.id.night:
				arrivalTime = "晚上";
				break;

			}

		}

	};

	// 送出
	void sentToLogin() {

		if (numberIndex == 0) { // 數量判斷
			Toast.makeText(getBaseContext(), "數量不可為0", Toast.LENGTH_SHORT)
					.show();
		} else if (nowDate > choseDate) { // 現在時間 比 選擇時間 早
			Toast.makeText(getBaseContext(), "日期不可為今天以前", Toast.LENGTH_SHORT)
					.show();
		} else if (arrivalTime == null) { // 時段判斷
			Toast.makeText(getBaseContext(), "時段尚未選擇", Toast.LENGTH_SHORT)
					.show();
		} else {
			Intent intent = new Intent(this, LoginGeneral.class);
			intent.putExtra("store", store); // 商店名稱
			intent.putExtra("commodityName", commodityName); // 商品名稱
			intent.putExtra("numberIndex", numberIndex); // 數量
			intent.putExtra("totalPrice", totalPrice); // 總價
			intent.putExtra("arrivalDate", Year + Mon + Day); // 取貨日期
			intent.putExtra("arrivalTime", arrivalTime); // 取貨日期
			startActivity(intent);
		}

	}

	// 返回
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
}
