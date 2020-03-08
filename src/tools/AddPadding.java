package tools;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * Tool to add padding to a JComponent item
 *
 * @since 3/4/2018.
 */
public class AddPadding {
    public static void pad(JComponent item){
        Border border = item.getBorder();
        Border margin = new EmptyBorder(10,10,10,10);
        item.setBorder(new CompoundBorder(border, margin));
    }
    public static void pad(JComponent item, int padLeft, int padRight, int padUp, int padDown){
        Border border = item.getBorder();
        Border margin = new EmptyBorder(padUp,padLeft,padDown,padRight);
        item.setBorder(new CompoundBorder(border, margin));
    }

}
