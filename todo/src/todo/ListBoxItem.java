// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Wrapper around HBox to make it easier to work with items in our list
package todo;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private HBox taskBox;
    private ITodoListItem item;
    private boolean isDraggable = false;


    EventHandler<MouseEvent> onClickDesc = new EventHandler<MouseEvent>() {
    	@Override
    	public void handle(MouseEvent event) {
    		description.setEditable(true);
    	}
    };
    
    EventHandler<KeyEvent> exitDesc = new EventHandler<KeyEvent>() {
    	@Override
    	public void handle(KeyEvent event) {
    		description.setOnKeyReleased(enterPressed -> {
    	  		  if (enterPressed.getCode() == KeyCode.ENTER){
    	  		     description.setEditable(false);
    	  		     //Store Description
    	  		     item.setDescription(description.getText());
    	  		  }
    		});
    	}
    };
    
    public HBox getHBox() {
    	return taskBox;
    }

    
    public ListBoxItem() {
        // init child controls
    	
    	taskBox = new HBox();
    	taskBox.setPrefSize(1050, 39);
    	taskBox.getStyleClass().addAll("bg-white", "border-grey");
    	
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
        description.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickDesc); //Add event filters
        description.addEventFilter(KeyEvent.KEY_RELEASED, exitDesc);
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
        
        //Add elements to HBox
        taskBox.getChildren().add(0, grip);
        taskBox.getChildren().add(0, statusBubble);
        taskBox.getChildren().add(0, description);
        taskBox.getChildren().add(0, dueDate);

    }

    /**
     * Initializes the control with the passed-in data
     *
     * @param listItem Item to populate control with
     * @param activeSort How the list is currently being sorted
     */
    public void init(ITodoListItem listItem, SortBy activeSort) {
        item = listItem;
        DateFormat dateFormatNoYear = new SimpleDateFormat("MM/dd");
        DateFormat dateFormatWithYear = new SimpleDateFormat("MM/dd/yyyy");

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
            dueDate.setText("Due " + item.getDueDate());
        } else {
            dueDate.setText("Due " + item.getDueDate());
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
