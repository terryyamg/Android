package com.manlen.tutorials.pushnotifications;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
import android.widget.TextView;
import android.widget.Toast;

public class Lottery extends FragmentActivity {
	private Button login, goBack;
	private TextView tv1, tv3, tv4;
	private EditText userNameEdit, telEdit, addressEdit;
	private String userName, userTel, userAdd, myName, myTel, myAdd, objectId;
	private int lotteryUsed;
	private boolean telboolean, myTelboolean;
	public ProgressDialog dialog = null;
	List<ParseObject> searchSc;
	Typeface fontch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		tv1 = (TextView) findViewById(R.id.tv1);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		login = (Button) findViewById(R.id.login);
		goBack = (Button) findViewById(R.id.back);
		userNameEdit = (EditText) findViewById(R.id.userNameEdit);
		telEdit = (EditText) findViewById(R.id.telEdit);
		addressEdit = (EditText) findViewById(R.id.addressEdit);

		tv1.setTypeface(fontch);
		tv3.setTypeface(fontch);
		tv4.setTypeface(fontch);
		login.setTypeface(fontch);
		goBack.setTypeface(fontch);
		userNameEdit.setTypeface(fontch);
		telEdit.setTypeface(fontch);
		addressEdit.setTypeface(fontch);
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // ���X�ۤv��id
		
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

				Log.i("userName", userName + "");
				Log.i("myName", myName + "");
				
				if (userName == null && myName == null) {
					Toast.makeText(getBaseContext(), "�m�W�|����g",
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
					ParseObject lotteryInfo = new ParseObject("Lottery");

					// �x�s����
					SharedPreferences preferences = getApplicationContext()
							.getSharedPreferences("Android",
									android.content.Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					lotteryInfo.put("installID", objectId); // ��JobjectId
					if (myName == null) {
						lotteryInfo.put("userName", userName); // �x�s����
						editor.putString("myName", userName); // �x�s���
					} else {
						lotteryInfo.put("userName", myName);
						editor.putString("myName", myName);
					}
					if (myTel == null) {
						lotteryInfo.put("userTel", userTel);
						editor.putString("myTel", userTel);
					} else {
						lotteryInfo.put("userTel", myTel);
						editor.putString("myTel", myTel);
					}
					if (myAdd == null) {
						lotteryInfo.put("userAdd", userAdd);
						editor.putString("myAdd", userAdd);
					} else {
						lotteryInfo.put("userAdd", myAdd);
						editor.putString("myAdd", myAdd);
					}
					editor.commit();

					lotteryInfo.saveInBackground();
					
					// �n�J���\ �����w�������+1
					try {
						ParseQuery<ParseObject> query = ParseQuery.getQuery("Scanner");
						query.whereEqualTo("installID", objectId);
						searchSc = query.find();// �j�M����
						for (ParseObject search : searchSc) {
							// ���o���
							lotteryUsed = (int) search.getInt("lotteryUsed" + 1); //���X�w�g����L������
							Log.i("lotteryUsedbrfore", lotteryUsed + "");
							lotteryUsed++;
							Log.i("lotteryUsed", lotteryUsed + "");
							search.put("lotteryUsed", lotteryUsed);
							search.saveInBackground();
						}
					} catch (ParseException e) {
						Log.i("eeeeeeeee", e + "");
					}
					
					Toast.makeText(getBaseContext(), "��J��Ƨ���!",
							Toast.LENGTH_LONG).show();
					go();
				}
			}
		});

		goBack.setOnClickListener(back); // ��^�W�@��
	}

	void go() {
		progress();
		Intent intent = new Intent(this, NavigationDrawer.class);
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
