package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nSimpleQuirk.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimpleQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,62:1\n3190#2,10:63\n*S KotlinDebug\n*F\n+ 1 SimpleQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk\n*L\n54#1:63,10\n*E\n"])
public class SimpleQuirk<T extends Entity>(secondsBetweenOccurrences: Pair<Float, Float>,
      condition: (PoseableEntityState<Any>) -> Boolean = <unrepresentable>.INSTANCE as Function1,
      loopTimes: IntRange = new IntRange(1, 1),
      animations: (PoseableEntityState<Any>) -> Iterable<StatefulAnimation<Any, *>>
   )
   : ModelQuirk<T, SimpleQuirkData<T>> {
   public final val animations: (PoseableEntityState<Any>) -> Iterable<StatefulAnimation<Any, *>>
   public final val condition: (PoseableEntityState<Any>) -> Boolean
   public final val loopTimes: IntRange
   private final val secondsBetweenOccurrences: Pair<Float, Float>

   init {
      this.secondsBetweenOccurrences = secondsBetweenOccurrences;
      this.condition = condition;
      this.loopTimes = loopTimes;
      this.animations = animations;
   }

   public open fun createData(): SimpleQuirkData<Any> {
      return new SimpleQuirkData<>();
   }

   protected open fun tick(state: PoseableEntityState<Any>, data: SimpleQuirkData<Any>) {
      if (data.getAnimations().isEmpty() && data.getPrimaryAnimation() == null) {
         if (this.condition.invoke(state) as java.lang.Boolean) {
            if (data.getRemainingLoops() > 0) {
               this.applyAnimations(state, data);
               data.setRemainingLoops(data.getRemainingLoops() + -1);
            }

            if (data.getRemainingLoops() == 0) {
               if (data.getNextOccurrenceSeconds() > 0.0F) {
                  if (data.getNextOccurrenceSeconds() <= state.getAnimationSeconds()) {
                     data.setRemainingLoops(RangesKt.random(this.loopTimes, Random.Default as Random) - 1);
                     this.applyAnimations(state, data);
                     data.setNextOccurrenceSeconds(-1.0F);
                  }
               } else {
                  data.setNextOccurrenceSeconds(state.getAnimationSeconds() + SimpleMathExtensionsKt.random(this.secondsBetweenOccurrences));
               }
            }
         }
      }
   }

   private fun applyAnimations(state: PoseableEntityState<Any>, data: SimpleQuirkData<Any>) {
      val primary: java.lang.Iterable = this.animations.invoke(state) as java.lang.Iterable;
      val primaryAnimation: ArrayList = new ArrayList();
      val `second$iv`: ArrayList = new ArrayList();

      for (Object element$iv : $this$partition$iv) {
         if (`element$iv` as StatefulAnimation is PrimaryAnimation) {
            primaryAnimation.add(`element$iv`);
         } else {
            `second$iv`.add(`element$iv`);
         }
      }

      val var3: Pair = new Pair(primaryAnimation, `second$iv`);
      val var12: java.util.List = var3.component1() as java.util.List;
      data.getAnimations().addAll(var3.component2() as java.util.List);
      if (!var12.isEmpty()) {
         val var10000: Any = CollectionsKt.first(var12);
         val var14: PrimaryAnimation = var10000 as PrimaryAnimation;
         data.setPrimaryAnimation(var10000 as PrimaryAnimation<T>);
         state.addPrimaryAnimation(var14);
      }
   }
}
