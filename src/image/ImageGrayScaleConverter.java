package image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Converts an image to grayscale. Uses singleton pattern.
 *
 * @since 3/1/2018
 * @see image.FileSelectionDialog
 */
public class ImageGrayScaleConverter extends image.FileSelectionDialog {
    private static ImageGrayScaleConverter instance = null;
    private static final String TITLE = "Convert Image to Grayscale";
    private BufferedImage image;
    private int width, height;

    public static ImageGrayScaleConverter getInstance(JFrame frame) {
        if(instance == null) {
            instance = new ImageGrayScaleConverter(frame);
        }
        return instance;
    }

    private ImageGrayScaleConverter(JFrame jFrame){
        super(jFrame, TITLE);
    }

    @Override
    protected Void workToDo(){
        if(fileName.equals("")) {
            JOptionPane.showMessageDialog(frame, "Choose a file!");
            return null;
        }
        progressBar.setIndeterminate(true);
        lbl2.setText("");
        try {
            //this code was retrieved from https://www.tutorialspoint.com/java_dip/grayscale_conversion.htm
            //slightly modified to work with existing code. Takes an image and turns it into grayscale version
            lbl.setText("Changing to grayscale...");
            File input = new File(fileName);
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    Color c = new Color(image.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() *0.114);
                    Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
                    image.setRGB(j,i,newColor.getRGB());
                }
            }
            //used to skip any periods in the directory, and get the position of the . in the filename
            int positiion = fileName.lastIndexOf('.');
            int lastDirectory = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
            if(positiion>lastDirectory) {
                String fileDirectoryAndName = fileName.substring(0,positiion);
                String fileType = fileName.substring(positiion+1);

                File output = new File(fileDirectoryAndName + "Grayscale." + fileType);
                ImageIO.write(image, fileType, output);
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
    protected void workDone(){
        super.workDone();
        lbl.setText("Done.");
        progressBar.setIndeterminate(false);
        readBtn.setEnabled(true);
    }
}

