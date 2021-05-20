package edu.uwsp.cnmt110;

/**
 * Represents a shape containing a height and width
 * and four right angles in a 2D coordinate space.
 */
public class Rectangle {
  private Point x1y1;
  private Point x2y2;

  /**
   * Creates an instance of the Rectangle class.
   * @param x1y1 The upper left point or 2D coordinate for the rectangle.
   * @param width The widtt or distance along the X axis
   * @param height The height or distance along the Y axis.
   */
  public Rectangle(Point x1y1, int width, int height) {
    this.x1y1 = x1y1.clone();
    this.x2y2 = new Point(x1y1.getX() + width, x1y1.getY() + height);
  }

  /**
   * Creates an instance of the Rectangle class.
   * @param x1y1 The upper left point or 2D coordinate position.
   * @param x2y2 The lower right point or 2D coordinate position.
   */
  public Rectangle(Point x1y1, Point x2y2) {
    this.x1y1 = x1y1.clone();
    this.x2y2 = x2y2.clone();
  }

  /**
   * Retrieves the upper left corner 2D coordinate position.
   * @return The upper left corner.
   */
  public Point getX1Y1() {
    return(this.x1y1.clone());
  }

  /**
   * Retrieves the lower right corner 2D coordinate position.
   * @return The lower right corner.
   */
  public Point getX2Y2() {
    return(this.x2y2.clone());
  }

  /**
   * Retrieves the horizontal 2D coordinate value from the upper left corner.
   * @return The horizontal 2D coordinate from the upper left corner.
   */
  public int getX1() {
    return(this.getX1Y1().getX());
  }

  /**
   * Retrieves the horizontal 2D coordinate value from the lower right corner.
   * @return The horizontal 2D coordinate from the lower right corner.
   */
  public int getX2() {
    return(this.getX2Y2().getX());
  }

  /**
   * Retrieves the vertical 2D coordinate value from the upper left corner.
   * @return The vertical 2D coordinate from the upper left corner.
   */
  public int getY1() {
    return(this.getX1Y1().getY());
  }

  /**
   * Retrieves the vertical 2D coordinate value from the lower right corner.
   * @return The vertical 2D coordinate from the lower right corner.
   */
  public int getY2() {
    return(this.getX2Y2().getY());
  }

  /**
   * Retrieves the height or distance on the Y access between the top left and lower right corner values.
   * @return The height
   */
  public int getHeight() {
    return (Math.abs(this.getX1Y1().getY() - this.getX2Y2().getY()));
  }

  /**
   * Retrieves the width or distance on the X access between the top left and lower right corner values.
   * @return The height
   */
  public int getWidth() {
    return (Math.abs(this.getX1Y1().getX() - this.getX2Y2().getX()));
  }

  /**
   * Checks for equality of another Rectangle instance.
   * @param rectangle The other Rectangle instance to compare.
   * @return True if equal, false if otherwise.
   */
  public boolean equals(Rectangle rectangle) {
    if (rectangle == null) return(false);
    return(this.getX1Y1().equals(rectangle.getX1Y1()) &&
           this.getX2Y2().equals(rectangle.getX2Y2()));
  }

  /**
   * Returns the default string representation of the Rectangle.
   * @return The default string representation.
   */
  public String toString() {
    return(String.format("[x1y1:%s,x2y2:%s]@%dx%d",
                         this.getX1Y1().toString(), this.getX2Y2().toString(),
                         getWidth(), getHeight()));
  }

  /**
   * Makes a shallow clone of copy
   * @return A new instance of Rectangle.
   */
  public Rectangle clone() {
    return(new Rectangle(this.getX1Y1(), this.getX2Y2()));
  }

  /**
   * Checks to see if the specified instance of Rectangle intersect or overlap
   * with each other.
   * @param rectangle
   * @return Tue if they intesect, false if otherwise.
   */
  public boolean intersectsWith(Rectangle rectangle) {
    int left = Math.max(getX1(), rectangle.getX1());
    int right = Math.min(getX2(), rectangle.getX2());
    int top = Math.max(getY1(), rectangle.getY1());
    int bottom = Math.min(getY2(), rectangle.getY2());

    return((left < right) && (bottom > top));
  }
}
