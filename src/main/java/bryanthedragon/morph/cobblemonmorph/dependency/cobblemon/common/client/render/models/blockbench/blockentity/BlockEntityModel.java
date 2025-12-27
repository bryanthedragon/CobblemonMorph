package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.blockentity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import java.util.Map.Entry
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nBlockEntityModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/blockentity/BlockEntityModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,44:1\n26#2:45\n*S KotlinDebug\n*F\n+ 1 BlockEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/blockentity/BlockEntityModel\n*L\n24#1:45\n*E\n"])
public class BlockEntityModel(root: Bone) : PoseableEntityModel(null, 1) {
   public final val boneName: String
   public final var idleAnimations: Array<StatelessAnimation<Entity, out ModelFrame>>
   public open val isForLivingEntityRenderer: Boolean
   public final var maxScale: Float
   public final val root: Bone
   public open val rootPart: ModelPart
   public final var yTranslation: Float

   init {
      this.root = root;
      val var10001: Any = (CollectionsKt.first(this.root.getChildren().entrySet()) as Entry).getKey();
      this.boneName = var10001 as java.lang.String;
      val var10002: Bone = this.root;
      this.rootPart = this.registerChildWithAllChildren(var10002 as ModelPart, this.boneName);
      this.idleAnimations = new StatelessAnimation[0];
      this.maxScale = 1.0F;
   }

   public override fun registerPoses() {
      val closedPose: Pose = PoseableEntityModel.registerPose$default(this, "CLOSED", PoseType.NONE, null, 0, null, null, null, null, null, 508, null);
      val openPose: Pose = PoseableEntityModel.registerPose$default(
         this,
         PoseType.OPEN,
         null,
         0,
         null,
         null,
         new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "gilded_chest", "open", null, 4, null)},
         null,
         null,
         222,
         null
      );
      closedPose.getTransitions()
         .put(
            openPose.getPoseName(),
            new Function2<Pose<Entity, ? extends ModelFrame>, Pose<Entity, ? extends ModelFrame>, BedrockStatefulAnimation<Entity>>(this) {
               {
                  super(2);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final BedrockStatefulAnimation<Entity> invoke(
                  @NotNull Pose<Entity, ? extends ModelFrame> var1, @NotNull Pose<Entity, ? extends ModelFrame> var2
               ) {
                  return PoseableEntityModel.bedrockStateful$default(this.this$0, "gilded_chest", "opening", null, 4, null);
               }
            }
         );
      openPose.getTransitions()
         .put(
            closedPose.getPoseName(),
            new Function2<Pose<Entity, ? extends ModelFrame>, Pose<Entity, ? extends ModelFrame>, BedrockStatefulAnimation<Entity>>(this) {
               {
                  super(2);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final BedrockStatefulAnimation<Entity> invoke(
                  @NotNull Pose<Entity, ? extends ModelFrame> var1, @NotNull Pose<Entity, ? extends ModelFrame> var2
               ) {
                  return PoseableEntityModel.bedrockStateful$default(this.this$0, "gilded_chest", "closing", null, 4, null);
               }
            }
         );
   }

   public open fun getState(entity: Entity): Nothing {
      throw new NotImplementedError("This is not supported for the gilded chest");
   }
}
