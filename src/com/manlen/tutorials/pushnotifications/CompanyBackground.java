package com.manlen.tutorials.pushnotifications;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class CompanyBackground extends FragmentActivity {
	private String store, scannerName;
	private int number, sum=0;
	private TextView tv1;
	Typeface fontch;
	List<ParseObject> searchObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_background);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");

		tv1 = (TextView) findViewById(R.id.cbtv1);
		tv1.setTypeface(fontch);

		/* ���o�ӫ~�W�ٻP���� */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // ���o���ʩ��W
		scannerName = intent.getStringExtra("scannerName"); // ���o�S���ө����W

		Log.i("store", store + "");
		Log.i("scannerName", scannerName + "");
		// tab1
		// �j�M�`�@�Q�ͺˤF�X��
		try {
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery("_Installation");
			searchObject = query.find();// �j�M����
			for (ParseObject search : searchObject) {
				// ���o���
				number = (int) search.getInt(scannerName); // scannerNumber
				sum = number++;
				Log.i("number", number+"");
				Log.i("sum", sum+"");
			}
		} catch (ParseException e) {
			Log.i("eeee", e+"");
		}
		tv1.setText(store + "\n" + "�u�f�`�@�Q���y�F" + sum + "��");
		setupViewComponent();
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
