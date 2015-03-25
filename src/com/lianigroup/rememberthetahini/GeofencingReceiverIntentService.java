package com.lianigroup.rememberthetahini;
import static com.lianigroup.rememberthetahini.Constants.TAG;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GeofencingReceiverIntentService extends
		ReceiveGeofenceTransitionBaseIntentService {
	private NotificationManager notificationManager;

	@Override
	protected void onEnteredGeofences(String[] strings) {
		Log.d(GeofencingReceiverIntentService.class.getName(), "onEnter");
		DBHelper db = new DBHelper(getApplicationContext());
		
		CreateNotification(db.getTasksById(strings[0]));
	
		// do something!
	}

	@Override
	protected void onExitedGeofences(String[] strings) {
		Log.d(GeofencingReceiverIntentService.class.getName(), "onExit");
		//CreateNotification("On exit");
		// do something!
	}

	@Override
	protected void onError(int i) {
		Log.e(GeofencingReceiverIntentService.class.getName(), "Error: " + i);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	private void CreateNotification(TaskItem task) {
		Intent intent = new Intent(this, NotificationReceiver.class);
		//TaskItem task = (TaskItem)intent.getSerializableExtra("task");
		//Log.d(TAG, task.getDescription());
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Notification n = new Notification.Builder(this)
				.setContentTitle("Geofence Reminder").setContentText(task.getDescription())
				.setSmallIcon(R.drawable.tasker_launcher).setContentIntent(pIntent)
				.setAutoCancel(true).build();
		
		n.defaults |= Notification.DEFAULT_SOUND;
		n.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, n);

	}
}
