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
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		close = (Button) findViewById(R.id.close);
		confime = (Button) findViewById(R.id.confime);
		say = (TextView) findViewById(R.id.say);
		recommendNumber = (EditText) findViewById(R.id.recommendNumber);

		close.setTypeface(fontch);
		confime.setTypeface(fontch);
		say.setTypeface(fontch);
		recommendNumber.setTypeface(fontch);

		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // ���X�ۤv��ID
		Log.i("objectId:::::::", objectId + "");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("RecommendList");
		query.whereEqualTo("installID", objectId);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					myRecommendNumber = object.getString("myRecommendNumber"); // �����ڪ����˽X
					Log.i("myRecommendNumber111111", myRecommendNumber + "");
				} else {

				}
			}
		});

		confime.setOnClickListener(new Button.OnClickListener() { // �T�{��J
			public void onClick(View v) {
				confimeRecommend();
			}
		});
		close.setOnClickListener(back); // ��������

	}

	/* ��J���� */
	void confimeRecommend() {

		gRecommend = recommendNumber.getText().toString(); // ��J�����˽X
		Log.i("gRecommend:::::::::", gRecommend + "");
		Log.i("myRecommendNumber::", myRecommendNumber + "");
		if (gRecommend.equals(myRecommendNumber)) {
			say.setText("��p�A�z��J�����ۤv�����˽X");
		} else {
			ParseQuery<ParseObject> queryOther = ParseQuery // �j�M���L�����˽X
					.getQuery("RecommendList");
			queryOther.whereEqualTo("myRecommendNumber", gRecommend);
			queryOther.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						say.setText("��p�A�z��J�����L�ı��˽X");

					} else {

						ParseQuery<ParseObject> queryMyself = ParseQuery
								.getQuery("RecommendList"); // �j�M�ۤv��J�L�����˽X
						queryMyself.whereEqualTo("installID", objectId);

						// �P�_�O�_�w�[�J�L
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
									getNumber = number.getString(i);// ���X�Ʀr
									String gPN = getNumber.replaceAll("[\\D]",
											"");
									Log.i("gPN", gPN + "");
									if (gRecommend.equals(gPN)) {
										// ���Ҥw�g��J�L
										say.setText("��p�A�z�w�g���˹L�����˽X");
										determine = false;
									}

								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
						if (determine) {// true����;false���Ʊ���,������
							queryMyself
									.getFirstInBackground(new GetCallback<ParseObject>() {
										public void done(
												ParseObject objectMyself,
												ParseException e) {
											if (e == null) {
												objectMyself.add(
														"recommendUser",
														Arrays.asList(gRecommend));// ��J���˸��X
												objectMyself.saveInBackground();

											}
										}
									});
							int recommendFrequency = object
									.getInt("recommendFrequency");
							Log.i("recommendFrequency", recommendFrequency + "");
							recommendFrequency++;
							object.put("recommendFrequency", recommendFrequency);// �s�J���˦��Ʀb����Ʈw
							object.saveInBackground();
							Toast.makeText(getBaseContext(), R.string.confime_success,
									Toast.LENGTH_SHORT).show();
							close.performClick(); // ��������
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
