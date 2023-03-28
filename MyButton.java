import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


// original code: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class MyButton extends JButton implements MouseListener {
    private boolean mousePressed = false;
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

    public MyButton(int width, int height) {
        super();
        this.setSize(width, height);
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
        notifyListeners(e);
        mousePressed = true;
        repaint();
        setText("Hit");
    }
    public void mouseReleased(MouseEvent e)
    {
        mousePressed = false;
        setText("");
    }
    public void addActionListener(ActionListener listener)
    {
        listeners.add(listener);
    }

    private void notifyListeners(MouseEvent e)
    {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
        synchronized(listeners)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(event);
            }
        }
    }
}
