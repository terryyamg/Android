package com.manlen.tutorials.pushnotifications;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Leading extends Activity {
	private ViewPager myViewPager; // ���d���e
	private List<View> list; // �s�񭶥d
	private TextView dot1, dot2, dot3, dot4, dot5; // �o���I���O��r
	private Button startButton; // ���s�A�}�l����
	private int first;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.leading);
		
		/* �إ߮ୱ���| */
		addShortcut();
		initDot();
		initViewPager();
	}

	/* �إ߮ୱ���| */
	private void addShortcut() {
		Intent shortcutIntent = new Intent(getApplicationContext(),
				SplashScreen.class); // �Ұʱ��|�J�f�A�@���MainActivity�A���ϥΨ�L�J�f�h��J�۹�W�١Aex:���ϥ�SplashScreen
		shortcutIntent.setAction(Intent.ACTION_MAIN);
		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // shortcutIntent�e�J
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name)); // ���|app�W��
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(
						getApplicationContext(),// ���|app��
						R.drawable.ic_launcher));
		addIntent.putExtra("duplicate", false); // ���Ыؤ@��
		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // �w��
		getApplicationContext().sendBroadcast(addIntent); // �e�X�s��
	}
	
	private void initDot() {
		dot1 = (TextView) this.findViewById(R.id.textView1); // �o���I���O��r
		dot2 = (TextView) this.findViewById(R.id.textView2);
		dot3 = (TextView) this.findViewById(R.id.textView3);
		dot4 = (TextView) this.findViewById(R.id.textView4);
		dot5 = (TextView) this.findViewById(R.id.textView5);
	}

	private void initViewPager() {
		myViewPager = (ViewPager) this.findViewById(R.id.viewPager);
		list = new ArrayList<View>();

		LayoutInflater inflater = getLayoutInflater();

		View view = inflater.inflate(R.layout.lay5, null); // �u�O���F���Ufindviewbuid�ӿW�߮��X�ӽᵹview

		list.add(inflater.inflate(R.layout.lay1, null));
		list.add(inflater.inflate(R.layout.lay2, null));
		list.add(inflater.inflate(R.layout.lay3, null));
		list.add(inflater.inflate(R.layout.lay4, null));
		list.add(view);
		try {
			myViewPager.setAdapter(new MyPagerAdapter(list));

			myViewPager.setOnPageChangeListener(new MyPagerChangeListener());
		} catch (NullPointerException e) {
		}
		/* �r�� */
		Typeface fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");
		startButton = (Button) view.findViewById(R.id.start); // �P�W�������A����o�ӫ��s
		startButton.setTypeface(fontch);
		
		startButton.setOnClickListener(new OnClickListener() {

			@SuppressLint("CommitPrefEdits")
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Leading.this, MainActivity.class);
				SharedPreferences preferences = getApplicationContext()
						.getSharedPreferences("Android",
								android.content.Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				first =1;
				editor.putInt("first", first); //�x�s���
				editor.commit();
				startActivity(intent);

			}
		});
	}

	class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	class MyPagerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) { // �]�m�I���C��
			case 0:
				dot1.setTextColor(Color.WHITE);
				dot2.setTextColor(Color.BLACK);
				dot3.setTextColor(Color.BLACK);
				dot4.setTextColor(Color.BLACK);
				dot5.setTextColor(Color.BLACK);
				break;

			case 1:
				dot1.setTextColor(Color.BLACK);
				dot2.setTextColor(Color.WHITE);
				dot3.setTextColor(Color.BLACK);
				dot4.setTextColor(Color.BLACK);
				dot5.setTextColor(Color.BLACK);
				break;

			case 2:
				dot1.setTextColor(Color.BLACK);
				dot2.setTextColor(Color.BLACK);
				dot3.setTextColor(Color.WHITE);
				dot4.setTextColor(Color.BLACK);
				dot5.setTextColor(Color.BLACK);
				break;
				
			case 3:
				dot1.setTextColor(Color.BLACK);
				dot2.setTextColor(Color.BLACK);
				dot3.setTextColor(Color.BLACK);
				dot4.setTextColor(Color.WHITE);
				dot5.setTextColor(Color.BLACK);
				break;
				
			case 4:
				dot1.setTextColor(Color.BLACK);
				dot2.setTextColor(Color.BLACK);
				dot3.setTextColor(Color.BLACK);
				dot4.setTextColor(Color.BLACK);
				dot5.setTextColor(Color.WHITE);
				break;

			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}
}
