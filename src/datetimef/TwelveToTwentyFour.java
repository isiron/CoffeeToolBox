package datetimef;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* Dialogue box that will display 2 textboxes and a button. In reality the button does nothing other than to have a
 * place the user can click it. Clicking out of a textbox will cause the other textbox to update
*
* @author  Ivan Suarerz
* @since   3/3/2018
*/
public class TwelveToTwentyFour extends JInternalFrame{
    private JTextField tfTwelve, tfTwentyFour;
    private JButton btn;
    private SimpleDateFormat twelveFormat, twentyFourFormat;
    private Date time = new Date();
    private JPanel upperPanel, midPanel, lowerPanel;
    private Calendar cal = Calendar.getInstance();
    private String previous12, previous24;
    private final String TIME_FORMAT_24HR = "HH:mm";
    private final String TIME_FORMAT_12HR = "hh:mm a";

    private static TwelveToTwentyFour instance = null;

    public static TwelveToTwentyFour getInstance() {
        if(instance == null) {
            instance = new TwelveToTwentyFour();
        }
        return instance;
    }

    /**
     * This function will be called when the button is pressed
     *
     * @param isTo24 If true, the textfield for 12hour time will be parsed to the 24hour field. Else 24hour will
     *               be parsed in 12 hour.
     */
    protected void actionPerformed(boolean isTo24) {
        //First parse the text from tfTwelve. Now have a dat object. Format it to get time as a string.
        // Set String to tfTwentyFour
        String timeParsed;

        try {
            if(isTo24){
                timeParsed = twentyFourFormat.format(twelveFormat.parse(tfTwelve.getText()));
                tfTwentyFour.setText(timeParsed);
                previous12 = tfTwelve.getText();
                previous24 = timeParsed;
            }
            else{
                timeParsed = twelveFormat.format(twentyFourFormat.parse(tfTwentyFour.getText()));
                tfTwelve.setText(timeParsed);
                previous12 = tfTwelve.getText();
                previous24 = timeParsed;
            }
        }
        catch(ParseException e) {
            JOptionPane.showMessageDialog(this, "Bad input! Try again.");
        }
    }

    private void addListeners(){

        //add keylisteners to everything
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //when enter is released, take request from any JTextbox, activating their listeners
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    btn.requestFocus();
            }
        };

        addKeyListener(keyListener);
        btn.addKeyListener(keyListener);
        tfTwelve.addKeyListener(keyListener);
        tfTwentyFour.addKeyListener(keyListener);


        // add focus listener. When clicked out of box, perform action performed. The one to be changed will be the
        // opposite of the one clicked out of.
        tfTwelve.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            // if the text does not equal the previous text, it has been changed, try to parse the other TextField
            @Override
            public void focusLost(FocusEvent e) {
                if (!tfTwelve.getText().equals(previous12)) {
                    actionPerformed(true);
                }
            }
        });

        tfTwentyFour.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!tfTwentyFour.getText().equals(previous24)) {
                    actionPerformed(false);
                }
            }
        });
    }

    /**
     * Constructor for a dialogue box, fills the textboxes with current time as well
     *
     * @see SimpleDateFormat
     */
    protected TwelveToTwentyFour() {

        // call constructor of JInternalFrame
        // Arguments: title, resizability, closability,
        // maximizability, and iconifiability
        super("12hr to 24hr", false, true, false, false);

        //Setup the formats for use in parsing later
        twelveFormat = new SimpleDateFormat(TIME_FORMAT_12HR);
        twentyFourFormat = new SimpleDateFormat(TIME_FORMAT_24HR);
        time = cal.getTime();

        //initiate objects, textfields have default value of current time
        tfTwelve = new JTextField(twelveFormat.format(time),5);
        tfTwentyFour = new JTextField(twentyFourFormat.format(time),3);
        previous12 = tfTwelve.getText();
        previous24 = tfTwentyFour.getText();
        btn = new JButton("Convert");
        upperPanel = new JPanel();
        midPanel = new JPanel();
        lowerPanel = new JPanel();

        upperPanel.setLayout(new FlowLayout());
        midPanel.setLayout(new FlowLayout());
        lowerPanel.setLayout(new FlowLayout());

        cal.setLenient(false);
        twelveFormat.setLenient(false);
        twentyFourFormat.setLenient(false);

        upperPanel.add(tfTwelve);

        midPanel.add(tfTwentyFour);

        lowerPanel.add(btn);

        add(upperPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);

        addListeners();

        setBounds(25, 25, 150, 130);
        setLocation(100, 100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
}
