// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Implementation of a todo list filter
package todo;

import java.util.EnumSet;

/**
 * A filter for a todo list.
 * Keeps track of which types of list items should be shown
 * and the order that they are sorted.
 */
public class TodoListFilter implements ITodoListFilter {
    private SortBy sortBy;
    private SortDirection sortDirection;
    private EnumSet<Status> statusFilter;

    /**
     * Constructs a new todo list filter.
     */
    public TodoListFilter() {
        sortBy = SortBy.Priority;
        sortDirection = SortDirection.Ascending;
        statusFilter = EnumSet.of(Status.NotStarted, Status.InProgress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortBy getSortBy() {
        return sortBy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortBy(SortBy value) {
        sortBy = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortDirection getSortDirection() {
        return sortDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSortDirection(SortDirection value) {
        sortDirection = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnumSet<Status> getStatusFilter() {
        return statusFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatusFilter(EnumSet<Status> value) {
        statusFilter = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(ITodoListItem o1, ITodoListItem o2) {
        // if we're comparing in descending order, we need to flip the final result
        // as it simplifies our internal calculations to only work in ascending order.
        // This multiplier variable handles this;
        int multiplier = (sortDirection == SortDirection.Ascending) ? 1 : -1;
        int result;

        switch (sortBy)
        {
            case Description:
                result = o1.getDescription().compareToIgnoreCase(o2.getDescription());
                break;
            case DueDate:
                if (o1.getDueDate() == null) {
                    if (o2.getDueDate() == null) {
                        result = 0;
                    } else {
                        // non-null elements are always sorted before null ones;
                        // o2 isn't null but o1 is, so o2 should go first
                        result = 1;
                    }
                } else {
                    if (o2.getDueDate() == null) {
                        // o1 isn't null but o2 is, so o1 should go first
                        result = -1;
                    } else {
                        result = o1.getDueDate().compareTo(o2.getDueDate());
                    }
                }
                break;
            case Priority:
                if (o1.getPriority() == null) {
                    if (o2.getPriority() == null) {
                        result = 0;
                    } else {
                        // non-null elements are always sorted before null ones;
                        // o2 isn't null but o1 is, so o2 should go first
                        result = 1;
                    }
                } else {
                    if (o2.getPriority() == null) {
                        // o1 isn't null but o2 is, so o1 should go first
                        result = -1;
                    } else {
                        result = o1.getPriority().compareTo(o2.getPriority());
                    }
                }
                break;
            case Status:
                result = Integer.compare(o1.getStatus().ordinal(), o2.getStatus().ordinal());
                break;
            default:
                throw new IllegalStateException("SortBy is not valid");
        }

        return result * multiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean test(ITodoListItem item) {
        return statusFilter.contains(item.getStatus());
    }
}
