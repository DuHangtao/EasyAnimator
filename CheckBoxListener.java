package cs3500.animator.controller;

import java.awt.event.ItemEvent;

import java.util.Map;

import javax.swing.JCheckBox;

/**
 * Manages the listening for all check boxes.
 */
public class CheckBoxListener implements ICheckBoxListener {
  Map<String, Runnable> checkBoxListener;

  /**
   * The constructor of the check box listener. Set the checkBoxListener as given maps.
   *
   * @param map the map contains string and runnable, the string is the name of the command and the
   *            runnable is the action for the specific action.
   */
  public CheckBoxListener(Map<String, Runnable> map) {
    super();
    this.checkBoxListener = map;
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    String who = ((JCheckBox) e.getItemSelectable()).getActionCommand();

    if (checkBoxListener.containsKey(who)) {
      checkBoxListener.get(who).run();
    }
  }
}