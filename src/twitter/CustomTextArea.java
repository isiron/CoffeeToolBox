package twitter;

import javax.swing.JTextArea;

/**
 * A preconfigured text area
 *
 * @since 3/4/2018.
 */
public class CustomTextArea extends JTextArea{
    /**
     * Creates a text area with a scroll bar of size and width 250.
     *
     * @param defaultText The text that will appear in the text area.
     * @param rows Number of rows for the Text Area to have.
     * @param cols Number of columns for the Text Area to have.
     */
    public CustomTextArea(String defaultText, int rows, int cols){
        super(rows, cols);
        int sizeWidth = 250;
        int sizeHeight = 250;
        setText(defaultText);
        setLineWrap(true);
        setWrapStyleWord(true);

    }
}
