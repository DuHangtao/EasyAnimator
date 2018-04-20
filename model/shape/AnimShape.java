package cs3500.animator.model.shape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import cs3500.animator.model.util.Util;


/**
 * Represents a single object in the animation.
 */
public class AnimShape implements IAnimShape {
  private final String name;
  private Color color;
  private IPos pos;
  private final int appears;
  private final int disappears;
  private final IShape shape;
  private final int layer;
  private String allAttributesOriginalValues;

  /**
   * Creates a new AnimShape with the given parameters.
   *
   * @param name       unique name of the AnimShape. Must match the one given when adding to model
   * @param color      color of this AnimShape
   * @param pos        (x,y) position of this AnimShape
   * @param appears    the time this object appears in the animation
   * @param disappears the time this object disappears in the animation
   * @param shape      the shape of this object
   * @param layer      the layer the shape should be drawn on
   */
  public AnimShape(String name, Color color, IPos pos,
                   int appears, int disappears, IShape shape, int layer) {
    if (appears < 0 || disappears < 0) {
      throw new IllegalArgumentException("Objects cannot appear or disappear at a t < 0.");
    }
    this.name = name;
    this.color = color;
    this.pos = pos;
    this.appears = appears;
    this.disappears = disappears;
    this.shape = shape;
    this.allAttributesOriginalValues = "";
    this.layer = layer;
  }

  /**
   * Creates a new AnimShape with the given parameters and a default layer of 0.
   *
   * @param name       unique name of the AnimShape. Must match the one given when adding to model
   * @param color      color of this AnimShape
   * @param pos        (x,y) position of this AnimShape
   * @param appears    the time this object appears in the animation
   * @param disappears the time this object disappears in the animation
   * @param shape      the shape of this object
   */
  public AnimShape(String name, Color color, IPos pos,
                   int appears, int disappears, IShape shape) {
    this(name, color, pos, appears, disappears, shape, 0);
  }

  /**
   * Copy constructor.
   *
   * @param other copy of this AnimShape
   */
  private AnimShape(AnimShape other) {
    this.name = other.name;
    this.color = other.color;
    this.pos = other.getPos().copy();
    this.appears = other.appears;
    this.disappears = other.disappears;
    this.shape = other.shape.copy();
    this.layer = other.layer;
  }

  @Override
  public IAnimShape copy() {
    return new AnimShape(name, color, pos, appears, disappears,
            shape.copy());
  }

  /**
   * Gets the name of this animation object.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Gets the position of this animation object.
   *
   * @return the position
   */
  @Override
  public IPos getPos() {
    return this.pos.copy();
  }

  /**
   * Gets the color of this animation object. Note: Color is immutable according to the library.
   *
   * @return the color
   */
  @Override
  public Color getColor() {
    return this.color;
  }

  /**
   * Gets a copy of the shape of this animation object.
   *
   * @return the shape
   */
  @Override
  public IShape getShape() {
    return this.shape.copy();
  }

  /**
   * Gets all of the dimensions of this shape.
   *
   * @return the dimensions
   */
  @Override
  public double[] allDimensions() {
    return this.shape.allDimensions();
  }

  /**
   * Gets the time of appearance of this shape in ticks.
   *
   * @return time of appearance
   */
  @Override
  public int getAppears() {
    return this.appears;
  }

  /**
   * Gets the time of disappearance of this shape in ticks.
   *
   * @return time of disappearance
   */
  @Override
  public int getDisappears() {
    return this.disappears;
  }

  @Override
  public int getLayer() {
    return this.layer;
  }

  /**
   * Moves this AnimShape to the given Pos.
   *
   * @param dest destination position
   */
  @Override
  public void move(IPos dest) {
    this.pos = dest;
  }

  /**
   * Scales this AnimShape to the given measurements.
   *
   * @param measurements new measurements to scale to - order of measurements depends on shape
   */
  @Override
  public void scale(double... measurements) {
    this.shape.scale(measurements);
  }

  /**
   * Changes the color of this AnimShape to the given Color.
   *
   * @param color the color to change this to
   */
  @Override
  public void changeColor(Color color) {
    this.color = color;
  }

  /**
   * Changes the visible aspects of this shape into those of the given shape.
   *
   * @param other the shape to change into
   */
  @Override
  public void changeInto(IAnimShape other) {
    this.move(other.getPos());
    this.changeColor(other.getColor());
    this.scale(other.allDimensions());
  }

  @Override
  public String toString() {
    return toString(1);
  }

  /**
   * Returns a String representing this AnimObject with the given rate of time.
   *
   * @param rate the number of ticks per second
   * @return String form of AnimObject
   */
  @Override
  public String toString(int rate) {
    if (rate < 1) {
      throw new IllegalArgumentException("Rate cannot be less than one.");
    }
    String x = Util.newLine();
    String ans = "";
    ans += "Name: " + this.name + x;
    ans += "Type: " + this.shape.getType() + x;
    ans += this.shape.posDesc() + ": " + this.pos.toString();
    ans += ", " + this.shape.dimensions() + ", Color: " + Util.colString(this.color) + x;
    String appearsText;
    String disappearsText;
    // the following code is for getting rid of the decimal point if no decimals are necessary
    if (rate == 1) {
      appearsText = this.appears + "s";
      disappearsText = this.disappears + "s";
    } else {
      appearsText = this.appears * (double) 1 / rate + "s";
      disappearsText = this.disappears * (double) 1 / rate + "s";
    }
    ans += "Appears at t=" + appearsText + x;
    ans += "Disappears at t=" + disappearsText;
    return ans;
  }

