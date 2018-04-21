package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import cs3500.animator.model.IAnimationModelReadOnly;
import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.model.util.Util;

/**
 * Shows the animation visually while allowing the user to interact with it through a series of
 * buttons. Allows the user to export the animation in svg format to a specified file.
 */
public class InteractiveView extends VisualAnimationView
        implements IInteractiveView<InteractiveView.AnimationState> {
  private SvgAnimationView svg;
  private String svgString;
  private JButton fasterButton;
  private JButton slowerButton;
  private JLabel speedLabel;
  private JButton pauseButton;
  private JButton resumeButton;
  private JButton restartButton;
  private JButton loopButton;
  private JButton quitButton;

  private JButton exportButton;
  private JTextField exportInput;
  private JLabel exportResponse;

  private JButton speedSetButton;
  private JTextField speedInput;

  private JCheckBox[] checkBoxes;

  private JFrame errorMessageWindow = new JFrame();

  private JSlider scrubSlider;

  private String SVGBackGroundColor;

  private JButton colorChooserButton = new JButton();


  /**
   * Constructs the interactive view with the given read-only model, rate, and svg.
   *
   * @param model The read-only model to use.
   * @param rate  The rate at which the animation runs (ticks per second).
   * @param svg   The SVG view to use for necessary methods. This is normally passed in by the
   *              AnimationViewCreator when creating this.
   */
  public InteractiveView(IAnimationModelReadOnly model, int rate, SvgAnimationView svg) {
    super(model, rate);
    svgString = "";
    this.svg = svg;
    SVGBackGroundColor = "(255,255,255)";
    init();
  }

  /**
   * Initializes the animation view with the appropriate swing elements.
   */
  private void init() {
    List<IAnimShape> shapesList = model.getShapesList();
    checkBoxes = new JCheckBox[model.getShapesList().size()];
    errorMessageWindow = new JFrame();
    this.setVisible(false);
    this.pause();

    // Add all button to the button panel.
    ButtonsPanel buttonsPanel = new ButtonsPanel();

    // Add speed up button.
    fasterButton = new JButton("Speed up");
    fasterButton.setActionCommand("Faster Button");
    buttonsPanel.add(fasterButton);

    // Add speed down button.
    slowerButton = new JButton("Slow down");
    slowerButton.setActionCommand("Slower Button");
    buttonsPanel.add(slowerButton);

    //Set the speed directly by typing the integers.
    JLabel speedSetInfo = new JLabel("Set speed to:");
    buttonsPanel.add(speedSetInfo);

    //Text field that accept the speed that players want to be.
    speedInput = new JTextField(10);
    buttonsPanel.add(speedInput);

    speedSetButton = new JButton("Set Speed");
    speedSetButton.setActionCommand("Speed Button");
    buttonsPanel.add(speedSetButton);

    speedLabel = new JLabel("");
    updateSpeedLabel();
    buttonsPanel.add(speedLabel);

    // Add Resume button with the same function with start.
    resumeButton = new JButton("Start");
    resumeButton.setActionCommand("Resume Button");
    buttonsPanel.add(resumeButton);

    // Add pause button.
    pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("Pause Button");
    buttonsPanel.add(pauseButton);

    // Add restart button.
    restartButton = new JButton("Restart");
    restartButton.setActionCommand("Restart Button");
    buttonsPanel.add(restartButton);

    // Add loop button.
    loopButton = new JButton("Start Looping");
    loopButton.setActionCommand("Loop Button");
    buttonsPanel.add(loopButton);

    // Add export file button.
    JLabel exportInfo = new JLabel("File to export to:");
    buttonsPanel.add(exportInfo);

    // Add new text field that player can type the name of the file.
    exportInput = new JTextField(10);
    buttonsPanel.add(exportInput);

    exportButton = new JButton("Export");
    exportButton.setActionCommand("Export Button");
    buttonsPanel.add(exportButton);

    exportResponse = new JLabel("No export done yet");
    buttonsPanel.add(exportResponse);

    //Add quit Button
    quitButton = new JButton();
    quitButton = new JButton("Quit Button");
    quitButton.setActionCommand("Quit Button");
    buttonsPanel.add(quitButton);

    // Add slider
    buttonsPanel.add(new JLabel("Animation Progress:"));
    this.scrubSlider = new JSlider(JSlider.HORIZONTAL, 0, this.model.endTime(), 0);
    Hashtable labelTable = new Hashtable();
    labelTable.put(new Integer(0), new JLabel("Start"));
    labelTable.put(new Integer(this.model.endTime()), new JLabel("End"));
    scrubSlider.setLabelTable(labelTable);
    scrubSlider.setPaintLabels(true);
    buttonsPanel.add(scrubSlider);

    //Add a button to choose the background color.
    this.colorChooserButton = new JButton("Choose a color");
    colorChooserButton.setActionCommand("Color chooser");
    buttonsPanel.add(colorChooserButton);

    //Set a label for the color chooser.
    JLabel backgroundLabel;
    backgroundLabel = new JLabel("Choose a background color");
    buttonsPanel.add(backgroundLabel);

    //add all the button panel to the main frame.
    this.add(buttonsPanel, BorderLayout.EAST);

    //Add check boxes for selecting shapes.
    JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setLayout(new GridLayout(10, 15));
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Selecting Shapes!"));

    JLabel checkboxDisplay = new JLabel("Please choose the shapes you want see");
    checkBoxPanel.add(checkboxDisplay);

    //Go through all the shapes,name the check boxes with the same names as shapes.
    for (int i = 0; i < checkBoxes.length; i++) {
      checkBoxes[i] = new JCheckBox(shapesList.get(i).getName());
      checkBoxes[i].setSelected(true);
      checkBoxes[i].setActionCommand(shapesList.get(i).getName());
      checkBoxPanel.add(checkBoxes[i]);
    }

    //Add scroll bar to the check box panel.
    JScrollPane checkBoxesScrollPane = new JScrollPane(checkBoxPanel);

    //Add the check panel to the main frame.
    this.add(checkBoxesScrollPane, BorderLayout.PAGE_END);

    this.setVisible(true);
  }


  @Override
  public String viewText(boolean isAbleToLoopback, String SVGBackGroundColor) {
    return svg.viewText(this.loop, this.SVGBackGroundColor);
  }

  /**
   * Adds the listener to all of the buttons.
   *
   * @param actionListener the ActionListener for the buttons.
   */
  @Override
  public void addActionListener(ActionListener actionListener) {
    fasterButton.addActionListener(actionListener);
    slowerButton.addActionListener(actionListener);
    pauseButton.addActionListener(actionListener);
    resumeButton.addActionListener(actionListener);
    restartButton.addActionListener(actionListener);
    loopButton.addActionListener(actionListener);
    exportButton.addActionListener(actionListener);
    speedSetButton.addActionListener(actionListener);
    quitButton.addActionListener(actionListener);
    colorChooserButton.addActionListener(actionListener);
  }

  /**
   * Adds the listener to all of the subset checkboxes.
   *
   * @param itemListener the ItemListener for the checkboxes.
   */
  @Override
  public void addItemListener(ItemListener itemListener) {
    for (JCheckBox checkBox : checkBoxes) {
      checkBox.addItemListener(itemListener);
    }
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    scrubSlider.addChangeListener(changeListener);
  }

  @Override
  public void setTime(int tick) {
    this.time = tick;
  }

  @Override
  protected void advance() {
    super.advance();
    this.scrubSlider.setValue(this.time);
  }

  /**
   * Speeds up the animation by a factor of 2.
   */
  @Override
  public void faster() {
    this.rate *= 2;
    if (this.rate > 1000) {
      this.rate = 1000;
    }
    this.timer.setDelay(1000 / this.rate);
    updateSpeedLabel();
    this.svg.setRate(this.rate);
  }

  /**
   * Slows down the animation by a factor of 2.
   */
  @Override
  public void slower() {
    if (this.rate > 1) {
      this.rate /= 2;
    }
    this.timer.setDelay(1000 / this.rate);
    updateSpeedLabel();
    this.svg.setRate(this.rate);
  }

  /**
   * Updates the speed label with the current speed.
   */
  @Override
  public void updateSpeedLabel() {
    this.speedLabel.setText("Current speed: " + this.rate + " ticks/s");
  }

  /**
   * Pauses a running animation.
   */
  @Override
  public void pause() {
    this.timer.stop();
  }

  /**
   * Resumes a paused or not started animation.
   */
  @Override
  public void resume() {

    this.timer.start();
    this.resumeButton.setText("Resume");
  }

  /**
   * Restarts the animation from the beginning.
   */
  @Override
  public void restart() {
    this.animationsPanel.restart();
    this.animationsPanel.repaint();
  }

  /**
   * Quit the game button.
   */
  @Override
  public void quit() {
    System.exit(1);
  }

  /**
   * Toggles the looping for the animation.
   */
  @Override
  public void toggleLoop() {
    if (this.loop) {
      loopButton.setText("Start looping");
    } else {
      loopButton.setText("Stop looping");
    }
    this.loop = !this.loop;
  }

  /**
   * Updating the speed by the given speed which is typed by player.
   */
  @Override
  public void setSpeed() {
    // Set to the default speed when inout is empty.
    if (speedInput.getText().equals("")) {
      this.rate = 20;
    } else {
      try {
        this.rate = Integer.parseInt(speedInput.getText());
        // Rate can not be 0, set to default speed.
        if (this.rate == 0) {
          this.rate = 20;
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(errorMessageWindow,
                "Speed should be an integer, please try again");
      }
    }
    if (this.rate > 1000) {
      this.rate = 1000;
    }
    this.svg.setRate(this.rate);
    this.timer.setDelay(1000 / this.rate);
    updateSpeedLabel();
  }

  /**
   * Exports the svg of the animation to the specified file.
   */
  @Override
  public void export() {
    //Synchronize the svg file speed to the current speed.
    this.svg.setShapesMap(this.visibleShapes);

    PrintStream origOut = System.out; // this may or may not be the console
    try {
      PrintStream ps;
      ps = new PrintStream(new FileOutputStream(exportInput.getText() + ".svg"));
      System.setOut(ps);
      exportResponse.setText("Exported to " + exportInput.getText());
    } catch (FileNotFoundException e) {
      exportResponse.setText("Export unsuccessful");
      return;
    }
    this.svgString = this.svg.viewText(this.loop, SVGBackGroundColor);
    System.out.println(this.svgString);
    System.setOut(origOut);

  }

  /**
   * Toggles the visibility of the given shape.
   *
   * @param shape the shape to change visibility for.
   */
  @Override
  public void toggleShape(IAnimShape shape) {
    if (this.visibleShapes.containsKey(shape.getName())) {
      visibleShapes.remove(shape.getName(), shape);
    } else {
      visibleShapes.put(shape.getName(), shape);
    }
  }

  @Override
  public void start() {
    this.setVisible(true);
  }

  @Override
  public void changeBGColor(Color color) {
    this.animationsPanel.setBackground(color);
    this.SVGBackGroundColor = Util.colRGBString(
            new Color(color.getRed(), color.getGreen(), color.getBlue()));
  }

  /**
   * Holds all of the buttons.
   */
  class ButtonsPanel extends JPanel {
    public ButtonsPanel() {
      super();
      this.setBackground(Color.YELLOW);
      this.setPreferredSize(new Dimension(200, 200));
    }
  }

  /**
   * Shows the current state of the animation, represented as a collection of conditions. The fields
   * are public for convenience but final to prevent altering.
   */
  public class AnimationState {
    public final boolean isRunning;
    public final boolean isLooping;
    public final int rate;
    public final int time;
    public final int numOfShapes;
    public final String svg;

    /**
     * Creates an object to represent the current state of the animation.
     *
     * @param isRunning   is the animation running
     * @param isLooping   is the animation looping
     * @param rate        the ticks per second of the animation
     * @param time        the current time of the animation
     * @param numOfShapes the number of shapes set to visible in the animation
     * @param svg         the last exported svg string
     */
    public AnimationState(boolean isRunning, boolean isLooping, int rate, int time,
                          int numOfShapes, String svg) {
      this.isRunning = isRunning;
      this.isLooping = isLooping;
      this.rate = rate;
      this.time = time;
      this.numOfShapes = numOfShapes;
      this.svg = svg;
    }
  }

  /**
   * Gets the current AnimationState of this view.
   *
   * @return the current AnimationState
   */
  @Override
  public AnimationState getViewState() {
    return new AnimationState(timer.isRunning(), loop, rate, time,
            visibleShapes.size(), svgString);
  }
}