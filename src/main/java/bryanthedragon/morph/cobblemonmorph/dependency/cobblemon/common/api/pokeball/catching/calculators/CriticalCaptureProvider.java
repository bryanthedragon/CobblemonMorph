package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators

import kotlin.math.MathKt
import kotlin.random.Random
import net.minecraft.server.level.ServerPlayer

public interface CriticalCaptureProvider {
   public open fun shouldHaveCriticalCapture(player: ServerPlayer, modifiedCatchRate: Float): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun shouldHaveCriticalCapture(`$this`: CriticalCaptureProvider, player: ServerPlayer, modifiedCatchRate: Float): Boolean {
         return Random.Default.nextInt(256) < MathKt.roundToInt(modifiedCatchRate * 0.0F * 1.0F / 6.0F);
      }
   }
}
