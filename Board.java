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
    private int numMoved = 0;

    Color head = new Color(10,115,37);
    Color darkBrown = new Color(135,115,85);
    Color lightBrown = new Color(178,150,110);
    Color background = new Color(150,210,140);
    Color border = new Color(75,63,45);
    Color apple = new Color(175,45,22);
    
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
        //background
        g.setColor(background);
        
        //border
        g.fillRect(0,0,1200,800);
        g.setColor(border);
        g.fillRect(0, 0, 20, 800);
        g.fillRect(0, 0, 1200, 20);
        g.fillRect(1180, 0, 20, 800);
        g.fillRect(0, 780, 1200, 20);
        g.setColor(apple);
        g.fillRect(appleX + 1, appleY + 1, 18, 18);
        
        //snake body
        int counter = 5;
        for(Dot square : dots){
            if((counter / 3) % 2 == 0) {
                g.setColor(lightBrown);
            }else{g.setColor(darkBrown);}
            g.fillRect(square.getX() + 1,square.getY() + 1,18,18);
            counter++;
        }
        
        //snake head
        g.setColor(head);
        g.fillRect(dots.get(0).getX() + 1,dots.get(0).getY() + 1, 18, 18);
        
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.PLAIN, 20));
        g.drawString("Score: " + dots.size(), 3, 18);
        
        //game over
        if(gameRunning == false){
            g.setColor(apple);
            g.setFont(new Font("Helvetica", Font.PLAIN, 80));
            g.drawString("Game Over", 400, 400);
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
            for(int index = 0; index < dots.size(); index++){
                if(dots.get(index).getX() == xApple && dots.get(index).getY() == yApple){
                    xApple = assignX();
                    yApple = assignY();
                    index = 0;
                }
            }
            temp = false;
        }
        appleX = xApple;
        appleY = yApple;
    }
    
    public void checkCollision(){
        int headX = dots.get(0).getX();
        int headY = dots.get(0).getY();
        if(headX == 0 || headX == 1180 || headY == 0 || headY == 780){
            gameRunning = false;
            repaint();
            return;
        }
        
        for(int index = 1; index < dots.size(); index++){
            if(dots.get(index).getX() == headX && dots.get(index).getY() == headY){
                gameRunning = false;
            }
        }
    }

    public void checkApple(){
        if(appleX == dots.get(0).getX() && appleY == dots.get(0).getY()){
            numAdd = 3;
            setApple();
        }
    }

    public void keyPressed(KeyEvent e){
        int keyPressed = e.getKeyCode();
        
        if(numMoved == 0){move();}
        
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
        numMoved = 0;
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
        numMoved++;
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
            checkCollision();
        }
    }
}
