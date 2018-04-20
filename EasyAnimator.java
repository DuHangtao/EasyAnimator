package cs3500.animator;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.animator.controller.IInteractiveController;
import cs3500.animator.controller.InteractiveController;
import cs3500.animator.model.AnimationFileReader;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.provider.controller.ControllerAdapter;
import cs3500.animator.provider.controller.IController;
import cs3500.animator.provider.model.ModelAdaptor;
import cs3500.animator.provider.view.HybridView;
import cs3500.animator.provider.view.IView;
import cs3500.animator.view.AnimationViewCreator;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.InteractiveView;

public final class EasyAnimator {
  /**
   * The main method to entry the program.
   * To create a command line argument.
   * In this run configuration, you can also specify command-line arguments,
   * such as the file you want to read in, and the view name you want to use.
   * @param args the argument that passed in.
   */
  public static void main(String[] args) {
    String inputFile = null;
    String inputView = null;
    String output = null;
    String speed = null;
    int rate;
    JFrame frame = new JFrame();

    if (args.length % 2 != 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    for (int i = 0; i < args.length; i += 2) {
      String arg = args[i + 1];
      switch (args[i]) {
        case "-if":
          inputFile = arg;
          break;
        case "-iv":
          inputView = arg;
          break;
        case "-o":
          output = arg;
          break;
        case "-speed":
          speed = arg;
          break;
        default:
          JOptionPane.showMessageDialog(frame, "Invalid arguments.");
          System.exit(1);
          return;
      }
    }
    if (inputFile == null || inputView == null) {
      JOptionPane.showMessageDialog(frame, "Input file and input view must be provided.");
      System.exit(1);
      return;
    }
    if (output != null && !output.equals("out")) {
      try {
        PrintStream ps = new PrintStream(new FileOutputStream(output));
        System.setOut(ps);
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(frame, "Output file not found.");
        System.exit(1);
        return;
      }
    }
    if (speed == null) {
      rate = 1;
    } else {
      try {
        rate = Integer.parseInt(speed);
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Speed should be an integer.");
        System.exit(1);
        return;
      }
    }
    AnimationFileReader reader = new AnimationFileReader();
    IAnimationModel<IAnimShape, IAnimation> model;
    float[] bg;
    try {
      model = reader.readFile(inputFile, AnimationModel.builder());
      bg = reader.getBackground(inputFile);

    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(frame, "Input file not found.");
      System.exit(1);
      return;
    }
    AnimationViewCreator viewCreator = new AnimationViewCreator(model, rate);
    if (inputView.equals("provider")) {
      cs3500.animator.provider.model.IAnimationModel adapted = new ModelAdaptor(model);
      IView view = new HybridView(rate);
      //IView view = new GUIView(rate);
      //IView view = new SVGView(adapted.getShapes(), adapted.getCommands(), new StringBuilder(""),
      //        rate, 1000, 1000);
      IController controller = new ControllerAdapter(adapted, view);
      controller.runView();
    }
    else {
      IAnimationView view = viewCreator.create(inputView);
      view.start();
      if (inputView.equals("interactive")) {
        //System.out.println(bg[0] + "," + bg[1] + "," + bg[2]);
        ((IInteractiveView) view).changeBGColor(new Color(bg[0], bg[1], bg[2]));
        IInteractiveController controller = new InteractiveController(model, (InteractiveView)view);
      }
    }
  }
}
