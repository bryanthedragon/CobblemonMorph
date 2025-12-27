package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.concurrent.CompletableFuture

public class ForkActionEffectKeyframe(condition: ExpressionLike = MoLangExtensionsKt.asExpressionLike("true"),
      ifTrue: List<ActionEffectKeyframe> = CollectionsKt.emptyList(),
      ifFalse: List<ActionEffectKeyframe> = CollectionsKt.emptyList()
   ) :
   ActionEffectKeyframe {
   public final val condition: ExpressionLike
   public final val ifFalse: List<ActionEffectKeyframe>
   public final val ifTrue: List<ActionEffectKeyframe>

   init {
      this.condition = condition;
      this.ifTrue = ifTrue;
      this.ifFalse = ifFalse;
   }

   public override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
      val future: CompletableFuture = new CompletableFuture();
      if (!this.condition.resolveBoolean(context.getRuntime())) {
         context.getActionEffect().chainKeyframes(context, CollectionsKt.toList(this.ifFalse).iterator(), future);
      } else {
         context.getActionEffect().chainKeyframes(context, CollectionsKt.toList(this.ifTrue).iterator(), future);
      }

      return future;
   }

   override fun interrupt(context: ActionEffectContext) {
      ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
   }

   override fun skip(): CompletableFuture<Unit> {
      return ActionEffectKeyframe.DefaultImpls.skip(this);
   }

   fun ForkActionEffectKeyframe() {
      this(null, null, null, 7, null);
   }
}
