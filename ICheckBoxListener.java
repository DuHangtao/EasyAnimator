package cs3500.animator.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The interface of the check box listener which extend the ActionListener contain all the functions
 * that the check box listener has.
 */
public interface ICheckBoxListener extends ItemListener {
  @Override
  void itemStateChanged(ItemEvent e);
}
