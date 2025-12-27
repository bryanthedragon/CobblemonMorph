package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import net.minecraft.world.level.Level

public enum MoonPhase {
   FULL_MOON,
   WANING_GIBBOUS,
   THIRD_QUARTER,
   WANING_CRESCENT,
   NEW_MOON,
   WAXING_CRESCENT,
   FIRST_QUARTER,
   WAXING_GIBBOUS   @JvmStatic
   public MoonPhase.Companion Companion = new MoonPhase.Companion(null);
   @JvmStatic
   private MoonPhase[] VALUES = values();

   public companion object {
      private final val VALUES: Array<MoonPhase>

      public fun ofWorld(world: Level): MoonPhase {
         return MoonPhase.access$getVALUES$cp()[world.m_46941_()];
      }
   }
}
