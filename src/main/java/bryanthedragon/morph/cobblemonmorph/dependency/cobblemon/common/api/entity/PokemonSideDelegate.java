package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity

public interface PokemonSideDelegate : EntitySideDelegate<PokemonEntity> {
   public abstract fun changePokemon(pokemon: Pokemon) {
   }

   public open fun drop(source: DamageSource?) {
   }

   public open fun updatePostDeath() {
   }

   public open fun handleStatus(status: Byte) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun drop(`$this`: PokemonSideDelegate, source: DamageSource?) {
      }

      @JvmStatic
      fun updatePostDeath(`$this`: PokemonSideDelegate) {
      }

      @JvmStatic
      fun handleStatus(`$this`: PokemonSideDelegate, status: Byte) {
      }

      @JvmStatic
      fun initialize(`$this`: PokemonSideDelegate, entity: PokemonEntity) {
         EntitySideDelegate.DefaultImpls.initialize(`$this`, entity as Entity);
      }

      @JvmStatic
      fun tick(`$this`: PokemonSideDelegate, entity: PokemonEntity) {
         EntitySideDelegate.DefaultImpls.tick(`$this`, entity as Entity);
      }

      @JvmStatic
      fun onTrackedDataSet(`$this`: PokemonSideDelegate, data: EntityDataAccessor<?>) {
         EntitySideDelegate.DefaultImpls.onTrackedDataSet(`$this`, data);
      }
   }
}
