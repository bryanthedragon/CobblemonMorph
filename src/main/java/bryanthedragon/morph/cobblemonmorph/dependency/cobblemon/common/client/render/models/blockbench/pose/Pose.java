package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nPose.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Pose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,48:1\n13579#2,2:49\n3792#2:51\n4307#2,2:52\n1855#3,2:54\n*S KotlinDebug\n*F\n+ 1 Pose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose\n*L\n40#1:49,2\n44#1:51\n44#1:52,2\n44#1:54,2\n*E\n"])
public class Pose<T extends Entity, F extends ModelFrame>(poseName: String,
   poseTypes: Set<PoseType>,
   condition: ((Any) -> Boolean)?,
   onTransitionedInto: (PoseableEntityState<Any>?) -> Unit = <unrepresentable>.INSTANCE as Function1,
   transformTicks: Int,
   animations: MutableMap<String, ExpressionLike> = (new LinkedHashMap()) as java.util.Map,
   vararg idleAnimations: Any,
   vararg transformedParts: Any,
   vararg quirks: Any
) {
   public final val animations: MutableMap<String, ExpressionLike>
   public final val condition: ((Any) -> Boolean)?
   public final val idleAnimations: Array<StatelessAnimation<Any, out Any>>
   public final val onTransitionedInto: (PoseableEntityState<Any>?) -> Unit
   public final var poseName: String
   public final val poseTypes: Set<PoseType>
   public final val quirks: Array<ModelQuirk<Any, *>>
   public final val transformTicks: Int
   public final val transformedParts: Array<ModelPartTransformation>
   public final val transitions: MutableMap<String, (Pose<Any, out ModelFrame>, Pose<Any, out ModelFrame>) -> StatefulAnimation<Any, ModelFrame>>

   init {
      this.poseName = poseName;
      this.poseTypes = poseTypes;
      this.condition = condition;
      this.onTransitionedInto = onTransitionedInto;
      this.transformTicks = transformTicks;
      this.animations = animations;
      this.idleAnimations = idleAnimations;
      this.transformedParts = transformedParts;
      this.quirks = quirks;
      this.transitions = new LinkedHashMap<>();
   }

   public fun isSuitable(entity: Any): Boolean {
      return this.condition == null || this.condition.invoke(entity) as java.lang.Boolean;
   }

   public fun idleStateless(
      model: PoseableEntityModel<Any>,
      state: PoseableEntityState<Any>?,
      limbSwing: Float = 0.0F,
      limbSwingAmount: Float = 0.0F,
      ageInTicks: Float = 0.0F,
      headYaw: Float = 0.0F,
      headPitch: Float = 0.0F,
      intensity: Float
   ) {
      val `$this$forEach$iv`: Any;
      for (Object element$iv : $this$forEach$iv) {
         ((StatelessAnimation)`element$iv`).apply(null, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
      }
   }

   public fun idleStateful(
      entity: Any?,
      model: PoseableEntityModel<Any>,
      state: PoseableEntityState<Any>,
      limbSwing: Float,
      limbSwingAmount: Float,
      ageInTicks: Float,
      headYaw: Float,
      headPitch: Float
   ) {
      val `$this$forEach$iv`: Array<Any> = this.idleAnimations;
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (state.shouldIdleRun((StatelessAnimation<T, ?>)`element$iv$iv`, 0.0F)) {
            `element$iv`.add(`element$iv$iv`);
         }
      }

      for (Object element$ivx : $this$filter$iv) {
         (`element$ivx` as StatelessAnimation)
            .apply(
               entity,
               model,
               state,
               limbSwing,
               limbSwingAmount,
               ageInTicks,
               headYaw,
               headPitch,
               state.getIdleIntensity(`element$ivx` as StatelessAnimation<T, ?>)
            );
      }
   }
}
