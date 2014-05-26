package com.parse.tutorials.pushnotifications;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

public class GPSService extends Service implements LocationListener {
	private LocationManager manager;
	private boolean isInArea;
	private double latitude, longitude;
	
	@Override
	public void onCreate() {
		
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1,
				this);
		isInArea = false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		latitude = (double) intent.getFloatExtra("LATITUDE", 22.6297370f);
		longitude = (double) intent.getFloatExtra("LONGITUDE", 120.3278820f);
		Log.d("GPSService", "lat/long: " + latitude + ": " + longitude);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		manager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location current) {
		// TODO Auto-generated method stub
		if (current == null)
			return;
		Location dest = new Location(current);
		dest.setLatitude(latitude);
		dest.setLongitude(longitude);
		float distance = current.distanceTo(dest);
		Log.d("²{¦b¶ZÂ÷", "¶ZÂ÷: " + distance);
		if (distance < 1000.0) {
			if (isInArea == false) {
				Intent intent = new Intent("android.broadcast.LOCATION");
				sendBroadcast(intent);
				isInArea = true;
			}
		} else {
			isInArea = false;
		}

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
