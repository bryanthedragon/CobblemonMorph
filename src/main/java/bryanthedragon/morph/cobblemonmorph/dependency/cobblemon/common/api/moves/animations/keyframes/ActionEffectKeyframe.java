package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import java.util.LinkedHashMap
import java.util.concurrent.CompletableFuture

public interface ActionEffectKeyframe {
   public abstract fun play(context: ActionEffectContext): CompletableFuture<Unit> {
   }

   public open fun interrupt(context: ActionEffectContext) {
   }

   public open fun skip(): CompletableFuture<Unit> {
   }

   public companion object {
      public final val types: MutableMap<String, Class<out ActionEffectKeyframe>> = (new LinkedHashMap()) as java.util.Map
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun interrupt(`$this`: ActionEffectKeyframe, context: ActionEffectContext) {
      }

      @JvmStatic
      fun skip(`$this`: ActionEffectKeyframe): CompletableFuture<Unit> {
         val var10000: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
         return var10000;
      }
   }
}