  /**
   * Two objects in the animation are the same if they have the same name.
   *
   * @param other object to compare this to
   * @return equality or lack thereof
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AnimShape)) {
      return false;
    }
    AnimShape x = (AnimShape) other;
    return this.getName().equals(x.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.appears, this.disappears);
  }

  /**
   * Comparison function for AnimationObjects. An animation object is less than another if it
   * appears earlier. This will be used in the future for running the animations.
   *
   * @param o IAnimation object to compare to
   * @return negative if this is less, positive if this is more, 0 otherwise.
   */
  @Override
  public int compareTo(IAnimShape o) {
    return this.appears - o.getAppears();
  }

  /**
   * Return the svg text for all animation shapes depend on their positions, colors, and
   * dimensions.
   *
   * @return the svg text for all animation shapes.
   */
  @Override
  public String getSvgAnimShapeText() {
    StringBuilder animShapeText = new StringBuilder("");

    animShapeText.append("<" + this.getShape().getSvgShape());
    animShapeText.append(" id=" + "\"" + this.name + "\" ");
    animShapeText.append(this.shape.getSvgShapeCods().get(0) + "=");
    animShapeText.append("\"" + this.pos.getX() + "\" ");
    animShapeText.append(this.shape.getSvgShapeCods().get(1) + "=");
    animShapeText.append("\"" + this.pos.getY() + "\" ");
    animShapeText.append(getSvgAnimDimensionsText());
    animShapeText.append("fill=" + "\"" + "rgb" + Util.colRGBString(this.color) + "\"");
    animShapeText.append(" visibility=\"hidden\"" + ">" + Util.newLine());

    return animShapeText.toString();
  }

  /**
   * Return the svg text for all the dimensions for all the animation shapes.
   *
   * @return the svg text for all the dimensions for all the animation shapes.
   */
  private String getSvgAnimDimensionsText() {
    String dimensionsText = "";
    List<String> lenNumber = this.shape.getSvgShapeLenNames();

    for (int i = 0; i < lenNumber.size(); i++) {
      dimensionsText += this.shape.getSvgShapeLenNames().get(i) + "=";
      dimensionsText += "\"" + this.shape.allDimensions()[i] + "\" ";
    }
    return dimensionsText;
  }

  /**
   * Sets the visibility of the shape and return the appropriate svg String.
   *
   * @param rate             the number of ticks in one second
   * @param isAbleToLoopBack the boolean to decide loop back or not.
   * @return the svg String with the appropriate visibility.
   */
  @Override
  public String setVisibility(int rate, boolean isAbleToLoopBack) {
    String text = "";
    String loopBase;
    if (isAbleToLoopBack) {
      loopBase = "base.begin+";
    } else {
      loopBase = "";
    }

    text += "<animate attributeType=\"xml\" attributeName=\"visibility\" to=\"visible\" ";
    text += "begin=\"" + loopBase + ((double) this.appears / rate) * 1000 + "ms\" ";
    text += "fill=\"freeze\" />" + Util.newLine();

    text += "<animate attributeType=\"xml\" attributeName=\"visibility\" to=\"hidden\" ";
    text += "begin=\"" + loopBase + ((double) this.disappears / rate) * 1000 + "ms\" ";
    text += "fill=\"freeze\" />" + Util.newLine();

    return text;
  }

  /**
   * Gets the original values of all attributes.
   *
   * @param acc the accumulated StringBuilder
   * @return the original values of all attributes.
   */
  @Override
  public StringBuilder getAllAttributesOriginalValues(StringBuilder acc) {
    List<Double> posList = new ArrayList<Double>();
    posList.add(pos.getX());
    posList.add(pos.getY());
    //allAttributesOriginalValues = "";
    StringBuilder origValues = new StringBuilder("");
    for (int i = 0; i < shape.getSvgShapeLenNames().size(); i++) {
      origValues.append("<animate attributeType=\"xml\" begin=\"base.end\"").append(
              " dur=\"100ms\" ").append(
              "attributeName=").append("\"").append(shape.getSvgShapeLenNames().get(i)).append(
              "\"").append(
              " to=" + "\"").append(shape.allDimensions()[i]).append("\" fill=\"freeze\" />")
              .append(Util.newLine());
    }
    for (int i = 0; i < shape.getSvgShapeCods().size(); i++) {
      origValues.append("<animate attributeType=\"xml\" begin=\"base.end\"").append(
              " dur=\"100ms\" ").append(
              "attributeName=\"").append(shape.getSvgShapeCods().get(i)).append("\"").append(
              " to=\"").append(posList.get(i)).append("\" fill=\"freeze\" />").append(Util
              .newLine());
    }
    origValues.append("<animate attributeType=\"xml\" begin=\"base.end\" ").append(
            "dur=\"100ms\" attributeName=\"fill\" to=\"").append("rgb").append(
            Util.colRGBString(this.color)).append(
            "\" fill=\"freeze\" />").append(Util.newLine());
    allAttributesOriginalValues = origValues.toString();
    return acc.append(allAttributesOriginalValues);
  }
}
