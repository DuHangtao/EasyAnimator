package cs3500.animator.controller;

import java.awt.event.ActionEvent;

import java.util.Map;

/**
 * Manages the listening for all buttons.
 */
public class ButtonListener implements IButtonListener {
  Map<String, Runnable> buttonActions;

  /**
   * The constructor of the button listener.
   * Set the buttonActions as given maps.
   *
   * @param map the map contains string and runnable, the string is
   *            the name of the command and the runnable is the action
   *            for the specific action.
   */
  public ButtonListener(Map<String, Runnable> map) {
    super();
    this.buttonActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonActions.containsKey(e.getActionCommand())) {
      buttonActions.get(e.getActionCommand()).run();
    }
  }
}
