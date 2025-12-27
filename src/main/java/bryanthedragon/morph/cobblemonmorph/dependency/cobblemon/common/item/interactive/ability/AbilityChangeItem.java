package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.EntityInteraction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction.Ownership
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Item.Properties

public open class AbilityChangeItem<T extends PotentialAbility>(changer: AbilityChanger<Any>) : CobblemonItem(new Properties()), PokemonEntityInteraction {
   public open val accepted: Set<Ownership>
   public final val changer: AbilityChanger<Any>

   init {
      this.changer = changer;
      this.accepted = SetsKt.setOf(PokemonEntityInteraction.Ownership.OWNER);
   }

   public override fun processInteraction(player: ServerPlayer, entity: PokemonEntity, stack: ItemStack): Boolean {
      if (this.changer.performChange(entity.getPokemon())) {
         EntityInteraction.DefaultImpls.consumeItem$default(this, player, stack, 0, 4, null);
         val var5: Array<Any> = new Object[]{entity.getPokemon().getDisplayName(), null};
         val var10003: MutableComponent = MiscUtilsKt.asTranslated(entity.getPokemon().getAbility().getDisplayName());
         var5[1] = var10003;
         player.m_213846_(LocalizationUtilsKt.lang("ability_changer.changed", var5) as Component);
         return true;
      } else {
         return false;
      }
   }

   override fun getSound(): SoundEvent? {
      return PokemonEntityInteraction.DefaultImpls.getSound(this);
   }

   override fun onInteraction(player: ServerPlayer, entity: PokemonEntity, stack: ItemStack): Boolean {
      return PokemonEntityInteraction.DefaultImpls.onInteraction(this, player, entity, stack);
   }

   override fun consumeItem(player: ServerPlayer, stack: ItemStack, amount: Int) {
      PokemonEntityInteraction.DefaultImpls.consumeItem(this, player, stack, amount);
   }
}
