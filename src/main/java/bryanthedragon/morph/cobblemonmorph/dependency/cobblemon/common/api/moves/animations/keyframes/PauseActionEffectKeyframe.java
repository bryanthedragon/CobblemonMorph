package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.concurrent.CompletableFuture

public class PauseActionEffectKeyframe : ConditionalActionEffectKeyframe {
   public final val pause: ExpressionLike = MoLangExtensionsKt.asExpressionLike("1")

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      return SchedulingFunctionsKt.delayedFuture$default(0, this.pause.resolveFloat(context.getRuntime()), true, 1, null);
   }
}
