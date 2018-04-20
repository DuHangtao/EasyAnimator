package cs3500.animator.model.animation;

//import cs3500.animator.model.shape.AnimShape;
import cs3500.animator.model.shape.IAnimShape;
import cs3500.animator.model.shape.IPos;
import cs3500.animator.model.shape.Pos;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractAnimation of moving an AnimShape from one Pos to another.
 */
public class MoveAnimation extends AbstractAnimation {
  private IPos dest;
  private IPos origin;

  /**
   * The constructor only take in dest position.
   *
   * @param start the start time of this animation.
   * @param end   the end time of this animation.
   * @param dest  the dest location of the shape will be moved to.
   */
  public MoveAnimation(int start, int end, IPos dest) {
    super(start, end);
    this.dest = dest;
  }

  /**
   * The constructor take in both original position and the dest position.
   */
  public MoveAnimation(int start, int end, IPos origin, IPos dest) {
    this(start, end, dest);
    this.origin = origin;
  }

  @Override
  public void setShape(IAnimShape shape) {
    super.setShape(shape);
    if (origin == null) {
      origin = this.shape.getPos();
    }
  }

  @Override
  public String changeText() {
    return "moves from " + this.origin.toString() + " to " + this.dest.toString();
  }

  @Override
  public void apply() {
    this.shape.move(this.dest);
  }

  @Override
  public void apply(int time) {
    if (time > this.start && time <= this.end) {
      double newX = this.curVal(time, origin.getX(), dest.getX());
      double newY = this.curVal(time, origin.getY(), dest.getY());
      this.shape.move(new Pos(newX, newY));
    }
  }

  @Override
  public String getAttributeName(int index) {
    List<String> codNames = this.shape.getShape().getSvgShapeCods();
    return "\"" + codNames.get(index) + "\"";
  }

  @Override
  public String fromValue(int index) {
    List<Double> formCods = new ArrayList<>();
    formCods.add(origin.getX());
    formCods.add(origin.getY());
    return "\"" + formCods.get(index) + "\"";
  }

  @Override
  public String toValue(int index) {
    List<Double> toCods = new ArrayList<>();
    toCods.add(dest.getX());
    toCods.add(dest.getY());
    return "\"" + toCods.get(index) + "\"";
  }

  @Override
  public int totalAttributesNumber() {
    return 2;
  }
}
