import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// original code: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class GridTile extends JButton implements MouseListener {
    //private boolean isObstacle;
    public GridTile(Dimension dimension, boolean isObstacle) {
        super();
        setFont(new Font("Arial", Font.PLAIN, dimension.height/5));
        setPreferredSize(dimension);
        enableInputMethods(true);   
        addMouseListener(this);

        // Background b = new Background();
        // TextArea text = new TextArea();
        // add(b, 0);
        // add(text, 1);
    }

    public void setObstacle() {
        setText("X");
        setFont(new Font("Arial", Font.PLAIN, getPreferredSize().height/2));
    }

    // @Override
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);

    //     g.setColor(Color.RED);
    //     g.fillRect(0, 0, getSize().width - 8, getSize().height - 8);
    // }

    public void setVisited(int stepNum) {
        setText("" + stepNum);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {setCursor(new Cursor(Cursor.HAND_CURSOR));}
    public void mouseExited(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}

// class Background extends JComponent {
//     @Override
//     public void paintComponent(Graphics g) {
//         super.paintComponent(g);

//         g.setColor(Color.RED);
//         g.fillRect(0, 0, getSize().width - 8, getSize().height - 8);
//     }
// }