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
     * New icon: page with 3 lines
     */
    public final static String New = "\uf15c";

    /**
     * Open icon: folder
     */
    public final static String Open = "\uf07b";

    /**
     * In Progress icon: arrow pointing right
     */
    public final static String InProgress = "\uf061";

    /**
     * Finished icon: check mark
     */
    public final static String Finished = "\uf00c";

    /**
     * Cancelled icon: x
     */
    public final static String Cancelled = "\uf00d";

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
