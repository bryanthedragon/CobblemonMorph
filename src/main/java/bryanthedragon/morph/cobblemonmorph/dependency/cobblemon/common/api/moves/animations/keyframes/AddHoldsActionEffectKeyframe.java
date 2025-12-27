package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import java.util.LinkedHashSet
import java.util.concurrent.CompletableFuture

public class AddHoldsActionEffectKeyframe : ActionEffectKeyframe {
   public final val holds: MutableSet<String> = (new LinkedHashSet()) as java.util.Set

   public override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
      context.getHolds().addAll(this.holds);
      val var10000: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
      return var10000;
   }

   override fun interrupt(context: ActionEffectContext) {
      ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
   }

   override fun skip(): CompletableFuture<Unit> {
      return ActionEffectKeyframe.DefaultImpls.skip(this);
   }
}
