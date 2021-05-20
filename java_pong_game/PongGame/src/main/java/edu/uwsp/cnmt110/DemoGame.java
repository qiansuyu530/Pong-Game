package edu.uwsp.cnmt110;

import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A demo / sample game that shows the minimum methods necessary.
 *  DO NOT USE THIS IN YOUR GAME. IT IS SIMPLY PROVIDED AS AN EXAMPLE
 */
public class DemoGame extends Game {
  private final int MAX_SPRITES = 500;
  private Timer timer;

  /**
   * Creates an instance of the Game class.
   *
   * @param width  The width of the window
   * @param height The height of the window.
   */
  public DemoGame(int width, int height) {
    super("My Demo", width, height);
    displayRandomMessages();
  }

  @Override
  public void getInput(InputEvent[] events, long interval) {
    // If user presses 'r', we'll clear all the sprites and start over.
    for(InputEvent event: events) {
      if (event instanceof KeyEvent) {
        switch (((KeyEvent) event).getKeyChar()) {
          case 'r':
            clearSprites();
            initialize();
            displayRandomMessages();
            break;
          case ' ':
            initialize();
            break;
        }
      }
      else if (event instanceof MouseEvent) {
        //((MouseEvent) event).getX();
      }
    }
  }

  @Override
  public boolean updatePosition(Sprite sprite, long interval) {
    if (((sprite.getX() - sprite.getWidth() / 2) < 0) ||
        ((sprite.getX() + sprite.getWidth() / 2) > getWidth())) {
      sprite.bounceX();
    }
    return false;
  }

  /**
   * Displays random messages to the screen.
   * Provides an example of how to use the setMessage() and clearMessage() methods.
   */
  private void displayRandomMessages() {
    if (timer != null) timer.cancel();
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      int countDown = 3;
      @Override
      public void run() {
        String message = null;
        switch(countDown) {
          case 0:
            message = "GO! GO! GO!";
            break;
          case 1:
            message = "<space> for MORE!";
            break;
          case 2:
            message = "<r> to RESTART!";
            break;
          case 3:
            message = "Keyboard Controls";
            break;
          default:
            timer.cancel();
        }

        if ((message == null) || (message.isEmpty())) {
          clearMessage();
        }
        else {
          setMessage(message);
        }
        countDown--;
      }
    }, 0, 1000);
  }

  @Override
  public void initialize() {
    // Randomly add sprites to the game / screen
    for(int index = 0; index < MAX_SPRITES; index++) {
      Sprite sprite = new RandomSprite(getWidth(), getHeight());
      addSprite(sprite);
    }
  }
}
