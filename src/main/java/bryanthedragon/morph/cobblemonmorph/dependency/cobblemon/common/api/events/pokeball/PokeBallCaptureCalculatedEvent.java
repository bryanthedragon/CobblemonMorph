package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

public class PokeBallCaptureCalculatedEvent(thrower: LivingEntity,
   pokemonEntity: PokemonEntity,
   pokeBallEntity: EmptyPokeBallEntity,
   captureResult: CaptureContext
) {
   public final var captureResult: CaptureContext
   public final val pokeBallEntity: EmptyPokeBallEntity
   public final val pokemonEntity: PokemonEntity
   public final val thrower: LivingEntity

   init {
      this.thrower = thrower;
      this.pokemonEntity = pokemonEntity;
      this.pokeBallEntity = pokeBallEntity;
      this.captureResult = captureResult;
   }

   public fun ifPlayer(action: (PokeBallCaptureCalculatedEvent, ServerPlayer) -> Unit) {
      if (this.thrower is ServerPlayer) {
         action.invoke(this, this.thrower);
      }
   }
}
