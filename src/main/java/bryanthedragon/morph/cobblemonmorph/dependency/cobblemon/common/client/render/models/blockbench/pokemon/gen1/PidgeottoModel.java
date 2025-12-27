package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

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

public class PidgeottoModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BiWingedFrame {
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
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var stand: Pose<PokemonEntity, ModelFrame>
   private final val tail: ModelPart
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   private final val wingClosedLeft: ModelPart
   private final val wingClosedRight: ModelPart
   private final val wingOpenLeft: ModelPart
   private final val wingOpenRight: ModelPart

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "pidgeotto");
      this.head = this.getPart("neck");
      this.leftWing = this.getPart("wing_closed_left");
      this.rightWing = this.getPart("wing_closed_right");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.tail = this.getPart("tail");
      this.wingOpenRight = this.getPart("wing_open_right");
      this.wingOpenLeft = this.getPart("wing_open_left");
      this.wingClosedRight = this.getPart("wing_closed_right");
      this.wingClosedLeft = this.getPart("wing_closed_left");
      this.portraitScale = 2.8F;
      this.portraitTranslation = new Vec3(-0.4, -0.9, 0.0);
      this.profileScale = 1.1F;
      this.profileTranslation = new Vec3(0.0, 0.1, 0.0);
      this.cryAnimation = PidgeottoModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgeotto", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val flyQuirk1: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgeotto", "air_fly_quirk", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val flyQuirk2: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgeotto", "air_fly_quirk2", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "pidgeotto", "sleep_PLACEHOLDER", null, 4, null)},
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)
            },
            null,
            316,
            null
         )
      );
      var var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var26: java.util.Set = SetsKt.minus(var10001, PoseType.HOVER);
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStand(
         PoseableEntityModel.registerPose$default(
            this,
            "stand",
            SetsKt.plus(var26, var10002),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgeotto", "ground_idle_PLACEHOLDER", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)
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
            "hover",
            SetsKt.minus(var10001, PoseType.FLY),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgeotto", "ground_idle_PLACEHOLDER", null, 4, null),
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
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      this.setHover(
         PoseableEntityModel.registerPose$default(
            this,
            "fly",
            PoseType.HOVER,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgeotto", "air_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(true)
            },
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      this.setFly(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.FLY,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgeotto", "air_fly", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(true)
            },
            new ModelQuirk[]{blink, flyQuirk1, flyQuirk2},
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
   fun `cryAnimation$lambda$0`(`this$0`: PidgeottoModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "pidgeotto", "cry", null, 4, null);
   }
}
