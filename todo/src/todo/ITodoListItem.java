// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Interface for to-do list items
package todo;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an item in a to-do list.
 */
public interface ITodoListItem extends Serializable {
	/**
	 * Retrieves the current status of this item.
	 *
	 * @return Current status
	 */
	public Status getStatus();

	/**
	 * Sets the current status of this item.
	 *
	 * @param value Status to set
	 */
	public void setStatus(Status value);

	/**
	 * Retrieves the description of this item.
	 *
	 * @return Current description
	 */
	public String getDescription();

	/**
	 * Sets the description of this item.
	 *
	 * @param value New description
	 */
	public void setDescription(String value);

	/**
	 * Retrieves the priority of this item, may be null.
	 *
	 * @return Current priority or null if the item is Finished/Cancelled
	 */
	public Integer getPriority();

	/**
	 * Sets the priority of this item.
	 *
	 * @param value New priority or null to clear priority
	 */
	public void setPriority(Integer value);

	/**
	 * Retrieves the due date of this item.
	 *
	 * @return Current due date
	 */
	public Date getDueDate();

	/**
	 * Sets the due date of this item.
	 *
	 * @param value New due date
	 */
	public void setDueDate(Date value);

	/**
	 * Retrieves when this item was started, may be null.
	 *
	 * @return Started date or null if the item was not yet started
	 */
	public Date getStartedDate();

	/**
	 * Sets the started date of this item.
	 *
	 * @param value New started date, or null to clear started date
	 */
	public void setStartedDate(Date value);

	/**
	 * Retrieves when the item was finished, may be null.
	 *
	 * @return Finished date or null if the item is not yet finished
	 */
	public Date getFinishedDate();

	/**
	 * Sets the finished date of the item.
	 *
	 * @param value New finished date, or null to clear
	 */
	public void setFinishedDate(Date value);
}
