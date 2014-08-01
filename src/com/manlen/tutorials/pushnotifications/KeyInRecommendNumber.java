package com.manlen.tutorials.pushnotifications;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
	private String objectId, gRecommend;
	private int firstKey, number, recommendCol;
	private boolean determine;

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
		recommendCol = 10; // user column 數
		gRecommend = recommendNumber.getText().toString();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_Installation");
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // 取出自己的推薦碼
		firstKey = ParseInstallation.getCurrentInstallation()
				.getInt("firstKey");

		if (gRecommend.equals(objectId)) {
			say.setText("抱歉，您輸入的為自己的推薦碼");
		} else {
			query.getInBackground(gRecommend, new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						say.setText("抱歉，您輸入的為無效推薦碼");

					} else {
						if (firstKey == 0) { // 第一次 建立 recommendList ObjectID
							ParseObject recommendList = new ParseObject(
									"RecommendList"); // 建立recommendList table
							number = recommendList.getInt("number"); // 輸入了幾次
							recommendList.put("installID", objectId); // 輸入installID
							recommendList.put("number", number + 1); // 輸入次數+1
							recommendList.put("user" + number, gRecommend); // 建立一個user
																			// column紀錄輸入的推薦碼
							recommendList.saveInBackground(); // 存入recommendList
																// table
							ParseInstallation.getCurrentInstallation().put(
									"firstKey", firstKey + 1);
							ParseInstallation.getCurrentInstallation()
									.saveInBackground();

						} else {
							ParseQuery<ParseObject> query = ParseQuery
									.getQuery("RecommendList");
							query.whereEqualTo("installID", objectId);
							query.getFirstInBackground(new GetCallback<ParseObject>() {
								public void done(ParseObject recommendList,
										ParseException e) {
									if (e == null) {

										for (int i = 0; i < recommendCol; i++) {
											if (gRecommend.equals(recommendList
													.get("user" + i))) {
												say.setText("抱歉，您已經推薦過此推薦碼");
												determine = false;
												break;
											} else {
												determine = true;
											}
										}
										if (determine) { // true執行;false重複推薦,不執行
											number = recommendList
													.getInt("number"); // 輸入了幾次
											recommendList.put("number",
													number + 1); // 輸入次數+1
											recommendList.put("user" + number,
													gRecommend); // 紀錄輸入的推薦碼
											recommendList.saveInBackground(); // 存入recommendList
																				// table
										}
									} else {

									}
								}
							});

						}
						if (determine) {// true執行;false重複推薦,不執行
							int recommendFrequency = object
									.getInt("recommendFrequency");

							recommendFrequency++; // 累計一次
							object.put("recommendFrequency", recommendFrequency); // 存入對方的推薦次數
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
