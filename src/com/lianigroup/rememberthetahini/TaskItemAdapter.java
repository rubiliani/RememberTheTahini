package com.lianigroup.rememberthetahini;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHold;
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.task_row, null);
			viewHold = new ViewHolder();
			convertView.setTag(viewHold);
		}
		else
		{
			viewHold = (ViewHolder)convertView.getTag();
		}
		
		viewHold.tv = (TextView)convertView.findViewById(R.id.textView1);
		viewHold.tv.setText(itemList.get(position).getDescription());
		viewHold.cb = (CheckBox)convertView.findViewById(R.id.checkBox1);
		viewHold.cb.setChecked(itemList.get(position).getCompleted());
		
		
		return convertView;
	}
	
	public static class ViewHolder{
		TextView tv;
		CheckBox cb;
	}

}
