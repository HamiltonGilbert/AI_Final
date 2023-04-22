import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// original code inspiration: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class SetTileButton extends JButton implements MouseListener {
    private Visualization visualization;
    private String tile;
    public SetTileButton(Visualization vis, Dimension dimension, String tile) {
        super();
        this.visualization = vis;
        this.tile = tile;
        if (this.tile != "Erase") {setText("Set " + this.tile);}
        else {setText(this.tile);}
        
        this.setPreferredSize(dimension); 
        addMouseListener(this);
    }

    // @Override
    // public void paintComponent(Graphics g) {

    // }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {setCursor(new Cursor(Cursor.HAND_CURSOR));}
    public void mouseExited(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
    public void mousePressed(MouseEvent e) {this.visualization.newSetTiles(tile);}
    public void mouseReleased(MouseEvent e) {}
}