package com.manlen.tutorials.pushnotifications;

import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SearchCommodity extends FragmentActivity {
	private String searchName, sn[];
	private int pr[],sc[], id;
	List<ParseObject> searchObject;
	private Button close,searchBuy[], recommend[];
	private String tableSearch[][];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_commodity);
		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(back); // ��^
		Intent intent = getIntent();
		searchName = intent.getStringExtra("searchName"); // ���o�j�M�W��
		Log.i("search", searchName + "");

		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // ����table
			queryCommodity.whereContains("commodity", searchName);
			searchObject = queryCommodity.find();// �j�M����
			int sizeob = searchObject.size(); // �X�����
			sc = new int[sizeob]; // �ӫ~���O�}�C
			sn = new String[sizeob]; // �ӫ~�W�ٰ}�C
			pr = new int[sizeob]; // �ӫ~����}�C
			tableSearch = new String[sizeob + 1][6];
			tableSearch[0][0] = "�ӫ~�Ϥ�";
			tableSearch[0][1] = "�ӫ~����";
			tableSearch[0][2] = "����";
			tableSearch[0][3] = "�ʶR";
			tableSearch[0][4] = "����";
			tableSearch[0][5] = "";
			
			int i = 0;
			for (ParseObject search : searchObject) {
				// ���o���
				sc[i] = (int) search.getInt("storeClass");
				sn[i] = (String) search.get("commodity");
				pr[i] = (int) search.getInt("price");
				tableSearch[i + 1][1] = sn[i];
				tableSearch[i + 1][2] = Integer.toString(pr[i]);
				tableSearch[i + 1][5] = Integer.toString(sc[i]);
				Log.i("s1", sn[i] + "");
				Log.i("pr1", pr[i] + "");
				i++;
			}

		} catch (Exception e) {
			Log.i("error", "error");
		}
		init();
	}

	public void init() {

		TableLayout t1 = (TableLayout) findViewById(R.id.tableSearch);
		
		searchBuy = new Button[tableSearch.length];
		recommend = new Button[tableSearch.length];
		

		
		for (int i = 0; i < tableSearch.length; i++) { // �C

			TableRow row = new TableRow(this);
			for (int j = 0; j < tableSearch[i].length; j++) { // ��
				if (i == 0) {
					TextView tv = new TextView(this); // �]�w�Ĥ@�Ƥ�r
					tv.setTextSize(20);
					tv.setTextColor(Color.BLACK);
					tv.setText(tableSearch[i][j] + " ");
					row.addView(tv, j);
				} else {
					if (j == 0) { // ��Ϥ�
						int k =Integer.parseInt(tableSearch[i][5]);
						ImageView iv = new ImageView(this);
						iv.setImageResource(R.drawable.store+k+ i);
						row.addView(iv, j);
					} else if (j > 0 && j < tableSearch[i].length - 3) { // �]�w��L�j�M�쪺��r
						TextView tv = new TextView(this);
						tv.setTextSize(20);
						tv.setTextColor(Color.BLACK);
						tv.setText(tableSearch[i][j] + " ");
						row.addView(tv, j);
					} else if (j == tableSearch[i].length - 3) { // �]�w�ʶR���s

						searchBuy[i] = new Button(this);
						searchBuy[i].setTextColor(Color.WHITE);
						searchBuy[i].setBackgroundResource(R.drawable.btn_black);
						searchBuy[i].setText("�ʶR");
						searchBuy[i].setId(i);
						searchBuy[i].setOnClickListener(bb); // �ʶR�ʧ@
						row.addView(searchBuy[i], j);
					} else if (j == tableSearch[i].length - 2) { // �]�w���˫��s

						recommend[i] = new Button(this);
						recommend[i].setTextColor(Color.WHITE);
						recommend[i]
								.setBackgroundResource(R.drawable.btn_black);
						recommend[i].setText("����");
						recommend[i].setId(i);
						recommend[i].setOnClickListener(rr); // ���˰ʧ@
						row.addView(recommend[i], j);
					}
				}

			}
			t1.addView(row);
		}

	}

	// �ʶR
	private OnClickListener bb = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			Log.i("id", id+"");
			buy();

		}
	};

	void buy() {
		Intent intent = new Intent(this, BuyConfirm.class);
		intent.putExtra("commodityName", sn[id-1]);
		intent.putExtra("price", pr[id-1]);
		startActivity(intent);
	}
	
	private OnClickListener rr = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			shareDialog();
		}
	};
	// ����app
		void shareDialog() {

			String shareText = tableSearch[id][1]+"�u��"+tableSearch[id][2]+"���˵��A ";
			// Uri imageUri = Uri.parse("android.resource://" + getPackageName() +
			// "/drawable/" + "android");
			// Log.i("imageUri:", imageUri + "");
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
			shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			// shareIntent.setType("image/png");
			// shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
			startActivity(Intent.createChooser(shareIntent, "����"));
		}
	// ��^
		private OnClickListener back = new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
}
