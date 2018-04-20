package cs3500.animator.model.animation;

import java.util.List;

import cs3500.animator.model.shape.IAnimShape;

public interface IAnimation extends Comparable<IAnimation> {
  /**
   * Sets the AnimShape of this AbstractAnimation to the given one. NOTE: This method only does
   * anything if shape is currently set to null.
   *
   * @param shape the AnimShape to set this.shape to
   */
  void setShape(IAnimShape shape);

  /**
   * Gets the start time of this AbstractAnimation.
   *
   * @return the start time
   */
  int getStart();

  /**
   * Gets the end time of this AbstractAnimation.
   *
   * @return the end time
   */
  int getEnd();

  /**
   * return the the animation shape in the animation.
   *
   * @return the the animation shape in the animation.
   */
  IAnimShape getShape();

  /**
   * Returns the String form of the animation given a unit of time and the rate of ticks per unit.
   *
   * @param rate the number of ticks in one second
   * @return the String form of the animation
   */
  String toString(int rate);

  /**
   * Returns a String that only shows the changes, not the names or times.
   *
   * @return a String showing changes.
   */
  String changeText();

  /**
   * Applies the animation changes to the animShape instantly.
   */
  void apply();

  /**
   * Applies the animation changes to the animShape based on the current time given Example: If
   * animation starts at t=0, ends at t=10, and the given time is 5, the animation is applied such
   * that it is halfway done.
   *
   * @param time the current time of the animation model
   */
  void apply(int time);

  /**
   * An AbstractAnimation is less than another if it begins at an earlier time. This will be used
   * when running the animations.
   *
   * @param o AbstractAnimation to compare this to.
   * @return Negative if this is less, Positive if this is more, 0 otherwise.
   */
  int compareTo(IAnimation o);

  /**
   * Checks if this animation conflicts with any animation in the given list of animations.
   *
   * @param as the list of animations to check
   * @return true if this conflicts with at least one animation in the list
   */
  boolean conflicts(List<IAnimation> as);

  /**
   * get the attribute name of the shape that we want to modify.
   *
   * @param index the index number of the current attribute name form a list of attribute names we
   *              want to modify.
   * @return the attribute name of the shape that we want to modify.
   */
  String getAttributeName(int index);

  /**
   * get the original value of the attribute that need to be modified.
   *
   * @param index the index number of the original value form a list of original values which is
   *              specific for each attributes which is distinguished by this index number.
   * @return the original value of the attribute that need to be modified.
   */
  String fromValue(int index);

  /**
   * get the value of the attribute that need to be modified to.
   *
   * @param index the index number of the value that we want change the attributes to form a list of
   *              to values.
   * @return the value of the attribute that need to be modified to.
   */
  String toValue(int index);

  /**
   * get the total number of the attributes will be changed for the specific shape going to be
   * changed.
   *
   * @return the total number of the attributes will be changed for the specific shape going to be
   *         changed.
   */
  int totalAttributesNumber();

  /**
   * this method will check how many Attributes Number that a shape have because the animation will
   * be applied on each Attribute of a shape. For example, if we want to move a shape, the Attribute
   * number will be 2, because the coordination only contains x-coordination and y-coordination. The
   * for loop will append all the svg text for a single animation on every attribute of a shape.
   *
   * @param rate the number of ticks in one second
   * @param isAbleToLoopBack the boolean to decide loop back or not.
   * @return the svg text form for the animation.
   */
  String svgAnimationText(int rate, boolean isAbleToLoopBack);
}
