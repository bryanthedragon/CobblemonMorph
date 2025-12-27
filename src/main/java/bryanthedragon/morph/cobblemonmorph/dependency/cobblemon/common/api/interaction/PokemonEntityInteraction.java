package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ItemStack

public interface PokemonEntityInteraction : EntityInteraction<PokemonEntity> {
   public val accepted: Set<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction.Ownership>

   public open val sound: SoundEvent?
      public open get() {
      }


   public open fun onInteraction(player: ServerPlayer, entity: PokemonEntity, stack: ItemStack): Boolean {
   }

   public abstract fun processInteraction(player: ServerPlayer, entity: PokemonEntity, stack: ItemStack): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun getSound(`$this`: PokemonEntityInteraction): SoundEvent? {
         return CobblemonSounds.ITEM_USE;
      }

      @JvmStatic
      fun onInteraction(`$this`: PokemonEntityInteraction, player: ServerPlayer, entity: PokemonEntity, stack: ItemStack): Boolean {
         val storeCoordinates: StoreCoordinates = entity.getPokemon().getStoreCoordinates().get();
         val ownership: PokemonEntityInteraction.Ownership = if (storeCoordinates == null)
            PokemonEntityInteraction.Ownership.WILD
            else
            (
               if (storeCoordinates.getStore().getUuid() == player.m_20148_())
                  PokemonEntityInteraction.Ownership.OWNER
                  else
                  PokemonEntityInteraction.Ownership.OWNED_ANOTHER
            );
         return `$this`.getAccepted().contains(ownership) && `$this`.processInteraction(player, entity, stack);
      }

      @JvmStatic
      fun consumeItem(`$this`: PokemonEntityInteraction, player: ServerPlayer, stack: ItemStack, amount: Int) {
         EntityInteraction.DefaultImpls.consumeItem(`$this`, player, stack, amount);
      }
   }

   public enum Ownership {
      OWNER,
      OWNED_ANOTHER,
      WILD   }
}
