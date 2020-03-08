package conversions;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import tools.AddPadding;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StandardDialogue extends JInternalFrame{

    protected JTextField tf;
    protected JRadioButton radioButton1, radioButton2;
    protected JLabel lbl, lbl2;
    private JButton btn;
    private JPanel upperPanel, midPanel,lowerPanel;
    private ButtonGroup group;

    //empty method, for implementing by other classes
    protected void ActionPerformed() {
    }

    //standard constructor, not for use
    protected StandardDialogue(){}

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

        tf.addKeyListener(keyListener);
        radioButton1.addKeyListener(keyListener);
        radioButton2.addKeyListener(keyListener);
    }

    /**
     * Constructor for a dialogue box
     * @param   title   The title for the specific dialog
     * @param   radioButton1Text    The text for the top radio button
     * @param   radioButton2Text    The text for the bottom radio button
     */
    protected StandardDialogue(String title, String radioButton1Text, String radioButton2Text) {

        // call constructor of JInternalFrame
        // Arguments: title, resizability, closability,
        // maximizability, and iconifiability
        super(title, false, true, false, false);

        tf = new JTextField(10);
        btn = new JButton("Convert");
        lbl = new JLabel("Answer: ");
        lbl2 = new JLabel();
        radioButton1 = new JRadioButton(radioButton1Text);
        radioButton2 = new JRadioButton(radioButton2Text);
        upperPanel = new JPanel(); //border
        midPanel = new JPanel(); //flow
        lowerPanel = new JPanel(); //flow
        group = new ButtonGroup();

        upperPanel.setLayout(new BorderLayout());

        radioButton1.setSelected(true);
        radioButton1.setActionCommand(radioButton1Text);
        radioButton2.setActionCommand(radioButton2Text);
        group.add(radioButton1);
        group.add(radioButton2);

        AddPadding.pad(radioButton1, 15, 0,0,0);
        AddPadding.pad(radioButton2, 15, 0,0,0);

        //adds textbox and input to mid panel
        midPanel.add(tf);
        midPanel.add(btn);

        //adds midPanel to top, then 2 radio buttons on top of each other underneath the textbox and button
        upperPanel.add(midPanel, BorderLayout.NORTH);
        upperPanel.add(radioButton1, BorderLayout.CENTER);
        upperPanel.add(radioButton2, BorderLayout.SOUTH);

        //adds labels for answer to the panel
        lowerPanel.add(lbl);
        lowerPanel.add(lbl2);

        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.SOUTH);

        addListeners();

        setBounds(25, 25, 250, 140);
        setLocation(100, 100);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }
}
