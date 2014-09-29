package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MyShopList extends Fragment {
	private Button cancel[];
	private int opr, pr, pn, id;
	private String userTel, myTel, sn, tableData[][];
	public ProgressDialog dialog = null;
	List<ParseObject> ob;
	Typeface fontch;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* �r�� */
		fontch = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wt001.ttf");
		SharedPreferences preferences = getActivity().getSharedPreferences("Android",
						android.content.Context.MODE_PRIVATE);
		myTel = preferences.getString("myTel", userTel);
		if (myTel == null) {
			myTel = "0"; // �줣�������X
		}
		// �j�MBuyerInfo �ϥΪ̪��Ҧ��ʶR��T
				try {

					ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
							"BuyerInfo");
					query.whereEqualTo("userTel", myTel);
					query.orderByDescending("arrivalDate");
					ob = query.find();

					int size = ob.size(); // �X�����

					String[] store = new String[size];
					String[] commodityName = new String[size];
					String[] arrivalDate = new String[size];
					String[] arrivalTime = new String[size];
					int[] numberIndex = new int[size];
					int[] totalPrice = new int[size];
					String[] objectId = new String[size];
					// ���Ĥ@�C��r
					tableData = new String[size + 1][8];
					tableData[0][0] = "�ө�";
					tableData[0][1] = "�ӫ~";
					tableData[0][2] = "��f���";
					tableData[0][3] = "��f�ɬq";
					tableData[0][4] = "�ƶq";
					tableData[0][5] = "�`��";
					tableData[0][6] = "����";
					tableData[0][7] = "ID";
					int i = 0;
					for (ParseObject search : ob) {
						// ���o���
						store[i] = (String) search.get("store");
						commodityName[i] = (String) search.get("commodityName");
						arrivalDate[i] = (String) search.get("arrivalDate");
						arrivalTime[i] = (String) search.get("arrivalTime");
						numberIndex[i] = (int) search.getInt("numberIndex");
						totalPrice[i] = (int) search.getInt("totalPrice");
						objectId[i] = search.getObjectId();
						// ��JtableData�r��
						tableData[i + 1][0] = store[i];
						tableData[i + 1][1] = commodityName[i];
						tableData[i + 1][2] = arrivalDate[i];
						tableData[i + 1][3] = arrivalTime[i];
						tableData[i + 1][4] = Integer.toString(numberIndex[i]);
						tableData[i + 1][5] = Integer.toString(totalPrice[i]);
						tableData[i + 1][6] = "";
						tableData[i + 1][7] = objectId[i];

						i++;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView(inflater, container);
	}

	private View initView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.my_shop_list, container, false);
		
		TableLayout t1 = (TableLayout) view.findViewById(R.id.tablay2);
		cancel = new Button[tableData.length];
		for (int i = 0; i < tableData.length; i++) { // �C

			TableRow row = new TableRow(getActivity());
			for (int j = 0; j < tableData[i].length - 1; j++) { // ��
				if (i == 0) {
					TextView tv = new TextView(getActivity()); // �]�w�Ĥ@�Ƥ�r
					tv.setTypeface(fontch);
					tv.setTextSize(20);
					tv.setTextColor(Color.BLACK);
					tv.setText(tableData[i][j] + " ");
					row.addView(tv, j);
				} else {
					if (j < tableData[i].length - 2) { // �]�w ��L�j�M�쪺��r
						TextView tv = new TextView(getActivity());
						tv.setTypeface(fontch);
						tv.setTextSize(20);
						tv.setTextColor(Color.BLACK);
						tv.setText(tableData[i][j] + " ");
						row.addView(tv, j);
					} else if (j == tableData[i].length - 2) { // �]�w���s

						cancel[i] = new Button(getActivity());
						cancel[i].setTypeface(fontch);
						cancel[i].setTextColor(Color.BLACK);
						cancel[i]
								.setBackgroundResource(R.drawable.list_commodity);
						cancel[i].setText("����");
						cancel[i].setId(i);
						cancel[i].setOnClickListener(cc); // �����q��
						row.addView(cancel[i], j);
					}
				}

			}
			t1.addView(row);
		}
		return view;
	}
	

	// �����q��
	private OnClickListener cc = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			// ���X�T�{����
			new AlertDialog.Builder(getActivity())
					.setTitle("�T�w�R��?")
					.setMessage("�R����" + id + "�����")
					.setNegativeButton("�R��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									ParseObject.createWithoutData("BuyerInfo",
											tableData[id][7])
											.deleteEventually(); // Parse���O
																	// �R�����w�� row
									ref();
								}
							})
					.setPositiveButton("���R��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();

		}
	};

	void ref() {
		Intent intent = new Intent(getActivity(), MyShopList.class);
		intent.putExtra("storeName", sn); // ���o�ӫ~�W��
		intent.putExtra("orientPrice", opr); // ���o��l����
		intent.putExtra("price", pr); // ���o����
		intent.putExtra("picNumber", pn); // ���o�Ϥ����X
		startActivity(intent);
	}
	
}
