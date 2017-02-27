/*By Victor Riveros*/
package pong;
/*Game Constants*/
public interface GameConstants {
    public final int TABLE_WIDTH = 960;
    public final int TABLE_HEIGHT = 660;
    public final int KID_RACKET_Y_START = 280;
    public final int KID_RACKET_X_START = 935;
    public final int TABLE_TOP = 12;
    public final int TABLE_BOTTOM = 550;
    public final int RACKET_INCREMENT = 2;
    public final int COMPUTER_RACKET_X = 15;
    public final int COMPUTER_RACKET_Y_START = 280;
    public final int BALL_INCREMENT = 5;
    public final int BALL_MIN_X = 10;
//    public final int BALL_MIN_Y = BALL_INCREMENT;
    public final int BALL_MAX_X = TABLE_WIDTH - 30;
    public final int BALL_MAX_Y = TABLE_HEIGHT - BALL_INCREMENT;
    public final int BALL_START_X = 910;
    public final int BALL_START_Y = 315;
    public final int BALL_CENTER = 5;
    public final int TABLE_LEFT = 30;
    public final int TABLE_RIGHT = 915; 
}
