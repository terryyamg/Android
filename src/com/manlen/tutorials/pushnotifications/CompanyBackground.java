package com.manlen.tutorials.pushnotifications;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CompanyBackground extends FragmentActivity {
	private String channels,store, scannerName, tableData[][];
	private int logoNumber,number, sum = 0;
	private TextView tv1;
	private ImageView imageView1;
	Typeface fontch;
	List<ParseObject> searchObject, ob;
	ArrayAdapter<String> MyArrayAdapter;
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_background);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");

		imageView1 = (ImageView) findViewById(R.id.imageView1);
		tv1 = (TextView) findViewById(R.id.cbtv1);
		tv1.setTypeface(fontch);
		tv1.setTextSize(20);
		/* ���o�ӫ~�W�ٻP���� */
		Intent intent = getIntent();
		logoNumber = intent.getIntExtra("logoNumber",1); // ���opic���X
		store = intent.getStringExtra("store"); // ���o���ʩ��W
		scannerName = intent.getStringExtra("scannerName"); // ���o�S���ө����W

		imageView1.setImageResource(R.drawable.logo+logoNumber);
		
		// tab1
		// �j�M�`�@�Q�ͺˤF�X��
		try {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Scanner");
			searchObject = query.find();// �j�M����
			for (ParseObject search : searchObject) {
				// ���o���
				number = (int) search.getInt(scannerName); // scannerNumber
				sum = sum + number;
			}
		} catch (ParseException e) {

		}
		tv1.setText(store + "\n" + "�u�f�`�@�Q���y�F" + sum + "��");

		/* tab2 */
		// �j�MBuyerInfo �ϥΪ̪��Ҧ��ʶR��T
		listview = (ListView) findViewById(R.id.list);
		MyArrayAdapter = new ArrayAdapter<String>(this,
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

		try {

			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"BuyerInfo");
			query.whereEqualTo("store", store);
			query.orderByDescending("arrivalDate");
			ob = query.find();

			int size = ob.size(); // �X�����

			String[] store = new String[size];
			String[] userName = new String[size];
			String[] userTel = new String[size];
			String[] userAdd = new String[size];
			String[] commodityName = new String[size];
			int[] numberIndex = new int[size];
			int[] totalPrice = new int[size];
			String[] arrivalDate = new String[size];
			String[] arrivalTime = new String[size];
			int[] ok = new int[size];
			String[] objectId = new String[size];

			// ���Ĥ@�C��r
			tableData = new String[size + 1][12];
			tableData[0][0] = "�ө�";
			tableData[0][1] = "�m�W";
			tableData[0][2] = "�q��";
			tableData[0][3] = "�a�}";
			tableData[0][4] = "�ӫ~";
			tableData[0][5] = "�ƶq";
			tableData[0][6] = "�`��";
			tableData[0][7] = "��f���";
			tableData[0][8] = "��f�ɬq";
			tableData[0][9] = "ok";
			tableData[0][10] = "ID";

			int i = 0;
			for (ParseObject search : ob) {
				// ���o���
				store[i] = (String) search.get("store");
				userName[i] = (String) search.get("userName");
				userTel[i] = (String) search.get("userTel");
				userAdd[i] = (String) search.get("userAdd");
				commodityName[i] = (String) search.get("commodityName");
				numberIndex[i] = (int) search.getInt("numberIndex");
				totalPrice[i] = (int) search.getInt("totalPrice");
				arrivalDate[i] = (String) search.get("arrivalDate");
				arrivalTime[i] = (String) search.get("arrivalTime");
				ok[i] = (int) search.getInt("complete");
				objectId[i] = search.getObjectId();
				// ��JtableData�r��
				tableData[i + 1][0] = store[i];
				tableData[i + 1][1] = userName[i];
				tableData[i + 1][2] = userTel[i];
				tableData[i + 1][3] = userAdd[i];
				tableData[i + 1][4] = commodityName[i];
				tableData[i + 1][5] = Integer.toString(numberIndex[i]);
				tableData[i + 1][6] = Integer.toString(totalPrice[i]);
				tableData[i + 1][7] = arrivalDate[i];
				tableData[i + 1][8] = arrivalTime[i];
				tableData[i + 1][9] = Integer.toString(ok[i]);
				tableData[i + 1][10] = objectId[i];
				String info = tableData[i + 1][1] + "  " + tableData[i + 1][2]
						+ "  " + tableData[i + 1][4] + "  �`��NT." + tableData[i + 1][6];

				MyArrayAdapter.add(info);
				MyArrayAdapter.notifyDataSetChanged();
				i++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		setupViewComponent();
	}

	/* tab2 */
	//
	void listViewClick(int id){
		Intent intent = new Intent(this, CompanyBackgroundDetail.class);
		//intent.putExtra("store", tableData[id][0]); //�ө�
		intent.putExtra("userName", tableData[id][1]); //�m�W
		intent.putExtra("userTel", tableData[id][2]); //�q��
		intent.putExtra("userAdd", tableData[id][3]); //�a�}
		intent.putExtra("commodityName", tableData[id][4]); //�ӫ~
		intent.putExtra("numberIndex", tableData[id][5]); //�ƶq
		intent.putExtra("totalPrice", tableData[id][6]); //�`��
		intent.putExtra("arrivalDate", tableData[id][7]); //��f���
		intent.putExtra("arrivalTime", tableData[id][8]); //��f�ɬq
		intent.putExtra("checkOk", tableData[id][9]); //�����_
		intent.putExtra("objectId", tableData[id][10]); //����id
		
		intent.putExtra("store", store); 
		intent.putExtra("scannerName", scannerName); 
		
		startActivity(intent);
	}
	
	private void setupViewComponent() {
		/* tabHost */
		// �q�귽���OR�����o��������
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("",
				getResources().getDrawable(R.drawable.commoditylist));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("", getResources().getDrawable(R.drawable.mylist));
		spec.setContent(R.id.tab2);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// �]�wtab���Ҫ��r��j�p
		TabWidget tabWidget = (TabWidget) tabHost
				.findViewById(android.R.id.tabs);
		View tabView = tabWidget.getChildTabViewAt(0);
		TextView tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);

		tabView = tabWidget.getChildTabViewAt(1);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);
	}
}
