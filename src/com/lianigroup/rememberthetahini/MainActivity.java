package com.lianigroup.rememberthetahini;

import java.util.List;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements ResultCallback{

	ListView list;
	List<TaskItem> itemList;
	Context context = MainActivity.this;
	TaskItemAdapter adapter;
	
	public final int REQUEST_CODE_NEW_TASK = 1;
	public final int REQUEST_CODE_UPDATE_TASK = 2;
	public final int REQUEST_CODE_REMOVE_TASK = 3;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		if(resultCode == RESULT_OK)
		{
			switch(requestCode)
			{
				case REQUEST_CODE_NEW_TASK:
				{
					TaskItem task = (TaskItem)data.getSerializableExtra("item");
					
					itemList.add(task);
					adapter =  new TaskItemAdapter(context, itemList);
					list.setAdapter(adapter);
			        adapter.notifyDataSetChanged();
				}
				break;
				
				case REQUEST_CODE_UPDATE_TASK:
				{
					
					TaskItem task = (TaskItem)data.getSerializableExtra("item");
					
					if(task.getToDelete())
					{
						for(int i=0;i<itemList.size();i++)
					    {
					    	if(itemList.get(i).getTaskId() == task.getTaskId())
					    	{
					    		itemList.remove(i);
					    	}
					    }
						
					}
					else
					{
					    for(int i=0;i<itemList.size();i++)
					    {
					    	if(itemList.get(i).getTaskId() == task.getTaskId())
					    	{
					    		itemList.set(i, task);
					    	}
					    }
					
					}
					adapter =  new TaskItemAdapter(context, itemList);
					list.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}
				break;
				
			}
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get the list
		list  = (ListView)findViewById(R.id.listView);
		
		//connect to SQLite
		DBHelper db = new DBHelper(this);
		//get all tasks from db
		itemList = db.getAllTasks();
		//fill the list with tasks
		list.setAdapter(new TaskItemAdapter(context, itemList));
		
		//long press action
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

		    @Override
		    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
		    	
		    	
		    	
		    	//get item instance from list
		    	TaskItem item = (TaskItem) ((TaskItemAdapter)parent.getAdapter()).getItem(position);
		    
		    	//start the create activity again, now for editing
		    	Intent i = new Intent(getApplicationContext(),CreateTaskActivity.class);
		    	i.putExtra("item", item);
				startActivityForResult(i, REQUEST_CODE_UPDATE_TASK);
				
		        return false;
		    }});
		
		
		
		list.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            
	        	Toast.makeText(context, "Long press to edit task", Toast.LENGTH_SHORT).show();
	        }
	    });
		/*
		Intent myIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent =
		PendingIntent.getActivity(context, 0, myIntent, 0);
		
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		
		Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.SECOND, 10);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        */
		
        /*
		NotificationManager notificationManager =
		(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

		// Build notification
		Notification noti = new Notification.Builder(context)
		.setContentTitle("Rubi").setContentText("Liani")
		.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pendingIntent)
		.build();
		notificationManager.notify(0, noti);
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void newTaskBtn(View view) {
		
		Intent i = new Intent(this,CreateTaskActivity.class);
		startActivityForResult(i, REQUEST_CODE_NEW_TASK);
	}
	
	public void updateTaskBtn(View view) {
		
		//Intent i = new Intent(this,CreateTaskActivity.class);
		//i.putExtra("item", )
		//startActivityForResult(i, 1);
	}

	@Override
	public void onResult(Result arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Result Main", Toast.LENGTH_LONG).show();
		
	}
	
}
