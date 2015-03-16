package com.lianigroup.rememberthetahini;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lianigroup.rememberthetahini.TaskItemContract.TaskItemEntry;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 4;

	private static final String DATABASE_NAME = "tasks.db";

	public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE "
				+ TaskItemEntry.TABLE_NAME + " (" 
				+ TaskItemEntry.COLUMN_NAME_TASK_ID+ " INTEGER PRIMARY KEY," 
				+ TaskItemEntry.COLUMN_NAME_DESCRIPTION+ " TEXT NOT NULL, " 
				+ TaskItemEntry.COLUMN_NAME_PRIORITY+ " INTEGER, " 
				+ TaskItemEntry.COLUMN_NAME_DUE_DATE+ " TEXT, " 
				+ TaskItemEntry.COLUMN_NAME_LOCATION_LAT+ " REAL, " 
				+ TaskItemEntry.COLUMN_NAME_LOCATION_LON+ " REAL, "
				+ TaskItemEntry.COLUMN_NAME_COMPLETED+ " TINYINT, "
				+ TaskItemEntry.COLUMN_NAME_DATE_ENABLED+ " TINYINT, "
				+ TaskItemEntry.COLUMN_NAME_LOCATION_ENABLED+ " TINYINT "
				+")";
		db.execSQL(SQL_CREATE_LOCATION_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TaskItemEntry.TABLE_NAME);
		onCreate(db);
		
	}
	
	public long addTask(TaskItem task) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(TaskItemEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
	    if(task.getPriority() == null)
	    	values.put(TaskItemEntry.COLUMN_NAME_PRIORITY, "NORMAL");
	    else
	    	values.put(TaskItemEntry.COLUMN_NAME_PRIORITY, task.getPriority().toString());
	    
	    if(task.getHasDate())
	    {
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    	
	    	values.put(TaskItemEntry.COLUMN_NAME_DUE_DATE, sdf.format(task.getDueDate()));
	    	values.put(TaskItemEntry.COLUMN_NAME_DATE_ENABLED, 1); 
	    }
	    else
	    	values.put(TaskItemEntry.COLUMN_NAME_DATE_ENABLED, 0); 
	    if(task.getHasLocation())
	    {
	    	values.put(TaskItemEntry.COLUMN_NAME_LOCATION_LAT, task.getLocation().getLat());
	    	values.put(TaskItemEntry.COLUMN_NAME_LOCATION_LON, task.getLocation().getLng());
	    	values.put(TaskItemEntry.COLUMN_NAME_LOCATION_ENABLED, 1); 
	    }
	    else
	    	 values.put(TaskItemEntry.COLUMN_NAME_LOCATION_ENABLED, 0); 
	    
	    
	    values.put(TaskItemEntry.COLUMN_NAME_COMPLETED, 0); 
	 
	    // Inserting Row
	    long res = db.insert(TaskItemEntry.TABLE_NAME, null, values);
	
	    db.close(); // Closing database connection
	    
	    return res;
	}
	
	
	public List<TaskItem> getAllTasks() {
	    List<TaskItem> taskList = new ArrayList<TaskItem>();
	    
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TaskItemEntry.TABLE_NAME;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	int id = cursor.getInt(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_TASK_ID));
	        	String desc = cursor.getString(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_DESCRIPTION));
	        	String dueDate = cursor.getString(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_DUE_DATE));
	        	Double lat = cursor.getDouble(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_LOCATION_LAT));
	        	Double lon = cursor.getDouble(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_LOCATION_LON));
	        	int comp = cursor.getInt(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_COMPLETED));
	        	int locEnable = cursor.getInt(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_LOCATION_ENABLED));
	        	int dateEnable = cursor.getInt(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_DATE_ENABLED));
	        	String prior = cursor.getString(cursor.getColumnIndex(TaskItemEntry.COLUMN_NAME_PRIORITY));
	        	
	        	Boolean complete = false;
	        	if(comp>0)
	        		complete = true;
	        	
	        	
	            TaskItem task = new TaskItem(id,desc,complete);
	            task.setDueDate(dueDate);
	            task.setLocation(lat,lon);
	            task.setPriority(Priority.valueOf(prior));
	          
	            taskList.add(task);
	        } while (cursor.moveToNext());
	    }
	 
	    // return list
	    return taskList;
	}
	
	public int updateTask(TaskItem task) {
		int boolVal=0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        if(task.getCompleted())
        	boolVal = 1;
        else
        	boolVal=0;
        
        values.put(TaskItemEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
	    if(task.getPriority() == null)
	    	values.put(TaskItemEntry.COLUMN_NAME_PRIORITY, "NORMAL");
	    else
	    	values.put(TaskItemEntry.COLUMN_NAME_PRIORITY, task.getPriority().toString());
	    
	    if(task.getHasDate())
	    {
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    	
	    	values.put(TaskItemEntry.COLUMN_NAME_DUE_DATE, sdf.format(task.getDueDate()));
	    	values.put(TaskItemEntry.COLUMN_NAME_DATE_ENABLED, 1); 
	    }
	    else
	    	values.put(TaskItemEntry.COLUMN_NAME_DATE_ENABLED, 0); 
	    if(task.getHasLocation())
	    {
	    	values.put(TaskItemEntry.COLUMN_NAME_LOCATION_LAT, task.getLocation().getLat());
	    	values.put(TaskItemEntry.COLUMN_NAME_LOCATION_LON, task.getLocation().getLng());
	    	values.put(TaskItemEntry.COLUMN_NAME_LOCATION_ENABLED, 1); 
	    }
	    else
	    	 values.put(TaskItemEntry.COLUMN_NAME_LOCATION_ENABLED, 0); 
	    
	    values.put(TaskItemEntry.COLUMN_NAME_COMPLETED, boolVal); 
 
        // updating row
        return db.update(TaskItemEntry.TABLE_NAME, values, TaskItemEntry.COLUMN_NAME_TASK_ID + " = ?",
                new String[] { String.valueOf(task.getTaskId()) });
    }
	
	public void deleteTask(TaskItem task) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TaskItemEntry.TABLE_NAME, TaskItemEntry.COLUMN_NAME_TASK_ID + " = ?",
	            new String[] { String.valueOf(task.getTaskId()) });
	    db.close();
	}

}
