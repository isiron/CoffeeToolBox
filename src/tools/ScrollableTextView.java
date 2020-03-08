package tools;

import javax.swing.JScrollPane;
import javax.swing.text.DefaultCaret;
import twitter.CustomTextArea;

/**
 * Preconfigured text area with a scroll bar
 *
 * @since 3/6/2018.
 */
public class ScrollableTextView extends JScrollPane {
    CustomTextArea textArea;

    public ScrollableTextView(){

        textArea = new CustomTextArea("",50,20);
        textArea.setEditable(false);
        //configure a scroll bar
        setViewportView(textArea);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    public ScrollableTextView(int rows, int cols){

        textArea = new CustomTextArea("",rows,cols);
        textArea.setEditable(false);
        //configure a scroll bar
        //areaScrollPane = new JScrollPane(textArea);
        setViewportView(textArea);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    public CustomTextArea getTextArea(){
        return textArea;
    }

    public void setText(String s){
        textArea.setText(s);
    }

    public void appendText(String s){
        textArea.append(s);
    }

    public String getText(){
        return textArea.getText();
    }
}
