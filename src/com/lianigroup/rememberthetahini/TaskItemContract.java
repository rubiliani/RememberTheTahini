package com.lianigroup.rememberthetahini;

import android.provider.BaseColumns;

public final class TaskItemContract {

	public TaskItemContract() {
		// TODO Auto-generated constructor stub
	}
	
	 public static abstract class TaskItemEntry implements BaseColumns {
	        public static final String TABLE_NAME = "Tasks";
	        public static final String COLUMN_NAME_TASK_ID = "taskid";
	        public static final String COLUMN_NAME_DESCRIPTION = "description";
	        public static final String COLUMN_NAME_COMPLETED = "completed";
	       
	    }

}
