package datetimef;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;

/**
 * Standard dialogue box that has functions built in for date usage. Action performed to be implemented by subclass
 *
 * @author  Ivan Suarerz
 * @since   3/2/2018
 */
public class DateStandardDialogue extends JInternalFrame {

    protected JTextField tf;
    protected JLabel lbl, lbl2;
    protected SimpleDateFormat dateFormat;
    protected final String DATE_FORMAT_STR = "MM/dd/yy";
    private JButton btn;
    private JPanel upperPanel, lowerPanel;


    /**
     * This function will be called when the button is pressed, to be implemented by subclass
     */
    protected void actionPerformed() {
    }

    /**
     * default constructor, not for use
     */
    protected DateStandardDialogue(){}

    /**
     * Constructor for a dialogue box, also sets a date format
     *
     * @param   title   The title for the specific dialog.
     * @see SimpleDateFormat
     */
    protected DateStandardDialogue(String title) {

        // call constructor of JInternalFrame
        // Arguments: title, resizability, closability,
        // maximizability, and iconifiability
        super(title, false, true, false, false);

        dateFormat = new SimpleDateFormat(DATE_FORMAT_STR);
        tf = new JTextField(DATE_FORMAT_STR.toLowerCase(),10);
        btn = new JButton("Determine");
        lbl = new JLabel("Answer: ");
        lbl2 = new JLabel();
        upperPanel = new JPanel();
        lowerPanel = new JPanel();

        upperPanel.setLayout(new FlowLayout());
        upperPanel.setLayout(new FlowLayout());

        upperPanel.add(tf);
        upperPanel.add(btn);

        lowerPanel.add(lbl);
        lowerPanel.add(lbl2);

        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.SOUTH);

        // add button listener
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DateStandardDialogue.this.actionPerformed();
            }
        });

        tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        actionPerformed();
                    }
            }
        });

        //add focus listener. When clicked out of box, if box is empty, it will appear a tip on how to do the formmating
        tf.setForeground(Color.GRAY);
        tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(DATE_FORMAT_STR.toLowerCase())) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) {
                    tf.setForeground(Color.GRAY);
                    tf.setText(DATE_FORMAT_STR.toLowerCase());
                }
            }
        });

        setBounds(25, 25, 250, 100);
        setLocation(100, 100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

}
