package com.manlen.tutorials.pushnotifications;

import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SearchCommodity extends FragmentActivity {
	private String searchName, sn[];
	private int opr[],pr[], id, picNumber[];
	List<ParseObject> searchObject;
	private String tableSearch[][];
	private ImageButton imgButton[];
	Typeface fontch;
	public ProgressDialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_commodity);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		Intent intent = getIntent();
		searchName = intent.getStringExtra("searchName"); // ���o�j�M�W��
		Log.i("search", searchName + "");

		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // ����table
			queryCommodity.whereContains("commodity", searchName);
			queryCommodity.orderByAscending("picNumber");
			searchObject = queryCommodity.find();// �j�M����
			int sizeob = searchObject.size(); // �X�����
			sn = new String[sizeob]; // �ӫ~�W�ٰ}�C
			opr = new int[sizeob]; // ����}�C
			pr = new int[sizeob]; // ���ʻ��}�C
			picNumber = new int[sizeob]; // �ĴX�i��
			tableSearch = new String[sizeob + 1][4];

			int i = 0;
			for (ParseObject search : searchObject) {
				// ���o���
				sn[i] = (String) search.get("commodity");
				opr[i] = (int) search.getInt("originalPrice");
				pr[i] = (int) search.getInt("price");
				picNumber[i] = (int) search.getInt("picNumber");

				tableSearch[i][0] = sn[i];
				tableSearch[i][1] = Integer.toString(opr[i]);
				tableSearch[i][2] = Integer.toString(pr[i]);
				tableSearch[i][3] = Integer.toString(picNumber[i]);
				
				i++;
			}

		} catch (Exception e) {
			Log.i("error", "error");
		}
		init();
	}

	public void init() {

		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);
		imgButton = new ImageButton[tableSearch.length];
		for (int i = 0; i < (tableSearch.length-1)*2; i++) { // �C

			TableRow row = new TableRow(this);

			// �Ĥ@�C �Ϥ�
			if (i % 2 == 0) {
				imgButton[i / 2] = new ImageButton(this);
				imgButton[i / 2].setImageResource(R.drawable.store
						+ picNumber[i / 2]);
				imgButton[i / 2].setBackgroundDrawable(null);
				imgButton[i / 2].setId(i / 2);
				imgButton[i / 2].setOnClickListener(imgButtonListen);
				row.addView(imgButton[i / 2], 0);
				// �ĤG�C �ӫ~�W��
				TextView tv2 = new TextView(this);
				tv2.setTextSize(15);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.BLACK);
				tv2.setText(tableSearch[i / 2][0] + " ");
				
				String namel = tableSearch[i / 2][0];
				String oprl = tableSearch[i / 2][1];
				String prl = tableSearch[i / 2][2];

				String ss = namel + "\n" + "���:NT$" + oprl + "\n" + "���ʻ�:NT$"
						+ prl;

				namel.length();// ���~�W����
				oprl.length(); // ����Ʀr����
				prl.length();// ���ʼƦr����
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), namel.length() + 7,
						namel.length() + 7 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �R���u
				msp.setSpan(new RelativeSizeSpan(2.0f), namel.length() + 15
						+ oprl.length(), namel.length() + 15 + oprl.length()
						+ prl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �⭿�j�p
				msp.setSpan(
						new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
						namel.length() + 15 + oprl.length(), namel.length()
								+ 15 + oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �צr��
				
				tv2.setText(msp);
				row.addView(tv2, 1);
			} else if (i % 2 == 1) {
				ImageView iv = new ImageView(this);
				iv.setImageResource(R.drawable.dividers);
				TableRow.LayoutParams rowSpanLayout = new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.WRAP_CONTENT);
				rowSpanLayout.span = 2;
				row.addView(iv, rowSpanLayout);
			}
			t1.addView(row);
		}

	}

	private OnClickListener imgButtonListen = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			goToListCommodity();

		}
	};

	void goToListCommodity() {
		progress();
		Intent intent = new Intent(this, ListCommodity.class);
		intent.putExtra("storeName", sn[id]);
		intent.putExtra("orientPrice", opr[id]);
		intent.putExtra("price", pr[id]);
		intent.putExtra("picNumber", id + 1);

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
