package com.manlen.tutorials.pushnotifications;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;



public class GPSService extends Service implements LocationListener {
	private LocationManager manager;
	private boolean isInArea;
	private int length00, length10, length20, length30, length40, length50,
			length60, length65, length70, length80, length90, length100,
			length110, length120, length130, length140, length150, length160,
			length170, length180, length190;
	private double[] latitude00, longitude00, latitude10, longitude10,
			latitude20, longitude20, latitude30, longitude30, latitude40,
			longitude40, latitude50, longitude50, latitude60, longitude60,
			latitude65, longitude65, latitude70, longitude70, latitude80,
			longitude80, latitude90, longitude90, latitude100, longitude100,
			latitude110, longitude110, latitude120, longitude120, latitude130,
			longitude130, latitude140, longitude140, latitude150, longitude150,
			latitude160, longitude160, latitude170, longitude170, latitude180,
			longitude180, latitude190, longitude190;

	// Get from the SharedPreferences
	/*
	 * SharedPreferences settings =
	 * getApplicationContext().getSharedPreferences( "Frequency", 0); private
	 * int Frequency = settings.getInt("Frequency", 0);
	 */

	@Override
	public void onCreate() {

		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,
				1, this);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1,
				this);
		isInArea = false;

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			length00 = (int) intent.getIntExtra("length00", 1); // 取得座標1的長度
			latitude00 = new double[length00];
			longitude00 = new double[length00];
			for (int i = 0; i < length00; i++) {
				latitude00[i] = (double) intent.getFloatExtra("LATITUDE00" + i,
						1f); // 取得00的所有latitude座標
				longitude00[i] = (double) intent.getFloatExtra("LONGITUDE00"
						+ i, 1f); // 取得00的所有longitude座標
			}

			length10 = (int) intent.getIntExtra("length10", 1); // 取得座標1的長度
			latitude10 = new double[length10];
			longitude10 = new double[length10];
			for (int i = 0; i < length10; i++) {
				latitude10[i] = (double) intent.getFloatExtra("LATITUDE10" + i,
						1f); // 取得10的所有latitude座標
				longitude10[i] = (double) intent.getFloatExtra("LONGITUDE10"
						+ i, 1f); // 取得10的所有longitude座標
			}

			length20 = (int) intent.getIntExtra("length20", 1); // 取得座標1的長度
			latitude20 = new double[length20];
			longitude20 = new double[length20];
			for (int i = 0; i < length20; i++) {
				latitude20[i] = (double) intent.getFloatExtra("LATITUDE20" + i,
						1f); // 取得20的所有latitude座標
				longitude20[i] = (double) intent.getFloatExtra("LONGITUDE20"
						+ i, 1f); // 取得20的所有longitude座標
			}

			length30 = (int) intent.getIntExtra("length30", 1); // 取得座標1的長度
			latitude30 = new double[length30];
			longitude30 = new double[length30];
			for (int i = 0; i < length30; i++) {
				latitude30[i] = (double) intent.getFloatExtra("LATITUDE30" + i,
						1f); // 取得30的所有latitude座標
				longitude30[i] = (double) intent.getFloatExtra("LONGITUDE30"
						+ i, 1f); // 取得30的所有longitude座標
			}

			length40 = (int) intent.getIntExtra("length40", 1); // 取得座標1的長度
			latitude40 = new double[length40];
			longitude40 = new double[length40];
			for (int i = 0; i < length40; i++) {
				latitude40[i] = (double) intent.getFloatExtra("LATITUDE40" + i,
						1f); // 取得40的所有latitude座標
				longitude40[i] = (double) intent.getFloatExtra("LONGITUDE40"
						+ i, 1f); // 取得40的所有longitude座標
			}

			length50 = (int) intent.getIntExtra("length50", 1); // 取得座標1的長度
			latitude50 = new double[length50];
			longitude50 = new double[length50];
			for (int i = 0; i < length50; i++) {
				latitude50[i] = (double) intent.getFloatExtra("LATITUDE50" + i,
						1f); // 取得50的所有latitude座標
				longitude50[i] = (double) intent.getFloatExtra("LONGITUDE50"
						+ i, 1f); // 取得50的所有longitude座標
			}

			length60 = (int) intent.getIntExtra("length60", 1); // 取得座標1的長度
			latitude60 = new double[length60];
			longitude60 = new double[length60];
			for (int i = 0; i < length60; i++) {
				latitude60[i] = (double) intent.getFloatExtra("LATITUDE60" + i,
						1f); // 取得60的所有latitude座標
				longitude60[i] = (double) intent.getFloatExtra("LONGITUDE60"
						+ i, 1f); // 取得60的所有longitude座標
			}

			length65 = (int) intent.getIntExtra("length65", 1); // 取得座標1的長度
			latitude65 = new double[length65];
			longitude65 = new double[length65];
			for (int i = 0; i < length65; i++) {
				latitude65[i] = (double) intent.getFloatExtra("LATITUDE65" + i,
						1f); // 取得60的所有latitude座標
				longitude65[i] = (double) intent.getFloatExtra("LONGITUDE65"
						+ i, 1f); // 取得60的所有longitude座標
			}

			length70 = (int) intent.getIntExtra("length70", 1); // 取得座標1的長度
			latitude70 = new double[length70];
			longitude70 = new double[length70];
			for (int i = 0; i < length70; i++) {
				latitude70[i] = (double) intent.getFloatExtra("LATITUDE70" + i,
						1f); // 取得70的所有latitude座標
				longitude70[i] = (double) intent.getFloatExtra("LONGITUDE70"
						+ i, 1f); // 取得70的所有longitude座標
			}

			length80 = (int) intent.getIntExtra("length80", 1); // 取得座標1的長度
			latitude80 = new double[length80];
			longitude80 = new double[length80];
			for (int i = 0; i < length80; i++) {
				latitude80[i] = (double) intent.getFloatExtra("LATITUDE80" + i,
						1f); // 取得80的所有latitude座標
				longitude80[i] = (double) intent.getFloatExtra("LONGITUDE80"
						+ i, 1f); // 取得80的所有longitude座標
			}

			length90 = (int) intent.getIntExtra("length90", 1); // 取得座標1的長度
			latitude90 = new double[length90];
			longitude90 = new double[length90];
			for (int i = 0; i < length90; i++) {
				latitude90[i] = (double) intent.getFloatExtra("LATITUDE90" + i,
						1f); // 取得90的所有latitude座標
				longitude90[i] = (double) intent.getFloatExtra("LONGITUDE90"
						+ i, 1f); // 取得90的所有longitude座標
			}

			length100 = (int) intent.getIntExtra("length100", 1); // 取得座標1的長度
			latitude100 = new double[length100];
			longitude100 = new double[length100];
			for (int i = 0; i < length100; i++) {
				latitude100[i] = (double) intent.getFloatExtra("LATITUDE100"
						+ i, 1f); // 取得100的所有latitude座標
				longitude100[i] = (double) intent.getFloatExtra("LONGITUDE100"
						+ i, 1f); // 取得100的所有longitude座標
			}

			length110 = (int) intent.getIntExtra("length110", 1); // 取得座標1的長度
			latitude110 = new double[length110];
			longitude110 = new double[length110];
			for (int i = 0; i < length110; i++) {
				latitude110[i] = (double) intent.getFloatExtra("LATITUDE110"
						+ i, 1f); // 取得110的所有latitude座標
				longitude110[i] = (double) intent.getFloatExtra("LONGITUDE110"
						+ i, 1f); // 取得110的所有longitude座標
			}

			length120 = (int) intent.getIntExtra("length120", 1); // 取得座標1的長度
			latitude120 = new double[length120];
			longitude120 = new double[length120];
			for (int i = 0; i < length120; i++) {
				latitude120[i] = (double) intent.getFloatExtra("LATITUDE120"
						+ i, 1f); // 取得120的所有latitude座標
				longitude120[i] = (double) intent.getFloatExtra("LONGITUDE120"
						+ i, 1f); // 取得120的所有longitude座標
			}

			length130 = (int) intent.getIntExtra("length130", 1); // 取得座標1的長度
			latitude130 = new double[length130];
			longitude130 = new double[length130];
			for (int i = 0; i < length130; i++) {
				latitude130[i] = (double) intent.getFloatExtra("LATITUDE130"
						+ i, 1f); // 取得130的所有latitude座標
				longitude130[i] = (double) intent.getFloatExtra("LONGITUDE130"
						+ i, 1f); // 取得130的所有longitude座標
			}

			length140 = (int) intent.getIntExtra("length140", 1); // 取得座標1的長度
			latitude140 = new double[length140];
			longitude140 = new double[length140];
			for (int i = 0; i < length140; i++) {
				latitude140[i] = (double) intent.getFloatExtra("LATITUDE140"
						+ i, 1f); // 取得140的所有latitude座標
				longitude140[i] = (double) intent.getFloatExtra("LONGITUDE140"
						+ i, 1f); // 取得140的所有longitude座標
			}

			length150 = (int) intent.getIntExtra("length150", 1); // 取得座標1的長度
			latitude150 = new double[length150];
			longitude150 = new double[length150];
			for (int i = 0; i < length150; i++) {
				latitude150[i] = (double) intent.getFloatExtra("LATITUDE150"
						+ i, 1f); // 取得150的所有latitude座標
				longitude150[i] = (double) intent.getFloatExtra("LONGITUDE150"
						+ i, 1f); // 取得150的所有longitude座標
			}

			length160 = (int) intent.getIntExtra("length160", 1); // 取得座標1的長度
			latitude160 = new double[length160];
			longitude160 = new double[length160];
			for (int i = 0; i < length160; i++) {
				latitude160[i] = (double) intent.getFloatExtra("LATITUDE160"
						+ i, 1f); // 取得160的所有latitude座標
				longitude160[i] = (double) intent.getFloatExtra("LONGITUDE160"
						+ i, 1f); // 取得160的所有longitude座標
			}

			length170 = (int) intent.getIntExtra("length170", 1); // 取得座標1的長度
			latitude170 = new double[length170];
			longitude170 = new double[length170];
			for (int i = 0; i < length170; i++) {
				latitude170[i] = (double) intent.getFloatExtra("LATITUDE170"
						+ i, 1f); // 取得170的所有latitude座標
				longitude170[i] = (double) intent.getFloatExtra("LONGITUDE170"
						+ i, 1f); // 取得170的所有longitude座標
			}

			length180 = (int) intent.getIntExtra("length180", 1); // 取得座標1的長度
			latitude180 = new double[length180];
			longitude180 = new double[length180];
			for (int i = 0; i < length180; i++) {
				latitude180[i] = (double) intent.getFloatExtra("LATITUDE180"
						+ i, 1f); // 取得180的所有latitude座標
				longitude180[i] = (double) intent.getFloatExtra("LONGITUDE180"
						+ i, 1f); // 取得180的所有longitude座標
			}

			length190 = (int) intent.getIntExtra("length190", 1); // 取得座標1的長度
			latitude190 = new double[length190];
			longitude190 = new double[length190];
			for (int i = 0; i < length190; i++) {
				latitude190[i] = (double) intent.getFloatExtra("LATITUDE190"
						+ i, 1f); // 取得190的所有latitude座標
				longitude190[i] = (double) intent.getFloatExtra("LONGITUDE190"
						+ i, 1f); // 取得190的所有longitude座標
			}

		} catch (NullPointerException e) {

		}
		// for (int i = 0; i < length1; i++) {
		// Log.d("GPSService", "lat/long: " + latitude1[i] + ": " +
		// longitude1[i]);
		// }

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		manager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location current) {
		// TODO Auto-generated method stub

		int Frequency = getFrequency("Frequency"); // 取的儲存的廣播次數
		if (current == null)
			return;
		Location dest = new Location(current);

		float[] distance00 = new float[length00];
		for (int i = 0; i < length00; i++) {
			dest.setLatitude(latitude00[i]);
			dest.setLongitude(longitude00[i]);
			distance00[i] = current.distanceTo(dest);// 計算0的所有座標距離
		}

		float[] distance10 = new float[length10];
		for (int i = 0; i < length10; i++) {
			dest.setLatitude(latitude10[i]);
			dest.setLongitude(longitude10[i]);
			distance10[i] = current.distanceTo(dest);// 計算1的所有座標距離
		}

		float[] distance20 = new float[length20];
		for (int i = 0; i < length20; i++) {
			dest.setLatitude(latitude20[i]);
			dest.setLongitude(longitude20[i]);
			distance20[i] = current.distanceTo(dest);// 計算2的所有座標距離
		}

		float[] distance30 = new float[length30];
		for (int i = 0; i < length30; i++) {
			dest.setLatitude(latitude30[i]);
			dest.setLongitude(longitude30[i]);
			distance30[i] = current.distanceTo(dest);// 計算3的所有座標距離
		}

		float[] distance40 = new float[length40];
		for (int i = 0; i < length40; i++) {
			dest.setLatitude(latitude40[i]);
			dest.setLongitude(longitude40[i]);
			distance40[i] = current.distanceTo(dest);// 計算4的所有座標距離
		}

		float[] distance50 = new float[length50];
		for (int i = 0; i < length50; i++) {
			dest.setLatitude(latitude50[i]);
			dest.setLongitude(longitude50[i]);
			distance50[i] = current.distanceTo(dest);// 計算5的所有座標距離
		}

		float[] distance60 = new float[length60];
		for (int i = 0; i < length60; i++) {
			dest.setLatitude(latitude60[i]);
			dest.setLongitude(longitude60[i]);
			distance60[i] = current.distanceTo(dest);// 計算60的所有座標距離
		}
		float[] distance65 = new float[length65];
		for (int i = 0; i < length65; i++) {
			dest.setLatitude(latitude65[i]);
			dest.setLongitude(longitude65[i]);
			distance65[i] = current.distanceTo(dest);// 計算65的所有座標距離
		}

		float[] distance70 = new float[length70];
		for (int i = 0; i < length70; i++) {
			dest.setLatitude(latitude70[i]);
			dest.setLongitude(longitude70[i]);
			distance70[i] = current.distanceTo(dest);// 計算7的所有座標距離
		}

		float[] distance80 = new float[length80];
		for (int i = 0; i < length80; i++) {
			dest.setLatitude(latitude80[i]);
			dest.setLongitude(longitude80[i]);
			distance80[i] = current.distanceTo(dest);// 計算8的所有座標距離
		}

		float[] distance90 = new float[length90];
		for (int i = 0; i < length90; i++) {
			dest.setLatitude(latitude90[i]);
			dest.setLongitude(longitude90[i]);
			distance90[i] = current.distanceTo(dest);// 計算9的所有座標距離
		}

		float[] distance100 = new float[length100];
		for (int i = 0; i < length100; i++) {
			dest.setLatitude(latitude100[i]);
			dest.setLongitude(longitude100[i]);
			distance100[i] = current.distanceTo(dest);// 計算10的所有座標距離
		}

		float[] distance110 = new float[length110];
		for (int i = 0; i < length110; i++) {
			dest.setLatitude(latitude110[i]);
			dest.setLongitude(longitude110[i]);
			distance110[i] = current.distanceTo(dest);// 計算11的所有座標距離
		}

		float[] distance120 = new float[length120];
		for (int i = 0; i < length120; i++) {
			dest.setLatitude(latitude120[i]);
			dest.setLongitude(longitude120[i]);
			distance120[i] = current.distanceTo(dest);// 計算12的所有座標距離
		}

		float[] distance130 = new float[length130];
		for (int i = 0; i < length130; i++) {
			dest.setLatitude(latitude130[i]);
			dest.setLongitude(longitude130[i]);
			distance130[i] = current.distanceTo(dest);// 計算13的所有座標距離
		}

		float[] distance140 = new float[length140];
		for (int i = 0; i < length140; i++) {
			dest.setLatitude(latitude140[i]);
			dest.setLongitude(longitude140[i]);
			distance140[i] = current.distanceTo(dest);// 計算14的所有座標距離
		}
		float[] distance150 = new float[length150];
		for (int i = 0; i < length150; i++) {
			dest.setLatitude(latitude150[i]);
			dest.setLongitude(longitude150[i]);
			distance150[i] = current.distanceTo(dest);// 計算15的所有座標距離
		}
		float[] distance160 = new float[length160];
		for (int i = 0; i < length160; i++) {
			dest.setLatitude(latitude160[i]);
			dest.setLongitude(longitude160[i]);
			distance160[i] = current.distanceTo(dest);// 計算16的所有座標距離
		}
		float[] distance170 = new float[length170];
		for (int i = 0; i < length170; i++) {
			dest.setLatitude(latitude170[i]);
			dest.setLongitude(longitude170[i]);
			distance170[i] = current.distanceTo(dest);// 計算17的所有座標距離
		}
		float[] distance180 = new float[length180];
		for (int i = 0; i < length180; i++) {
			dest.setLatitude(latitude180[i]);
			dest.setLongitude(longitude180[i]);
			distance180[i] = current.distanceTo(dest);// 計算18的所有座標距離
		}
		float[] distance190 = new float[length190];
		for (int i = 0; i < length190; i++) {
			dest.setLatitude(latitude190[i]);
			dest.setLongitude(longitude190[i]);
			distance190[i] = current.distanceTo(dest);// 計算19的所有座標距離
		}
try{
		if (distance00[0] < 1000.0 || distance00[1] < 1000.0
				|| distance00[2] < 1000.0 || distance00[3] < 1000.0
				|| distance00[4] < 1000.0 || distance00[5] < 1000.0
				|| distance00[6] < 1000.0 || distance00[7] < 1000.0
				|| distance10[0] < 1000.0 || distance10[1] < 1000.0
				|| distance10[2] < 1000.0 || distance10[3] < 1000.0
				|| distance10[4] < 1000.0 || distance10[5] < 1000.0
				|| distance10[6] < 1000.0 || distance10[7] < 1000.0
				|| distance10[8] < 1000.0 || distance10[9] < 1000.0
				|| distance10[10] < 1000.0 || distance10[11] < 1000.0
				|| distance10[12] < 1000.0 || distance10[13] < 1000.0
				|| distance10[14] < 1000.0 || distance10[15] < 1000.0
				|| distance10[16] < 1000.0 || distance10[17] < 1000.0
				|| distance10[18] < 1000.0 || distance10[19] < 1000.0
				|| distance10[20] < 1000.0 || distance10[21] < 1000.0
				|| distance10[22] < 1000.0 || distance10[23] < 1000.0
				|| distance10[24] < 1000.0 || distance10[25] < 1000.0
				|| distance10[26] < 1000.0 || distance10[27] < 1000.0
				|| distance10[28] < 1000.0 || distance10[29] < 1000.0
				|| distance10[30] < 1000.0 || distance10[31] < 1000.0
				|| distance10[32] < 1000.0 || distance10[33] < 1000.0
				|| distance10[34] < 1000.0 || distance10[35] < 1000.0
				|| distance10[36] < 1000.0 || distance10[37] < 1000.0
				|| distance10[38] < 1000.0 || distance10[39] < 1000.0
				|| distance10[40] < 1000.0 || distance20[0] < 1000.0
				|| distance20[1] < 1000.0 || distance20[2] < 1000.0
				|| distance20[3] < 1000.0 || distance20[4] < 1000.0
				|| distance20[5] < 1000.0 || distance20[6] < 1000.0
				|| distance20[7] < 1000.0 || distance20[8] < 1000.0
				|| distance20[9] < 1000.0 || distance20[10] < 1000.0
				|| distance20[11] < 1000.0 || distance20[12] < 1000.0
				|| distance20[13] < 1000.0 || distance20[14] < 1000.0
				|| distance20[15] < 1000.0 || distance20[16] < 1000.0
				|| distance20[17] < 1000.0 || distance20[18] < 1000.0
				|| distance20[19] < 1000.0 || distance20[20] < 1000.0
				|| distance20[21] < 1000.0 || distance20[22] < 1000.0
				|| distance20[23] < 1000.0 || distance20[24] < 1000.0
				|| distance20[25] < 1000.0 || distance20[26] < 1000.0
				|| distance20[27] < 1000.0 || distance20[28] < 1000.0
				|| distance20[29] < 1000.0 || distance20[30] < 1000.0
				|| distance20[31] < 1000.0 || distance20[32] < 1000.0
				|| distance20[33] < 1000.0 || distance20[34] < 1000.0
				|| distance20[35] < 1000.0 || distance20[36] < 1000.0
				|| distance20[37] < 1000.0 || distance20[38] < 1000.0
				|| distance20[39] < 1000.0 || distance20[40] < 1000.0
				|| distance20[41] < 1000.0 || distance20[42] < 1000.0
				|| distance20[43] < 1000.0 || distance20[44] < 1000.0
				|| distance20[45] < 1000.0 || distance20[46] < 1000.0
				|| distance20[47] < 1000.0 || distance20[48] < 1000.0
				|| distance20[49] < 1000.0 || distance20[50] < 1000.0
				|| distance20[51] < 1000.0 || distance20[52] < 1000.0
				|| distance20[53] < 1000.0 || distance20[54] < 1000.0
				|| distance20[55] < 1000.0 || distance20[56] < 1000.0
				|| distance20[57] < 1000.0 || distance20[58] < 1000.0
				|| distance20[59] < 1000.0 || distance20[60] < 1000.0
				|| distance20[61] < 1000.0 || distance20[62] < 1000.0
				|| distance20[63] < 1000.0 || distance20[64] < 1000.0
				|| distance20[65] < 1000.0 || distance20[66] < 1000.0
				|| distance20[67] < 1000.0 || distance20[68] < 1000.0
				|| distance20[69] < 1000.0 || distance20[70] < 1000.0
				|| distance20[71] < 1000.0 || distance20[72] < 1000.0
				|| distance30[0] < 1000.0 || distance30[1] < 1000.0
				|| distance30[2] < 1000.0 || distance30[3] < 1000.0
				|| distance30[4] < 1000.0 || distance30[5] < 1000.0
				|| distance30[6] < 1000.0 || distance30[7] < 1000.0
				|| distance30[8] < 1000.0 || distance30[9] < 1000.0
				|| distance30[10] < 1000.0 || distance30[11] < 1000.0
				|| distance30[12] < 1000.0 || distance30[13] < 1000.0
				|| distance30[14] < 1000.0 || distance30[15] < 1000.0
				|| distance30[16] < 1000.0 || distance30[17] < 1000.0
				|| distance30[18] < 1000.0 || distance30[19] < 1000.0
				|| distance30[20] < 1000.0 || distance30[21] < 1000.0
				|| distance30[22] < 1000.0 || distance30[23] < 1000.0
				|| distance30[24] < 1000.0 || distance30[25] < 1000.0
				|| distance30[26] < 1000.0 || distance30[27] < 1000.0
				|| distance30[28] < 1000.0 || distance30[29] < 1000.0
				|| distance30[30] < 1000.0 || distance30[31] < 1000.0
				|| distance30[32] < 1000.0 || distance30[33] < 1000.0
				|| distance30[34] < 1000.0 || distance30[35] < 1000.0
				|| distance30[36] < 1000.0 || distance30[37] < 1000.0
				|| distance30[38] < 1000.0 || distance30[39] < 1000.0
				|| distance30[40] < 1000.0 || distance40[0] < 1000.0
				|| distance40[1] < 1000.0 || distance40[2] < 1000.0
				|| distance40[3] < 1000.0 || distance40[4] < 1000.0
				|| distance50[0] < 1000.0 || distance50[1] < 1000.0
				|| distance50[2] < 1000.0 || distance50[3] < 1000.0
				|| distance50[4] < 1000.0 || distance50[5] < 1000.0
				|| distance50[6] < 1000.0 || distance60[0] < 1000.0
				|| distance60[1] < 1000.0 || distance60[2] < 1000.0
				|| distance60[3] < 1000.0 || distance60[4] < 1000.0
				|| distance60[5] < 1000.0 || distance65[0] < 1000.0
				|| distance70[0] < 1000.0 || distance70[1] < 1000.0
				|| distance70[2] < 1000.0 || distance70[3] < 1000.0
				|| distance70[4] < 1000.0 || distance70[5] < 1000.0
				|| distance70[6] < 1000.0 || distance70[7] < 1000.0
				|| distance70[8] < 1000.0 || distance70[9] < 1000.0
				|| distance70[10] < 1000.0 || distance70[11] < 1000.0
				|| distance70[12] < 1000.0 || distance70[13] < 1000.0
				|| distance70[14] < 1000.0 || distance70[15] < 1000.0
				|| distance70[16] < 1000.0 || distance70[17] < 1000.0
				|| distance70[18] < 1000.0 || distance70[19] < 1000.0
				|| distance70[20] < 1000.0 || distance70[21] < 1000.0
				|| distance70[22] < 1000.0 || distance70[23] < 1000.0
				|| distance70[24] < 1000.0 || distance70[25] < 1000.0
				|| distance70[26] < 1000.0 || distance70[27] < 1000.0
				|| distance70[28] < 1000.0 || distance70[29] < 1000.0
				|| distance70[30] < 1000.0 || distance70[31] < 1000.0
				|| distance70[32] < 1000.0 || distance70[33] < 1000.0
				|| distance70[34] < 1000.0 || distance70[35] < 1000.0
				|| distance70[36] < 1000.0 || distance70[37] < 1000.0
				|| distance70[38] < 1000.0 || distance70[39] < 1000.0
				|| distance70[40] < 1000.0 || distance70[41] < 1000.0
				|| distance70[42] < 1000.0 || distance70[43] < 1000.0
				|| distance70[44] < 1000.0 || distance80[0] < 1000.0
				|| distance80[1] < 1000.0 || distance80[2] < 1000.0
				|| distance80[3] < 1000.0 || distance80[4] < 1000.0
				|| distance80[5] < 1000.0 || distance80[6] < 1000.0
				|| distance80[7] < 1000.0 || distance90[0] < 1000.0
				|| distance90[1] < 1000.0 || distance90[2] < 1000.0
				|| distance90[3] < 1000.0 || distance90[4] < 1000.0
				|| distance90[5] < 1000.0 || distance90[6] < 1000.0
				|| distance90[7] < 1000.0 || distance90[8] < 1000.0
				|| distance90[9] < 1000.0 || distance90[10] < 1000.0
				|| distance90[11] < 1000.0 || distance90[12] < 1000.0
				|| distance90[13] < 1000.0 || distance90[14] < 1000.0
				|| distance90[15] < 1000.0 || distance90[16] < 1000.0
				|| distance90[17] < 1000.0 || distance90[18] < 1000.0
				|| distance100[0] < 1000.0 || distance100[1] < 1000.0
				|| distance100[2] < 1000.0 || distance100[3] < 1000.0
				|| distance100[4] < 1000.0 || distance100[5] < 1000.0
				|| distance100[6] < 1000.0 || distance100[7] < 1000.0
				|| distance110[0] < 1000.0 || distance110[1] < 1000.0
				|| distance110[2] < 1000.0 || distance110[3] < 1000.0
				|| distance110[4] < 1000.0 || distance110[5] < 1000.0
				|| distance110[6] < 1000.0 || distance110[7] < 1000.0
				|| distance110[8] < 1000.0 || distance110[9] < 1000.0
				|| distance120[0] < 1000.0 || distance120[1] < 1000.0
				|| distance120[2] < 1000.0 || distance120[3] < 1000.0
				|| distance120[4] < 1000.0 || distance120[5] < 1000.0
				|| distance120[6] < 1000.0 || distance120[7] < 1000.0
				|| distance120[8] < 1000.0 || distance120[9] < 1000.0
				|| distance120[10] < 1000.0 || distance120[11] < 1000.0
				|| distance120[12] < 1000.0 || distance120[13] < 1000.0
				|| distance120[14] < 1000.0 || distance120[15] < 1000.0
				|| distance120[16] < 1000.0 || distance120[17] < 1000.0
				|| distance120[18] < 1000.0 || distance120[19] < 1000.0
				|| distance120[20] < 1000.0 || distance120[21] < 1000.0
				|| distance120[22] < 1000.0 || distance120[23] < 1000.0
				|| distance130[0] < 1000.0 || distance130[1] < 1000.0
				|| distance130[2] < 1000.0 || distance130[3] < 1000.0
				|| distance130[4] < 1000.0 || distance130[5] < 1000.0
				|| distance130[6] < 1000.0 || distance130[7] < 1000.0
				|| distance130[8] < 1000.0 || distance130[9] < 1000.0
				|| distance130[10] < 1000.0 || distance130[11] < 1000.0
				|| distance130[12] < 1000.0 || distance130[13] < 1000.0
				|| distance130[14] < 1000.0 || distance140[0] < 1000.0
				|| distance140[1] < 1000.0 || distance140[2] < 1000.0
				|| distance140[3] < 1000.0 || distance140[4] < 1000.0
				|| distance140[5] < 1000.0 || distance140[6] < 1000.0
				|| distance140[7] < 1000.0 || distance140[8] < 1000.0
				|| distance150[0] < 1000.0 || distance160[0] < 1000.0
				|| distance160[1] < 1000.0 || distance160[2] < 1000.0
				|| distance170[0] < 1000.0 || distance170[1] < 1000.0
				|| distance170[2] < 1000.0 || distance170[3] < 1000.0
				|| distance170[4] < 1000.0 || distance170[5] < 1000.0
				|| distance180[0] < 1000.0 || distance190[0] < 1000.0) {
			if (isInArea == false && Frequency <= 999) {
				Intent intent = new Intent("android.broadcast.LOCATION");
				sendBroadcast(intent);
				isInArea = true;
				Frequency++;
				saveFrequency("Frequency", Frequency);

			}
		} else {
			isInArea = false;
		}
} catch (ArrayIndexOutOfBoundsException e) {

}
	}

	/* 儲存廣播次數 */
	private int getFrequency(String key) {

		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences("Frequency", 0);
		return settings.getInt(key, 0);
	}

	private void saveFrequency(String key, int value) {
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences("Frequency", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);

		// Apply the edits!
		editor.apply();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
