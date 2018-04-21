package cs3500.animator.view;

import cs3500.animator.model.IAnimationModelReadOnly;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.model.util.Util;

import java.util.HashMap;
import java.util.List;

/**
 * Implementation  of the svg view of shape animations. This view can support shapes with multiple
 * animations.
 */
public class SvgAnimationView implements IAnimationView<IAnimShape> {
  private IAnimationModelReadOnly<IAnimShape, IAnimation> model;
  private int rate;
  private HashMap<String, IAnimShape> shapesMap;
  String x = Util.newLine();

  /**
   * Initialize all the private fields.
   *
   * @param model the given model when the view is generated.
   * @param rate  represent how many ticks per second.
   */
  SvgAnimationView(IAnimationModelReadOnly<IAnimShape, IAnimation> model, int rate) {
    this.model = model;
    this.rate = rate;
    this.shapesMap = model.getShapes();
  }

  /**
   * Return a xml-based format string which include all the shapes information and animations for
   * shapes in SVG text format. The calculation is done using StringBuilder and the String is only
   * built at the end to improve performance, the background color can be changed.
   *
   * @param isAbleToLoopback   the boolean to decide to loop back or not.
   * @param SVGBackGroundColor the given background color of the svg animation.
   * @return a xml-based format string in SVG text format.
   */
  @Override
  public String viewText(boolean isAbleToLoopback, String SVGBackGroundColor) {
    StringBuilder svgText = new StringBuilder("");
    StringBuilder backGroundColor = new StringBuilder("<rect id=\"BG\" x=\"0\" y=\"0\" " +
            "width=\"10000.0\" height=\"10000.0\" fill=").append("\"rgb" +
            SVGBackGroundColor + "\"").append(
            " visibility=\"visible\">\n" + " </rect>" + x);


    svgText = svgCanvasText(svgText).append(x);
    svgText = setLoopBack(svgText, isAbleToLoopback);
    svgText = svgText.append(backGroundColor);
    svgText = svgShapeText(svgText, isAbleToLoopback);
    svgText.append("</svg>");
    return svgText.toString();
  }

  @Override
  public void start() {
    System.out.println(this.viewText(false,
            ""));
  }

  /**
   * Return a the string of canvas by the SVG text form. The size of the canvas is fixed which is
   * 700 in width and 500 in height.
   *
   * @param acc accumulated StringBuilder
   * @return a the StringBuilder of canvas by the SVG text form.
   */
  private StringBuilder svgCanvasText(StringBuilder acc) {
    return acc.append("<svg width=\"10000\" height=\"10000\" version=\"1.1\"" +
            " xmlns=\"http://www.w3.org/2000/svg\">");
  }

  /**
   * Return the string represent all the shapes and their animations. This method will first set the
   * visibility of the shapes depend on their appear time and disappear time. After that, it will
   * add a shape to the current text and add all of it's animations. Then add another shape with
   * it's on animations. It is also able to select the subset of all the shapes and animations
   * depends on the user input.
   *
   * @param acc              currently accumulated StringBuilder
   * @param isAbleToLoopBack loop or not
   * @return the StringBuilder represent all the shapes and their animations.
   */
  private StringBuilder svgShapeText(StringBuilder acc, boolean isAbleToLoopBack) {
    List<IAnimShape> shapes = this.model.getOriginalShapesList();
    for (IAnimShape aniShape : shapes) {
      if (this.shapesMap.containsKey(aniShape.getName())) {
        acc.append(aniShape.getSvgAnimShapeText());
        acc.append(aniShape.setVisibility(this.rate, isAbleToLoopBack));
        acc = svgAnimationsText(acc, aniShape, isAbleToLoopBack);
        acc.append("</").append(aniShape.getShape().getSvgShape()).append(">").append(x);
      }
    }
    return acc;
  }

  /**
   * Return all the animations of a shape in svg text form.
   *
   * @param acc              the accumulated StringBuilder.
   * @param isAbleToLoopBack the boolean to decide to loop back or not.
   * @return all the animations of a shape in svg text form.
   */
  private StringBuilder svgAnimationsText(StringBuilder acc, IAnimShape animShape, boolean
          isAbleToLoopBack) {
    List<IAnimation> listOfAnimations = this.model.getAnimations();
    for (IAnimation a : listOfAnimations) {
      if (a.getShape().getName().equals(animShape.getName())) {
        acc.append(a.svgAnimationText(this.rate, isAbleToLoopBack)).append(x);
      }
    }
    acc = animShape.getAllAttributesOriginalValues(acc);
    return acc;
  }

  /**
   * Set the loopback for all the animations.
   *
   * @param acc              the accumulated StringBuilder
   * @param isAbleToLoopBack the boolean to decide to loop back or not.
   * @return an empty string if not loop back, otherwise return a loop back base for all animations.
   */

  private StringBuilder setLoopBack(StringBuilder acc, boolean isAbleToLoopBack) {
    if (isAbleToLoopBack) {
      return acc.append("<rect>").append(x).append("<animate id=\"base\" begin=\"0;base.end\"")
              .append(" dur=\"").append(((double) model.endTime() / this.rate) * 1000)
              .append("ms\" attributeName=\"visibility\" from=\"hide\"").append(x).append(
                      " to=\"hide\"/>").append(x).append("</rect>");
    } else {
      return acc;
    }
  }

  /**
   * To synchronize the current visible shapes with the visual view.
   *
   * @param visibleShapes The current visible shapes.
   */
  @Override
  public void setShapesMap(HashMap<String, IAnimShape> visibleShapes) {
    this.shapesMap = new HashMap<String, IAnimShape>(visibleShapes);
  }

  /**
   * To synchronize the speed visible shapes with the visual view.
   *
   * @param currentRate the current rate in the visual view.
   */
  @Override
  public void setRate(int currentRate) {
    this.rate = currentRate;
  }
}


















