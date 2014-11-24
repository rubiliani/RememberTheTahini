package com.lianigroup.rememberthetahini;

import java.io.Serializable;

public class TaskItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//our fields in the tasks
	private long taskId;
	private String description;
	private Boolean completed;
	
	
	public TaskItem(int id,String description, Boolean completed) {
		super();
		setTaskId(id);
		setDescription(description);
		setCompleted(completed);
	}
	
	public TaskItem(String description, Boolean completed) {
		super();
		setDescription(description);
		setCompleted(completed);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	

}
