package cs3500.animator.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cs3500.animator.model.IAnimationModelReadOnly;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.model.util.Util;

/**
 * Represents the animation in text form.
 */
public class TextAnimationView implements IAnimationView<IAnimShape> {
  private IAnimationModelReadOnly<IAnimShape, IAnimation> model;
  private int rate;
  private String x = Util.newLine();

  /**
   * The constructor for the text view only takes in a read only model and the rate.
   *
   * @param model the read only model.
   * @param rate the tempo of the animations.
   */
  TextAnimationView(IAnimationModelReadOnly<IAnimShape, IAnimation> model, int rate) {
    this.model = model;
    this.rate = rate;
  }

  @Override
  public String viewText(boolean isAbleToLoopback,String SVGBackGroundColor) {
    return objectsText(rate) + x + animationsText(rate);
  }

  @Override
  public void start() {
    System.out.println(this.viewText(false,
            ""));
  }

  @Override
  public void setShapesMap(HashMap<String, IAnimShape> visibleShapes) {
    // this method isn't applicable for the text view.
    return;
  }

  @Override
  public void setRate(int currentRate) {
    this.rate = currentRate;
  }

  /**
   * Gives a text representation of all of the animations in order of which they start.
   *
   * @param rate number of ticks per second
   * @return text representation of all of the animations.
   */
  private String animationsText(int rate) {
    List<IAnimation> animations = this.model.getAnimations();
    Collections.sort(animations);
    String ans = "";
    for (IAnimation a : animations) {
      ans += a.toString(rate) + x;
    }
    // takes off the final newline if the answer isn't empty
    if (ans.length() > 0) {
      ans = ans.substring(0, ans.length() - 1);
    }
    return ans;
  }

  /**
   * Gives a text representation of all of the AnimationObjects in order of which they appear.
   *
   * @param rate number of ticks per second
   * @return text representation of all of the AnimationObjects.
   */
  private String objectsText(int rate) {
    List<IAnimShape> list = this.model.getShapesList();
    Collections.sort(list);
    String ans = "Shapes:";
    for (IAnimShape a : list) {
      ans += x;
      ans += a.toString(rate);
      ans += x;
    }
    return ans;
  }
}