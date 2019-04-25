// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Listeners for the main (list view) screen
package todo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Listeners and events for the main screen (list view)
 */
public class MainScreenController implements Initializable {
	
    @FXML // fx:id="sortDueDateAscendingItem"
    private CheckMenuItem sortDueDateAscendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortStatusDescendingItem"
    private CheckMenuItem sortStatusDescendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortDueDateDescendingItem"
    private CheckMenuItem sortDueDateDescendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="showNotStartedItem"
    private CheckMenuItem showNotStartedItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortStatusAscendingItem"
    private CheckMenuItem sortStatusAscendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortPriorityDescendingItem"
    private CheckMenuItem sortPriorityDescendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortDescriptionAscendingItem"
    private CheckMenuItem sortDescriptionAscendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="showCancelledItem"
    private CheckMenuItem showCancelledItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortDescriptionDescendingItem"
    private CheckMenuItem sortDescriptionDescendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="showFinishedItem"
    private CheckMenuItem showFinishedItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortPriorityAscendingItem"
    private CheckMenuItem sortPriorityAscendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="showInProgressItem"
    private CheckMenuItem showInProgressItem; // Value injected by FXMLLoader

    @FXML // fx:id="listTitle"
    private TextField listTitle; // Value injected by FXMLLoader

    @FXML // fx:id="listBox"
    private VBox listBox; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPane"
    private ScrollPane scrollPane; // Value injected by FXMLLoader
    
    @FXML
    private Button newTaskButton;

    @FXML
    void menuNew(ActionEvent event) {

    }

    @FXML
    void menuOpen(ActionEvent event) {
    	if(!Program.getDirtyFlag() || showUnsavedChangesPrompt()) {
    		showOpenDialog();
    	}
    }

    @FXML
    void menuClose(ActionEvent event) {
    	if(!Program.getDirtyFlag() || showUnsavedChangesPrompt()) {
    		try {
				Program.changeScene("SplashScreen.fxml");
			} catch (Exception e) {
				Program.close();
			}
    	}
    }

    @FXML
    void menuSave(ActionEvent event) {
    	saveToFilename();
    }

    @FXML
    void menuSaveAs(ActionEvent event) {
    	showSaveAsDialog();
    }

    @FXML
    void menuPrint(ActionEvent event) {

    }

    @FXML
    void menuExit(ActionEvent event) {
        if (!Program.getDirtyFlag() || showUnsavedChangesPrompt()) {
            Program.close();
        }
    }

    @FXML
    void menuSortPriorityAscending(ActionEvent event) {

    }

    @FXML
    void menuSortPriorityDescending(ActionEvent event) {

    }

    @FXML
    void menuSortStatusAscending(ActionEvent event) {

    }

    @FXML
    void menuSortStatusDescending(ActionEvent event) {

    }

    @FXML
    void menuSortDescriptionAscending(ActionEvent event) {

    }

    @FXML
    void menuSortDescriptionDescending(ActionEvent event) {

    }

    @FXML
    void menuSortDueDateAscending(ActionEvent event) {

    }

    @FXML
    void menuSortDueDateDescending(ActionEvent event) {

    }

    @FXML
    void menuShowNotStarted(ActionEvent event) {

    }

    @FXML
    void menuShowInProgress(ActionEvent event) {

    }

    @FXML
    void menuShowFinished(ActionEvent event) {

    }

    @FXML
    void menuShowCancelled(ActionEvent event) {

    }

    @FXML
    void menuUserGuide(ActionEvent event) {

    }

    @FXML
    void menuAbout(ActionEvent event) {
    	
    }

    @FXML
    void listTitleClick(MouseEvent event) {
    	listTitle.setEditable(true);
    }
    
    @FXML
    void listTitleEnter(KeyEvent event) {
    	listTitle.setOnKeyReleased(enterPressed -> {
  		  if (enterPressed.getCode() == KeyCode.ENTER){
  		     listTitle.setEditable(false);
  		     //Store list title
  		     Program.getList().setName(listTitle.getText());
  		  }
  	});
    }

