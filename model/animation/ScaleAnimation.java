package cs3500.animator.model.animation;


import java.util.List;

import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.model.shape.IShape;

/**
 * Represents animations where the AnimShape's dimensions change.
 */
public class ScaleAnimation extends AbstractAnimation {
  private double[] endDims;
  private double[] startDims;

  /**
   * Creates a new ScaleAnimation which only take in end dimension.
   *
   * @param start   the time the animation starts
   * @param end     the time the animation ends
   * @param endDims the dimensions to scale to
   */
  public ScaleAnimation(int start, int end, double... endDims) {
    super(start, end);
    this.endDims = endDims;
  }

  /**
   * Creates a new ScaleAnimation which take in both start dimension and end dimension.
   *
   * @param start   the time the animation starts
   * @param end     the time the animation ends
   * @param endDims the dimensions to scale to
   */
  public ScaleAnimation(int start, int end, double[] startDims, double[] endDims) {
    this(start, end, endDims);
    if (this.startDims == null) {
      this.startDims = startDims;
    }
  }

  @Override
  public void setShape(IAnimShape shape) {
    super.setShape(shape);
    this.startDims = shape.allDimensions();
    if (endDims.length != startDims.length) {
      throw new IllegalArgumentException("Wrong number of dimensions.");
    }
  }

  @Override
  public String changeText() {
    // this creates a copy to get the new description of the IShape object without knowing
    // if the description of the rectangle has changed (may be a subclass)
    IShape copy = this.shape.getShape().copy();
    copy.scale(endDims);
    return "scales from " + this.shape.getShape().dimensions() + " to " + copy.dimensions();
  }

  @Override
  public void apply() {
    this.shape.scale(this.endDims);
  }

  @Override
  public void apply(int time) {
    double[] newDims = new double[startDims.length];
    if (time > this.start && time <= this.end) {
      for (int i = 0; i < startDims.length; i++) {
        double newDim = this.curVal(time, startDims[i], endDims[i]);
        newDims[i] = newDim;
      }
      this.shape.scale(newDims);
    }
  }

  @Override
  public String getAttributeName(int index) {
    List<String> lenNames = this.shape.getShape().getSvgShapeLenNames();
    return "\"" + lenNames.get(index) + "\"";
  }

  @Override
  public String fromValue(int index) {
    double[] scalesFrom = startDims;
    return "\"" + scalesFrom[index] + "\"";
  }

  @Override
  public String toValue(int index) {
    double[] scalesTo = endDims;
    return "\"" + scalesTo[index] + "\"";
  }

  @Override
  public int totalAttributesNumber() {
    return this.startDims.length;
  }
}
