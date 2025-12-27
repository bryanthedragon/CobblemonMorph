package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import net.minecraft.commands.SharedSuggestionProvider

public open class CommandExperienceSource(source: SharedSuggestionProvider) : ExperienceSource {
   public final val source: SharedSuggestionProvider

   init {
      this.source = source;
   }

   override fun isBattle(): Boolean {
      return ExperienceSource.DefaultImpls.isBattle(this);
   }

   override fun isInteraction(): Boolean {
      return ExperienceSource.DefaultImpls.isInteraction(this);
   }

   override fun isCommand(): Boolean {
      return ExperienceSource.DefaultImpls.isCommand(this);
   }

   override fun isSidemod(): Boolean {
      return ExperienceSource.DefaultImpls.isSidemod(this);
   }
}
