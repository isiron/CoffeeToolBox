package misctools;

import javax.swing.JOptionPane;

import java.text.DecimalFormat;

/**
 * Create a small box to see what is the area of a circle
 *
 * @since 3/6/2018.
 */
public class AreaOfCircle extends StandardInputBox{
    private static final String TITLE = "Area of Circle";
    private static AreaOfCircle instance = null;

    /**
     * Creates the are of cirlce dialogue, or gives the existing instance
     *
     * @return AreaOfCircle, a JInternalFrame
     */
    public static AreaOfCircle getInstance() {
        if(instance == null) {
            instance = new AreaOfCircle();
        }
        return instance;
    }

    private AreaOfCircle(){
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
            converted = (float)((input*input)*3.14);
            answer.setText(converted + "");

        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Bad input! Try again.");
        }
    }

}
