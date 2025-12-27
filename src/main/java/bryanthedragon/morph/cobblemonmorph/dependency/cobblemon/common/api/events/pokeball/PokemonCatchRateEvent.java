package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.world.entity.LivingEntity

public class PokemonCatchRateEvent(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, pokemonEntity: PokemonEntity, catchRate: Float) {
   public final var catchRate: Float
   public final val pokeBallEntity: EmptyPokeBallEntity
   public final val pokemonEntity: PokemonEntity
   public final val thrower: LivingEntity

   init {
      this.thrower = thrower;
      this.pokeBallEntity = pokeBallEntity;
      this.pokemonEntity = pokemonEntity;
      this.catchRate = catchRate;
   }
}
