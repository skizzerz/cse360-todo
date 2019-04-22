package todo;

import java.util.EnumSet;
import java.util.Iterator;

public class TodoListIterable implements Iterator<ITodoListItem>, Iterable<ITodoListItem>{
	private SortBy order;
	private SortDirection direction;
	private EnumSet<Status> filter;
	private TodoListItem head;
	private TodoListItem next;
	public TodoListIterable(TodoListItem head){
		this(SortBy.Priority,SortDirection.Ascending,EnumSet.of(Status.NotStarted,Status.InProgress),head);
	}
	public TodoListIterable(SortBy order, SortDirection direction, EnumSet<Status> filter, TodoListItem head) {
		this.order = order;
		this.direction = direction;
		this.filter = filter;
		this.head = head;
		this.next = head;
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
		next = next.traverse(order,direction);
		while(filter != null && !filter.contains(next.getStatus())) {
			next = next.traverse(order,direction);
		}
		return returnable;
	}
	public Iterator<ITodoListItem> iterator() {
		next = head;
		return this;
	}
}
