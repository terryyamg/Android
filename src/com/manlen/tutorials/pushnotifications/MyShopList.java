package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MyShopList extends Fragment {
	private Button cancel[];
	private int opr, pr, pn, id;
	private String userTel, myTel, sn, tableData[][];

	List<ParseObject> ob;
	ArrayAdapter<String> MyArrayAdapter;
	ListView listview;
	Typeface fontch;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 字型 */
		fontch = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wt001.ttf");
		SharedPreferences preferences = getActivity().getSharedPreferences("Android",
						android.content.Context.MODE_PRIVATE);
		myTel = preferences.getString("myTel", userTel);
		if (myTel == null) {
			myTel = "0"; // 抓不到手機號碼
		}
		
		
				
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView(inflater, container);
	}

	private View initView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.my_shop_list, container, false);
		
		// 搜尋BuyerInfo 使用者的所有購買資訊
				listview = (ListView) view.findViewById(R.id.list);
				MyArrayAdapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1);
				listview.setAdapter(MyArrayAdapter);
				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						// TODO Auto-generated method stub
						listViewClick((int)(arg3+1));
						
					}
				});
				
				// 搜尋BuyerInfo 使用者的所有購買資訊
						try {

							ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
									"BuyerInfo");
							query.whereEqualTo("userTel", myTel);
							query.orderByDescending("arrivalDate");
							ob = query.find();

							int size = ob.size(); // 幾筆資料

							String[] store = new String[size];
							String[] commodityName = new String[size];
							String[] arrivalDate = new String[size];
							String[] arrivalTime = new String[size];
							int[] numberIndex = new int[size];
							int[] totalPrice = new int[size];
							String[] objectId = new String[size];
							// 表格第一列文字
							tableData = new String[size + 1][7];
							tableData[0][0] = "商店";
							tableData[0][1] = "商品";
							tableData[0][2] = "到貨日期";
							tableData[0][3] = "到貨時段";
							tableData[0][4] = "數量";
							tableData[0][5] = "總價";
							tableData[0][6] = "ID";
							int i = 0;
							for (ParseObject search : ob) {
								// 取得資料
								store[i] = (String) search.get("store");
								commodityName[i] = (String) search.get("commodityName");
								arrivalDate[i] = (String) search.get("arrivalDate");
								arrivalTime[i] = (String) search.get("arrivalTime");
								numberIndex[i] = (int) search.getInt("numberIndex");
								totalPrice[i] = (int) search.getInt("totalPrice");
								objectId[i] = search.getObjectId();
								// 放入tableData字串
								tableData[i + 1][0] = store[i];
								tableData[i + 1][1] = commodityName[i];
								tableData[i + 1][2] = arrivalDate[i];
								tableData[i + 1][3] = arrivalTime[i];
								tableData[i + 1][4] = Integer.toString(numberIndex[i]);
								tableData[i + 1][5] = Integer.toString(totalPrice[i]);
								tableData[i + 1][6] = objectId[i];
								String info = tableData[i + 1][0] + "  " + tableData[i + 1][1]
										+ "  數量:" + tableData[i + 1][4] + "  總價NT." + tableData[i + 1][5];

								MyArrayAdapter.add(info);
								MyArrayAdapter.notifyDataSetChanged();
								i++;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
		
//		TableLayout t1 = (TableLayout) view.findViewById(R.id.tablay2);
//		cancel = new Button[tableData.length];
//		for (int i = 0; i < tableData.length; i++) { // 列
//
//			TableRow row = new TableRow(getActivity());
//			for (int j = 0; j < tableData[i].length - 1; j++) { // 行
//				if (i == 0) {
//					TextView tv = new TextView(getActivity()); // 設定第一排文字
//					tv.setTypeface(fontch);
//					tv.setTextSize(20);
//					tv.setTextColor(Color.BLACK);
//					tv.setText(tableData[i][j] + " ");
//					row.addView(tv, j);
//				} else {
//					if (j < tableData[i].length - 2) { // 設定 其他搜尋到的文字
//						TextView tv = new TextView(getActivity());
//						tv.setTypeface(fontch);
//						tv.setTextSize(20);
//						tv.setTextColor(Color.BLACK);
//						tv.setText(tableData[i][j] + " ");
//						row.addView(tv, j);
//					} else if (j == tableData[i].length - 2) { // 設定按鈕
//
//						cancel[i] = new Button(getActivity());
//						cancel[i].setTypeface(fontch);
//						cancel[i].setTextColor(Color.BLACK);
//						cancel[i]
//								.setBackgroundResource(R.drawable.list_commodity);
//						cancel[i].setText("取消");
//						cancel[i].setId(i);
//						cancel[i].setOnClickListener(cc); // 取消訂單
//						row.addView(cancel[i], j);
//					}
//				}
//
//			}
//			t1.addView(row);
//		}
		return view;
	}
	
	void listViewClick(int id){
		Intent intent = new Intent(getActivity(), MyShopListDetail.class);
		intent.putExtra("store", tableData[id][0]); //商店
		intent.putExtra("commodityName", tableData[id][1]); //商品
		intent.putExtra("arrivalDate", tableData[id][2]); //到貨日期
		intent.putExtra("arrivalTime", tableData[id][3]); //到貨時段
		intent.putExtra("numberIndex", tableData[id][4]); //數量
		intent.putExtra("totalPrice", tableData[id][5]); //總價
		intent.putExtra("objectId", tableData[id][6]); //物件id	
		
		startActivity(intent);
	}
	
}
