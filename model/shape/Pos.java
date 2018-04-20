package cs3500.animator.model.shape;

import java.util.Objects;

/**
 * Represents Cartesian coordinate pairs.
 */
public class Pos implements IPos {
  private final double x;
  private final double y;

  /**
   * Create a constructor which take in a x coordinate and y coordinate.
   *
   * @param x x coordinate.
   * @param y y coordinate.
   */
  public Pos(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "(" + this.x + "," + this.y + ")";
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Pos)) {
      return false;
    }
    Pos o = (Pos) other;
    return Math.abs(this.x - o.x) < 0.01 && Math.abs(this.y - o.y) < 0.01;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }

  @Override
  public IPos copy() {
    return new Pos(this.x, this.y);
  }
}
