package edu.uwsp.cnmt110;

import java.awt.*;

/**
 * Represents a Sprite that knows how to draw itself to a graphics canvas.
 */
public class DrawableSprite extends Sprite implements IDrawable {
  private Color color = Color.WHITE;
  private Color textColor = Color.RED;
  private String name;

  /**
   * Creates an instance of the DrawableSprite class.
   * @param x The x coordinate in a 2d coordinate space where 0,0 is the upper left.
   * @param y The y coordinate in a 2d coordinate space where 0,0 is the upper left.
   * @param width The width of the Sprite
   * @param height The height of the Sprite
   * @param angle The angle along which the Sprite is traveling.
   * @param speed The speed or distance moved per second.
   */
  public DrawableSprite(int x, int y, int width, int height, float angle, int speed) {
    super(x, y, width, height, angle, speed);
  }

  /**
   * Creates an instance of the DrawableSprite class.
   * @param sprite An instance of Sprite to wrap or clone.
   */
  public DrawableSprite(Sprite sprite) {
    this(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), sprite.getAngle(), sprite.getSpeed());
  }

  /**
   * Overlays the specified label onto the sprite.
   * @param g The graphics canvas to draw onto.
   */
  protected void paintLabel(Graphics g) {
    // avoiding drawing labels, no need of it
//    String name = getName();
//    if ((name != null) && (! name.isEmpty())) {
//      String label = Float.toString(getAngle());
//      Font font = new Font("serif", Font.PLAIN, 9);
//      g.setColor(getTextColor());
//      g.setFont(font);
//      FontMetrics metrics = g.getFontMetrics(font);
//      g.drawString(label, getX() - (metrics.stringWidth(label) / 2),
//        getY() + (metrics.getHeight() / 2));
//    }
  }

  /**
   * Draws or renders the information.
   * @param g The graphics / canvas to draw to.
   */
  @Override
  public void paint(Graphics g) {
    Rectangle boundingRect = getBoundingRect();
    g.setColor(getColor());
    g.drawRect(boundingRect.getX1(), boundingRect.getY1(), boundingRect.getWidth(), boundingRect.getHeight());

    paintLabel(g);
  }

  /**
   * Retrieves the optional name / label associated with the sprite.
   * @return The optional name / label
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the optional name / label associated with the sprite.
   * @param name The optional name / label to set for the Sprite.
   * @return The instance of the Sprite so that subsequent calls can be chained together.
   */
  public Sprite setName(String name) {
    this.name = name;
    return(this);
  }

  /**
   * Retrieves the color of the outline for the Sprite.
   * @return The current color.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Sets the color to use for the outline.
   * @param color The color of the outline.
   * @return The instance of the Sprite so that subsequent calls can be chained together.
   */
  public DrawableSprite setColor(Color color) {
    this.color = color;
    return(this);
  }

  /**
   * Retrieves the color of the text or label for the Sprite.
   * @return The current text or label color.
   */
  public Color getTextColor() {
    return textColor;
  }

  /**
   * Sets the text color to use for the label.
   * @param textColor The color of the text
   * @return The instance of the Sprite so that subsequent calls can be chained together.
   */
  public DrawableSprite setTextColor(Color textColor) {
    this.textColor = textColor;
    return(this);
  }
}
