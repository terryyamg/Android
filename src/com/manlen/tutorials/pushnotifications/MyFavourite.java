package com.manlen.tutorials.pushnotifications;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MyFavourite extends Fragment {
	private String objectId, tableSearch[][], sn[];
	private int opr[], pr[], id, picNumber[], temporary[];
	private ImageButton imgButton[], delButton[];
	Typeface fontch;
	List<ParseObject> results, searchObject;
	public ProgressDialog dialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 字型 */
		fontch = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/wt001.ttf");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView(inflater, container);
	}

	private View initView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.my_favourite, container, false);

		// 取出我的最愛商品圖片數字
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // 取出自己的id
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MyFavourite");
		query.whereEqualTo("installID", objectId);

		try {

			results = query.find();
			ParseObject object = results.get(0);
			JSONArray pic = object.getJSONArray("picNumber");
			sn = new String[pic.length()]; // 商品名稱陣列
			opr = new int[pic.length()]; // 原價陣列
			pr = new int[pic.length()]; // 團購價陣列
			picNumber = new int[pic.length()]; // 第幾張圖
			tableSearch = new String[pic.length() + 1][4];
			for (int i = 0; i < pic.length(); i++) {
				String getPicNumber;
				try {
					getPicNumber = pic.getString(i); // 取出數字
					int gPN = Integer.parseInt(getPicNumber.replaceAll("[\\D]",
							""));
					// 搜尋商品
					ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
							"Commodity"); // 哪個table
					queryCommodity.whereEqualTo("picNumber", gPN);
					searchObject = queryCommodity.find();// 搜尋物件

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

					}

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// loop and add it to array or arraylist
			}

			// 排版
			TableLayout t1 = (TableLayout) view.findViewById(R.id.tablay2);
			Log.i("tableSearch.length", tableSearch.length + "");
			imgButton = new ImageButton[tableSearch.length];
			delButton = new ImageButton[tableSearch.length];
			for (int i = 0; i < (tableSearch.length - 1) * 2; i++) { // 列

				TableRow row = new TableRow(getActivity());

				// 第一列 圖片
				if (i % 2 == 0) {
					imgButton[i / 2] = new ImageButton(getActivity());
					imgButton[i / 2].setImageResource(R.drawable.storem
							+ picNumber[i / 2]);
					imgButton[i / 2].setBackgroundDrawable(null);
					imgButton[i / 2].setId(i / 2);
					imgButton[i / 2].setOnClickListener(imgButtonListen);
					row.addView(imgButton[i / 2], 0);
					// 第二列 商品名稱
					TextView tv2 = new TextView(getActivity());
					tv2.setTextSize(15);
					tv2.setTypeface(fontch);
					tv2.setTextColor(Color.BLACK);
					tv2.setText(tableSearch[i / 2][0] + " ");

					String namel = tableSearch[i / 2][0];
					String oprl = tableSearch[i / 2][1];
					String prl = tableSearch[i / 2][2];

					String ss = namel + "\n" + "原價:NT$" + oprl + "\n"
							+ "團購價:NT$" + prl;

					namel.length();// 產品名長度
					oprl.length(); // 原價數字長度
					prl.length();// 團購數字長度
					Spannable msp = new SpannableString(ss);
					msp.setSpan(new StrikethroughSpan(), namel.length() + 7,
							namel.length() + 7 + oprl.length(),
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 刪除線
					msp.setSpan(new RelativeSizeSpan(2.0f), namel.length() + 15
							+ oprl.length(),
							namel.length() + 15 + oprl.length() + prl.length(),
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 兩倍大小
					msp.setSpan(new StyleSpan(
							android.graphics.Typeface.BOLD_ITALIC),
							namel.length() + 15 + oprl.length(), namel.length()
									+ 15 + oprl.length() + prl.length(),
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 斜字體

					tv2.setText(msp);
					row.addView(tv2, 1);
					// 移除按鈕
					delButton[i / 2] = new ImageButton(getActivity());
					delButton[i / 2].setImageResource(R.drawable.delete);
					delButton[i / 2].setBackgroundDrawable(null);
					delButton[i / 2].setId(i / 2);
					delButton[i / 2].setOnClickListener(delButtonListen);
					row.addView(delButton[i / 2], 2);
				} else if (i % 2 == 1) {
					ImageView iv = new ImageView(getActivity());
					iv.setImageResource(R.drawable.dividers);
					TableRow.LayoutParams rowSpanLayout = new TableRow.LayoutParams(
							TableRow.LayoutParams.FILL_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					rowSpanLayout.span = 3;
					row.addView(iv, rowSpanLayout);
				}
				t1.addView(row);
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return view;
	}

	private OnClickListener imgButtonListen = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			goToListCommodity();

		}
	};

	void goToListCommodity() {
		progress();
		Intent intent = new Intent(getActivity(), ListCommodity.class);
		intent.putExtra("storeName", sn[id]);
		intent.putExtra("orientPrice", opr[id]);
		intent.putExtra("price", pr[id]);
		intent.putExtra("picNumber", id + 1);

		startActivity(intent);
	}

	private OnClickListener delButtonListen = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();

			ParseQuery<ParseObject> query = ParseQuery.getQuery("MyFavourite");
			query.whereEqualTo("installID", objectId);
			try {
				results = query.find();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ParseObject object = results.get(0);
			JSONArray pic = object.getJSONArray("picNumber");
			temporary = new int[pic.length() - 1];
			int k = 0;
			for (int i = 0; i < pic.length(); i++) {
				String getPicNumber;

				try {
					getPicNumber = pic.getString(i);// 取出數字
					int gPN = Integer.parseInt(getPicNumber.replaceAll("[\\D]",
							""));
					Log.i("picNumber[id]", picNumber[id] + "");
					Log.i("gPN", gPN + "");
					if (picNumber[id] != gPN) { // 相等不存，不相等暫存
						Log.i("ooook", i + "");
						Log.i("kkkkk", k + "");
						temporary[k] = gPN;
						k++;
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			query.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject myFavouriteList, ParseException e) {
					if (e == null) {
						myFavouriteList.remove("picNumber"); // 移除所有
						Log.i("temporary.length1", temporary.length + "");
						for (int i = 0; i < temporary.length; i++) {
							Log.i("temporary.length2", temporary.length + "");
							Log.i("temporary[i]", temporary[i] + "");

							myFavouriteList.add("picNumber",
									Arrays.asList(temporary[i]));// 輸入圖片號碼

						}
						myFavouriteList.saveInBackground(); // 存入MyFavorite
						// table
					} else {
						Log.i("eeeeeeeeeeeee", e + "");
					}
				}
			});

			Toast.makeText(getActivity().getBaseContext(),
					"移除" + tableSearch[id][0] + "!!", Toast.LENGTH_SHORT)
					.show();
			delCommodity();

		}
	};

	void delCommodity() {
		progress();
		Intent intent = new Intent(getActivity(), NavigationDrawer.class);
		startActivity(intent);
	}

	void progress() {
		dialog = ProgressDialog.show(getActivity(), "讀取中", "請稍後...", true);
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
