package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.effects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.entity.LivingEntity

public class FriendshipEarningBoostEffect(multiplier: Float) : CaptureEffect {
   public final val multiplier: Float

   init {
      this.multiplier = multiplier;
   }

   public override fun apply(thrower: LivingEntity, pokemon: Pokemon) {
   }
}
