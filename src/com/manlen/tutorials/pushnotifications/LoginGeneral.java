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
		/* �r�� */
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
		/* ���o�ʶR��T */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // ���o�ө��W��
		commodityName = intent.getStringExtra("commodityName"); // ���o�ӫ~�W��
		numberIndex = intent.getIntExtra("numberIndex", 1); // ���o�ƶq
		totalPrice = intent.getIntExtra("totalPrice", 1); // ���o�`��
		arrivalDate = intent.getStringExtra("arrivalDate"); // ���o��F���
		arrivalTime = intent.getStringExtra("arrivalTime"); // ���o��F�ɬq
		storeClass = intent.getStringExtra("storeClass"); // �������a�W�D

		/* �n�J��T */
		//����S���ө����aemail
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"CompanyAccount"); // ����table
				query.whereEqualTo("channels", storeClass); // ����
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							// ���oemail���
							email = object.getString("email"); // ���email
							Log.i("email", email + "");
						}
					}
				});
				
		
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
				try {
					Pattern pattern2 = Pattern.compile("(09)+[\\d]{8}"); // ���h
					Matcher matcher2 = pattern2.matcher(myTel);
					myTelboolean = matcher2.matches();
				} catch (Exception e) {
					Log.i("Exception", e + "");
				}

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
						buyInfo.put("userName", userName); // �x�s����
						editor.putString("myName", userName); // �x�s���
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
		// �����q�����a�W�D
		ParsePush push = new ParsePush();
		push.setChannel(storeClass); // ����W�D
		Log.i("storeClass", storeClass + "");
		push.setMessage("�z���@���s������q��!!");
		push.sendInBackground();
		// �o�eemail�����a
		
		try {
					// �H����D
					subject = "�z���@���s������q��!!";
					// �H�󤺮e
					if(userName == null){
						text = "�Ӧ�" + myName + "(" + gender + ")������q��" + "\n�s���q��:"
								+ myTel + "\n�p���a�}:" + myAdd + "\n�ʶR�F�ӫ~�W��:" + commodityName
								+ "\n�ӫ~�ƶq:" + numberIndex + "\n�ӫ~�`��:" + totalPrice + "\n���f�ɬq:"
								+ arrivalDate + "��" + arrivalTime + "\n�нЯS���Ӯa�s���T�{�ԲӨƩy";
					}else{
					text = "�Ӧ�" + userName + "(" + gender + ")������q��" + "\n�s���q��:"
							+ userTel + "\n�p���a�}:" + userAdd + "\n�ʶR�F�ӫ~�W��:" + commodityName
							+ "\n�ӫ~�ƶq:" + numberIndex + "\n�ӫ~�`��:" + totalPrice + "\n���f�ɬq:"
							+ arrivalDate + "��" + arrivalTime + "\n�нЯS���Ӯa�s���T�{�ԲӨƩy";
					}
					GMailSender sender = new GMailSender("manlenapp@gmail.com",
							"manlen123");
					sender.sendMail(subject, text, "manlenapp@gmail.com",email);
				} catch (Exception e) {
					Log.e("SendMail", e.getMessage(), e);
				}
	
		
		Intent intent = new Intent(this, NavigationDrawer.class);
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
