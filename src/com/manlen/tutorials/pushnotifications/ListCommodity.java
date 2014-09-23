package com.manlen.tutorials.pushnotifications;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
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

public class ListCommodity extends FragmentActivity {
	private Button buyButton, recommend, more, favourite;
	private ImageView img;
	private int opr, pr, pn, sellNumber, favouriteKey;
	private String store, userTel, myTel, sn, si, storeClass, objectId;
	public ProgressDialog dialog = null;
	private boolean picIndex1;
	List<ParseObject> searchObject, ob, results;
	Typeface fontch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_commodity);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		try {
			// tab1 取得商品資料
			Intent intent = getIntent();
			sn = intent.getStringExtra("storeName"); // 取得商品名稱
			opr = intent.getIntExtra("orientPrice", 0); // 取得原始價格
			pr = intent.getIntExtra("price", 0); // 取得價格
			pn = intent.getIntExtra("picNumber", 0); // 取得圖片號碼
			// tab2 抓手機號碼
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // 哪個table
			queryCommodity.whereEqualTo("commodity", sn); // 條件
			searchObject = queryCommodity.find();// 搜尋物件
			for (ParseObject search : searchObject) {
				// 取得資料
				si = (String) search.get("introduction"); // 商品介紹
				store = (String) search.get("store"); // 店名
				storeClass = (String) search.get("storeClass"); // 店家類別
				sellNumber = (int) search.getInt("sellNumber"); // 賣出數量
			}

			SharedPreferences preferences = getApplicationContext()
					.getSharedPreferences("Android",
							android.content.Context.MODE_PRIVATE);

			myTel = preferences.getString("myTel", userTel);
			if (myTel == null) {
				myTel = "0"; // 抓不到手機號碼
			}
		} catch (Exception e) {
			myTel = "0"; // 抓不到手機號碼
		}

		// 排版
		setTable();

	}

	/* tab1 */

	void setTable() {
		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);

		// 排版
		buyButton = new Button(this);
		recommend = new Button(this);
		more = new Button(this);
		favourite = new Button(this);
		for (int i = 0; i < 8; i++) { // 列
			TableRow row = new TableRow(this);
			TableRow.LayoutParams rowSpanLayout = new TableRow.LayoutParams(
					200, 200);
			rowSpanLayout.span = 2;
			TableRow.LayoutParams rowSpanLayout2 = new TableRow.LayoutParams(
					30, 30);
			rowSpanLayout2.topMargin = 20;
			// 第一列 圖片
			switch (i) {
			case 0: // 第一列 商品圖片
				img = new ImageView(this);
				img.setImageResource(R.drawable.store + pn);
				img.setLayoutParams(rowSpanLayout);
				row.addView(img, 0);
				break;
			case 1: // 第二列 商品名稱
				TextView tv2 = new TextView(this);
				tv2.setTextSize(20);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.BLACK);
				tv2.setText(store + "\n" + sn + " ");
				row.addView(tv2, 0);
				break;
			case 2: // 賣出數量
				TextView tv3 = new TextView(this);
				tv3.setTextSize(20);
				tv3.setTypeface(fontch);
				tv3.setTextColor(Color.BLACK);
				tv3.setText("已賣出" + sellNumber + "份");
				row.addView(tv3, 0);
				favourite = new Button(this);
				favourite.setBackgroundResource(R.drawable.fav);
				favourite.setLayoutParams(rowSpanLayout2);
				favourite.setId(i);
				favourite.setOnClickListener(ff); // 購買動作
				row.addView(favourite, 1);
				break;
			case 3: // 說明
				TextView tv4 = new TextView(this);
				tv4.setTextSize(12);
				tv4.setTypeface(fontch);
				tv4.setTextColor(Color.BLACK);
				try {
					String showSi = "";
					String[] siSplit = si.split(":");
					for (int k = 0; k < siSplit.length; k++) {
						showSi = showSi + siSplit[k] + "\n";
					}

					tv4.setText(showSi);
					row.addView(tv4, 0);
				} catch (Exception e) {

				}
				break;
			case 4: // 價格
				TextView tv5 = new TextView(this);
				tv5.setTextSize(20);
				tv5.setTypeface(fontch);
				tv5.setTextColor(Color.BLACK);
				String oprl = Integer.toString(opr);
				String prl = Integer.toString(pr);
				String ss = "原價:NT$" + oprl + "\n團購價:NT$" + prl;
				oprl.length(); // 原價數字長度
				prl.length();// 團購數字長度
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), 6, 6 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 刪除線
				msp.setSpan(new RelativeSizeSpan(2.0f), 14 + oprl.length(), 14
						+ oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 兩倍大小
				msp.setSpan(
						new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
						14 + oprl.length(), 14 + oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 斜字體
				tv5.setText(msp);
				row.addView(tv5, 0);
				break;
			case 5:
				buyButton = new Button(this);
				buyButton.setTypeface(fontch);
				buyButton.setTextColor(Color.BLACK);
				buyButton.setBackgroundResource(R.drawable.list_commodity);
				buyButton.setText("NT$" + pr + " 馬上加入優惠");
				buyButton.setId(i);
				buyButton.setOnClickListener(bb); // 購買動作
				row.addView(buyButton, 0);
				break;
			case 6:
				recommend = new Button(this);
				recommend.setTypeface(fontch);
				recommend.setTextColor(Color.BLACK);
				recommend.setBackgroundResource(R.drawable.list_commodity);
				recommend.setText("推薦給好友");
				recommend.setId(i);
				recommend.setOnClickListener(rr); // 推薦動作
				row.addView(recommend, 0);
				break;
			case 7:
				more = new Button(this);
				more.setTypeface(fontch);
				more.setTextColor(Color.BLACK);
				more.setBackgroundResource(R.drawable.list_commodity);
				more.setText("觀看更多店家商品");
				more.setId(i);
				more.setOnClickListener(mm); // 推薦動作
				row.addView(more, 0);
				break;

			}
			t1.addView(row);
		}

	}

	// 加入收藏
	private OnClickListener ff = new OnClickListener() {
		public void onClick(View v) {
			addMyFavourite();
		}
	};

	void addMyFavourite() {
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // 取出自己的id
		favouriteKey = ParseInstallation.getCurrentInstallation().getInt(
				"favouriteKey"); // 取得是否建立過指標
		if (favouriteKey == 0) { // 第一次 建立 recommendList ObjectID
			ParseObject myFavourite = new ParseObject("MyFavourite"); // 建立MyFavorite
																		// table
			myFavourite.put("installID", objectId); // 輸入installID
			myFavourite.add("picNumber", Arrays.asList(pn));// 輸入圖片號碼
			myFavourite.saveInBackground(); // 存入MyFavorite table

			ParseInstallation.getCurrentInstallation().put("favouriteKey",
					favouriteKey + 1);
			ParseInstallation.getCurrentInstallation().saveInBackground();
			Toast.makeText(getBaseContext(), "成功加入我的收藏!", Toast.LENGTH_SHORT)
					.show();
		} else {

			ParseQuery<ParseObject> query = ParseQuery.getQuery("MyFavourite");
			query.whereEqualTo("installID", objectId);

			// 判斷是否已加入過
			try {
				results = query.find();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ParseObject object = results.get(0);
			JSONArray pic = object.getJSONArray("picNumber");
			for (int i = 0; i < pic.length(); i++) {
				String getPicNumber;
				picIndex1 = true;
				try {
					getPicNumber = pic.getString(i);// 取出數字
					int gPN = Integer.parseInt(getPicNumber.replaceAll("[\\D]",
							""));
					if (pn == gPN) { // 有輸入過
						picIndex1 = false;
					}

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// true:輸入;false:不輸入
			if (picIndex1) {
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject myFavouriteList,
							ParseException e) {
						if (e == null) {
							myFavouriteList.add("picNumber", Arrays.asList(pn));// 輸入圖片號碼
							myFavouriteList.saveInBackground(); // 存入MyFavorite
																// table

						}
					}
				});
				Toast.makeText(getBaseContext(), "成功加入我的收藏!",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getBaseContext(), "此商品已在我的收藏!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	// 購買
	private OnClickListener bb = new OnClickListener() {
		public void onClick(View v) {
			progress();
			buy();
		}
	};

	void buy() {
		Intent intent = new Intent(this, BuyConfirm.class);
		intent.putExtra("commodityName", sn);
		intent.putExtra("price", pr);
		intent.putExtra("store", store);
		intent.putExtra("storeClass", storeClass);
		startActivity(intent);
	}

	private OnClickListener rr = new OnClickListener() {
		public void onClick(View v) {
			shareDialog();
		}
	};
	// 觀看更多店家商品
	private OnClickListener mm = new OnClickListener() {
		public void onClick(View v) {

		}
	};

	// 分享app
	void shareDialog() {

		String shareText = "好便宜!" + store + "-" + sn + "只賣NT." + pr + "推薦給你 ";
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		startActivity(Intent.createChooser(shareIntent, "分享"));
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
