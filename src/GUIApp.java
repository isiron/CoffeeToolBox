// abstract window toolkit (awt)
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Cursor;

// awt events
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// swing
import conversions.TemperatureDialog;
import conversions.HeightDialog;
import conversions.WeightDialog;
import datetimef.DayOnDate;
import datetimef.TwelveToTwentyFour;
import datetimef.WorldClock;
import image.ImageResize;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JTree;

// swing event

// swing tree
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import misctools.AreaOfBox;
import misctools.AreaOfCircle;
import misctools.FileEditor;
import twitter.TwitterGetTrends;
import twitter.TwitterGetTweets;
import twitter.TwitterPostStatus;

/**
 * The main panel that appears when you run the app.
 *
 * @author Ivan Suarez/Sevak Asadorian
 * @since 2/31/18
 */
public class GUIApp {

    private JFrame frame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JDesktopPane desktop;
    private JSplitPane splitPane;
    private JPanel labelPanel;
    private JLabel statusLabel;
    private JTree tree;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuItem exitItem;
    private JMenuItem aboutItem;

    // constructor
    public GUIApp() {
        initComponents();
        statusLabel.setText("Initialization complete.");
    }

    private void exitActionPerformed() {
        frame.dispose();
    }

    private void aboutActionPerformed() {
        JOptionPane.showMessageDialog(null, "Coffee tool box 1.0");
    }

