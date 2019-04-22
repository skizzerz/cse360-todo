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
	private TodoListItem nextDesc = null;
	private TodoListItem prevDesc = null;
	private TodoListItem nextPrio = null;
	private TodoListItem prevPrio = null;
	private TodoListItem nextDue = null;
	private TodoListItem prevDue = null;
	private TodoListItem nextStat = null;
	private TodoListItem prevStat = null;
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
		if(!this.hasPrevPrio() && !this.hasNextPrio()) {
			this.priority = priority;
		}
	}
	public void rectifyPriority() {
		if(!this.hasPrevPrio()) {
			this.priority = 1;
		} else if(this.priority != this.getPrevPrio().getPriority()+1) {
			this.priority = this.getPrevPrio().getPriority()+1;
		}
		if(this.hasNextPrio()) {
			this.getNextPrio().rectifyPriority();
		}
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
		case Cancelled:
			return "Cancelled";
		case NotStarted:
			return "Not Started";
		case InProgress:
			return "In Progress";
		case Finished:
			return "Finished";
		}
		return "Unknown Status";
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
	public TodoListItem traverse(SortBy order, SortDirection direction) {
		switch(order) {
		case Description:
			if(direction == SortDirection.Ascending) {
				return this.getNextDesc();
			} else {
				return this.getPrevDesc();
			}
		case DueDate:
			if(direction == SortDirection.Ascending) {
				return this.getNextDue();
			} else {
				return this.getPrevDue();
			}
		case Status:
			if(direction == SortDirection.Ascending) {
				return this.getNextStat();
			} else {
				return this.getPrevStat();
			}
		default:
		case Priority:
			if(direction == SortDirection.Ascending) {
				return this.getNextPrio();
			} else {
				return this.getPrevPrio();
			}
		}
	}
	public boolean hasNext(SortBy order, SortDirection direction) {
		switch(order) {
		case Description:
			if(direction == SortDirection.Ascending) {
				return this.hasNextDesc();
			} else {
				return this.hasPrevDesc();
			}
		case DueDate:
			if(direction == SortDirection.Ascending) {
				return this.hasNextDue();
			} else {
				return this.hasPrevDue();
			}
		case Status:
			if(direction == SortDirection.Ascending) {
				return this.hasNextStat();
			} else {
				return this.hasPrevStat();
			}
		default:
		case Priority:
			if(direction == SortDirection.Ascending) {
				return this.hasNextPrio();
			} else {
				return this.hasPrevPrio();
			}
		}
	}
	public boolean hasNextDesc() {
		return nextDesc != null;
	}
	public TodoListItem getNextDesc() {
		return nextDesc;
	}
	public void setNextDesc(TodoListItem nextDesc) {
		this.nextDesc = nextDesc;
	}
	public boolean hasPrevDesc() {
		return prevDesc != null;
	}
	public TodoListItem getPrevDesc() {
		return prevDesc;
	}
	public void setPrevDesc(TodoListItem prevDesc) {
		this.prevDesc = prevDesc;
	}
	public boolean hasNextPrio() {
		return nextPrio != null;
	}
	public TodoListItem getNextPrio() {
		return nextPrio;
	}
	public void setNextPrio(TodoListItem nextPrio) {
		this.nextPrio = nextPrio;
	}
	public boolean hasPrevPrio() {
		return prevPrio != null;
	}
	public TodoListItem getPrevPrio() {
		return prevPrio;
	}
	public void setPrevPrio(TodoListItem prevPrio) {
		this.prevPrio = prevPrio;
	}
	public boolean hasNextDue() {
		return nextDue != null;
	}
	public TodoListItem getNextDue() {
		return nextDue;
	}
	public void setNextDue(TodoListItem nextDue) {
		this.nextDue = nextDue;
	}
	public boolean hasPrevDue() {
		return prevDue != null;
	}
	public TodoListItem getPrevDue() {
		return prevDue;
	}
	public void setPrevDue(TodoListItem prevDue) {
		this.prevDue = prevDue;
	}
	public boolean hasNextStat() {
		return nextStat != null;
	}
	public TodoListItem getNextStat() {
		return nextStat;
	}
	public void setNextStat(TodoListItem nextStat) {
		this.nextStat = nextStat;
	}
	public boolean hasPrevStat() {
		return prevStat!= null;
	}
	public TodoListItem getPrevStat() {
		return prevStat;
	}
	public void setPrevStat(TodoListItem prevStat) {
		this.prevStat = prevStat;
	}
	//Helper Functions
	public static Date currentDate() {
		return new Date();
	}
}
