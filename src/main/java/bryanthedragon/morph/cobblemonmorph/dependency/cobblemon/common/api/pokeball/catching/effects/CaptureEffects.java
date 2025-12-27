package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.effects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.entity.LivingEntity

public object CaptureEffects {
   public final val FULL_RESTORE: CaptureEffect = CaptureEffects::FULL_RESTORE$lambda$0

   public fun friendshipSetter(value: Int): CaptureEffect {
      return CaptureEffects::friendshipSetter$lambda$1;
   }

   @JvmStatic
   fun `FULL_RESTORE$lambda$0`(var0: LivingEntity, pokemon: Pokemon) {
      pokemon.heal();
   }

   @JvmStatic
   fun `friendshipSetter$lambda$1`(`$value`: Int, var1: LivingEntity, pokemon: Pokemon) {
      Pokemon.setFriendship$default(pokemon, `$value`, false, 2, null);
   }
}
