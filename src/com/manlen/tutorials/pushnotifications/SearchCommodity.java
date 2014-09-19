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
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
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
		for (int i = 0; i < (tableSearch.length-1)*2; i++) { // 列

			TableRow row = new TableRow(this);

			// 第一列 圖片
			if (i % 2 == 0) {
				imgButton[i / 2] = new ImageButton(this);
				imgButton[i / 2].setImageResource(R.drawable.store
						+ picNumber[i / 2]);
				imgButton[i / 2].setBackgroundDrawable(null);
				imgButton[i / 2].setId(i / 2);
				imgButton[i / 2].setOnClickListener(imgButtonListen);
				row.addView(imgButton[i / 2], 0);
				// 第二列 商品名稱
				TextView tv2 = new TextView(this);
				tv2.setTextSize(15);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.BLACK);
				tv2.setText(tableSearch[i / 2][0] + " ");
				
				String namel = tableSearch[i / 2][0];
				String oprl = tableSearch[i / 2][1];
				String prl = tableSearch[i / 2][2];

				String ss = namel + "\n" + "原價:NT$" + oprl + "\n" + "團購價:NT$"
						+ prl;

				namel.length();// 產品名長度
				oprl.length(); // 原價數字長度
				prl.length();// 團購數字長度
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), namel.length() + 7,
						namel.length() + 7 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 刪除線
				msp.setSpan(new RelativeSizeSpan(2.0f), namel.length() + 15
						+ oprl.length(), namel.length() + 15 + oprl.length()
						+ prl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 兩倍大小
				msp.setSpan(
						new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
						namel.length() + 15 + oprl.length(), namel.length()
								+ 15 + oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 斜字體
				
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
