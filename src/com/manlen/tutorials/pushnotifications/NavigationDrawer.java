package com.manlen.tutorials.pushnotifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NavigationDrawer extends Activity {

	private DrawerLayout layDrawer;
	private ListView lstDrawer;

	private ActionBarDrawerToggle drawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private String[] drawer_menu;

	private EditText searchName;
	private String searchObject;
	private Button searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initActionBar();
		initDrawer();
		initDrawerList();

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	// ================================================================================
	// Init
	// ================================================================================
	private void initActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	private void initDrawer() {
		setContentView(R.layout.drawer);

		layDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		lstDrawer = (ListView) findViewById(R.id.left_drawer);

		layDrawer
				.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		mTitle = mDrawerTitle = getTitle();
		drawerToggle = new ActionBarDrawerToggle(this, layDrawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
			}
		};
		drawerToggle.syncState();

		layDrawer.setDrawerListener(drawerToggle);
	}

	private void initDrawerList() {
		drawer_menu = this.getResources().getStringArray(R.array.drawer_menu);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.drawer_list_item, drawer_menu);

		List<HashMap<String, String>> lstData = new ArrayList<HashMap<String, String>>();

		// list1
		HashMap<String, String> mapValue1 = new HashMap<String, String>();
		mapValue1.put("icon", Integer.toString(R.drawable.list0));
		mapValue1.put("title", drawer_menu[0]);
		lstData.add(mapValue1);
		// list2
		HashMap<String, String> mapValue2 = new HashMap<String, String>();
		mapValue2.put("icon", Integer.toString(R.drawable.list1));
		mapValue2.put("title", drawer_menu[1]);
		lstData.add(mapValue2);
		// list3
		HashMap<String, String> mapValue3 = new HashMap<String, String>();
		mapValue3.put("icon", Integer.toString(R.drawable.list2));
		mapValue3.put("title", drawer_menu[2]);
		lstData.add(mapValue3);
		// list4
		HashMap<String, String> mapValue4 = new HashMap<String, String>();
		mapValue4.put("icon", Integer.toString(R.drawable.list3));
		mapValue4.put("title", drawer_menu[3]);
		lstData.add(mapValue4);
		// list5
		HashMap<String, String> mapValue5 = new HashMap<String, String>();
		mapValue5.put("icon", Integer.toString(R.drawable.list4));
		mapValue5.put("title", drawer_menu[4]);
		lstData.add(mapValue5);
		// list6
		HashMap<String, String> mapValue6 = new HashMap<String, String>();
		mapValue6.put("icon", Integer.toString(R.drawable.list5));
		mapValue6.put("title", drawer_menu[5]);
		lstData.add(mapValue6);

		SimpleAdapter adapter = new SimpleAdapter(this, lstData,
				R.layout.drawer_list_item2, new String[] { "icon", "title" },
				new int[] { R.id.imgIcon, R.id.txtItem });
		lstDrawer.setAdapter(adapter);

		// 側選單點選偵聽器
		lstDrawer.setOnItemClickListener(new DrawerItemClickListener());
	}

	// ================================================================================
	// Action Button 建立及點選事件
	// ================================================================================
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		View v = (View) menu.findItem(R.id.action_search).getActionView();

		searchName = (EditText) v.findViewById(R.id.search);
		searchName.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				try {
					searchObject = searchName.getText().toString(); // 取得輸入文字
				} catch (Exception e) {

				}

			}
		});

		searchButton = (Button) v.findViewById(R.id.searchGo);
		searchButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goSearch();
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// home
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// action buttons
		switch (item.getItemId()) {

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	void goSearch() {
		if (searchObject == null) {

		} else {

			Intent intent = new Intent(this, SearchCommodity.class);
			intent.putExtra("searchName", searchObject);
			startActivity(intent);
		}
	}

	// ================================================================================
	// 側選單點選事件
	// ================================================================================
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Fragment fragment = null;

		switch (position) {
		case 0:
			fragment = new MainActivity();
			break;
		case 1:
			fragment = new MyShopList();
			break;
		case 2:
			fragment = new SetUp();
			break;
		case 3:
			fragment = new Advertising();
			break;
		case 4:
			fragment = new MyFavourite();
			break;
		case 5:
			Intent goWeb = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/lifetoneplus?fref=ts"));
			startActivity(goWeb);
			break;
		default:
			// 還沒製作的選項，fragment 是 null，直接返回
			return;
		}

		FragmentManager fragmentManager = getFragmentManager();
		// [方法1]直接置換，無法按 Back 返回
		// fragmentManager.beginTransaction().replace(R.id.content_frame,
		// fragment).commit();

		// [方法2]開啟並將前一個送入堆疊
		// 重要！ 必須加寫 "onBackPressed"

		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		try {
			fragmentTransaction.replace(R.id.content_frame, fragment);
		} catch (Exception e) {

		}
		fragmentTransaction.addToBackStack("home");
		fragmentTransaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.commit();

		// 更新被選擇項目，換標題文字，關閉選單
		lstDrawer.setItemChecked(position, true);
		setTitle(drawer_menu[position]);
		layDrawer.closeDrawer(lstDrawer);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * Back 鍵處理 當最後一個 stack 為 R.id.content_frame, 結束 App
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		FragmentManager fragmentManager = this.getFragmentManager();
		int stackCount = fragmentManager.getBackStackEntryCount();
		if (stackCount == 0) {
			this.finish();
		}
	}

}
