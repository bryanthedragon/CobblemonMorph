package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

public class LeftoversCreatedEvent(playerEntity: ServerPlayer, leftovers: ItemStack) : Cancelable {
   public final var leftovers: ItemStack
   public final val playerEntity: ServerPlayer

   init {
      this.playerEntity = playerEntity;
      this.leftovers = leftovers;
   }
}
