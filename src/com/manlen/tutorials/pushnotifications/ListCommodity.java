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
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		try {
			// tab1 ���o�ӫ~���
			Intent intent = getIntent();
			sn = intent.getStringExtra("storeName"); // ���o�ӫ~�W��
			opr = intent.getIntExtra("orientPrice", 0); // ���o��l����
			pr = intent.getIntExtra("price", 0); // ���o����
			pn = intent.getIntExtra("picNumber", 0); // ���o�Ϥ����X
			// tab2 �������X
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // ����table
			queryCommodity.whereEqualTo("commodity", sn); // ����
			searchObject = queryCommodity.find();// �j�M����
			for (ParseObject search : searchObject) {
				// ���o���
				si = (String) search.get("introduction"); // �ӫ~����
				store = (String) search.get("store"); // ���W
				storeClass = (String) search.get("storeClass"); // ���a���O
				sellNumber = (int) search.getInt("sellNumber"); // ��X�ƶq
			}

			SharedPreferences preferences = getApplicationContext()
					.getSharedPreferences("Android",
							android.content.Context.MODE_PRIVATE);

			myTel = preferences.getString("myTel", userTel);
			if (myTel == null) {
				myTel = "0"; // �줣�������X
			}
		} catch (Exception e) {
			myTel = "0"; // �줣�������X
		}

		// �ƪ�
		setTable();

	}

	/* tab1 */

	void setTable() {
		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);

		// �ƪ�
		buyButton = new Button(this);
		recommend = new Button(this);
		more = new Button(this);
		favourite = new Button(this);
		for (int i = 0; i < 8; i++) { // �C
			TableRow row = new TableRow(this);
			TableRow.LayoutParams rowSpanLayout = new TableRow.LayoutParams(
					200, 200);
			rowSpanLayout.span = 2;
			TableRow.LayoutParams rowSpanLayout2 = new TableRow.LayoutParams(
					30, 30);
			rowSpanLayout2.topMargin = 20;
			// �Ĥ@�C �Ϥ�
			switch (i) {
			case 0: // �Ĥ@�C �ӫ~�Ϥ�
				img = new ImageView(this);
				img.setImageResource(R.drawable.store + pn);
				img.setLayoutParams(rowSpanLayout);
				row.addView(img, 0);
				break;
			case 1: // �ĤG�C �ӫ~�W��
				TextView tv2 = new TextView(this);
				tv2.setTextSize(20);
				tv2.setTypeface(fontch);
				tv2.setTextColor(Color.BLACK);
				tv2.setText(store + "\n" + sn + " ");
				row.addView(tv2, 0);
				break;
			case 2: // ��X�ƶq
				TextView tv3 = new TextView(this);
				tv3.setTextSize(20);
				tv3.setTypeface(fontch);
				tv3.setTextColor(Color.BLACK);
				tv3.setText("�w��X" + sellNumber + "��");
				row.addView(tv3, 0);
				favourite = new Button(this);
				favourite.setBackgroundResource(R.drawable.fav);
				favourite.setLayoutParams(rowSpanLayout2);
				favourite.setId(i);
				favourite.setOnClickListener(ff); // �ʶR�ʧ@
				row.addView(favourite, 1);
				break;
			case 3: // ����
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
			case 4: // ����
				TextView tv5 = new TextView(this);
				tv5.setTextSize(20);
				tv5.setTypeface(fontch);
				tv5.setTextColor(Color.BLACK);
				String oprl = Integer.toString(opr);
				String prl = Integer.toString(pr);
				String ss = "���:NT$" + oprl + "\n���ʻ�:NT$" + prl;
				oprl.length(); // ����Ʀr����
				prl.length();// ���ʼƦr����
				Spannable msp = new SpannableString(ss);
				msp.setSpan(new StrikethroughSpan(), 6, 6 + oprl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �R���u
				msp.setSpan(new RelativeSizeSpan(2.0f), 14 + oprl.length(), 14
						+ oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �⭿�j�p
				msp.setSpan(
						new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
						14 + oprl.length(), 14 + oprl.length() + prl.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �צr��
				tv5.setText(msp);
				row.addView(tv5, 0);
				break;
			case 5:
				buyButton = new Button(this);
				buyButton.setTypeface(fontch);
				buyButton.setTextColor(Color.BLACK);
				buyButton.setBackgroundResource(R.drawable.list_commodity);
				buyButton.setText("NT$" + pr + " ���W�[�J�u�f");
				buyButton.setId(i);
				buyButton.setOnClickListener(bb); // �ʶR�ʧ@
				row.addView(buyButton, 0);
				break;
			case 6:
				recommend = new Button(this);
				recommend.setTypeface(fontch);
				recommend.setTextColor(Color.BLACK);
				recommend.setBackgroundResource(R.drawable.list_commodity);
				recommend.setText("���˵��n��");
				recommend.setId(i);
				recommend.setOnClickListener(rr); // ���˰ʧ@
				row.addView(recommend, 0);
				break;
			case 7:
				more = new Button(this);
				more.setTypeface(fontch);
				more.setTextColor(Color.BLACK);
				more.setBackgroundResource(R.drawable.list_commodity);
				more.setText("�[�ݧ�h���a�ӫ~");
				more.setId(i);
				more.setOnClickListener(mm); // ���˰ʧ@
				row.addView(more, 0);
				break;

			}
			t1.addView(row);
		}

	}

	// �[�J����
	private OnClickListener ff = new OnClickListener() {
		public void onClick(View v) {
			addMyFavourite();
		}
	};

	void addMyFavourite() {
		objectId = ParseInstallation.getCurrentInstallation().getObjectId(); // ���X�ۤv��id
		favouriteKey = ParseInstallation.getCurrentInstallation().getInt(
				"favouriteKey"); // ���o�O�_�إ߹L����
		if (favouriteKey == 0) { // �Ĥ@�� �إ� recommendList ObjectID
			ParseObject myFavourite = new ParseObject("MyFavourite"); // �إ�MyFavorite
																		// table
			myFavourite.put("installID", objectId); // ��JinstallID
			myFavourite.add("picNumber", Arrays.asList(pn));// ��J�Ϥ����X
			myFavourite.saveInBackground(); // �s�JMyFavorite table

			ParseInstallation.getCurrentInstallation().put("favouriteKey",
					favouriteKey + 1);
			ParseInstallation.getCurrentInstallation().saveInBackground();
			Toast.makeText(getBaseContext(), "���\�[�J�ڪ�����!", Toast.LENGTH_SHORT)
					.show();
		} else {

			ParseQuery<ParseObject> query = ParseQuery.getQuery("MyFavourite");
			query.whereEqualTo("installID", objectId);

			// �P�_�O�_�w�[�J�L
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
					getPicNumber = pic.getString(i);// ���X�Ʀr
					int gPN = Integer.parseInt(getPicNumber.replaceAll("[\\D]",
							""));
					if (pn == gPN) { // ����J�L
						picIndex1 = false;
					}

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// true:��J;false:����J
			if (picIndex1) {
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					public void done(ParseObject myFavouriteList,
							ParseException e) {
						if (e == null) {
							myFavouriteList.add("picNumber", Arrays.asList(pn));// ��J�Ϥ����X
							myFavouriteList.saveInBackground(); // �s�JMyFavorite
																// table

						}
					}
				});
				Toast.makeText(getBaseContext(), "���\�[�J�ڪ�����!",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getBaseContext(), "���ӫ~�w�b�ڪ�����!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	// �ʶR
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
	// �[�ݧ�h���a�ӫ~
	private OnClickListener mm = new OnClickListener() {
		public void onClick(View v) {

		}
	};

	// ����app
	void shareDialog() {

		String shareText = "�n�K�y!" + store + "-" + sn + "�u��NT." + pr + "���˵��A ";
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		startActivity(Intent.createChooser(shareIntent, "����"));
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
