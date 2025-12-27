package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

public open class CandyExperienceSource(player: ServerPlayer, stack: ItemStack) : ExperienceSource {
   public final val player: ServerPlayer
   public final val stack: ItemStack

   init {
      this.player = player;
      this.stack = stack;
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
