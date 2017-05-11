import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Board extends JPanel implements KeyListener
{
    public Board(){
        addKeyListener(this);
        setFocusable(true);
        JFrame board = new JFrame();
        board.setSize(1200, 822);
        board.add(this);
        board.setVisible(true);
    }   
    public static void main(String[] args){
    	new Board();
    }
    private int x = 20;
    private int y = 20;
    private int currentDirection;
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
    }
    
    public void moveLeft(){
        x-=20;
    }
    
    public void moveDown(){
        y+=20;
    }
    
    public void moveUp(){
        y-=20;
    }
    
    public void keyPressed(KeyEvent e){
        if(currentDirection == 0){
            currentDirection = e.getKeyCode();
        }
        else if(currentDirection == KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT){
            currentDirection = e.getKeyCode();
        }
        else if(currentDirection == KeyEvent.VK_RIGHT && e.getKeyCode() != KeyEvent.VK_LEFT){
            currentDirection = e.getKeyCode();
        }
        else if(currentDirection == KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_UP){
            currentDirection = e.getKeyCode();
        }
        else if(currentDirection == KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_DOWN){
            currentDirection = e.getKeyCode();
        }
        move();
    }
    
    public void keyReleased(KeyEvent e){}
    
    public void keyTyped(KeyEvent e){}
    
    public void move(){
        if(currentDirection == KeyEvent.VK_LEFT)moveLeft();
        else if(currentDirection == KeyEvent.VK_RIGHT)moveRight();
        else if(currentDirection == KeyEvent.VK_DOWN)moveDown();
        else if(currentDirection == KeyEvent.VK_UP)moveUp();
        repaint();
    }
    
    public void game(){
        while(true){
            move();
        }
    }
}
