package com.lianigroup.rememberthetahini;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//our fields in the tasks
	private long taskId;
	private String description;
	private Boolean completed = false;
	private Boolean hasLocation = false;
	private Boolean HasDate = false;
	private MapPoint location;
	//private Date createDate;
	private Date dueDate;
	private Priority priority;
	private Boolean toDelete = false;
	
	

	public TaskItem(int id,String description, Boolean completed) {
		super();
		setTaskId(id);
		setDescription(description);
		setCompleted(completed);
	}
	
	public TaskItem() {
		super();
	}
	
	public TaskItem(String description, Boolean completed) {
		super();
		setDescription(description);
		setCompleted(completed);
	}
	
	public Boolean getToDelete() {
		return toDelete;
	}

	public void setToDelete(Boolean toDelete) {
		this.toDelete = toDelete;
	}
	
	public Boolean getHasLocation() {
		return hasLocation;
	}

	public void setHasLocation(Boolean hasLocation) {
		this.hasLocation = hasLocation;
	}

	public Boolean getHasDate() {
		return HasDate;
	}

	public void setHasDate(Boolean hasDate) {
		HasDate = hasDate;
	}

	
	
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setDueDate(String dueDate) {
		try
		{
			if(dueDate==null || dueDate=="")
			{
				setHasDate(false);
				return;
			}
			Date myDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dueDate);
			setDueDate(myDate);
			setHasDate(true);
		}catch(Exception e){};
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	

	public MapPoint getLocation() {
		return location;
	}

	public void setLocation(MapPoint location) {
		this.location = location;
	}
	
	public void setLocation(Double lat,Double lng) {
		if(lat==null || lng==null || lat==0.0 || lng==0.0)
		{
			setHasLocation(false);
			return;
		}
		this.location = new MapPoint(lat, lng);
		setHasLocation(true);
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
