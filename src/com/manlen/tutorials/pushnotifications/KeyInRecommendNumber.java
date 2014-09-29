package com.manlen.tutorials.pushnotifications;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Typeface;
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
	private String objectId, gRecommend, myRecommendNumber;
	private boolean determine = true;
	List<ParseObject> results;
	Typeface fontch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		close = (Button) findViewById(R.id.close);
		confime = (Button) findViewById(R.id.confime);
		say = (TextView) findViewById(R.id.say);
		recommendNumber = (EditText) findViewById(R.id.recommendNumber);

		close.setTypeface(fontch);
		confime.setTypeface(fontch);
		say.setTypeface(fontch);
		recommendNumber.setTypeface(fontch);

		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // 取出自己的ID
		Log.i("objectId:::::::", objectId + "");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("RecommendList");
		query.whereEqualTo("installID", objectId);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					myRecommendNumber = object.getString("myRecommendNumber"); // 取的我的推薦碼
					Log.i("myRecommendNumber111111", myRecommendNumber + "");
				} else {

				}
			}
		});

		confime.setOnClickListener(new Button.OnClickListener() { // 確認輸入
			public void onClick(View v) {
				confimeRecommend();
			}
		});
		close.setOnClickListener(back); // 關閉視窗

	}

	/* 輸入驗證 */
	void confimeRecommend() {

		gRecommend = recommendNumber.getText().toString(); // 輸入的推薦碼
		Log.i("gRecommend:::::::::", gRecommend + "");
		Log.i("myRecommendNumber::", myRecommendNumber + "");
		if (gRecommend.equals(myRecommendNumber)) {
			say.setText("抱歉，您輸入的為自己的推薦碼");
		} else {
			ParseQuery<ParseObject> queryOther = ParseQuery // 搜尋有無此推薦碼
					.getQuery("RecommendList");
			queryOther.whereEqualTo("myRecommendNumber", gRecommend);
			queryOther.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						say.setText("抱歉，您輸入的為無效推薦碼");

					} else {

						ParseQuery<ParseObject> queryMyself = ParseQuery
								.getQuery("RecommendList"); // 搜尋自己輸入過的推薦碼
						queryMyself.whereEqualTo("installID", objectId);

						// 判斷是否已加入過
						try {
							results = queryMyself.find();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						ParseObject object2 = results.get(0);
						JSONArray number = object2
								.getJSONArray("recommendUser");

						if (number != null) {
							Log.i("number", number + "");
							for (int i = 0; i < number.length(); i++) {
								String getNumber;
								try {
									getNumber = number.getString(i);// 取出數字
									String gPN = getNumber.replaceAll("[\\D]",
											"");
									Log.i("gPN", gPN + "");
									if (gRecommend.equals(gPN)) {
										// 驗證已經輸入過
										say.setText("抱歉，您已經推薦過此推薦碼");
										determine = false;
									}

								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
						if (determine) {// true執行;false重複推薦,不執行
							queryMyself
									.getFirstInBackground(new GetCallback<ParseObject>() {
										public void done(
												ParseObject objectMyself,
												ParseException e) {
											if (e == null) {
												objectMyself.add(
														"recommendUser",
														Arrays.asList(gRecommend));// 輸入推薦號碼
												objectMyself.saveInBackground();

											}
										}
									});
							int recommendFrequency = object
									.getInt("recommendFrequency");
							Log.i("recommendFrequency", recommendFrequency + "");
							recommendFrequency++;
							object.put("recommendFrequency", recommendFrequency);// 存入推薦次數在對方資料庫
							object.saveInBackground();
							Toast.makeText(getBaseContext(), R.string.confime_success,
									Toast.LENGTH_SHORT).show();
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
