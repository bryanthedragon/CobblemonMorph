package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nFossilModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,43:1\n26#2:44\n26#2:45\n*S KotlinDebug\n*F\n+ 1 FossilModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilModel\n*L\n32#1:44\n33#1:45\n*E\n"])
public class FossilModel(root: Bone) : PoseableEntityModel(null, 1) {
   public final val boneName: String
   public open val isForLivingEntityRenderer: Boolean
   public final var maxScale: Float
   public open val rootPart: ModelPart
   public final var tankAnimations: Array<StatelessAnimation<Entity, out ModelFrame>>
   public final var tankQuirks: Array<ModelQuirk<Entity, *>>
   public final var yGrowthPoint: Float
   public final var yTranslation: Float

   init {
      val var10001: Any = (CollectionsKt.first(root.getChildren().entrySet()) as Entry).getKey();
      this.boneName = var10001 as java.lang.String;
      this.rootPart = this.registerChildWithAllChildren(root as ModelPart, this.boneName);
      this.maxScale = 1.0F;
      this.tankAnimations = new StatelessAnimation[0];
      this.tankQuirks = new ModelQuirk[0];
   }

   public override fun registerPoses() {
      PoseableEntityModel.registerPose$default(this, PoseType.SLEEP, null, 0, null, null, this.tankAnimations, null, this.tankQuirks, 94, null);
   }

   public open fun getState(entity: Entity): Nothing {
      throw new NotImplementedError("This is not supported for fossil models");
   }
}
