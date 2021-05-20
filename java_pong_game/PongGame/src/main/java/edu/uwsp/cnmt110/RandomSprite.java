package edu.uwsp.cnmt110;

import java.awt.*;
import java.util.Random;

/**
 * Represents a sprite with randomly generated attributes.
 * DO NOT USE THIS IN YOUR GAME. IT IS SIMPLY PROVIDED AS AN EXAMPLE
 */
public class RandomSprite extends DrawableSprite {
  private final static int MIN_SPRITE_HEIGHT = 5;
  private final static int MAX_SPRITE_HEIGHT = 25;
  private final static int MIN_SPRITE_WIDTH = 5;
  private final static int MAX_SPRITE_WIDTH = 25;
  private final static int MIN_SPRITE_SPEED = 2;
  private final static int MAX_SPRITE_SPEED = 12;
  private static Random random = new Random();

  /**
   * Creates an instance of a RandomSprite class.
   * @param screenWidth The maximum width of the screen.
   * @param screenHeight The maximum height of the screen.
   */
  public RandomSprite(int screenWidth, int screenHeight) {
    super(getRandom().nextInt(screenWidth), getRandom().nextInt(screenHeight),
         getRandomWidth(), getRandomHeight(),
         getRandom().nextInt(360), getRandomSpeed());
    setColor(getRandomColor());
  }

  /**
   * Retrieves a shared Random helper to ensure true randomness.
   * @return The configured / shared Random instance.
   */
  private static Random getRandom() {
    return random;
  }

  /**
   * Generates a random Color
   * @return A random color.
   */
  private Color getRandomColor() {
    return(new Color(getRandom().nextInt(255),
                     getRandom().nextInt(255),
                     getRandom().nextInt(255)));
  }

  /**
   * Generates a random number within the specified range.
   * @param minimum The minimum value inclusive.
   * @param maximum The maximum value inclusive.
   * @return The random number.
   */
  private static int getRandomValue(int minimum, int maximum) {
    int value;
    do {
      value = getRandom().nextInt(maximum);
    } while (value < minimum);
    return(value);
  }

  /**
   * Generates a random width of the sprite.
   * @return A random width.
   */
  private static int getRandomWidth() {
    return(getRandomValue(MIN_SPRITE_WIDTH, MAX_SPRITE_WIDTH));
  }

  /**
   * Generates a random height of the sprite.
   * @return A random height.
   */
  private static int getRandomHeight() {
    return(getRandomValue(MIN_SPRITE_HEIGHT, MAX_SPRITE_HEIGHT));
  }

  /**
   * Generates a random speed or distance moved for the sprite.
   * @return A random speed.
   */
  private static int getRandomSpeed() {
    return(getRandomValue(MIN_SPRITE_SPEED, MAX_SPRITE_SPEED));
  }
}
