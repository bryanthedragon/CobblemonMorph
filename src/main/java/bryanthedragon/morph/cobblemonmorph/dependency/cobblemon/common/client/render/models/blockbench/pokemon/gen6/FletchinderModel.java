package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen6

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

public class FletchinderModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BiWingedFrame {
   public open val cryAnimation: CryProvider
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open val leftLeg: ModelPart
   public open val leftWing: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rightWing: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var stand: Pose<PokemonEntity, ModelFrame>
   private final val tail: ModelPart
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "fletchinder");
      this.leftWing = this.getPart("wing_left");
      this.rightWing = this.getPart("wing_right");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.head = this.getPart("head_ai");
      this.tail = this.getPart("tail");
      this.portraitScale = 2.8F;
      this.portraitTranslation = new Vec3(-0.4, -1.15, 0.0);
      this.profileScale = 1.1F;
      this.profileTranslation = new Vec3(0.0, -0.01, 0.0);
      this.cryAnimation = FletchinderModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "fletchinder", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10001: EnumSet = PoseType.Companion.getSHOULDER_POSES();
      val var19: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStand(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(SetsKt.plus(var19, var10002), PoseType.STAND),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "fletchinder", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            180,
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
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.6F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(10.0F), 4, null), <unrepresentable>.INSTANCE, 2
               )
            },
            null,
            new ModelQuirk[]{blink},
            180,
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
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walking",
            PoseType.WALK,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "fletchinder", "ground_idle", null, 4, null),
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
   fun `cryAnimation$lambda$0`(`this$0`: FletchinderModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "fletchinder", "cry", null, 4, null);
   }
}
