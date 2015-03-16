package com.lianigroup.rememberthetahini;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ReminderNotification extends BroadcastReceiver{

	public ReminderNotification() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onReceive(Context context, Intent intent) {
        // The PendingIntent to launch our activity if the user selects this notification
		TaskItem task = (TaskItem)intent.getSerializableExtra("task");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        // Set the info for the views that show in the notification panel.
       
		 NotificationManager notificationManager =
					(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

					// Build notification
					Notification noti = new Notification.Builder(context)
					.setContentTitle("Task Reminder").setContentText(task.getDescription())
					.setSmallIcon(R.drawable.ic_launcher).setContentIntent(contentIntent)
					.build();
					noti.flags |= Notification.FLAG_AUTO_CANCEL;
					notificationManager.notify(0, noti);
					
		
	}

}
