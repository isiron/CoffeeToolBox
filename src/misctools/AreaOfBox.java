package misctools;

import javax.swing.JOptionPane;

import java.text.DecimalFormat;

/**
 * Create a small box to see what is the area of a box
 *
 * @since 3/6/2018.
 */
public class AreaOfBox extends StandardInputBox{
    private static final String TITLE = "Area of Box";
    private static AreaOfBox instance = null;

    /**
     * Creates the are of box dialogue, or gives the existing instance
     *
     * @return AreaOfBox, a JInternalFrame
     */
    public static AreaOfBox getInstance() {
        if(instance == null) {
            instance = new AreaOfBox();
        }
        return instance;
    }

    private AreaOfBox(){
        super(TITLE);
    }

    @Override
    protected void ActionPerformed() {
        super.ActionPerformed();
        int input = 0;
        DecimalFormat df = new DecimalFormat("0.0");
        df.setMaximumFractionDigits(1);
        Float converted;

        try {
            input = Integer.parseInt(textField.getText());
            converted = (float)(input*input);
            answer.setText(converted + "");

        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Bad input! Try again.");
        }
    }

}