import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


// original code: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class GridTile extends JPanel implements MouseListener {
    public GridTile(Dimension dimension) {
        super();
        this.setPreferredSize(dimension);
        enableInputMethods(true);   
        addMouseListener(this);
    }

    public void setVisited(int stepNum) {
        setText("" + stepNum);
    }

    // @Override
    // public void paintComponent(Graphics g) {

    // }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {setCursor(new Cursor(Cursor.HAND_CURSOR));}
    public void mouseExited(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
    public void mousePressed(MouseEvent e)
    {
        // notifyListeners(e);
        repaint();
        setText("Hit");
    }
    public void mouseReleased(MouseEvent e)
    {
        setText("");
    }
}