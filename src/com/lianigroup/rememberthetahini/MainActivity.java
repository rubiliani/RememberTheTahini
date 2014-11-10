package com.lianigroup.rememberthetahini;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView list;
	List<TaskItem> itemList;
	Context context = MainActivity.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list  = (ListView)findViewById(R.id.listView);
		itemList = PopulateData();
		list.setAdapter(new TaskItemAdapter(context, itemList));
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
	
	private List<TaskItem> PopulateData()
	{
		List<TaskItem> list = new ArrayList<TaskItem>();
		list.add(new TaskItem("Task 1", true));
		list.add(new TaskItem("Task 2", false));
		list.add(new TaskItem("Task 3", false));
		list.add(new TaskItem("Task 4", true));
		list.add(new TaskItem("Task 5", true));
		list.add(new TaskItem("Task 6", true));
		list.add(new TaskItem("Task 1", true));
		list.add(new TaskItem("Task 2", false));
		list.add(new TaskItem("Task 3", false));
		list.add(new TaskItem("Task 4", true));
		list.add(new TaskItem("Task 5", true));
		list.add(new TaskItem("Task 6", true));
		list.add(new TaskItem("Task 1", true));
		list.add(new TaskItem("Task 2", false));
		list.add(new TaskItem("Task 3", false));
		list.add(new TaskItem("Task 4", true));
		list.add(new TaskItem("Task 5", true));
		list.add(new TaskItem("Task 6", true));
		
		return list;
		
	}
}
