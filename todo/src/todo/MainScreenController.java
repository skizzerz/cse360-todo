// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Listeners for the main (list view) screen
package todo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    	if(!Program.getDirtyFlag() || showUnsavedChangesPrompt()) {
    		createNewList();
    	}
    }

    @FXML
    void menuOpen(ActionEvent event) {
    	if(!Program.getDirtyFlag() || showUnsavedChangesPrompt()) {
    		showOpenDialog();
    	}
    }

    @FXML
    void menuClose(ActionEvent event) throws Exception{
		Program.changeScene("SplashScreen.fxml");
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
    void menuPrint(ActionEvent event){
    	PrintReport.saveReport(Program.getList());
    }

    @FXML
    void menuExit(ActionEvent event) {
        if (!Program.getDirtyFlag() || showUnsavedChangesPrompt()) {
            Program.close();
        }
    }

    @FXML
    void menuSortPriorityAscending(ActionEvent event) {
        Program.getFilter().setSortBy(SortBy.Priority);
        Program.getFilter().setSortDirection(SortDirection.Ascending);
    	setSortMenuItem(SortBy.Priority, SortDirection.Ascending);
    	redrawList();
    }

    @FXML
    void menuSortPriorityDescending(ActionEvent event) {
        Program.getFilter().setSortBy(SortBy.Priority);
        Program.getFilter().setSortDirection(SortDirection.Descending);
    	setSortMenuItem(SortBy.Priority, SortDirection.Descending);
    	redrawList();
    }

    @FXML
    void menuSortStatusAscending(ActionEvent event) {
        Program.getFilter().setSortBy(SortBy.Status);
        Program.getFilter().setSortDirection(SortDirection.Ascending);
    	setSortMenuItem(SortBy.Status, SortDirection.Ascending);
    	redrawList();
    }

    @FXML
    void menuSortStatusDescending(ActionEvent event) {
        Program.getFilter().setSortBy(SortBy.Status);
        Program.getFilter().setSortDirection(SortDirection.Descending);
    	setSortMenuItem(SortBy.Status, SortDirection.Descending);
    	redrawList();
    }

    @FXML
    void menuSortDescriptionAscending(ActionEvent event) {
        Program.getFilter().setSortBy(SortBy.Description);
        Program.getFilter().setSortDirection(SortDirection.Ascending);
    	setSortMenuItem(SortBy.Description, SortDirection.Ascending);
    	redrawList();
    }

    @FXML
    void menuSortDescriptionDescending(ActionEvent event) {
        Program.getFilter().setSortBy(SortBy.Description);
        Program.getFilter().setSortDirection(SortDirection.Descending);
    	setSortMenuItem(SortBy.Description, SortDirection.Descending);
    	redrawList();
    }

    @FXML
    void menuSortDueDateAscending(ActionEvent event) {
    	Program.getFilter().setSortBy(SortBy.DueDate);
        Program.getFilter().setSortDirection(SortDirection.Ascending);
    	setSortMenuItem(SortBy.DueDate, SortDirection.Ascending);
    	redrawList();
    }

    @FXML
    void menuSortDueDateDescending(ActionEvent event) {
        Program.getFilter().setSortBy(SortBy.DueDate);
        Program.getFilter().setSortDirection(SortDirection.Descending);
    	setSortMenuItem(SortBy.DueDate, SortDirection.Descending);
    	redrawList();
    }

    @FXML
    void menuShowNotStarted(ActionEvent event) {
        // if checked, unselect it. Otherwise select it
        if (Program.getFilter().getStatusFilter().contains(Status.NotStarted)) {
            Program.getFilter().getStatusFilter().remove(Status.NotStarted);
        } else {
            Program.getFilter().getStatusFilter().add(Status.NotStarted);
        }

    	redrawList();
    }

    @FXML
    void menuShowInProgress(ActionEvent event) {
        if (Program.getFilter().getStatusFilter().contains(Status.InProgress)) {
            Program.getFilter().getStatusFilter().remove(Status.InProgress);
        } else {
            Program.getFilter().getStatusFilter().add(Status.InProgress);
        }

    	redrawList();
    }

    @FXML
    void menuShowFinished(ActionEvent event) {
        if (Program.getFilter().getStatusFilter().contains(Status.Finished)) {
            Program.getFilter().getStatusFilter().remove(Status.Finished);
        } else {
            Program.getFilter().getStatusFilter().add(Status.Finished);
        }

    	redrawList();
    }

    @FXML
    void menuShowCancelled(ActionEvent event) {
        if (Program.getFilter().getStatusFilter().contains(Status.Cancelled)) {
            Program.getFilter().getStatusFilter().remove(Status.Cancelled);
        } else {
            Program.getFilter().getStatusFilter().add(Status.Cancelled);
        }

    	redrawList();
    }

    @FXML
    void menuUserGuide(ActionEvent event) throws Exception {
		java.awt.Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1wi3M6ad1Fy0sIpJA9fJFJLoAceUZ-tly/view?usp=sharing"));
    }

    @FXML
    void menuAbout(ActionEvent event) throws Exception {
    	Stage about = Program.getModal("AboutScreen.fxml");
        about.setResizable(false);
        about.setTitle("About To-Do List");
        about.show();
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
  		     //Set the dirty bit for this change
  		     Program.setDirtyFlag(true);
  		  }
  	});
    }

    //Add new item
    @FXML
    void newTaskAction(ActionEvent event) {
    	removeNodeInVBox("PlaceHolder");
    	
    	TodoListItem newItem = new TodoListItem();
    	
    	TextInputDialog inputDesc = new TextInputDialog("");
    	inputDesc.setTitle("New Task");
    	inputDesc.setHeaderText(null);
    	inputDesc.setContentText("Enter Description:");
    	
    	Optional<String> result = inputDesc.showAndWait();
    	result.ifPresent(description -> {
    		newItem.setDescription(description);
    	});
    	
    	Program.getList().addItem(newItem);
    	
    	redrawList();
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
    public void redrawList() {
    	//Clear List
    	listBox.getChildren().clear();

    	//Repopulate based on filters
    	for (ITodoListItem item : Program.getFilteredList()) {
    		ListBoxItem hbox = new ListBoxItem(this);
    		hbox.init(item, Program.getFilter().getSortBy());
            listBox.getChildren().add(hbox);
    	}

    	listBox.getChildren().add(newTaskButton);
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
    /**
     * Sends the user to the main screen with a new, blank list
     */
    private void createNewList() {
        TodoList list = new TodoList();
        list.setName("(Click to change list name)");

        Program.setList(list);
        Program.setDirtyFlag(true);
        Program.setFilter(new TodoListFilter());

        try {
            Program.changeScene("MainScreen.fxml");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
