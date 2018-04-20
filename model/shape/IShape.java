package cs3500.animator.model.shape;

import java.util.List;

/**
 * Represents shapes (only the shape - anything to do with the animation is in AnimShape).
 */
public interface IShape {
  /**
   * Gets a String representing the type of shape it is (rectangle, oval, etc.).
   *
   * @return the String representing the shape.
   */
  String getType();

  /**
   * Gets a description of what position this shape would be mapped from. Examples: center, top
   * right corner, bottom center, etc.
   *
   * @return description of significance of position
   */
  String posDesc();

  /**
   * Gets the dimensions of this IShape as a String separated by commas.
   *
   * @return String representation of dimensions
   */
  String dimensions();

  /**
   * Returns a new copy of this IShape.
   *
   * @return copy of this IShape
   */
  IShape copy();

  /**
   * Changes this IShape's dimensions to the new ones.
   */
  void scale(double... measurements);

  /**
   * Returns all of this IShape's dimensions.
   *
   * @return the dimensions of this ISHape
   */
  double[] allDimensions();

  /**
   * Returns a svg shape format shape name.
   *
   * @return the svg shape format shape name.
   */
  String getSvgShape() throws IllegalArgumentException;

  /**
   * Return all the Dimensions name of a shape in svg text from. (For example, for rectangle it has
   * width anf height.
   *
   * @return all the Dimensions name of a shape in svg text from. (For example, for rectangle it has
   *         x and y.
   */
  List<String> getSvgShapeLenNames();


  /**
   * Return all the coordinates' names for a shape in svg text form. (For example, the  coordinates
   * for a rectangle in svg is x and y.
   *
   * @return all the coordinates' names for a shape in svg text form.
   */
  List<String> getSvgShapeCods();


}
