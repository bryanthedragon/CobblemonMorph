package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import java.util.concurrent.CompletableFuture

public class SequenceActionEffectKeyframe(keyframes: List<ActionEffectKeyframe> = CollectionsKt.emptyList()) : ConditionalActionEffectKeyframe {
   public final val keyframes: List<ActionEffectKeyframe>

   init {
      this.keyframes = keyframes;
   }

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      val future: CompletableFuture = new CompletableFuture();
      context.getActionEffect().chainKeyframes(context, CollectionsKt.toList(this.keyframes).iterator(), future);
      return future;
   }

   fun SequenceActionEffectKeyframe() {
      this(null, 1, null);
   }
}
