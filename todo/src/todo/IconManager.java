// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Makes referencing icons elsewhere in code easier
package todo;

import javafx.scene.text.Font;

public class IconManager {
    private static Font splashIconFont;
    private static Font statusIconFont;

    // Icon unicode points
    /**
     * Not started "icon": just blank space
     */
    public final static String NOT_STARTED = " ";

    /**
     * New icon: page with 3 lines
     */
    public final static String NEW = "\uf15c";

    /**
     * Open icon: folder
     */
    public final static String OPEN = "\uf07b";

    /**
     * In Progress icon: arrow pointing right
     */
    public final static String IN_PROGRESS = "\uf061";

    /**
     * Finished icon: check mark
     */
    public final static String FINISHED = "\uf00c";

    /**
     * Cancelled icon: x
     */
    public final static String CANCELLED = "\uf00d";

    /**
     * Grip icon: 2x3 grid of dots
     */
    public final static String GRIP = "\uf58e";

    /**
     * Application icon: check list
     */
    public final static String APP_ICON = "\uf0ae";

    static {
        String fontRef = Program.class.getResource("fa-solid-900.ttf").toExternalForm();
        splashIconFont = Font.loadFont(fontRef, 72);
        statusIconFont = Font.loadFont(fontRef, 24);
    }

    /**
     * Retrieves the icon font sized for the splash screen
     * @return Icon font
     */
    public static Font getSplashIconFont() {
        return splashIconFont;
    }

    /**
     * Retrieves the icon font sized for status bubbles
     * @return Icon font
     */
    public static Font getStatusIconFont() {
        return statusIconFont;
    }
}
