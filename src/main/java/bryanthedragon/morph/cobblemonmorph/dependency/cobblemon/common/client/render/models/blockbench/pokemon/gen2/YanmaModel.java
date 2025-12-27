package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

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

public class YanmaModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "yanma");
      this.head = this.getPart("head");
      this.portraitScale = 2.1F;
      this.portraitTranslation = new Vec3(-0.6, -1.0, 0.0);
      this.profileScale = 0.75F;
      this.profileTranslation = new Vec3(0.0, 0.5, 0.0);
      this.cryAnimation = YanmaModel::cryAnimation$lambda$0;
   }

   public override fun registerPoses() {
      val wingFrame1: <unrepresentable> = new BiWingedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart leftWing;
         @NotNull
         private final ModelPart rightWing;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.leftWing = `$receiver`.getPart("wing_left1");
            this.rightWing = `$receiver`.getPart("wing_right1");
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
            this.leftWing = `$receiver`.getPart("wing_left2");
            this.rightWing = `$receiver`.getPart("wing_right2");
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
      var var10001: PoseableEntityModel = this;
      var var10003: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var8: java.util.Set = var10003;
      val var10004: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            var10001,
            "standing",
            SetsKt.plus(var8, var10004),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "yanma", "ground_idle", null, 4, null),
               wingFrame1.wingFlap(WaveFunctionKt.triangleFunction$default(0.4F, 0.1F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 2),
               wingFrame2.wingFlap(WaveFunctionKt.triangleFunction$default(0.4F, 0.1F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 2)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -4)},
            null,
            316,
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
               PoseableEntityModel.bedrock$default(this, "yanma", "ground_idle", null, 4, null),
               wingFrame1.wingFlap(WaveFunctionKt.triangleFunction$default(0.4F, 0.1F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 2),
               wingFrame2.wingFlap(WaveFunctionKt.triangleFunction$default(0.4F, 0.1F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 2)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -4)},
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

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: YanmaModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "yanma", "cry", null, 4, null);
   }
}
