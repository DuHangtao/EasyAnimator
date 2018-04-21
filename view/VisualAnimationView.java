package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import java.util.HashMap;
import java.util.List;

import cs3500.animator.model.IAnimationModelReadOnly;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IAnimShape;

/**
 * Shows the animation visually when the program is run.
 */
public class VisualAnimationView extends JFrame implements IAnimationView<IAnimShape> {
  protected IAnimationModelReadOnly<IAnimShape, IAnimation> model;
  protected int rate;
  protected Timer timer;
  protected AnimationPanel animationsPanel;
  private JScrollPane scrollPane;
  protected boolean loop;
  protected int time;

  protected HashMap<String, IAnimShape> visibleShapes;

  /**
   * The visual view takes in a read only model and the tempo. Drawing the animation depends on the
   * information given by the model and tempo.
   *
   * @param model the read-only model.
   * @param rate  the given tempo of the animation.
   */
  VisualAnimationView(IAnimationModelReadOnly<IAnimShape, IAnimation> model, int rate) {
    super();
    this.model = model;
    this.rate = rate;
    this.time = 0;

    this.timer = new Timer(1000 / this.rate, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        advance();
      }
    });
    this.loop = false;
    this.visibleShapes = model.getShapes();

    this.setTitle("ShapeAnimations!");
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    init();
  }

  /**
   * Add the model into the panel, also implement the time into the panel. Make the visibility into
   * true, so that we can see the animation when we running.
   */
  private void init() {
    animationsPanel = new AnimationPanel();
    scrollPane = new JScrollPane(animationsPanel);

    this.add(scrollPane, BorderLayout.CENTER);
    this.repaint();
  }

  @Override
  public String viewText(boolean isAbleToLoopback, String SvgBG) {
    return "No view text applicable.";
  }

  @Override
  public void start() {
    timer.start();
    this.setVisible(true);
  }

  @Override
  public void setShapesMap(HashMap<String, IAnimShape> visibleShapes) {
    this.visibleShapes = visibleShapes;
  }

  @Override
  public void setRate(int currentRate) {
    this.rate = currentRate;
  }

  /**
   * Advances the animation forward by one tick.
   */
  protected void advance() {
    animationsPanel.advance();
    scrollPane.repaint();
  }

  /**
   * Panel where the animation happens.
   */
  class AnimationPanel extends JPanel {
    List<List<IAnimShape>> list;
    List<List<List<IAnimShape>>> layeredList;
    int endTime;

    public AnimationPanel() {
      super();
      this.setLayout(new BorderLayout());
      this.setBackground(Color.WHITE);
      this.setPreferredSize(new Dimension(1000, 1000));
      this.endTime = model.endTime();

      this.list = model.getShapesListAllTicks();
      this.layeredList = model.getLayeredAllTicks();
    }

    /**
     * Advances time forward by one tick.
     */
    protected void advance() {
      time++;
    }

    /**
     * Restarts the animation by turning time back to the start.
     */
    protected void restart() {
      time = 0;
      timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (loop && time > endTime) {
        this.restart();
        return;
      } else if (time > endTime) {
        timer.stop();
        return;
      }

      for (List<IAnimShape> list : layeredList.get(time)) {
        for (IAnimShape s : list) {
          if (visibleShapes.containsKey(s.getName())) {
            if (time >= s.getAppears() && time <= s.getDisappears()) {
              g.setColor(s.getColor());
              String shape = s.getShape().getType();
              if (shape.equals("rectangle")) {
                g.fillRect((int) s.getPos().getX(), (int) s.getPos().getY(),
                        (int) s.allDimensions()[0], (int) s.allDimensions()[1]);
              } else if (shape.equals("oval")) {
                g.fillOval((int) (s.getPos().getX() - 0.5 * s.allDimensions()[0]),
                        (int) (s.getPos().getY() + 0.5 * s.allDimensions()[1]),
                        (int) s.allDimensions()[0], (int) s.allDimensions()[1]);
              } else {
                System.out.println(shape + " is not known.");
              }
            }
          }
        }
      }
    }
  }
}
