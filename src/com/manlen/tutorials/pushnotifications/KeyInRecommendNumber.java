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

		confime.setOnClickListener(new Button.OnClickListener() { // �T�{��J
			public void onClick(View v) {
				confimeRecommend();
			}
		});
		close.setOnClickListener(back); // ��������

	}

	/* ��J���� */
	void confimeRecommend() {
		recommendCol = 10; // user column ��
		gRecommend = recommendNumber.getText().toString();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_Installation");
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // ���X�ۤv�����˽X
		firstKey = ParseInstallation.getCurrentInstallation()
				.getInt("firstKey");

		if (gRecommend.equals(objectId)) {
			say.setText("��p�A�z��J�����ۤv�����˽X");
		} else {
			query.getInBackground(gRecommend, new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						say.setText("��p�A�z��J�����L�ı��˽X");

					} else {
						if (firstKey == 0) { // �Ĥ@�� �إ� recommendList ObjectID
							ParseObject recommendList = new ParseObject(
									"RecommendList"); // �إ�recommendList table
							number = recommendList.getInt("number"); // ��J�F�X��
							recommendList.put("installID", objectId); // ��JinstallID
							recommendList.put("number", number + 1); // ��J����+1
							recommendList.put("user" + number, gRecommend); // �إߤ@��user
																			// column������J�����˽X
							recommendList.saveInBackground(); // �s�JrecommendList
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
												say.setText("��p�A�z�w�g���˹L�����˽X");
												determine = false;
												break;
											} else {
												determine = true;
											}
										}
										if (determine) { // true����;false���Ʊ���,������
											number = recommendList
													.getInt("number"); // ��J�F�X��
											recommendList.put("number",
													number + 1); // ��J����+1
											recommendList.put("user" + number,
													gRecommend); // ������J�����˽X
											recommendList.saveInBackground(); // �s�JrecommendList
																				// table
										}
									} else {

									}
								}
							});

						}
						if (determine) {// true����;false���Ʊ���,������
							int recommendFrequency = object
									.getInt("recommendFrequency");

							recommendFrequency++; // �֭p�@��
							object.put("recommendFrequency", recommendFrequency); // �s�J��誺���˦���
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
