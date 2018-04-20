package cs3500.animator.model;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IAnimShape;

/**
 * Creates animation models.
 */
public class AnimationModelCreator {
  public static IAnimationModel<IAnimShape, IAnimation> create() {
    return AnimationModel.builder().build();
  }
}
