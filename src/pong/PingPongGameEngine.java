/*By Victor Riveros*/
package pong;
import sun.audio.*;
import java.io.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

/*Instances of the class*/
public class PingPongGameEngine implements Runnable,MouseListener,MouseMotionListener,GameConstants,KeyListener{
    private PingPongTable table;
    public int kidRacket_Y = KID_RACKET_Y_START;
    public int computerRacket_Y = COMPUTER_RACKET_Y_START;
    Thread worker;
    private int ballX = BALL_START_X;
    private int ballY = BALL_START_Y;
    private boolean ballServed = false;
    private boolean movingLeft = true;
    private boolean gameOver = false;
    private int kidScore = 0;
    private int computerScore = 0;
    private int BALL_INCREMENT_Y;
    private boolean red = false;
    private boolean canServe;
    private int counter = 0;
    private int BALL_INCREMENT;
    /*Constructor*/
    PingPongGameEngine(PingPongTable table){
        this.table = table;
        worker = new Thread(this);
        worker.start();
        table.addKeyListener(this);
    }
    /*Controls the color of the table as a boolean*/
    public boolean returnRed(){
        return red;
    }
    /*Reads the keys pressed*/
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case 78:
                //new 'N'
                gameOver = false;
                ballServed = false;
                table.setKidRacket_Y(KID_RACKET_Y_START);
                ballY = BALL_START_Y;
                ballX = BALL_START_X;
                table.setBallPosition(ballX, ballY);
                table.setComputerRacket_Y(KID_RACKET_Y_START);
                kidScore = 0;
                computerScore = 0;
                table.clearHits();
                table.setMessageText("Score Computer: " + computerScore + " Player: " + kidScore + " Press 'S' to serve. Use arrow keys to control your paddle. 'O' to load and 'X' to save.");
                table.repaint();
                break;
            case 81:
                //end 'Q'
                System.exit(0);
                break;
            case 83:
                if(!ballServed){
                    kidServe();
                }
                    break;
            case 38: //up
                if(kidRacket_Y <= TABLE_TOP){
                }
                else{   
                    if(ballServed){
                        kidRacket_Y -= 35;
                        table.setKidRacket_Y(kidRacket_Y);
                        table.repaint();
                    }  
                }   
                break;
            case 40: //down
                if(kidRacket_Y >= TABLE_BOTTOM){
                }
                else{
                    if(ballServed){
                        kidRacket_Y += 35;
                        table.setKidRacket_Y(kidRacket_Y);
                        table.repaint();
                    }  
                }   break;
            case 79:    //load
                File inputFile = new File("score.txt");
                try {
                    Scanner input = new Scanner(inputFile);
                    while(input.hasNextLine()){
                        String string = input.nextLine();
                        String[] split = string.split("-");
                        String player = split[0];
                        String computer = split[1];
                        kidScore = Integer.parseInt(player);
                        computerScore = Integer.parseInt(computer);
                        table.setMessageText("Loading last game. Computer: "+ computerScore +" Player: "+kidScore);
                    }
                } 
                catch(FileNotFoundException | NumberFormatException z){
                    System.out.println("The input file \"score.txt\" was not found.");
                    System.out.println(z.toString());
                }
                table.repaint();
                break;
            case 88:
                File output = new File("score.txt");
                try{
                    if(!ballServed){
                    table.setMessageText("Game Saved.");
                    PrintWriter out = new PrintWriter(output);
                    String scoreSaved = kidScore+"-"+computerScore;
                    out.println(scoreSaved);
                    out.close();
                    }
                } catch(Exception x){
                    System.out.println("Error encountered " + x.toString());
                    }
                break;
            default:
            break;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
 //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
 //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
 //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void mouseDragged(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*What happens when the ball is served*/
    public void kidServe(){
        if(!gameOver){
        table.resetPowerUps();      //in kidServe();
        BALL_INCREMENT = 5;
        table.checkTaken(false);
        table.setRandomNumbers();
        ballServed = true;
        movingLeft = true;
        table.setBallPosition(BALL_START_X, BALL_START_Y);
        Random rand = new Random();
        BALL_INCREMENT_Y = (-8) + rand.nextInt(17);
        }
    }
    @Override
     public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int mouse_Y = e.getY();
        //If a mouse is above the kid's racket
        //and the racket did not go over the table top
        // and move it up, otherwise move it down
        if (mouse_Y < kidRacket_Y && kidRacket_Y > TABLE_TOP) {
            kidRacket_Y -= RACKET_INCREMENT;
        } else if (kidRacket_Y < TABLE_BOTTOM) {
            kidRacket_Y += RACKET_INCREMENT;
        }
        //Set the new position of the racket table class
        table.setKidRacket_Y(kidRacket_Y);
        table.repaint();
    }
     /*Where the game runs*/
    @Override
    public void run() {
        boolean down = true;
        boolean turn = true;
        Random rand = new Random();
        while(true){
            if(ballServed){
                if(movingLeft){
                    if((ballX >= GameConstants.TABLE_LEFT)&&(ballX <= GameConstants.TABLE_RIGHT)){
                        ballX -= BALL_INCREMENT;
                        ballY += BALL_INCREMENT_Y;
                        table.setBallPosition(ballX, ballY);
                        
                        if((ballY < GameConstants.TABLE_TOP)||(ballY > 630)){
                            BALL_INCREMENT_Y = BALL_INCREMENT_Y * (-1);
                        }
                    }
                    else if((ballX < GameConstants.TABLE_LEFT)&&(ballX <= GameConstants.TABLE_RIGHT)){
                        if((ballY < computerRacket_Y)||(ballY > (computerRacket_Y + 100))){
                            kidScore += 1;
                            table.clearHits();
                            if(kidScore == 11){
                                    table.setMessageText("Game Over! You have won. Press 'N for new game.");
                                    gameOver = true;
                            }
                            else{
                            table.setMessageText("Score Computer: " + computerScore + " Player: " + kidScore + " Press 'S' to serve. Use arrow keys to control your paddle. 'O' to load and 'X' to save.");
                            }
                            ballServed = false;
                            table.setKidRacket_Y(KID_RACKET_Y_START);
                            kidRacket_Y = KID_RACKET_Y_START;
                            ballY = BALL_START_Y;
                            ballX = BALL_START_X;
                            table.setBallPosition(ballX, ballY);
                            table.setComputerRacket_Y(COMPUTER_RACKET_Y_START);
                            table.repaint();
                            table.requestFocus();     
                        }
                        else{
                        ballX = GameConstants.TABLE_LEFT;
                        BALL_INCREMENT_Y = (-8) + rand.nextInt(17);
                        movingLeft = false;
                        table.checkRed(true);
                        table.addHit();
                        }
                    }
                }
                else{
                    if((ballX >= GameConstants.TABLE_LEFT)&&(ballX < GameConstants.TABLE_RIGHT)){
                        ballX += BALL_INCREMENT;
                        ballY += BALL_INCREMENT_Y;
                        table.setBallPosition(ballX, ballY);
                        if((ballY < GameConstants.TABLE_TOP)||(ballY > 630)){
                            BALL_INCREMENT_Y = BALL_INCREMENT_Y * (-1);
                        }
                    }
                    else if((ballX > GameConstants.TABLE_LEFT)&&(ballX >= GameConstants.TABLE_RIGHT)){
                        if((ballY < kidRacket_Y)||(ballY > (kidRacket_Y + 100))){
                            computerScore += 1;
                            table.clearHits();
                            if(computerScore == 11){
                                table.setMessageText("Game Over! Computer has won. Press 'N' for new game.");
                                gameOver= true;   
                            }
                            else{
                            table.setMessageText("Score Computer: " + computerScore + " Player: " + kidScore + " Press 'S' to serve. Use arrow keys to control your paddle.");
                            }
                            ballServed = false;
                            table.setKidRacket_Y(KID_RACKET_Y_START);
                            kidRacket_Y = KID_RACKET_Y_START;
                            ballY = BALL_START_Y;
                            ballX = BALL_START_X;
                            table.setBallPosition(ballX, ballY);
                            table.setComputerRacket_Y(COMPUTER_RACKET_Y_START);
                            table.repaint();
                            table.requestFocus();
                        }
                        ballX = GameConstants.TABLE_RIGHT;
                        BALL_INCREMENT_Y = (-10) + rand.nextInt(21);
                        movingLeft = true;
                        table.checkRed(false);
                        table.addHit();
                    }
                }
                if((ballX > (table.returnPowerUpX() - 40))&&(ballX < (table.returnPowerUpX() + 40))){         //POWERUP GETTING TAKEN
                    if((ballY > (table.returnPowerUpY() - 40))&&(ballY < (table.returnPowerUpY() + 40))){         //SHOULD ALL BE 5
                        BALL_INCREMENT = 15;
                        table.checkTaken(true);
                    }
                }
                if(ballY < (table.getComputerRacket_Y() + 50)){
                    if((table.getComputerRacket_Y() > GameConstants.TABLE_TOP)&&(table.getComputerRacket_Y() > GameConstants.TABLE_BOTTOM)){
                        computerRacket_Y = table.getComputerRacket_Y() - RACKET_INCREMENT;
                        down = false;
                    }
                    else{
                        computerRacket_Y = table.getComputerRacket_Y() - 6;     //AI DIFFICULTY
                    }
                    table.setComputerRacket_Y(computerRacket_Y);
                }
                else{ 
                    if((computerRacket_Y < GameConstants.TABLE_TOP)&&(computerRacket_Y < GameConstants.TABLE_BOTTOM)){
                        computerRacket_Y = table.getComputerRacket_Y() + GameConstants.RACKET_INCREMENT + GameConstants.RACKET_INCREMENT;
                        down = true;
                    }
                    else{
                        computerRacket_Y = table.getComputerRacket_Y() + 6; 
                    }
                    table.setComputerRacket_Y(computerRacket_Y);
                }

                try {
                    Thread.sleep(12);
                    counter += 10;
                    if((counter % 100) == 0){
                        table.chekcWhite(false);
                    }
                    if(((counter % 200) == 0)){
                        table.chekcWhite(true);
                    }
                }
                catch(InterruptedException ex){
                    Logger.getLogger(PingPongGameEngine.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
            else{
            }
            table.requestFocus();
        }
    }
}



