package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.concurrent.CompletableFuture

public abstract class ConditionalActionEffectKeyframe : ActionEffectKeyframe {
   public final var condition: ExpressionLike = MoLangExtensionsKt.asExpressionLike("true")

   public override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
      return if (this.condition.resolveBoolean(context.getRuntime())) this.playWhenTrue(context) else this.skip();
   }

   public abstract fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
   }

   override fun interrupt(context: ActionEffectContext) {
      ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
   }

   override fun skip(): CompletableFuture<Unit> {
      return ActionEffectKeyframe.DefaultImpls.skip(this);
   }
}
