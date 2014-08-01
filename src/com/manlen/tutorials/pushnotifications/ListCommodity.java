package com.manlen.tutorials.pushnotifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ListCommodity extends FragmentActivity {
	private Button close, bc1, bc2;
	private TextView cd1, cd2;
	private String[] s1 = new String[] { "甜蜜花語", "炭燒咖啡" };
	private int price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_commodity);

		close = (Button) findViewById(R.id.close);
		bc1 = (Button) findViewById(R.id.bc1);
		bc2 = (Button) findViewById(R.id.bc2);

		cd1 = (TextView) findViewById(R.id.cd1);
		cd2 = (TextView) findViewById(R.id.cd2);

		cd1.setText(s1[0] + "\n白色的鮮奶油對上鮮紅的草莓，酸 \n" + "甜草莓餡、軟滑布丁，在鮮奶油的 \n"
				+ "萬花齊放下，展現出柔軟美妙的新 \n" + "視覺及甜蜜雙重享受。");
		cd2.setText(s1[1] + "\n咖啡戚風蛋糕結合牛奶布蕾，入口 \n" + "後濃郁的咖啡香搭配爽脆的綜合堅 \n"
				+ "果，滋味絕配。");
		// 第一件商品
		bc1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				search(s1[0]);
			}
		});
		bc2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				search(s1[1]);
			}
		});

		close.setOnClickListener(back); // 返回

	}

	// 搜尋商品價格
	void search(final String s) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Commodity");
		query.whereEqualTo("commodity", s);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					price = object.getInt("price");
					buy(s);
				} else {
				}
			}
		});

	}

	// 第一件
	void buy(String name) {
		Intent intent = new Intent(this, BuyConfirm.class);
		if (name.equals(s1[0])) {
			intent.putExtra("commodityName", s1[0]);
			intent.putExtra("price", price);
		} else if (name.equals(s1[1])) {
			intent.putExtra("commodityName", s1[1]);
			intent.putExtra("price", price);
		}
		startActivity(intent);
	}

	// 返回
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

}
