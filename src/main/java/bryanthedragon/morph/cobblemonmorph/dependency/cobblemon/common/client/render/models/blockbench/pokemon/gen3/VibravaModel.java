package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.QuadrupedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function3
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class VibravaModel(root: ModelPart) : PokemonPoseableModel, QuadrupedFrame, HeadedFrame {
   public open val cryAnimation: CryProvider
   public open val foreLeftLeg: ModelPart
   public open val foreRightLeg: ModelPart
   public open val head: ModelPart
   public open val hindLeftLeg: ModelPart
   public open val hindRightLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final val wing_front_left: ModelPart
   public final val wing_front_right: ModelPart

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "vibrava");
      this.head = this.getPart("head");
      this.foreLeftLeg = this.getPart("leg_front_left");
      this.foreRightLeg = this.getPart("leg_front_right");
      this.hindLeftLeg = this.getPart("leg_back_left");
      this.hindRightLeg = this.getPart("leg_back_right");
      this.portraitScale = 1.36F;
      this.portraitTranslation = new Vec3(-0.37, -0.55, 0.0);
      this.profileScale = 0.54F;
      this.profileTranslation = new Vec3(-0.01, 0.71, 0.0);
      this.cryAnimation = VibravaModel::cryAnimation$lambda$0;
      this.wing_front_left = this.getPart("wing_front_left");
      this.wing_front_right = this.getPart("wing_front_right");
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "vibrava", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val wingFrame1: <unrepresentable> = new BiWingedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart leftWing;
         @NotNull
         private final ModelPart rightWing;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.leftWing = `$receiver`.getPart("wing_front_left");
            this.rightWing = `$receiver`.getPart("wing_front_right");
         }

         @NotNull
         public ModelPart getRootPart() {
            return this.rootPart;
         }

         @NotNull
         @Override
         public ModelPart getLeftWing() {
            return this.leftWing;
         }

         @NotNull
         @Override
         public ModelPart getRightWing() {
            return this.rightWing;
         }

         @NotNull
         @Override
         public <T extends Entity> WingFlapIdleAnimation<T> wingFlap(
            @NotNull Function1<? super java.lang.Float, java.lang.Float> flapFunction,
            @NotNull Function3<? super PoseableEntityState<T>, ? super java.lang.Float, ? super java.lang.Float, java.lang.Float> timeVariable,
            int axis
         ) {
            return BiWingedFrame.DefaultImpls.wingFlap(this, flapFunction, timeVariable, axis);
         }
      };
      val wingFrame2: <unrepresentable> = new BiWingedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart leftWing;
         @NotNull
         private final ModelPart rightWing;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.leftWing = `$receiver`.getPart("wing_back_left");
            this.rightWing = `$receiver`.getPart("wing_back_right");
         }

         @NotNull
         public ModelPart getRootPart() {
            return this.rootPart;
         }

         @NotNull
         @Override
         public ModelPart getLeftWing() {
            return this.leftWing;
         }

         @NotNull
         @Override
         public ModelPart getRightWing() {
            return this.rightWing;
         }

         @NotNull
         @Override
         public <T extends Entity> WingFlapIdleAnimation<T> wingFlap(
            @NotNull Function1<? super java.lang.Float, java.lang.Float> flapFunction,
            @NotNull Function3<? super PoseableEntityState<T>, ? super java.lang.Float, ? super java.lang.Float, java.lang.Float> timeVariable,
            int axis
         ) {
            return BiWingedFrame.DefaultImpls.wingFlap(this, flapFunction, timeVariable, axis);
         }
      };
      var var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var13: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.minus(SetsKt.plus(var13, var10002), PoseType.HOVER),
            null,
            30,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, 0.6F, 0.3F, null, null, null, null, 975, null),
               PoseableEntityModel.bedrock$default(this, "vibrava", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wing_front_left).addRotationDegrees(1, -75),
               ModelPartExtensionsKt.createTransformation(this.wing_front_right).addRotationDegrees(1, 75)
            },
            new ModelQuirk[]{blink},
            52,
            null
         )
      );
      var10001 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            SetsKt.plus(var10001, PoseType.HOVER),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, 0.6F, 0.3F, null, null, null, null, 975, null),
               PoseableEntityModel.bedrock$default(this, "vibrava", "ground_idle", null, 4, null),
               wingFrame1.wingFlap(WaveFunctionKt.triangleFunction$default(0.6F, 0.08F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 2),
               wingFrame2.wingFlap(WaveFunctionKt.triangleFunction$default(0.4F, 0.1F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 2)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -4),
               ModelPartExtensionsKt.createTransformation(this.wing_front_left).addRotationDegrees(2, -30),
               ModelPartExtensionsKt.createTransformation(this.wing_front_right).addRotationDegrees(2, 30)
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

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: VibravaModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "vibrava", "cry", null, 4, null);
   }
}
