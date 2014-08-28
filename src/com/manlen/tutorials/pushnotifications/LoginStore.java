package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LoginStore extends FragmentActivity {
	private String password, store, scannerName, storeAc, storePs;
	private TextView textView1, textView2, say;
	private Button confime;
	private EditText storeAccount, storePassword;
	Typeface fontch;
	List<ParseObject> searchObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_store);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");

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
					storeAc = storeAccount.getText().toString(); // ���o��J�b��
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
					storePs = storePassword.getText().toString(); // ���o��J�K�X
				} catch (Exception e) {

				}

			}
		});

		confime.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"CompanyAccount"); // ����table
				query.whereEqualTo("account", storeAc); // ����
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							// ���o���
							password = object.getString("password"); // ����K�X
							store = object.getString("store"); // ������ʩ��W
							scannerName = object.getString("scannerName"); // ����S���ө����W

							if (storePs.equals(password)) { // �K�Xok
								checkOk();
							} else {
								say.setText("��p�A�z���K�X�����T");
							}

						} else {
							say.setText("��p�A�z���b�������T");
						}
					}
				});
			}
		});
	}

	void checkOk() {
		Intent intent = new Intent(this, CompanyBackground.class);
		intent.putExtra("store", store);
		intent.putExtra("scannerName", scannerName);
		startActivity(intent);
		Toast.makeText(getBaseContext(), "�n�J���\!", Toast.LENGTH_SHORT).show();
	}
}
