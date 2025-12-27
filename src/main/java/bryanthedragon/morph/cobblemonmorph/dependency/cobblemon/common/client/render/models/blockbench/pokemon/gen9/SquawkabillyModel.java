package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
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

public class SquawkabillyModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BiWingedFrame {
   public open val cryAnimation: CryProvider
   public final lateinit var flying: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hovering: Pose<PokemonEntity, ModelFrame>
   public open val leftLeg: ModelPart
   public open val leftWing: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rightWing: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var shoulderLeft: Pose<PokemonEntity, ModelFrame>
   public final val shoulderOffsetX: Double
   public final val shoulderOffsetY: Int
   public final lateinit var shoulderRight: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   private final val tail: ModelPart
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "squawkabilly");
      this.head = this.getPart("head");
      this.leftWing = this.getPart("wing_left");
      this.rightWing = this.getPart("wing_right");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.tail = this.getPart("tail");
      this.portraitScale = 2.0F;
      this.portraitTranslation = new Vec3(-0.2, -0.2, 0.0);
      this.profileScale = 0.85F;
      this.profileTranslation = new Vec3(0.0, 0.51, 0.0);
      this.cryAnimation = SquawkabillyModel::cryAnimation$lambda$0;
      this.shoulderOffsetX = -1.0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "squawkabilly", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var3: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var10001: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var10001, PoseType.STAND),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null)
            },
            null,
            var3,
            180,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.WALK,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null),
               this.translation(this.getRootPart(), WaveFunctionKt.parabolaFunction(-4.0F, 0.4F), 1, <unrepresentable>.INSTANCE),
               this.translation(
                  this.getHead(),
                  WaveFunctionKt.sineFunction$default(AngleExtensionsKt.toRadians(-20.0F), 1.0F, 0.0F, AngleExtensionsKt.toRadians(-10.0F), 4, null),
                  0,
                  <unrepresentable>.INSTANCE
               ),
               this.rotation(
                  this.getLeftLeg(), WaveFunctionKt.parabolaFunction(-20.0F, 0.0F, AngleExtensionsKt.toRadians(30.0F)), 0, <unrepresentable>.INSTANCE
               ),
               this.rotation(
                  this.getRightLeg(), WaveFunctionKt.parabolaFunction(-20.0F, 0.0F, AngleExtensionsKt.toRadians(30.0F)), 0, <unrepresentable>.INSTANCE
               ),
               this.rotation(
                  this.tail, WaveFunctionKt.sineFunction$default(AngleExtensionsKt.toRadians(-5.0F), 1.0F, 0.0F, 0.0F, 12, null), 0, <unrepresentable>.INSTANCE
               ),
               this.wingFlap(
                  WaveFunctionKt.sineFunction(AngleExtensionsKt.toRadians(-5.0F), 0.4F, 0.0F, AngleExtensionsKt.toRadians(-20.0F)),
                  <unrepresentable>.INSTANCE,
                  2
               ),
               this.translation(
                  this.getRightWing(), WaveFunctionKt.parabolaFunction(-10.0F, 30.0F, AngleExtensionsKt.toRadians(25.0F)), 1, <unrepresentable>.INSTANCE
               ),
               this.translation(
                  this.getLeftWing(), WaveFunctionKt.parabolaFunction(-10.0F, 30.0F, AngleExtensionsKt.toRadians(25.0F)), 1, <unrepresentable>.INSTANCE
               )
            },
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      this.setHovering(
         PoseableEntityModel.registerPose$default(
            this,
            "hovering",
            SetsKt.setOf(new PoseType[]{PoseType.FLOAT, PoseType.HOVER}),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.9F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(10.0F), 4, null), <unrepresentable>.INSTANCE, 2
               )
            },
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      this.setFlying(
         PoseableEntityModel.registerPose$default(
            this,
            "flying",
            SetsKt.setOf(new PoseType[]{PoseType.FLY, PoseType.SWIM}),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.9F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(14.0F), 4, null), <unrepresentable>.INSTANCE, 2
               )
            },
            null,
            new ModelQuirk[]{blink},
            180,
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
               PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(this.shoulderOffsetX, this.shoulderOffsetY, 0.0)
            },
            new ModelQuirk[]{blink},
            30,
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
               PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(-this.shoulderOffsetX, this.shoulderOffsetY, 0.0)
            },
            new ModelQuirk[]{blink},
            30,
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
   fun `cryAnimation$lambda$0`(`this$0`: SquawkabillyModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "squawkabilly", "cry", null, 4, null);
   }
}
