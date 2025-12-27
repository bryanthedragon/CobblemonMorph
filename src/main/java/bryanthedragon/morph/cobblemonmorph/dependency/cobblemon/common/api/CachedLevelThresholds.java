package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api

import java.util.ArrayList;

public class CachedLevelThresholds(levelLimit: Int = 1000, experienceToLevel: (Int) -> Int) {
   public final val experienceToLevel: (Int) -> Int
   public final val levelLimit: Int
   public final val savedThresholds: MutableList<Int>

   init {
      this.levelLimit = levelLimit;
      this.experienceToLevel = experienceToLevel;
      this.savedThresholds = new ArrayList<>();
   }

   public fun getLevel(experience: Int): Int {
      var level: Int;
      for (level = 1; level <= this.savedThresholds.size(); level++) {
         if (experience < this.savedThresholds.get(level - 1).intValue()) {
            return level - 1;
         }
      }

      while (level < this.levelLimit) {
         val var4: Int = (this.experienceToLevel.invoke(level) as java.lang.Number).intValue();
         this.savedThresholds.add(var4);
         if (experience < var4) {
            return level - 1;
         }

         level++;
      }

      return 1;
   }
}
