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

		confime.setOnClickListener(new Button.OnClickListener() { // �T�{��J
			public void onClick(View v) {
				confimeRecommend();
			}
		});
		close.setOnClickListener(back); // ��������

	}

	/* ��J���� */
	void confimeRecommend() {

		String gRecommend = recommendNumber.getText().toString();
		Log.i("gRecommend:", gRecommend + "");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_Installation");
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // ���X�ۤv�����˽X
		recommendBoolean = ParseInstallation.getCurrentInstallation()
				.getBoolean("recommendBoolean"); // ���X�ۤv�O�_��J�L���˽X
		if (gRecommend.equals(objectId)) {
			say.setText("��p�A�z��J�����ۤv�����˽X");
		} else if (recommendBoolean == true) {
			say.setText("��p�A�z�w�g���\���˹L�F");
		} else {
			query.getInBackground(gRecommend, new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						Log.i("f:", e + "");
						say.setText("��p�A�z��J�����L�ı��˽X");

					} else {
						int recommendFrequency = object
								.getInt("recommendFrequency");
						Log.i("recommendNumber:", recommendFrequency + "");

						recommendFrequency++; // �֭p�@��
						object.put("recommendFrequency", recommendFrequency); // �s�J��誺���˦���
						ParseInstallation.getCurrentInstallation().put(
								"recommendBoolean", true); // �u����ˤ@��
						ParseInstallation.getCurrentInstallation()
								.saveInBackground(); // �s�J
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
			});
		}
	}

	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

}
