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
     * Gets the primary stage for the application
     *
     * @return Primary stage
     */
    public static Stage getStage() {
        return instance.stage;
    }
}
