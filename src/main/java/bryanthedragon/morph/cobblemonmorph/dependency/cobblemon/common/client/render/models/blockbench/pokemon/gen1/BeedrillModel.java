package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class BeedrillModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BiWingedFrame {
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open val leftWing: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightWing: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithSpecificChildren(
         root,
         "beedrill",
         CollectionsKt.listOf(
            new java.lang.String[]{
               "body",
               "antenna_right",
               "antenna_right2",
               "antenna_left",
               "antenna_left2",
               "wing_right",
               "wing_right2",
               "wing_left",
               "wing_left2",
               "abdomen",
               "arm_right",
               "arm_left",
               "leg_right",
               "leg_left",
               "leg_left2",
               "leg_right2",
               "head_AI",
               "head",
               "arm_right2",
               "arm_left2"
            }
         )
      );
      this.head = this.getPart("head");
      this.leftWing = this.getPart("wing_left");
      this.rightWing = this.getPart("wing_right");
      this.portraitScale = 1.9F;
      this.portraitTranslation = new Vec3(0.05, 0.9, 0.0);
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(-0.1, 0.9, 0.0);
      this.cryAnimation = BeedrillModel::cryAnimation$lambda$0;
   }

   public override fun registerPoses() {
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "beedrill", "sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -3.0F)},
            null,
            158,
            null
         )
      );
      var var7: PoseableEntityModel = this;
      var var10003: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var9: java.util.Set = var10003;
      val var10004: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            var7,
            "standing",
            SetsKt.plus(var9, var10004),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "beedrill", "air_idle", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -10.0F)},
            null,
            316,
            null
         )
      );
      var7 = this;
      var10003 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            var7,
            "walk",
            var10003,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "beedrill", "air_fly", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -5.0F)},
            null,
            316,
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

   override fun <T extends Entity> wingFlap(
      flapFunction: (java.lang.Float?) -> java.lang.Float,
      timeVariable: (PoseableEntityState<T>?, java.lang.Float?, java.lang.Float?) -> java.lang.Float,
      axis: Int
   ): WingFlapIdleAnimation<T> {
      return BiWingedFrame.DefaultImpls.wingFlap(this, flapFunction, timeVariable, axis);
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: BeedrillModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "beedrill", "cry", null, 4, null);
   }
}
