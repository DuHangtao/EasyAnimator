package cs3500.animator.view;

import java.util.HashMap;

/**
 * Interface for viewing animations. Provides methods that all implementations of a view should
 * include in some form, even if it simply resorts to a default behavior by the model/controller.
 *
 * @param <O> Object representing animation shapes
 */
public interface IAnimationView<O> {
  /**
   * Returns the view in text form.
   *
   * @param isAbleToLoopback   the boolean to decide to loop back or not.
   * @param SVGBackGroundColor the background color of the svg animation.
   * @return the view in text form
   */
  String viewText(boolean isAbleToLoopback, String SVGBackGroundColor);

  /**
   * Starts the animation in whatever view it's in. Text views will export their Strings to the
   * appropriate file (non-loop by default). Animation views will begin the animation.
   */
  void start();

  /**
   * Sets the visible shapes for this view.
   *
   * @param visibleShapes The currently visible shapes.
   */
  void setShapesMap(HashMap<String, O> visibleShapes);

  /**
   * Sets the rate for this view.
   *
   * @param currentRate the rate to set to.
   */
  void setRate(int currentRate);
}
