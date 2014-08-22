package com.manlen.tutorials.pushnotifications;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

public class MainActivity extends FragmentActivity {
	/* update */
	protected static final int UPDATA_CLIENT = 0;
	protected static final int CHECK_UPDATE = 1;
	protected static final int DOWN_ERROR = 0;
	/** Called when the activity is first created. */
	private int serverVersion = 1; // �{�b����
	private int newServerVersion; // �̷s����
	private String downLoadApkUrl = "https://play.google.com/store/apps/details?id=com.manlen.tutorials.pushnotifications"; // ��m�̷s�ɮ׺��}

	/* �إ߮ୱ���| */

	/* tab1 */

	/* tab2 */
	private Button wheelWidget, openMap;
	int spinnerNumber = 0; // �즸�imap���ϥΤU�Կ��
	private float coordinate[][][][] = { // [����][������|��][���a][�y��]
			{
					{
							{ 0, 0, (float) 25.1288160, (float) 121.7404860 },// �򶩭�
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
							{ 1, 0, (float) 25.0574640, (float) 121.5554840 },// �x�_��
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
							{ 2, 0, (float) 25.0076090, (float) 121.5122990 }, // �s�_��
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
							{ 3, 0, (float) 24.9980900, (float) 121.3087550 }, // ��魹
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
							{ 4, 0, (float) 24.7750460, (float) 121.0274180 }, // �s�˥���
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
							{ 5, 0, (float) 24.8350340, (float) 121.0145440 }, // �s�˿���
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

							{ 6, 0, (float) 24.5716230, (float) 120.8305750 },// �]�߭�
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
							{ 7, 0, (float) 24.1593040, (float) 120.6798110 },// �x����
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
							{ 8, 0, (float) 23.9056200, (float) 120.6884100 }, // �n�뭹
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
							{ 9, 0, (float) 24.0794190, (float) 120.5405520 }, // ���ƭ�
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
							{ 10, 0, (float) 23.6722880, (float) 120.4759330 }, // ���L��
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
							{ 11, 0, (float) 23.4611770, (float) 120.2425220 }, // �Ÿq��
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
							{ 12, 0, (float) 23.0414130, (float) 120.2391620 },// �x�n����
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
							{ 13, 0, (float) 22.6442340, (float) 120.3087290 },// ��������
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
							{ 14, 0, (float) 22.3667560, (float) 120.5930540 }, // �̪F��
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
					{ { 15, 0, (float) 22.7536810, (float) 121.1526210 } },// �x�F��
					{ { 15, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 15, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 16, 0, (float) 23.9872900, (float) 121.6011890 }, // �Ὤ��
							{ 16, 0, (float) 23.9787210, (float) 121.6099400 },
							{ 16, 0, (float) 23.9762380, (float) 121.6019000 } },
					{ { 16, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 16, 5, (float) 24.1260010, (float) 120.6628070 } } },
			{
					{
							{ 17, 0, (float) 24.7487250, (float) 121.7509280 }, // �y����
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
					{ { 18, 5, (float) 24.1260010, (float) 120.6628070 } } }, // ���
			{ { { 19, 0, (float) 24.4303000, (float) 118.3154460 } },
					{ { 19, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 19, 5, (float) 24.1260010, (float) 120.6628070 } } },// ������
			{ { { 20, 0, (float) 26.1511110, (float) 119.9272220 } },
					{ { 20, 1, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 2, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 3, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 4, (float) 24.1260010, (float) 120.6628070 } },
					{ { 20, 5, (float) 24.1260010, (float) 120.6628070 } } } };

	/* tab3 */
	private TextView info, info2, info3;
	private Button scanner, scanner2;
	private int scannerError = 0;
	/* tab4 */
	private EditText searchName;
	private String searchObject, sn[], tableCommodity[][];
	private Button searchButton;
	private ImageButton imgButton[];
	public ProgressDialog dialog = null;
	private int opr[], pr[], sc[], id;
	List<ParseObject> Object;

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

	Typeface fontch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(),
				"fonts/wt034.ttf");
		/* update */
		/* �[�JStrictMode�קK�o�� android.os.NetworkOnMainThreadException */
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		/*
		 * StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		 * .penaltyLog().penaltyDeath().build());
		 */

		// JSONArray obj =
		// getJson("http://terryyamg.github.io/myweb/update_verson.json"); //
		// ��s��������ɦ�m
		// try {
		// for (int i = 0; i < obj.length(); i++) {
		// JSONObject data = obj.getJSONObject(i);
		// newServerVersion = Integer.parseInt(data.getString("code")); //
		// code���W�١A��X��newServerVersion����
		// }
		// } catch (JSONException e) {
		//
		// } catch (NullPointerException e) {
		//
		// }
		//
		// new Thread(new Runnable() {
		// public void run() {
		// try {
		// Message msg = new Message();
		// msg.what = CHECK_UPDATE;
		// handler.sendMessage(msg);
		//
		// } catch (NumberFormatException e) {
		// // TODO Auto-generated catch block
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		//
		// }
		//
		// }
		// }).start();

		/* �إ߮ୱ���| */
		addShortcut();
		// Track app opens.
		ParseAnalytics.trackAppOpened(getIntent());

		// Set up our UI member properties.

		/* tab1 */

		/* tab2 */
		// google map
		// try {

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

		// } catch (NullPointerException e) {
		// Log.i("map", "NullPointException");
		// }

		wheelWidget = (Button) findViewById(R.id.wheelWidget);
		openMap = (Button) findViewById(R.id.openMap);

		wheelWidget.setTypeface(fontch);
		openMap.setTypeface(fontch);

		wheelWidget.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				wheelWidget();
			}
		});
		openMap.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				goToGoogleMap();
			}
		});
		/* tab3 */
		info = (TextView) findViewById(R.id.info);
		info2 = (TextView) findViewById(R.id.info2);
		info3 = (TextView) findViewById(R.id.info3);
		scanner = (Button) findViewById(R.id.scanner);
		scanner2 = (Button) findViewById(R.id.scanner2);

		info.setTypeface(fontch); // �r��
		info2.setTypeface(fontch);
		info3.setTypeface(fontch);
		scanner.setTypeface(fontch);
		scanner2.setTypeface(fontch);

		scanner2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				int scannerNumberInfo0 = ParseInstallation
						.getCurrentInstallation().getInt("scannerNumber" + 0);
				int scannerNumberInfo1 = ParseInstallation
						.getCurrentInstallation().getInt("scannerNumber" + 1);
				int sumOfUse = scannerNumberInfo0 + scannerNumberInfo1;
				String setResult[] = { "99��a", "�֨���" };
				info2.setText("�z�`�@�w�g�ϥ�" + Integer.valueOf(sumOfUse).toString()
						+ "���u�f��� \n");

				String scannerNextTime0 = ParseInstallation
						.getCurrentInstallation().getString("scannerTime" + 0);
				String scannerNextTime1 = ParseInstallation
						.getCurrentInstallation().getString("scannerTime" + 1);

				info3.setText("�z�U���i�H�ϥ�" + setResult[0] + "�u�f���ɶ���"
						+ scannerNextTime0 + "\n" + "�z�U���i�H�ϥ�" + setResult[1]
						+ "�u�f���ɶ���" + scannerNextTime1);
			}
		});
		scanner.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent iScaner = new Intent("la.droid.qr.scan"); // �ϥ�QRDroid�����y�\��
				iScaner.putExtra("la.droid.qr.complete", true); // ����^�ǡA���I��scheme
				try {
					// �}�� QRDroid App �����y�\��A���� call back onActivityResult()
					startActivityForResult(iScaner, 0);
				} catch (ActivityNotFoundException ex) {
					// �Y�S�w�� QRDroid�A�h�}�� Google Play�ө��A����� QRDroid App
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("market://details?id=la.droid.qr"));
					startActivity(intent);
				}
			}
		});

		/* tab4 */

		searchName = (EditText) findViewById(R.id.searchName);
		searchButton = (Button) findViewById(R.id.searchButton);

		searchButton.setTypeface(fontch);

		// �j�M�ӫ~
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
				/* �p���`�� */
				try {
					searchObject = searchName.getText().toString(); // ���o��J��r
				} catch (Exception e) {

				}

			}
		});
		searchButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				goSearch();
			}
		});

		// �C�X�Ҧ��ӫ~
		try {
			ParseQuery<ParseObject> queryCommodity = new ParseQuery<ParseObject>(
					"Commodity"); // ����table
			Object = queryCommodity.find();// �j�M����
			int sizeob = Object.size(); // �X�����
			sn = new String[sizeob]; // �ӫ~�W�ٰ}�C
			opr = new int[sizeob]; // ����}�C
			pr = new int[sizeob]; // ���ʻ��}�C
			sc = new int[sizeob]; // �ӫ~���O�}�C
			tableCommodity = new String[sizeob + 1][3];

			int i = 0;
			for (ParseObject search : Object) {
				// ���o���
				sn[i] = (String) search.get("commodity");
				opr[i] = (int) search.getInt("originalPrice");
				pr[i] = (int) search.getInt("price");

				tableCommodity[i][0] = sn[i];
				tableCommodity[i][1] = Integer.toString(opr[i]);
				tableCommodity[i][2] = Integer.toString(pr[i]);

				Log.i("tableCommodity", tableCommodity[i][0] + "");
				Log.i("tableCommodity", tableCommodity[i][1] + "");
				Log.i("tableCommodity", tableCommodity[i][2] + "");

				i++;
			}

		} catch (Exception e) {
			Log.i("error", "error");
		}
		// �ƪ�
		setTable();

		/* tab5 */
		output = (TextView) findViewById(R.id.output);
		checkBox_service = (CheckBox) findViewById(R.id.checkBox_service);
		checkBox_service.setChecked(getFromSP("checkBox1")); // checkBox�x�s�]�w
		shareButton = (Button) findViewById(R.id.shareButton);
		myRecommend = (TextView) findViewById(R.id.myRecommend);
		keyRecommedn = (Button) findViewById(R.id.keyRecommend);

		output.setTypeface(fontch);
		shareButton.setTypeface(fontch);
		myRecommend.setTypeface(fontch);
		keyRecommedn.setTypeface(fontch);

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
							saveInSp("checkBox1", isChecked); // ����w����x�s�]�w

						} else {
							stop_Click();
							saveInSp("checkBox1", isChecked);// ������w����x�s�]�w
						}
					}

				});

		// ����app
		recommendFrequency = ParseInstallation.getCurrentInstallation().getInt(
				"recommendFrequency");
		objectId = ParseInstallation.getCurrentInstallation().getObjectId();
		myRecommend.setText("�ڪ����˽X: \n" + objectId + "\n�ڱ��˦��\����:"
				+ recommendFrequency);
		myRecommend.setTextSize(20);
		shareButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				shareDialog();
			}
		});
		// ��J���˽X keyRecommendNumber

		keyRecommedn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				keyRecommendNumber();
			}
		});

		// checkBox_service.performClick(); //�۰ʫ�checkBox

		/* tab5 */
		// this.genderFemaleButton = (RadioButton)
		// findViewById(R.id.gender_female_button);
		// this.genderMaleButton = (RadioButton)
		// findViewById(R.id.gender_male_button);
		// this.ageEditText = (EditText) findViewById(R.id.age_edit_text);
		// this.myNumber = (EditText) findViewById(R.id.my_number);
		// this.genderRadioGroup = (RadioGroup)
		// findViewById(R.id.gender_radio_group);

		/* �P�_�����O�_�}�� */
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);// �����o��service

		NetworkInfo networInfo = conManager.getActiveNetworkInfo(); // �b���o������T

		if (networInfo == null || !networInfo.isAvailable()) { // �P�_�O�_������
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("�����]�w")
					.setMessage("�z�|���}�Һ����s�u")
					.setPositiveButton("�Х��Ұʺ����s�u",
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
	// public static JSONArray getJson(String url) {
	// InputStream is = null;
	// String result = "";
	// // �Y�u�W��Ƭ��}�C�A�h�ϥ�JSONArray
	// JSONArray jsonArray = null;
	// // �Y�u�W��Ƭ��浧��ơA�h�ϥ�JSONObject
	// // JSONObject jsonObj = null;
	// // �z�LHTTP�s�u���o�^��
	// try {
	// HttpClient httpclient = new DefaultHttpClient(); // for port 80
	// HttpGet httppost = new HttpGet(url); // �n��Get�A��Post�|�X�{
	// // java.lang.string cannot
	// // be converted to jsonarray
	// HttpResponse response = httpclient.execute(httppost);//
	// �S���ȷ|catch���~�A�[�J�e��StrictMode�N�i�H
	// HttpEntity entity = response.getEntity();
	// is = entity.getContent();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // Ū���^��
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// is, "utf8"), 9999999); // 99999���Ǭy�j�p�A�Y��ƫܤj�A�i�ۦ�վ�
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// // �v����o���
	// sb.append(line + "\n");
	// }
	// is.close();
	// result = sb.toString();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // �ഫ��r��JSONArray
	// try {
	// jsonArray = new JSONArray(result);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return jsonArray;
	// }
	//
	// public void showUpdateDialog() {

	// @SuppressWarnings("unused")
	// AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
	// .setTitle("��s����").setIcon(android.R.drawable.ic_dialog_info)
	// .setMessage("�̷s�u�f�X�ӰաA�֨ӤU����s")
	// .setPositiveButton("�U��", new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss(); // ������ܮ�
	// downLoadApk();
	// }
	//
	// }).show();

	// }

	// protected void downLoadApk() {
	// final ProgressDialog pd; // �i�ױ���ܮ�
	// pd = new ProgressDialog(this);
	// pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	// pd.setMessage("���b�U����s");
	// pd.show();
	// new Thread() {
	// @Override
	// public void run() {
	// try {
	// File file = getFileFromServer(downLoadApkUrl, pd);
	// sleep(3000);
	// installApk(file);
	// pd.dismiss(); // �����i�ױ���ܮ�
	// } catch (Exception e) {
	// pd.dismiss();
	// Message msg = new Message();
	// msg.what = DOWN_ERROR;
	// handler.sendMessage(msg);
	// e.printStackTrace();
	// }
	// }
	// }.start();
	// }
	//
	// public static File getFileFromServer(String path, ProgressDialog pd)
	// throws Exception {
	// /* �p�G�۵����ܪ�ܷ�e��SDcard�����b����W�åB�O�i�Ϊ� */
	// if (Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED)) {
	// URL url = new URL(path);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setConnectTimeout(5000);
	// pd.setMax(conn.getContentLength()); // ����ƥ����j�p
	// InputStream is = conn.getInputStream();
	// File file = new File(Environment.getExternalStorageDirectory(),
	// "update.apk");
	// FileOutputStream fos = new FileOutputStream(file);
	// BufferedInputStream bis = new BufferedInputStream(is);
	// byte[] buffer = new byte[1024];
	// int len;
	// int total = 0;
	// while ((len = bis.read(buffer)) != -1) {
	// fos.write(buffer, 0, len);
	// total += len;
	// pd.setProgress(total); // �����e�U���q
	// }
	// fos.close();
	// bis.close();
	// is.close();
	// return file;
	// } else {
	// return null;
	// }
	// }
	//
	// /* �w��APK */
	// protected void installApk(File file) {
	// Intent intent = new Intent();
	// intent.setAction(Intent.ACTION_VIEW); // ����ʧ@
	// intent.setDataAndType(Uri.fromFile(file),
	// "application/vnd.android.package-archive"); // ���檺�ƾ�����
	// startActivity(intent);
	// }
	//
	// Handler handler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case DOWN_ERROR:
	// // �U��APK����
	// Toast.makeText(getApplicationContext(), "�U���s��������", 1).show();
	// break;
	// case CHECK_UPDATE:
	// // �ˬd��s
	//
	// if (serverVersion == 0) {
	// serverVersion = newServerVersion;
	// }
	//
	// if (serverVersion != newServerVersion) {
	// showUpdateDialog(); // �����s
	// }
	// break;
	// }
	// }
	// };

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

	/* tab1 */

	/* tab2 */
	void wheelWidget() {
		progress();
		Intent intent = new Intent(this, WheelWidget.class);
		startActivity(intent);
	}

	void goToGoogleMap() {
		progress();
		Intent intent = new Intent(this, GoToGoogleMap.class);
		startActivity(intent);
	}

	/* tab3 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (0 == requestCode && null != data && data.getExtras() != null) {
			// ���y���G�s��b key �� la.droid.qr.result ���Ȥ�
			String result = data.getExtras().getString("la.droid.qr.result");
			String setResult[] = { "99��a", "donutes" };
			int scannerNumber[] = new int[setResult.length];
			String scannerNextTime[] = new String[setResult.length];
			for (int i = 0; i < setResult.length; i++) {
				if (setResult[i].equals(result)) {
					String messsenger = "�w��ϥ� \n" + "�����q�u�f���";

					scannerNumber[i] = ParseInstallation
							.getCurrentInstallation().getInt(
									"scannerNumber" + i); // ���X��i�����y����

					scannerNextTime[i] = ParseInstallation
							.getCurrentInstallation().getString(
									"scannerTime" + i); // ���X��i���U���i�H���y�ɶ�

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss"); // ���o�p��
					String scannerTime = sdf.format(new java.util.Date()); // ���o�{�b�ɶ�
					int waitTime = 1; // �N�o�ɶ�

					Date dt = null; // �{�b�ɶ�date��l��

					try {

						dt = sdf.parse(scannerTime);// �{�b�ɶ�

					} catch (java.text.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dt);
					calendar.add(Calendar.HOUR, waitTime);// +�N�o�ɶ�
					Date tdt = calendar.getTime();// ���o�[��L�᪺Date
					String sumTime = sdf.format(tdt);// �̷ӳ]�w�榡���o�r��

					if (scannerNextTime[i] == null) { // �즸���y�AscannerNextTime���ŭ�

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
						info2.setText("�z�w�g�ϥ�"
								+ Integer.valueOf(scannerNumber[i]).toString()
								+ "��" + setResult[i] + "�u�f���");

						info3.setText("�z�U���i�H�ϥ�" + setResult[i] + "�u�f���ɶ���"
								+ sumTime);
						info.setTextSize(20);
						info.setText(messsenger); // �N���G��ܦb TextVeiw ��
					} else { // scannerNextTime����
						Date snt = null;// ���X�ɶ�
						try {
							snt = sdf.parse(scannerNextTime[i]);
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if (snt.before(dt)) { // ����X�ӥi���y�ɶ�(�L�h)snt before
												// �{�b�ɶ�dt�A�i�O��

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
							info2.setText("�z�w�g�ϥ�"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "��" + setResult[i]
									+ "�u�f���");

							info3.setText("�z�U���i�H�ϥ�" + setResult[i] + "�u�f���ɶ���"
									+ sumTime);
							info.setTextSize(20);
							info.setText(messsenger); // �N���G��ܦb TextVeiw ��
						} else { // �N�o�ɶ��|�������A���O��
							info2.setText("�z�w�g�ϥ�"
									+ Integer.valueOf(scannerNumber[i])
											.toString() + "��" + setResult[i]
									+ "�u�f���");

							info3.setText("�z�U���i�H�ϥ�" + setResult[i] + "�u�f���ɶ���"
									+ scannerNextTime[i]);

							info.setTextSize(20);
							info.setText(messsenger); // �N���G��ܦb TextVeiw ��
						}
					}
					break;
				} else {
					scannerError++;
					if (scannerError <= 3) {
						String messsenger = "��p�A�S�����˦��\; \n" + "�z�i�H�A�դ@��";
						info.setTextSize(20);
						info.setText(messsenger); // �N���G��ܦb TextVeiw ��
					} else {
						String messsenger = "�z�i�H�бЪ����H���A \n" + "�λP�ڭ��pô";
						info.setTextSize(20);
						info.setText(messsenger); // �N���G��ܦb TextVeiw ��
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

	void setTable() {
		TableLayout t1 = (TableLayout) findViewById(R.id.tableSet);
		Log.i("tableCommodity.length", tableCommodity.length + "");
		imgButton = new ImageButton[tableCommodity.length];
		// �ƪ�
		for (int i = 0; i < (tableCommodity.length-1)*3; i++) { // �C
			TableRow row = new TableRow(this);

			// �Ĥ@�C �Ϥ�
			if(i%3==0){
			Log.i("iiii", i + "");
			imgButton[i/3] = new ImageButton(this);
			imgButton[i/3].setImageResource(R.drawable.store + (i/3 + 1));
			imgButton[i/3].setBackgroundDrawable(null);
			imgButton[i/3].setId(i/3);
			imgButton[i/3].setOnClickListener(imgButtonListen);
			row.addView(imgButton[i/3], 0);
			}else if(i%3==1){
			// �ĤG�C �ӫ~�W��
			TextView tv2 = new TextView(this);
			tv2.setTextSize(15);
			tv2.setTypeface(fontch);
			tv2.setTextColor(Color.WHITE);
			tv2.setText(tableCommodity[i/3][0] + " ");
			row.addView(tv2, 0);
			}else if(i%3==2){
			// �ĤT�C ����
			TextView tv3 = new TextView(this);
			tv3.setTextSize(15);
			tv3.setTypeface(fontch);
			tv3.setTextColor(Color.WHITE);
			String oprl = tableCommodity[i/3][1];
			String prl = tableCommodity[i/3][2];
			String ss = "���:$" + oprl + "���ʻ�:$" + prl;
			oprl.length(); // ����Ʀr����
			prl.length();// ���ʼƦr����
			Spannable msp = new SpannableString(ss);
			msp.setSpan(new StrikethroughSpan(), 4, 4 + oprl.length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �R���u
			msp.setSpan(new RelativeSizeSpan(2.0f), 9 + oprl.length(),
					9 + oprl.length() + prl.length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// �⭿�j�p
			tv3.setText(msp);
			row.addView(tv3, 0);
			}
			t1.addView(row);
		}

	}

	private OnClickListener imgButtonListen = new OnClickListener() {
		public void onClick(View v) {
			id = v.getId();
			Log.i("iddddd", id+"");
			goToListCommodity();

		}
	};

	void goToListCommodity() {
		progress();
		Intent intent = new Intent(this, ListCommodity.class);
		intent.putExtra("storeName", sn[id]);
		Log.i("button", sn[id]+"");
		//startActivity(intent);
	}

	void progress() {
		dialog = ProgressDialog.show(this, "Ū����", "�еy��...", true);
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
		intent.putExtra("length00", coordinate[0][0].length); // �ǰe����
		for (int i = 0; i < coordinate[0][0].length; i++) {
			intent.putExtra("LATITUDE00" + i, latitude00[i]);
			intent.putExtra("LONGITUDE00" + i, longitude00[i]);
		}

		intent.putExtra("length10", coordinate[1][0].length); // �ǰe����
		for (int i = 0; i < coordinate[1][0].length; i++) {
			intent.putExtra("LATITUDE10" + i, latitude10[i]);
			intent.putExtra("LONGITUDE10" + i, longitude10[i]);
		}

		intent.putExtra("length20", coordinate[2][0].length); // �ǰe����
		for (int i = 0; i < coordinate[2][0].length; i++) {
			intent.putExtra("LATITUDE20" + i, latitude20[i]);
			intent.putExtra("LONGITUDE20" + i, longitude20[i]);
		}

		intent.putExtra("length30", coordinate[3][0].length); // �ǰe����
		for (int i = 0; i < coordinate[3][0].length; i++) {
			intent.putExtra("LATITUDE30" + i, latitude30[i]);
			intent.putExtra("LONGITUDE30" + i, longitude30[i]);
		}

		intent.putExtra("length40", coordinate[4][0].length); // �ǰe����
		for (int i = 0; i < coordinate[4][0].length; i++) {
			intent.putExtra("LATITUDE40" + i, latitude40[i]);
			intent.putExtra("LONGITUDE40" + i, longitude40[i]);
		}

		intent.putExtra("length50", coordinate[5][0].length); // �ǰe����
		for (int i = 0; i < coordinate[5][0].length; i++) {
			intent.putExtra("LATITUDE50" + i, latitude50[i]);
			intent.putExtra("LONGITUDE50" + i, longitude50[i]);
		}

		intent.putExtra("length60", coordinate[6][0].length); // �ǰe����
		for (int i = 0; i < coordinate[6][0].length; i++) {
			intent.putExtra("LATITUDE60" + i, latitude60[i]);
			intent.putExtra("LONGITUDE60" + i, longitude60[i]);
		}
		intent.putExtra("length65", coordinate[6][5].length); // �ǰe����
		for (int i = 0; i < coordinate[6][5].length; i++) {
			intent.putExtra("LATITUDE65" + i, latitude65[i]);
			intent.putExtra("LONGITUDE65" + i, longitude65[i]);
		}

		intent.putExtra("length70", coordinate[7][0].length); // �ǰe����
		for (int i = 0; i < coordinate[7][0].length; i++) {
			intent.putExtra("LATITUDE70" + i, latitude70[i]);
			intent.putExtra("LONGITUDE70" + i, longitude70[i]);
		}

		intent.putExtra("length80", coordinate[8][0].length); // �ǰe����
		for (int i = 0; i < coordinate[8][0].length; i++) {
			intent.putExtra("LATITUDE80" + i, latitude80[i]);
			intent.putExtra("LONGITUDE80" + i, longitude80[i]);
		}

		intent.putExtra("length90", coordinate[9][0].length); // �ǰe����
		for (int i = 0; i < coordinate[9][0].length; i++) {
			intent.putExtra("LATITUDE90" + i, latitude90[i]);
			intent.putExtra("LONGITUDE90" + i, longitude90[i]);
		}

		intent.putExtra("length100", coordinate[10][0].length); // �ǰe����
		for (int i = 0; i < coordinate[10][0].length; i++) {
			intent.putExtra("LATITUDE100" + i, latitude100[i]);
			intent.putExtra("LONGITUDE100" + i, longitude100[i]);
		}
		intent.putExtra("length110", coordinate[11][0].length); // �ǰe����
		for (int i = 0; i < coordinate[11][0].length; i++) {
			intent.putExtra("LATITUDE110" + i, latitude110[i]);
			intent.putExtra("LONGITUDE110" + i, longitude110[i]);
		}

		intent.putExtra("length120", coordinate[12][0].length); // �ǰe����
		for (int i = 0; i < coordinate[12][0].length; i++) {
			intent.putExtra("LATITUDE120" + i, latitude120[i]);
			intent.putExtra("LONGITUDE120" + i, longitude120[i]);
		}

		intent.putExtra("length130", coordinate[13][0].length); // �ǰe����
		for (int i = 0; i < coordinate[13][0].length; i++) {
			intent.putExtra("LATITUDE130" + i, latitude130[i]);
			intent.putExtra("LONGITUDE130" + i, longitude130[i]);
		}
		intent.putExtra("length140", coordinate[14][0].length); // �ǰe����
		for (int i = 0; i < coordinate[14][0].length; i++) {
			intent.putExtra("LATITUDE140" + i, latitude140[i]);
			intent.putExtra("LONGITUDE140" + i, longitude140[i]);
		}
		intent.putExtra("length150", coordinate[15][0].length); // �ǰe����
		for (int i = 0; i < coordinate[15][0].length; i++) {
			intent.putExtra("LATITUDE150" + i, latitude150[i]);
			intent.putExtra("LONGITUDE150" + i, longitude150[i]);
		}
		intent.putExtra("length160", coordinate[16][0].length); // �ǰe����
		for (int i = 0; i < coordinate[16][0].length; i++) {
			intent.putExtra("LATITUDE160" + i, latitude160[i]);
			intent.putExtra("LONGITUDE160" + i, longitude160[i]);
		}
		intent.putExtra("length170", coordinate[17][0].length); // �ǰe����
		for (int i = 0; i < coordinate[17][0].length; i++) {
			intent.putExtra("LATITUDE170" + i, latitude170[i]);
			intent.putExtra("LONGITUDE170" + i, longitude170[i]);
		}
		intent.putExtra("length180", coordinate[18][0].length); // �ǰe����
		for (int i = 0; i < coordinate[18][0].length; i++) {
			intent.putExtra("LATITUDE180" + i, latitude180[i]);
			intent.putExtra("LONGITUDE180" + i, longitude180[i]);
		}
		intent.putExtra("length190", coordinate[19][0].length); // �ǰe����
		for (int i = 0; i < coordinate[19][0].length; i++) {
			intent.putExtra("LATITUDE190" + i, latitude190[i]);
			intent.putExtra("LONGITUDE190" + i, longitude190[i]);
		}

		startService(intent);
		output.setText("�A�ȱҰʤ�");
	}

	public void stop_Click() {
		Intent intent = new Intent(this, GPSService.class);
		stopService(intent);
		output.setText("�A�Ȱ��");
		output.setTextSize(20);
	}

	// ����app
	void shareDialog() {

		String shareText = "���p�q�u�f�A���z������|�ֳ��ٿ�  "
				+ "\n https://play.google.com/store/apps/details?id=com.manlen.tutorials.pushnotifications"
				+ "\n��J�ڪ����˽X" + objectId + "\n �N�i��o�u�f";
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
		startActivity(Intent.createChooser(shareIntent, "����"));
	}

	void keyRecommendNumber() {
		Intent intent = new Intent(this, KeyInRecommendNumber.class);
		startActivity(intent);
	}

	/* �x�s�]�w */
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
		// �q�귽���OR�����o��������
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec = tabHost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab1));
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab2");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab2));
		spec.setContent(R.id.tab2);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab3");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab3));
		spec.setContent(R.id.tab3);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab4");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab4));
		spec.setContent(R.id.tab4);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tab5");
		spec.setIndicator("", getResources().getDrawable(R.drawable.tab5));
		spec.setContent(R.id.tab5);
		tabHost.addTab(spec);

		// spec = tabHost.newTabSpec("tab5");
		// spec.setIndicator("", getResources().getDrawable(R.drawable.tab5));
		// spec.setContent(R.id.tab5);
		// tabHost.addTab(spec);

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

	/* ���}�{�� */
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			closeAll();
			return true;
		}
		return super.onKeyDown(keycode, event);
	}

	public void closeAll() {
		new AlertDialog.Builder(MainActivity.this).setTitle("�T�w���}���{���H")
				.setMessage("��������i�I������")
				.setNegativeButton("���}", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setPositiveButton("�~��", new DialogInterface.OnClickListener() {
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
