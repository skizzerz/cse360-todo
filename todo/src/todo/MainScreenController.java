//CSE 360 Project - Team 10
//Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
//Controls the main screen
package todo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML // fx:id="sortDueDateAscendingItem"
    private CheckMenuItem sortDueDateAscendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortStatusDescendingItem"
    private CheckMenuItem sortStatusDescendingItem; // Value injected by FXMLLoader

    @FXML // fx:id="sortDueDateDescendingItem3"
    private CheckMenuItem sortDueDateDescendingItem3; // Value injected by FXMLLoader

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
    private Label listTitle; // Value injected by FXMLLoader

    @FXML
    void menuNew(ActionEvent event) {

    }

    @FXML
    void menuOpen(ActionEvent event) {

    }

    @FXML
    void menuClose(ActionEvent event) {

    }

    @FXML
    void menuSave(ActionEvent event) {

    }

    @FXML
    void menuSaveAs(ActionEvent event) {

    }

    @FXML
    void menuPrint(ActionEvent event) {

    }

    @FXML
    void menuExit(ActionEvent event) {

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
    void listTitleClick(ActionEvent event) {

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

    }
}
