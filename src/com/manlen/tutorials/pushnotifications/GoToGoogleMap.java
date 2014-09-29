package com.manlen.tutorials.pushnotifications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class GoToGoogleMap extends FragmentActivity implements
		OnMarkerClickListener {
	private GoogleMap map;
	private Button pushStore;
	LatLng latLng;
	int spinnerNumber = 0; // 初次進map不使用下拉選單
	LatLng[] p00, p10, p20, p30, p40, p50, p60, p70, p80, p90, p100, p110,
			p120, p130, p140, p150, p160, p170, p180, p190;// 食
	LatLng[] p65;
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
					{ { 6, 5, (float) 24.4928170, (float) 120.6736930 } } // 苗栗樂
			},
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
	private int no1, no2, no3;
	Typeface fontch;

	// 下拉選單
	private String[] type = new String[] { "全部", "食", "衣", "住", "行", "育", "樂" };
	private Spinner sp;// 第一個下拉選單
	private Context context;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.go_to_googlemap);
		/* 字型 */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt001.ttf");

		// 取的滾輪選擇項目
		Intent intent = getIntent();
		String check = intent.getStringExtra("check");
		no1 = intent.getIntExtra("no1", 0); // 取得縣市指標
		no2 = intent.getIntExtra("no2", 0); // 取得食衣住行指標
		no3 = intent.getIntExtra("no3", 0); // 取得特約商店指標

		pushStore = (Button) findViewById(R.id.pushStore);
		pushStore.setTypeface(fontch);
		pushStore.setTextColor(Color.BLACK);
		pushStore.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				pushStore();
			}
		});
		// google map
		try {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			p00 = new LatLng[coordinate[0][0].length];
			p10 = new LatLng[coordinate[1][0].length];
			p20 = new LatLng[coordinate[2][0].length];
			p30 = new LatLng[coordinate[3][0].length];
			p40 = new LatLng[coordinate[4][0].length];
			p50 = new LatLng[coordinate[5][0].length];
			p60 = new LatLng[coordinate[6][0].length]; // 苗栗食
			p65 = new LatLng[coordinate[6][5].length]; // 苗栗樂
			p70 = new LatLng[coordinate[7][0].length];
			p80 = new LatLng[coordinate[8][0].length];
			p90 = new LatLng[coordinate[9][0].length];
			p100 = new LatLng[coordinate[10][0].length];
			p110 = new LatLng[coordinate[11][0].length];
			p120 = new LatLng[coordinate[12][0].length];
			p130 = new LatLng[coordinate[13][0].length];
			p140 = new LatLng[coordinate[14][0].length];
			p150 = new LatLng[coordinate[15][0].length];
			p160 = new LatLng[coordinate[16][0].length];
			p170 = new LatLng[coordinate[17][0].length];
			p180 = new LatLng[coordinate[18][0].length];
			p190 = new LatLng[coordinate[19][0].length];

			for (int i = 0; i < coordinate[0][0].length; i++) {
				p00[i] = new LatLng(coordinate[0][0][i][2],
						coordinate[0][0][i][3]);
			}

			for (int i = 0; i < coordinate[1][0].length; i++) {
				p10[i] = new LatLng(coordinate[1][0][i][2],
						coordinate[1][0][i][3]);
			}

			for (int i = 0; i < coordinate[2][0].length; i++) {
				p20[i] = new LatLng(coordinate[2][0][i][2],
						coordinate[2][0][i][3]);
			}

			for (int i = 0; i < coordinate[3][0].length; i++) {
				p30[i] = new LatLng(coordinate[3][0][i][2],
						coordinate[3][0][i][3]);
			}

			for (int i = 0; i < coordinate[4][0].length; i++) {
				p40[i] = new LatLng(coordinate[4][0][i][2],
						coordinate[4][0][i][3]);
			}

			for (int i = 0; i < coordinate[5][0].length; i++) {
				p50[i] = new LatLng(coordinate[5][0][i][2],
						coordinate[5][0][i][3]);
			}

			for (int i = 0; i < coordinate[6][0].length; i++) {
				p60[i] = new LatLng(coordinate[6][0][i][2],
						coordinate[6][0][i][3]);
			}
			for (int i = 0; i < coordinate[6][5].length; i++) {
				p65[i] = new LatLng(coordinate[6][5][i][2],
						coordinate[6][5][i][3]);
			}

			for (int i = 0; i < coordinate[7][0].length; i++) {
				p70[i] = new LatLng(coordinate[7][0][i][2],
						coordinate[7][0][i][3]);
			}

			for (int i = 0; i < coordinate[8][0].length; i++) {
				p80[i] = new LatLng(coordinate[8][0][i][2],
						coordinate[8][0][i][3]);
			}

			for (int i = 0; i < coordinate[9][0].length; i++) {
				p90[i] = new LatLng(coordinate[9][0][i][2],
						coordinate[9][0][i][3]);
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

				// google location
				setUpMap();
			}

		} catch (NullPointerException e) {
			// Log.i("map", "NullPointException");
		}

		if (check != null) {
			setMapLocation();
			Log.i("check", check + "");
		}

		// 下拉選單
		context = this;

		// 程式剛啟始時載入第一個下拉選單
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp = (Spinner) findViewById(R.id.type);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(selectListener);

	}

	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// 動作
			classification();
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	};

	// 選取後動作
	void classification() {
		int iSelect1 = sp.getSelectedItemPosition(); // 第一個下拉選單被選到的第幾個項目
		boolean[] vis = new boolean[6];
		switch (iSelect1) {
		case 0: // 全部
			vis[0] = true;
			vis[1] = true;
			vis[2] = true;
			vis[3] = true;
			vis[4] = true;
			vis[5] = true;
			break;
		case 1: // 食
			vis[0] = true;
			vis[1] = false;
			vis[2] = false;
			vis[3] = false;
			vis[4] = false;
			vis[5] = false;
			break;
		case 2:// 衣
			vis[0] = false;
			vis[1] = true;
			vis[2] = false;
			vis[3] = false;
			vis[4] = false;
			vis[5] = false;
			break;
		case 3:// 住
			vis[0] = false;
			vis[1] = false;
			vis[2] = true;
			vis[3] = false;
			vis[4] = false;
			vis[5] = false;
			break;
		case 4:// 行
			vis[0] = false;
			vis[1] = false;
			vis[2] = false;
			vis[3] = true;
			vis[4] = false;
			vis[5] = false;
			break;
		case 5:// 育
			vis[0] = false;
			vis[1] = false;
			vis[2] = false;
			vis[3] = false;
			vis[4] = true;
			vis[5] = false;
			break;
		case 6:// 樂
			vis[0] = false;
			vis[1] = false;
			vis[2] = false;
			vis[3] = false;
			vis[4] = false;
			vis[5] = true;
			break;
		}
		Log.i("iSelect1", iSelect1 + "");
		Log.i("vis[[[[[]]]]]", vis[0] + "-"+vis[1] + "-"+vis[2] + "-"+vis[3] + "-"+vis[4] + "-"+vis[5]);
		map.clear();
		map.setOnMarkerClickListener(this);
		// google mark
		// 0基隆
		for (int i = 0; i < coordinate[0][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p00[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		// 1台北
		for (int i = 0; i < coordinate[1][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p10[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		// 2新北
		for (int i = 0; i < coordinate[2][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p20[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		// 3桃園
		for (int i = 0; i < coordinate[3][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p30[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		// 4新竹市
		for (int i = 0; i < coordinate[4][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p40[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		// 5新竹縣
		for (int i = 0; i < coordinate[5][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p50[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		// 6苗栗

		for (int i = 0; i < coordinate[6][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p60[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 苗栗樂
		map.addMarker(new MarkerOptions()
				.visible(vis[5])
				.position(p65[0])
				.title("通霄海水浴場")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
				.snippet("通霄海水浴場"));

		// 7台中
		for (int i = 0; i < coordinate[7][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p70[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 8南投縣市
		for (int i = 0; i < coordinate[8][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p80[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 9彰化縣市
		for (int i = 0; i < coordinate[9][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p90[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 10雲林縣市
		for (int i = 0; i < coordinate[10][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p100[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 11嘉義縣市
		for (int i = 0; i < coordinate[11][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p110[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 12 台南
		for (int i = 0; i < coordinate[12][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p120[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		// 13 高雄
		for (int i = 0; i < coordinate[13][0].length - 1; i++) {

			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p130[i])
					.title("99度a高雄高醫大店")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

		map.addMarker(new MarkerOptions()
				.visible(vis[0])
				.position(p130[14])
				.title("少那之高雄中正門市")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
				.snippet("少那之"));
		// 14屏東縣市
		for (int i = 0; i < coordinate[14][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p140[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 15台東縣市
		for (int i = 0; i < coordinate[15][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p150[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 16花蓮縣市
		for (int i = 0; i < coordinate[16][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p160[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 17宜蘭縣市
		for (int i = 0; i < coordinate[17][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p170[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 18澎湖縣
		for (int i = 0; i < coordinate[18][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p180[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}
		// 19金門縣
		for (int i = 0; i < coordinate[19][0].length; i++) {
			map.addMarker(new MarkerOptions()
					.visible(vis[0])
					.position(p190[i])
					.title("99度a")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.snippet("99度a"));
		}

	}

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
			latLng = new LatLng(latitude, longitude);
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

	public void setMapLocation() {
		try {

			double dLat = coordinate[no1][no2][no3][2]; // 抓取座標,南北緯
			double dLon = coordinate[no1][no2][no3][3]; // 抓取座標,東西經

			// if (spinnerNumber > 2) { // 初次進map不使用下拉選單
			LatLng gg = new LatLng(dLat, dLon); // 將座標位置輸入設定
			map.moveCamera(CameraUpdateFactory.newLatLng(gg)); // 移動到定點
			map.animateCamera(CameraUpdateFactory.zoomTo(15)); // 縮放
			// }
			// spinnerNumber = spinnerNumber + 1;
			Polyline line = map.addPolyline(new PolylineOptions()
					.add(gg, latLng).width(5).color(Color.RED));
		} catch (NullPointerException e) {
		}
	}

	void pushStore() {
		Intent intent = new Intent(this, PushStore.class);
		startActivity(intent);
	}

}
