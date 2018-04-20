package cs3500.animator.model.shape;

import java.awt.Color;

public interface IAnimShape extends Comparable<IAnimShape> {

  /**
   * Make a copy for the animShape.
   */
  IAnimShape copy();

  /**
   * Gets the name of this animation object.
   *
   * @return the name
   */
  String getName();

  /**
   * Gets the position of this animation object.
   *
   * @return the position
   */
  IPos getPos();

  /**
   * Gets the color of this animation object. Note: Color is immutable according to the library.
   *
   * @return the color
   */
  Color getColor();

  /**
   * Gets a copy of the shape of this animation object.
   *
   * @return the shape
   */
  IShape getShape();

  /**
   * Gets all of the dimensions of this shape.
   *
   * @return the dimensions
   */
  double[] allDimensions();

  /**
   * Gets the time of appearance of this shape in ticks.
   *
   * @return time of appearance
   */
  int getAppears();

  /**
   * Gets the time of disappearance of this shape in ticks.
   *
   * @return time of disappearance
   */
  int getDisappears();

  /**
   * Gets the layer of this shape. Higher layers are drawn on the top.
   * @return the layer of this shape
   */
  int getLayer();

  /**
   * Moves this AnimShape to the given Pos.
   *
   * @param dest destination position
   */
  void move(IPos dest);

  /**
   * Scales this AnimShape to the given measurements.
   *
   * @param measurements new measurements to scale to - order of measurements depends on shape
   */
  void scale(double... measurements);

  /**
   * Changes the color of this AnimShape to the given Color.
   *
   * @param color the color to change this to
   */
  void changeColor(Color color);

  /**
   * Changes the visible aspects of this shape into those of the given shape.
   *
   * @param other the shape to change into
   */
  void changeInto(IAnimShape other);

  /**
   * Returns a String representing this AnimObject with the given rate of time.
   *
   * @param rate the number of ticks per second
   * @return String form of AnimObject
   */
  String toString(int rate);

  /**
   * Return the svg text for all animation shapes depend on their positions, colors, and
   * dimensions.
   *
   * @return the svg text for all animation shapes.
   */
  String getSvgAnimShapeText();

  /**
   * Sets the visibility of the shape and return the appropriate svg String.
   *
   * @param rate             the number of ticks in one second
   * @param isAbleToLoopBack the boolean to decide loop back or not.
   * @return the svg String with the appropriate visibility.
   */
  String setVisibility(int rate, boolean isAbleToLoopBack);

  /**
   * Gets the original values of all attributes.
   *
   * @param acc the accumulated StringBuilder
   * @return the original values of all attributes.
   */
  StringBuilder getAllAttributesOriginalValues(StringBuilder acc);
}
