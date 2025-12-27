package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

public interface EntityQueryRequirement : EvolutionRequirement {
   public override fun check(pokemon: Pokemon): Boolean {
   }

   public abstract fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun check(`$this`: EntityQueryRequirement, pokemon: Pokemon): Boolean {
         val var10000: PokemonEntity = pokemon.getEntity();
         val var3: LivingEntity;
         if (var10000 != null) {
            var3 = var10000 as LivingEntity;
         } else {
            val var4: ServerPlayer = pokemon.getOwnerPlayer();
            if (var4 == null) {
               return false;
            }

            var3 = var4 as LivingEntity;
         }

         return `$this`.check(pokemon, var3);
      }
   }
}
