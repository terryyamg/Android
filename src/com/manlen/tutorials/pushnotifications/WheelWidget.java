package com.manlen.tutorials.pushnotifications;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WheelWidget extends Activity {
	// Scrolling flag
	private boolean scrolling = false;
	private int indexFor1;
	private Button goButton;
	private int no1 = 1, no2 = 0, no3 = 0;
	private TextView wheeltv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 字型 */
		Typeface fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");
		
		setContentView(R.layout.cities_layout);
		wheeltv = (TextView) findViewById(R.id.wheeltv);
		goButton = (Button) findViewById(R.id.goButton);
		
		wheeltv.setTypeface(fontch); //字型
		goButton.setTypeface(fontch);
		
		goButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				goToGoogleMap();
			}
		});
		final WheelView wheel1 = (WheelView) findViewById(R.id.wheel1);// 第一個
		wheel1.setVisibleItems(3);
		wheel1.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = new String[][] { // 第二個字
		// 0基隆
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 1台北
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 2新北
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 3桃園
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 4新竹市
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 5新竹縣
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 6苗栗
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 7台中
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 8南投
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 9彰化
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 10雲林
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 11嘉義
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 12台南
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 13高雄
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 14屏東
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 15台東
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 16花蓮
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 17宜蘭
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 18澎湖
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 19金門
				new String[] { "食", "衣", "住", "行", "育", "樂" },
				// 20連江
				new String[] { "食", "衣", "住", "行", "育", "樂" } };
		final String storeName[][][] = new String[][][] { // 第二個字
		// 0基隆
				new String[][] {
						new String[] { "99度a基隆孝一店", "99度a基隆東信店", "99度a七堵明德店",
								"99度a基隆義二店", "99度a基隆樂一店", "99度a基隆長庚店",
								"99度a七堵百福店", "99度a基隆新豐店" },
						new String[] { "基隆衣" }, new String[] { "基隆住" },
						new String[] { "基隆行" }, new String[] { "基隆育" },
						new String[] { "基隆樂" } },
				// 1台北
				new String[][] {
						new String[] { "99度a台北光復北店", "99度a木柵木新店", "99度a台北安和店",
								"99度a東湖康樂店", "99度a台北京華店", "99度a台北景美店",
								"99度a台北莊敬店", "99度a內湖文德店", "99度a台北永吉店",
								"99度a木柵秀明店", "99度a萬華萬大店", "99度a台北龍江店",
								"99度a台北中研店", "99度a台北榮星店", "99度a台北民族店",
								"99度a台北民生店", "99度a台北吉林店", "99度a台北錦州店",
								"99度a台北長安店", "99度a台北中華店", "99度a台北長安東店",
								"99度a內湖康寧店", "99度a台北汀洲店", "99度a台北漢中店",
								"99度a萬華廣州店", "99度a士林劍潭店", "99度a台北雨聲店",
								"99度a北投育仁店", "99度a台北延平店", "99度a天母德行店",
								"99度a台北同德店", "99度a台北和平東店", "99度a台北寶興店",
								"99度a台北永春店", "99度a台北三民店", "99度a北投中和店",
								"99度a士林文林店", "99度a大安微風店", "99度a台北寧波東店",
								"99度a台北微風南京店", "99度a士林基河店" },
						new String[] { "台北衣" }, new String[] { "台北住" },
						new String[] { "台北行" }, new String[] { "台北育" },
						new String[] { "台北樂" } },
				// 2新北
				new String[][] {
						new String[] { "99度a永和保平店", "99度a永和永貞店", "99度a永和頂溪店",
								"99度a永和得和店", "99度a中和圓通店", "99度a中和南華店",
								"99度a中和環球店", "99度a中和員山店", "99度a中和興南店",
								"99度a新店碧潭店", "99度a板橋民德店", "99度a永和中正店",
								"99度a三重集美店", "99度a三重中華店", "99度a三重仁政店",
								"99度a板橋國光店", "99度a三重重新店", "99度a三重中正店",
								"99度a三重重陽店", "99度a新店民權店", "99度a樹林中山店",
								"99度a新店中正店", "99度a新莊昌平店", "99度a蘆洲中興店",
								"99度a新店安康店", "99度a新店七張店", "99度a新莊龍安店",
								"99度a新莊新泰店", "99度a新莊富國店", "99度a新莊化成店",
								"99度a林口竹林店", "99度a新莊幸福店", "99度a新莊中平店",
								"99度a板橋四維店", "99度a板橋大仁店", "99度a板橋四川店",
								"99度a板橋文化店", "99度a鶯歌鶯桃店", "99度a板橋大同店",
								"99度a板橋中山店", "99度a板橋南雅店", "99度a板橋新海店",
								"99度a板橋仁化店", "99度a蘆洲光華店", "99度a蘆洲復興店",
								"99度a蘆洲長安店", "99度a土城裕民店", "99度a萬里野柳店",
								"99度a樹林中華店", "99度a樹林保安店", "99度a樹林金門店",
								"99度a三重仁愛店", "99度a汐止中興店", "99度a汐止新台五店",
								"99度a汐止樟樹店", "99度a汐止建成店", "99度a台北八里店",
								"99度a台北深坑店", "99度a金山中山店", "99度a新莊建安店",
								"99度a泰山明志店", "99度a台北泰山店", "99度a五股成泰店",
								"99度a淡水竹圍店", "99度a淡水中山店", "99度a淡水學府店",
								"99度a三峽民生店", "99度a三峽文化店", "99度a三峽中華店",
								"99度a鶯歌國慶店", "99度a瑞芳明燈店", "99度a板橋三民店",
								"99度a新店安和店" }, new String[] { "新北衣" },
						new String[] { "新北住" }, new String[] { "新北行" },
						new String[] { "新北育" }, new String[] { "新北樂" }, },
				// 3桃園
				new String[][] {
						new String[] { "99度a桃園北興店", "99度a桃園中華店", "99度a桃園縣府店",
								"99度a桃園經國店", "99度a桃園桃鶯店", "99度a桃園大業店",
								"99度a桃園龍安店", "99度a桃園中正店", "99度a中壢中原店",
								"99度a內壢中華店", "99度a中壢中央東店", "99度a中壢環中店",
								"99度a中壢龍慈店", "99度a中壢民族(高榮)店", "99度a中壢元化店",
								"99度a中壢龍岡店", "99度a中壢中山店", "99度a中壢振興店",
								"99度a八德介壽店", "99度a平鎮中豐店", "99度a新屋中山店",
								"99度a中壢壢中店", "99度a桃園萬壽店", "99度a桃園八德店",
								"99度a桃園福竹店", "99度a南崁南竹店", "99度a桃園南崁店",
								"99度a大園中山店", "99度a觀音新坡店", "99度a中壢健行店",
								"99度a楊梅新成店", "99度a楊梅中山店", "99度a桃園泰昌店",
								"99度a林口長庚店", "99度a龍潭北龍店", "99度a迴龍龍華店",
								"99度a平鎮延平店", "99度a平鎮金陵店", "99度a楊梅埔心店",
								"99度a平鎮文化店", "99度a中壢福州店" }, { "桃園衣" },
						new String[] { "桃園住" }, new String[] { "桃園行" },
						new String[] { "桃園育" }, new String[] { "桃園樂" } },
				// 4新竹市
				new String[][] {
						new String[] { "99度a新竹竹科店", "99度a新竹民權店", "99度a新竹中正店",
								"99度a新竹經國店", "99度a新竹中華店" },
						new String[] { "新竹市衣" }, new String[] { "新竹市住" },
						new String[] { "新竹市行" }, new String[] { "新竹市育" },
						new String[] { "新竹市樂" } },
				// 5新竹縣
				new String[][] {
						new String[] { "99度a竹北博愛店", "99度a竹北光明店", "99度a新豐建興店",
								"99度a湖口忠孝店", "99度a竹東長春店", "99度a關西正義店",
								"99度a湖口中華店" }, new String[] { "新竹縣衣" },
						new String[] { "新竹縣住" }, new String[] { "新竹縣行" },
						new String[] { "新竹縣育" }, new String[] { "新竹縣樂" } },
				// 6苗栗
				new String[][] {
						new String[] { "99度a苗栗為公店", "99度a苗栗中正店", "99度a竹南光復店",
								"99度a頭份中正店", "99度a頭份中央店", "99度a苑裡中山店" },
						new String[] { "苗栗衣" }, new String[] { "苗栗住" },
						new String[] { "苗栗行" }, new String[] { "苗栗育" },
						new String[] { "通霄海水浴場" } },
				// 7台中
				new String[][] {
						new String[] { "99度a台中健行店", "99度a台中復興店", "99度a台中東勢店",
								"99度a台中青海店", "99度a台中大肚店", "99度a台中高工店",
								"99度a台中一中店", "99度a台中友達店", "99度a台中中科店",
								"99度a台中健行店", "99度a台中潭子店", "99度a台中逢甲店",
								"99度a台中漢口店", "99度a台中大業店", "99度a台中昌平店",
								"99度a台中水湳店", "99度a台中嶺東店", "99度a台中大墩店",
								"99度a台中崇德店", "99度a台中清水店", "99度a台中黎明店",
								"99度a台中北平店", "99度a台中樂業店", "99度a大里國光店",
								"99度a太平中山店", "99度a大里成功店", "99度a豐原南陽店",
								"99度a台中西屯店", "99度a太平太平店", "99度a大里中興店",
								"99度a大雅學府店", "99度a霧峰吉峰店", "99度a大里益民店",
								"99度a台中東海店", "99度a霧峰中正店", "99度a台中醫院店",
								"99度a烏日中山店", "99度a大甲大甲店", "99度a台中公益店",
								"99度a大里永隆店", "99度a梧棲文化店", "99度a豐原中興店",
								"99度a台中沙鹿店", "99度a台中愛買店", "99度a豐原廟東店" },
						new String[] { "台中衣" }, new String[] { "台中住" },
						new String[] { "台中行" }, new String[] { "台中育" },
						new String[] { "台中樂" } },
				// 8南投
				new String[][] {
						new String[] { "99度a南投大同店", "99度a草屯中山店", "99度a埔里中正店",
								"99度a南投竹山店", "99度a南投中興店", "99度a南投水里店",
								"99度a名間彰南店", "99度a日月潭水社店" },
						new String[] { "南投衣" }, new String[] { "南投住" },
						new String[] { "南投行" }, new String[] { "南投育" },
						new String[] { "南投樂" } },
				// 9彰化
				new String[][] {
						new String[] { "99度a彰化和平店", "99度a彰化中正店", "99度a彰化彰基店",
								"99度a員林萬年店", "99度a員林靜修店", "99度a溪湖平和店",
								"99度a彰化田中店", "99度a彰化和美店", "99度a鹿港中正店",
								"99度a彰濱秀傳店", "99度a彰化花壇店", "99度a彰化二林店",
								"99度a彰化秀水店", "99度a彰化社頭店", "99度a彰化永靖店",
								"99度a員林大同店", "99度a彰化中民店", "99度a彰化彰美店",
								"99度a鹿港民族店" }, new String[] { "彰化衣" },
						new String[] { "彰化住" }, new String[] { "彰化行" },
						new String[] { "彰化育" }, new String[] { "彰化樂" } },
				// 10雲林
				new String[][] {
						new String[] { "99度a斗南延平店", "99度a斗六站前店", "99度a雲林麥寮店",
								"99度a雲林北港店", "99度a西螺中山店", "99度a虎尾林森店",
								"99度a斗六民生南店", "99度a土庫建國店" },
						new String[] { "雲林衣" }, new String[] { "雲林住" },
						new String[] { "雲林行" }, new String[] { "雲林育" },
						new String[] { "雲林樂" } },
				// 11嘉義
				new String[][] {
						new String[] { "99度a嘉義朴子店", "99度a嘉義民雄店", "99度a嘉義水上店",
								"99度a新港奉天店", "99度a嘉義民族店", "99度a嘉義中山店",
								"99度a嘉義彌陀店", "99度a嘉義友愛店", "99度a嘉義nice店",
								"99度a嘉義仁愛店" }, new String[] { "嘉義衣" },
						new String[] { "嘉義住" }, new String[] { "嘉義行" },
						new String[] { "嘉義育" }, new String[] { "嘉義樂" } },
				// 12台南
				new String[][] {
						new String[] { "99度a永康南科店", "99度a永康小東店", "99度a台南關廟店",
								"99度a永康崑山店", "99度a台南柳營店", "99度a台南新化店",
								"99度a仁德中正店", "99度a台南新市店", "99度a台南中華東店",
								"99度a台南歸仁店", "99度a麻豆中華店", "99度a新營民權店",
								"99度a台南善化店", "99度a台南佳里店", "99度a南科聯研店",
								"99度a台南安南店", "99度a台南東寧店", "99度a台南健康店",
								"99度a台南北門店", "99度a台南小北店", "99度a台南建平店",
								"99度a台南成功店", "99度a永康中華店", "99度a台南東門店" },
						new String[] { "台南衣" }, new String[] { "台南住" },
						new String[] { "台南行" }, new String[] { "台南育" },
						new String[] { "台南樂" } },
				// 13高雄
				new String[][] {
						new String[] { "99度a高雄高醫大店", "99度a路竹忠孝店", "99度a高雄中正店",
								"99度a鳳山文濱店", "99度a鳳山五甲店", "99度a高雄林園店",
								"99度a高雄仁武店", "99度a高雄旗山店", "99度a楠梓旗楠店",
								"99度a高雄岡山店", "99度a高雄右昌店", "99度a仁武仁雄店",
								"99度a鳳山中山店", "99度a高雄七賢店", "少那之高雄中正門市" },
						new String[] { "高雄衣" }, new String[] { "高雄住" },
						new String[] { "高雄行" }, new String[] { "高雄育" },
						new String[] { "高雄樂" } },
				// 14屏東
				new String[][] {
						new String[] { "99度a屏東枋寮店", "99度a屏東恆春店", "99度a屏東中正店",
								"99度a屏東麟洛店", "99度a屏東和平店", "99度a屏東潮州店",
								"99度a屏東仁愛店", "99度a屏東內埔店", "99度a屏東萬丹店" },
						new String[] { "屏東衣" }, new String[] { "屏東住" },
						new String[] { "屏東行" }, new String[] { "屏東育" },
						new String[] { "屏東樂" } },
				// 15台東
				new String[][] { new String[] { "99度a台東中華店" },
						new String[] { "台東衣" }, new String[] { "台東住" },
						new String[] { "台東行" }, new String[] { "台東育" },
						new String[] { "台東樂" } },
				// 16花蓮
				new String[][] {
						new String[] { "99度a花蓮中山店", "99度a花蓮中正店", "99度a花蓮和平店" },
						new String[] { "花蓮衣" }, new String[] { "花蓮住" },
						new String[] { "花蓮行" }, new String[] { "花蓮育" },
						new String[] { "花蓮樂" } },
				// 17宜蘭
				new String[][] {
						new String[] { "99度a宜蘭復興店", "99度a宜蘭站前店", "99度a羅東民權店",
								"99度a羅東站前店", "99度a頭城青雲店", "99度a宜蘭礁溪店" },
						new String[] { "宜蘭衣" }, new String[] { "宜蘭住" },
						new String[] { "宜蘭行" }, new String[] { "宜蘭育" },
						new String[] { "宜蘭樂" } },
				// 18澎湖
				new String[][] { new String[] { "99度a澎湖馬公店" },
						new String[] { "澎湖衣" }, new String[] { "澎湖住" },
						new String[] { "澎湖行" }, new String[] { "澎湖育" },
						new String[] { "澎湖樂" } },
				// 19金門
				new String[][] { new String[] { "99度a金門民族店" },
						new String[] { "金門衣" }, new String[] { "金門住" },
						new String[] { "金門行" }, new String[] { "金門育" },
						new String[] { "金門樂" } },
				// 20連江
				new String[][] { new String[] { "連江食" },
						new String[] { "連江衣" }, new String[] { "連江住" },
						new String[] { "連江行" }, new String[] { "連江育" },
						new String[] { "連江樂" } } };
		final WheelView wheel2 = (WheelView) findViewById(R.id.wheel2); // 第二個
		wheel2.setVisibleItems(5); // 設定可見數目
		final WheelView wheel3 = (WheelView) findViewById(R.id.wheel3); // 第三個
		wheel3.setVisibleItems(5);

		// 第一個改變監聽
		wheel1.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateCities(wheel2, cities, newValue);
					indexFor1 = newValue;
				}
			}
		});
		// 第二個改變監聽
		wheel2.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue2) {
				if (!scrolling) {
					updateCities2(wheel3, storeName, indexFor1, newValue2);
				}
			}
		});
		// 第一個滾動監聽
		wheel1.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				updateCities(wheel2, cities, wheel1.getCurrentItem()); // 帶動第二個
				updateCities2(wheel3, storeName, wheel1.getCurrentItem(),// 帶動第三個
						wheel2.getCurrentItem());
				no1 = wheel1.getCurrentItem();
				no2 = wheel2.getCurrentItem();
				no3 = wheel3.getCurrentItem();

			}
		});
		// 第二個滾動監聽
		wheel2.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				updateCities2(wheel3, storeName, wheel1.getCurrentItem(),
						wheel2.getCurrentItem());
				no1 = wheel1.getCurrentItem();
				no2 = wheel2.getCurrentItem();
				no3 = wheel3.getCurrentItem();

			}
		});
		// 第三個滾動監聽
		wheel3.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				no1 = wheel1.getCurrentItem();
				no2 = wheel2.getCurrentItem();
				no3 = wheel3.getCurrentItem();

			}
		});
		wheel1.setCurrentItem(1);
		wheel2.setCurrentItem(0);
		wheel3.setCurrentItem(0);
	}

	/**
	 * Updates the city wheel
	 */
	// 第一個改變帶動第二個
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(cities[index].length / 2); // 帶動後的起始位置

	}

	// 第二個改變帶動第三個
	private void updateCities2(WheelView city, String cities[][][], int index,
			int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(cities[index][index2].length / 2);// 帶動後的起始位置

	}

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = // 第一個字
		new String[] { "基隆市", "台北市", "新北市", "桃園縣市", "新竹市", "新竹縣", "苗栗縣", "台中市",
				"南投縣市", "彰化縣市", "雲林縣市", "嘉義縣市", "台南市", "高雄市", "屏東縣市", "台東縣市",
				"花蓮縣市", "宜蘭縣市", "澎湖縣", "金門縣", "連江縣" };

		// Countries flags

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.country_layout, NO_RESOURCE);

			setItemTextResource(R.id.country_name);
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}

	// 送出
	void goToGoogleMap() {
		Intent intent = new Intent(this, GoToGoogleMap.class);
		intent.putExtra("check", "wheel");
		intent.putExtra("no1", no1);
		intent.putExtra("no2", no2);
		intent.putExtra("no3", no3);
		startActivity(intent);
	}
}
