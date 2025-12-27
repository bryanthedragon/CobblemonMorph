package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.entity.LivingEntity

public fun interface CaptureEffect {
   public abstract fun apply(thrower: LivingEntity, pokemon: Pokemon) {
   }
}
