package edu.uwsp.cnmt110;

/**
 * Represents a coordinate in a 2D space with an X and Y axis.
 */
public class Point {
  private int x;
  private int y;

  /**
   * Creates an instance of the Point class.
   * @param x The location on the horizontal axis
   * @param y The location on the vertical axis
   */
  public Point(int x, int y) {
    this.set(x, y);
  }

  /**
   * Creates an instance of the Point class.
   * @param point The existing point to use or copy from.
   */
  public Point(Point point) {
    this(point.getX(), point.getY());
  }

  /**
   * Creates a copy or shallow clone of the object.
   * @return A Point
   */
  public Point clone() {
    return(new Point(getX(), getY()));
  }

  /**
   * Returns the string representation of the Point instance.
   * @return The string representation describing the Point.
   */
  @Override
  public String toString() {
    return(String.format("(x:%d,y:%d)", getX(), getY()));
  }

  /**
   * Sets the x and y position for the location.
   * @param x The location on the horizontal axis
   * @param y The location on the vertical axis
   */
  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Sets the x and y position for the location.
   * @param point The point containing the x & y location to set.
   */
  public void set(Point point) {
    this.set(point.getX(), point.getY());
  }

  /**
   * Retrieves the value for the horizontal axis.
   * @return The position on the horizontal axis.
   */
  public int getX() {
    return x;
  }

  /**
   * Sets or changes the value of the horizontal axis.
   * @param x The location on the horizontal axis
   * @return The instance of Point so calls can be chained together in succession.
   */
  public Point setX(int x) {
    this.x = x;
    return(this);
  }

  /**
   * Retrieves the value for the vertical axis.
   * @return The position on the vertical axis.
   */
  public int getY() {
    return y;
  }

  /**
   * Sets or changes the value of the vertical axis.
   * @param y The location on the vertical axis
   * @return The instance of Point so calls can be chained together in succession.
   */
  public Point setY(int y) {
    this.y = y;
    return(this);
  }

  /**
   * Checks for equality of the Point instance with the specified x & y coordinate values.
   * @param x The location to compare on the horizontal axis.
   * @param y The location to compare on the vertical axis.
   * @return True if the coordinates are equal, false if otherwise.
   */
  public boolean equals(int x, int y) {
    return((getX() == x) &&
           (getY() == y));
  }

  /**
   * Checks for equality of the Point instance with the specified x & y coordinate values.
   * @param point The location to compare.
   * @return True if the coordinates are equal, false if otherwise.
   */
  public boolean equals(Point point) {
    return(equals(point.getX(), point.getY()));
  }
}
