// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Interface for to-do list filters
package todo;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.function.Predicate;

/**
 * Represents a filter that restricts what items are shown in a to-do list.
 * Additionally, it contains information about how the list should be sorted.
 */
public interface ITodoListFilter extends Comparator<ITodoListItem>, Predicate<ITodoListItem> {
	/**
	 * Retreives the item in the list that is being sorted.
	 *
	 * @return Sort item
	 * @see #getSortDirection()
	 */
	SortBy getSortBy();

	/**
	 * Sets the item in the list that is being sorted.
	 *
	 * @param value New SortBy value
	 */
	void setSortBy(SortBy value);

	/**
	 * Retreives the direction that the list should be sorted in.
	 *
	 * @return Sort direction
	 * @see #getSortBy()
	 */
	SortDirection getSortDirection();

	/**
	 * Sets the sort direction.
	 *
	 * @param value New SortDirection value
	 */
	void setSortDirection(SortDirection value);

	/**
	 * Retrieves which statuses are meant to be shown in the list.
	 *
	 * Statuses not in the filter are not shown.
	 *
	 * @return The set of statuses to show
	 */
	EnumSet<Status> getStatusFilter();

	/**
	 * Sets which statuses are meant to be shown in the list.
	 *
	 * @param value The set of statuses to show
	 */
	void setStatusFilter(EnumSet<Status> value);
}
