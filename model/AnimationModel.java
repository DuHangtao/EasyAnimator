package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.AnimShape;
import cs3500.animator.model.animation.ColorAnimation;
import cs3500.animator.model.animation.MoveAnimation;
import cs3500.animator.model.animation.ScaleAnimation;
import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Pos;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.util.TweenModelBuilder;

/**
 * Implementation of the IAnimation Model. Added Builder and skipTo when details about view were
 * revealed. Each model runs a series of animations with multiple objects and multiple
 * motions/animations.
 */
public final class AnimationModel implements IAnimationModel<IAnimShape, IAnimation> {

  private HashMap<String, IAnimShape> shapes;
  private List<IAnimShape> shapesList;
  private List<IAnimation> animations;
  private HashMap<String, IAnimShape> original;
  private HashMap<Integer, List<IAnimShape>> layeredShapes;

  /**
   * A private constructor takes in nothing. Initialize all the fields.
   */
  private AnimationModel() {
    shapes = new HashMap<>();
    shapesList = new ArrayList<>();
    original = new HashMap<>();
    animations = new ArrayList<>();

    layeredShapes = new HashMap<>();
  }

  @Override
  public IAnimationModel copy() {
    AnimationModel copy = new AnimationModel();
    copy.shapes = new HashMap<>(this.shapes);
    copy.animations = new ArrayList<>(this.animations);
    return copy;
  }

  @Override
  public int endTime() {
    int endTime = 0;
    for (String name : shapes.keySet()) {
      endTime = Math.max(endTime, shapes.get(name).getDisappears());
    }
    for (IAnimation a : animations) {
      endTime = Math.max(endTime, a.getEnd());
    }
    return endTime;
  }

  /**
   * Return a new builder.
   *
   * @return a new builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * The builder class which is nested in the model.
   */
  public static final class Builder implements TweenModelBuilder<IAnimationModel> {
    IAnimationModel<IAnimShape, IAnimation> model = new AnimationModel();

