package cs3500.animator.model.animation;

import java.awt.Color;

import cs3500.animator.model.shape.AnimShape;
import cs3500.animator.model.util.Util;

/**
 * AbstractAnimation involving changing colors.
 */
public class ColorAnimation extends AbstractAnimation {
  private Color endColor;
  private Color startColor;

  /**
   * the constructor takes in only end color.
   *
   * @param start    the start time of the animation.
   * @param end      the end time of the animation.
   * @param endColor the end color of the shape wants to be.
   */
  public ColorAnimation(int start, int end, Color endColor) {
    super(start, end);
    this.endColor = endColor;
  }

  /**
   * the constructor which take in both start color and end color.
   *
   * @param start      the start time of the animation .
   * @param end        the start time of the animation .
   * @param startColor the start color of the shape.
   * @param endColor   the end color of the shape wants to be.
   */
  public ColorAnimation(int start, int end, Color startColor, Color endColor) {
    this(start, end, endColor);
    this.startColor = startColor;
  }

  /**
   * If the start color is null, to set the color to the given color.
   *
   * @param shape the AnimShape to set this.shape to
   */
  public void setShape(AnimShape shape) {
    super.setShape(shape);
    if (startColor == null) {
      startColor = this.shape.getColor();
    }
  }

  @Override
  public String changeText() {
    return "changes color from " + Util.colString(this.shape.getColor()) +
            " to " + Util.colString(this.endColor);
  }

  @Override
  public void apply() {
    this.shape.changeColor(this.endColor);
  }

  @Override
  public void apply(int time) {
    if (time > start) {
      time = Math.min(time, end);
      float[] startCom = this.startColor.getColorComponents(null);
      float[] endCom = this.endColor.getColorComponents(null);
      float r = (float) this.curVal(time, startCom[0], endCom[0]);
      float g = (float) this.curVal(time, startCom[1], endCom[1]);
      float b = (float) this.curVal(time, startCom[2], endCom[2]);
      this.shape.changeColor(new Color(r, g, b));
    }
  }

  @Override
  public String getAttributeName(int index) {
    return "\"fill\"";
  }

  @Override
  public String fromValue(int index) {
    return "\"rgb" + Util.colRGBString(this.startColor) + "\"";
  }

  @Override
  public String toValue(int index) {
    return "\"rgb" + Util.colRGBString(this.endColor) + "\"";
  }

  @Override
  public int totalAttributesNumber() {
    return 1;
  }
}
