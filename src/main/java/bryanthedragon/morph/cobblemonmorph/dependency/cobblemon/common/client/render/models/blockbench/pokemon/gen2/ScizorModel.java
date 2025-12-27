package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BimanualSwingAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class ScizorModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BimanualFrame, BiWingedFrame {
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open val leftArm: ModelPart
   public open val leftLeg: ModelPart
   public open val leftWing: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightArm: ModelPart
   public open val rightLeg: ModelPart
   public open val rightWing: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "scizor");
      this.head = this.getPart("head_pivot");
      this.leftWing = this.getPart("left_wing");
      this.rightWing = this.getPart("right_wing");
      this.leftArm = this.getPart("left_upper_arm");
      this.rightArm = this.getPart("right_upper_arm");
      this.leftLeg = this.getPart("left_leg_joint");
      this.rightLeg = this.getPart("right_leg_joint");
      this.portraitScale = 2.6F;
      this.portraitTranslation = new Vec3(-0.28, 2.9, 0.0);
      this.profileScale = 0.55F;
      this.profileTranslation = new Vec3(0.0, 0.9, 0.0);
   }

   public override fun registerPoses() {
      val blink: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "scizor", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10001: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var10001, PoseType.STAND),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "scizor", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.WALK,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               new BipedWalkAnimation(this, 0.7F, 0.85F),
               new BimanualSwingAnimation(this, 0.0F, 0.85F, 2, null),
               PoseableEntityModel.bedrock$default(this, "scizor", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setHover(
         PoseableEntityModel.registerPose$default(
            this,
            "hover",
            SetsKt.setOf(new PoseType[]{PoseType.HOVER, PoseType.FLOAT}),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "scizor", "air_idle", null, 4, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.6F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(10.0F), 4, null), <unrepresentable>.INSTANCE, 1
               )
            },
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setFly(
         PoseableEntityModel.registerPose$default(
            this,
            "fly",
            SetsKt.setOf(new PoseType[]{PoseType.FLY, PoseType.SWIM}),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "scizor", "air_idle", null, 4, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.6F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(14.0F), 4, null), <unrepresentable>.INSTANCE, 1
               )
            },
            null,
            new ModelQuirk[]{blink},
            188,
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
}
