package com.manlen.tutorials.pushnotifications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

public class MainActivity extends FragmentActivity implements
		OnMarkerClickListener {
	/* update */
	protected static final int UPDATA_CLIENT = 0;
	protected static final int CHECK_UPDATE = 1;
	protected static final int DOWN_ERROR = 0;
	/** Called when the activity is first created. */
	private int serverVersion = 1; // 現在版本
	private int newServerVersion; // 最新版本
	private String downLoadApkUrl = "https://play.google.com/store/apps/details?id=com.manlen.tutorials.pushnotifications"; // 放置最新檔案網址

	/* 建立桌面捷徑 */

	/* tab1 */
	
	/* tab2 */
	private GoogleMap map;
	int spinnerNumber = 0; // 初次進map不使用下拉選單
	private float coordinate[][][][] = { // [縣市][食衣住行育樂][店家][座標]
			{
					{
							{ 0, 0, (float) 25.1288160, (float) 121.7404860 },// 基隆食
							{ 0, 0, (float) 25.1286310, (float) 121.7594120 },
							{ 0, 0, (float) 25.0972380, (float) 121.7122780 },
							{ 0, 0, (float) 25.1305950, (float) 121.7449390 },
							{ 0, 0, (float) 25.1316830, (float) 121.7346190 },
							{ 0, 0, (float) 25.1217490, (float) 121.7241710 },
							{ 0, 0, (float) 25.0816850, (float) 121.6971980 },
							{ 0, 0, (float) 25.1357140, (float) 121.7872250 } },
					{ { 0, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 0, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 0, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 0, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 0, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 1, 0, (float) 25.0574640, (float) 121.5554840 },// 台北食
							{ 1, 0, (float) 24.9836380, (float) 121.5673320 },
							{ 1, 0, (float) 25.0310780, (float) 121.5522090 },
							{ 1, 0, (float) 25.0693920, (float) 121.6184910 },
							{ 1, 0, (float) 25.0487230, (float) 121.5639450 },
							{ 1, 0, (float) 24.9916710, (float) 121.5412270 },
							{ 1, 0, (float) 25.0271960, (float) 121.5662010 },
							{ 1, 0, (float) 25.0784370, (float) 121.5788260 },
							{ 1, 0, (float) 25.0456920, (float) 121.5753850 },
							{ 1, 0, (float) 24.9889690, (float) 121.5635400 },
							{ 1, 0, (float) 25.0215250, (float) 121.4986180 },
							{ 1, 0, (float) 25.0557780, (float) 121.5407160 },
							{ 1, 0, (float) 25.0520410, (float) 121.6159130 },
							{ 1, 0, (float) 25.0660890, (float) 121.5408550 },
							{ 1, 0, (float) 25.0687160, (float) 121.5160600 },
							{ 1, 0, (float) 25.0574020, (float) 121.5196630 },
							{ 1, 0, (float) 25.0600090, (float) 121.5304280 },
							{ 1, 0, (float) 25.0601810, (float) 121.5246820 },
							{ 1, 0, (float) 25.0503300, (float) 121.5199610 },
							{ 1, 0, (float) 25.0279940, (float) 121.5057920 },
							{ 1, 0, (float) 25.0483360, (float) 121.5423050 },
							{ 1, 0, (float) 25.0793940, (float) 121.5939670 },
							{ 1, 0, (float) 25.0241710, (float) 121.5219070 },
							{ 1, 0, (float) 25.0416000, (float) 121.5072560 },
							{ 1, 0, (float) 25.0366250, (float) 121.5004940 },
							{ 1, 0, (float) 25.0805740, (float) 121.5225480 },
							{ 1, 0, (float) 25.1021360, (float) 121.5291810 },
							{ 1, 0, (float) 25.1347990, (float) 121.4997040 },
							{ 1, 0, (float) 25.0882690, (float) 121.5087340 },
							{ 1, 0, (float) 25.1097880, (float) 121.5340120 },
							{ 1, 0, (float) 25.0450650, (float) 121.5840020 },
							{ 1, 0, (float) 25.0196760, (float) 121.5573100 },
							{ 1, 0, (float) 25.0258820, (float) 121.4942980 },
							{ 1, 0, (float) 25.0414770, (float) 121.5793780 },
							{ 1, 0, (float) 25.0606460, (float) 121.5634140 },
							{ 1, 0, (float) 25.1410140, (float) 121.4996070 },
							{ 1, 0, (float) 25.0965010, (float) 121.5241070 },
							{ 1, 0, (float) 25.0448380, (float) 121.5460360 },
							{ 1, 0, (float) 25.0322470, (float) 121.5200250 },
							{ 1, 0, (float) 25.0525490, (float) 121.5474910 },
							{ 1, 0, (float) 25.0887840, (float) 121.5232470 } },
					{ { 1, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 1, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 1, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 1, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 1, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 2, 0, (float) 25.0076090, (float) 121.5122990 }, // 新北食
							{ 2, 0, (float) 25.0039210, (float) 121.5146960 },
							{ 2, 0, (float) 25.0119100, (float) 121.5147650 },
							{ 2, 0, (float) 24.9995170, (float) 121.5230320 },
							{ 2, 0, (float) 24.9940720, (float) 121.4963270 },
							{ 2, 0, (float) 24.9974140, (float) 121.5056170 },
							{ 2, 0, (float) 25.0064940, (float) 121.4726490 },
							{ 2, 0, (float) 25.0010370, (float) 121.4813470 },
							{ 2, 0, (float) 24.9855340, (float) 121.5074410 },
							{ 2, 0, (float) 24.9546370, (float) 121.5395140 },
							{ 2, 0, (float) 25.0033300, (float) 121.4689700 },
							{ 2, 0, (float) 24.9980230, (float) 121.5163000 },
							{ 2, 0, (float) 25.0569260, (float) 121.4876570 },
							{ 2, 0, (float) 25.0625210, (float) 121.4837150 },
							{ 2, 0, (float) 25.0796200, (float) 121.4914080 },
							{ 2, 0, (float) 25.0185610, (float) 121.4611830 },
							{ 2, 0, (float) 25.0633100, (float) 121.5012910 },
							{ 2, 0, (float) 25.0692130, (float) 121.4781240 },
							{ 2, 0, (float) 25.0667240, (float) 121.4888570 },
							{ 2, 0, (float) 24.9830830, (float) 121.5352850 },
							{ 2, 0, (float) 24.9912160, (float) 121.4231210 },
							{ 2, 0, (float) 24.9695700, (float) 121.5457820 },
							{ 2, 0, (float) 25.0530290, (float) 121.4545830 },
							{ 2, 0, (float) 25.0873390, (float) 121.4816700 },
							{ 2, 0, (float) 24.9590100, (float) 121.5086800 },
							{ 2, 0, (float) 24.9742180, (float) 121.5427720 },
							{ 2, 0, (float) 25.0196890, (float) 121.4235880 },
							{ 2, 0, (float) 25.0404810, (float) 121.4458230 },
							{ 2, 0, (float) 25.0260390, (float) 121.4192620 },
							{ 2, 0, (float) 25.0547690, (float) 121.4658020 },
							{ 2, 0, (float) 25.0805070, (float) 121.3892160 },
							{ 2, 0, (float) 25.0494810, (float) 121.4584470 },
							{ 2, 0, (float) 25.0461220, (float) 121.4447780 },
							{ 2, 0, (float) 25.0251000, (float) 121.4654030 },
							{ 2, 0, (float) 25.0169910, (float) 121.4557730 },
							{ 2, 0, (float) 25.0031670, (float) 121.4598860 },
							{ 2, 0, (float) 25.0195290, (float) 121.4658740 },
							{ 2, 0, (float) 24.9692830, (float) 121.3292220 },
							{ 2, 0, (float) 25.0268560, (float) 121.4766050 },
							{ 2, 0, (float) 25.0146290, (float) 121.4718670 },
							{ 2, 0, (float) 25.0071850, (float) 121.4563910 },
							{ 2, 0, (float) 25.0270020, (float) 121.4585800 },
							{ 2, 0, (float) 25.0314510, (float) 121.4741690 },
							{ 2, 0, (float) 25.0822110, (float) 121.4651670 },
							{ 2, 0, (float) 25.0870710, (float) 121.4756190 },
							{ 2, 0, (float) 25.0826970, (float) 121.4593770 },
							{ 2, 0, (float) 24.9877970, (float) 121.4486160 },
							{ 2, 0, (float) 24.9980230, (float) 121.5163000 },
							{ 2, 0, (float) 24.9817900, (float) 121.4200110 },
							{ 2, 0, (float) 24.9949560, (float) 121.4227560 },
							{ 2, 0, (float) 24.9879310, (float) 121.4309640 },
							{ 2, 0, (float) 25.0820760, (float) 121.4892540 },
							{ 2, 0, (float) 25.0655870, (float) 121.6316200 },
							{ 2, 0, (float) 25.0629010, (float) 121.6556950 },
							{ 2, 0, (float) 25.0643570, (float) 121.6419530 },
							{ 2, 0, (float) 25.0728590, (float) 121.6636080 },
							{ 2, 0, (float) 25.1497410, (float) 121.4041050 },
							{ 2, 0, (float) 25.0011250, (float) 121.6124652 },
							{ 2, 0, (float) 25.2194180, (float) 121.6397470 },
							{ 2, 0, (float) 25.0242260, (float) 121.4266730 },
							{ 2, 0, (float) 25.0610120, (float) 121.4325390 },
							{ 2, 0, (float) 25.0360530, (float) 121.4224980 },
							{ 2, 0, (float) 25.0842670, (float) 121.4383760 },
							{ 2, 0, (float) 25.1379170, (float) 121.4602060 },
							{ 2, 0, (float) 25.1776110, (float) 121.4427070 },
							{ 2, 0, (float) 25.1749280, (float) 121.4467060 },
							{ 2, 0, (float) 24.9364270, (float) 121.3759330 },
							{ 2, 0, (float) 24.9362920, (float) 121.3704200 },
							{ 2, 0, (float) 24.9299180, (float) 121.3716670 },
							{ 2, 0, (float) 24.9540290, (float) 121.3508660 },
							{ 2, 0, (float) 25.1078380, (float) 121.8072060 },
							{ 2, 0, (float) 25.0161820, (float) 121.4793600 },
							{ 2, 0, (float) 24.9752330, (float) 121.5177000 } },
					{ { 2, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 2, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 2, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 2, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 2, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 3, 0, (float) 24.9980900, (float) 121.3087550 }, // 桃園食
							{ 3, 0, (float) 24.9904990, (float) 121.3099160 },
							{ 3, 0, (float) 24.9921610, (float) 121.3033710 },
							{ 3, 0, (float) 25.0203210, (float) 121.3034360 },
							{ 3, 0, (float) 24.9861390, (float) 121.3178880 },
							{ 3, 0, (float) 25.0073270, (float) 121.3160330 },
							{ 3, 0, (float) 24.9955850, (float) 121.2669630 },
							{ 3, 0, (float) 25.0156090, (float) 121.2987630 },
							{ 3, 0, (float) 24.9556110, (float) 121.2358490 },
							{ 3, 0, (float) 24.9715190, (float) 121.2542910 },
							{ 3, 0, (float) 24.9562770, (float) 121.2258490 },
							{ 3, 0, (float) 24.9649080, (float) 121.2585620 },
							{ 3, 0, (float) 24.9412790, (float) 121.2454510 },
							{ 3, 0, (float) 24.9578550, (float) 121.1666720 },
							{ 3, 0, (float) 24.9626050, (float) 121.2228110 },
							{ 3, 0, (float) 24.9308570, (float) 121.2541430 },
							{ 3, 0, (float) 24.9570490, (float) 121.2143570 },
							{ 3, 0, (float) 24.9452370, (float) 121.2196280 },
							{ 3, 0, (float) 24.9474660, (float) 121.2973550 },
							{ 3, 0, (float) 24.8992350, (float) 121.2109910 },
							{ 3, 0, (float) 24.9728680, (float) 121.1055860 },
							{ 3, 0, (float) 24.9615730, (float) 121.2113000 },
							{ 3, 0, (float) 24.9939390, (float) 121.3335140 },
							{ 3, 0, (float) 24.9626330, (float) 121.2998680 },
							{ 3, 0, (float) 25.0223020, (float) 121.2614100 },
							{ 3, 0, (float) 25.0468480, (float) 121.2913690 },
							{ 3, 0, (float) 25.0501290, (float) 121.2920410 },
							{ 3, 0, (float) 25.0639320, (float) 121.1972710 },
							{ 3, 0, (float) 25.0117440, (float) 121.1375640 },
							{ 3, 0, (float) 24.9411450, (float) 121.2318020 },
							{ 3, 0, (float) 24.9090990, (float) 121.1467570 },
							{ 3, 0, (float) 24.9082570, (float) 121.1692830 },
							{ 3, 0, (float) 24.9881340, (float) 121.2945930 },
							{ 3, 0, (float) 25.0615060, (float) 121.3645770 },
							{ 3, 0, (float) 24.8663970, (float) 121.2147060 },
							{ 3, 0, (float) 25.0174910, (float) 121.4028450 },
							{ 3, 0, (float) 24.9417140, (float) 121.2059580 },
							{ 3, 0, (float) 24.9494070, (float) 121.2228880 },
							{ 3, 0, (float) 24.9193420, (float) 121.1839310 },
							{ 3, 0, (float) 24.9559790, (float) 121.2024080 },
							{ 3, 0, (float) 24.9928120, (float) 121.2337670 } },
					{ { 3, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 3, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 3, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 3, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 3, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 4, 0, (float) 24.7750460, (float) 121.0274180 }, // 新竹市食
							{ 4, 0, (float) 24.8069090, (float) 120.9758850 },
							{ 4, 0, (float) 24.8120120, (float) 120.9642980 },
							{ 4, 0, (float) 24.8144900, (float) 120.9743510 },
							{ 4, 0, (float) 24.7888640, (float) 120.9299500 } },
					{ { 4, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 4, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 4, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 4, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 4, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 5, 0, (float) 24.8350340, (float) 121.0145440 }, // 新竹縣食
							{ 5, 0, (float) 24.8294770, (float) 121.0097050 },
							{ 5, 0, (float) 24.8734970, (float) 120.9953070 },
							{ 5, 0, (float) 24.9010840, (float) 121.0445450 },
							{ 5, 0, (float) 24.7447670, (float) 121.0824260 },
							{ 5, 0, (float) 24.7936360, (float) 121.1761840 },
							{ 5, 0, (float) 24.8723420, (float) 121.0102010 } },
					{ { 5, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 5, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 5, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 5, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 5, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{

							{ 6, 0, (float) 24.5716230, (float) 120.8305750 },// 苗栗食
							{ 6, 0, (float) 24.5515780, (float) 120.8161300 },
							{ 6, 0, (float) 24.6877670, (float) 120.8793820 },
							{ 6, 0, (float) 24.6839370, (float) 120.8772290 },
							{ 6, 0, (float) 24.6927220, (float) 120.9009030 },
							{ 6, 0, (float) 24.4421090, (float) 120.6512670 } },
					{ { 6, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 6, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 6, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 6, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 6, 5, (float) 24.4928170, (float) 120.6736930 } } },
			{
					{
							{ 7, 0, (float) 24.1593040, (float) 120.6798110 },// 台中食
							{ 7, 0, (float) 24.1342630, (float) 120.6828400 },
							{ 7, 0, (float) 24.2533680, (float) 120.8299100 },
							{ 7, 0, (float) 24.1655070, (float) 120.6530810 },
							{ 7, 0, (float) 24.1543310, (float) 120.5443510 },
							{ 7, 0, (float) 24.1139960, (float) 120.6665160 },
							{ 7, 0, (float) 24.1139960, (float) 120.6665160 },
							{ 7, 0, (float) 24.2085010, (float) 120.6156490 },
							{ 7, 0, (float) 24.1901530, (float) 120.6120910 },
							{ 7, 0, (float) 24.1593040, (float) 120.6798110 },
							{ 7, 0, (float) 24.2127990, (float) 120.7005530 },
							{ 7, 0, (float) 24.1749320, (float) 120.6488460 },
							{ 7, 0, (float) 24.1665460, (float) 120.6640610 },
							{ 7, 0, (float) 24.1540230, (float) 120.6348880 },
							{ 7, 0, (float) 24.1767670, (float) 120.6904490 },
							{ 7, 0, (float) 24.1765870, (float) 120.6696700 },
							{ 7, 0, (float) 24.1476490, (float) 120.6116200 },
							{ 7, 0, (float) 24.1397810, (float) 120.6497540 },
							{ 7, 0, (float) 24.1685650, (float) 120.6848940 },
							{ 7, 0, (float) 24.2717120, (float) 120.5762700 },
							{ 7, 0, (float) 24.1413880, (float) 120.6382570 },
							{ 7, 0, (float) 24.1714520, (float) 120.6744920 },
							{ 7, 0, (float) 24.1364990, (float) 120.7063500 },
							{ 7, 0, (float) 24.1043740, (float) 120.6810890 },
							{ 7, 0, (float) 24.1482260, (float) 120.7030430 },
							{ 7, 0, (float) 24.0916280, (float) 120.7033240 },
							{ 7, 0, (float) 24.2473980, (float) 120.7239840 },
							{ 7, 0, (float) 24.1859340, (float) 120.6235390 },
							{ 7, 0, (float) 24.1250620, (float) 120.7134820 },
							{ 7, 0, (float) 24.1102460, (float) 120.6897180 },
							{ 7, 0, (float) 24.2265820, (float) 120.6482730 },
							{ 7, 0, (float) 24.0767240, (float) 120.7093460 },
							{ 7, 0, (float) 24.1113460, (float) 120.6838230 },
							{ 7, 0, (float) 24.1810600, (float) 120.5915700 },
							{ 7, 0, (float) 24.0644670, (float) 120.6986790 },
							{ 7, 0, (float) 24.1120210, (float) 120.6278530 },
							{ 7, 0, (float) 24.1062450, (float) 120.6249590 },
							{ 7, 0, (float) 24.3442310, (float) 120.6233380 },
							{ 7, 0, (float) 24.1510000, (float) 120.6536400 },
							{ 7, 0, (float) 24.1093540, (float) 120.6765650 },
							{ 7, 0, (float) 24.2550500, (float) 120.5308760 },
							{ 7, 0, (float) 24.2460400, (float) 120.7110080 },
							{ 7, 0, (float) 24.2426110, (float) 120.5610580 },
							{ 7, 0, (float) 24.1619921, (float) 120.6501857 },
							{ 7, 0, (float) 24.2651620, (float) 120.7202140 } },
					{ { 7, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 7, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 7, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 7, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 7, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 8, 0, (float) 23.9056200, (float) 120.6884100 }, // 南投食
							{ 8, 0, (float) 23.9778960, (float) 120.6830040 },
							{ 8, 0, (float) 23.9659870, (float) 120.9663930 },
							{ 8, 0, (float) 23.7580660, (float) 120.6868210 },
							{ 8, 0, (float) 23.9463000, (float) 120.6907280 },
							{ 8, 0, (float) 23.8118690, (float) 120.8521920 },
							{ 8, 0, (float) 23.8844010, (float) 120.6909830 },
							{ 8, 0, (float) 23.8664620, (float) 120.9111390 } },
					{ { 8, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 8, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 8, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 8, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 8, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 9, 0, (float) 24.0794190, (float) 120.5405520 }, // 彰化食
							{ 9, 0, (float) 24.0670460, (float) 120.5352150 },
							{ 9, 0, (float) 24.0708020, (float) 120.5430300 },
							{ 9, 0, (float) 23.9611490, (float) 120.5736910 },
							{ 9, 0, (float) 23.9621590, (float) 120.5675370 },
							{ 9, 0, (float) 23.9558140, (float) 120.4794690 },
							{ 9, 0, (float) 23.8608870, (float) 120.5827580 },
							{ 9, 0, (float) 24.1077280, (float) 120.4940650 },
							{ 9, 0, (float) 24.0569330, (float) 120.4401170 },
							{ 9, 0, (float) 24.0781630, (float) 120.4110610 },
							{ 9, 0, (float) 24.0237750, (float) 120.5413160 },
							{ 9, 0, (float) 23.8938390, (float) 120.3640150 },
							{ 9, 0, (float) 24.0370830, (float) 120.5019130 },
							{ 9, 0, (float) 23.8993000, (float) 120.5886910 },
							{ 9, 0, (float) 23.9183990, (float) 120.5460920 },
							{ 9, 0, (float) 23.9600810, (float) 120.5802590 },
							{ 9, 0, (float) 24.0839340, (float) 120.5472710 },
							{ 9, 0, (float) 24.1144860, (float) 120.4967130 },
							{ 9, 0, (float) 24.0544600, (float) 120.4347610 } },
					{ { 9, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 9, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 9, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 9, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 9, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 10, 0, (float) 23.6722880, (float) 120.4759330 }, // 雲林食
							{ 10, 0, (float) 23.7118910, (float) 120.5423500 },
							{ 10, 0, (float) 23.7508820, (float) 120.2512660 },
							{ 10, 0, (float) 23.5711880, (float) 120.3023940 },
							{ 10, 0, (float) 23.7999490, (float) 120.4620870 },
							{ 10, 0, (float) 23.7106630, (float) 120.4350030 },
							{ 10, 0, (float) 23.7001860, (float) 120.5368680 },
							{ 10, 0, (float) 23.6791670, (float) 120.3929430 } },
					{ { 10, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 10, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 10, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 10, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 10, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 11, 0, (float) 23.4611770, (float) 120.2425220 }, // 嘉義食
							{ 11, 0, (float) 23.5562190, (float) 120.4289800 },
							{ 11, 0, (float) 23.4336490, (float) 120.3988720 },
							{ 11, 0, (float) 23.5542900, (float) 120.3474840 },
							{ 11, 0, (float) 23.4767410, (float) 120.4556220 },
							{ 11, 0, (float) 23.4819810, (float) 120.4576100 },
							{ 11, 0, (float) 23.4687420, (float) 120.4661130 },
							{ 11, 0, (float) 23.4833450, (float) 120.4339190 },
							{ 11, 0, (float) 23.5017800, (float) 120.4496970 },
							{ 11, 0, (float) 23.4774390, (float) 120.4420070 } },
					{ { 11, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 11, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 11, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 11, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 11, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 12, 0, (float) 23.0414130, (float) 120.2391620 },// 台南市食
							{ 12, 0, (float) 22.9986350, (float) 120.2393130 },
							{ 12, 0, (float) 22.9658050, (float) 120.2532300 },
							{ 12, 0, (float) 23.0002280, (float) 120.2586450 },
							{ 12, 0, (float) 23.2790300, (float) 120.3204810 },
							{ 12, 0, (float) 23.0388530, (float) 120.3092700 },
							{ 12, 0, (float) 22.9690050, (float) 120.2524400 },
							{ 12, 0, (float) 23.0800810, (float) 120.2954460 },
							{ 12, 0, (float) 22.9756810, (float) 120.2248460 },
							{ 12, 0, (float) 22.9663000, (float) 120.2984990 },
							{ 12, 0, (float) 23.1837870, (float) 120.2471570 },
							{ 12, 0, (float) 23.3069400, (float) 120.3124430 },
							{ 12, 0, (float) 23.3069400, (float) 120.3124430 },
							{ 12, 0, (float) 23.1586140, (float) 120.1787770 },
							{ 12, 0, (float) 23.0956620, (float) 120.2818350 },
							{ 12, 0, (float) 23.0270520, (float) 120.1911810 },
							{ 12, 0, (float) 22.9909120, (float) 120.2246550 },
							{ 12, 0, (float) 22.9817360, (float) 120.1917570 },
							{ 12, 0, (float) 22.9986160, (float) 120.2123100 },
							{ 12, 0, (float) 23.0134420, (float) 120.2091820 },
							{ 12, 0, (float) 22.9902180, (float) 120.1687310 },
							{ 12, 0, (float) 23.0021980, (float) 120.1948830 },
							{ 12, 0, (float) 23.0160700, (float) 120.2293450 },
							{ 12, 0, (float) 22.9779280, (float) 120.2337220 } },
					{ { 12, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 12, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 12, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 12, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 12, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 13, 0, (float) 22.6442340, (float) 120.3087290 },// 高雄市食
							{ 13, 0, (float) 22.8602010, (float) 120.2594690 },
							{ 13, 0, (float) 22.6297090, (float) 120.3280480 },
							{ 13, 0, (float) 22.6424440, (float) 120.3490910 },
							{ 13, 0, (float) 22.5963340, (float) 120.3333480 },
							{ 13, 0, (float) 22.5152020, (float) 120.3952550 },
							{ 13, 0, (float) 22.7000860, (float) 120.3512410 },
							{ 13, 0, (float) 22.8880370, (float) 120.4817980 },
							{ 13, 0, (float) 22.7319970, (float) 120.3300180 },
							{ 13, 0, (float) 22.7996710, (float) 120.2927570 },
							{ 13, 0, (float) 22.7210040, (float) 120.2898950 },
							{ 13, 0, (float) 22.6334880, (float) 120.3624740 },
							{ 13, 0, (float) 22.6282660, (float) 120.3439930 },
							{ 13, 0, (float) 22.6318730, (float) 120.2925990 },
							{ 13, 0, (float) 22.6297370, (float) 120.3278820 } },
					{ { 13, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 13, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 13, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 13, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 13, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 14, 0, (float) 22.3667560, (float) 120.5930540 }, // 屏東食
							{ 14, 0, (float) 22.0078930, (float) 120.7434030 },
							{ 14, 0, (float) 22.6860210, (float) 120.4953680 },
							{ 14, 0, (float) 22.6619130, (float) 120.5080110 },
							{ 14, 0, (float) 22.6652460, (float) 120.4785270 },
							{ 14, 0, (float) 22.5543310, (float) 120.5403810 },
							{ 14, 0, (float) 22.6725680, (float) 120.4930480 },
							{ 14, 0, (float) 22.6761110, (float) 120.4941670 },
							{ 14, 0, (float) 22.5886740, (float) 120.4899090 } },
					{ { 14, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 14, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 14, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 14, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 14, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{ { 15, 0, (float) 22.7536810, (float) 121.1526210 } },// 台東食
					{ { 15, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 16, 0, (float) 23.9872900, (float) 121.6011890 }, // 花蓮食
							{ 16, 0, (float) 23.9787210, (float) 121.6099400 },
							{ 16, 0, (float) 23.9762380, (float) 121.6019000 } },
					{ { 16, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 17, 0, (float) 24.7487250, (float) 121.7509280 }, // 宜蘭食
							{ 17, 1, (float) 24.7556600, (float) 121.7577530 },
							{ 17, 2, (float) 24.6762740, (float) 121.7670570 },
							{ 17, 3, (float) 24.6781420, (float) 121.7724150 },
							{ 17, 4, (float) 24.8565540, (float) 121.8246770 },
							{ 17, 5, (float) 24.8282350, (float) 121.7725930 } },
					{ { 17, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 17, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 17, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 17, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 17, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{ { { 18, 0, (float) 23.5671890, (float) 119.5654420 } },
					{ { 18, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 18, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 18, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 18, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 18, 5, (float) 24.1260010, (float) 120.6628070 } } }, // 澎湖食
			{ { { 19, 0, (float) 24.4303000, (float) 118.3154460 } },
					{ { 19, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 5, (float) 24.1260010, (float) 120.6628070 } } },// 金門食
			{ { { 20, 0, (float) 26.1511110, (float) 119.9272220 } },
					{ { 20, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 5, (float) 24.1260010, (float) 120.6628070 } } } };
	private String[] type = new String[] { "基隆市", "台北市", "新北市", "桃園縣市", "新竹市",
			"新竹縣", "苗栗縣", "台中市", "南投縣市", "彰化縣市", "雲林縣市", "嘉義縣市", "台南市", "高雄市",
			"屏東縣市", "台東縣市", "花蓮縣市", "宜蘭縣市", "澎湖縣", "金門縣", "連江縣" };
	private String[] locationName1 = new String[] { "食", "衣", "住", "行", "育",
			"樂" };
	private String[] locationName2 = new String[] { "99度a基隆孝一店", "99度a基隆東信店",
			"99度a七堵明德店", "99度a基隆義二店", "99度a基隆樂一店", "99度a基隆長庚店", "99度a七堵百福店",
			"99度a基隆新豐店" };
	private String[][] type2 = new String[][] {
			// 0基隆
			{ "食", "衣", "住", "行", "育", "樂" },
			// 1台北
			{ "食", "衣", "住", "行", "育", "樂" },
			// 2新北
			{ "食", "衣", "住", "行", "育", "樂" },
			// 3桃園
			{ "食", "衣", "住", "行", "育", "樂" },
			// 4新竹市
			{ "食", "衣", "住", "行", "育", "樂" },
			// 5新竹縣
			{ "食", "衣", "住", "行", "育", "樂" },
			// 6苗栗
			{ "食", "衣", "住", "行", "育", "樂" },
			// 7台中
			{ "食", "衣", "住", "行", "育", "樂" },
			// 8南投
			{ "食", "衣", "住", "行", "育", "樂" },
			// 9彰化
			{ "食", "衣", "住", "行", "育", "樂" },
			// 10雲林
			{ "食", "衣", "住", "行", "育", "樂" },
			// 11嘉義
			{ "食", "衣", "住", "行", "育", "樂" },
			// 12台南
			{ "食", "衣", "住", "行", "育", "樂" },
			// 13高雄
			{ "食", "衣", "住", "行", "育", "樂" },
			// 14屏東
			{ "食", "衣", "住", "行", "育", "樂" },
			// 15台東
			{ "食", "衣", "住", "行", "育", "樂" },
			// 16花蓮
			{ "食", "衣", "住", "行", "育", "樂" },
			// 17宜蘭
			{ "食", "衣", "住", "行", "育", "樂" },
			// 18澎湖
			{ "食", "衣", "住", "行", "育", "樂" },
			// 19金門
			{ "食", "衣", "住", "行", "育", "樂" },
			// 20連江
			{ "食", "衣", "住", "行", "育", "樂" }, };
	private String[][][] type3 = new String[][][] {
			// 0基隆
			{
					{ "99度a基隆孝一店", "99度a基隆東信店", "99度a七堵明德店", "99度a基隆義二店",
							"99度a基隆樂一店", "99度a基隆長庚店", "99度a七堵百福店", "99度a基隆新豐店" },
					{ "基隆衣" }, { "基隆住" }, { "基隆行" }, { "基隆育" }, { "基隆樂" } },
			// 1台北
			{
					{ "99度a台北光復北店", "99度a木柵木新店", "99度a台北安和店", "99度a東湖康樂店",
							"99度a台北京華店", "99度a台北景美店", "99度a台北莊敬店", "99度a內湖文德店",
							"99度a台北永吉店", "99度a木柵秀明店", "99度a萬華萬大店", "99度a台北龍江店",
							"99度a台北中研店", "99度a台北榮星店", "99度a台北民族店", "99度a台北民生店",
							"99度a台北吉林店", "99度a台北錦州店", "99度a台北長安店", "99度a台北中華店",
							"99度a台北長安東店", "99度a內湖康寧店", "99度a台北汀洲店",
							"99度a台北漢中店", "99度a萬華廣州店", "99度a士林劍潭店", "99度a台北雨聲店",
							"99度a北投育仁店", "99度a台北延平店", "99度a天母德行店", "99度a台北同德店",
							"99度a台北和平東店", "99度a台北寶興店", "99度a台北永春店",
							"99度a台北三民店", "99度a北投中和店", "99度a士林文林店", "99度a大安微風店",
							"99度a台北寧波東店", "99度a台北微風南京店", "99度a士林基河店" },
					{ "台北衣" }, { "台北住" }, { "台北行" }, { "台北育" }, { "台北樂" } },
			// 2新北
			{
					{ "99度a永和保平店", "99度a永和永貞店", "99度a永和頂溪店", "99度a永和得和店",
							"99度a中和圓通店", "99度a中和南華店", "99度a中和環球店", "99度a中和員山店",
							"99度a中和興南店", "99度a新店碧潭店", "99度a板橋民德店", "99度a永和中正店",
							"99度a三重集美店", "99度a三重中華店", "99度a三重仁政店", "99度a板橋國光店",
							"99度a三重重新店", "99度a三重中正店", "99度a三重重陽店", "99度a新店民權店",
							"99度a樹林中山店", "99度a新店中正店", "99度a新莊昌平店", "99度a蘆洲中興店",
							"99度a新店安康店", "99度a新店七張店", "99度a新莊龍安店", "99度a新莊新泰店",
							"99度a新莊富國店", "99度a新莊化成店", "99度a林口竹林店", "99度a新莊幸福店",
							"99度a新莊中平店", "99度a板橋四維店", "99度a板橋大仁店", "99度a板橋四川店",
							"99度a板橋文化店", "99度a鶯歌鶯桃店", "99度a板橋大同店", "99度a板橋中山店",
							"99度a板橋南雅店", "99度a板橋新海店", "99度a板橋仁化店", "99度a蘆洲光華店",
							"99度a蘆洲復興店", "99度a蘆洲長安店", "99度a土城裕民店", "99度a萬里野柳店",
							"99度a樹林中華店", "99度a樹林保安店", "99度a樹林金門店", "99度a三重仁愛店",
							"99度a汐止中興店", "99度a汐止新台五店", "99度a汐止樟樹店",
							"99度a汐止建成店", "99度a台北八里店", "99度a台北深坑店", "99度a金山中山店",
							"99度a新莊建安店", "99度a泰山明志店", "99度a台北泰山店", "99度a五股成泰店",
							"99度a淡水竹圍店", "99度a淡水中山店", "99度a淡水學府店", "99度a三峽民生店",
							"99度a三峽文化店", "99度a三峽中華店", "99度a鶯歌國慶店", "99度a瑞芳明燈店",
							"99度a板橋三民店", "99度a新店安和店" }, { "新北衣" }, { "新北住" },
					{ "新北行" }, { "新北育" }, { "新北樂" } },
			// 3桃園
			{
					{ "99度a桃園北興店", "99度a桃園中華店", "99度a桃園縣府店", "99度a桃園經國店",
							"99度a桃園桃鶯店", "99度a桃園大業店", "99度a桃園龍安店", "99度a桃園中正店",
							"99度a中壢中原店", "99度a內壢中華店", "99度a中壢中央東店",
							"99度a中壢環中店", "99度a中壢龍慈店", "99度a中壢民族(高榮)店",
							"99度a中壢元化店", "99度a中壢龍岡店", "99度a中壢中山店", "99度a中壢振興店",
							"99度a八德介壽店", "99度a平鎮中豐店", "99度a新屋中山店", "99度a中壢壢中店",
							"99度a桃園萬壽店", "99度a桃園八德店", "99度a桃園福竹店", "99度a南崁南竹店",
							"99度a桃園南崁店", "99度a大園中山店", "99度a觀音新坡店", "99度a中壢健行店",
							"99度a楊梅新成店", "99度a楊梅中山店", "99度a桃園泰昌店", "99度a林口長庚店",
							"99度a龍潭北龍店", "99度a迴龍龍華店", "99度a平鎮延平店", "99度a平鎮金陵店",
							"99度a楊梅埔心店", "99度a平鎮文化店", "99度a中壢福州店" }, { "桃園衣" },
					{ "桃園住" }, { "桃園行" }, { "桃園育" }, { "桃園樂" } },
			// 4新竹市
			{
					{ "99度a新竹竹科店", "99度a新竹民權店", "99度a新竹中正店", "99度a新竹經國店",
							"99度a新竹中華店" }, { "新竹市衣" }, { "新竹市住" }, { "新竹市行" },
					{ "新竹市育" }, { "新竹市樂" } },
			// 5新竹縣
			{
					{ "99度a竹北博愛店", "99度a竹北光明店", "99度a新豐建興店", "99度a湖口忠孝店",
							"99度a竹東長春店", "99度a關西正義店", "99度a湖口中華店" },
					{ "新竹縣衣" }, { "新竹縣住" }, { "新竹縣行" }, { "新竹縣育" }, { "新竹縣樂" } },
			// 6苗栗
			{
					{ "99度a苗栗為公店", "99度a苗栗中正店", "99度a竹南光復店", "99度a頭份中正店",
							"99度a頭份中央店", "99度a苑裡中山店" }, { "苗栗衣" }, { "苗栗住" },
					{ "苗栗行" }, { "苗栗育" }, { "通霄海水浴場" } },
			// 7台中
			{
					{ "99度a台中健行店", "99度a台中復興店", "99度a台中東勢店", "99度a台中青海店",
							"99度a台中大肚店", "99度a台中高工店", "99度a台中一中店", "99度a台中友達店",
							"99度a台中中科店", "99度a台中健行店", "99度a台中潭子店", "99度a台中逢甲店",
							"99度a台中漢口店", "99度a台中大業店", "99度a台中昌平店", "99度a台中水湳店",
							"99度a台中嶺東店", "99度a台中大墩店", "99度a台中崇德店", "99度a台中清水店",
							"99度a台中黎明店", "99度a台中北平店", "99度a台中樂業店", "99度a大里國光店",
							"99度a太平中山店", "99度a大里成功店", "99度a豐原南陽店", "99度a台中西屯店",
							"99度a太平太平店", "99度a大里中興店", "99度a大雅學府店", "99度a霧峰吉峰店",
							"99度a大里益民店", "99度a台中東海店", "99度a霧峰中正店", "99度a台中醫院店",
							"99度a烏日中山店", "99度a大甲大甲店", "99度a台中公益店", "99度a大里永隆店",
							"99度a梧棲文化店", "99度a豐原中興店", "99度a台中沙鹿店", "99度a台中愛買店",
							"99度a豐原廟東店" }, { "台中衣" }, { "台中住" }, { "台中行" },
					{ "台中育" }, { "台中樂" } },
			// 8南投
			{
					{ "99度a南投大同店", "99度a草屯中山店", "99度a埔里中正店", "99度a南投竹山店",
							"99度a南投中興店", "99度a南投水里店", "99度a名間彰南店", "99度a日月潭水社店" },
					{ "南投衣" }, { "南投住" }, { "南投行" }, { "南投育" }, { "南投樂" } },
			// 9彰化
			{
					{ "99度a彰化和平店", "99度a彰化中正店", "99度a彰化彰基店", "99度a員林萬年店",
							"99度a員林靜修店", "99度a溪湖平和店", "99度a彰化田中店", "99度a彰化和美店",
							"99度a鹿港中正店", "99度a彰濱秀傳店", "99度a彰化花壇店", "99度a彰化二林店",
							"99度a彰化秀水店", "99度a彰化社頭店", "99度a彰化永靖店", "99度a員林大同店",
							"99度a彰化中民店", "99度a彰化彰美店", "99度a鹿港民族店" }, { "彰化衣" },
					{ "彰化住" }, { "彰化行" }, { "彰化育" }, { "彰化樂" } },
			// 10雲林
			{
					{ "99度a斗南延平店", "99度a斗六站前店", "99度a雲林麥寮店", "99度a雲林北港店",
							"99度a西螺中山店", "99度a虎尾林森店", "99度a斗六民生南店", "99度a土庫建國店" },
					{ "雲林衣" }, { "雲林住" }, { "雲林行" }, { "雲林育" }, { "雲林樂" } },
			// 11嘉義
			{
					{ "99度a嘉義朴子店", "99度a嘉義民雄店", "99度a嘉義水上店", "99度a新港奉天店",
							"99度a嘉義民族店", "99度a嘉義中山店", "99度a嘉義彌陀店", "99度a嘉義友愛店",
							"99度a嘉義nice店", "99度a嘉義仁愛店" }, { "嘉義衣" }, { "嘉義住" },
					{ "嘉義行" }, { "嘉義育" }, { "嘉義樂" } },
			// 12台南
			{
					{ "99度a永康南科店", "99度a永康小東店", "99度a台南關廟店", "99度a永康崑山店",
							"99度a台南柳營店", "99度a台南新化店", "99度a仁德中正店", "99度a台南新市店",
							"99度a台南中華東店", "99度a台南歸仁店", "99度a麻豆中華店",
							"99度a新營民權店", "99度a台南善化店", "99度a台南佳里店", "99度a南科聯研店",
							"99度a台南安南店", "99度a台南東寧店", "99度a台南健康店", "99度a台南北門店",
							"99度a台南小北店", "99度a台南建平店", "99度a台南成功店", "99度a永康中華店",
							"99度a台南東門店" }, { "台南衣" }, { "台南住" }, { "台南行" },
					{ "台南育" }, { "台南樂" } },
			// 13高雄
			{
					{ "99度a高雄高醫大店", "99度a路竹忠孝店", "99度a高雄中正店", "99度a鳳山文濱店",
							"99度a鳳山五甲店", "99度a高雄林園店", "99度a高雄仁武店", "99度a高雄旗山店",
							"99度a楠梓旗楠店", "99度a高雄岡山店", "99度a高雄右昌店", "99度a仁武仁雄店",
							"99度a鳳山中山店", "99度a高雄七賢店", "少那之高雄中正門市" }, { "高雄衣" },
					{ "高雄住" }, { "高雄行" }, { "高雄育" }, { "高雄樂" } },
			// 14屏東
			{
					{ "99度a屏東枋寮店", "99度a屏東恆春店", "99度a屏東中正店", "99度a屏東麟洛店",
							"99度a屏東和平店", "99度a屏東潮州店", "99度a屏東仁愛店", "99度a屏東內埔店",
							"99度a屏東萬丹店" }, { "屏東衣" }, { "屏東住" }, { "屏東行" },
					{ "屏東育" }, { "屏東樂" } },
			// 15台東
			{ { "99度a台東中華店" }, { "台東衣" }, { "台東住" }, { "台東行" }, { "台東育" },
					{ "台東樂" } },
			// 16花蓮
			{ { "99度a花蓮中山店", "99度a花蓮中正店", "99度a花蓮和平店" }, { "花蓮衣" }, { "花蓮住" },
					{ "花蓮行" }, { "花蓮育" }, { "花蓮樂" } },
			// 17宜蘭
			{
					{ "99度a宜蘭復興店", "99度a宜蘭站前店", "99度a羅東民權店", "99度a羅東站前店",
							"99度a頭城青雲店", "99度a宜蘭礁溪店" }, { "宜蘭衣" }, { "宜蘭住" },
					{ "宜蘭行" }, { "宜蘭育" }, { "宜蘭樂" } },
			// 18澎湖
			{ { "99度a澎湖馬公店" }, { "澎湖衣" }, { "澎湖住" }, { "澎湖行" }, { "澎湖育" },
					{ "澎湖樂" } },
			// 19金門
			{ { "99度a金門民族店" }, { "金門衣" }, { "金門住" }, { "金門行" }, { "金門育" },
					{ "金門樂" } },
			// 20連江
			{ { "連江食" }, { "連江衣" }, { "連江住" }, { "連江行" }, { "連江育" }, { "連江樂" } } };
	private Spinner sp1;// 第一個下拉選單
	private Spinner sp2;// 第二個下拉選單
	private Spinner sp3;// 第三個下拉選單
	private Context context;

	ArrayAdapter<String> adapter;
	ArrayAdapter<String> adapter2;
	ArrayAdapter<String> adapter3;

	/* tab3 */
	private TextView info, info2, info3;
	private Button scanner, scanner2;
	private int scannerError = 0;
	/* tab4 */
	private ImageButton store1, store2;
	private TextView storeInfo1, storeInfo2;
	private EditText searchName;
	private String searchObject;
	private Button searchButton;
	public ProgressDialog dialog = null;
	/* tab5 */
	private TextView output, myRecommend;
	private CheckBox checkBox_service;
	private Button shareButton, keyRecommedn;
	private int recommendFrequency;
	private String objectId;
	/* tab6 */
	private RadioButton genderFemaleButton;
	private RadioButton genderMaleButton;
	private EditText ageEditText, myNumber;
	private RadioGroup genderRadioGroup;
	public static final String GENDER_MALE = "male";
	public static final String GENDER_FEMALE = "female";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* update */
		/* 加入StrictMode避免發生 android.os.NetworkOnMainThreadException */
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		/*
		 * StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		 * .penaltyLog().penaltyDeath().build());
		 */

		JSONArray obj = getJson("http://terryyamg.github.io/myweb/update_verson.json"); // 更新版本文件檔位置
		try {
			for (int i = 0; i < obj.length(); i++) {
				JSONObject data = obj.getJSONObject(i);
				newServerVersion = Integer.parseInt(data.getString("code")); // code為名稱，抓出來newServerVersion為值
			}
		} catch (JSONException e) {

		} catch (NullPointerException e) {

		}

		new Thread(new Runnable() {
			public void run() {
				try {
					Message msg = new Message();
					msg.what = CHECK_UPDATE;
					handler.sendMessage(msg);

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block

				} catch (Exception e) {
					// TODO Auto-generated catch block

				}

			}
		}).start();

		/* 建立桌面捷徑 */
		addShortcut();
		// Track app opens.
		ParseAnalytics.trackAppOpened(getIntent());

		// Set up our UI member properties.

		/* tab1 */
		
		/* tab2 */
		// google map
		// try {
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		LatLng[] p00 = new LatLng[coordinate[0][0].length];
		LatLng[] p10 = new LatLng[coordinate[1][0].length];
		LatLng[] p20 = new LatLng[coordinate[2][0].length];
		LatLng[] p30 = new LatLng[coordinate[3][0].length];
		LatLng[] p40 = new LatLng[coordinate[4][0].length];
		LatLng[] p50 = new LatLng[coordinate[5][0].length];
		LatLng[] p60 = new LatLng[coordinate[6][0].length];
		LatLng[] p65 = new LatLng[coordinate[6][5].length];
		LatLng[] p70 = new LatLng[coordinate[7][0].length];
		LatLng[] p80 = new LatLng[coordinate[8][0].length];
		LatLng[] p90 = new LatLng[coordinate[9][0].length];
		LatLng[] p100 = new LatLng[coordinate[10][0].length];
		LatLng[] p110 = new LatLng[coordinate[11][0].length];
		LatLng[] p120 = new LatLng[coordinate[12][0].length];
		LatLng[] p130 = new LatLng[coordinate[13][0].length];
		LatLng[] p140 = new LatLng[coordinate[14][0].length];
		LatLng[] p150 = new LatLng[coordinate[15][0].length];
		LatLng[] p160 = new LatLng[coordinate[16][0].length];
		LatLng[] p170 = new LatLng[coordinate[17][0].length];
		LatLng[] p180 = new LatLng[coordinate[18][0].length];
		LatLng[] p190 = new LatLng[coordinate[19][0].length];

		for (int i = 0; i < coordinate[0][0].length; i++) {
			p00[i] = new LatLng(coordinate[0][0][i][2], coordinate[0][0][i][3]);
		}

		for (int i = 0; i < coordinate[1][0].length; i++) {
			p10[i] = new LatLng(coordinate[1][0][i][2], coordinate[1][0][i][3]);
		}

		for (int i = 0; i < coordinate[2][0].length; i++) {
			p20[i] = new LatLng(coordinate[2][0][i][2], coordinate[2][0][i][3]);
		}

		for (int i = 0; i < coordinate[3][0].length; i++) {
			p30[i] = new LatLng(coordinate[3][0][i][2], coordinate[3][0][i][3]);
		}

		for (int i = 0; i < coordinate[4][0].length; i++) {
			p40[i] = new LatLng(coordinate[4][0][i][2], coordinate[4][0][i][3]);
		}

		for (int i = 0; i < coordinate[5][0].length; i++) {
			p50[i] = new LatLng(coordinate[5][0][i][2], coordinate[5][0][i][3]);
		}

		for (int i = 0; i < coordinate[6][0].length; i++) {
			p60[i] = new LatLng(coordinate[6][0][i][2], coordinate[6][0][i][3]);
		}
		for (int i = 0; i < coordinate[6][5].length; i++) {
			p65[i] = new LatLng(coordinate[6][5][i][2], coordinate[6][5][i][3]);
		}

		for (int i = 0; i < coordinate[7][0].length; i++) {
			p70[i] = new LatLng(coordinate[7][0][i][2], coordinate[7][0][i][3]);
		}

		for (int i = 0; i < coordinate[8][0].length; i++) {
			p80[i] = new LatLng(coordinate[8][0][i][2], coordinate[8][0][i][3]);
		}

		for (int i = 0; i < coordinate[9][0].length; i++) {
			p90[i] = new LatLng(coordinate[9][0][i][2], coordinate[9][0][i][3]);
		}

		for (int i = 0; i < coordinate[10][0].length; i++) {
			p100[i] = new LatLng(coordinate[10][0][i][2],
					coordinate[10][0][i][3]);
		}

		for (int i = 0; i < coordinate[11][0].length; i++) {
			p110[i] = new LatLng(coordinate[11][0][i][2],
					coordinate[11][0][i][3]);
		}

		for (int i = 0; i < coordinate[12][0].length; i++) {
			p120[i] = new LatLng(coordinate[12][0][i][2],
					coordinate[12][0][i][3]);
		}

		for (int i = 0; i < coordinate[13][0].length; i++) {
			p130[i] = new LatLng(coordinate[13][0][i][2],
					coordinate[13][0][i][3]);
		}

		for (int i = 0; i < coordinate[14][0].length; i++) {
			p140[i] = new LatLng(coordinate[14][0][i][2],
					coordinate[14][0][i][3]);
		}

		for (int i = 0; i < coordinate[15][0].length; i++) {
			p150[i] = new LatLng(coordinate[15][0][i][2],
					coordinate[15][0][i][3]);
		}

		for (int i = 0; i < coordinate[16][0].length; i++) {
			p160[i] = new LatLng(coordinate[16][0][i][2],
					coordinate[16][0][i][3]);
		}

		for (int i = 0; i < coordinate[17][0].length; i++) {
			p170[i] = new LatLng(coordinate[17][0][i][2],
					coordinate[17][0][i][3]);
		}

		for (int i = 0; i < coordinate[18][0].length; i++) {
			p180[i] = new LatLng(coordinate[18][0][i][2],
					coordinate[18][0][i][3]);
		}

		for (int i = 0; i < coordinate[19][0].length; i++) {
			p190[i] = new LatLng(coordinate[19][0][i][2],
					coordinate[19][0][i][3]);
		}

		if (map != null) {
			map.setOnMarkerClickListener(this);
			// google mark
			// 0基隆
			for (int i = 0; i < coordinate[0][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p00[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			// 1台北
			for (int i = 0; i < coordinate[1][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p10[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			// 2新北
			for (int i = 0; i < coordinate[2][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p20[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			// 3桃園
			for (int i = 0; i < coordinate[3][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p30[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			// 4新竹市
			for (int i = 0; i < coordinate[4][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p40[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			// 5新竹縣
			for (int i = 0; i < coordinate[5][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p50[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			// 6苗栗

			for (int i = 0; i < coordinate[6][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p60[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			map.addMarker(new MarkerOptions()
					.position(p65[0])
					.title("通霄海水浴場")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
					.snippet("通霄"));

			// 7台中
			for (int i = 0; i < coordinate[7][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p70[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 8南投縣市
			for (int i = 0; i < coordinate[8][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p80[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 9彰化縣市
			for (int i = 0; i < coordinate[9][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p90[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 10雲林縣市
			for (int i = 0; i < coordinate[10][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p100[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 11嘉義縣市
			for (int i = 0; i < coordinate[11][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p110[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 12 台南
			for (int i = 0; i < coordinate[12][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p120[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			// 13 高雄
			for (int i = 0; i < coordinate[13][0].length - 1; i++) {

				map.addMarker(new MarkerOptions()
						.position(p130[i])
						.title("99度a高雄高醫大店")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}

			map.addMarker(new MarkerOptions()
					.position(p130[14])
					.title("少那之高雄中正門市")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
					.snippet("少那之"));
			// 14屏東縣市
			for (int i = 0; i < coordinate[14][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p140[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 15台東縣市
			for (int i = 0; i < coordinate[15][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p150[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 16花蓮縣市
			for (int i = 0; i < coordinate[16][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p160[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 17宜蘭縣市
			for (int i = 0; i < coordinate[17][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p170[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 18澎湖縣
			for (int i = 0; i < coordinate[18][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p180[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// 19金門縣
			for (int i = 0; i < coordinate[19][0].length; i++) {
				map.addMarker(new MarkerOptions()
						.position(p190[i])
						.title("99度a")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("99度a"));
			}
			// google location
			setUpMap();
		}

		// } catch (NullPointerException e) {
		// Log.i("map", "NullPointException");
		// }

		context = this;

		// 程式剛啟動時載入第一個下拉選單
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1 = (Spinner) findViewById(R.id.type);
		sp1.setAdapter(new NothingSelectedSpinnerAdapter(adapter,
				R.layout.contact_spinner_row_nothing_selected,
				// R.layout.contact_spinner_nothing_selected_dropdown, //
				// Optional
				this));
		sp1.setOnItemSelectedListener(selectListener);

		// 程式剛啟動時載入第二個下拉選單
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, locationName1);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2 = (Spinner) findViewById(R.id.type2);
		sp2.setAdapter(new NothingSelectedSpinnerAdapter(adapter2,
				R.layout.contact_spinner_row_nothing_selected2,
				// R.layout.contact_spinner_nothing_selected_dropdown, //
				// Optional
				this));
		sp2.setOnItemSelectedListener(selectListener2);

		// 程式剛啟動時載入第二個下拉選單，所以第三選單要載入第二個選單的子項目
		adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, locationName2);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp3 = (Spinner) findViewById(R.id.type3);
		sp3.setAdapter(new NothingSelectedSpinnerAdapter(adapter3,
				R.layout.contact_spinner_row_nothing_selected3,
				// R.layout.contact_spinner_nothing_selected_dropdown, //
				// Optional
				this));
		sp3.setOnItemSelectedListener(selectListener3);

		/* tab3 */
		info = (TextView) findViewById(R.id.info);
		info2 = (TextView) findViewById(R.id.info2);
		info3 = (TextView) findViewById(R.id.info3);
		scanner = (Button) findViewById(R.id.scanner);
		scanner2 = (Button) findViewById(R.id.scanner2);
		scanner2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				int scannerNumberInfo0 = ParseInstallation
						.getCurrentInstallation().getInt("scannerNumber" + 0);
				int scannerNumberInfo1 = ParseInstallation
						.getCurrentInstallation().getInt("scannerNumber" + 1);
				String setResult[] = { "99度a", "少那之" };
				info2.setText("您已經使用"
						+ Integer.valueOf(scannerNumberInfo0).toString() + "次"
						+ setResult[0] + "優惠方案 \n" + "您已經使用"
						+ Integer.valueOf(scannerNumberInfo1).toString() + "次"
						+ setResult[1] + "優惠方案 \n");

				String scannerNextTime0 = ParseInstallation
						.getCurrentInstallation().getString("scannerTime" + 0);
				String scannerNextTime1 = ParseInstallation
						.getCurrentInstallation().getString("scannerTime" + 1);

				info3.setText("您下次可以使用" + setResult[0] + "優惠的時間為"
						+ scannerNextTime0 + "\n" + "您下次可以使用" + setResult[1]
						+ "優惠的時間為" + scannerNextTime1);
			}
		});
		scanner.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent iScaner = new Intent("la.droid.qr.scan"); // 使用QRDroid的掃描功能
				iScaner.putExtra("la.droid.qr.complete", true); // 完整回傳，不截掉scheme
				try {
					// 開啟 QRDroid App 的掃描功能，等待 call back onActivityResult()
					startActivityForResult(iScaner, 0);
				} catch (ActivityNotFoundException ex) {
					// 若沒安裝 QRDroid，則開啟 Google Play商店，並顯示 QRDroid App
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("market://details?id=la.droid.qr"));
					startActivity(intent);
				}
			}
		});

		/* tab4 */
		store1 = (ImageButton) findViewById(R.id.list_store1);
		store2 = (ImageButton) findViewById(R.id.list_store2);
		storeInfo1 = (TextView) findViewById(R.id.store1_info);
		storeInfo2 = (TextView) findViewById(R.id.store2_info);
		searchName = (EditText) findViewById(R.id.searchName);
		searchButton = (Button) findViewById(R.id.searchButton);

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
				/* 計算總價 */
				try {
					searchObject = searchName.getText().toString(); // 取得輸入文字
				} catch (Exception e) {

				}

			}
		});
		searchButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				goSearch();
			}
		});
		storeInfo1.setText("店家名稱:小薇的店 \n" + "聯絡電話:\n"
				+ "0938065905;0970978949 \n" + "Skype:olive8688 \n"
				+ "Line ID:olive88888 \n");
		storeInfo2.setText("");

		store1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				store_list1();
			}
		});
		store2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				store_list2();
			}
		});
		/* tab5 */
		output = (TextView) findViewById(R.id.output);
		checkBox_service = (CheckBox) findViewById(R.id.checkBox_service);
		checkBox_service.setChecked(getFromSP("checkBox1")); // checkBox儲存設定
		shareButton = (Button) findViewById(R.id.shareButton);
		myRecommend = (TextView) findViewById(R.id.myRecommend);
		keyRecommedn = (Button) findViewById(R.id.keyRecommend);
		if (checkBox_service.isChecked()) {
			start_Click();
		} else {
			stop_Click();
		}
		checkBox_service
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (checkBox_service.isChecked()) {
							start_Click();
							saveInSp("checkBox1", isChecked); // 執行定位並儲存設定

						} else {
							stop_Click();
							saveInSp("checkBox1", isChecked);// 不執行定位並儲存設定
						}
					}

				});

		// 分享app
		recommendFrequency = ParseInstallation.getCurrentInstallation().getInt(
				"recommendFrequency");
		objectId = ParseInstallation.getCurrentInstallation().getObjectId();
		myRecommend.setText("我的推薦碼: \n" + objectId + "\n我推薦成功次數:"
				+ recommendFrequency);
		myRecommend.setTextSize(20);
		shareButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				shareDialog();
			}
		});
		// 輸入推薦碼 keyRecommendNumber

		keyRecommedn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				keyRecommendNumber();
			}
		});

		// checkBox_service.performClick(); //自動按checkBox

		/* tab5 */
		// this.genderFemaleButton = (RadioButton)
		// findViewById(R.id.gender_female_button);
		// this.genderMaleButton = (RadioButton)
		// findViewById(R.id.gender_male_button);
		// this.ageEditText = (EditText) findViewById(R.id.age_edit_text);
		// this.myNumber = (EditText) findViewById(R.id.my_number);
		// this.genderRadioGroup = (RadioGroup)
		// findViewById(R.id.gender_radio_group);

		/* 判斷網路是否開啟 */
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);// 先取得此service

		NetworkInfo networInfo = conManager.getActiveNetworkInfo(); // 在取得相關資訊

		if (networInfo == null || !networInfo.isAvailable()) { // 判斷是否有網路
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("網路設定")
					.setMessage("您尚未開啟網路連線")
					.setPositiveButton("請先啟動網路連線",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).show();
		}

		setupViewComponent();
	}

	/* update */
	public static JSONArray getJson(String url) {
		InputStream is = null;
		String result = "";
		// 若線上資料為陣列，則使用JSONArray
		JSONArray jsonArray = null;
		// 若線上資料為單筆資料，則使用JSONObject
		// JSONObject jsonObj = null;
		// 透過HTTP連線取得回應
		try {
			HttpClient httpclient = new DefaultHttpClient(); // for port 80
			HttpGet httppost = new HttpGet(url); // 要用Get，用Post會出現
													// java.lang.string cannot
													// be converted to jsonarray
			HttpResponse response = httpclient.execute(httppost);// 沒有值會catch錯誤，加入前面StrictMode就可以
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 讀取回應
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf8"), 9999999); // 99999為傳流大小，若資料很大，可自行調整
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// 逐行取得資料
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 轉換文字為JSONArray
		try {
			jsonArray = new JSONArray(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	public void showUpdateDialog() {

		// @SuppressWarnings("unused")
		// AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
		// .setTitle("更新提示").setIcon(android.R.drawable.ic_dialog_info)
		// .setMessage("最新優惠出來啦，快來下載更新")
		// .setPositiveButton("下載", new DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss(); // 關閉對話框
		// downLoadApk();
		// }
		//
		// }).show();

	}

	protected void downLoadApk() {
		final ProgressDialog pd; // 進度條對話框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下載更新");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(downLoadApkUrl, pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // 結束進度條對話框
				} catch (Exception e) {
					pd.dismiss();
					Message msg = new Message();
					msg.what = DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		/* 如果相等的話表示當前的SDcard掛載在手機上並且是可用的 */
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			pd.setMax(conn.getContentLength()); // 獲取副本文件大小
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(),
					"update.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				pd.setProgress(total); // 獲取當前下載量
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	/* 安裝APK */
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW); // 執行動作
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive"); // 執行的數據類型
		startActivity(intent);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case DOWN_ERROR:
				// 下載APK失敗
				Toast.makeText(getApplicationContext(), "下載新版本失敗", 1).show();
				break;
			case CHECK_UPDATE:
				// 檢查更新

				if (serverVersion == 0) {
					serverVersion = newServerVersion;
				}

				if (serverVersion != newServerVersion) {
					showUpdateDialog(); // 執行更新
				}
				break;
			}
		}
	};

	/* 建立桌面捷徑 */
	private void addShortcut() {
		Intent shortcutIntent = new Intent(getApplicationContext(),
				SplashScreen.class); // 啟動捷徑入口，一般用MainActivity，有使用其他入口則填入相對名稱，ex:有使用SplashScreen
		shortcutIntent.setAction(Intent.ACTION_MAIN);
		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // shortcutIntent送入
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name)); // 捷徑app名稱
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(
						getApplicationContext(),// 捷徑app圖
						R.drawable.ic_launcher));
		addIntent.putExtra("duplicate", false); // 指創建一次
		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // 安裝
		getApplicationContext().sendBroadcast(addIntent); // 送出廣播
	}

	/* tab1 */
	
	
	/* tab2 */
	private void setUpMap() {
		// Enable MyLocation Layer of Google Map
		map.setMyLocationEnabled(true);
		// Get LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		// Create a criteria object to resrieve provider
		Criteria criteria = new Criteria();

		// Get the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);
		try {

			// Get Current Location
			Location myLocation = locationManager
					.getLastKnownLocation(provider);
			// set map type
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Get latitude of the current location
			double latitude = myLocation.getLatitude();
			// Get longitude of the current location
			double longitude = myLocation.getLongitude();
			// Create a LatLng object for the current location
			LatLng latLng = new LatLng(latitude, longitude);
			// Show the current location in Google Map
			map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

			// Zoom in the Google Map
			map.animateCamera(CameraUpdateFactory.zoomTo(15));

			// map.addMarker(new MarkerOptions().position(new LatLng(latitude,
			// longitude)).title("現在位置"));

		} catch (NullPointerException e) {
		}

		/* GPS開啟跳出確認視窗 */
		// try {
		// if (!locationManager
		// .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		// new AlertDialog.Builder(MainActivity.this)
		// .setTitle("GPS設定")
		// .setMessage("您尚未啟動GPS，要啟動嗎?")
		// .setCancelable(false)
		// .setNegativeButton("啟動",
		// new DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface dialog,
		// int which) {
		// startActivity(new Intent(
		// Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		// }
		// })
		// .setPositiveButton("不啟動",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog,
		// int which) {
		// Toast.makeText(MainActivity.this,
		// "不啟動GPS將使用網路定位",
		// Toast.LENGTH_SHORT).show();
		// }
		// }).show();
		//
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/* 點選marker顯示 */
	@Override
	public boolean onMarkerClick(Marker marker) {
		Intent intent = new Intent(this, ShowMarkerInfo.class);
		intent.putExtra("MarkTitle", marker.getSnippet());
		startActivity(intent);
		return false;
	}

	// 第一個下拉選單監聽動作
	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// 讀取第一個下拉選單是選擇第幾個
			int pos = sp1.getSelectedItemPosition();
			// 重新產生新的Adapter，用的是二維陣列type2[pos]
			adapter2 = new ArrayAdapter<String>(context,
					android.R.layout.simple_spinner_item, type2[pos]);
			// 載入第二個下拉選單Spinner
			sp2.setAdapter(adapter2);

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	// 第二個下拉類別的監看式
	private OnItemSelectedListener selectListener2 = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// 讀取第一個下拉選單是選擇第幾個
			int pos = sp1.getSelectedItemPosition();
			// 讀取第二個下拉選單是選擇第幾個
			int pos2 = sp2.getSelectedItemPosition();
			// 重新產生新的Adapter，用的是三維陣列type3[pos][pos2]
			adapter3 = new ArrayAdapter<String>(context,
					android.R.layout.simple_spinner_item, type3[pos][pos2]);
			// 載入第三個下拉選單Spinner
			sp3.setAdapter(adapter3);
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	// 第二個下拉選單監聽動作
	private OnItemSelectedListener selectListener3 = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			setMapLocation(); // 移動到選定項目位置
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	public void setMapLocation() {
		try {
			int iSelect1 = sp1.getSelectedItemPosition(); // 第一個下拉選單被選到的第幾個項目
			int iSelect2 = sp2.getSelectedItemPosition(); // 第二個下拉選單被選到的第幾個項目
			int iSelect3 = sp3.getSelectedItemPosition(); // 第三個下拉選單被選到的第幾個項目
			double dLat = coordinate[iSelect1][iSelect2][iSelect3][2]; // 抓取座標,南北緯
			double dLon = coordinate[iSelect1][iSelect2][iSelect3][3]; // 抓取座標,東西經

			if (spinnerNumber > 2) { // 初次進map不使用下拉選單
				LatLng gg = new LatLng(dLat, dLon); // 將座標位置輸入設定
				map.moveCamera(CameraUpdateFactory.newLatLng(gg)); // 移動到定點
				map.animateCamera(CameraUpdateFactory.zoomTo(15)); // 縮放
			}
			spinnerNumber = spinnerNumber + 1;
		} catch (NullPointerException e) {
		}
	}

	/* tab3 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (0 == requestCode && null != data && data.getExtras() != null) {
			// 掃描結果存放在 key 為 la.droid.qr.result 的值中
			String result = data.getExtras().getString("la.droid.qr.result");
			String setResult[] = { "99度a", "donutes" };
			int scannerNumber[] = new int[setResult.length];
			String scannerNextTime[] = new String[setResult.length];
			for (int i = 0; i < setResult.length; i++) {
				if (setResult[i].equals(result)) {
					String messsenger = "歡迎使用 \n" + "本公司優惠方案";

					scannerNumber[i] = ParseInstallation
							.getCurrentInstallation().getInt(
									"scannerNumber" + i); // 取出第i間掃描次數

					scannerNextTime[i] = ParseInstallation
							.getCurrentInstallation().getString(
									"scannerTime" + i); // 取出第i間下次可以掃描時間

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss"); // 取得小時
					String scannerTime = sdf.format(new java.util.Date()); // 取得現在時間
					int waitTime = 3; // 冷卻時間

					Date dt = null; // 現在時間date初始化

					try {

						dt = sdf.parse(scannerTime);// 現在時間

					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dt);
					calendar.add(Calendar.HOUR, waitTime);// +冷卻時間
					Date tdt = calendar.getTime();// 取得加減過後的Date
					String sumTime = sdf.format(tdt);// 依照設定格式取得字串

					if (scannerNextTime[i] == null) { // 初次掃描，scannerNextTime為空值

						ParseInstallation.getCurrentInstallation().put(
								"scannerTime" + i, sumTime);
						scannerNumber[i]++;
						ParseInstallation.getCurrentInstallation().put(
								"scannerNumber" + i, scannerNumber[i]);
						ParseInstallation.getCurrentInstallation()
								.saveInBackground(new SaveCallback() {
									@Override
									public void done(ParseException e) {
										if (e == null) {
											Toast toast = Toast.makeText(
													getApplicationContext(),
													R.string.scanner_success,
													Toast.LENGTH_SHORT);
											toast.show();
										} else {
											e.printStackTrace();

											Toast toast = Toast.makeText(
													getApplicationContext(),
													R.string.scanner_failed,
													Toast.LENGTH_SHORT);
											toast.show();
										}
									}
								});
						info2.setText("您已經使用"
								+ Integer.valueOf(scannerNumber[i]).toString()
								+ "次" + setResult[i] + "優惠方案");

						info3.setText("您下次可以使用" + setResult[i] + "優惠的時間為"
								+ sumTime);
						info.setTextSize(20);
						info.setText(messsenger); // 將結果顯示在 TextVeiw 中
					} else { // scannerNextTime有值
						Date snt = null;// 取出時間
						try {
							snt = sdf.parse(scannerNextTime[i]);
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if (snt.before(dt)) { // 抓取出來可掃描時間(過去)snt before
												// 現在時間dt，可記錄

							ParseInstallation.getCurrentInstallation().put(
									"scannerTime" + i, sumTime);
							scannerNumber[i]++;
							ParseInstallation.getCurrentInstallation().put(
									"scannerNumber" + i, scannerNumber[i]);
							ParseInstallation.getCurrentInstallation()
									.saveInBackground(new SaveCallback() {
										@Override
										public void done(ParseException e) {
											if (e == null) {
												Toast toast = Toast
														.makeText(
																getApplicationContext(),
																R.string.scanner_success,
																Toast.LENGTH_SHORT);
												toast.show();
											} else {
												e.printStackTrace();

												Toast toast = Toast
														.makeText(
																getApplicationContext(),
																R.string.scanner_failed,
																Toast.LENGTH_SHORT);
												toast.show();
											}
										}
									});
							info2.setText("您已經使用"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "次" + setResult[i]
									+ "優惠方案");

							info3.setText("您下次可以使用" + setResult[i] + "優惠的時間為"
									+ sumTime);
							info.setTextSize(20);
							info.setText(messsenger); // 將結果顯示在 TextVeiw 中
						} else { // 冷卻時間尚未結束，不記錄
							info2.setText("您已經使用"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "次" + setResult[i]
									+ "優惠方案");

							info3.setText("您下次可以使用" + setResult[i] + "優惠的時間為"
									+ scannerNextTime[i]);

							info.setTextSize(20);
							info.setText(messsenger); // 將結果顯示在 TextVeiw 中
						}
					}
					break;
				} else {
					scannerError++;
					if (scannerError <= 3) {
						String messsenger = "抱歉，沒有掃瞄成功; \n" + "您可以再試一次";
						info.setTextSize(20);
						info.setText(messsenger); // 將結果顯示在 TextVeiw 中
					} else {
						String messsenger = "您可以請教門市人員， \n" + "或與我們聯繫";
						info.setTextSize(20);
						info.setText(messsenger); // 將結果顯示在 TextVeiw 中
					}

				}
			}

		}
	}

	/* tab4 */
	void goSearch() {
		if (searchObject == null) {

		} else {
			progress();
			Intent intent = new Intent(this, SearchCommodity.class);
			intent.putExtra("searchName", searchObject);
			startActivity(intent);
		}
	}

	void store_list1() {
		progress();
		Intent intent = new Intent(this, ListCommodity.class);
		intent.putExtra("storeName", "小薇的店");
		startActivity(intent);
	}

	void store_list2() {
		progress();
		Intent intent = new Intent(this, ListCommodity.class);
		startActivity(intent);
	}

	void progress() {
		dialog = ProgressDialog.show(this, "讀取中", "請稍後...", true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dialog.dismiss();
				}
			}
		}).start();
	}

	/* tab5 */
	public void start_Click() {
		float[] latitude00 = new float[coordinate[0][0].length];
		float[] longitude00 = new float[coordinate[0][0].length];
		float[] latitude10 = new float[coordinate[1][0].length];
		float[] longitude10 = new float[coordinate[1][0].length];
		float[] latitude20 = new float[coordinate[2][0].length];
		float[] longitude20 = new float[coordinate[2][0].length];
		float[] latitude30 = new float[coordinate[3][0].length];
		float[] longitude30 = new float[coordinate[3][0].length];
		float[] latitude40 = new float[coordinate[4][0].length];
		float[] longitude40 = new float[coordinate[4][0].length];
		float[] latitude50 = new float[coordinate[5][0].length];
		float[] longitude50 = new float[coordinate[5][0].length];
		float[] latitude60 = new float[coordinate[6][0].length];
		float[] longitude60 = new float[coordinate[6][0].length];
		float[] latitude65 = new float[coordinate[6][5].length];
		float[] longitude65 = new float[coordinate[6][5].length];
		float[] latitude70 = new float[coordinate[7][0].length];
		float[] longitude70 = new float[coordinate[7][0].length];
		float[] latitude80 = new float[coordinate[8][0].length];
		float[] longitude80 = new float[coordinate[8][0].length];
		float[] latitude90 = new float[coordinate[9][0].length];
		float[] longitude90 = new float[coordinate[9][0].length];
		float[] latitude100 = new float[coordinate[10][0].length];
		float[] longitude100 = new float[coordinate[10][0].length];
		float[] latitude110 = new float[coordinate[11][0].length];
		float[] longitude110 = new float[coordinate[11][0].length];
		float[] latitude120 = new float[coordinate[12][0].length];
		float[] longitude120 = new float[coordinate[12][0].length];
		float[] latitude130 = new float[coordinate[13][0].length];
		float[] longitude130 = new float[coordinate[13][0].length];
		float[] latitude140 = new float[coordinate[14][0].length];
		float[] longitude140 = new float[coordinate[14][0].length];
		float[] latitude150 = new float[coordinate[15][0].length];
		float[] longitude150 = new float[coordinate[15][0].length];
		float[] latitude160 = new float[coordinate[16][0].length];
		float[] longitude160 = new float[coordinate[16][0].length];
		float[] latitude170 = new float[coordinate[17][0].length];
		float[] longitude170 = new float[coordinate[17][0].length];
		float[] latitude180 = new float[coordinate[18][0].length];
		float[] longitude180 = new float[coordinate[18][0].length];
		float[] latitude190 = new float[coordinate[19][0].length];
		float[] longitude190 = new float[coordinate[19][0].length];

		for (int i = 0; i < coordinate[0][0].length; i++) {
			latitude00[i] = coordinate[0][0][i][2];
			longitude00[i] = coordinate[0][0][i][3];
		}
		for (int i = 0; i < coordinate[1][0].length; i++) {
			latitude10[i] = coordinate[1][0][i][2];
			longitude10[i] = coordinate[1][0][i][3];
		}
		for (int i = 0; i < coordinate[2].length; i++) {
			latitude20[i] = coordinate[2][0][i][2];
			longitude20[i] = coordinate[2][0][i][3];
		}
		for (int i = 0; i < coordinate[3][0].length; i++) {
			latitude30[i] = coordinate[3][0][i][2];
			longitude30[i] = coordinate[3][0][i][3];
		}
		for (int i = 0; i < coordinate[4][0].length; i++) {
			latitude40[i] = coordinate[4][0][i][2];
			longitude40[i] = coordinate[4][0][i][3];
		}
		for (int i = 0; i < coordinate[5][0].length; i++) {
			latitude50[i] = coordinate[5][0][i][2];
			longitude50[i] = coordinate[5][0][i][3];
		}
		for (int i = 0; i < coordinate[6][0].length; i++) {
			latitude60[i] = coordinate[6][0][i][2];
			longitude60[i] = coordinate[6][0][i][3];
		}
		for (int i = 0; i < coordinate[6][5].length; i++) {
			latitude65[i] = coordinate[6][5][i][2];
			longitude65[i] = coordinate[6][5][i][3];
		}
		for (int i = 0; i < coordinate[7][0].length; i++) {
			latitude70[i] = coordinate[7][0][i][2];
			longitude70[i] = coordinate[7][0][i][3];
		}
		for (int i = 0; i < coordinate[8][0].length; i++) {
			latitude80[i] = coordinate[8][0][i][2];
			longitude80[i] = coordinate[8][0][i][3];
		}
		for (int i = 0; i < coordinate[9][0].length; i++) {
			latitude90[i] = coordinate[9][0][i][2];
			longitude90[i] = coordinate[9][0][i][3];
		}
		for (int i = 0; i < coordinate[10][0].length; i++) {
			latitude100[i] = coordinate[10][0][i][2];
			longitude100[i] = coordinate[10][0][i][3];
		}
		for (int i = 0; i < coordinate[11][0].length; i++) {
			latitude110[i] = coordinate[11][0][i][2];
			longitude110[i] = coordinate[11][0][i][3];
		}
		for (int i = 0; i < coordinate[12][0].length; i++) {
			latitude120[i] = coordinate[12][0][i][2];
			longitude120[i] = coordinate[12][0][i][3];
		}
		for (int i = 0; i < coordinate[13][0].length; i++) {
			latitude130[i] = coordinate[13][0][i][2];
			longitude130[i] = coordinate[13][0][i][3];
		}
		for (int i = 0; i < coordinate[14][0].length; i++) {
			latitude140[i] = coordinate[14][0][i][2];
			longitude140[i] = coordinate[14][0][i][3];
		}
		for (int i = 0; i < coordinate[15][0].length; i++) {
			latitude150[i] = coordinate[15][0][i][2];
			longitude150[i] = coordinate[15][0][i][3];
		}
		for (int i = 0; i < coordinate[16][0].length; i++) {
			latitude160[i] = coordinate[16][0][i][2];
			longitude160[i] = coordinate[16][0][i][3];
		}
		for (int i = 0; i < coordinate[17][0].length; i++) {
			latitude170[i] = coordinate[17][0][i][2];
			longitude170[i] = coordinate[17][0][i][3];
		}
		for (int i = 0; i < coordinate[18][0].length; i++) {
			latitude180[i] = coordinate[18][0][i][2];
			longitude180[i] = coordinate[18][0][i][3];
		}
		for (int i = 0; i < coordinate[19][0].length; i++) {
			latitude190[i] = coordinate[19][0][i][2];
			longitude190[i] = coordinate[19][0][i][3];
		}

		Intent intent = new Intent(this, GPSService.class);
		intent.putExtra("length00", coordinate[0][0].length); // 傳送長度
		for (int i = 0; i < coordinate[0][0].length; i++) {
			intent.putExtra("LATITUDE00" + i, latitude00[i]);
			intent.putExtra("LONGITUDE00" + i, longitude00[i]);
		}

		intent.putExtra("length10", coordinate[1][0].length); // 傳送長度
		for (int i = 0; i < coordinate[1][0].length; i++) {
			intent.putExtra("LATITUDE10" + i, latitude10[i]);
			intent.putExtra("LONGITUDE10" + i, longitude10[i]);
		}

		intent.putExtra("length20", coordinate[2][0].length); // 傳送長度
		for (int i = 0; i < coordinate[2][0].length; i++) {
			intent.putExtra("LATITUDE20" + i, latitude20[i]);
			intent.putExtra("LONGITUDE20" + i, longitude20[i]);
		}

		intent.putExtra("length30", coordinate[3][0].length); // 傳送長度
		for (int i = 0; i < coordinate[3][0].length; i++) {
			intent.putExtra("LATITUDE30" + i, latitude30[i]);
			intent.putExtra("LONGITUDE30" + i, longitude30[i]);
		}

		intent.putExtra("length40", coordinate[4][0].length); // 傳送長度
		for (int i = 0; i < coordinate[4][0].length; i++) {
			intent.putExtra("LATITUDE40" + i, latitude40[i]);
			intent.putExtra("LONGITUDE40" + i, longitude40[i]);
		}

		intent.putExtra("length50", coordinate[5][0].length); // 傳送長度
		for (int i = 0; i < coordinate[5][0].length; i++) {
			intent.putExtra("LATITUDE50" + i, latitude50[i]);
			intent.putExtra("LONGITUDE50" + i, longitude50[i]);
		}

		intent.putExtra("length60", coordinate[6][0].length); // 傳送長度
		for (int i = 0; i < coordinate[6][0].length; i++) {
			intent.putExtra("LATITUDE60" + i, latitude60[i]);
			intent.putExtra("LONGITUDE60" + i, longitude60[i]);
		}
		intent.putExtra("length65", coordinate[6][5].length); // 傳送長度
		for (int i = 0; i < coordinate[6][5].length; i++) {
			intent.putExtra("LATITUDE65" + i, latitude65[i]);
			intent.putExtra("LONGITUDE65" + i, longitude65[i]);
		}

		intent.putExtra("length70", coordinate[7][0].length); // 傳送長度
		for (int i = 0; i < coordinate[7][0].length; i++) {
			intent.putExtra("LATITUDE70" + i, latitude70[i]);
			intent.putExtra("LONGITUDE70" + i, longitude70[i]);
		}

		intent.putExtra("length80", coordinate[8][0].length); // 傳送長度
		for (int i = 0; i < coordinate[8][0].length; i++) {
			intent.putExtra("LATITUDE80" + i, latitude80[i]);
			intent.putExtra("LONGITUDE80" + i, longitude80[i]);
		}

		intent.putExtra("length90", coordinate[9][0].length); // 傳送長度
		for (int i = 0; i < coordinate[9][0].length; i++) {
			intent.putExtra("LATITUDE90" + i, latitude90[i]);
			intent.putExtra("LONGITUDE90" + i, longitude90[i]);
		}

		intent.putExtra("length100", coordinate[10][0].length); // 傳送長度
		for (int i = 0; i < coordinate[10][0].length; i++) {
			intent.putExtra("LATITUDE100" + i, latitude100[i]);
			intent.putExtra("LONGITUDE100" + i, longitude100[i]);
		}
		intent.putExtra("length110", coordinate[11][0].length); // 傳送長度
		for (int i = 0; i < coordinate[11][0].length; i++) {
			intent.putExtra("LATITUDE110" + i, latitude110[i]);
			intent.putExtra("LONGITUDE110" + i, longitude110[i]);
		}

		intent.putExtra("length120", coordinate[12][0].length); // 傳送長度
		for (int i = 0; i < coordinate[12][0].length; i++) {
			intent.putExtra("LATITUDE120" + i, latitude120[i]);
			intent.putExtra("LONGITUDE120" + i, longitude120[i]);
		}

		intent.putExtra("length130", coordinate[13][0].length); // 傳送長度
		for (int i = 0; i < coordinate[13][0].length; i++) {
			intent.putExtra("LATITUDE130" + i, latitude130[i]);
			intent.putExtra("LONGITUDE130" + i, longitude130[i]);
		}
		intent.putExtra("length140", coordinate[14][0].length); // 傳送長度
		for (int i = 0; i < coordinate[14][0].length; i++) {
			intent.putExtra("LATITUDE140" + i, latitude140[i]);
			intent.putExtra("LONGITUDE140" + i, longitude140[i]);
		}
		intent.putExtra("length150", coordinate[15][0].length); // 傳送長度
		for (int i = 0; i < coordinate[15][0].length; i++) {
			intent.putExtra("LATITUDE150" + i, latitude150[i]);
			intent.putExtra("LONGITUDE150" + i, longitude150[i]);
		}
		intent.putExtra("length160", coordinate[16][0].length); // 傳送長度
		for (int i = 0; i < coordinate[16][0].length; i++) {
			intent.putExtra("LATITUDE160" + i, latitude160[i]);
			intent.putExtra("LONGITUDE160" + i, longitude160[i]);
		}
		intent.putExtra("length170", coordinate[17][0].length); // 傳送長度
		for (int i = 0; i < coordinate[17][0].length; i++) {
			intent.putExtra("LATITUDE170" + i, latitude170[i]);
			intent.putExtra("LONGITUDE170" + i, longitude170[i]);
		}
		intent.putExtra("length180", coordinate[18][0].length); // 傳送長度
		for (int i = 0; i < coordinate[18][0].length; i++) {
			intent.putExtra("LATITUDE180" + i, latitude180[i]);
			intent.putExtra("LONGITUDE180" + i, longitude180[i]);
		}
		intent.putExtra("length190", coordinate[19][0].length); // 傳送長度
		for (int i = 0; i < coordinate[19][0].length; i++) {
			intent.putExtra("LATITUDE190" + i, latitude190[i]);
			intent.putExtra("LONGITUDE190" + i, longitude190[i]);
		}

		startService(intent);
		output.setText("服務啟動中");
	}

	public void stop_Click() {
		Intent intent = new Intent(this, GPSService.class);
		stopService(intent);
		output.setText("服務停止中");
		output.setTextSize(20);
	}

	// 分享app
	void shareDialog() {

		String shareText = "曼聯通優惠，讓您食衣住行育樂都省錢  "
				+ "\n https://play.google.com/store/apps/details?id=com.manlen.tutorials.pushnotifications"
				+ "\n輸入我的推薦碼" + objectId + "\n 將可獲得優惠";
		// Uri imageUri = Uri.parse("android.resource://" + getPackageName() +
		// "/drawable/" + "android");
		// Log.i("imageUri:", imageUri + "");
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		// shareIntent.setType("image/png");
		// shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
		startActivity(Intent.createChooser(shareIntent, "分享"));
	}

	void keyRecommendNumber() {
		Intent intent = new Intent(this, KeyInRecommendNumber.class);
		startActivity(intent);
	}

	/* 儲存設定 */
	private boolean getFromSP(String key) {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("Android",
						android.content.Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	private void saveInSp(String key, boolean value) {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("Android",
						android.content.Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	private void setupViewComponent() {
		/* tabHost */
		// 從資源類別R中取得介面元件
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("說明", getResources().getDrawable(R.drawable.tab1));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("地圖", getResources().getDrawable(R.drawable.tab2));
		spec.setContent(R.id.tab2);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab3");
		spec.setIndicator("掃描", getResources().getDrawable(R.drawable.tab3));
		spec.setContent(R.id.tab3);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab4");
		spec.setIndicator("團購", getResources().getDrawable(R.drawable.tab4));
		spec.setContent(R.id.tab4);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab5");
		spec.setIndicator("設定", getResources().getDrawable(R.drawable.tab5));
		spec.setContent(R.id.tab5);
		tabHost.addTab(spec);

		// spec = tabHost.newTabSpec("tab5");
		// spec.setIndicator("", getResources().getDrawable(R.drawable.tab5));
		// spec.setContent(R.id.tab5);
		// tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// 設定tab標籤的字體大小
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

		tabView = tabWidget.getChildTabViewAt(2);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);

		tabView = tabWidget.getChildTabViewAt(3);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);

		tabView = tabWidget.getChildTabViewAt(4);
		tab = (TextView) tabView.findViewById(android.R.id.title);
		tab.setTextSize(15);
		tab.setTextColor(Color.LTGRAY);

		// tabView = tabWidget.getChildTabViewAt(4);
		// tab = (TextView) tabView.findViewById(android.R.id.title);
		// tab.setTextSize(10);

	}

	/* 離開程式 */
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			closeAll();
			return true;
		}
		return super.onKeyDown(keycode, event);
	}

	public void closeAll() {
		new AlertDialog.Builder(MainActivity.this).setTitle("確定離開本程式？")
				.setMessage("按中間鍵可背景執行")
				.setNegativeButton("離開", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setPositiveButton("繼續", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Kill myself
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/* tab3 */

	@Override
	public void onStart() {
		super.onStart();

		// Display the current values for this user, such as their age and
		// gender.
		// displayUserProfile();
		// refreshUserProfile();
	}

	/* tab5 */
	// Save the user's profile to their installation.
	public void saveUserProfile(View view) {
		String ageTextString = ageEditText.getText().toString();
		String myNumberTextString = myNumber.getText().toString();

		if (ageTextString.length() > 0) {
			ParseInstallation.getCurrentInstallation().put("age",
					Integer.valueOf(ageTextString));
		}
		if (myNumberTextString.length() > 0) {
			ParseInstallation.getCurrentInstallation().put("mynumber",
					Integer.valueOf(myNumberTextString));
		}

		if (genderRadioGroup.getCheckedRadioButtonId() == genderFemaleButton
				.getId()) {
			ParseInstallation.getCurrentInstallation().put("gender",
					GENDER_FEMALE);
		} else if (genderRadioGroup.getCheckedRadioButtonId() == genderMaleButton
				.getId()) {
			ParseInstallation.getCurrentInstallation().put("gender",
					GENDER_MALE);
		} else {
			ParseInstallation.getCurrentInstallation().remove("gender");
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(ageEditText.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(myNumber.getWindowToken(), 0);

		ParseInstallation.getCurrentInstallation().saveInBackground(
				new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e == null) {
							Toast toast = Toast.makeText(
									getApplicationContext(),
									R.string.alert_dialog_success,
									Toast.LENGTH_SHORT);
							toast.show();
						} else {
							e.printStackTrace();

							Toast toast = Toast.makeText(
									getApplicationContext(),
									R.string.alert_dialog_failed,
									Toast.LENGTH_SHORT);
							toast.show();
						}
					}
				});
	}

	// Refresh the UI with the data obtained from the current ParseInstallation
	// object.
	private void displayUserProfile() {
		String gender = ParseInstallation.getCurrentInstallation().getString(
				"gender");
		int age = ParseInstallation.getCurrentInstallation().getInt("age");

		if (gender != null) {
			genderMaleButton.setChecked(gender.equalsIgnoreCase(GENDER_MALE));
			genderFemaleButton.setChecked(gender
					.equalsIgnoreCase(GENDER_FEMALE));
		} else {
			genderMaleButton.setChecked(false);
			genderFemaleButton.setChecked(false);
		}

		if (age > 0) {
			ageEditText.setText(Integer.valueOf(age).toString());
		}

	}

	// Get the latest values from the ParseInstallation object.
	private void refreshUserProfile() {
		ParseInstallation.getCurrentInstallation().refreshInBackground(
				new RefreshCallback() {

					@Override
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							// displayUserProfile();
						}
					}
				});
	}
}
