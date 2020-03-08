package twitter;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import javax.swing.SwingWorker;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import tools.ScrollableTextView;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * Gets the tweets of the users following, displaying them in a non-editable text area
 *
 * @since 3/5/2018.
 */
public class TwitterGetTweets extends JInternalFrame {
    private static TwitterGetTweets instance = null;

    private static final String TITLE = "Tweet feed";

    private JFrame frame; // to properly center JDialogFrame
    //private CustomTextArea textArea;
    private ScrollableTextView areaScrollPane;
    private TwitterStream twitterStream;
    private StatusListener listener;
    private Task taskCleanup;
    private TaskTweetStream taskTweetStream;


    public static TwitterGetTweets getInstance(JFrame frame) {
        if (instance == null) {
            instance = new TwitterGetTweets(frame, TITLE);
        }
        return instance;
    }

    class Task extends SwingWorker<Void, String> {
        @Override
        public Void doInBackground()
        {
            twitterStream.cleanUp();
            return null;
        }
    }

    class TaskTweetStream extends SwingWorker<Void, String> {
        @Override
        public Void doInBackground()
        {
            // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
            if(twitterStream!=null){
                twitterStream.user();
            }
            else {
                //sets up a stream with a new listener
                listener = new StatusListener() {
                    public void onStatus(Status status) {
                        areaScrollPane.appendText("@" + status.getUser().getName() + " : " + status.getText() + "\n");
                    }
                    public void onStallWarning(StallWarning stallWarning) {}
                    public void onScrubGeo(long l1, long l2) {}
                    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
                    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
                    public void onException(Exception ex) {
                        ex.printStackTrace();
                    }
                };
                twitterStream = new TwitterStreamFactory().getInstance();
                twitterStream.addListener(listener);
                twitterStream.user();
            }
            return null;
        }
    }

    private TwitterGetTweets(JFrame frame, String title) {
        super(title, false, true, false, false);
        this.frame = frame;
        // init
        areaScrollPane = new ScrollableTextView();

        JPanel textAreaPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        textAreaPanel.setLayout(new FlowLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        textAreaPanel.add(areaScrollPane);
        mainPanel.add(textAreaPanel);

        add(areaScrollPane, BorderLayout.CENTER);

        pack();
        setBounds(25, 25, 600, 265);
        setLocation(50, 50);

        taskCleanup = new Task();
        taskTweetStream = new TaskTweetStream();
        taskTweetStream.execute();

        //add listeners for when the program is opened and closed
        addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosed(InternalFrameEvent e) {
                taskCleanup.execute();
            }
        });
        addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameOpened(InternalFrameEvent e) {
                taskTweetStream = new TaskTweetStream();
                taskTweetStream.execute();
            }
        });
    }
}
