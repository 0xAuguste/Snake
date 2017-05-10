import java.awt.*;
import javax.swing.*;
public class Board extends JPanel{
	public Board(){
		JFrame board = new JFrame();
		board.setSize(1200, 822);
		board.add(this);
		board.setVisible(true);
		board.setBackground(Color.white);
	}	
	public static void main(String[] args){
		new Board();
	}
	private int x = 30;
    private int y = 30;
    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,1200,800);
        g.setColor(Color.blue);
        g.fillRect(0, 0, 30, 800);
        g.fillRect(0, 0, 1200, 30);
        g.fillRect(1170, 0, 30, 800);
        g.fillRect(0, 770, 1200, 30);
        g.setColor(Color.black);
        g.fillRect(x,y,30,30);
    }

    public void moveRight(){
        x+=30;
        repaint();
    }
    
    public void moveLeft(){
        x-=30;
        repaint();
    }
    
    public void moveDown(){
        y+=30;
        repaint();
    }
    
    public void moveUp(){
        y-=30;
        repaint();
    }
}