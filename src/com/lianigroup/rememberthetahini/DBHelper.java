package com.lianigroup.rememberthetahini;

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
	
	private static final int DATABASE_VERSION = 2;

	private static final String DATABASE_NAME = "tasks.db";

	public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE "
				+ TaskItemEntry.TABLE_NAME + " (" + TaskItemEntry.COLUMN_NAME_TASK_ID
				+ " INTEGER PRIMARY KEY," + TaskItemEntry.COLUMN_NAME_DESCRIPTION
				+ " TEXT NOT NULL, " + TaskItemEntry.COLUMN_NAME_COMPLETED
				+ " TINYINT)";
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
	        	int id = cursor.getInt(0);
	        	String desc = cursor.getString(1); 
	        	int comp = cursor.getInt(2);
	        	Boolean complete = false;
	        	if(comp>0)
	        		complete = true;
	        	
	        	
	            TaskItem task = new TaskItem(id,desc,complete);
	          
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
