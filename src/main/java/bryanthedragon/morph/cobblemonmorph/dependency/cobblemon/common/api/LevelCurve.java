package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api

public interface LevelCurve {
   public abstract fun getExperience(level: Int): Int {
   }

   public abstract fun getLevel(experience: Int): Int {
   }
}
