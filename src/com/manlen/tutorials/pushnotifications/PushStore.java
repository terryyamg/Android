package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PushStore extends FragmentActivity {
	private String tableData[][], location;
	private int id;
	private ImageButton imgButton;
	private TextView output;
	public ProgressDialog dialog = null;
	List<ParseObject> ob;
	Typeface fontch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_info);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		output = (TextView) findViewById(R.id.output);
		output.setTypeface(fontch);
		output.setTextSize(25);
		
		location = ParseInstallation.getCurrentInstallation().getString(
				"location"); // ���� �ڪ���m
		Log.i("location", location + "");
		if (location == null) {
			output.setText("��p�A�Х��e���]�w�}�үS���ө��D�ʳq��"+"\n "+"���o�z����m");
		}
		try {
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"MarkerInfo");
			query.whereContains("region", location);
			query.orderByDescending("push");
			ob = query.find();
			int size = ob.size(); // �X�����
			String[] storeName = new String[size];
			String[] storeLocation = new String[size];
			String[] storeDetail = new String[size];
			int[] picNumber = new int[size];
			int[] push = new int[size];

			tableData = new String[size + 1][5];
			int i = 0;
			for (ParseObject search : ob) {
				// ���o���
				storeName[i] = (String) search.get("storeName");
				storeLocation[i] = (String) search.get("storeLocation");
				storeDetail[i] = (String) search.get("storeDetail");
				picNumber[i] = (int) search.getInt("picNumber");
				push[i] = (int) search.getInt("push");
				// ��JtableData�r��
				tableData[i][0] = storeName[i];
				tableData[i][1] = storeLocation[i];
				tableData[i][2] = storeDetail[i];
				tableData[i][3] = Integer.toString(picNumber[i]);
				tableData[i][4] = Integer.toString(push[i]);
				Log.i("storeName", storeName[i] + "");
				Log.i("storeLoaction", storeLocation[i] + "");
				Log.i("storeDetail", storeDetail[i] + "");
				Log.i("picNumber", picNumber[i] + "");
				Log.i("push", push[i] + "");
				i++;
			}

			TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);

			// �ƪ�
			for (int j = 0; j < (tableData.length - 1); j++) { // �C
				TableRow row = new TableRow(this);
				if (tableData[j][4] != null) { // ���˦���
					imgButton = new ImageButton(this);
					imgButton.setImageResource(R.drawable.market
							+ Integer.parseInt(tableData[j][3]));
					imgButton.setId(j);
					imgButton.setBackgroundDrawable(null);
					imgButton.setOnClickListener(imgButtonListen);
					row.addView(imgButton, 0);
				}
				t1.addView(row);
			}
		} catch (Exception e) {
			Log.i("Exception", e + "");
		}
	}

	private OnClickListener imgButtonListen = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			detail();
		}
	};

	void detail() {
		progress();
		Intent intent = new Intent(this, DetailMarkerInfo.class);
		intent.putExtra("storeName", tableData[id][0]);
		intent.putExtra("storeLocation", tableData[id][1]);
		intent.putExtra("storeDetail", tableData[id][2]);
		intent.putExtra("picNumber", tableData[id][3]);
		startActivity(intent);
	}

	void progress() {
		dialog = ProgressDialog.show(this, "Ū����", "�еy��...", true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dialog.dismiss();
				}
			}
		}).start();
	}
}
