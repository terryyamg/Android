package com.manlen.tutorials.pushnotifications;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LoginGeneral extends FragmentActivity {
	private Button login, goBack;
	private EditText userNameEdit, telEdit, addressEdit;
	private RadioButton genderFemaleButton,genderMaleButton;
	private RadioGroup genderRadioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_general);
		login = (Button) findViewById(R.id.login);
		goBack = (Button) findViewById(R.id.back);
		userNameEdit = (EditText) findViewById(R.id.userNameEdit);
		telEdit = (EditText) findViewById(R.id.telEdit);
		addressEdit = (EditText) findViewById(R.id.addressEdit);
		genderFemaleButton = (RadioButton) findViewById(R.id.gender_female_button);
		genderMaleButton = (RadioButton) findViewById(R.id.gender_male_button);
		genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
		
		goBack.setOnClickListener(back); //  ªð¦^¤W¤@­¶
	}
	
	//back
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
}