    private void treeClicked() {

        // get the last selected tree node
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        // if the node is a leaf (no children, keep going)
        if (node != null && node.isLeaf()) {

            statusLabel.setText(node.toString() + " clicked.");

            if(node.toString().equals("Temperature")) {
                TemperatureDialog tem = TemperatureDialog.getInstance();
                if(!tem.isVisible()) {
                    tem.setVisible(true);
                    desktop.add(tem);
                }
            }
            else if(node.toString().equals("Weight")) {
                WeightDialog wt = WeightDialog.getInstance();
                if(!wt.isVisible()) {
                    wt.setVisible(true);
                    desktop.add(wt);
                }
            }
            else if(node.toString().equals("Height")) {
                HeightDialog ht = HeightDialog.getInstance();
                if (!ht.isVisible()) {
                    ht.setVisible(true);
                    desktop.add(ht);
                }
            }else if(node.toString().equals("Day On Date")) {
                DayOnDate dod = DayOnDate.getInstance();
                if (!dod.isVisible()) {
                    dod.setVisible(true);
                    desktop.add(dod);
                }
            }else if(node.toString().equals("12Hr to 24Hr")) {
                TwelveToTwentyFour totf = TwelveToTwentyFour.getInstance();
                if (!totf.isVisible()) {
                    totf.setVisible(true);
                    desktop.add(totf);
                }
            }else if(node.toString().equals("World Clock")) {
                WorldClock wc = WorldClock.getInstance();
                if (!wc.isVisible()) {
                    wc.setVisible(true);
                    desktop.add(wc);
                }
            }
            else if(node.toString().equals("Convert Image Type")) {
                image.ImageTypeConverter itc = image.ImageTypeConverter.getInstance(frame);
                if (!itc.isVisible()) {
                    itc.setVisible(true);
                    desktop.add(itc);
                }
            }
            else if(node.toString().equals("Convert Image to Grayscale")) {
                image.ImageGrayScaleConverter igc = image.ImageGrayScaleConverter.getInstance(frame);
                if (!igc.isVisible()) {
                    igc.setVisible(true);
                    desktop.add(igc);
                }
            }else if(node.toString().equals("Resize Image")) {
                ImageResize ri = ImageResize.getInstance(frame);
                if (!ri.isVisible()) {
                    ri.setVisible(true);
                    desktop.add(ri);
                }
            }else if(node.toString().equals("Post Status")) {
                TwitterPostStatus tps = TwitterPostStatus.getInstance(frame);
                if (!tps.isVisible()) {
                    tps.setVisible(true);
                    desktop.add(tps);
                }
            }else if(node.toString().equals("Get Twitter Feed")) {
                TwitterGetTweets tgt = TwitterGetTweets.getInstance(frame);
                if (!tgt.isVisible()) {
                    tgt.setVisible(true);
                    desktop.add(tgt);
                }
            }else if(node.toString().equals("Text File Editor")) {
                FileEditor tfe = FileEditor.getInstance(frame);
                if (!tfe.isVisible()) {
                    tfe.setVisible(true);
                    desktop.add(tfe);
                }
            }else if(node.toString().equals("Get Trending In LA")) {
                TwitterGetTrends tgtr = TwitterGetTrends.getInstance();
                if (!tgtr.isVisible()) {
                    tgtr.setVisible(true);
                    desktop.add(tgtr);
                }
            }else if(node.toString().equals("Area of circle")) {
                AreaOfCircle aoc = AreaOfCircle.getInstance();
                if (!aoc.isVisible()) {
                    aoc.setVisible(true);
                    desktop.add(aoc);
                }
            }else if(node.toString().equals("Area of box")) {
                AreaOfBox aob = AreaOfBox.getInstance();
                if (!aob.isVisible()) {
                    aob.setVisible(true);
                    desktop.add(aob);
                }
            }


        } // end if isLeaf

    } // end treeClicked

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildDesktop() {

        desktop = new JDesktopPane()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                ImageIcon icon = new ImageIcon("resources/GBJL1FI.jpg");
                Image image = icon.getImage();

                int x=0, y=0;
                double imageWidth = image.getWidth(null);
                double imageHeight = image.getHeight(null);
                double screenWidth = getWidth();
                double screenHeight = getHeight();

                if(screenWidth != 0) {
                    x = (int)screenWidth  / 2 - (int)imageWidth  / 2;
                }

                if(screenHeight != 0) {
                    y = (int)screenHeight / 2 - (int)imageHeight / 2;
                }

                g.drawImage(image, x, y, this);
            }
        };

    } // end buildDesktop

    private void buildTree() {

        // Create data for the tree
        DefaultMutableTreeNode root
                = new DefaultMutableTreeNode("Tools");

        //category types
        DefaultMutableTreeNode alg
                = new DefaultMutableTreeNode("Algorithms");
        DefaultMutableTreeNode io
                = new DefaultMutableTreeNode("Images");
        DefaultMutableTreeNode inter
                = new DefaultMutableTreeNode("Internet");

        //sorted by categories with tools under
        DefaultMutableTreeNode convs
                = new DefaultMutableTreeNode("Conversions");
        DefaultMutableTreeNode tem
                = new DefaultMutableTreeNode("Temperature");
        DefaultMutableTreeNode weight
                = new DefaultMutableTreeNode("Weight");
        DefaultMutableTreeNode height
                = new DefaultMutableTreeNode("Height");


        DefaultMutableTreeNode dandt
                = new DefaultMutableTreeNode("Date and Time");
        DefaultMutableTreeNode chzo
                = new DefaultMutableTreeNode("12Hr to 24Hr");
        DefaultMutableTreeNode dod
                = new DefaultMutableTreeNode("Day On Date");
        DefaultMutableTreeNode tz
                = new DefaultMutableTreeNode("World Clock");


        DefaultMutableTreeNode img
                = new DefaultMutableTreeNode("Process");
        DefaultMutableTreeNode conImg
                = new DefaultMutableTreeNode("Convert Image Type");
        DefaultMutableTreeNode grayImg
                = new DefaultMutableTreeNode("Convert Image to Grayscale");
        DefaultMutableTreeNode reImg
                = new DefaultMutableTreeNode("Resize Image");


        DefaultMutableTreeNode twit
                = new DefaultMutableTreeNode("Twitter");
        DefaultMutableTreeNode getNewT
                = new DefaultMutableTreeNode("Get Twitter Feed");
        DefaultMutableTreeNode poImg
                = new DefaultMutableTreeNode("Post Status");
        DefaultMutableTreeNode getTrend
                = new DefaultMutableTreeNode("Get Trending In LA");


        DefaultMutableTreeNode misc
                = new DefaultMutableTreeNode("Misc");
        DefaultMutableTreeNode tFileEditor
                = new DefaultMutableTreeNode("Text File Editor");
        DefaultMutableTreeNode areaOfCirlce
                = new DefaultMutableTreeNode("Area of box");
        DefaultMutableTreeNode areaOfBox
                = new DefaultMutableTreeNode("Area of circle");

        convs.add(tem);
        convs.add(weight);
        convs.add(height);

        dandt.add(chzo);
        dandt.add(dod);
        dandt.add(tz);

        img.add(conImg);
        img.add(grayImg);
        img.add(reImg);

        twit.add(getNewT);
        twit.add(poImg);
        twit.add(getTrend);

        misc.add(tFileEditor);
        misc.add(areaOfCirlce);
        misc.add(areaOfBox);

        alg.add(convs);
        alg.add(dandt);
        io.add(img);
        inter.add(twit);

        root.add(alg);
        root.add(io);
        root.add(inter);
        root.add(misc);

        // create a new tree
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);

    } // buildTree

    private void addTreeListeners() {

        tree.addMouseMotionListener(
                new MouseAdapter() {

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ((JTree)e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                        TreePath pathForLocation = tree.getPathForLocation(e.getX(), e.getY());
                        if(pathForLocation != null) {
                            Object lastPathComponent = pathForLocation.getLastPathComponent();
                            if(lastPathComponent instanceof DefaultMutableTreeNode) {
                                DefaultMutableTreeNode node = (DefaultMutableTreeNode)lastPathComponent;
                                if(node.isLeaf()) {
                                    ((JTree)e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }
                                else {
                                    ((JTree)e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //System.out.println("clicked!");
                    }
                }
        );

        tree.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        int selRow = tree.getRowForLocation(e.getX(), e.getY());
                        if(selRow != -1) {
                            treeClicked();
                        }
                    }
                }
        );
    } // addTreeListeners

    private void buildMenu() {

        // build menu
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");
        exitItem = new JMenuItem("Exit");
        aboutItem = new JMenuItem("About");
        fileMenu.add(exitItem);
        helpMenu.add(aboutItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

    } // end buildMenu

    private void addMenuListeners() {

        // add listener for exit menu item
        exitItem.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        exitActionPerformed();
                    }
                }
        );

        // add listener for about menu item
        aboutItem.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aboutActionPerformed();
                    }
                }
        );

    } // end addMenuListeners

    private void buildPanel() {

        panel = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        scrollPane = new JScrollPane();
        labelPanel = new JPanel();
        statusLabel = new JLabel();

        scrollPane.getViewport().add(tree);

        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusLabel.setMinimumSize(new Dimension(0,18));
        statusLabel.setPreferredSize(new Dimension(0,18));

        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        splitPane.setContinuousLayout(true);
        splitPane.add(desktop, JSplitPane.RIGHT);
        splitPane.add(scrollPane, JSplitPane.LEFT);

        panel.setLayout(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);

        labelPanel.setLayout(new BorderLayout());
        labelPanel.add(statusLabel, BorderLayout.CENTER);

    } // end buildPanel

    private void buildFrame() {

        // create a new frame and show it
        frame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Coffee's Tools");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/IWS.png"));
        // add label panel
        frame.getContentPane().add(labelPanel, BorderLayout.SOUTH);
        // add main panel
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        // add menu bar
        frame.setJMenuBar(menuBar);
        frame.setSize(1280,800);
        frame.setVisible(true);

    }

    // create a GUI and make it visible to user
    private void initComponents() {
        setLookAndFeel();
        buildDesktop();
        buildTree();
        addTreeListeners();
        buildMenu();
        addMenuListeners();
        buildPanel();
        buildFrame();
    } // end initComponents

} // end MyFirstGUIApp
