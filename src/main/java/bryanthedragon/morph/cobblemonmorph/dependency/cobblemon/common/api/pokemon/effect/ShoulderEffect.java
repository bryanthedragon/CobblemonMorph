package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer

public interface ShoulderEffect {
   public abstract fun applyEffect(pokemon: Pokemon, player: ServerPlayer, isLeft: Boolean) {
   }

   public abstract fun removeEffect(pokemon: Pokemon, player: ServerPlayer, isLeft: Boolean) {
   }
}
