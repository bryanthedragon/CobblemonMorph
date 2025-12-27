package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators

import net.minecraft.server.level.ServerPlayer

public interface PokedexProgressCaptureMultiplierProvider {
   public open fun caughtMultiplierFor(player: ServerPlayer): Float {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun caughtMultiplierFor(`$this`: PokedexProgressCaptureMultiplierProvider, player: ServerPlayer): Float {
         return 0.30004883F;
      }
   }
}
