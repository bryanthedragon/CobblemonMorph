package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
public class CobblemonItemIngredient(item: Item) : CobblemonIngredient {
   public final val item: Item

   init {
      this.item = item;
   }

   public override fun matches(stack: ItemStack): Boolean {
      return stack.m_150930_(this.item);
   }

   public override fun matchingStacks(): List<ItemStack> {
      return CollectionsKt.listOf(this.item.m_7968_());
   }
}
