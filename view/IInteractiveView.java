package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.event.ChangeListener;

import cs3500.animator.model.shape.IAnimShape;

/**
 * Interface for all implementations of the interactive view. Parameterized over object representing
 * the state of the Animation.
 *
 * @param <S> Object type representing the state of the animation.
 */
public interface IInteractiveView<S> extends IAnimationView<IAnimShape> {

  /**
   * Repaints the view.
   */
  void repaint();

  /**
   * Adds the action listeners to this view. May throw UnsupportedOperationException if the
   * implementation does not use action listeners.
   *
   * @param actionListener ActionListener to add to this view.
   */
  void addActionListener(ActionListener actionListener);

  /**
   * Adds the item listener to this view. May throw UnsupportedOperationException if the
   * implementation does not use item listeners.
   *
   * @param itemListener ItemListener to add to this view.
   */
  void addItemListener(ItemListener itemListener);

  /**
   * Adds the change listener for the slide of this view.
   *
   * @param changeListener ChangeListener to add to this view.
   */
  void addChangeListener(ChangeListener changeListener);

  /**
   * Adds the action listener for the box to choose background color.
   *
   * @param actionListener ActionListener to add to the background drop-down.
   */
  //void addBoxListener(ActionListener actionListener);

  /**
   * Sets the time of the animation to the given tick.
   *
   * @param tick the tick to set the animation to.
   */
  void setTime(int tick);

  /**
   * Makes the animation go faster. How much faster is implementation dependent.
   */
  void faster();

  /**
   * Makes the animation go slower. How much slower is implementation dependent.
   */
  void slower();

  /**
   * Updates the area showing the speed of the animation.
   */
  void updateSpeedLabel();

  /**
   * Pauses the animation.
   */
  void pause();

  /**
   * Resumes a paused animation or starts an unstarted animation.
   */
  void resume();

  /**
   * Restarts the animation from the beginning with the current settings.
   */
  void restart();

  /**
   * Quits the animation.
   */
  void quit();

  /**
   * Toggles looping for the animation.
   */
  void toggleLoop();

  /**
   * Sets the speed of the animation according to a user defined field.
   */
  void setSpeed();

  /**
   * Toggles the given shape's visibility.
   *
   * @param shape AnimShape to make appear or disappear.
   */
  void toggleShape(IAnimShape shape);

  /**
   * Exports the animation to a file specified by the user in the GUI.
   */
  void export();

  /**
   * Changes the background color of this animation to the specified color.
   *
   * @param color the new color to change to.
   */
  void changeBGColor(Color color);


  /**
   * Gets the current state of the animation represented as an implementation defined type.
   *
   * @return current state of the animation.
   */
  S getViewState();

}
