package com.manlen.tutorials.pushnotifications;

import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SearchCommodity extends FragmentActivity {
	private String searchName,s1[];
	private int pr1[];
	List<ParseObject> searchObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_commodity);

		Intent intent = getIntent();
		searchName = intent.getStringExtra("searchName"); // 取得搜尋名稱
		Log.i("search", searchName + "");
		
		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // 哪個table
			queryCommodity.whereContains("commodity", searchName);
			searchObject = queryCommodity.find();// 搜尋物件
			int sizeob = searchObject.size(); // 幾筆資料
			s1 = new String[sizeob]; // 商品名稱陣列
			pr1 = new int[sizeob]; // 商品價格陣列
			int i = 0;
			for (ParseObject search : searchObject) {
				// 取得資料
				s1[i] = (String) search.get("commodity");
				pr1[i] = (int) search.getInt("price");
				Log.i("s1", s1[i]+"");
				Log.i("pr1", pr1[i]+"");
				i++;
			}

		} catch (Exception e) {
			Log.i("error", "error");
		}
	}
}
