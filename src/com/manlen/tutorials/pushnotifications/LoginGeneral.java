package com.manlen.tutorials.pushnotifications;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

public class LoginGeneral extends FragmentActivity {
	private Button login, goBack;
	private TextView tv1, tv2, tv3, tv4;
	private EditText userNameEdit, telEdit, addressEdit;
	private RadioGroup genderRadioGroup;
	private RadioButton gender_male_button, gender_female_button;
	private String store, commodityName, arrivalDate, arrivalTime, userName,
			userTel, userAdd, gender, myName, myTel, myAdd, storeClass, email,subject,text;
	private int numberIndex, totalPrice;
	private boolean telboolean, myTelboolean;
	public ProgressDialog dialog = null;
	Typeface fontch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_general);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		login = (Button) findViewById(R.id.login);
		goBack = (Button) findViewById(R.id.back);
		userNameEdit = (EditText) findViewById(R.id.userNameEdit);
		telEdit = (EditText) findViewById(R.id.telEdit);
		addressEdit = (EditText) findViewById(R.id.addressEdit);
		genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
		gender_male_button = (RadioButton) findViewById(R.id.gender_male_button);
		gender_female_button = (RadioButton) findViewById(R.id.gender_female_button);

		tv1.setTypeface(fontch);
		tv2.setTypeface(fontch);
		tv3.setTypeface(fontch);
		tv4.setTypeface(fontch);
		login.setTypeface(fontch);
		goBack.setTypeface(fontch);
		userNameEdit.setTypeface(fontch);
		telEdit.setTypeface(fontch);
		addressEdit.setTypeface(fontch);
		gender_male_button.setTypeface(fontch);
		gender_female_button.setTypeface(fontch);
		/* 取得購買資訊 */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // 取得商店名稱
		commodityName = intent.getStringExtra("commodityName"); // 取得商品名稱
		numberIndex = intent.getIntExtra("numberIndex", 1); // 取得數量
		totalPrice = intent.getIntExtra("totalPrice", 1); // 取得總價
		arrivalDate = intent.getStringExtra("arrivalDate"); // 取得抵達日期
		arrivalTime = intent.getStringExtra("arrivalTime"); // 取得抵達時段
		storeClass = intent.getStringExtra("storeClass"); // 取的店家頻道

		/* 登入資訊 */
		//抓取特約商店店家email
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"CompanyAccount"); // 哪個table
				query.whereEqualTo("channels", storeClass); // 條件
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							// 取得email資料
							email = object.getString("email"); // 抓取email
							Log.i("email", email + "");
						}
					}
				});
				
		
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
				try {
					Pattern pattern2 = Pattern.compile("(09)+[\\d]{8}"); // 正則
					Matcher matcher2 = pattern2.matcher(myTel);
					myTelboolean = matcher2.matches();
				} catch (Exception e) {
					Log.i("Exception", e + "");
				}

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
						buyInfo.put("userName", userName); // 儲存雲端
						editor.putString("myName", userName); // 儲存手機
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
		// 推撥通知店家頻道
		ParsePush push = new ParsePush();
		push.setChannel(storeClass); // 選擇頻道
		Log.i("storeClass", storeClass + "");
		push.setMessage("您有一筆新的交易通知!!");
		push.sendInBackground();
		// 發送email給店家
		
		try {
					// 信件標題
					subject = "您有一筆新的交易通知!!";
					// 信件內容
					if(userName == null){
						text = "來自" + myName + "(" + gender + ")的交易通知" + "\n連絡電話:"
								+ myTel + "\n聯絡地址:" + myAdd + "\n購買了商品名稱:" + commodityName
								+ "\n商品數量:" + numberIndex + "\n商品總價:" + totalPrice + "\n取貨時段:"
								+ arrivalDate + "的" + arrivalTime + "\n煩請特約商家連絡確認詳細事宜";
					}else{
					text = "來自" + userName + "(" + gender + ")的交易通知" + "\n連絡電話:"
							+ userTel + "\n聯絡地址:" + userAdd + "\n購買了商品名稱:" + commodityName
							+ "\n商品數量:" + numberIndex + "\n商品總價:" + totalPrice + "\n取貨時段:"
							+ arrivalDate + "的" + arrivalTime + "\n煩請特約商家連絡確認詳細事宜";
					}
					GMailSender sender = new GMailSender("manlenapp@gmail.com",
							"manlen123");
					sender.sendMail(subject, text, "manlenapp@gmail.com",email);
				} catch (Exception e) {
					Log.e("SendMail", e.getMessage(), e);
				}
	
		
		Intent intent = new Intent(this, NavigationDrawer.class);
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
