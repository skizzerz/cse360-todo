// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Wrapper around HBox to make it easier to work with items in our list
package todo;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * GUI widget for ITodoListItem
 */
public class ListBoxItem extends HBox {
    private Button statusBubble;
    private TextField description;
    private Label dueDate;
    private Label priority;
    private Label grip;
    private ITodoListItem item;
    private boolean isDraggable = false;

    public ListBoxItem() {
        // init child controls
        statusBubble = new Button();
        statusBubble.getStyleClass().addAll("fg-dark", "status-icon", "no-chrome", "round");
        statusBubble.setText(IconManager.NOT_STARTED);
        statusBubble.setCursor(Cursor.HAND);
        statusBubble.setPrefWidth(50);
        HBox.setHgrow(statusBubble, Priority.NEVER);

        description = new TextField();
        description.setEditable(false);
        description.getStyleClass().addAll("fg-dark", "list-item", "no-chrome");
        description.setCursor(Cursor.TEXT);
        HBox.setHgrow(description, Priority.ALWAYS);

        dueDate = new Label();
        dueDate.getStyleClass().addAll("fg-dark", "list-item");
        dueDate.setCursor(Cursor.HAND);
        dueDate.setPrefWidth(200);
        HBox.setHgrow(dueDate, Priority.NEVER);

        priority = new Label();
        priority.getStyleClass().addAll("fg-dark", "list-item");
        priority.setCursor(Cursor.HAND);
        priority.setPrefWidth(200); // width changes based on sort by selection; see init() below
        HBox.setHgrow(dueDate, Priority.NEVER);

        grip = new Label();
        grip.getStyleClass().addAll("fg-dark", "status-icon");
        grip.setText(IconManager.GRIP);
        grip.setCursor(Cursor.MOVE);
        grip.setPrefWidth(50);
        HBox.setHgrow(grip, Priority.NEVER);

        // apply base styling
        getStyleClass().addAll("bg-white", "border-grey");
    }

    /**
     * Initializes the control with the passed-in data
     *
     * @param listItem Item to populate control with
     * @param activeSort How the list is currently being sorted
     */
    public void init(ITodoListItem listItem, SortBy activeSort) {
        item = listItem;
        DateFormat dateFormatNoYear = new SimpleDateFormat("M/d");
        DateFormat dateFormatWithYear = new SimpleDateFormat("M/d/yy");

        switch (item.getStatus()) {
            case NotStarted:
                statusBubble.setText(IconManager.NOT_STARTED);
                break;
            case InProgress:
                statusBubble.setText(IconManager.IN_PROGRESS);
                break;
            case Finished:
                statusBubble.setText(IconManager.FINISHED);
                break;
            case Cancelled:
                statusBubble.setText(IconManager.CANCELLED);
                break;
        }

        description.setText(item.getDescription());
        if (item.getDueDate().getYear() == LocalDate.now().getYear()) {
            dueDate.setText("Due " + dateFormatNoYear.format(item.getDueDate()));
        } else {
            dueDate.setText("Due " + dateFormatWithYear.format(item.getDueDate()));
        }

        // if sorting by due date, priority field takes former position of due date field.
        // Otherwise, it's either not shown, or shown as just a number
        if (activeSort == SortBy.DueDate) {
            priority.setText("Priority " + item.getPriority().toString());
            priority.setPrefWidth(200);
        } else {
            priority.setText(item.getPriority().toString());
            priority.setPrefWidth(50);
        }

        switch (activeSort) {
            case Priority:
                isDraggable = true;
                getChildren().addAll(grip, statusBubble, description, dueDate);
                break;
            case DueDate:
                isDraggable = false;
                getChildren().addAll(statusBubble, description, priority);
                break;
            case Description:
                isDraggable = false;
                getChildren().addAll(priority, statusBubble, description, dueDate);
                break;
        }
    }
}
