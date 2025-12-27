package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

public interface InteractiveItem<T extends Entity> {
   public abstract fun onInteraction(player: ServerPlayer, entity: Any, stack: ItemStack): Boolean {
   }
}
