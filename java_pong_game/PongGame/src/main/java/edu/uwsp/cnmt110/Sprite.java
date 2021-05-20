package edu.uwsp.cnmt110;

/**
 * Represents a sprite for a game.
 */
public abstract class Sprite {
  private int directionX = 1;
  private int directionY = 1;

  private float x;
  private float y;
  private int width;
  private int height;
  private float angle;
  private int speed;

  /**
   * Creates an instance of the Sprite class.
   * @param x The center position on the horizontal axis.
   * @param y The center position on the vertical axis.
   * @param width The width of the sprite.
   * @param height The height of the sprite.
   */
  protected Sprite(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Creates an instance of the Sprite class.
   * @param location The center position (x & y)
   * @param width The width of the sprite.
   * @param height The height of the sprite.
   */
  protected Sprite(Point location, int width, int height) {
    this(location.getX(), location.getY(), width, height);
  }

  /**
   * Creates an instance of the Sprite class.
   * @param x The center position on the horizontal axis.
   * @param y The center position on the vertical axis.
   * @param width The width of the sprite.
   * @param height The height of the sprite.
   * @param angle The angle or direction that the sprite is moving.
   * @param speed The distance that the sprite moves along the specified angle per second.
   */
  protected Sprite(int x, int y, int width, int height, float angle, int speed) {
    this(new Point(x, y), width, height, angle, speed);
  }

  /**
   * Creates an instance of the Sprite class.
   * @param location The center position (x & y)
   * @param width The width of the sprite.
   * @param height The height of the sprite.
   * @param angle The angle or direction that the sprite is moving.
   * @param speed The distance that the sprite moves along the specified angle per second.
   */
  protected Sprite(Point location, int width, int height, float angle, int speed) {
    this(location, width, height);
    this.setAngle(angle);
    this.speed = speed;
  }

  /**
   * Returns the string representation of the Sprite.
   * @return The string representation.
   */
  public String toString() {
    return(String.format("Sprite[center:%s,size:%dx%d,angle:%.1f,speed:%d)]",
      getPosition().toString(), getWidth(), getHeight(), getAngle(), getSpeed()));
  }

  /**
   * Returns the width of the Sprite.
   * @return The width in pixels.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of the Sprite.
   * @return The height in pixels.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the angle or direction that the sprite is traveling in a 2D coordinate space.
   * @return The angle between 0-359.
   */
  public float getAngle() {
    return angle;
  }

  /**
   * Sets or changes the angle / direction of the sprite.
   * @param angle The angle to set. Accepted values are between 0-359.
   * @return The instance of Sprite so subsequent methods can be chained together.
   */
  public Sprite setAngle(float angle) {
    this.angle = (angle >= 360) ? angle % 360 : angle;
    return(this);
  }

  /**
   * Retrieves the current speed or distance that the sprite moves per second.
   * @return The speed or distance of the sprite.
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Sets or changes the speed or distance the sprite moves per second.
   * @param speed The speed or number of pixels moved per second.
   * @return The instance of Sprite so subsequent methods can be chained together.
   */
  public Sprite setSpeed(int speed) {
    this.speed = speed;
    return(this);
  }

  /**
   * Returns the center point for the Sprite.
   * @return The center point.
   */
  public Point getPosition() {
    return(new Point(getX(), getY()));
  }

  /**
   * Moves or repositions the sprite to the specified location.
   * @param x The new position on the horizontal axis.
   * @param y The new position on the veritical axis.
   * @return The instance of Sprite so subsequent methods can be chained together.
   */
  public Sprite moveTo(int x, int y) {
    this.x = x;
    this.y = y;
    return(this);
  }

  /**
   * Moves or repositions the sprite to the specified location.
   * @param point The new location to move to.
   * @return The instance of Sprite so subsequent methods can be chained together.
   */
  public Sprite moveTo(Point point) {
    return(moveTo(point.getX(), point.getY()));
  }

  /**
   * Performs a automatic move or recalculation of the Sprites position based on it's speed and angle.
   * @param timeInterval The amount of time in milliseconds that has passed since the last update interval.
   * @return The new location of the Sprite.
   */
  public Point move(long timeInterval) {
    if (timeInterval > 0) {
      float distance = getSpeed() / ((float) timeInterval / (60.0f / 1000.0f));

      float radianAngle = (float) (getAngle() * Math.PI/180f);
      float distanceX = (float) Math.cos(radianAngle) * distance;
      float distanceY = (float) Math.sin(radianAngle) * distance;

      this.x += (distanceX * directionX);
      this.y += (distanceY * directionY);
    }
    return(new Point(getX(), getY()));
  }

  /**
   * Returns the bounding rectangle that encompasses or outlines the sprite.
   * @return The bounding rectangle.
   */
  public Rectangle getBoundingRect() {
    Point position = getPosition();
    return(new Rectangle(new Point(position.getX() - (getWidth() / 2),
                                   position.getY() - (getHeight() /2 )),
                         getWidth(), getHeight()));
  }

  /**
   * Checks to see if the sprite is colliding or intersecting with the specified sprite.
   * @param sprite The sprite to check
   * @return True if colliding, false if otherwise.
   */
  public boolean collidesWith(Sprite sprite) {
    return(this.getBoundingRect().intersectsWith(sprite.getBoundingRect()));
  }

  /**
   * Returns the sprites center location on the horizontal axis.
   * @return The position on the x axis.
   */
  public int getX() {
    return Math.round(this.x);
  }

  /**
   * Returns the sprites center location on the vertical axis.
   * @return The position on the y axis.
   */
  public int getY() {
    return Math.round(this.y);
  }

  /**
   * Changes the direction of the Sprite on the X axis. A simple implementation
   * of a reflection or bounce.
   * @return The instance of Sprite so subsequent methods can be chained together.
   */
  public Sprite bounceX() {
    this.directionX *= -1;
    return(this);
  }

  /**
   * Changes the direction of the Sprite on the y axis. A simple implementation
   * of a reflection or bounce.
   * @return The instance of Sprite so subsequent methods can be chained together.
   */
  public Sprite bounceY() {
    this.directionY *= -1;
    return(this);
  }
}
