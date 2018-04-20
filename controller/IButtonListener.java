package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The interface of the button listener which extend the ActionListener contain all the functions
 * that the button listener has.
 */
public interface IButtonListener extends ActionListener {
  @Override
  void actionPerformed(ActionEvent e);
}
