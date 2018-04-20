package cs3500.animator.model.shape;

/**
 * The Interface of the Pos which holds all the methods.
 */
public interface IPos {

  /**
   * Get the X position.
   *
   * @return the X position.
   */
  double getX();

  /**
   * Get the Y position.
   *
   * @return the Y position.
   */
  double getY();

  /**
   * return the copy of the Position.
   *
   * @return the copy of the Position.
   */
  IPos copy();
}
