import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


// original code: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class GridButton extends JButton implements MouseListener {
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

    public GridButton(Dimension dimension) {
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

    public void mouseClicked(MouseEvent e){}
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
    public void addActionListener(ActionListener listener)
    {
        listeners.add(listener);
    }

    // private void notifyListeners(MouseEvent e)
    // {
    //     ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
    //     synchronized(listeners)
    //     {
    //         for (int i = 0; i < listeners.size(); i++)
    //         {
    //             ActionListener tmp = listeners.get(i);
    //             tmp.actionPerformed(event);
    //         }
    //     }
    // }
}
