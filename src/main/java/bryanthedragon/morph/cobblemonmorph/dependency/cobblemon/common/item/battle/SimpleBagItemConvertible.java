package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

public interface SimpleBagItemConvertible : BagItemConvertible {
   public val bagItem: BagItem

   public override fun getBagItem(stack: ItemStack): BagItem? {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nSimpleBagItemConvertible.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimpleBagItemConvertible.kt\ncom/cobblemon/mod/common/item/battle/SimpleBagItemConvertible$DefaultImpls\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,24:1\n1#2:25\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun getBagItem(`$this`: SimpleBagItemConvertible, stack: ItemStack): BagItem? {
         val var2: Item = stack.m_41720_();
         return if ((if (var2 == `$this`) var2 else null) != null) `$this`.getBagItem() else null;
      }

      @JvmStatic
      fun handleInteraction(`$this`: SimpleBagItemConvertible, player: ServerPlayer, battlePokemon: BattlePokemon, stack: ItemStack): Boolean {
         return BagItemConvertible.DefaultImpls.handleInteraction(`$this`, player, battlePokemon, stack);
      }
   }
}
