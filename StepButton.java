import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


// original code: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class StepButton extends JButton implements MouseListener {
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

    public StepButton(String text, Dimension dimension) {
        super(text);
        this.setPreferredSize(dimension);
        enableInputMethods(true);   
        addMouseListener(this);
    }

    // @Override
    // public void paintComponent(Graphics g) {

    // }

    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e) {setCursor(new Cursor(Cursor.HAND_CURSOR));}
    public void mouseExited(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
    public void mousePressed(MouseEvent e)
    {

    }
    public void mouseReleased(MouseEvent e)
    {
        Test.nextBtnHit();
    }
}
