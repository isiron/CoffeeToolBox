import javax.swing.SwingUtilities;

/**
 * This program is a toolbox of various useful tools. Ranging from manipulating files to doing some math
 * to posting on twitter.
 *
 * @author Ivan Suarez
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIApp();
            }
        });
    }
}
