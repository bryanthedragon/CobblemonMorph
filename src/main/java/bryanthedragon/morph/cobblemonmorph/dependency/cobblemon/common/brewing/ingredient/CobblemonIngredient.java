package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient

import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
public sealed interface CobblemonIngredient {
   public abstract fun matches(stack: ItemStack): Boolean {
   }

   public abstract fun matchingStacks(): List<ItemStack> {
   }
}
