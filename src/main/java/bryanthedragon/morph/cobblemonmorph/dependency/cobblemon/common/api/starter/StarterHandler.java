package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory
import net.minecraft.server.level.ServerPlayer

public interface StarterHandler {
   public abstract fun getStarterList(player: ServerPlayer): List<StarterCategory> {
   }

   public abstract fun handleJoin(player: ServerPlayer) {
   }

   public abstract fun requestStarterChoice(player: ServerPlayer) {
   }

   public abstract fun chooseStarter(player: ServerPlayer, categoryName: String, index: Int) {
   }
}
