package conversions;

import javax.swing.JOptionPane;

import java.text.DecimalFormat;

/**
 * Does a conversion from inches to centimeters. Uses singleton pattern
 *
 * @since 3/1/2018
 * @see StandardDialogue
 */
public class HeightDialog extends StandardDialogue {
    private static HeightDialog instance = null;

    /**
     * Creates the height dialogue, or gives the existing instance
     *
     * @return HeightDialogue, a JInternalFrame
     */
    public static HeightDialog getInstance() {
        if(instance == null) {
            instance = new HeightDialog();
        }
        return instance;
    }

    private HeightDialog(){
        super("Height conversion",
                "Inches to Centimeters",
                "Centimeters to Inches");
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
            //if first button is true then compute for Inches to Centimeters, else assume Centimeters to Inches
            if(radioButton1.isSelected()) {
                converted = (float)(input*2.54);
                converted = Float.parseFloat(df.format(converted));
                lbl2.setText(converted + "cm");
            }
            else {
                converted = (float)(input/2.54);
                converted = Float.parseFloat(df.format(converted));
                lbl2.setText(converted + "in");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Bad input! Try again.");
        }
    }
}
