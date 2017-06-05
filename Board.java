import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel implements KeyListener, ActionListener
{
    JFrame board = new JFrame();

    private int up = KeyEvent.VK_UP;
    private int down = KeyEvent.VK_DOWN;
    private int left = KeyEvent.VK_LEFT;
    private int right = KeyEvent.VK_RIGHT;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private int currentDirection = 0;
    boolean gameRunning = false;
    private int appleX;
    private int appleY;
    private int numAdd = 0;
    private int numMoved = 0;
    private boolean menu = true;
    private int difficulty = 70;

    Color head = new Color(10,115,37);
    Color darkBrown = new Color(135,115,85);
    Color lightBrown = new Color(178,150,110);
    Color background = new Color(150,210,140);
    Color border = new Color(75,63,45);
    Color apple = new Color(175,45,22);

    TimerTask timerTask = new TimerTask(){
            public void run() {
                if(gameRunning){
                    move();
                    checkApple();
                    checkCollision();
                }
            }
        };
    Timer timer = new Timer(true);

    JButton easy = new JButton("Easy");
    JButton medium = new JButton("Medium");
    JButton hard = new JButton("Hard");
    JButton start = new JButton("Start Game");
    JLabel label = new JLabel("Choose Your Difficulty");
    public Board(){
        addKeyListener(this);
        setFocusable(true);
        this.add(label);
        this.add(easy);
        easy.addActionListener(this);
        this.add(medium);
        medium.addActionListener(this);
        this.add(hard);
        hard.addActionListener(this);
        this.add(start);
        start.addActionListener(this);

        board.pack();
        board.setSize(board.getInsets().left + 1200 + board.getInsets().right,
            board.getInsets().top + 800 + board.getInsets().bottom);
        board.add(this);
        board.setResizable(false);
        dots.add(new Dot(assignX(),assignY()));
        board.setVisible(true);
        setApple();
    } 

    public static void main(String[] args){

    }

    public void paintComponent(Graphics g){
        if(menu){
            g.setColor(Color.white);
            g.fillRect(0,0,1200,800);
        }else {
            //gameplay
            //background
            g.setColor(background);
            g.fillRect(0,0,1200,800);
            //border
            g.setColor(border);
            g.fillRect(0, 0, 20, 800);
            g.fillRect(0, 0, 1200, 20);
            g.fillRect(1180, 0, 20, 800);
            g.fillRect(0, 780, 1200, 20);

            //apple
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
            if(!gameRunning){
                g.setColor(apple);
                g.setFont(new Font("Helvetica", Font.PLAIN, 80));
                g.drawString("Game Over", 400, 400);
                g.setFont(new Font("Helvetica", Font.PLAIN, 20));
                g.drawString("Press N to start a new game",480,450);
            }
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
            timer.cancel();
            timer.purge();
            repaint();
            return;
        }

        for(int index = 1; index < dots.size(); index++){
            if(dots.get(index).getX() == headX && dots.get(index).getY() == headY){
                gameRunning = false;
                timer.cancel();
                timer.purge();
            }
        }
    }

    public void checkApple(){
        if(appleX == dots.get(0).getX() && appleY == dots.get(0).getY()){
            numAdd = 3;
            setApple();
        }
    }

    public void newGame(){
        dots.clear();
        dots.add(new Dot(assignX(), assignY()));
        currentDirection = 0;
        numAdd = 0;
        setApple();
        this.repaint();
    }

    public void keyPressed(KeyEvent e){
        int keyPressed = e.getKeyCode();
        if(numMoved == 0 && gameRunning){move();}

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
        else if(keyPressed == KeyEvent.VK_Q){
            gameRunning = false;
            timer.cancel();
            timer.purge();
            repaint();
        }
        else if(keyPressed == KeyEvent.VK_P)timer.cancel();
        numMoved = 0;

        if(!gameRunning && keyPressed == KeyEvent.VK_N){
            menu = true;
            newGame();
            setDisplay();
        }
    }

    public void setDisplay(){
        easy.setVisible(menu);
        medium.setVisible(menu);
        hard.setVisible(menu);
        start.setVisible(menu);
        label.setVisible(menu);
    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        if(command == easy.getActionCommand()){difficulty = 100;}
        else if(command == medium.getActionCommand()){difficulty = 70;}
        else if(command == hard.getActionCommand()){difficulty = 50;}
        else if(command == start.getActionCommand()){
            menu = false;
            setDisplay();
            gameRunning = true;
            timer.scheduleAtFixedRate(timerTask, 0, difficulty);
        }
    }

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
}
