package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation
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

public class DecidueyeHisuianModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BiWingedFrame {
   public final val arrow: ModelPart
   public open val cryAnimation: CryProvider
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   private final val leftClosedWing: ModelPart
   public open val leftLeg: ModelPart
   public open val leftWing: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   private final val rightClosedWing: ModelPart
   public open val rightLeg: ModelPart
   public open val rightWing: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "decidueye_hisui");
      this.head = this.getPart("head");
      this.leftClosedWing = this.getPart("wing_closed_left1");
      this.rightClosedWing = this.getPart("wing_closed_right1");
      this.leftWing = this.getPart("wing_open_left1");
      this.rightWing = this.getPart("wing_open_right1");
      this.leftLeg = this.getPart("thigh_left");
      this.rightLeg = this.getPart("thigh_right");
      this.arrow = this.getPart("arrow");
      this.portraitTranslation = new Vec3(-0.28, 2.5300000000000047, 0.0);
      this.portraitScale = 1.5200002F;
      this.profileTranslation = new Vec3(0.0, 1.0299999999999998, 0.0);
      this.profileScale = 0.46999997F;
      this.cryAnimation = DecidueyeHisuianModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "decidueye", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      var var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var21: java.util.Set = SetsKt.minus(var10001, PoseType.HOVER);
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var21, var10002),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "decidueye", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      var10001 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            SetsKt.minus(var10001, PoseType.FLY),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               new BipedWalkAnimation(this, 0.0F, 0.0F, 6, null),
               PoseableEntityModel.bedrock$default(this, "decidueye", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      this.setHover(
         PoseableEntityModel.registerPose$default(
            this,
            "hover",
            PoseType.HOVER,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               PoseableEntityModel.bedrock$default(this, "decidueye", "air_idle", null, 4, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.6F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(10.0F), 4, null), <unrepresentable>.INSTANCE, 1
               )
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            52,
            null
         )
      );
      this.setFly(
         PoseableEntityModel.registerPose$default(
            this,
            "fly",
            PoseType.FLY,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               PoseableEntityModel.bedrock$default(this, "decidueye", "air_fly", null, 4, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.9F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(14.0F), 4, null), <unrepresentable>.INSTANCE, 1
               )
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            52,
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
   fun `cryAnimation$lambda$0`(`this$0`: DecidueyeHisuianModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "decidueye", "hisuian_cry", null, 4, null);
   }
}
