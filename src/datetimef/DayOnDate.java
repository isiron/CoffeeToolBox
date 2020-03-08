package datetimef;

import javax.swing.JOptionPane;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Creates a dialogue box that will get the day on a specified date. Such as May 2nd being a Friday
 *
 * @author  Ivan Suarez
 * @since   3/2/2018
 */
public class DayOnDate extends DateStandardDialogue{
    private static DayOnDate instance = null;

    public static DayOnDate getInstance() {
        if(instance == null) {
            instance = new DayOnDate();
        }
        return instance;
    }

    /**
     * calls default constructor of DateStandardDialogue
     *
     * @see DateStandardDialogue
     */
    private DayOnDate(){
        super("Day name on certain date");
    }

    /**
     *  Takes the input and parses it, getting a date and extracting the day of the week from it, sending the number
     *  through a switch case in order to turn the number into a string. E.G. 5 is Friday.
     *
     *  @see Calendar
     */
    @Override
    protected void actionPerformed() {
        super.actionPerformed();
        Calendar cal = Calendar.getInstance();
        String dayString = "";

        dateFormat.setLenient(false);
        cal.setLenient(false);
        lbl2.setText("");

        try {
            cal.setTime(dateFormat.parse(tf.getText()));
            switch (cal.get(Calendar.DAY_OF_WEEK)){
                case 1: dayString = "Sunday";   break;
                case 2: dayString = "Monday";   break;
                case 3: dayString = "Tuesday";  break;
                case 4: dayString = "Wednesday";break;
                case 5: dayString = "Thursday"; break;
                case 6: dayString = "Friday";   break;
                case 7: dayString = "Saturday"; break;
                default:    break;
            }
            lbl2.setText("This day is a " + dayString);
        }
        catch(ParseException e) {
            JOptionPane.showMessageDialog(this, "Bad input! Try again.");
        }
    }
}
