package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

public class LoginStore extends FragmentActivity {
	private String password, store, scannerName, channels, storeAc, storePs;
	private TextView textView1, textView2, say;
	private Button confime;
	private EditText storeAccount, storePassword;
	private int logoNumber;
	Typeface fontch;
	List<ParseObject> searchObject;
	public ProgressDialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_store);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");

		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		say = (TextView) findViewById(R.id.say);
		confime = (Button) findViewById(R.id.confime);
		storeAccount = (EditText) findViewById(R.id.storeAccount);
		storePassword = (EditText) findViewById(R.id.storePassword);

		textView1.setTypeface(fontch);
		textView2.setTypeface(fontch);
		say.setTypeface(fontch);
		confime.setTypeface(fontch);
		storeAccount.setTypeface(fontch);
		storePassword.setTypeface(fontch);

		storeAccount.addTextChangedListener(new TextWatcher() {
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
				try {
					storeAc = storeAccount.getText().toString(); // 取得輸入帳號
				} catch (Exception e) {

				}

			}
		});

		storePassword.addTextChangedListener(new TextWatcher() {
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
				try {
					storePs = storePassword.getText().toString(); // 取得輸入密碼
				} catch (Exception e) {

				}

			}
		});

		confime.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"CompanyAccount"); // 哪個table
				query.whereEqualTo("account", storeAc); // 條件
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							// 取得資料
							password = object.getString("password"); // 抓取密碼
							store = object.getString("store"); // 抓取團購店名
							scannerName = object.getString("scannerName"); // 抓取特約商店店名
							channels = object.getString("channels"); // 抓取頻道
							logoNumber= object.getInt("logoNumber"); // 抓取pic號碼
							
							if (storePs.equals(password)) { // 密碼ok
								checkOk();
							} else {
								say.setText("抱歉，您的密碼不正確");
							}

						} else {
							say.setText("抱歉，您的帳號不正確");
						}
					}
				});
			}
		});
	}

	void checkOk() {
		// 存入該手機用戶為商店頻道
		progress();
		PushService.subscribe(getApplicationContext(), channels,
				NavigationDrawer.class);

		Intent intent = new Intent(this, CompanyBackground.class);
		intent.putExtra("logoNumber", logoNumber);
		intent.putExtra("store", store);
		intent.putExtra("scannerName", scannerName);
		startActivity(intent);
		Toast.makeText(getBaseContext(), "登入成功!", Toast.LENGTH_SHORT).show();
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
