package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

public interface EntityInteraction<T extends Entity> {
   public abstract fun onInteraction(player: ServerPlayer, entity: Any, stack: ItemStack): Boolean {
   }

   public open fun consumeItem(player: ServerPlayer, stack: ItemStack, amount: Int = ...) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends Entity> consumeItem(`$this`: EntityInteraction<T>, player: ServerPlayer, stack: ItemStack, amount: Int) {
         if (!player.m_7500_()) {
            stack.m_41774_(amount);
         }
      }
   }
}
