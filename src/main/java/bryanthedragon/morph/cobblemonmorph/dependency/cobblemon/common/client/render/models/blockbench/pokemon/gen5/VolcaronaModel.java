package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5

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

public class VolcaronaModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public final val leftfluff: ModelPart
   public final val leftwings: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public final val rightfluff: ModelPart
   public final val rightwings: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "volcarona");
      this.head = this.getPart("head");
      this.rightwings = this.getPart("wings_right");
      this.leftwings = this.getPart("wings_left");
      this.rightfluff = this.getPart("wing_base_right");
      this.leftfluff = this.getPart("wing_base_left");
      this.portraitScale = 1.83F;
      this.portraitTranslation = new Vec3(-0.62, 1.89, 0.0);
      this.profileScale = 0.46F;
      this.profileTranslation = new Vec3(0.0, 1.06, 0.0);
      this.cryAnimation = VolcaronaModel::cryAnimation$lambda$0;
   }

   public override fun registerPoses() {
      val wingFrame: <unrepresentable> = new BiWingedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart leftWing;
         @NotNull
         private final ModelPart rightWing;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.leftWing = `$receiver`.getPart("wings_left");
            this.rightWing = `$receiver`.getPart("wings_right");
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
      val fluffFrame: <unrepresentable> = new BiWingedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart leftWing;
         @NotNull
         private final ModelPart rightWing;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.leftWing = `$receiver`.getPart("wing_base_left");
            this.rightWing = `$receiver`.getPart("wing_base_right");
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
      val var10001: EnumSet = PoseType.Companion.getUI_POSES();
      val var10: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var10, var10002),
            null,
            20,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, 0.2F, 0.3F, null, null, null, null, 975, null),
               PoseableEntityModel.bedrock$default(this, "volcarona", "ground_idle", null, 4, null),
               wingFrame.wingFlap(WaveFunctionKt.sineFunction$default(0.25F, 1.0F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 1),
               fluffFrame.wingFlap(WaveFunctionKt.sineFunction$default(0.3F, 1.0F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 1)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.rightwings).addRotationDegrees(1, 5),
               ModelPartExtensionsKt.createTransformation(this.leftwings).addRotationDegrees(1, -5),
               ModelPartExtensionsKt.createTransformation(this.rightfluff).addRotationDegrees(1, 17),
               ModelPartExtensionsKt.createTransformation(this.leftfluff).addRotationDegrees(1, -17)
            },
            null,
            308,
            null
         )
      );
      val var7: EnumSet = PoseType.Companion.getMOVING_POSES();
      val var8: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, 0.2F, 0.3F, null, null, null, null, 975, null),
         PoseableEntityModel.bedrock$default(this, "volcarona", "ground_idle", null, 4, null),
         wingFrame.wingFlap(WaveFunctionKt.sineFunction$default(0.25F, 0.8F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 1),
         fluffFrame.wingFlap(WaveFunctionKt.sineFunction$default(0.3F, 0.8F, 0.0F, 0.0F, 12, null), <unrepresentable>.INSTANCE, 1)
      };
      val var9: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.rightwings).addRotationDegrees(1, 5),
         ModelPartExtensionsKt.createTransformation(this.leftwings).addRotationDegrees(1, -5),
         ModelPartExtensionsKt.createTransformation(this.rightfluff).addRotationDegrees(1, 17),
         ModelPartExtensionsKt.createTransformation(this.leftfluff).addRotationDegrees(1, -17),
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -5)
      };
      val var11: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var11, "walk", var7, null, 20, null, null, var8, var9, null, 308, null));
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
   fun `cryAnimation$lambda$0`(`this$0`: VolcaronaModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "volcarona", "cry", null, 4, null);
   }
}
