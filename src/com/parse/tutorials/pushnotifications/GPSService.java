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
	private double latitude1, longitude1, latitude2, longitude2;

	@Override
	public void onCreate() {

		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1,
				this);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1,
				this);
		isInArea = false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {

			latitude1 = (double) intent.getFloatExtra("LATITUDE1", 22.6297370f);
			longitude1 = (double) intent.getFloatExtra("LONGITUDE1",
					120.3278820f);
			latitude2 = (double) intent.getFloatExtra("LATITUDE2", 22.6297370f);
			longitude2 = (double) intent.getFloatExtra("LONGITUDE2",
					120.3278820f);

		} catch (NullPointerException e) {
			Log.i("GPSService","NullPointException");
		}
		Log.d("GPSService", "lat/long: " + latitude1 + ": " + longitude1);
		Log.d("GPSService", "lat/long: " + latitude2 + ": " + longitude2);
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
		dest.setLatitude(latitude1);
		dest.setLongitude(longitude1);
		dest.setLatitude(latitude2);
		dest.setLongitude(longitude2);
		float distance1 = current.distanceTo(dest);
		float distance2 = current.distanceTo(dest);
		if (distance1 < 1000.0 || distance2 < 1000.0) {
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