    @Override
    public TweenModelBuilder<IAnimationModel> addOval(String name, float cx, float cy,
                                                      float xRadius, float yRadius,
                                                      float red, float green, float blue,
                                                      int startOfLife, int endOfLife) {
      this.model.addAnimShape(new AnimShape(name, new Color(red, green, blue), new Pos(cx, cy),
              startOfLife, endOfLife, new Oval(xRadius, yRadius)));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addOval(String name, float cx, float cy,
                                                      float xRadius, float yRadius,
                                                      float red, float green, float blue,
                                                      int startOfLife, int endOfLife, int layer) {
      this.model.addAnimShape(new AnimShape(name, new Color(red, green, blue), new Pos(cx, cy),
              startOfLife, endOfLife, new Oval(xRadius, yRadius), layer));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addRectangle(String name, float lx, float ly,
                                                           float width, float height,
                                                           float red, float green, float blue,
                                                           int startOfLife, int endOfLife) {
      this.model.addAnimShape(new AnimShape(name, new Color(red, green, blue), new Pos(lx, ly),
              startOfLife, endOfLife, new Rectangle(width, height)));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addRectangle(String name, float lx, float ly,
                                                           float width, float height,
                                                           float red, float green, float blue,
                                                           int startOfLife, int endOfLife,
                                                           int layer) {
      this.model.addAnimShape(new AnimShape(name, new Color(red, green, blue), new Pos(lx, ly),
              startOfLife, endOfLife, new Rectangle(width, height), layer));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addMove(String name,
                                                      float moveFromX, float moveFromY,
                                                      float moveToX, float moveToY,
                                                      int startTime, int endTime) {
      this.model.addAnimation(name, new MoveAnimation(startTime, endTime,
              new Pos(moveFromX, moveFromY), new Pos(moveToX, moveToY)));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addColorChange(String name,
                                                             float oldR, float oldG, float oldB,
                                                             float newR, float newG, float newB,
                                                             int startTime, int endTime) {
      this.model.addAnimation(name, new ColorAnimation(startTime, endTime,
              new Color(oldR, oldG, oldB), new Color(newR, newG, newB)));
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimationModel> addScaleToChange(String name,
                                                               float fromSx, float fromSy,
                                                               float toSx, float toSy,
                                                               int startTime, int endTime) {
      this.model.addAnimation(name, new ScaleAnimation(startTime, endTime,
              new double[]{fromSx, fromSy}, new double[]{toSx, toSy}));
      return this;
    }

    @Override
    public IAnimationModel build() {
      return this.model;
    }
  }

  @Override
  public void addAnimShape(String name, IAnimShape animObject)
          throws IllegalArgumentException {
    if (!name.equals(animObject.getName())) {
      throw new IllegalArgumentException("Names are not consistent.");
    }
    this.shapes.put(name, animObject);
    this.original.put(name, animObject.copy());
    this.shapesList.add(animObject);

    int layer = animObject.getLayer();
    if (layeredShapes.containsKey(layer)) {
      layeredShapes.get(layer).add(animObject);
    } else {
      List<IAnimShape> singleLayer = new ArrayList<>();
      singleLayer.add(animObject);
      layeredShapes.put(layer, singleLayer);
    }
  }

  @Override
  public void addAnimShape(IAnimShape animShape) {
    this.addAnimShape(animShape.getName(), animShape);
  }

  @Override
  public void addAnimation(String name, IAnimation animation)
          throws IllegalArgumentException {
    if (!this.shapes.containsKey(name)) {
      throw new IllegalArgumentException("IAnimation object of given name not found.");
    } else {
      animation.setShape(shapes.get(name));
      if (animation.conflicts(this.animations)) {
        throw new IllegalArgumentException("This animation conflicts with another one.");
      }
      this.animations.add(animation);
    }
  }

  @Override
  public void skipTo(int time) {
    for (IAnimation a : this.animations) {
      a.apply(time);
    }
  }

  @Override
  public void rewind() {
    for (String name : this.shapes.keySet()) {
      this.shapes.get(name).changeInto(this.original.get(name));
    }
  }

  @Override
  public List<IAnimation> getAnimations() {
    return new ArrayList<>(this.animations);
  }

  @Override
  public HashMap<String, IAnimShape> getShapes() {
    HashMap<String, IAnimShape> toRet = new HashMap<>();
    for (IAnimShape s : shapesList) {
      toRet.put(s.getName(), s.copy());
    }
    return toRet;
  }

  @Override
  public List<IAnimShape> getShapesList() {
    List<IAnimShape> toRet = new ArrayList<>();
    for (IAnimShape s : shapesList) {
      toRet.add(s.copy());
    }
    return toRet;
  }

  @Override
  public List<List<IAnimShape>> getShapesListAllTicks() {
    this.rewind();
    List<List<IAnimShape>> list = new ArrayList<>();
    for (int i = 0; i <= this.endTime(); i++) {
      this.skipTo(i);
      list.add(this.getShapesList());
    }
    this.rewind();
    return list;
  }

  /**
   * Gets a copy of the list of shapes depend on their layers.
   *
   * @param layer the layer of the shape which is represented by the integer.
   * @return a copy of the list of shapes depend on their layers.
   */
  private List<IAnimShape> getLayerCopy(int layer) {
    List<IAnimShape> toRet = new ArrayList<>();
    for (IAnimShape shape : layeredShapes.get(layer)) {
      toRet.add(shape.copy());
    }
    return toRet;
  }

  @Override
  public List<List<List<IAnimShape>>> getLayeredAllTicks() {
    this.rewind();

    List<Integer> layersList = this.layersList();

    List<List<List<IAnimShape>>> layeredAllTicks = new ArrayList<>();
    for (int i = 0; i <= this.endTime(); i++) {
      this.skipTo(i);
      List<List<IAnimShape>> tickList = new ArrayList<>();
      for (Integer layer : layersList) {
        tickList.add(getLayerCopy(layer));
      }
      layeredAllTicks.add(tickList);
    }
    return layeredAllTicks;
  }

  /**
   * Gets the lilayer of this shapes. Higher layers are drawn on the top.
   *
   * @return a list of layers of shapes which are represented by the integer.
   */
  private List<Integer> layersList() {
    List<Integer> layers = new ArrayList<>();
    for (IAnimShape s : this.shapesList) {
      if (!layers.contains(s.getLayer())) {
        layers.add(s.getLayer());
      }
    }
    Collections.sort(layers);
    return layers;
  }

  @Override
  public List<IAnimShape> getOriginalShapesList() {
    List<IAnimShape> toRet = new ArrayList<>();
    List<IAnimShape> shapes = sortLayerShapes(this.shapesList);
    for (IAnimShape s : shapes) {
      toRet.add(original.get(s.getName()));
    }
    return toRet;
  }

  /**
   * Remove the duplications of a list of integers.
   *
   * @param list list of layers of the specific shapes which is sorted.
   * @return a non-duplicated list of integers.
   */
  private static List<Integer> removeDuplicates(List<Integer> list) {
    ArrayList<Integer> result = new ArrayList<>();
    HashSet<Integer> set = new HashSet<>();
    for (Integer item : list) {
      if (!set.contains(item)) {
        result.add(item);
        set.add(item);
      }
    }
    return result;
  }

  /**
   * Gives a list of shapes which is sorted by their levels of layer.
   *
   * @param listShapes the given list of IAnimShapes.
   * @return a sorted shapes depends on the level of their layers.
   */
  private List<IAnimShape> sortLayerShapes(List<IAnimShape> listShapes) {
    List<IAnimShape> listOfShapes = new ArrayList<>();
    List<Integer> layerList = new ArrayList<Integer>(this.layersList());
    removeDuplicates(layerList);
    for (Integer i : layerList) {
      for (IAnimShape shape : listShapes) {
        if (shape.getLayer() == i) {
          listOfShapes.add(shape);
        }
      }
    }
    return listOfShapes;
  }
}
