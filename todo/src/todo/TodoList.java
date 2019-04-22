package todo;

public class TodoList implements ITodoList {
	//Version ID for serialization
	private static final long serialVersionUID = 1;
	//Name
	private String name = "";
	//Heads and Tails
	private TodoListItem headDesc = null;
	private TodoListItem tailDesc = null;
	private TodoListItem headPrio = null;
	private TodoListItem tailPrio = null;
	private TodoListItem headDue = null;
	private TodoListItem tailDue = null;
	private TodoListItem headStat = null;
	private TodoListItem tailStat = null;
	//TODO
	public Iterable<ITodoListItem> getList() {
		return new TodoListIterable(headPrio);
	}
	@Override
	public Iterable<ITodoListItem> getList(ITodoListFilter filter) {
		return new TodoListIterable(filter.getSortBy(),filter.getSortDirection(),filter.getStatusFilter(),headPrio);
	}
	//Naming functions
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//Lookup Functions
	public TodoListItem findByDesc(String toFind) {
		TodoListItem temp = headDesc;
		while(temp != null) {
			if(toFind.compareTo(temp.getDescription()) == 0) {
				return temp;
			} else if(toFind.compareTo(temp.getDescription()) < 0) {
				return null;
			}
			temp = temp.getNextDesc();
		}
		return null;
	}
	public boolean hasDesc(String toFind) {
		return findByDesc(toFind) != null;
	}
	public TodoListItem findByPrio(int toFind) {
		TodoListItem temp = headPrio;
		while(temp != null) {
			if(toFind == temp.getPriority()) {
				return temp;
			} else if(toFind < temp.getPriority()) {
				return null;
			}
			temp = temp.getNextPrio();
		}
		return null;
	}
	public TodoListItem traversalHead(int order) {
		switch(order) {
		case 1:
			return headPrio;
		case -1:
			return tailPrio;
		case 2:
			return headDesc;
		case -2:
			return tailDesc;
		case 3:
			return headDue;
		case -3:
			return tailDue;
		case 4:
			return headStat;
		case -4:
			return tailStat;
		}
		return null;
	}
	public String toString() {
		String returnable = "List:";
		TodoListItem temp = headPrio;
		while(temp != null) {
			returnable += "\n"+temp.toString();
			temp = temp.getNextPrio();
		}
		returnable += "\nEnd List.";
		return returnable;
	}
	//Insertion Functions
	public void addItem(ITodoListItem toInsert) {
		toInsert = (TodoListItem)toInsert;
		//return addItem(toInsert);
		addItem(toInsert);
	}
	public boolean addItem(TodoListItem toInsert) {
		if(!insertDesc(toInsert)) {
			return false;
		}
		this.insertPrio(toInsert);
		this.insertDue(toInsert);
		this.insertStat(toInsert);
		return true;
	}
	public boolean insertDesc(TodoListItem toInsert) {
		if(headDesc == null) {
			headDesc = toInsert;
			tailDesc = toInsert;
			toInsert.setNextDesc(null);
			toInsert.setPrevDesc(null);
			return true;
		}
		if(toInsert.compareToDesc(headDesc) < 0) {
			toInsert.setNextDesc(headDesc);
			headDesc.setPrevDesc(toInsert);
			toInsert.setPrevDesc(null);
			headDesc = toInsert;
			return true;
		}
		TodoListItem temp = headDesc;
		while(temp.hasNextDesc()) {
			if(toInsert.compareToDesc(temp.getNextDesc()) < 0) {
				toInsert.setNextDesc(temp.getNextDesc());
				temp.getNextDesc().setPrevDesc(toInsert);
				toInsert.setPrevDesc(temp);
				temp.setNextDesc(toInsert);
				return true;
			}
			temp = temp.getNextDesc();
		}
		toInsert.setPrevDesc(temp);
		temp.setNextDesc(toInsert);
		toInsert.setNextDesc(null);
		tailDesc = toInsert;
		return true;
	}
	private void insertPrio(TodoListItem toInsert) {
		if(headPrio == null) {
			headPrio = toInsert;
			tailPrio = toInsert;
			toInsert.setNextPrio(null);
			toInsert.setPrevPrio(null);
			toInsert.rectifyPriority();
			return;
		}
		if(toInsert.getPriority() <= 0) {
			tailPrio.setNextPrio(toInsert);
			toInsert.setPrevPrio(tailPrio);
			toInsert.setNextPrio(null);
			tailPrio = toInsert;
			toInsert.rectifyPriority();
			return;
		}
		if(toInsert.compareToPrio(headPrio) <= 0) {
			toInsert.setNextPrio(headPrio);
			headPrio.setPrevPrio(toInsert);
			toInsert.setPrevPrio(null);
			toInsert.getNextPrio().rectifyPriority();
			headPrio = toInsert;
			return;
		}
		TodoListItem temp = headPrio;
		while(temp.hasNextPrio()) {
			if(toInsert.compareToPrio(temp.getNextPrio()) <= 0) {
				toInsert.setNextPrio(temp.getNextPrio());
				temp.getNextPrio().setPrevPrio(toInsert);
				toInsert.setPrevPrio(temp);
				temp.setNextPrio(toInsert);
				toInsert.getNextPrio().rectifyPriority();
				return;
			}
			temp = temp.getNextPrio();
		}
		toInsert.setPrevPrio(temp);
		temp.setNextPrio(toInsert);
		toInsert.setNextPrio(null);
		toInsert.rectifyPriority();
		tailPrio = toInsert;
		return;
	}
	public void insertDue(TodoListItem toInsert) {
		if(headDue == null) {
			headDue = toInsert;
			tailDue = toInsert;
			toInsert.setNextDue(null);
			toInsert.setPrevDue(null);
			return;
		}
		if(toInsert.compareToDue(headDue) < 0) {
			toInsert.setNextDue(headDue);
			headDue.setPrevDue(toInsert);
			toInsert.setPrevDue(null);
			headDue = toInsert;
			return;
		}
		TodoListItem temp = headDue;
		while(temp.hasNextDue()) {
			if(toInsert.compareToDue(temp.getNextDue()) < 0) {
				toInsert.setNextDue(temp.getNextDue());
				temp.getNextDue().setPrevDue(toInsert);
				toInsert.setPrevDue(temp);
				temp.setNextDue(toInsert);
				return;
			}
			temp = temp.getNextDue();
		}
		toInsert.setPrevDue(temp);
		temp.setNextDue(toInsert);
		toInsert.setNextDue(null);
		tailDue = toInsert;
		return;
	}
	public void insertStat(TodoListItem toInsert) {
		if(headStat == null) {
			headStat = toInsert;
			tailStat = toInsert;
			toInsert.setNextStat(null);
			toInsert.setPrevStat(null);
			return;
		}
		if(toInsert.compareToStat(headStat) < 0) {
			toInsert.setNextStat(headStat);
			headStat.setPrevStat(toInsert);
			toInsert.setPrevStat(null);
			headStat = toInsert;
			return;
		}
		TodoListItem temp = headStat;
		while(temp.hasNextStat()) {
			if(toInsert.compareToStat(temp.getNextStat()) < 0) {
				toInsert.setNextStat(temp.getNextStat());
				temp.getNextStat().setPrevStat(toInsert);
				toInsert.setPrevStat(temp);
				temp.setNextStat(toInsert);
				return;
			}
			temp = temp.getNextStat();
		}
		toInsert.setPrevStat(temp);
		temp.setNextStat(toInsert);
		toInsert.setNextStat(null);
		tailStat = toInsert;
		return;
	}
	//Removal Functions
	public void deleteItem(ITodoListItem toRemove) {
		toRemove = (TodoListItem)toRemove;
		deleteItem(toRemove);
	}
	public void deleteItem(TodoListItem toRemove) {
		removeDesc(toRemove);
		removePrio(toRemove);
		removeDue(toRemove);
		removeStat(toRemove);
	}
	public void removeDesc(TodoListItem toRemove) {
		if(toRemove == headDesc) {
			headDesc = toRemove.getNextDesc();
		}
		if(toRemove == tailDesc) {
			tailDesc = toRemove.getPrevDesc();
		}
		if(toRemove.hasPrevDesc()) {
			toRemove.getPrevDesc().setNextDesc(toRemove.getNextDesc());
			toRemove.setPrevDesc(null);
		}
		if(toRemove.hasNextDesc()) {
			toRemove.getNextDesc().setPrevDesc(toRemove.getPrevDesc());
			toRemove.setNextDesc(null);
		}
	}
	private void removePrio(TodoListItem toRemove) {
		if(toRemove == headPrio) {
			headPrio = toRemove.getNextPrio();
		}
		if(toRemove == tailPrio) {
			tailPrio = toRemove.getPrevPrio();
		}
		if(toRemove.hasPrevPrio()) {
			toRemove.getPrevPrio().setNextPrio(toRemove.getNextPrio());
			toRemove.setPrevPrio(null);
		}
		if(toRemove.hasNextPrio()) {
			toRemove.getNextPrio().setPrevPrio(toRemove.getPrevPrio());
			toRemove.getNextPrio().rectifyPriority();
			toRemove.setNextPrio(null);
		}
	}
	public void removeDue(TodoListItem toRemove) {
		if(toRemove == headDue) {
			headDue = toRemove.getNextDue();
		}
		if(toRemove == tailDue) {
			tailDue = toRemove.getPrevDue();
		}
		if(toRemove.hasPrevDue()) {
			toRemove.getPrevDue().setNextDue(toRemove.getNextDue());
			toRemove.setPrevDue(null);
		}
		if(toRemove.hasNextDue()) {
			toRemove.getNextDue().setPrevDue(toRemove.getPrevDue());
			toRemove.setNextDue(null);
		}
	}
	public void removeStat(TodoListItem toRemove) {
		if(toRemove == headStat) {
			headStat = toRemove.getNextStat();
		}
		if(toRemove == tailStat) {
			tailStat = toRemove.getPrevStat();
		}
		if(toRemove.hasPrevStat()) {
			toRemove.getPrevStat().setNextStat(toRemove.getNextStat());
			toRemove.setPrevStat(null);
		}
		if(toRemove.hasNextStat()) {
			toRemove.getNextStat().setPrevStat(toRemove.getPrevStat());
			toRemove.setNextStat(null);
		}
	}
}
