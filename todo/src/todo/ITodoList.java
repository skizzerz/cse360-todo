// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Interface for to-do lists
package todo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Represents the public interface of a to-do list.
 *
 * Methods for operating on and retrieving information about the list are exposed.
 */
public interface ITodoList extends Serializable {
	/**
	 * Retreives the name of the list.
	 *
	 * @return List name
	 */
	public String getName();

	/**
	 * Sets the name of the list.
	 * @param name New name
	 */
	public void setName(String name);

	/**
	 * Retrieves all items in the todo list, in no particular order.
	 *
	 * @return All items in the list
	 */
	public Iterable<ITodoListItem> getList();

	/**
	 * Retrieves the todo list, sorted and filtered according to the passed-in filter.
	 *
	 * @param filter A filter to apply to the list to restrict and sort output
	 * @return An iterable containing the sorted and filtered list
	 */
	public default Iterable<ITodoListItem> getList(ITodoListFilter filter) {
		return Arrays.asList(stream().filter(filter).sorted(filter).toArray(ITodoListItem[]::new));
	}

	/**
	 * Returns a sequential stream representing this list's items.
	 *
	 * @return A sequential stream over the items in the list
	 */
	public default Stream<ITodoListItem> stream() {
		return StreamSupport.stream(getList().spliterator(), false);
	}

	/**
	 * Adds the given item to the to-do list.
	 *
	 * @param item Item to add
	 */
	public void addItem(ITodoListItem item);

	/**
	 * Deletes the item from the to-do list.
	 *
	 * @param item Item to delete
	 */
	public void deleteItem(ITodoListItem item);
}
