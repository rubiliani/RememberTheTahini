package com.lianigroup.rememberthetahini;

import java.io.Serializable;

public class TaskItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//our fields in the tasks
	private String description;
	private Boolean completed;
	
	
	public TaskItem(String description, Boolean completed) {
		super();
		this.description = description;
		this.completed = completed;
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
	
	

}
