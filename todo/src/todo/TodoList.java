package todo;

import java.util.ArrayList;

public class TodoList implements ITodoList {
	//Version ID for serialization
	private static final long serialVersionUID = 3;
	//Name
	private String name = "";
	//Heads and Tails
	private ArrayList<ITodoListItem> list = new ArrayList<>();

	public Iterable<ITodoListItem> getList() {
		return list;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addItem(ITodoListItem toInsert) {
        toInsert.setPriority(getMaxPriority() + 1);
		list.add(toInsert);
	}

	public void deleteItem(ITodoListItem toRemove) {
	    if (!list.contains(toRemove)) {
	        throw new IllegalArgumentException("Item not in list");
        }

        list.remove(toRemove);

	    if (toRemove.getPriority() != null) {
	        // shift everything else up to fill in the slot
            stream().filter(o -> o.getPriority() != null && o.getPriority() > toRemove.getPriority()).forEach(o -> o.setPriority(o.getPriority() - 1));
        }
	}

	private int getMaxPriority() {
	    return (int)stream().filter(o -> o.getStatus() == Status.NotStarted || o.getStatus() == Status.InProgress).count();
    }

	public void setPriority(ITodoListItem item, Integer newPriority) {
        // ensure that item is actually in the list
        if (!list.contains(item)) {
            throw new IllegalArgumentException("Item not in list");
        }

        if (newPriority != null && (newPriority < 1 || newPriority > getMaxPriority())) {
            throw new IllegalArgumentException("Invalid priority, must be between 1 and " + getMaxPriority());
        }
        Integer curPriority = item.getPriority();
        if ((curPriority == null && newPriority == null) || (curPriority != null && curPriority.equals(newPriority))) {
            // nothing to do here
            return;
        }

        // this is terribly inefficient but I also don't care. Lists are going to be smallish
        if (curPriority != null) {
            // if we have a current priority, shift everything below it up by 1 to fill the now-empty slot
            stream().filter(o -> o.getPriority() != null && o.getPriority() > curPriority).forEach(o -> o.setPriority(o.getPriority() - 1));
        }

        if (newPriority != null) {
            // push everything currently at this priority down 1 to make room for us
            stream().filter(o -> o.getPriority() != null && o.getPriority() >= newPriority).forEach(o -> o.setPriority(o.getPriority() + 1));
        }

        item.setPriority(newPriority);
    }

    public void setMaxPriorityIfNull(ITodoListItem item) {
        // ensure that item is actually in the list
        if (!list.contains(item)) {
            throw new IllegalArgumentException("Item not in list");
        }

        if (item.getStatus() != Status.NotStarted && item.getStatus() != Status.InProgress) {
            throw new IllegalArgumentException("Item has bad status for setting priority");
        }

        if (item.getPriority() != null) {
            return;
        }

        item.setPriority(getMaxPriority());
    }
}
