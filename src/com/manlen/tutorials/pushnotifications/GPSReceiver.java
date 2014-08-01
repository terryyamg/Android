package com.manlen.tutorials.pushnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class GPSReceiver extends BroadcastReceiver {
	static int id = 70000;
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		//Notification
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.ic_launcher,
				"您已經接近特約商店", System.currentTimeMillis());
		Intent newintent = new Intent(context, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				newintent, 0);
		notification.setLatestEventInfo(context, "您已經進入曼聯通優惠商店區", null, contentIntent);

		notificationManager.notify(id++, notification);
		
		//手機震動
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(500); // 半秒
	}
}
