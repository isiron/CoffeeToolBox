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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Loads an image, scales it to whatever is specified, then saves it
 *
 * @since 3/4/2018
 * @see image.FileSelectionDialog
 */
public class ImageResize extends JInternalFrame {
    private static ImageResize instance = null;
    private static final String TITLE = "Image File Type Converter";
    private BufferedImage image;
    private JLabel lbl, lbl2;
    private JTextField tf, textField;
    private JButton fileBtn, readBtn;
    private JFileChooser fc;
    private String fileName;
    private Task task;
    private JProgressBar progressBar;
    private JFrame frame; // to properly center JDialogFrame

    public static ImageResize getInstance(JFrame frame) {
        if(instance == null) {
            instance = new ImageResize(frame);
        }
        return instance;
    }
    class Task extends SwingWorker<Void, String> {
        /*
        * Main task. Executed in background thread.
        */
        @Override
        public Void doInBackground() {
            if(fileName.equals("")) {
                JOptionPane.showMessageDialog(frame, "Choose a file!");
                return null;
            }
            progressBar.setIndeterminate(true);

            try {
                //this code was retrieved from https://www.tutorialspoint.com/java_dip/grayscale_conversion.htm
                //slightly modified to work with existing code. Takes an image and turns it into grayscale version

                File input = new File(fileName);
                image = ImageIO.read(input);

                //used to skip any periods in the directory, and get the position of the . in the filename
                int positiion = fileName.lastIndexOf('.');
                int lastDirectory = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
                float scale;
                int width, height;

                if(positiion>lastDirectory) {
                    String fileDirectoryAndName = fileName.substring(0,positiion);
                    String fileType = fileName.substring(positiion+1);

                    try{
                        scale = Float.parseFloat(textField.getText());

                        Image newImage = image.getScaledInstance((int)(image.getWidth()*scale),
                                (int)(image.getHeight()*scale), Image.SCALE_DEFAULT);

                        //convert to buffered image, ready for writing
                        BufferedImage bufferedImage = new BufferedImage (newImage.getWidth(null),
                                newImage.getHeight(null),BufferedImage.TYPE_INT_RGB);

                        Graphics2D drawer = bufferedImage.createGraphics();
                        drawer.drawImage(newImage, 0, 0, null);
                        drawer.dispose();

                        File output = new File(fileDirectoryAndName + "x" + scale + "." + fileType);
                        ImageIO.write(bufferedImage, fileType, output);
                    }
                    catch(Exception e) {
                        //not float, maybe two ints seperated by an 'x'?
                        int inputXPos = textField.getText().lastIndexOf('x');
                        try{
                            width = Integer.parseInt(textField.getText().substring(0,inputXPos));
                            height = Integer.parseInt(textField.getText().substring(inputXPos+1));

                            Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                            //convert to buffered image, ready for writing
                            BufferedImage bufferedImage = new BufferedImage (newImage.getWidth(null),
                                    newImage.getHeight(null),BufferedImage.TYPE_INT_RGB);

                            Graphics2D drawer = bufferedImage.createGraphics();
                            drawer.drawImage(newImage, 0, 0, null);
                            drawer.dispose();

                            File output = new File(fileDirectoryAndName + width + "x" + height + "." + fileType);
                            ImageIO.write(bufferedImage, fileType, output);
                        }
                        catch (Exception ex){
                            //failed both, alert user
                            JOptionPane.showMessageDialog(frame, "Please enter valid scale or dimensions." +
                                    "1.2 or 500x500 for example");
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Error in trying to write image. " +
                            "Funky directories with a lot of periods will do this");
                }
            }
            catch(FileNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "File not found!");
            }
            catch(IOException ex) {
                JOptionPane.showMessageDialog(frame, "An error occured");
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, "An error occured");
            }
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
            lbl.setText("Done.");
            progressBar.setIndeterminate(false);
            readBtn.setEnabled(true);
        }
    }

    private void chooseFile() {
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

    private ImageResize(JFrame frame) {
        super(TITLE, false, true, false, false);
        this.frame = frame;
        // init
        tf = new JTextField(50);
        tf.setEditable(false);
        fileBtn = new JButton("...");
        readBtn = new JButton("Start ...");
        lbl = new JLabel("Ready...");
        lbl2 = new JLabel("Enter scale. Or Width x Height dimensions: ");
        fc = new JFileChooser();
        progressBar = new JProgressBar(0, 100);
        //Call setStringPainted now so that the progress bar height
        //stays the same whether or not the string is shown.
        progressBar.setStringPainted(false);
        fileName = "";
        textField = new JTextField(15);
        lbl2.setText("1.0 or 100x100 format");

        fileBtn.setPreferredSize(new Dimension(20, 20));
        readBtn.setPreferredSize(new Dimension(80, 20));

        JPanel upperPanel = new JPanel();
        JPanel midPanel = new JPanel();
        JPanel lowerPanel = new JPanel();
        JPanel selectButtonPanel = new JPanel();
        JPanel convButtonPanel = new JPanel();
        JPanel progPanel = new JPanel();

        upperPanel.setLayout(new FlowLayout());
        midPanel.setLayout(new BorderLayout());
        lowerPanel.setLayout(new FlowLayout());
        selectButtonPanel.setLayout(new FlowLayout());
        convButtonPanel.setLayout(new FlowLayout());
        progPanel.setLayout(new FlowLayout());

        //filter for only image files
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes()));
        fc.setAcceptAllFileFilterUsed(false);

        upperPanel.add(tf);
        upperPanel.add(fileBtn);

        selectButtonPanel.add(lbl2);
        selectButtonPanel.add(textField);

        convButtonPanel.add(readBtn);
        progPanel.add(progressBar);

        midPanel.add(selectButtonPanel,BorderLayout.NORTH);
        midPanel.add(convButtonPanel,BorderLayout.CENTER);
        midPanel.add(progPanel,BorderLayout.SOUTH);

        lowerPanel.add(lbl);

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

        String exampleText = "1.0 or 100x100 format";
        //add focus listener. When clicked out of box, if box is empty, it will appear a tip on how to do the formmating
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(exampleText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(exampleText);
                }
            }
        });

        pack();
        setBounds(25, 25, 600, 180);
        setLocation(50, 50);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}