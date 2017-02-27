/*By Victor Riveros*/
package pong;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
public class PingPongTable extends JPanel implements GameConstants {
    
    JLabel label;
    public Point point = new Point(0,0);
    public int ComputerRacket_X = 15;
    public int computerRacket_Y = COMPUTER_RACKET_Y_START;
    private int kidRacket_Y = KID_RACKET_Y_START;
    Dimension preferredSize = new Dimension(TABLE_WIDTH, TABLE_HEIGHT);
    private int ballX = BALL_START_X;
    private int ballY = BALL_START_Y;
    private boolean red;
    private boolean white; //for powerup
    private int hits = 1;
    private int counter = 0;
    private int powerUpX = 0;
    private int powerUpY = 0;
    private int rand1;
    private int rand2;
    private boolean taken = false;
    PingPongTable(){
        PingPongGameEngine gameEngine = new PingPongGameEngine(this);
        //Listen to mouse clicks to show coordinates
        addMouseListener(gameEngine);
        //Listen to mouse movements to move the racket
        addMouseMotionListener(gameEngine);
    }
    public Dimension getPreferredSize(){
        return preferredSize;
    }
    void addPanelToFrame(Container container){
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(this);
        //label = new JLabel("Click to see coordinates");
        //container.add(label);
        label = new JLabel ("Score Computer: 0 Player: 0 Press 'S' to serve. Use arrow keys to control your paddle. 'O' to load and 'X' to save.");
        container.add(label);
    }
    /*Sets the area where the powerup can spawn randomly*/
    public void setRandomNumbers(){
        Random rand = new Random();
        rand1 = 240 + rand.nextInt(481);
        rand2 = 165 + rand.nextInt(331);
    }
    /*Message text*/
    public void setMessageText(String text){
        String labelText = text;
        label.setText(labelText);
        repaint();
    }
    /*Reads what the color of the board should be from the game engine class*/
    public void checkRed(boolean check){
        red = check;
    }
    /*Sees whether or not the ball has hit the powerup*/
    public void checkTaken(boolean check){
        taken = check;
    }
    /*Increments everytime the the computer or kid hits the ball*/
    public void addHit(){
        hits++;
    }
    /*Clears the hits when a round is over*/
    public void clearHits(){
        hits = 0;
    }
    /*Reads a bool from game engine in charge of powerup blinking*/
    public void chekcWhite(boolean check){
        white = check;
    }
    /*Returns powerup X coordinate*/
    public int returnPowerUpX(){
        return powerUpX;
    }
    /*Returns powerup Y coordinate*/
    public int returnPowerUpY(){
        return powerUpY;
    }
    public void resetPowerUps(){        //in PingPongTable
        powerUpX = 0;
        powerUpY = 0;
    }
    /*In charge of painting board, rackets, ball, and powerup*/
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Color myColor;
        
        if(red){
            myColor = Color.red;
        }
        else{
            myColor = Color.blue;
        }
        
        g.setColor(myColor);        
        g.fillRect(0,0,TABLE_WIDTH,TABLE_HEIGHT);
        
        if((hits >= 3)&&(!taken)){                      //POWERUP
            powerUpX = rand1;
            powerUpY = rand2;
            Color myColor2 = Color.white;
            if(white){
                myColor2 = Color.white;
            }
            else{
                myColor2 = myColor;
            }
            g.setColor(myColor2);
            g.fillRect(powerUpX,powerUpY,40,40);
        }
        //paint the right racket
        g.setColor(Color.white);
        g.fillRect(KID_RACKET_X_START, kidRacket_Y, 10, 100);
        
        //paint the left racket
        g.setColor(Color.white);
        g.fillRect(COMPUTER_RACKET_X,computerRacket_Y,10,100);
        
        //paint the ball
        g.setColor(Color.white);
        g.fillOval(ballX,ballY,20,20);
        
        g.setColor(Color.white);
        g.drawRect(10,10,940,640);
        g.drawLine(480,10,480,650);//480
    }
    /*Paint method*/
    public void paint(Graphics g,int color){
        super.paintComponent(g);
        if(color == 2){
            Color myColor = Color.white;
            g.setColor(myColor);
            g.fillRect(0,0,TABLE_WIDTH,TABLE_HEIGHT);
        }
        else if(color == 3){
            Color myColor = Color.red;
            g.setColor(myColor);
            g.fillRect(0,0,TABLE_WIDTH,TABLE_HEIGHT);
        }
    }
    /*Sets kid racket's X coordinate*/
    public void setKidRacket_Y(int xCoordinate){
        this.kidRacket_Y = xCoordinate;
    }
    /*Gets kid racket's Y Coordinate*/
    public int getKidRacket_Y(int xCoordinate){
        return kidRacket_Y;
    }
    /*Main method*/
    public static void main(String[] args){
        JFrame f = new JFrame("Ping Pong Table");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setResizable(false);
        PingPongTable table = new PingPongTable();
        table.addPanelToFrame(f.getContentPane());
        f.pack();
        f.setVisible(true);
    }
    /*Sets the computer racket's Y coordinate*/
    public void setComputerRacket_Y(int yCoordinate){
        this.computerRacket_Y = yCoordinate;
        repaint();
    }
    /*Gets computer racket's Y coordinate*/
    public int getComputerRacket_Y(){
        return computerRacket_Y;
    }
    /*Sets ball position*/
    public void setBallPosition(int xPos, int yPos){
        ballX = xPos;
        ballY = yPos;
        repaint();
    }
}
