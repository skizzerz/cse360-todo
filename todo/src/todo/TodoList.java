package todo;

public class TodoList implements ITodoList {
	//Version ID for serialization
	private static final long serialVersionUID = 1;
	//Name
	private String name = "";
	//Heads and Tails
	private TodoListItem head = null;
	//TODO
	public Iterable<ITodoListItem> getList() {
		return new TodoListIterable(head);
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
		TodoListItem temp = head;
		while(temp != null) {
			if(toFind.compareTo(temp.getDescription()) == 0) {
				return temp;
			} else if(toFind.compareTo(temp.getDescription()) < 0) {
				return null;
			}
			temp = temp.getNext();
		}
		return null;
	}
	public boolean hasDesc(String toFind) {
		return findByDesc(toFind) != null;
	}
	public String toString() {
		String returnable = "List called \""+name+"\":";
		TodoListItem temp = head;
		while(temp != null) {
			returnable += "\n"+temp.toString();
			temp = temp.getNext();
		}
		returnable += "\nEnd List.";
		return returnable;
	}
	//Insertion Functions
	public void addItem(TodoListItem toInsert) {
		//toInsert = (TodoListItem)toInsert;
		//return addItem(toInsert);
		addTodoItem(toInsert);
	}
	public boolean addTodoItem(TodoListItem toInsert) {
		if(!insert(toInsert)) {
			return false;
		}
		return true;
	}
	public boolean insert(TodoListItem toInsert) {
		if(head == null) {
			head = toInsert;
			toInsert.setNext(null);
			return true;
		}
		if(toInsert.compareToDesc(head) < 0) {
			toInsert.setNext(head);
			head = toInsert;
			return true;
		}
		TodoListItem temp = head;
		while(temp.hasNext()) {
			if(toInsert.compareToDesc(temp.getNext()) < 0) {
				toInsert.setNext(temp.getNext());
				temp.setNext(toInsert);
				return true;
			}
			temp = temp.getNext();
		}
		temp.setNext(toInsert);
		toInsert.setNext(null);
		return true;
	}

	public void deleteItem(TodoListItem toRemove) {
		remove(toRemove);
	}
	public void remove(TodoListItem toRemove) {
		if(toRemove == null) {
			return;
		} else if(toRemove == head) {
			head = toRemove.getNext();
			toRemove.setNext(null);
		} else {
			TodoListItem temp = head;
			while(temp.hasNext()) {
				if(temp.getNext() == toRemove) {
					temp.setNext(temp.getNext().getNext());
					toRemove.setNext(null);
					return;
				}
				temp = temp.getNext();
			}
		}
	}
}
