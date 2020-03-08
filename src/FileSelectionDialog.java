package image;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * A base for file selection. Create of child this and use workToDo() to select what happens once the file is selected
 *
 * @since 3/1/2018
 */
public class FileSelectionDialog extends JInternalFrame {
    protected JLabel lbl, lbl2;
    private JTextField tf;
    protected JButton fileBtn, readBtn;
    private JFileChooser fc;
    protected String fileName;
    private Task task;
    protected JProgressBar progressBar;
    protected JFrame frame; // to properly center JDialogFrame

    class Task extends SwingWorker<Void, String> {
        /*
        * Main task. Executed in background thread.
        */
        @Override
        public Void doInBackground() {
            workToDo();
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            // Messages received from the doInBackground() (when invoking the publish() method)
            //System.out.println("in process (called by doInBackground), setting label to: " + String.valueOf(chunks.get(chunks.size()-1)));
            //lbl2.setText(String.valueOf(chunks.get(chunks.size()-1)));
            //System.out.println("in process (called by doInBackground): numOfTimeProcessGotCalled = " + numOfTimeProcessGotCalled);
        }

        /*
        * Executed in event dispatch thread
        */
        @Override
        public void done() {
            workDone();
        }
    }

    protected Void workToDo()
    {
        return null;
    }
    protected void workDone()
    {

    }

    private void chooseFile() {
        lbl2.setText("");
        fileName = "";
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            tf.setText(file.getAbsolutePath());
            fileName = file.getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(this, "Open command cancelled by user.");
        }
    }

    protected FileSelectionDialog(JFrame frame, String title) {
        super(title, false, true, false, false);
        this.frame = frame;
        // init
        tf = new JTextField(50);
        tf.setEditable(false);
        fileBtn = new JButton("...");
        readBtn = new JButton("Convert");
        lbl = new JLabel("Ready...");
        lbl2 = new JLabel();
        fc = new JFileChooser();
        progressBar = new JProgressBar(0, 100);
        //Call setStringPainted now so that the progress bar height
        //stays the same whether or not the string is shown.
        progressBar.setStringPainted(false);
        fileName = "";

        fileBtn.setPreferredSize(new Dimension(20, 20));
        readBtn.setPreferredSize(new Dimension(80, 20));

        JPanel upperPanel = new JPanel();
        JPanel midPanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        upperPanel.setLayout(new FlowLayout());
        midPanel.setLayout(new FlowLayout());
        lowerPanel.setLayout(new FlowLayout());

        //filter for only image files
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes()));
        fc.setAcceptAllFileFilterUsed(false);

        upperPanel.add(tf);
        upperPanel.add(fileBtn);
        upperPanel.add(readBtn);

        midPanel.add(progressBar);

        lowerPanel.add(lbl);
        lowerPanel.add(lbl2);

        add(upperPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);

        // add button listener
        fileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });

        // add button listener
        readBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readBtn.setEnabled(false);
                task = new Task();
                //task.addPropertyChangeListener(this);
                task.execute();
            }
        });

        pack();
        setBounds(25, 25, 700, 120);
        setLocation(50, 50);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

}
