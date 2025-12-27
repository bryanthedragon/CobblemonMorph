package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.misc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent.Post
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

public object GimmighoulStashHandler {
   public final val BLOCK_VALUE: Int = INGOT_VALUE * 9
   public final val COIN_VALUE: Int = 1
   public final val INGOT_VALUE: Int = SCRAP_VALUE * 4
   public final val POUCH_VALUE: Int = COIN_VALUE * 9
   public final val SACK_VALUE: Int = POUCH_VALUE * 9
   public final val SCRAP_VALUE: Int = 1

   public fun interactMob(player: Player, hand: InteractionHand, pokemon: Pokemon): Boolean {
      val itemStack: ItemStack = player.m_21120_(hand);
      var success: Boolean = false;
      if (player is ServerPlayer && pokemon.getOwnerPlayer() == player) {
         val var10002: Item = itemStack.m_41720_();
         success = this.handleItem(pokemon, var10002);
         if (success) {
            itemStack.m_41774_(1);
         }
      }

      return success;
   }

   public fun giveHeldItem(event: Post) {
      val pokemon: Pokemon = event.getPokemon();
      val item: Item = event.getReceived().m_41720_();
      if (this.handleItem(pokemon, item)) {
         pokemon.removeHeldItem();
      }
   }

   public fun handleItem(pokemon: Pokemon, item: Item): Boolean {
      val goldHoard: IntSpeciesFeature = pokemon.getFeature("gimmighoul_coins");
      val netheriteHoard: IntSpeciesFeature = pokemon.getFeature("gimmighoul_netherite");
      if (goldHoard != null && goldHoard.getValue() < 999) {
         val increase: Int = if (item == CobblemonItems.RELIC_COIN)
            COIN_VALUE
            else
            (if (item == CobblemonItems.RELIC_COIN_POUCH) POUCH_VALUE else (if (item == CobblemonItems.RELIC_COIN_SACK) SACK_VALUE else 0));
         if (increase != 0) {
            goldHoard.setValue(goldHoard.getValue() + increase);
            if (goldHoard.getValue() > 999) {
               goldHoard.setValue(999);
            }

            if (pokemon.getEntity() != null) {
               val var8: PokemonEntity = pokemon.getEntity();
               var8.m_5496_(CobblemonSounds.GIMMIGHOUL_GIVE_ITEM_SMALL, 1.0F, 1.0F);
            }

            pokemon.markFeatureDirty(goldHoard);
            return true;
         }
      }

      if (netheriteHoard != null && netheriteHoard.getValue() < 256) {
         val var7: Int = if (item == Items.f_42419_)
            SCRAP_VALUE
            else
            (if (item == Items.f_42418_) INGOT_VALUE else (if (item == Items.f_42791_) BLOCK_VALUE else 0));
         if (var7 != 0) {
            netheriteHoard.setValue(netheriteHoard.getValue() + var7);
            if (netheriteHoard.getValue() > 256) {
               netheriteHoard.setValue(256);
            }

            if (pokemon.getEntity() != null) {
               val var10000: PokemonEntity = pokemon.getEntity();
               var10000.m_5496_(CobblemonSounds.GIMMIGHOUL_GIVE_ITEM_SMALL, 1.0F, 1.0F);
            }

            pokemon.markFeatureDirty(netheriteHoard);
            return true;
         }
      }

      return false;
   }
}
