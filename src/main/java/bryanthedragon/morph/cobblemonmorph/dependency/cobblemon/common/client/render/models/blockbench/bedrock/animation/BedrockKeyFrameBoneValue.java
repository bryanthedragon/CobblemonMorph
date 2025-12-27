package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.runtime.MoLangRuntime
import java.util.HashMap
import java.util.SortedMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockKeyFrameBoneValue\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n336#2,8:347\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockKeyFrameBoneValue\n*L\n265#1:347,8\n*E\n"])
public class BedrockKeyFrameBoneValue : HashMap<java.lang.Double, BedrockAnimationKeyFrame>, BedrockBoneValue {
   public fun SortedMap<Double, BedrockAnimationKeyFrame>.getAtIndex(index: Int?): BedrockAnimationKeyFrame? {
      if (index == null) {
         return null;
      } else {
         val var10000: java.util.Set = `$this$getAtIndex`.keySet();
         val key: java.lang.Double = CollectionsKt.elementAtOrNull(var10000, index) as java.lang.Double;
         return if (key != null) `$this$getAtIndex`.get(key) as BedrockAnimationKeyFrame else null;
      }
   }

   public override fun resolve(time: Double, runtime: MoLangRuntime): Vec3 {
      val sortedTimeline: SortedMap = MapsKt.toSortedMap(this);
      val var10000: java.util.Set = sortedTimeline.keySet();
      val beforeIndex: java.lang.Iterable = var10000;
      var before: Int = 0;
      val afterData: java.util.Iterator = beforeIndex.iterator();

      while (true) {
         if (!afterData.hasNext()) {
            var24 = -1;
            break;
         }

         val beforeData: Any = afterData.next();
         if (before < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         val beforePlusIndex: java.lang.Double = beforeData as java.lang.Double;
         if (beforePlusIndex > time) {
            var24 = before;
            break;
         }

         before++;
      }

      var afterIndex: Int = var24;
      if (afterIndex == -1) {
         afterIndex = null;
      }

      var var17: BedrockAnimationKeyFrame;
      label104: {
         var16 = if (afterIndex == null) sortedTimeline.size() - 1 else (if (afterIndex == 0) null else afterIndex - 1);
         var17 = this.getAtIndex(sortedTimeline, afterIndex);
         var18 = this.getAtIndex(sortedTimeline, var16);
         if (var17 != null) {
            val var25: MolangBoneValue = var17.getPre();
            if (var25 != null) {
               var26 = var25.resolve(time, runtime);
               if (var26 != null) {
                  break label104;
               }
            }
         }

         var26 = Vec3.f_82478_;
      }

      label98: {
         if (var18 != null) {
            val var27: MolangBoneValue = var18.getPost();
            if (var27 != null) {
               var28 = var27.resolve(time, runtime);
               if (var28 != null) {
                  break label98;
               }
            }
         }

         var28 = Vec3.f_82478_;
      }

      if (var18 == null && var17 == null) {
         return new Vec3(0.0, 0.0, 0.0);
      } else if (var18 != null && var18.getInterpolationType() === InterpolationType.SMOOTH
         || var17 != null && var17.getInterpolationType() === InterpolationType.SMOOTH) {
         if (var18 != null && var17 != null) {
            return InterpolationMathKt.catmullromLerp(
               this.getAtIndex(sortedTimeline, if (var16 != null && var16 != 0) var16 - 1 else null),
               var18,
               var17,
               this.getAtIndex(sortedTimeline, if (afterIndex != null && afterIndex != this.size() - 1) afterIndex + 1 else null),
               time,
               runtime
            );
         } else if (var18 != null) {
            return var28;
         } else {
            return var26;
         }
      } else if (var18 != null && var17 != null) {
         return new Vec3(
            var28.f_82479_ + (var26.f_82479_ - var28.f_82479_) * InterpolationMathKt.linearLerpAlpha(var18.getTime(), var17.getTime(), time),
            var28.f_82480_ + (var26.f_82480_ - var28.f_82480_) * InterpolationMathKt.linearLerpAlpha(var18.getTime(), var17.getTime(), time),
            var28.f_82481_ + (var26.f_82481_ - var28.f_82481_) * InterpolationMathKt.linearLerpAlpha(var18.getTime(), var17.getTime(), time)
         );
      } else if (var18 != null) {
         return var28;
      } else {
         return var26;
      }
   }
}
