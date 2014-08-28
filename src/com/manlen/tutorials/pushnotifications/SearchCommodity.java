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
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");
		Intent intent = getIntent();
		searchName = intent.getStringExtra("searchName"); // 取得搜尋名稱
		Log.i("search", searchName + "");

		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // 哪個table
			queryCommodity.whereContains("commodity", searchName);
			queryCommodity.orderByAscending("picNumber");
			searchObject = queryCommodity.find();// 搜尋物件
			int sizeob = searchObject.size(); // 幾筆資料
			sn = new String[sizeob]; // 商品名稱陣列
			opr = new int[sizeob]; // 原價陣列
			pr = new int[sizeob]; // 團購價陣列
			picNumber = new int[sizeob]; // 第幾張圖
			tableSearch = new String[sizeob + 1][4];

			int i = 0;
			for (ParseObject search : searchObject) {
				// 取得資料
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
		for (int i = 0; i < (tableSearch.length-1)*3; i++) { // 列

			TableRow row = new TableRow(this);

			// 第一列 圖片
			if (i % 3 == 0) {
				imgButton[i / 3] = new ImageButton(this);
				imgButton[i / 3].setImageResource(R.drawable.store
						+ picNumber[i / 3]);
				imgButton[i / 3].setBackgroundDrawable(null);
				imgButton[i / 3].setId(i / 3);
				imgButton[i / 3].setOnClickListener(imgButtonListen);
				row.addView(imgButton[i / 3], 0);
			} else if (i % 3 == 1) {
				// 第二列 商品名稱
				TextView tv2 = new TextView(this);
				tv2.setTextSize(15);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.WHITE);
				tv2.setText(tableSearch[i / 3][0] + " ");
				row.addView(tv2, 0);
			} else if (i % 3 == 2) {
				// 第三列 價格
				TextView tv3 = new TextView(this);
				tv3.setTextSize(15);
				tv3.setTypeface(fontch);
				tv3.setTextColor(Color.WHITE);
				String oprl = tableSearch[i / 3][1];
				String prl = tableSearch[i / 3][2];
				String ss = "原價:$" + oprl + "團購價:$" + prl;
				oprl.length(); // 原價數字長度
				prl.length();// 團購數字長度
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), 4, 4 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 刪除線
				msp.setSpan(new RelativeSizeSpan(2.0f), 9 + oprl.length(), 9
						+ oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 兩倍大小
				tv3.setText(msp);
				row.addView(tv3, 0);
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
		dialog = ProgressDialog.show(this, "讀取中", "請稍後...", true);
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
