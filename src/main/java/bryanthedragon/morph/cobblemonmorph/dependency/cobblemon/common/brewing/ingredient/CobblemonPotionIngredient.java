package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient

import java.util.ArrayList;
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionUtils
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
public class CobblemonPotionIngredient(potion: Potion) : CobblemonIngredient {
   public final val potion: Potion

   init {
      this.potion = potion;
   }

   public override fun matches(stack: ItemStack): Boolean {
      return PotionUtils.m_43579_(stack) == this.potion;
   }

   public override fun matchingStacks(): List<ItemStack> {
      val list: ArrayList = new ArrayList();
      list.add(PotionUtils.m_43549_(Items.f_42589_.m_7968_(), this.potion));
      list.add(PotionUtils.m_43549_(Items.f_42736_.m_7968_(), this.potion));
      list.add(PotionUtils.m_43549_(Items.f_42739_.m_7968_(), this.potion));
      return list;
   }
}