    //Add new item
    @FXML
    void newTaskAction(ActionEvent event) {
    	removeNodeInVBox("PlaceHolder");
    	
    	TodoListItem newItem = new TodoListItem();
    	
    	Program.getList().addItem(newItem);
    	
    	redrawList();
    }
    
    //Draw an item from the list
    void addItemToVBox(ListBoxItem item) {
    	item.getHBox().getChildren().clear();
    	
    	item.getHBox().getChildren().add(0, item.getDueDate());
    	item.getHBox().getChildren().add(0, item.getPriority());
    	item.getHBox().getChildren().add(0, item.getDescription());
    	item.getHBox().getChildren().add(0, item.getStatusBubble());
    	item.getHBox().getChildren().add(0, item.getGrip());
    	
    	VBox.setVgrow(item.getHBox(), Priority.SOMETIMES);
    	
    	listBox.getChildren().add(0, item.getHBox());
    }
    
    //Removes node with given 'tag' (tag created by using .setUserData("your tag here") )
    //Tag of placeHolder node is "PlaceHolder"
    void removeNodeInVBox(String nodeData) {
    	boolean found = false;
    	Node del = null;
    	
    	for (Node n : listBox.getChildren()) {
    		if (nodeData.equals(n.getUserData())) {
    			del = n;
    			found = true;
    		}
    	}
    	
    	if(found == true) {
    		listBox.getChildren().remove(del);
    	}
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ITodoList list = Program.getList();

        // Fix up list title
        listTitle.setText(list.getName());

        // Check menu items corresponding to our current sort and filter
        ITodoListFilter filter = Program.getFilter();
        setSortMenuItem(filter.getSortBy(), filter.getSortDirection());
        setShowMenuItem(filter.getStatusFilter());

        // add items to the list box
        if (list.stream().count() > 0) {
            redrawList();
        } else {
            // no items in the list; add a placeholder item for the user
            // giving them directions to click the New Task button
            Pane placeholderPane = new Pane();
            placeholderPane.setUserData("PlaceHolder");
            placeholderPane.setPrefSize(listBox.getWidth(), Control.USE_COMPUTED_SIZE);
            placeholderPane.getStyleClass().addAll("bg-white", "border-grey");
            VBox.setVgrow(placeholderPane, Priority.NEVER);

            Label placeholderLabel = new Label();
            placeholderLabel.setText("There are no tasks in this list. Click New Task below to create one!");
            placeholderLabel.setPadding(new Insets(5));
            placeholderLabel.getStyleClass().addAll("list-item", "fg-dark");

            placeholderPane.getChildren().add(placeholderLabel);
            listBox.getChildren().add(0, placeholderPane);
        }
    }

    /**
     * Redraws the list; this should be called whenever we do a filter, sort, add/remove an item, or
     * complete a drag-and-drop operation so that we can show the updated list in the UI.
     */
    private void redrawList() {
    	//Clear List
    	listBox.getChildren().clear();
    	
    	VBox.setVgrow(newTaskButton, Priority.SOMETIMES);
    	listBox.getChildren().add(0,newTaskButton);
    	
    	//Repopulate based on filters
    	for(ITodoListItem n : Program.getList().getList(Program.getFilter())) {
    		
    		//Show not started
    		if(showNotStartedItem.isSelected()) {
    			if(n.getStatus() == Status.NotStarted) {
    				addItemToVBox(n.getListBoxItem());
    			}
    		}
    		
    		//
    		if(showInProgressItem.isSelected()) {
    			if(n.getStatus() == Status.InProgress) {
    				addItemToVBox(n.getListBoxItem());
    			}
    		}
    		
    		if(showFinishedItem.isSelected()) {
    			if(n.getStatus() == Status.Finished) {
    				addItemToVBox(n.getListBoxItem());
    			}
    		}
    		
    		if(showCancelledItem.isSelected()) {
    			if(n.getStatus() != Status.Cancelled) {
    				addItemToVBox(n.getListBoxItem());
    			}
    		}
    	}
    	
    	
    	
    	
    }

