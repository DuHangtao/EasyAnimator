package cs3500.animator.model.shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents ovals.
 */
public class Oval implements IShape {
  private double xRadius;
  private double yRadius;

  /**
   * Creates a new Oval object.
   *
   * @param xRadius horizontal radius
   * @param yRadius vertical radius
   */
  public Oval(double xRadius, double yRadius) {
    if (xRadius <= 0 || yRadius <= 0) {
      throw new IllegalArgumentException("Can't create a shape with non-positive dimensions.");
    }
    this.xRadius = xRadius;
    this.yRadius = yRadius;
  }

  @Override
  public String getType() {
    return "oval";
  }

  @Override
  public String posDesc() {
    return "Center";
  }

  @Override
  public String dimensions() {
    return "X radius: " + this.xRadius + ", Y radius: " + this.yRadius;
  }

  @Override
  public IShape copy() {
    return new Oval(this.xRadius, this.yRadius);
  }

  @Override
  public void scale(double... measurements) {
    if (measurements.length != 2) {
      throw new IllegalArgumentException("Arguments must be x radius and y radius only.");
    }
    xRadius = measurements[0];
    yRadius = measurements[1];
  }

  @Override
  public double[] allDimensions() {
    return new double[]{xRadius, yRadius};
  }

  @Override
  public String getSvgShape() {
    return "ellipse";
  }

  @Override
  public List<String> getSvgShapeLenNames() {
    List<String> nameList = new ArrayList<>();
    nameList.add("rx");
    nameList.add("ry");
    return nameList;
  }

  @Override
  public List<String> getSvgShapeCods() {
    List<String> codNames = new ArrayList<>();
    codNames.add("cx");
    codNames.add("cy");
    return codNames;
  }

}
