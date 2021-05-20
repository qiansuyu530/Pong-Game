package edu.uwsp.cnmt110;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a base Game class for implementation of a game with graphics
 * content using the java Swing framework.
 */
public abstract class Game extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
  private final String MESSAGE_GAME_OVER = "GAME OVER";

  private Random random;
  private int width;
  private int height;
  private boolean isRunning = false;
  private Screen screen;
  private CopyOnWriteArrayList<Sprite> sprites;
  private ConcurrentLinkedQueue<InputEvent> events;
  private boolean gameover;
  private String message;

  /**
   * Creates an instance of the Game class.
   * 
   * @param title  The title for the window
   * @param width  The width of the window
   * @param height The height of the window.
   */
  Game(String title, int width, int height) {
    super(title);

    this.width = width;
    this.height = height;
    this.screen = new Screen(this);
    this.sprites = new CopyOnWriteArrayList<Sprite>();
    this.events = new ConcurrentLinkedQueue<InputEvent>();
    this.random = new Random();

    getContentPane().add(screen);
    setSize(width, height);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // Register for mouse events
    addMouseListener(this);
    addMouseMotionListener(this);
    // Register for keyboard events
    addKeyListener(this);

    initialize();
  }

  /**
   * Releases memory and resources when object is being disposed.
   */
  protected void finalize() {
    removeMouseListener(this);
    removeMouseMotionListener(this);
    removeKeyListener(this);
  }

  /**
   * Retrieves the user input including both mouse and keyboard.
   * 
   * @param events   The collection of events that have occurred since the last
   *                 update. The most recent event is at the end of the array.
   * @param interval The amount of time that has passed since the last update.
   */
  public abstract void getInput(InputEvent[] events, long interval);

  /**
   * Updates the sprite position based on it's speed.
   * 
   * @param sprite   The sprite who's position needs to be updated.
   * @param interval The amount of time that has passed since the last update.
   * @return True if the position update was handled by the code. If false, the
   *         the sprites move operation is called to automatically update it's
   *         position.
   */
  public abstract boolean updatePosition(Sprite sprite, long interval);

  /**
   * Initializes the game sprites and configures the play area.
   */
  public abstract void initialize();

  /**
   * Retrieve a shared / common random generator.
   * 
   * @return The Random instance.
   */
  protected Random getRandom() {
    return random;
  }

  /**
   * The actual game / screen.
   */
  class Screen extends JPanel {
    long frames = 0;
    final long started = System.currentTimeMillis();
    long lastUpdate = 0;
    private Game game;

    /**
     * Creates an instance of the Screen class.
     * 
     * @param game The parent game that owns this screen
     */
    Screen(Game game) {
      this.game = game;
    }

    /**
     * Displays / draws the FPS to the lower corner of the screen.
     * 
     * @param g   The graphics / canvas to draw or paint on.
     * @param now The current date/time to use for calculation.
     */
    private void overlayStatistics(Graphics g, long now) {
      g.setColor(Color.LIGHT_GRAY);
      Font font = new Font("dialog", Font.PLAIN, 12);
      g.setFont(font);
      FontMetrics metrics = g.getFontMetrics(font);
      g.drawString(String.format("Statistics: (interval %dms, frames: %d, sprites: %d)", now - lastUpdate, frames,
          getSprites().length), 5, getHeight() - metrics.getHeight() / 2);
    }

    /**
     * Displays or overlays the specified message to the center of the screen.
     * 
     * @param message The message to display.
     * @param g       The graphics / canvas to draw to.
     */
    protected void overlayMessage(Graphics g, String message) {
      g.setColor(Color.ORANGE);

      Font font = new Font("helvetica", Font.PLAIN | Font.BOLD, 36);
      g.setFont(font);
      FontMetrics metrics = g.getFontMetrics(font);
      int width = metrics.stringWidth(message);

      g.drawString(message, getWidth() / 2 - width / 2, getHeight() / 2);
    }

    /**
     * Draws or renders the information.
     * 
     * @param g The graphics / canvas to draw to.
     */
    @Override
    public void paint(Graphics g) {
      long now = new Date().getTime();
      frames++;

      g.setColor(Color.BLACK);
      g.fillRect(0, 0, width, height);

      for (Sprite sprite : getSprites()) {
        if (sprite instanceof IDrawable) {
          ((IDrawable) sprite).paint(g);
        }
      }

      overlayStatistics(g, now);
      if ((message != null) && (!message.isEmpty())) {
        overlayMessage(g, message);
      }
      lastUpdate = now;
    }
  }

  /**
   * Indicates if gameplay has ended.
   * 
   * @return True if ended, false if otherwise.
   */
  protected boolean isGameover() {
    return (gameover);
  }

  /**
   * Sets / changes the gameover value.
   * 
   * @param gameover The value to set. Tre if game is ended.
   * @return The instance of Game so that subsequent calls can be chained
   *         together.
   */
  protected Game setGameover(boolean gameover) {
    this.gameover = gameover;
    message = (gameover) ? MESSAGE_GAME_OVER : null;
    return (this);
  }

  /**
   * Sets the message to overlay or display to the screen.
   * 
   * @param message The message to display.
   * @return The instance of Game so that subsequent calls can be chained
   *         together.
   */
  protected Game setMessage(String message) {
    this.message = message;
    return (this);
  }

  /**
   * Clears the message
   * 
   * @return The instance of Game so that subsequent calls can be chained
   *         together.
   */
  protected Game clearMessage() {
    message = null;
    return (this);
  }

  /**
   * Retrieves all of the sprites currently in the game.
   * 
   * @return The enumeration of sprites.
   */
  protected Sprite[] getSprites() {
    return (sprites.toArray(new Sprite[0]));
  }

  /**
   * Adds the specified Sprite into the existing list of sprites.
   * 
   * @param sprite The sprite to add.
   * @return The instance of Game so that subsequent calls can be chained
   *         together.
   */
  protected Game addSprite(Sprite sprite) {
    if (sprite != null) {
      sprites.add(sprite);
    }
    return (this);
  }

  /**
   * Adds the specified Sprite into the existing list of sprites.
   * 
   * @param sprite The sprite to add.
   * @return True if the specified Sprite was removed, false if otherwise.
   */
  protected boolean removeSprite(Sprite sprite) {
    if (sprite != null) {
      return (sprites.remove(sprite));
    }
    return (false);
  }

  /**
   * Removes all sprites.
   * 
   * @return The instance of Game so that subsequent calls can be chained
   *         together.
   */
  protected Game clearSprites() {
    for (Sprite sprite : getSprites()) {
      removeSprite(sprite);
    }
    return (this);
  }

  /**
   * Updates all of the positions for each sprite registered on the game board.
   * 
   * @param interval The amount of time that has passed since the last update.
   */
  private void updatePositions(long interval) {
    for (Sprite sprite : sprites) {
      if (!updatePosition(sprite, interval)) {
        sprite.move(interval);
      }
    }
  }

  /**
   * Starts the execution of the game. This is a blocking call.
   */
  public void start() {
    if (!isRunning) {
      isRunning = true;
      long started = System.currentTimeMillis();
      long last = started;
      while (isRunning) {
        long now = System.currentTimeMillis();
        try {
          long interval = now - last;

          // Get input from user
          InputEvent[] pendingEvents = new InputEvent[events.size()];
          for (int index = 0; index < pendingEvents.length; index++) {
            pendingEvents[index] = events.poll();
          }
          getInput(pendingEvents, interval);

          // Calculate updated sprite positions
          updatePositions(interval);

          // Redraw screen
          repaint();

          // To avoid 100% CPU utilization, sleep / wait for at least 1ms
          Thread.sleep(1);
          last = now;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Stops the execution of the game.
   */
  public void stop() {
    isRunning = false;
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a
   * component.
   * 
   * @param mouseEvent An event which indicates that a mouse action occurred in a
   *                   component.
   */
  @Override
  public void mouseClicked(MouseEvent mouseEvent) {
    events.add(mouseEvent);
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   * 
   * @param mouseEvent An event which indicates that a mouse action occurred in a
   *                   component.
   */
  @Override
  public void mousePressed(MouseEvent mouseEvent) {
  }

  /**
   * Invoked when a mouse button has been released on a component.
   * 
   * @param mouseEvent An event which indicates that a mouse action occurred in a
   *                   component.
   */
  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
  }

  /**
   * Invoked when the mouse enters a component.
   * 
   * @param mouseEvent An event which indicates that a mouse action occurred in a
   *                   component.
   */
  @Override
  public void mouseEntered(MouseEvent mouseEvent) {
  }

  /**
   * Invoked when the mouse exits a component.
   * 
   * @param mouseEvent An event which indicates that a mouse action occurred in a
   *                   component.
   */
  @Override
  public void mouseExited(MouseEvent mouseEvent) {
  }

  /**
   * Invoked when a mouse button is pressed on a component and then dragged.
   * 
   * @param mouseEvent An event which indicates that a mouse action occurred in a
   *                   component.
   */
  @Override
  public void mouseDragged(MouseEvent mouseEvent) {
  }

  /**
   * Invoked when the mouse cursor has been moved onto a component but no buttons
   * have been pushed.
   * 
   * @param mouseEvent An event which indicates that a mouse action occurred in a
   *                   component.
   */
  @Override
  public void mouseMoved(MouseEvent mouseEvent) {
    events.add(mouseEvent);
  }

  /**
   * Invoked when a key has been typed.
   * 
   * @param keyEvent An event which indicates that a keystroke occurred in a
   *                 component.
   */
  @Override
  public void keyTyped(KeyEvent keyEvent) {
    events.add(keyEvent);
  }

  /**
   * Invoked when a key has been pressed.
   * 
   * @param keyEvent An event which indicates that a keystroke occurred in a
   *                 component.
   */
  @Override
  public void keyPressed(KeyEvent keyEvent) {
  }

  /**
   * Invoked when a key has been released.
   * 
   * @param keyEvent An event which indicates that a keystroke occurred in a
   *                 component.
   */
  @Override
  public void keyReleased(KeyEvent keyEvent) {
  }
}
