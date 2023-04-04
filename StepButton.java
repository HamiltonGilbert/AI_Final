import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// original code inspiration: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class StepButton extends JButton implements MouseListener {
    private boolean forward;
    public StepButton(Dimension dimension, boolean forward) {
        super();
        this.forward = forward;
        if (forward) setText("Step");
        else setText("Backstep");
        this.forward = forward;
        
        this.setPreferredSize(dimension); 
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
        if (forward) Visualization.nextBtnHit();
        else Visualization.backBtnHit();
    }
}
