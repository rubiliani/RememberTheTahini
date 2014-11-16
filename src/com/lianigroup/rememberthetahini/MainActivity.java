package com.lianigroup.rememberthetahini;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView list;
	List<TaskItem> itemList;
	Context context = MainActivity.this;
	
	public final int REQUEST_CODE_TASK = 1;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == REQUEST_CODE_TASK)
		{
			if(resultCode == RESULT_OK)
			{
				TaskItem task = (TaskItem)data.getSerializableExtra("item");
			
				itemList.add(task);
				TaskItemAdapter adapter =  new TaskItemAdapter(context, itemList);
				list.setAdapter(adapter);
		        adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list  = (ListView)findViewById(R.id.listView);
		itemList = PopulateData();
		list.setAdapter(new TaskItemAdapter(context, itemList));
		
		
		list.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            
	        	//Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
	        }
	    });
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
		startActivityForResult(i, 1);
	}
	
	private List<TaskItem> PopulateData()
	{
		List<TaskItem> list = new ArrayList<TaskItem>();
		return list;
		
	}
}
