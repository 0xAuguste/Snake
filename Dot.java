public class Dot{
    private int xCoord;
    private int yCoord;
    
    public Dot(int x, int y){
        xCoord = x;
        yCoord = y;
    }
    
    public int getX(){
        return xCoord;
    }
    
    public int getY(){
        return yCoord;
    }
    
    public void moveRight(){
        xCoord+=20;
    }

    public void moveLeft(){
        xCoord-=20;
    }

    public void moveDown(){
        yCoord+=20;
    }

    public void moveUp(){
        yCoord-=20;
    }
}
