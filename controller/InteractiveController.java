package cs3500.animator.controller;

import java.awt.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IAnimShape;

import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.InteractiveView;

/**
 * Acts as the controller for the interactive view. The other views don't really have user
 * interaction, so a controller was not necessary for them.
 */
public class InteractiveController
        implements IInteractiveController<InteractiveView.AnimationState> {
  private IAnimationModel model;
  private IInteractiveView<InteractiveView.AnimationState> view;
  private HashMap<String, IAnimShape> shapes;
  private ButtonListener buttonListener;
  private CheckBoxListener checkBoxListener;

  /**
   * This controller basically takes in a hybrid view and the read only model.
   *
   * @param model the read only model contains only getters form the model.
   * @param view  the hybrid view which has the functionality of visual view and svg view.
   */
  public InteractiveController(IAnimationModel<IAnimShape, IAnimation> model,
                               InteractiveView view) {
    this.model = model;
    this.view = view;
    shapes = new HashMap<String, IAnimShape>();
    this.shapes = model.getShapes();
    this.init();

  }

  /**
   * Configure the listeners for the buttons and checkboxes.
   */
  @Override
  public void init() {
    this.configureButtonListener();
    this.configureCheckBoxListener();
    this.configureChangeListener();
  }

  /**
   * Configures all of the buttons with the appropriate runnable sequences.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttons = new HashMap<String, Runnable>();
    buttons.put("Faster Button", new FasterButtonAction());
    buttons.put("Slower Button", new SlowerButtonAction());
    buttons.put("Pause Button", new PauseButtonAction());
    buttons.put("Resume Button", new ResumeButtonAction());
    buttons.put("Restart Button", new RestartButtonAction());
    buttons.put("Loop Button", new LoopButtonAction());
    buttons.put("Export Button", new ExportButtonAction());
    buttons.put("Speed Button", new SpeedSetAction());
    buttons.put("Quit Button", new QuitGameAction());
    buttons.put("Color chooser", () -> {
      Color col = JColorChooser.showDialog(((InteractiveView) view).getContentPane(),
              "Choose a color", ((InteractiveView) view).getBackground());
      view.changeBGColor(col);
    });
    ButtonListener buttonListener = new ButtonListener(buttons);
    this.buttonListener = buttonListener;
    this.view.addActionListener(buttonListener);
  }

  /**
   * Configures all of the checkboxes with the appropriate action.
   */
  private void configureCheckBoxListener() {
    Map<String, Runnable> checkBoxes = new HashMap<String, Runnable>();
    List<IAnimShape> listShapes = model.getShapesList();
    for (IAnimShape shape : listShapes) {
      checkBoxes.put(shape.getName(), () -> {
        view.toggleShape(shapes.get(shape.getName()));
        view.repaint();
      });
    }
    CheckBoxListener checkBoxListener = new CheckBoxListener(checkBoxes);
    this.checkBoxListener = checkBoxListener;
    this.view.addItemListener(checkBoxListener);
  }

  /**
   * Configures the slider bar with the appropriate action.
   */
  private void configureChangeListener() {
    ChangeListener listener = new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider src = (JSlider) e.getSource();
        if (!view.getViewState().isRunning) {
          view.pause();
          view.setTime(src.getValue());
          view.repaint();
        } else {
          view.setTime(src.getValue());
        }
      }
    };
    this.view.addChangeListener(listener);
  }

  @Override
  public CheckBoxListener getCheckBoxListener() {
    return checkBoxListener;
  }

  @Override
  public ButtonListener getButtonListener() {
    return buttonListener;
  }


  @Override
  public InteractiveView.AnimationState getState() {
    return view.getViewState();
  }

  /**
   * Action to use when the Faster Button is pressed.
   */
  class FasterButtonAction implements Runnable {
    @Override
    public void run() {
      view.faster();
    }
  }

  /**
   * Action to use when the Slower Button is pressed.
   */
  class SlowerButtonAction implements Runnable {
    @Override
    public void run() {
      view.slower();
    }
  }

  /**
   * Action to use when the Pause Button is pressed.
   */
  class PauseButtonAction implements Runnable {
    @Override
    public void run() {
      view.pause();
    }
  }

  /**
   * Action to use when the Resume Button is pressed.
   */
  class ResumeButtonAction implements Runnable {
    @Override
    public void run() {
      view.resume();
    }
  }

  /**
   * Action to use when the Restart Button is pressed.
   */
  class RestartButtonAction implements Runnable {
    @Override
    public void run() {
      view.restart();
    }
  }

  /**
   * Action to use when the Loop Button is pressed.
   */
  class LoopButtonAction implements Runnable {
    @Override
    public void run() {
      view.toggleLoop();
    }
  }

  /**
   * Action to use when the Export Button is pressed.
   */
  class ExportButtonAction implements Runnable {
    @Override
    public void run() {
      view.export();
    }
  }

  /**
   * Action to use when the Speed Set Button is pressed.
   */
  class SpeedSetAction implements Runnable {
    @Override
    public void run() {
      view.setSpeed();
    }
  }

  /**
   * Action to use when the Quit Set Button is pressed.
   */
  class QuitGameAction implements Runnable {
    @Override
    public void run() {
      view.quit();
    }
  }
}
