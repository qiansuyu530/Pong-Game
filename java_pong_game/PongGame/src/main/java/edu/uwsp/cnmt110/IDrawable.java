package edu.uwsp.cnmt110;

import java.awt.*;

/**
 * Represents an object that can be drawn to a Graphics canvas.
 */
public interface IDrawable {
  /**
   * Draws the specified item to the Graphics canvas.
   * @param g The canvas to draw on.
   */
  void paint(Graphics g);
}
