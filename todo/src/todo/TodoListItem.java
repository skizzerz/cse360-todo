/*	CSE 360 Project - Team 10
	Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
	Implementation of to-do list items*/
package todo;

import java.time.LocalDate;

public class TodoListItem implements ITodoListItem {
	//Version ID for serialization
	private static final long serialVersionUID = 2;
	//Basic Data
	private int priority = 0;
	private String description = "";
	private Status status = Status.NotStarted;
	private LocalDate dueDate = null;
	private LocalDate startDate = null;
	private LocalDate finishDate = null;
	//List Links
	private TodoListItem next = null;
	//Constructor
	public TodoListItem() {
		this("",0, LocalDate.now(), Status.NotStarted);
	}
	public TodoListItem(String description, int priority, LocalDate dueDate, Status status) {
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
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public LocalDate getStartedDate() {
		if(status == Status.InProgress || status == Status.Finished) {
			return startDate;
		} else {
			return null;
		}
	}
	public void setStartedDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getFinishedDate() {
		if(status == Status.Finished) {
			return finishDate;
		} else {
			return null;
		}
	}
	public void setFinishedDate(LocalDate finishDate) {
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
			startDate = LocalDate.now();
		} else if(status == Status.Finished) {
			if(startDate == null) {
				startDate = LocalDate.now();
			}
			finishDate = LocalDate.now();
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
}
