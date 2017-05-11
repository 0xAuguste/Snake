import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Board extends JPanel // implements ActionListener
{
    public Board(){
        JFrame board = new JFrame();
        board.setSize(1200, 822);
        board.add(this);
        board.setVisible(true);
    }   

    private int x = 20;
    private int y = 20;
    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,1200,800);
        g.setColor(Color.blue);
        g.fillRect(0, 0, 20, 800);
        g.fillRect(0, 0, 1200, 20);
        g.fillRect(1180, 0, 20, 800);
        g.fillRect(0, 780, 1200, 20);
        g.setColor(Color.black);
        g.fillRect(x,y,20,20);
    }

    public void moveRight(){
        x+=20;
        repaint();
    }
    
    public void moveLeft(){
        x-=20;
        repaint();
    }
    
    public void moveDown(){
        y+=20;
        repaint();
    }
    
    public void moveUp(){
        y-=20;
        repaint();
    }
    
    
}
