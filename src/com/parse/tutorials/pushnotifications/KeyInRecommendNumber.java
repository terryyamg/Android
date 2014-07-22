package com.parse.tutorials.pushnotifications;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class KeyInRecommendNumber extends FragmentActivity {
	private Button close, confime;
	private TextView say;
	private EditText recommendNumber;
	private String objectId;
	private Boolean recommendBoolean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend);

		close = (Button) findViewById(R.id.close);
		confime = (Button) findViewById(R.id.confime);
		say = (TextView) findViewById(R.id.say);
		recommendNumber = (EditText) findViewById(R.id.recommendNumber);

		confime.setOnClickListener(new Button.OnClickListener() { // 確認輸入
			public void onClick(View v) {
				confimeRecommend();
			}
		});
		close.setOnClickListener(back); // 關閉視窗

	}

	/* 輸入驗證 */
	void confimeRecommend() {

		String gRecommend = recommendNumber.getText().toString();
		Log.i("gRecommend:", gRecommend + "");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_Installation");
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // 取出自己的推薦碼
		recommendBoolean = ParseInstallation.getCurrentInstallation()
				.getBoolean("recommendBoolean"); // 取出自己是否輸入過推薦碼
		if (gRecommend.equals(objectId)) {
			say.setText("抱歉，您輸入的為自己的推薦碼");
		} else if (recommendBoolean == true) {
			say.setText("抱歉，您已經成功推薦過了");
		} else {
			query.getInBackground(gRecommend, new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						Log.i("f:", e + "");
						say.setText("抱歉，您輸入的為無效推薦碼");

					} else {
						int recommendFrequency = object
								.getInt("recommendFrequency");
						Log.i("recommendNumber:", recommendFrequency + "");

						recommendFrequency++; // 累計一次
						object.put("recommendFrequency", recommendFrequency); // 存入對方的推薦次數
						ParseInstallation.getCurrentInstallation().put(
								"recommendBoolean", true); // 只能推薦一次
						ParseInstallation.getCurrentInstallation()
								.saveInBackground(); // 存入
						object.saveInBackground(new SaveCallback() {
							@Override
							public void done(ParseException e) {
								if (e == null) {
									Toast toast = Toast.makeText(
											getApplicationContext(),
											R.string.confime_success,
											Toast.LENGTH_SHORT);
									toast.show();
								} else {
									e.printStackTrace();

									Toast toast = Toast.makeText(
											getApplicationContext(),
											R.string.confime_failed,
											Toast.LENGTH_SHORT);
									toast.show();
								}
							}
						});
						close.performClick(); // 關閉視窗
					}
				}
			});
		}
	}

	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

}
