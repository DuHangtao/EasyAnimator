package cs3500.animator.controller;

/**
 * Controls any implementation of interactive views.
 */
public interface IInteractiveController<S> {

  /**
   * Configure the listeners for the buttons and checkboxes.
   */
  void init();

  /**
   * Gets the current state of the animation that this controls.
   */
  S getState();

  /**
   * Gets the listener for the check boxes. Some implementations may throw
   * UnsupportedOperationException.
   *
   * @return the checkbox listener for this interactive controller.
   */
  ICheckBoxListener getCheckBoxListener();

  /**
   * Get the listener for buttons. Some implementations may throw unsupportedOperationException.
   *
   * @return the buttonListener for this interactive controller.
   */
  IButtonListener getButtonListener();
}
