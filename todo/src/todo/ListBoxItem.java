// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Wrapper around HBox to make it easier to work with items in our list
package todo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
import java.util.Optional;

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
    private ITodoListItem item;
    private MainScreenController controller;

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
    	  			  if(Program.getList().containsDescription(description.getText())) {
    	  				Alert alert = new Alert(AlertType.ERROR,"Description cannot be the same as that of another item.", ButtonType.OK);
    	                alert.showAndWait();
    	  			  } else {
	    	  		     description.setEditable(false);
	    	  		     //Store Description
	    	  		     item.setDescription(description.getText());
	    	  		     Program.setDirtyFlag(true);
    	  			  }
    	  		  }
    		});
    	}
    };
    
    EventHandler<MouseEvent> onClickDueDate = new EventHandler<MouseEvent>() {
    	@Override
    	public void handle(MouseEvent event) {
    		BorderPane root = new BorderPane();
    		Scene scene = new Scene(root, 400, 210);

            DatePicker calendar = new DatePicker(LocalDate.now());
    		DatePickerSkin datePickerSkin = new DatePickerSkin(calendar);
    		Node popupContent = datePickerSkin.getPopupContent();
    		
    		
    	
    		root.setCenter(popupContent);
    		
    		Stage calendarPopUp = new Stage();
    		calendarPopUp.initModality(Modality.APPLICATION_MODAL);
    		calendarPopUp.initOwner(Program.getStage());
    		
    		calendarPopUp.setScene(scene);
    		calendarPopUp.setTitle("Select Due Date");
    		calendarPopUp.show();
    		
    		calendar.valueProperty().addListener((observable, oldValue, newValue) -> {
    			item.setDueDate(newValue);
    			Program.setDirtyFlag(true);
    			changeDueDateText();
    			calendarPopUp.hide();
                controller.redrawList();
    		});
    		
    	}
    };
    
    EventHandler<ActionEvent> onClickStatusBubble = new EventHandler<ActionEvent>() {
    	@Override
    	public void handle(ActionEvent event) {
    	    Status currentStatus = item.getStatus();
    	    String currentStatusStr = null;
    	    switch (currentStatus) {
                case NotStarted:
                    currentStatusStr = "Not Started";
                    break;
                case InProgress:
                    currentStatusStr = "In Progress";
                    break;
                case Finished:
                    currentStatusStr = "Finished";
                    break;
                case Cancelled:
                    currentStatusStr = "Cancelled";
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(currentStatusStr, "Not Started", "In Progress", "Finished", "Cancelled");
    	    dialog.setTitle("Change Status");
    	    dialog.setHeaderText(null);
    	    dialog.setContentText("New Status:");
    	    Optional<String> choice = dialog.showAndWait();
    	    if (choice.isPresent() && !choice.get().equals(currentStatusStr)) {
    	        if (choice.get().equals("Not Started")) {
    	            item.setStatus(Status.NotStarted);
    	            statusBubble.setText(IconManager.NOT_STARTED);
                } else if (choice.get().equals("In Progress")) {
    	            item.setStatus(Status.InProgress);
                    statusBubble.setText(IconManager.IN_PROGRESS);
                } else if (choice.get().equals("Finished")) {
    	            item.setStatus(Status.Finished);
                    statusBubble.setText(IconManager.FINISHED);
                } else if (choice.get().equals("Cancelled")) {
    	            item.setStatus(Status.Cancelled);
                    statusBubble.setText(IconManager.CANCELLED);
                }

    	        Program.setDirtyFlag(true);
    	        controller.redrawList();
            }
    	}
    };

    EventHandler<MouseEvent> onClickPriority = new EventHandler<MouseEvent>() {
    	@Override
    	public void handle(MouseEvent event) {
    		TextInputDialog inputPrio = new TextInputDialog(item.getPriority().toString());
    		inputPrio.setTitle("Change Priority");
    		inputPrio.setHeaderText(null);
    		inputPrio.setContentText("New Priority:");
    		
    		Optional<String> result = inputPrio.showAndWait();
    		
    		result.ifPresent(priority -> {
    			try {
    			    int newPriority = Integer.parseInt(priority);
    			    Program.getList().setPriority(item, newPriority);
    				setPriorityLabel();
    				Program.setDirtyFlag(true);
    				controller.redrawList();
    			} catch (NumberFormatException e) {
    				Alert alert = new Alert(AlertType.ERROR, "Value is not an integer!");
    				alert.show();
    			} catch (IllegalArgumentException e) {
                    Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                    alert.show();
                }
    		});
    	}
    };

    public void setPriorityLabel() {
    	priority.setText("Priority " + item.getPriority());
    }
    
    public ListBoxItem(MainScreenController controller) {
        this.controller = controller;

        // init child controls
        statusBubble = new Button();
        statusBubble.getStyleClass().addAll("fg-dark", "status-icon", "no-chrome");
        statusBubble.setText(IconManager.NOT_STARTED);
        statusBubble.setCursor(Cursor.HAND);
        statusBubble.setPrefWidth(50);
        statusBubble.setTextOverrun(OverrunStyle.CLIP);
        statusBubble.addEventFilter(ActionEvent.ACTION, onClickStatusBubble);
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
        priority.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickPriority);
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
    }
    
    //Updates due date label to what is stored in duedateval
    private void changeDueDateText() {
    	DateTimeFormatter dateFormatNoYear = DateTimeFormatter.ofPattern ("MM/dd");
        DateTimeFormatter dateFormatWithYear = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
    	
    	if (item.getDueDate().getYear() == LocalDate.now().getYear()) {
            dueDate.setText("Due " + item.getDueDate().format(dateFormatNoYear));
        } else {
            dueDate.setText("Due " + item.getDueDate().format(dateFormatWithYear));
        }
    }

    /**
     * Initializes the control with the passed-in data
     *
     * @param listItem Item to populate control with
     * @param activeSort How the list is currently being sorted
     */
    public void init(ITodoListItem listItem, SortBy activeSort) {
        item = listItem;
        
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
        getChildren().addAll(statusBubble, description);

        // Finished and Cancelled tasks don't have priorities
        if (item.getPriority() != null) {
            setPriorityLabel();
            priority.setPrefWidth(150);
            priority.setPadding(new Insets(5));

            getChildren().add(priority);
        }

        changeDueDateText();
        getChildren().add(dueDate);
    }
    
}
