package com.lianigroup.rememberthetahini;

import java.util.List;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class TaskItemAdapter extends BaseAdapter{

	private List<TaskItem> itemList;
	private Context context;
	private LayoutInflater inflater;
	
	public TaskItemAdapter(Context context, List<TaskItem> list) {
		this.itemList = list;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		if (this.itemList != null && itemList.size() > position)
			return this.itemList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHold;
		TaskItem item = (TaskItem)getItem(position);
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.task_row, null);
			viewHold = new ViewHolder();
			convertView.setTag(viewHold);
			
			viewHold.cb = (CheckBox)convertView.findViewById(R.id.checkBox1);
			viewHold.tv = (TextView)convertView.findViewById(R.id.textView1);
			
			viewHold.cb.setOnClickListener( new View.OnClickListener() {  
		          public void onClick(View v) {  
		            CheckBox cb = (CheckBox) v ; 
		            Log.d("position",Integer.toString(position));
		            TaskItem item = (TaskItem) cb.getTag();
		            item.setCompleted(cb.isChecked());
		            
		            DBHelper db = new DBHelper(context);
		            db.updateTask(item);
		            
		    
		            strikeThruText(viewHold,item.getCompleted());
		          }  
		        });  
			
			
			strikeThruText(viewHold,item.getCompleted());
			
			
		}
		else
		{
			viewHold = (ViewHolder)convertView.getTag();
		
			strikeThruText(viewHold,item.getCompleted());
		}
		
		 
		viewHold.tv = (TextView)convertView.findViewById(R.id.textView1);
		viewHold.tv.setText(itemList.get(position).getDescription());
		viewHold.cb = (CheckBox)convertView.findViewById(R.id.checkBox1);
		viewHold.cb.setChecked(itemList.get(position).getCompleted());
		viewHold.cb.setTag(item);
		
		
		
		return convertView;
	}
	
	private void strikeThruText(ViewHolder vh,boolean value)
	{
		if(value)
			vh.tv.setPaintFlags(vh.tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		else
			vh.tv.setPaintFlags(vh.tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	}
	
	public static class ViewHolder{
		TextView tv;
		CheckBox cb;
	}
	
	public boolean removeItem(int position)
	{
		 DBHelper db = new DBHelper(context);
         db.deleteTask((TaskItem)getItem(position));
		itemList.remove(position);
		return true;
	}

}