    /**
     * Set which sort menu item is shown as checked.
     *
     * @param sortBy Column to show as being sorted
     * @param sortDirection Direction to show as being sorted
     */
    private void setSortMenuItem(SortBy sortBy, SortDirection sortDirection) {
        sortPriorityAscendingItem.setSelected(false);
        sortPriorityDescendingItem.setSelected(false);
        sortStatusAscendingItem.setSelected(false);
        sortStatusDescendingItem.setSelected(false);
        sortDescriptionAscendingItem.setSelected(false);
        sortDescriptionDescendingItem.setSelected(false);
        sortDueDateAscendingItem.setSelected(false);
        sortDueDateDescendingItem.setSelected(false);

        switch (sortBy) {
            case Priority:
                if (sortDirection == SortDirection.Ascending) {
                    sortPriorityAscendingItem.setSelected(true);
                } else {
                    sortPriorityDescendingItem.setSelected(true);
                }
                break;
            case Status:
                if (sortDirection == SortDirection.Ascending) {
                    sortStatusAscendingItem.setSelected(true);
                } else {
                    sortStatusDescendingItem.setSelected(true);
                }
                break;
            case Description:
                if (sortDirection == SortDirection.Ascending) {
                    sortDescriptionAscendingItem.setSelected(true);
                } else {
                    sortDescriptionDescendingItem.setSelected(true);
                }
                break;
            case DueDate:
                if (sortDirection == SortDirection.Ascending) {
                    sortDueDateAscendingItem.setSelected(true);
                } else {
                    sortDueDateDescendingItem.setSelected(true);
                }
                break;
        }
    }

    /**
     * Set which statuses are listed in the Show menu as checked
     *
     * @param statuses Statuses to check
     */
    private void setShowMenuItem(EnumSet<Status> statuses) {
        showNotStartedItem.setSelected(statuses.contains(Status.NotStarted));
        showInProgressItem.setSelected(statuses.contains(Status.InProgress));
        showFinishedItem.setSelected(statuses.contains(Status.Finished));
        showCancelledItem.setSelected(statuses.contains(Status.Cancelled));
    }

    /**
     * Prompts the user that they have unsaved changes, and gives them an opportunity to save.
     *
     * If the user elects to save, that will be handled by this function as well.
     *
     * @return Returns true if the action that caused this prompt to appear should continue.
     */
    private boolean showUnsavedChangesPrompt() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("You have unsaved changes. If you do not save them before exiting, they will be lost.");

        ButtonType saveButton = new ButtonType("Save and exit");
        ButtonType exitButton = new ButtonType("Exit without saving");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(saveButton, exitButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == saveButton) {
                // Save the list
            	saveToFilename();
            } else if (result.get() == exitButton) {
                // Exit without saving
                return true;
            } else if (result.get() == cancelButton) {
                // user is cancelling, don't exit out
                return false;
            }
        }

        // no result present = user clicked x button to cancel out
        return false;
    }
    /**
     * Displays a dialog that allows the user to browse for and open a file
     */
    private void showOpenDialog() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("To-Do Lists", "*.tdl"));

        File file = chooser.showOpenDialog(Program.getStage());
        if (file != null) {
            try {
                Program.setList(FileManager.loadFromFile(file));
                Program.setFilename(file.getAbsolutePath());
                Program.setDirtyFlag(false);
                Program.setFilter(new TodoListFilter());
                Program.changeScene("MainScreen.fxml");
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
    /**
     * Displays a dialog that allows the user to save a file at a location of their choosing
     */
    private boolean showSaveAsDialog() {
    	FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("To-Do Lists", "*.tdl"));

        File file = chooser.showSaveDialog(Program.getStage());
        if (file != null) {
            try {
                FileManager.saveToFile(file,Program.getList());
                Program.setFilename(file.getAbsolutePath());
                Program.setDirtyFlag(false);
                return true;
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    	return false;
    }
    /**
     * Saves the list to the specified file, if such a filename exists.  If it does not, opens the "Save As" prompt.
     * Returns true on a successful save to any path, false otherwise.
     */
    private boolean saveToFilename() {
    	if(Program.getFilename() == null) {
    		return showSaveAsDialog();
    	} else {
	    	try {
	    		File savior = new File(Program.getFilename());
				FileManager.saveToFile(savior,Program.getList());
				Program.setDirtyFlag(false);
				return true;
			} catch (Exception e) {
				return false;
			}
    	}
    }
}
