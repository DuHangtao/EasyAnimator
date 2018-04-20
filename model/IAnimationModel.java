package cs3500.animator.model;

import java.util.List;

import cs3500.animator.model.shape.IAnimShape;

/**
 * Interface of the animation model. Holds methods that all animation model implementations are
 * expected to have.
 *
 * @param <O> the type that represents the animation object.
 * @param <A> the type that represents the animation transformation.
 */
public interface IAnimationModel<O, A> extends IAnimationModelReadOnly<O, A> {

  /**
   * Adds the given animation object to the model with its given name. If the given animation object
   * also stores its name AND the given name and object name are not consistent, throws an
   * IllegalArgumentException
   *
   * @param name       Name to refer to the animation object with.
   * @param animObject IAnimation object to add to the animation.
   * @throws IllegalArgumentException if given name and object name are inconsistent
   */
  void addAnimShape(String name, O animObject) throws IllegalArgumentException;

  /**
   * Adds the given animation object to the model with its given name. Assumes the name of the
   * animation object can be retrieved from the given animShape.
   *
   * @param animShape the animation object to add that can have its name retrieved
   */
  void addAnimShape(O animShape);

  /**
   * Adds the given animation to this animation model.
   *
   * @param name      The name of the object to animate. Must already exist in this
   *                  cs3500.animator.model.IAnimationModel.
   * @param animation The animation/transformation to add.
   * @throws IllegalArgumentException if object with given name does not exist in model.
   * @throws IllegalArgumentException if animation conflicts with already existing one.
   */
  void addAnimation(String name, A animation) throws IllegalArgumentException;

  /**
   * Skips to the given time in the animation.
   *
   * @param time the time to skip to.
   */
  void skipTo(int time);

  /**
   * Rewinds to the beginning of the animation.
   */
  void rewind();

  /**
   * Gets the shapes with the initial states.
   */
  List<IAnimShape> getOriginalShapesList();

}
