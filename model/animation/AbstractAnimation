package cs3500.animator.model.animation;

import java.util.List;

//import cs3500.animator.model.shape.AnimShape;
import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.model.util.Util;

/**
 * Represents a single animation (motion, color change, etc).
 */
public abstract class AbstractAnimation implements IAnimation {
  protected final int start;
  protected final int end;
  protected IAnimShape shape;

  /**
   * Creates a new AbstractAnimation.
   *
   * @param start time the animation starts
   * @param end   time the animation ends
   */
  public AbstractAnimation(int start, int end) {
    if (start < 0 || end < 0) {
      throw new IllegalArgumentException("Start and end times cannot be less than zero.");
    }
    if (start > end) {
      throw new IllegalArgumentException("Start must not be later than end.");
    }
    this.start = start;
    this.end = end;

  }

  /**
   * Sets the AnimShape of this AbstractAnimation to the given one. NOTE: This method only does
   * anything if shape is currently set to null.
   *
   * @param shape the AnimShape to set this.shape to
   */
  @Override
  public void setShape(IAnimShape shape) {
    if (this.shape == null) {
      this.shape = shape;
    }
  }

  /**
   * Gets the start time of this AbstractAnimation.
   *
   * @return the start time
   */
  @Override
  public int getStart() {
    return start;
  }

  /**
   * Gets the end time of this AbstractAnimation.
   *
   * @return the end time
   */
  @Override
  public int getEnd() {
    return end;
  }

  @Override
  public String toString() {
    return toString(1);
  }

  /**
   * Returns the String form of the animation given a unit of time and the rate of ticks per unit.
   *
   * @param rate the number of ticks in one second
   * @return the String form of the animation
   */
  @Override
  public String toString(int rate) {
    String ans = "Shape " + this.shape.getName() + " " + this.changeText();
    String startText;
    String endText;
    if (rate == 1) {
      startText = this.start + "s";
      endText = this.end + "s";
    } else {
      startText = this.start * (double) 1 / rate + "s";
      endText = this.end * (double) 1 / rate + "s";
    }
    ans += " from t=" + startText + " to t=" + endText;
    return ans;
  }

  /**
   * Returns a String that only shows the changes, not the names or times.
   *
   * @return a String showing changes.
   */
  @Override
  public abstract String changeText();

  /**
   * Applies the animation changes to the animShape instantly.
   */
  @Override
  public abstract void apply();

  /**
   * Applies the animation changes to the animShape based on the current time given Example: If
   * animation starts at t=0, ends at t=10, and the given time is 5, the animation is applied such
   * that it is halfway done.
   *
   * @param time the current time of the animation model
   */
  @Override
  public abstract void apply(int time);


  /**
   * Returns the current value of the given measurement based on time, start value, and end value.
   *
   * @param t the current time
   * @param a the starting value of the measurement
   * @param b the ending value of the measurement
   * @return the current value of the measurement
   */
  protected double curVal(int t, double a, double b) {
    return a + (b - a) * ((double) (t - start)) / (end - start);
  }

  /**
   * An AbstractAnimation is less than another if it begins at an earlier time. This will be used
   * when running the animations.
   *
   * @param o AbstractAnimation to compare this to.
   * @return Negative if this is less, Positive if this is more, 0 otherwise.
   */
  @Override
  public int compareTo(IAnimation o) {
    return this.start - o.getStart();
  }

  /**
   * Checks if this animation conflicts with the given animation. Two animations conflict with each
   * other if and only if: 1. They operate on the same object. 2. They are different types of
   * animations. 3. One starts while the other is still going.
   *
   * @param a the animation to check with this for conflicts.
   * @return true if they conflict, false if they do not
   */
  private boolean conflicts(IAnimation a) {
    if (this.shape.getName().equals(a.getShape().getName()) && a.getClass().isInstance(this)) {
      if ((this.start >= a.getStart() && this.start <= a.getEnd()) ||
              (a.getStart() >= this.start && a.getStart() <= this.end)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if this animation conflicts with any animation in the given list of animations.
   *
   * @param as the list of animations to check
   * @return true if this conflicts with at least one animation in the list
   */
  @Override
  public boolean conflicts(List<IAnimation> as) {
    for (IAnimation a : as) {
      if (this.conflicts(a)) {
        return true;
      }
    }
    return false;
  }

  /**
   * return the the animation shape in the animation.
   *
   * @return the the animation shape in the animation.
   */
  @Override
  public IAnimShape getShape() {
    return shape.copy();
  }

  /**
   * get the attribute name of the shape that we want to modify.
   *
   * @param index the index number of the current attribute name form a list of attribute names we
   *              want to modify.
   * @return the attribute name of the shape that we want to modify.
   */
  @Override
  public abstract String getAttributeName(int index);

  /**
   * get the original value of the attribute that need to be modified.
   *
   * @param index the index number of the original value form a list of original values which is
   *              specific for each attributes which is distinguished by this index number.
   * @return the original value of the attribute that need to be modified.
   */
  @Override
  public abstract String fromValue(int index);

  /**
   * get the value of the attribute that need to be modified to.
   *
   * @param index the index number of the value that we want change the attributes to form a list of
   *              to values.
   * @return the value of the attribute that need to be modified to.
   */
  @Override
  public abstract String toValue(int index);

  /**
   * get the total number of the attributes will be changed for the specific shape going to be
   * changed.
   *
   * @return the total number of the attributes will be changed for the specific shape going to be
   *         changed.
   */
  @Override
  public abstract int totalAttributesNumber();


  /**
   * this method will check how many Attributes Number that a shape have because the animation will
   * be applied on each Attribute of a shape. For example, if we want to move a shape, the Attribute
   * number will be 2, because the coordination only contains x-coordination and y-coordination. The
   * for loop will append all the svg text for a single animation on every attribute of a shape.
   *
   * @param rate             the number of ticks in one second
   * @param isAbleToLoopBack the boolean to decide loop back or not.
   * @return the svg text form for the animation.
   */
  @Override
  public String svgAnimationText(int rate, boolean isAbleToLoopBack) {
    String svgText = "";
    String loopBase;
    if (isAbleToLoopBack) {
      loopBase = "base.begin+";
    } else {
      loopBase = "";
    }
    for (int i = 0; i < this.totalAttributesNumber(); i++) {
      svgText += "<animate attributeType=\"xml\"";
      svgText += " begin=" + "\"" + loopBase + ((double) this.start / rate) * 1000 + "ms" + "\"";
      svgText += " dur=" + "\"" + ((double) (this.end - this.start) / rate) * 1000 + "ms" + "\" ";
      svgText += "attributeName=" + this.getAttributeName(i);
      svgText += " from=" + this.fromValue(i);
      svgText += " to=" + this.toValue(i) + " ";
      svgText += "fill=\"freeze\" />" + Util.newLine();
    }
    return svgText;
  }
}
