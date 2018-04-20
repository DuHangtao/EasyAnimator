package cs3500.animator.model;

import java.util.HashMap;
import java.util.List;

import cs3500.animator.model.shape.IAnimShape;

/**
 * The read only version of the model.
 *
 * @param <O> type representing the objects in the animation
 * @param <A> type representing the animations themselves
 */
public interface IAnimationModelReadOnly<O, A> {
  /**
   * Get all the animations were added to the model which is a hash map of IAnimation.
   *
   * @return all the animations were added to the model.
   */
  List<A> getAnimations();

  /**
   * Get all the shapes which were added to the model which is a HashMap of AnimShapes.
   *
   * @return return all the shapes which were added to the model which is a HashMap.
   */
  HashMap<String, O> getShapes();

  /**
   * Gets all the shapes in the model as a List instead of a HashMap.
   *
   * @return list of shapes
   */
  List<O> getShapesList();

  /**
   * Gets the list of shapes at all ticks, indexed by the tick at which they're shown.
   *
   * @return list of shapes at all ticks
   */
  List<List<O>> getShapesListAllTicks();

  /**
   * Gets the list of shapes at all ticks divided by layers. The outermost list is the layer, the
   * next is the tick, and the innermost is the shape.
   *
   * @return list of shapes at every tick at every layer.
   */
  List<List<List<O>>> getLayeredAllTicks();

  /**
   * Returns a copy of the animationModel.
   *
   * @return copy of model
   */
  IAnimationModel copy();

  /**
   * Returns the end time of the animation in ticks.
   *
   * @return the end time of the animation in ticks
   */
  int endTime();

  /**
   * Gets the shapes with the initial states.
   */
  List<IAnimShape> getOriginalShapesList();

}
