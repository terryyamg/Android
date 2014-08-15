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

		/* ���o�ʶR��T */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // ���o�ө��W��
		commodityName = intent.getStringExtra("commodityName"); // ���o�ӫ~�W��
		numberIndex = intent.getIntExtra("numberIndex", 1); // ���o�ƶq
		totalPrice = intent.getIntExtra("totalPrice", 1); // ���o�`��
		arrivalDate = intent.getStringExtra("arrivalDate"); // ���o��F���
		arrivalTime = intent.getStringExtra("arrivalTime"); // ���o��F�ɬq

		/* �n�J��T */
		// �m�W
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
				/* �p���`�� */
				try {
					userName = userNameEdit.getText().toString(); // ���o��J�m�W
				} catch (Exception e) {

				}

			}
		});
		// �ʧO
		genderRadioGroup.setOnCheckedChangeListener(listener);
		// �q��
		
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
				/* �p���`�� */
				try {
					userTel = telEdit.getText().toString(); // ���o��J�q��
					Pattern pattern = Pattern.compile("(09)+[\\d]{8}"); // ���h
					Matcher matcher = pattern.matcher(userTel);
					telboolean = matcher.matches();

				} catch (Exception e) {

				}

			}
		});
		// ��}
		
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
				/* �p���`�� */
				try {
					userAdd = addressEdit.getText().toString(); // ���o��J��}
				} catch (Exception e) {

				}

			}
		});

		login.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Pattern pattern2 = Pattern.compile("(09)+[\\d]{8}"); // ���h
				Matcher matcher2 = pattern2.matcher(myTel);
				myTelboolean = matcher2.matches();
				if (userName == null && myName == null) {
					Toast.makeText(getBaseContext(), "�m�W�|����g",
							Toast.LENGTH_SHORT).show();
				} else if (gender == null) {
					Toast.makeText(getBaseContext(), "�ʧO�|�����",
							Toast.LENGTH_SHORT).show();
				} else if (userTel == null && myTel == null) {
					Toast.makeText(getBaseContext(), "������X�|����g",
							Toast.LENGTH_SHORT).show();
				} else if (telboolean == false && myTelboolean == false) {
					Log.i("telboolean", telboolean + "");
					Log.i("myTelboolean", myTelboolean + "");
					Toast.makeText(getBaseContext(), "������X�榡�����T",
							Toast.LENGTH_SHORT).show();

				} else if (userAdd == null && myAdd == null) {
					Toast.makeText(getBaseContext(), "��}�|����g",
							Toast.LENGTH_SHORT).show();
				} else {
					ParseObject buyInfo = new ParseObject("BuyerInfo");
					
					// �x�s����
					SharedPreferences preferences = getApplicationContext()
							.getSharedPreferences("Android",
									android.content.Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					
					if (myName == null) {
						buyInfo.put("userName", userName); //�x�s����
						editor.putString("myName", userName); //�x�s���
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
					Toast.makeText(getBaseContext(), "���߱z�ʶR���\!",
							Toast.LENGTH_SHORT).show();
					

					goListCommodity();
				}
			}
		});

		goBack.setOnClickListener(back); // ��^�W�@��
	}

	private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.gender_male_button:
				gender = "�k";
				break;
			case R.id.gender_female_button:
				gender = "�k";
				break;

			}

		}

	};

	void goListCommodity() {
		progress();
		Intent intent = new Intent(this, ListCommodity.class);
		intent.putExtra("userTel", userTel); // �ǰe�ϥΪ̹q�ܸ��
		startActivity(intent);
	}

	void progress() {
		dialog = ProgressDialog.show(this, "Ū����", "�еy��...", true);
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
