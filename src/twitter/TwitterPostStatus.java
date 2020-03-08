package twitter;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import tools.AddPadding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.List;

/**
 * Class that has the window for creating and posting a tweet
 *
 * @since 3/3/18
 */
public class TwitterPostStatus extends JInternalFrame {
    private static TwitterPostStatus instance = null;

    private static final String TITLE = "Post To Twitter";

    private JTextField mediaTextField;
    private JLabel statusLabel, mediaLabel;
    private JButton fileBtn, postBtn, clearFileBtn;
    private JFileChooser fc;
    private JProgressBar progressBar;
    private JFrame frame; // to properly center JDialogFrame
    private JScrollPane areaScrollPane;
    private CustomTextArea textArea;

    private String fileName, defaultTextAreaMessage, tweetStatus;
    private Task task;
    private boolean hasEdited = false, isToLong = false;

    private final String OPTIONAL_STR= "optional";
    private final String EMPTYTWEETERROR_STR= "Error: No image or text.";

    public static TwitterPostStatus getInstance(JFrame frame) {
        if (instance == null) {
            instance = new TwitterPostStatus(frame, TITLE);
        }
        return instance;
    }

    class Task extends SwingWorker<Void, String> {
        /*
        * Main task. Executed in background thread.
        */
        @Override
        public Void doInBackground() {
            //if media name still says optional then there is no media to upload
            if(textArea.getText().length() >280) {
                isToLong = true;
                statusLabel.setText("Tweet is " + textArea.getText().length() + " characters long. Limit is 280.");
                return null;
            }else
                isToLong = false;

            TwitterHandler th = TwitterHandler.getInstance();
            statusLabel.setText("Posting...");

            //if the media field's text still says optional, only try to do string
            if (mediaTextField.getText().equals(OPTIONAL_STR)) {

                //if the text area doesn't contain the default message, unless it has been edited, send tweet
                if (textArea.getText().equals(defaultTextAreaMessage) || hasEdited) {

                    //if the message is empty print error, else post
                    if(textArea.getText().equals(""))
                        statusLabel.setText(EMPTYTWEETERROR_STR);
                    else
                        tweetStatus = th.createTweet(textArea.getText());
                }
                else
                    statusLabel.setText(EMPTYTWEETERROR_STR);
                return null;
            } else // else there is media, try reading it and adding it to the tweet to send
                {
                try {
                    //make a file object to send
                    File input = new File(fileName);
                    String tweetWords = textArea.getText();

                    //make sure not to send default text area. If it is the default message and has not been edited
                    if (tweetWords.equals(defaultTextAreaMessage))
                        if(!hasEdited)
                            tweetWords = "";

                    //send tweet with the image
                    tweetStatus = th.createTweet(tweetWords,input);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occured trying to read the file");
                }
                return null;
            }
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
            //if successful, erase the media field
            if(tweetStatus!=null) {
                if (tweetStatus.equals(TwitterHandler.getSuccessMessage())) {
                    textArea.setText("");
                    mediaTextField.setText(OPTIONAL_STR);
                }
            }

            //if there wasn't an error with the message being empty, update the label
            if(!statusLabel.getText().equals(EMPTYTWEETERROR_STR) && !isToLong)
                statusLabel.setText(tweetStatus);
            progressBar.setIndeterminate(false);
            postBtn.setEnabled(true);
        }
    }

    private void chooseFile() {
        mediaTextField.setText("");
        fileName = "";
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            mediaTextField.setText(file.getAbsolutePath());
            fileName = file.getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(this, "Open command cancelled by user.");
            mediaTextField.setText(OPTIONAL_STR);
        }
    }

    private TwitterPostStatus(JFrame frame, String title) {
        super(title, false, true, false, false);
        this.frame = frame;
        // init
        defaultTextAreaMessage = "Enter message here. Limit 280 Characters.";
        mediaTextField = new JTextField(37);
        mediaTextField.setText(OPTIONAL_STR);
        mediaTextField.setEditable(false);
        textArea = new CustomTextArea(defaultTextAreaMessage, 7, 50);
        fileBtn = new JButton("...");
        clearFileBtn = new JButton("X");
        postBtn = new JButton("Post");
        statusLabel = new JLabel("Ready...");
        fc = new JFileChooser();
        mediaLabel = new JLabel("Add image: ");
        progressBar = new JProgressBar(0, 100);
        //Call setStringPainted now so that the progress bar height
        //stays the same whether or not the string is shown.
        progressBar.setStringPainted(false);
        fileName = "";

        //configure a scroll bar
        areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        fileBtn.setPreferredSize(new Dimension(20, 20));
        clearFileBtn.setPreferredSize(new Dimension(45, 20));
        postBtn.setPreferredSize(new Dimension(80, 20));


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

        //filter for only image files
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes()));
        fc.setAcceptAllFileFilterUsed(false);

        AddPadding.pad(statusLabel);
        //mediaSelectorPanel needs to be offset to the right a bit
        AddPadding.pad(mediaSelectorPanel,15,0,0,0);

        textAreaPanel.add(areaScrollPane);
        mediaSelectorPanel.add(mediaLabel);
        mediaSelectorPanel.add(mediaTextField);
        mediaSelectorPanel.add(fileBtn);
        mediaSelectorPanel.add(clearFileBtn);
        buttonPanel.add(postBtn);
        statusPanel.add(statusLabel);

        mainPanel.add(textAreaPanel);
        mainPanel.add(mediaSelectorPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(statusPanel);


        add(mainPanel, BorderLayout.NORTH);

        // add button listeners
        fileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });

        clearFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaTextField.setText(OPTIONAL_STR);
                statusLabel.setText("Media selector cleared.");
            }
        });

        postBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postBtn.setEnabled(false);
                task = new Task();
                task.execute();
            }
        });

        //add focus listener. When clicked out of box, if box is empty, it will appear a tip on how to do the formmating
        textArea.setForeground(Color.GRAY);
        mediaTextField.setDisabledTextColor(Color.GRAY);
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(defaultTextAreaMessage) && !hasEdited) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty() && !hasEdited) {
                    textArea.setForeground(Color.GRAY);
                    textArea.setText(defaultTextAreaMessage);
                } else
                    hasEdited = true;
            }
        });

        pack();
        setBounds(25, 25, 600, 265);
        setLocation(50, 50);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}