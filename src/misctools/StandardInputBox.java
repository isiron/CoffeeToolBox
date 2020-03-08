package misctools;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by My on 3/6/2018.
 */
public class StandardInputBox extends JInternalFrame {

    protected JTextField textField;
    protected JLabel display, answer;
    private JButton btn;
    private JPanel upperPanel, lowerPanel;

    //empty method, for implementing by other classes
    protected void ActionPerformed() {
    }

    //standard constructor, not for use
    protected StandardInputBox(){}

    protected void addListeners(){
        // add button listener
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActionPerformed();
            }
        });

        //add key listener to radio buttons and textfield
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    ActionPerformed();
            }
        };

        textField.addKeyListener(keyListener);
    }

    /**
     * Constructor for a dialogue box
     * @param   title   The title for the specific dialog
     */
    protected StandardInputBox(String title) {

        // call constructor of JInternalFrame
        // Arguments: title, resizability, closability,
        // maximizability, and iconifiability
        super(title, false, true, false, false);

        textField = new JTextField(10);
        btn = new JButton("Convert");
        display = new JLabel("Answer: ");
        answer = new JLabel();
        upperPanel = new JPanel();
        lowerPanel = new JPanel();

        upperPanel.add(textField);
        lowerPanel.add(display);
        lowerPanel.add(answer);

        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.SOUTH);

        addListeners();

        setBounds(25, 25, 150, 100);
        setLocation(100, 100);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }
}