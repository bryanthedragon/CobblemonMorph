package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.CachedLevelThresholds
import kotlin.jvm.functions.Function1
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public abstract class CachedExperienceGroup : ExperienceGroup {
   private final val thresholds: CachedLevelThresholds = new CachedLevelThresholds(0, (new Function1<Integer, Integer>(this) {
      {
         super(1, receiver, CachedExperienceGroup::class.java, "getExperience", "getExperience(I)I", 0);
      }

      @NotNull
      public final Integer invoke(int p0) {
         return (this.receiver as CachedExperienceGroup).getExperience(p0);
      }
   }) as Function1, 1, null)

   public override fun getLevel(experience: Int): Int {
      return this.thresholds.getLevel(experience);
   }

   override fun getTranslatedName(): MutableComponent {
      return ExperienceGroup.DefaultImpls.getTranslatedName(this);
   }
}
