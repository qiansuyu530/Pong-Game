package edu.uwsp.cnmt110;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

enum GameState {
    WelcomeScreen, Started, GameOver,
}

public class PongGame extends Game {

    DrawableSprite player, computer;
    GameState curState;

    final int BarWidth = 15; // player/computer bar width
    final int BarHeight = 70; // player/computer bar height
    final int BallWidth = 25; // ball width
    final int BallHeight = 25; // ball height
    final int MaxAngle = 360; // maximum angle
    final int MinAngle = 0; // minimum angle
    final int MinSpeed = 1; // minimum speed of balls
    final int MaxSpeed = 5; // maximum speed of balls

    /**
     * Construct a Pong game window with given width and height.
     *
     * @param width  window width
     * @param height window height
     */
    public PongGame(int width, int height) {
        super("Pong Game", width, height);
        curState = GameState.WelcomeScreen;
        setMessage("<ENTER> to START");
        setLocationRelativeTo(null); // place the window at center of the screen
    }

    /**
     * Do a count down before starting or restarting the game.
     */
    private void startCountDown() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int countDown = 3;

            @Override
            public void run() {
                switch (countDown) {
                case 3:
                    setMessage("3");
                    break;
                case 2:
                    setMessage("2");
                    break;
                case 1:
                    setMessage("1");
                    break;
                default:
                    clearMessage();
                    clearSprites();
                    initialize();
                    curState = GameState.Started;
                    timer.cancel();
                    break;
                }
                countDown--;
            }
        }, 0, 1500);
    }

    @Override
    public void getInput(InputEvent[] events, long interval) {
        for (InputEvent event : events) {
            // key press event
            if (event instanceof KeyEvent) {
                char key = ((KeyEvent) event).getKeyChar();
                switch (key) {
                case 'r':
                case 'R':
                    if (curState != GameState.Started && curState != GameState.WelcomeScreen) {
                        // restart the game
                        startCountDown();
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (curState == GameState.Started) {
                        addSprite(getNewBall());
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (curState == GameState.WelcomeScreen) {
                        startCountDown();
                    }
                    break;
                default:
                    break;
                }
            }

            // mouse events
            if (event instanceof MouseEvent && curState == GameState.Started) {
                // move the player bar according to the mouse y coordinate
                int mouseY = ((MouseEvent) event).getY();
                Insets insets = getInsets();
                int playerBarY = mouseY - insets.top;
                if (mouseY <= ((BarHeight / 2) + insets.top)) {
                    playerBarY = BarHeight / 2;
                } else if ((mouseY + BarHeight / 2) >= (getHeight() - insets.bottom)) {
                    playerBarY = getHeight() - (BarHeight / 2) - insets.bottom - insets.top - 1;
                }
                player.moveTo(player.getX(), playerBarY);

                if (((MouseEvent) event).getButton() == MouseEvent.BUTTON1) {
                    // add a new ball
                    addSprite(getNewBall());
                }
            }
        }
    }

    @Override
    public boolean updatePosition(Sprite sprite, long interval) {
        DrawableSprite dSprite = (DrawableSprite) sprite;
        if (dSprite.getName() == null) {
            // move the ball anyway
            dSprite.move(interval);

            // check if its hit by upper or lower boundary
            if ((dSprite.getY() - dSprite.getHeight() / 2) <= 0 || (dSprite.getY() + dSprite.getHeight() / 2
                    + getInsets().bottom + getInsets().top + 1) >= getHeight()) {
                dSprite.bounceY();
            }

            // check if its crossing player's boundary
            // update player input only if game is running
            if (curState == GameState.Started) {
                if ((player.getX() + BarWidth / 2) >= (dSprite.getX() - dSprite.getWidth() / 2)) {
                    if (dSprite.collidesWith(player)) {
                        // bounce the ball
                        dSprite.bounceX();
                    } else {
                        setGameover(true);
                        removeSprite(dSprite);
                        curState = GameState.GameOver;

                        // start a timer to show the restart game message eventually
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                setMessage("<r> to RESTART");
                                timer.cancel();
                            }
                        }, 2500, 1000);
                    }
                }
            }

            // check if its crossing computer's boundary
            if ((computer.getX() - BarWidth / 2) <= (dSprite.getX() + dSprite.getWidth() / 2)) {
                // move the computer bar to right position
                int computerY = dSprite.getY();
                Insets insets = getInsets();
                if (computerY <= ((BarHeight / 2) + insets.top)) {
                    computerY = BarHeight / 2;
                } else if ((computerY + BarHeight / 2) >= (getHeight() - insets.bottom)) {
                    computerY = getHeight() - (BarHeight / 2) - insets.bottom - insets.top - 1;
                }
                computer.moveTo(computer.getX(), computerY);

                // bounce the ball anyway
                dSprite.bounceX();
            }
        }
        return true;
    }

    /**
     * Generates a random Color
     * 
     * @return A random color.
     */
    private Color getRandomColor() {
        return (new Color(getRandom().nextInt(255), getRandom().nextInt(255), getRandom().nextInt(255)));
    }

    /**
     * Get a randomly constructed ball with new color and positioned at the center x
     * of the screen. The ball will have a random speed and angle.
     */
    DrawableSprite getNewBall() {
        int speed = getRandom().nextInt(MaxSpeed - MinSpeed + 1) + MinSpeed;
        float angle = -1.0f;
        do {
            angle = (float) getRandom().nextInt(MaxAngle - MinAngle + 1) + MinAngle;
        } while ((angle < 5 && angle > 88) && (angle < 92 && angle > 175) && (angle < 185 && angle > 268)
                && (angle < 272 && angle > 355));
        Color color = getRandomColor();
        int yMax = getHeight() - BallHeight / 2 - getInsets().top - getInsets().bottom - 1;
        int yMin = BallHeight / 2 + getInsets().top;
        int ballY = getRandom().nextInt(yMax - yMin + 1) + yMin;
        DrawableSprite ball = new DrawableSprite(getWidth() / 2 - BallWidth / 2 - getInsets().left, ballY, BallWidth,
                BallHeight, angle, speed);
        ball.setColor(color);
        return ball;
    }

    @Override
    public void initialize() {
        // add player bar
        player = new DrawableSprite(20 + BarWidth / 2, getHeight() / 2 - getInsets().top / 2, BarWidth, BarHeight, 0,
                1);
        player.setColor(new Color(0, 255, 0));
        player.setName("PLAYER");
        addSprite(player);

        // add computer bar
        computer = new DrawableSprite(getWidth() - 20 - BarWidth / 2 - getInsets().right * 2,
                getHeight() / 2 - getInsets().top / 2, BarWidth, BarHeight, 0, 1);
        computer.setColor(new Color(0, 0, 255));
        computer.setName("COMPUTER");
        addSprite(computer);

        addSprite(getNewBall());
    }
}
