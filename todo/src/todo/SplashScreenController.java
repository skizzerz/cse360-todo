// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Listeners for splash screen
package todo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.InputStream;
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
        newListButton.setText(IconManager.New);

        openListButton.setFont(IconManager.getSplashIconFont());
        openListButton.setText(IconManager.Open);
    }

    /**
     * Called when the user clicks on the New List button
     * @param event UI event
     */
    @FXML
    void newList(ActionEvent event) {

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
     * Displays a dialog that allows the user to browse for and open a file
     */
    private void showOpenDialog() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("To-Do Lists", "*.tdl"));

        File file = chooser.showOpenDialog(Program.getStage());
        if (file != null) {
            // call into FileManager (or whatever we call it) to open and process the file
            // we'll need to show an error message if the file they tried to open is invalid
        }
    }
}
