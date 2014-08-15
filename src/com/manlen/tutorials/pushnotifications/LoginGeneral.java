package com.manlen.tutorials.pushnotifications;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseObject;

public class LoginGeneral extends FragmentActivity {
	private Button login, goBack;
	private EditText userNameEdit, telEdit, addressEdit;
	private RadioGroup genderRadioGroup;
	private String store, commodityName, arrivalDate, arrivalTime, userName,
			userTel, userAdd, gender, myName, myTel, myAdd;
	private int numberIndex, totalPrice;
	private boolean telboolean, myTelboolean;
	public ProgressDialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_general);
		login = (Button) findViewById(R.id.login);
		goBack = (Button) findViewById(R.id.back);
		userNameEdit = (EditText) findViewById(R.id.userNameEdit);
		telEdit = (EditText) findViewById(R.id.telEdit);
		addressEdit = (EditText) findViewById(R.id.addressEdit);
		genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);

		/* 取得購買資訊 */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // 取得商店名稱
		commodityName = intent.getStringExtra("commodityName"); // 取得商品名稱
		numberIndex = intent.getIntExtra("numberIndex", 1); // 取得數量
		totalPrice = intent.getIntExtra("totalPrice", 1); // 取得總價
		arrivalDate = intent.getStringExtra("arrivalDate"); // 取得抵達日期
		arrivalTime = intent.getStringExtra("arrivalTime"); // 取得抵達時段

		/* 登入資訊 */
		// 姓名
		try {
			SharedPreferences preferences = getApplicationContext()
					.getSharedPreferences("Android",
							android.content.Context.MODE_PRIVATE);

			myName = preferences.getString("myName", userName);
			userNameEdit.setText(myName);
			myTel = preferences.getString("myTel", userTel);
			telEdit.setText(myTel);
			myAdd = preferences.getString("myAdd", userAdd);
			addressEdit.setText(myAdd);
		} catch (Exception e) {

		}
		userNameEdit.addTextChangedListener(new TextWatcher() {
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
					userName = userNameEdit.getText().toString(); // 取得輸入姓名
				} catch (Exception e) {

				}

			}
		});
		// 性別
		genderRadioGroup.setOnCheckedChangeListener(listener);
		// 電話
		
		telEdit.addTextChangedListener(new TextWatcher() {
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
					userTel = telEdit.getText().toString(); // 取得輸入電話
					Pattern pattern = Pattern.compile("(09)+[\\d]{8}"); // 正則
					Matcher matcher = pattern.matcher(userTel);
					telboolean = matcher.matches();

				} catch (Exception e) {

				}

			}
		});
		// 住址
		
		addressEdit.addTextChangedListener(new TextWatcher() {
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
					userAdd = addressEdit.getText().toString(); // 取得輸入住址
				} catch (Exception e) {

				}

			}
		});

		login.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Pattern pattern2 = Pattern.compile("(09)+[\\d]{8}"); // 正則
				Matcher matcher2 = pattern2.matcher(myTel);
				myTelboolean = matcher2.matches();
				if (userName == null && myName == null) {
					Toast.makeText(getBaseContext(), "姓名尚未填寫",
							Toast.LENGTH_SHORT).show();
				} else if (gender == null) {
					Toast.makeText(getBaseContext(), "性別尚未選擇",
							Toast.LENGTH_SHORT).show();
				} else if (userTel == null && myTel == null) {
					Toast.makeText(getBaseContext(), "手機號碼尚未填寫",
							Toast.LENGTH_SHORT).show();
				} else if (telboolean == false && myTelboolean == false) {
					Log.i("telboolean", telboolean + "");
					Log.i("myTelboolean", myTelboolean + "");
					Toast.makeText(getBaseContext(), "手機號碼格式不正確",
							Toast.LENGTH_SHORT).show();

				} else if (userAdd == null && myAdd == null) {
					Toast.makeText(getBaseContext(), "住址尚未填寫",
							Toast.LENGTH_SHORT).show();
				} else {
					ParseObject buyInfo = new ParseObject("BuyerInfo");
					
					// 儲存到手機
					SharedPreferences preferences = getApplicationContext()
							.getSharedPreferences("Android",
									android.content.Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					
					if (myName == null) {
						buyInfo.put("userName", userName); //儲存雲端
						editor.putString("myName", userName); //儲存手機
					} else {
						buyInfo.put("userName", myName);
						editor.putString("myName", myName);
					}
					if (myTel == null) {
						buyInfo.put("userTel", userTel);
						editor.putString("myTel", userTel);
					} else {
						buyInfo.put("userTel", myTel);
						editor.putString("myTel", myTel);
					}
					if (myAdd == null) {
						buyInfo.put("userAdd", userAdd);
						editor.putString("myAdd", userAdd);
					} else {
						buyInfo.put("userAdd", myAdd);
						editor.putString("myAdd", myAdd);
					}
					editor.commit();
					buyInfo.put("gender", gender);

					buyInfo.put("store", store);
					buyInfo.put("commodityName", commodityName);
					buyInfo.put("numberIndex", numberIndex);
					buyInfo.put("totalPrice", totalPrice);
					buyInfo.put("arrivalDate", arrivalDate);
					buyInfo.put("arrivalTime", arrivalTime);

					buyInfo.saveInBackground();
					Toast.makeText(getBaseContext(), "恭喜您購買成功!",
							Toast.LENGTH_SHORT).show();
					

					goListCommodity();
				}
			}
		});

		goBack.setOnClickListener(back); // 返回上一頁
	}

	private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.gender_male_button:
				gender = "男";
				break;
			case R.id.gender_female_button:
				gender = "女";
				break;

			}

		}

	};

	void goListCommodity() {
		progress();
		Intent intent = new Intent(this, ListCommodity.class);
		intent.putExtra("userTel", userTel); // 傳送使用者電話資料
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
	// back
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
}
