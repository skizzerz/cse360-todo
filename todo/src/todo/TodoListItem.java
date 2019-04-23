/*	CSE 360 Project - Team 10
	Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
	Implementation of to-do list items*/
package todo;

import java.util.Date;

public class TodoListItem implements ITodoListItem {
	//Version ID for serialization
	private static final long serialVersionUID = 2;
	//Basic Data
	private int priority = 0;
	private String description = "";
	private Status status = Status.NotStarted;
	private Date dueDate = null;
	private Date startDate = null;
	private Date finishDate = null;
	//List Links
	private TodoListItem next = null;
	//Constructor
	public TodoListItem() {
		this("",0,currentDate(),Status.NotStarted);
	}
	public TodoListItem(String description,int priority,Date dueDate,Status status) {
		this.setDescription(description);
		this.setPriority(priority);
		this.setDueDate(dueDate);
		this.setStatus(status);
	}
	//Data Accessors/Mutators
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getStartedDate() {
		if(status == Status.InProgress || status == Status.Finished) {
			return startDate;
		} else {
			return null;
		}
	}
	public void setStartedDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishedDate() {
		if(status == Status.Finished) {
			return finishDate;
		} else {
			return null;
		}
	}
	public void setFinishedDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Status getStatus() {
		return status;
	}
	public String getStatusText() {
		switch(this.getStatus()) {
		case NotStarted:
			return "Not Started";
		case InProgress:
			return "In Progress";
		case Finished:
			return "Finished";
		default:
		case Cancelled:
			return "Cancelled";
		}
	}
	public void setStatus(Status status) {
		this.status = status;
		if(status == Status.InProgress) {
			startDate = currentDate();
		} else if(status == Status.Finished) {
			if(startDate == null) {
				startDate = currentDate();
			}
			finishDate = currentDate();
		}
	}
	public String toString() {
		return description+", priority: "+priority+", due: "+dueDate+", started: "+startDate+", finished: "+finishDate+", status: "+this.getStatusText();
	}
	//Comparator Functions
	public int compareToDesc(TodoListItem comparedTo) {
		return this.getDescription().compareToIgnoreCase(comparedTo.getDescription());
	}
	public int compareToPrio(TodoListItem comparedTo) {
		return this.getPriority()-comparedTo.getPriority();
	}
	public int compareToDue(TodoListItem comparedTo) {
		int returnable = getDueDate().compareTo(comparedTo.getDueDate());
		if(returnable == 0) {
			return compareToPrio(comparedTo);
		} else {
			return returnable;
		}
	}
	public int compareToStat(TodoListItem comparedTo) {
		int returnable = 0;
		switch(status) {
		case Cancelled:
			if(comparedTo.getStatus() == Status.Cancelled) {
				returnable = 0;
			} else {
				returnable = -1;
			}
			break;
		case NotStarted:
			if(comparedTo.getStatus() == Status.Cancelled) {
				returnable = 1;
			} else if(comparedTo.getStatus() == Status.NotStarted){
				returnable = 0;
			} else {
				returnable = -1;
			}
			break;
		case InProgress:
			if(comparedTo.getStatus() == Status.Finished) {
				returnable = -1;
			} else if(comparedTo.getStatus() == Status.InProgress){
				returnable = 0;
			} else {
				returnable = 1;
			}
			break;
		case Finished:
			if(comparedTo.getStatus() == Status.Finished){
				returnable = 0;
			} else {
				returnable = 1;
			}
			break;
		}
		if(returnable == 0) {
			return compareToPrio(comparedTo);
		} else {
			return returnable;
		}
	}
	//List Traversal Functions
	public boolean hasNext() {
		return next != null;
	}
	public TodoListItem getNext() {
		return next;
	}
	public void setNext(TodoListItem next) {
		this.next = next;
	}
	//Helper Functions
	public static Date currentDate() {
		return new Date();
	}
}
