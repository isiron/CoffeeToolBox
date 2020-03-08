package conversions;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;

/**
 * Does a conversion from pounds to kilogram. Uses singleton pattern.
 *
 * @since 3/1/2018
 * @see StandardDialogue
 */
public class WeightDialog extends StandardDialogue {
    private static WeightDialog instance = null;

    /**
     * Creates the weight dialogue, or gives the existing instance
     *
     * @return WeightDialog, a JInternalFrame
     */
    public static WeightDialog getInstance() {
        if(instance == null) {
            instance = new WeightDialog();
        }
        return instance;
    }

    private WeightDialog(){
        super("Weight Conversion",
                "Pounds to Kilograms",
                "Kilograms to Pounds");
    }

    @Override
    protected void ActionPerformed() {
        super.ActionPerformed();
        int input = 0;
        lbl2.setText("");
        DecimalFormat df = new DecimalFormat("0.0");
        df.setMaximumFractionDigits(1);
        Float converted;

        try {
            input = Integer.parseInt(tf.getText());
            //if first button is true then compute for Pounds to Kilograms, else assume Kilograms to Pounds
            if(radioButton1.isSelected()) {
                converted = (float)(input/2.2046226218);
                converted = Float.parseFloat(df.format(converted));
                lbl2.setText(converted + "Kg");
            }
            else {
                converted = (float)(input*2.2046226218);
                converted = Float.parseFloat(df.format(converted));
                lbl2.setText(converted + "Lbs");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Bad input! Try again.");
        }
    }
}
