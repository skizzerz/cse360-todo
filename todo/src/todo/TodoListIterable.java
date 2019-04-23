package todo;

import java.util.EnumSet;
import java.util.Iterator;

public class TodoListIterable implements Iterator<ITodoListItem>, Iterable<ITodoListItem>{
	private TodoListItem head;
	private TodoListItem next;
	public TodoListIterable(TodoListItem head){
		this.head = head;
	}
	public boolean hasNext() {
		if(next == null) {
			return false;
		} else {
			return true;
		}
	}
	public TodoListItem next() {
		TodoListItem returnable = next;
		next = next.getNext();
		return returnable;
	}
	public Iterator<ITodoListItem> iterator() {
		next = head;
		return this;
	}
}
