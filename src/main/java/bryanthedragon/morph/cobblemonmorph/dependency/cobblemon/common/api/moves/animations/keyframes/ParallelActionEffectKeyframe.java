package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import java.util.ArrayList;
import java.util.Arrays
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nParallelActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParallelActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ParallelActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,25:1\n1549#2:26\n1620#2,3:27\n37#3,2:30\n*S KotlinDebug\n*F\n+ 1 ParallelActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ParallelActionEffectKeyframe\n*L\n19#1:26\n19#1:27,3\n22#1:30,2\n*E\n"])
public class ParallelActionEffectKeyframe : ConditionalActionEffectKeyframe {
   public final var keyframes: MutableList<ActionEffectKeyframe> = (new ArrayList()) as java.util.List

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      val `$this$toTypedArray$iv`: java.lang.Iterable = this.keyframes;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.keyframes, 10));

      for (Object item$iv$iv : $this$map$iv) {
         val it: ActionEffectKeyframe = `item$iv$iv` as ActionEffectKeyframe;
         context.getCurrentKeyframes().add(`item$iv$iv` as ActionEffectKeyframe);
         `destination$iv$iv`.add(it.play(context).thenRun(ParallelActionEffectKeyframe::playWhenTrue$lambda$1$lambda$0));
      }

      val var2: Array<CompletableFuture> = (`destination$iv$iv` as java.util.List).toArray(new CompletableFuture[0]);
      val var10000: CompletableFuture = CompletableFuture.allOf(Arrays.copyOf(var2, var2.length))
         .thenApply(ParallelActionEffectKeyframe::playWhenTrue$lambda$2);
      return var10000;
   }

   @JvmStatic
   fun `playWhenTrue$lambda$1$lambda$0`(`$context`: ActionEffectContext, `$it`: ActionEffectKeyframe) {
      `$context`.getCurrentKeyframes().remove(`$it`);
   }

   @JvmStatic
   fun `playWhenTrue$lambda$2`(`$tmp0`: Function1, p0: Any): Unit {
      return `$tmp0`.invoke(p0) as Unit;
   }
}
