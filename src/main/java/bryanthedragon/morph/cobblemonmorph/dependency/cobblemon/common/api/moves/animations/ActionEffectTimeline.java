package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.jvm.functions.Function1

public class ActionEffectTimeline(timeline: List<ActionEffectKeyframe> = (new ArrayList()) as java.util.List,
   condition: Expression = MoLangExtensionsKt.asExpression("true")
) {
   public final val condition: Expression
   public final val timeline: List<ActionEffectKeyframe>

   init {
      this.timeline = timeline;
      this.condition = condition;
   }

   public fun run(context: ActionEffectContext): CompletableFuture<Unit> {
      var var10000: CompletableFuture;
      if (!this.timeline.isEmpty() && MoLangExtensionsKt.resolveBoolean(context.getRuntime(), this.condition)) {
         val finalFuture: CompletableFuture = new CompletableFuture();
         this.chainKeyframes(context, CollectionsKt.toList(this.timeline).iterator(), finalFuture);
         var10000 = finalFuture;
      } else {
         var10000 = CompletableFuture.completedFuture(Unit.INSTANCE);
      }

      var10000 = var10000.exceptionallyCompose(ActionEffectTimeline::run$lambda$0);
      return var10000;
   }

   public fun chainKeyframes(context: ActionEffectContext, iterator: Iterator<ActionEffectKeyframe>, finalFuture: CompletableFuture<Unit>) {
      if (!iterator.hasNext()) {
         finalFuture.complete(Unit.INSTANCE);
      } else {
         val keyframe: ActionEffectKeyframe = iterator.next() as ActionEffectKeyframe;
         context.getCurrentKeyframes().add(keyframe);
         keyframe.play(context)
            .thenRun(ActionEffectTimeline::chainKeyframes$lambda$1)
            .thenApply(ActionEffectTimeline::chainKeyframes$lambda$2)
            .exceptionally(ActionEffectTimeline::chainKeyframes$lambda$3);
      }
   }

   @JvmStatic
   fun `run$lambda$0`(it: java.lang.Throwable): CompletionStage {
      it.printStackTrace();
      return CompletableFuture.completedFuture(Unit.INSTANCE);
   }

   @JvmStatic
   fun `chainKeyframes$lambda$1`(`$context`: ActionEffectContext, `$keyframe`: ActionEffectKeyframe) {
      `$context`.getCurrentKeyframes().remove(`$keyframe`);
   }

   @JvmStatic
   fun `chainKeyframes$lambda$2`(`$tmp0`: Function1, p0: Any): Unit {
      return `$tmp0`.invoke(p0) as Unit;
   }

   @JvmStatic
   fun `chainKeyframes$lambda$3`(`$finalFuture`: CompletableFuture, it: java.lang.Throwable): Unit {
      `$finalFuture`.completeExceptionally(it);
      return Unit.INSTANCE;
   }

   fun ActionEffectTimeline() {
      this(null, null, 3, null);
   }

   public companion object {
      public final val NONE: ActionEffectTimeline
   }
}
