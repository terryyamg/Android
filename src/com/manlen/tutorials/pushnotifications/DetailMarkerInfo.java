package com.manlen.tutorials.pushnotifications;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DetailMarkerInfo extends FragmentActivity {
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private String[] addresses = { "1", "2", "3" };
	private String storeName,storeLocation,storeDetail,picNumber;
	private ImageView img;
	private TextView tabText1,tabText2;
	Typeface fontch;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.detail_marker_info);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		 
		Intent intent = getIntent();
		storeName = intent.getStringExtra("storeName"); //店名
		storeLocation = intent.getStringExtra("storeLocation"); //位置
		storeDetail = intent.getStringExtra("storeDetail");// 細節
		picNumber = intent.getStringExtra("picNumber"); //圖

		/*tab1*/
		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);
		TableRow row = new TableRow(this);
		img = new ImageView(this);
		img.setImageResource(R.drawable.market + Integer.parseInt(picNumber));
		row.addView(img, 0);
		t1.addView(row);

		/*tab2 tab3*/
		tabText1 = (TextView) findViewById(R.id.tabText1);
		tabText2 = (TextView) findViewById(R.id.tabText2);
		tabText1.setTypeface(fontch);
		tabText2.setTypeface(fontch);
		tabText1.setTextColor(Color.BLACK);
		tabText2.setTextColor(Color.BLACK);
		tabText1.setTextSize(20);
		tabText2.setTextSize(20);
		tabText1.setText(storeName+"\n "+storeLocation);
		tabText2.setText(storeDetail);
		
		mViewPager = (ViewPager) findViewById(R.id.viewPager1);
		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabHost.addTab(mTabHost.newTabSpec("one").setIndicator("商品明細", getResources().getDrawable(R.drawable.detail_marker_info1))
				.setContent(R.id.tab1));
		mTabHost.addTab(mTabHost.newTabSpec("two").setIndicator("店家位置", getResources().getDrawable(R.drawable.detail_marker_info2))
				.setContent(R.id.tab2));
		mTabHost.addTab(mTabHost.newTabSpec("three").setIndicator("注意事項", getResources().getDrawable(R.drawable.detail_marker_info3))
				.setContent(R.id.tab3));

		TabWidget tabWidget = mTabHost.getTabWidget();
		int count = tabWidget.getChildCount();
		for (int i = 0; i != count; i++) {
			final int index = i;
			tabWidget.getChildAt(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mTabHost.setCurrentTab(index);
					mViewPager.setCurrentItem(index);
				}
			});
		}
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

			}
		});

		mTabHost.performClick();

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				mTabHost.setCurrentTab(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			return MyFragment.create(addresses[position]);
		}

		@Override
		public int getCount() {
			return addresses.length;
		}

	}

	public static class MyFragment extends Fragment {
		public static MyFragment create(String address) {
			MyFragment f = new MyFragment();
			Bundle b = new Bundle();
			b.putString("address", address);
			f.setArguments(b);
			return f;
		}

//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			Random r = new Random(System.currentTimeMillis());
//
//			Bundle b = getArguments();
//
//			View v = inflater.inflate(R.layout.fragment_viewpager1_layout1,
//					null);
//			v.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
//
//			TextView txvAddress = (TextView) v.findViewById(R.id.textView1);
//			txvAddress.setTextColor(r.nextInt() >> 8 | 0xFF << 24);
//			txvAddress.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
//
//			txvAddress.setText(b.getString("address", ""));
//			return v;
//		}

	}
}