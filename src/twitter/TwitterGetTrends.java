package twitter;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import tools.ScrollableTextView;
import twitter4j.Location;
import twitter4j.ResponseList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * Gets the trends of a given location, displaying them in a non-editable text area
 *
 * @since 3/4/2018.
 */
public class TwitterGetTrends extends JInternalFrame {
    private static TwitterGetTrends instance = null;

    private static final String TITLE = "Trending In LA";

    private ScrollableTextView areaScrollPane;
    private  TwitterHandler twitterHandler;
    private Task task;
    private String locationName = "Los Angeles";


    public static TwitterGetTrends getInstance() {
        if (instance == null) {
            instance = new TwitterGetTrends();
        }
        return instance;
    }

    class Task extends SwingWorker<Void, String> {
        @Override
        public Void doInBackground()
        {
            return null;
        }
    }

    private TwitterGetTrends() {
        super(TITLE, false, true, false, false);
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


        int idTrendLocation = 0;
        twitterHandler = TwitterHandler.getInstance();
        ResponseList<Location> locations;
        /*
        try {
            locations = twitterHandler.getTwitterFactory().getInstance().getAvailableTrends();

            for (Location location : locations) {
                if (location.getName().toLowerCase().equals(locationName.toLowerCase())) {
                    idTrendLocation = location.getWoeid();
                    break;
                }
            }

            if (idTrendLocation > 0) {
            }

            return null;
            if (idTrendLocation == null) {
                System.out.println("Trend Location Not Found");
            }

            Trends trends = twitter.getPlaceTrends(idTrendLocation);
            for (int i = 0; i < trends.getTrends().length; i++) {
                System.out.println(trends.getTrends()[i].getName());
            }
        }catch(TwitterException tw){

        }
        */
    }
}
