package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class SableyeModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BimanualFrame {
   public open val head: ModelPart
   public open val leftArm: ModelPart
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightArm: ModelPart
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var shoulderLeft: Pose<PokemonEntity, ModelFrame>
   public final val shoulderOffset: Int
   public final lateinit var shoulderRight: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "sableye");
      this.head = this.getPart("head");
      this.leftArm = this.getPart("arm_left");
      this.rightArm = this.getPart("arm_right");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.portraitScale = 2.2F;
      this.portraitTranslation = new Vec3(-0.15, -0.5, 0.0);
      this.profileScale = 1.0F;
      this.profileTranslation = new Vec3(0.0, 0.28, 0.0);
      this.shoulderOffset = 1;
   }

   public override fun registerPoses() {
      var var10001: PoseableEntityModel = this;
      var var10003: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var11: java.util.Set = var10003;
      val var10004: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            var10001,
            "standing",
            SetsKt.plus(var11, var10004),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "sableye", "ground_idle", null, 4, null)
            },
            null,
            null,
            444,
            null
         )
      );
      var10001 = this;
      var10003 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            var10001,
            "walk",
            var10003,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "sableye", "ground_idle", null, 4, null),
               PoseableEntityModel.bedrock$default(this, "sableye", "ground_walk", null, 4, null)
            },
            null,
            null,
            444,
            null
         )
      );
      this.setShoulderLeft(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SHOULDER_LEFT,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "sableye", "shoulder_left", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, this.shoulderOffset)},
            null,
            158,
            null
         )
      );
      this.setShoulderRight(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SHOULDER_RIGHT,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "sableye", "shoulder_right", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, -this.shoulderOffset)},
            null,
            158,
            null
         )
      );
   }

   override fun <T extends Entity> singleBoneLook(
      invertX: Boolean,
      invertY: Boolean,
      disableX: Boolean,
      disableY: Boolean,
      pitchMultiplier: java.lang.Float?,
      yawMultiplier: java.lang.Float?,
      maxPitch: java.lang.Float?,
      minPitch: java.lang.Float?,
      maxYaw: java.lang.Float?,
      minYaw: java.lang.Float?
   ): SingleBoneLookAnimation<T> {
      return HeadedFrame.DefaultImpls.singleBoneLook(
         this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw
      );
   }
}
