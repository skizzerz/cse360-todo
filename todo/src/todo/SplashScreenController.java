// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Listeners for splash screen
package todo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Listeners and events for the Splash Screen
 */
public class SplashScreenController implements Initializable {

    @FXML // fx:id="newListButton"
    private Button newListButton; // Value injected by FXMLLoader

    @FXML // fx:id="openListButton"
    private Button openListButton; // Value injected by FXMLLoader

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
        newListButton.setFont(IconManager.getSplashIconFont());
        newListButton.setText(IconManager.NEW);

        openListButton.setFont(IconManager.getSplashIconFont());
        openListButton.setText(IconManager.OPEN);
    }

    /**
     * Called when the user clicks on the New List button
     * @param event UI event
     */
    @FXML
    void newList(ActionEvent event) {
        createNewList();
    }

    /**
     * Called when the user clicks on the Open List button
     * @param event UI event
     */
    @FXML
    void openList(ActionEvent event) {
        showOpenDialog();
    }

    /**
     * Called when the user selects the File->New menu option
     * @param event UI event
     */
    @FXML
    void menuNew(ActionEvent event) {
        createNewList();
    }

    /**
     * Called when the user selects the File->Open menu option
     * @param event UI event
     */
    @FXML
    void menuOpen(ActionEvent event) {
        showOpenDialog();
    }

    /**
     * Called when the user selects the File->Exit menu option
     * @param event UI event
     */
    @FXML
    void menuExit(ActionEvent event) {
        Program.close();
    }

    /**
     * Called when the user selects the Help->User Guide menu option
     * @param event UI event
     */
    @FXML
    void menuUserGuide(ActionEvent event) {
    	try {
			java.awt.Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1wi3M6ad1Fy0sIpJA9fJFJLoAceUZ-tly/view?usp=sharing"));
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
		}
    }

    /**
     * Called when the user selects the Help->About menu option
     * @param event UI event
     */
    @FXML
    void menuAbout(ActionEvent event) throws Exception {
        Stage about = Program.getModal("AboutScreen.fxml");
        about.setResizable(false);
        about.setTitle("About To-Do List");
        about.show();
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
                Program.setDirtyFlag(false);
                Program.setFilter(new TodoListFilter());
                Program.changeScene("MainScreen.fxml");
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
}
