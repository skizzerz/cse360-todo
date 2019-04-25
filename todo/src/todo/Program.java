// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Main entry point
package todo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main entry point to the program
 */
public class Program extends Application {
    private static Program instance;
    private Stage stage;
    private ITodoList list = null;
    private ITodoListFilter filter = null;
    private boolean dirtyFlag = false;
    private String filename = null;

    /**
     * Main entry point to the program
     * @param args Command-line args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("To-Do List");

        // load splash screen
        Parent splashScreen = FXMLLoader.load(Program.class.getResource("SplashScreen.fxml"));
        stage.setScene(new Scene(splashScreen));
        stage.show();
    }

    /**
     * Exits the program
     */
    public static void close() {
        instance.stage.close();
    }

    /**
     * Retrieves a modal window above the main stage. The caller can finish setting up the stage
     * and then call show() or showAndWait() to display it to the end user.
     *
     * @param fxmlName FXML file name to load and display
     * @return Stage containing modal window
     * @throws Exception On error with display manager
     */
    public static Stage getModal(String fxmlName) throws Exception {
        Stage modalStage = new Stage();
        Parent modalScreen = FXMLLoader.load(Program.class.getResource(fxmlName));
        modalStage.setScene(new Scene(modalScreen));
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(instance.stage);

        return modalStage;
    }

    /**
     * Changes to the specified scene.
     *
     * @param fxmlName FXML file name to load and display
     * @throws Exception On error with display manager
     */
    public static void changeScene(String fxmlName) throws Exception {
        Parent screen = FXMLLoader.load(Program.class.getResource(fxmlName));
        instance.stage.setScene(new Scene(screen));
    }

    /**
     * Gets the primary stage for the application
     *
     * @return Primary stage
     */
    public static Stage getStage() {
        return instance.stage;
    }

    /**
     * Retrieves the list managed by the program
     *
     * @return The list that was opened
     */
    public static ITodoList getList() {
        return instance.list;
    }

    /**
     * Sets the list managed by the program
     *
     * @param value The list that is opened
     */
    public static void setList(ITodoList value) {
        instance.list = value;
    }

    /**
     * Gets whether or not the list has unsaved changes
     *
     * @return True if the list has unsaved changes, false otherwise
     */
    public static boolean getDirtyFlag() {
        return instance.dirtyFlag;
    }

    /**
     * Sets whether or not the list has unsaved changes
     *
     * @param value True if the list has unsaved changes, false otherwise
     */
    public static void setDirtyFlag(boolean value) {
        instance.dirtyFlag = value;
    }

    /**
     * Gets the filter for the current list
     *
     * @return List filter
     */
    public static ITodoListFilter getFilter() {
        return instance.filter;
    }

    /**
     * Sets the filter for the current list
     *
     * @param value List filter
     */
    public static void setFilter(ITodoListFilter value) {
        instance.filter = value;
    }

    /**
     * Gets the filename that regular save actions save to.
     *
     * If null, indicates no filename is set. In that case,
     * treat the "Save" action as if it were "Save As" so the user is
     * prompted to choose a filename. Then, call setFilename() with the chosen
     * name so it is remembered in the future.
     *
     * @return Filename or null
     */
    public static String getFilename() {
        return instance.filename;
    }

    /**
     * Sets the filename that regular save actions save to
     *
     * @param value Filename
     */
    public static void setFilename(String value) {
        instance.filename = value;
    }
}
