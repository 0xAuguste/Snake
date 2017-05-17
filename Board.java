import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
public class Board extends JPanel implements KeyListener
{
    private int up = KeyEvent.VK_UP;
    private int down = KeyEvent.VK_DOWN;
    private int left = KeyEvent.VK_LEFT;
    private int right = KeyEvent.VK_RIGHT;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private int currentDirection = 0;
    private boolean gameRunning = true;
    private int appleX;
    private int appleY;
    private int numAdd = 0;
    
    Color head = new Color(10,115,37);
    Color darkBrown = new Color(135,115,85);
    Color lightBrown = new Color(190,165,130);
    public Board(){
        addKeyListener(this);
        setFocusable(true);
        JFrame board = new JFrame();
        board.setSize(1200, 822);
        board.add(this);
        board.setVisible(true);
        dots.add(new Dot(assignX(),assignY()));
        setApple();
        game();
    }   

    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,1200,800);
        g.setColor(Color.blue);
        g.fillRect(0, 0, 20, 800);
        g.fillRect(0, 0, 1200, 20);
        g.fillRect(1180, 0, 20, 800);
        g.fillRect(0, 780, 1200, 20);
        g.setColor(Color.green);
        g.fillRect(appleX, appleY, 20, 20);
        
        //snake
        g.setColor(head);
        g.fillRect(dots.get(0).getX() + 1,dots.get(0).getY() + 1, 18, 18);
        for(int index = 1; index < dots.size();index++){
            if(index % 2 == 0) {
                g.setColor(lightBrown);
            }
            else{
                g.setColor(darkBrown);
            }
            g.fillRect(dots.get(index).getX() + 1,dots.get(index).getY() + 1,18,18);
        }
    }
    
    public int assignX(){
        int x = (int)(Math.random()*58 +1);
        return x*20;
    }
    
    public int assignY(){
        int y = (int)(Math.random()*38 +1);
        return y*20;
    }
    
    public void setApple(){
        boolean temp = true;
        int xApple = assignX();
        int yApple = assignY();
        while(temp){
            xApple = assignX();
            yApple = assignY();
            for(int length = 0; length < dots.size(); length++){
                if(dots.get(length).getX() == xApple && dots.get(length).getY() == yApple){
                    break;
                }
            }
            temp = false;
        }
        appleX = xApple;
        appleY = yApple;
    }

    public void checkApple(){
        if(appleX == dots.get(0).getX() && appleY == dots.get(0).getY()){
            numAdd = 2;
            setApple();
        }
    }
    
    public void increaseLength(){
        
    }

    public void keyPressed(KeyEvent e){
        int keyPressed = e.getKeyCode();
        if(currentDirection == left && (keyPressed == up || keyPressed == down)){
            currentDirection = keyPressed;
        }
        else if(currentDirection == right && (keyPressed == up || keyPressed == down)){
            currentDirection = keyPressed;
        }
        else if(currentDirection == down && (keyPressed == left || keyPressed == right)){
            currentDirection = keyPressed;
        }
        else if(currentDirection == up && (keyPressed == left || keyPressed == right)){
            currentDirection = keyPressed;
        }
        else if(currentDirection == 0 &&
            (keyPressed == up || keyPressed == down || keyPressed == left || keyPressed == right)){
            currentDirection = keyPressed;
        }
        else if(keyPressed == KeyEvent.VK_Q){gameRunning = false;}
    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}

    public void move(){
        int numDots = dots.size();
        if(currentDirection == left){
            dots.add(0, new Dot(dots.get(0).getX() - 20,dots.get(0).getY()));
        }
        else if(currentDirection == right){
            dots.add(0, new Dot(dots.get(0).getX() + 20,dots.get(0).getY()));
        }
        else if(currentDirection == down){
            dots.add(0, new Dot(dots.get(0).getX(),dots.get(0).getY() + 20));
        }
        else if(currentDirection == up){
            {dots.add(0, new Dot(dots.get(0).getX(),dots.get(0).getY() - 20));}
        }
        if(dots.size() == numDots+1 && numAdd <= 0)dots.remove(dots.size()-1);
        else{numAdd--;}
        repaint();
    }

    public void game(){
        while(gameRunning){
            try {
                Thread.sleep(70);
            }catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            move();
            checkApple();
        }
    }
    
    
}
