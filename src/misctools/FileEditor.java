package misctools;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import tools.AddPadding;
import tools.ScrollableTextView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Creates a simple file editor
 *
 * @since 3/6/2018
 */
public class FileEditor extends JInternalFrame {
    private static FileEditor instance = null;

    private static final String TITLE = "Text Editor";

    private JTextField fileTextField;
    private JLabel statusLabel, fileLabel;
    private JButton fileBtn, saveBtn, saveAsBtn, closeFileBtn;
    private JFileChooser fc;
    private JFrame frame; // to properly center JDialogFrame
    private ScrollableTextView textArea;
    //private CustomTextArea textArea;
    BufferedReader reader;
    private String fileName, defaultTextAreaMessage;
    private ReadTask readTask;
    private WriteTask writeTask;
    private boolean hasEdited = false, savingAsNewFile = false;

    public static FileEditor getInstance(JFrame frame) {
        if (instance == null) {
            instance = new FileEditor(frame, TITLE);
        }
        return instance;
    }

    /**
     * Writes contents of the text area into the file
     */
    class WriteTask extends SwingWorker<Void, String> {
        @Override
        public Void doInBackground()
        {
            try {
                File input;
                System.out.println("what");

                //if a file is selected and user chose not to save as new file. Else get a filepath from user
                if(!(savingAsNewFile || fileTextField.getText().equals("")))
                    input = new File(fileName);
                else {
                    savingAsNewFile = false;

                    //get a place to save from the user
                    if(fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        fileName = fc.getSelectedFile().getAbsolutePath();
                        fileTextField.setText(fileName);
                        input = new File(fileName);
                    }
                    else {
                        return null;
                    }
                }

                //now have a save location and name, write it
                try{
                    PrintWriter writer = new PrintWriter(input, "UTF-8");
                    writer.println(textArea.getText());
                    writer.close();
                    statusLabel.setText("Saved!");
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(frame, "Writing failed.");
                }
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(frame, "IOError: " + e.getMessage());
            }
            return null;
        }
    }

    /**
     * Clears the text area and loads in the file
     */
    class ReadTask extends SwingWorker<Void, String> {
        /*
        * Main task. Executed in background thread.
        */
        @Override
        public Void doInBackground()
        {
            if(fileName.equals("")) {
                JOptionPane.showMessageDialog(frame, "Choose a file!");
                return null;
            }

            try {
                File input = new File(fileName);
                reader = new BufferedReader(new FileReader(input));
                textArea.setText("");
                String read;
                while ((read = reader.readLine()) != null)
                    textArea.appendText(read);
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(frame, "An IO error occurred: " + e.getMessage());
            }
            return null;
        }
    }

    private void chooseFile() {
        fileTextField.setText("");
        fileName = "";
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            fileTextField.setText(file.getAbsolutePath());
            fileName = file.getAbsolutePath();
            readTask.execute();
        } else {
            JOptionPane.showMessageDialog(this, "Open command cancelled by user.");
        }
    }

    private void addListeners(){
        // add button listeners

        //activates file selector
        fileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readTask = new ReadTask();
                chooseFile();
                statusLabel.setText("Ready");
            }
        });

        //activates reset
        closeFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        //starts a background task to write
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTask = new WriteTask();
                writeTask.execute();
            }
        });

        //saves but also raises flag to select a new file
        saveAsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeTask = new WriteTask();
                savingAsNewFile = true;
                writeTask.execute();
            }
        });

        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                statusLabel.setText("Ready");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }

    /**
     * Clears the text area, resets values to initial
     * asking if the user is sure they want to close without saving if the file has been edited
     *
     */
    private void reset(){
        statusLabel.setText("Ready");
        fileTextField.setText("");
        textArea.setText("");
        fileName = "";
    }

    private void setupFilter(){
        //filter for only text files
        //FileNameExtensionFilter f1 = new FileNameExtensionFilter("txt file");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt","doc",
                "docx","rtf", "log","odt"));
        fc.setAcceptAllFileFilterUsed(false);
    }

    private FileEditor(JFrame frame, String title) {
        super(title, false, true, false, false);
        this.frame = frame;
        // init
        defaultTextAreaMessage = "";
        textArea = new ScrollableTextView(20, 50);
        fileBtn = new JButton("Open");
        closeFileBtn = new JButton("Clear");
        saveBtn = new JButton("Save");
        saveAsBtn = new JButton("Save as");
        statusLabel = new JLabel("Ready...");
        fc = new JFileChooser();
        fileLabel = new JLabel("File Path: ");
        fileName = "";
        fileTextField = new JTextField(45);
        fileTextField.setEditable(false);

        textArea.getTextArea().setEditable(true);
        fileBtn.setPreferredSize(new Dimension(80, 20));
        closeFileBtn.setPreferredSize(new Dimension(80, 20));
        saveBtn.setPreferredSize(new Dimension(80, 20));
        saveAsBtn.setPreferredSize(new Dimension(80, 20));


        JPanel textAreaPanel = new JPanel();
        JPanel mediaSelectorPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        textAreaPanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout());
        statusPanel.setLayout(new FlowLayout());
        mediaSelectorPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        setupFilter();

        //AddPadding.pad(statusLabel);
        //mediaSelectorPanel needs to be offset to the right a bit
        AddPadding.pad(mediaSelectorPanel,15,0,0,0);

        textAreaPanel.add(textArea);
        buttonPanel.add(fileBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(saveAsBtn);
        buttonPanel.add(closeFileBtn);
        mediaSelectorPanel.add(fileLabel);
        mediaSelectorPanel.add(fileTextField);
        statusPanel.add(statusLabel);

        mainPanel.add(textAreaPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(mediaSelectorPanel);
        mainPanel.add(statusPanel);

        add(mainPanel, BorderLayout.NORTH);

        addListeners();
        pack();
        setBounds(25, 25, 600, 475);
        setLocation(50, 50);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}