package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity

import java.util.EnumSet

public enum PoseType {
   STAND,
   WALK,
   SLEEP,
   HOVER,
   FLY,
   FLOAT,
   SWIM,
   SHOULDER_LEFT,
   SHOULDER_RIGHT,
   PROFILE,
   PORTRAIT,
   OPEN,
   NONE   @JvmStatic
   public PoseType.Companion Companion = new PoseType.Companion(null);
   @JvmStatic
   private EnumSet<PoseType> ALL_POSES = EnumSet.allOf(PoseType.class);
   @JvmStatic
   private EnumSet<PoseType> FLYING_POSES = EnumSet.of(PoseType.FLY, PoseType.HOVER);
   @JvmStatic
   private EnumSet<PoseType> SWIMMING_POSES = EnumSet.of(PoseType.SWIM, PoseType.FLOAT);
   @JvmStatic
   private EnumSet<PoseType> STANDING_POSES = EnumSet.of(PoseType.STAND, PoseType.WALK);
   @JvmStatic
   private EnumSet<PoseType> SHOULDER_POSES = EnumSet.of(PoseType.SHOULDER_LEFT, PoseType.SHOULDER_RIGHT);
   @JvmStatic
   private EnumSet<PoseType> UI_POSES = EnumSet.of(PoseType.PROFILE, PoseType.PORTRAIT);
   @JvmStatic
   private EnumSet<PoseType> MOVING_POSES = EnumSet.of(PoseType.WALK, PoseType.SWIM, PoseType.FLY);
   @JvmStatic
   private EnumSet<PoseType> STATIONARY_POSES = EnumSet.of(PoseType.STAND, PoseType.FLOAT, PoseType.HOVER);

   public companion object {
      public final val ALL_POSES: EnumSet<PoseType>
      public final val FLYING_POSES: EnumSet<PoseType>
      public final val MOVING_POSES: EnumSet<PoseType>
      public final val SHOULDER_POSES: EnumSet<PoseType>
      public final val STANDING_POSES: EnumSet<PoseType>
      public final val STATIONARY_POSES: EnumSet<PoseType>
      public final val SWIMMING_POSES: EnumSet<PoseType>
      public final val UI_POSES: EnumSet<PoseType>
   }
}
