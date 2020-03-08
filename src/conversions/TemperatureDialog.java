package conversions;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;

/**
 * Does a conversion from fahrenheit to celsius. Uses singleton pattern.
 *
 * @since 3/1/2018
 * @see StandardDialogue
 */
public class TemperatureDialog extends StandardDialogue {
    private static TemperatureDialog instance = null;

    /**
     * Creates the Temperature dialogue, or gives the existing instance
     *
     * @return TemperatureDialog, a JInternalFrame
     */
    public static TemperatureDialog getInstance() {
        if(instance == null) {
            instance = new TemperatureDialog();
        }
        return instance;
    }

    private TemperatureDialog(){
        super("Temperature conversion",
                "\u00b0" + "F to " + "\u00b0" + "C",
                "\u00b0" + "C to " + "\u00b0" + "F");
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
            //if first button is true then compute for Fahrenheit to Celsius, else assume Cel to Fahr
            if(radioButton1.isSelected()) {
                converted = (float)((input-32)*.5556);
                converted = Float.parseFloat(df.format(converted));
                lbl2.setText(converted + "\u00b0" + "C");
            }
            else {
                converted = (float)(input*1.8)+32;
                converted = Float.parseFloat(df.format(converted));
                lbl2.setText(converted + "\u00b0" + "F");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Bad input! Try again.");
        }
    }
}
