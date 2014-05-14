package com.parse.tutorials.pushnotifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

public class MainActivity extends FragmentActivity  {
	private GoogleMap map;
	private RadioButton genderFemaleButton;
	private RadioButton genderMaleButton;
	private EditText ageEditText;
	private RadioGroup genderRadioGroup;

	public static final String GENDER_MALE = "male";
	public static final String GENDER_FEMALE = "female";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Track app opens.
		ParseAnalytics.trackAppOpened(getIntent());

		// Set up our UI member properties.
		this.genderFemaleButton = (RadioButton) findViewById(R.id.gender_female_button);
		this.genderMaleButton = (RadioButton) findViewById(R.id.gender_male_button);
		this.ageEditText = (EditText) findViewById(R.id.age_edit_text);
		this.genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
		// google map
		map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		setupViewComponent();
	}

	private void setupViewComponent() {
		// 從資源類別R中取得介面元件
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator(
				"優惠方案",
				getResources().getDrawable(
						android.R.drawable.ic_lock_idle_alarm));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("地理位置",
				getResources().getDrawable(android.R.drawable.ic_dialog_alert));
		spec.setContent(R.id.tab2);
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("tab3");
		spec.setIndicator("資料填寫",
				getResources().getDrawable(android.R.drawable.ic_dialog_alert));
		spec.setContent(R.id.tab3);
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("tab4");
		spec.setIndicator("表4",
				getResources().getDrawable(android.R.drawable.ic_dialog_alert));
		spec.setContent(R.id.tab4);
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("tab5");
		spec.setIndicator("表5",
				getResources().getDrawable(android.R.drawable.ic_dialog_alert));
		spec.setContent(R.id.tab5);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// 設定tab標籤的字體大小
		TabWidget tabWidget = (TabWidget) tabHost
				.findViewById(android.R.id.tabs);
		View tabView = tabWidget.getChildTabViewAt(0);
		TextView tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);
		
		tabView = tabWidget.getChildTabViewAt(1);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);
		
		tabView = tabWidget.getChildTabViewAt(2);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);
		
		tabView = tabWidget.getChildTabViewAt(3);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);
		
		tabView = tabWidget.getChildTabViewAt(4);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(10);
	}

	@Override
	public void onStart() {
		super.onStart();

		// Display the current values for this user, such as their age and
		// gender.
		displayUserProfile();
		refreshUserProfile();
	}

	// Save the user's profile to their installation.
	public void saveUserProfile(View view) {
		String ageTextString = ageEditText.getText().toString();

		if (ageTextString.length() > 0) {
			ParseInstallation.getCurrentInstallation().put("age",
					Integer.valueOf(ageTextString));
		}

		if (genderRadioGroup.getCheckedRadioButtonId() == genderFemaleButton
				.getId()) {
			ParseInstallation.getCurrentInstallation().put("gender",
					GENDER_FEMALE);
		} else if (genderRadioGroup.getCheckedRadioButtonId() == genderMaleButton
				.getId()) {
			ParseInstallation.getCurrentInstallation().put("gender",
					GENDER_MALE);
		} else {
			ParseInstallation.getCurrentInstallation().remove("gender");
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(ageEditText.getWindowToken(), 0);

		ParseInstallation.getCurrentInstallation().saveInBackground(
				new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e == null) {
							Toast toast = Toast.makeText(
									getApplicationContext(),
									R.string.alert_dialog_success,
									Toast.LENGTH_SHORT);
							toast.show();
						} else {
							e.printStackTrace();

							Toast toast = Toast.makeText(
									getApplicationContext(),
									R.string.alert_dialog_failed,
									Toast.LENGTH_SHORT);
							toast.show();
						}
					}
				});
	}

	// Refresh the UI with the data obtained from the current ParseInstallation
	// object.
	private void displayUserProfile() {
		String gender = ParseInstallation.getCurrentInstallation().getString(
				"gender");
		int age = ParseInstallation.getCurrentInstallation().getInt("age");

		if (gender != null) {
			genderMaleButton.setChecked(gender.equalsIgnoreCase(GENDER_MALE));
			genderFemaleButton.setChecked(gender
					.equalsIgnoreCase(GENDER_FEMALE));
		} else {
			genderMaleButton.setChecked(false);
			genderFemaleButton.setChecked(false);
		}

		if (age > 0) {
			ageEditText.setText(Integer.valueOf(age).toString());
		}

	}

	// Get the latest values from the ParseInstallation object.
	private void refreshUserProfile() {
		ParseInstallation.getCurrentInstallation().refreshInBackground(
				new RefreshCallback() {

					@Override
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							displayUserProfile();
						}
					}
				});
	}
}
