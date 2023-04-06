import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// original code: https://github.com/kdeloach/labs/blob/master/java/yahtzee/src/Dice.java
public class VisibleTile extends JButton implements MouseListener {
    //private boolean isObstacle;
    private ImageIcon tileImage = new ImageIcon("Images/Tile.png");
    private ImageIcon obstacleImage = new ImageIcon("Images/Obstacle.png");
    private ImageIcon goalImage = new ImageIcon("Images/Goal.png");
    private ImageIcon keyTileImage = new ImageIcon("Images/KeyTile.png");
    private Dimension dimension;
    private String text = "";

    public VisibleTile(Dimension dimension) {
        super();
        this.dimension = dimension;
        setIcon(resizeImage(tileImage));
        setFont(new Font("Arial", Font.PLAIN, dimension.height/3));
        setPreferredSize(dimension);   
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawString(text, dimension.width/3, dimension.height/2);
    }

    public void setObstacle() {
        setIcon(resizeImage(obstacleImage));
        setFont(new Font("Arial", Font.PLAIN, dimension.height/3));
    }

    public void setKeyTile() {
        setIcon(resizeImage(keyTileImage));
        setFont(new Font("Arial", Font.PLAIN, dimension.height/3));
    }

    public void setGoal() {
        setIcon(resizeImage(goalImage));
        setFont(new Font("Arial", Font.PLAIN, dimension.height/3));
    }

    public void setVisited(boolean visited, int stepNum) {
        if (visited) this.text = "" + stepNum;
        else this.text = "";
        repaint();
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {setCursor(new Cursor(Cursor.HAND_CURSOR));}
    public void mouseExited(MouseEvent e) {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public ImageIcon resizeImage(ImageIcon imageIcon) {
        Image image = imageIcon.getImage(); // transform it 
        Image newImage = image.getScaledInstance(dimension.width, dimension.height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        return new ImageIcon(newImage);  // transform it back
    }
}