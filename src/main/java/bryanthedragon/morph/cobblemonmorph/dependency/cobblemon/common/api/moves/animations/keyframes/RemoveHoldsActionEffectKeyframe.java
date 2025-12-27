package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import java.util.LinkedHashSet
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1

public class RemoveHoldsActionEffectKeyframe : ActionEffectKeyframe {
   public final val delay: Float
   public final val holds: MutableSet<String> = (new LinkedHashSet()) as java.util.Set

   public override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
      val var10000: CompletableFuture = SchedulingFunctionsKt.delayedFuture$default(0, this.delay, true, 1, null)
         .thenApply(RemoveHoldsActionEffectKeyframe::play$lambda$0);
      return var10000;
   }

   override fun interrupt(context: ActionEffectContext) {
      ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
   }

   override fun skip(): CompletableFuture<Unit> {
      return ActionEffectKeyframe.DefaultImpls.skip(this);
   }

   @JvmStatic
   fun `play$lambda$0`(`$tmp0`: Function1, p0: Any): Unit {
      return `$tmp0`.invoke(p0) as Unit;
   }
}
