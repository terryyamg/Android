package com.manlen.tutorials.pushnotifications;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;
	private int first, checkFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				SharedPreferences preferences = getApplicationContext()
						.getSharedPreferences("Android",
								android.content.Context.MODE_PRIVATE);

				checkFirst = preferences.getInt("first", first);
				if (checkFirst == 1) { // 有開過
					Intent i = new Intent(SplashScreen.this, MainActivity.class);
					startActivity(i);
				} else { // 沒開過，開啟說明頁
					Intent i = new Intent(SplashScreen.this, Leading.class);
					startActivity(i);
				}
				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}