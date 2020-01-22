import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int brickscount = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private Mapping map;

    public GamePlay() {
        map = new Mapping(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();

    }

    public void paint(Graphics g) {
        //setting Background color
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //settinng bordders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //Scores
        g.setColor(Color.red);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+ score,590,30);

        //setting platform Disk
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //setting Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if(brickscount <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Congo$$ You WON "+ score,260,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter To Restart ",230,350);
        }

        if(ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("GAME OVER, You Score-> "+ score,190,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter To Restart ",230,350);
        }

        //drawing map
        map.draw((Graphics2D)g);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();
        if(play) {
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
                ballYdir = - ballYdir;
            }

            A: for(int i=0;i<map.map.length;i++) {
                for(int j=0;j<map.map[0].length;j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j*map.brickWidth+80;
                        int brickY = i*map.brickHeight+50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickvalue(0,i,j);
                            brickscount--;
                            score = score + 10;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width ) {
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX = ballposX + ballXdir;
            ballposY = ballposY + ballYdir;
            if(ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if(ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if(ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            }
            else {
                moveRight();
            }
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                brickscount = 21;
                map = new Mapping(3,7);
                repaint();
            }
        }
    }

    private void moveLeft() {
        play = true;
        playerX = playerX - 20;
    }

    private void moveRight() {
        play = true;
        playerX = playerX + 20;
    }


}
