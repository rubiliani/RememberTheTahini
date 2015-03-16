package com.lianigroup.rememberthetahini;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GeofencingReceiverIntentService extends ReceiveGeofenceTransitionBaseIntentService 
{
	private NotificationManager notificationManager;

	@Override
	protected void onEnteredGeofences(String[] strings) 
	{
		Log.d(GeofencingReceiverIntentService.class.getName(), "onEnter");
		CreateNotification("On enter");
		// do something!
	}

	@Override
	protected void onExitedGeofences(String[] strings) 
	{
		Log.d(GeofencingReceiverIntentService.class.getName(), "onExit");
		CreateNotification("On exit");
		// do something!
	}
	
	@Override
	protected void onError(int i) 
	{
		Log.e(GeofencingReceiverIntentService.class.getName(), "Error: " + i);
		CreateNotification("Error!");
	}

	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	private void CreateNotification(String text) 
	{

		Intent intent = new Intent(this, NotificationReciever.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		
		// build notification
		// the addAction re-use the same intent to keep the example short
		
		NotificationManager notificationManager =
				(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

				// Build notification
				Notification noti = new Notification.Builder(this)
				.setContentTitle("Task Reminder").setContentText(text)
				.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
				.build();
				noti.flags |= Notification.FLAG_AUTO_CANCEL;
				notificationManager.notify(0, noti);

	}
}
