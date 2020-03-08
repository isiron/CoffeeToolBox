package datetimef;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Starts a dialog box that has 4 clocks aligned vertically
 *
 * @author  Ivan Suarerz
 * @since   3/2/2018
 */
public class WorldClock extends JInternalFrame {
    private JLabel[] clockLabels;
    private String[] timeZoneNames, timeZoneInternal;
    private JComboBox<String>[] timeZoneList;
    private SimpleDateFormat twelveFormat;
    private int timeOffset;
    private long alteredTime, period = 1000, dstOffset = 3600000;
    private Timer timer;
    private Date time = new Date();
    private JPanel upperPanel;
    private Calendar cal = Calendar.getInstance();
    private final String TIME_FORMAT_24HR = "HH:mm";
    private final String TIME_FORMAT_12HR = "hh:mm a";

    private static WorldClock instance = null;

    public static WorldClock getInstance() {
        if(instance == null) {
            instance = new WorldClock();
        }
        return instance;
    }

    /**
     * Applies a listener that forces a refresh when a new item is selected from the combo boxes
     *
     */
    private void addListeners(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshClock();
            }
        };

        for (int i = 0; i<timeZoneList.length; i++)
            timeZoneList[i].addActionListener(actionListener);
    }

    private void refreshClock(){
        int selected;
        for(int i = 0; i<clockLabels.length; i++) {
            selected = timeZoneList[i].getSelectedIndex();
            if (TimeZone.getTimeZone(timeZoneInternal[selected]).inDaylightTime(new Date()))
                dstOffset = 3600000;
            else
                dstOffset = 0;
            alteredTime = cal.getTime().getTime() + TimeZone.getTimeZone(timeZoneInternal[selected]).getRawOffset() - timeOffset + dstOffset;
            cal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneInternal[selected]));
            cal.setTimeInMillis(alteredTime);
            clockLabels[i].setText(twelveFormat.format(cal.getTime()));
            cal = Calendar.getInstance();
        }
    }

    private void setClock(int clock, int timeZone){
        timeZoneList[clock].setSelectedIndex(timeZone);
        if(TimeZone.getTimeZone(timeZoneInternal[timeZone]).inDaylightTime(new Date()))
            dstOffset = 3600000;
        else
            dstOffset = 0;
        alteredTime = cal.getTime().getTime() + TimeZone.getTimeZone(timeZoneInternal[timeZone]).getRawOffset() - timeOffset + dstOffset;
        cal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneInternal[timeZone]));
        cal.setTimeInMillis(alteredTime);
        clockLabels[clock].setText(twelveFormat.format(cal.getTime()));
        cal = Calendar.getInstance();
    }

    /**
     * Constructor for a dialogue box, fills the textboxes with current time as well
     *
     * @see SimpleDateFormat
     */
    protected WorldClock() {

        // call constructor of JInternalFrame
        // Arguments: title, resizability, closability,
        // maximizability, and iconifiability
        super("World Clock", false, true, false, false);

        //Setup the formats for use in parsing later
        twelveFormat = new SimpleDateFormat(TIME_FORMAT_12HR);

        //initiate objects, There will be 4 labels, 4 textboxes with 24 options
        clockLabels = new JLabel[4];
        timeZoneList = new JComboBox[4];
        timeZoneNames = new String[]{"UTC +12 Auckland", "UTC +11 Noumea", "UTC +10 Sydney",
                "UTC +9 Tokyo","UTC +8 Beijing","UTC +7 Bangkok","UTC +6 Dhaka","UTC +5 Karachi","UTC +4 Dubai",
                "UTC +3 Moscow","UTC +2 Cairo","UTC +1 Berlin","UTC +0 London","UTC -1 Cape Verde",
                "UTC -2 South Georgia","UTC -3 São Paulo","UTC -4 Santiago", "UTC -5 New York",
                "UTC -6 Chicago", "UTC -7 Phoenix","UTC -8 Los Angeles","UTC -9 Anchorage", "UTC -10 Honolulu",
                "UTC -11 American Samoa"};
        timeZoneInternal = new String[] { "Pacific/Auckland", "Pacific/Noumea", "Australia/Sydney",
                "Asia/Tokyo", "Asia/Singapore", "Asia/Bangkok", "Asia/Dhaka", "Asia/Karachi", "Asia/Dubai",
                "Europe/Moscow","Africa/Cairo", "Europe/Berlin","Europe/London","Atlantic/Cape_Verde",
                "Atlantic/South_Georgia", "America/Sao_Paulo", "America/Santiago", "America/New_York",
                "America/Chicago", "America/Phoenix", "America/Los_Angeles", "America/Anchorage",
                "Pacific/Honolulu", "Pacific/Samoa"};
        for (int i = 0; i<clockLabels.length; i++) {
            clockLabels[i] = new JLabel("I am label " + i);
            timeZoneList[i] = new JComboBox<String>(timeZoneNames);
        }
        timeOffset = cal.getTimeZone().getRawOffset();
        timer = new Timer();

        upperPanel = new JPanel();

        upperPanel.setLayout(new FlowLayout());

        cal.setLenient(false);

        //UTC +12, UTC+1, UTC -3, UTC -8 or Indexes 0, 11, 15, 20
        setClock(0,0);
        setClock(1,11);
        setClock(2,15);
        setClock(3,20);

        for (int i = 0; i<clockLabels.length; i++) {
            upperPanel.add(clockLabels[i]);
            upperPanel.add(timeZoneList[i]);
        }

        add(upperPanel, BorderLayout.CENTER);
        addListeners();

        setBounds(25, 25, 300, 160);
        setLocation(100, 100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshClock();
            }
        }, 0, period);
    }
}
