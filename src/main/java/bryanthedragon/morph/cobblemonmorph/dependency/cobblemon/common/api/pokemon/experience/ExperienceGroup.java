package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.LevelCurve
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.Locale
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public interface ExperienceGroup : LevelCurve {
   public val name: String

   public open val translatedName: MutableComponent
      public open get() {
      }


   public companion object {
      public fun dummy(name: String): ExperienceGroup {
         return new ExperienceGroup(name) {
            @NotNull
            private final java.lang.String name;

            {
               this.name = `$name`;
            }

            @NotNull
            @Override
            public java.lang.String getName() {
               return this.name;
            }

            @Override
            public int getExperience(int level) {
               return 0;
            }

            @Override
            public int getLevel(int experience) {
               return 1;
            }

            @NotNull
            @Override
            public MutableComponent getTranslatedName() {
               return ExperienceGroup.DefaultImpls.getTranslatedName(this);
            }
         };
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun getTranslatedName(`$this`: ExperienceGroup): MutableComponent {
         val var10000: java.lang.String = `$this`.getName().toLowerCase(Locale.ROOT);
         val var1: MutableComponent = LocalizationUtilsKt.lang("experience_group.$var10000");
         return var1;
      }
   }
}
