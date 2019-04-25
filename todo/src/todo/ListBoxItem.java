// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Wrapper around HBox to make it easier to work with items in our list
package todo;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import com.sun.javafx.scene.control.skin.DatePickerSkin;

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
    private TodoListItem item;
    private boolean isDraggable = false;
    private DatePicker calendar;
    
    private int priorityVal = 0;
	private String descriptionVal = "";
	private Status status = Status.NotStarted;
	private LocalDate dueDateVal = null;
	private LocalDate startDate = null;
	private LocalDate finishDate = null;


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
    
    EventHandler<MouseEvent> onClickDueDate = new EventHandler<MouseEvent>() {
    	@Override
    	public void handle(MouseEvent event) {
    		BorderPane root = new BorderPane();
    		Scene scene = new Scene(root, 400, 250);
    		
    		calendar = new DatePicker(LocalDate.now());
    		DatePickerSkin datePickerSkin = new DatePickerSkin(calendar);
    		Node popupContent = datePickerSkin.getPopupContent();
    		
    		
    	
    		root.setCenter(popupContent);
    		
    		Stage calendarPopUp = new Stage();
    		calendarPopUp.initModality(Modality.APPLICATION_MODAL);
    		calendarPopUp.initOwner(Program.getStage());
    		
    		calendarPopUp.setScene(scene);
    		calendarPopUp.show();
    		
    		calendar.valueProperty().addListener((observable, oldValue, newValue) -> {
    			item.setDueDate(newValue);
    			setDueDate(newValue);
    			changeDueDateText();
    			calendarPopUp.hide();
    		});
    		
    	}
    };
    
    
    public HBox getHBox() {
    	return taskBox;
    }
    public Button getStatusBubble() {
    	return statusBubble;
    }
    public TextField getDescription() {
    	return description;
    }
    public Label getDueDate() {
    	return dueDate;
    }
    public Label getGrip() {
    	return grip;
    }
    public Label getPriority() {
    	return priority;
    }
    
    public void setDescription(String desc) {
    	descriptionVal = desc;
    }
    public void setDueDate(LocalDate dueDate) {
    	dueDateVal = dueDate;
    }
    public void setPriority(int prio) {
    	priorityVal = prio;
    }
    public void setStatus(Status stat) {
    	status = stat;
    }
    
    
    
    public ListBoxItem(int prio, String desc, Status stat, LocalDate due) {
    	//Set data values
    	this.setDescription(desc);
    	this.setDueDate(due);
    	this.setPriority(prio);
    	this.setStatus(stat);
    	
    	
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
        description.setPrefWidth(800);
        description.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickDesc); //Add event filters
        description.addEventFilter(KeyEvent.KEY_RELEASED, exitDesc);
        HBox.setHgrow(description, Priority.ALWAYS);

        dueDate = new Label();
        dueDate.getStyleClass().addAll("fg-dark", "list-item");
        dueDate.setCursor(Cursor.HAND);
        dueDate.setPrefWidth(200);
        dueDate.setPadding(new Insets(5));
        dueDate.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickDueDate);
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
        grip.setPadding(new Insets(5));
        HBox.setHgrow(grip, Priority.NEVER);

        // apply base styling
        getStyleClass().addAll("bg-white", "border-grey");
        
        //Add elements to HBox
        //taskBox.getChildren().add(0, grip);
        //taskBox.getChildren().add(0, statusBubble);
        //taskBox.getChildren().add(0, description);
        //taskBox.getChildren().add(0, dueDate);

    }
    
    //Updates due date label to what is stored in duedateval
    private void changeDueDateText() {
    	DateTimeFormatter dateFormatNoYear = DateTimeFormatter.ofPattern ("MM/dd");
        DateTimeFormatter dateFormatWithYear = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
    	
    	if (dueDateVal.getYear() == LocalDate.now().getYear()) {
            dueDate.setText("Due " + dueDateVal.format(dateFormatNoYear));
        } else {
            dueDate.setText("Due " + dueDateVal.format(dateFormatWithYear));
        }
    }

    /**
     * Initializes the control with the passed-in data
     *
     * @param listItem Item to populate control with
     * @param activeSort How the list is currently being sorted
     */
    public void init(TodoListItem item, SortBy activeSort) {
        this.item = item;
        
        switch (status) {
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

        description.setText(descriptionVal);
        
        changeDueDateText();
        
        
        // if sorting by due date, priority field takes former position of due date field.
        // Otherwise, it's either not shown, or shown as just a number
        if (activeSort == SortBy.DueDate) {
            priority.setText("Priority " + priority);
            priority.setPrefWidth(150);
            priority.setPadding(new Insets(5));
        } else {
            priority.setText("");
            priority.setPrefWidth(150);
        }

        switch (activeSort) {
            case Priority:
                isDraggable = true;
                //getChildren().addAll(grip, statusBubble, description, dueDate);
                break;
            case DueDate:
                isDraggable = false;
                //getChildren().addAll(statusBubble, description, priority);
                break;
            case Description:
                isDraggable = false;
                //getChildren().addAll(priority, statusBubble, description, dueDate);
                break;
        }
    }
    
}
