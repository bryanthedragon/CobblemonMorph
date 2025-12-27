package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import java.util.concurrent.CompletableFuture

public class CannotInterruptActionEffectKeyframe : ConditionalActionEffectKeyframe {
   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      context.setCanBeInterrupted(true);
      val var10000: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
      return var10000;
   }
}
