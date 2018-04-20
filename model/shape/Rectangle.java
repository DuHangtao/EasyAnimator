package cs3500.animator.model.shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents rectangles.
 */
public class Rectangle implements IShape {
  private double width;
  private double height;

  /**
   * Creates a new Rectangle object.
   *
   * @param width  horizontal length of the rectangle
   * @param height vertical length of the rectangle
   */
  public Rectangle(double width, double height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Can't create a shape with non-positive dimensions.");
    }
    this.width = width;
    this.height = height;
  }

  @Override
  public String getType() {
    return "rectangle";
  }

  @Override
  public String posDesc() {
    return "Lower-left corner";
  }

  @Override
  public String dimensions() {
    return "Width: " + this.width + ", Height: " + this.height;
  }

  @Override
  public IShape copy() {
    return new Rectangle(this.width, this.height);
  }

  @Override
  public void scale(double... measurements) {
    if (measurements.length != 2) {
      throw new IllegalArgumentException("The measurements need to be width and height only.");
    }
    this.width = measurements[0];
    this.height = measurements[1];
  }

  @Override
  public double[] allDimensions() {
    double[] dims = {width, height};
    return dims;
  }

  @Override
  public String getSvgShape() {
    return "rect";
  }

  @Override
  public List<String> getSvgShapeLenNames() {
    List<String> nameList = new ArrayList<>();
    nameList.add("width");
    nameList.add("height");
    return nameList;
  }

  @Override
  public List<String> getSvgShapeCods() {
    List<String> codNames = new ArrayList<>();
    codNames.add("x");
    codNames.add("y");
    return codNames;
  }
}
