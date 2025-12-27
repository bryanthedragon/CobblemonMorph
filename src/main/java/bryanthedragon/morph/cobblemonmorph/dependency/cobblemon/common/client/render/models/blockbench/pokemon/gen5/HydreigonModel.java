package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame
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
import kotlin.jvm.functions.Function3
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class HydreigonModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "hydreigon");
      this.head = this.getPart("neck");
      this.portraitScale = 1.57F;
      this.portraitTranslation = new Vec3(-1.04, 1.93, 0.0);
      this.profileScale = 0.58F;
      this.profileTranslation = new Vec3(-0.06, 1.2, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "hydreigon", "blink", null, 4, null);
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
            this.leftWing = `$receiver`.getPart("wing_top_left");
            this.rightWing = `$receiver`.getPart("wing_top_right");
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
            this.leftWing = `$receiver`.getPart("wing_middle_left");
            this.rightWing = `$receiver`.getPart("wing_middle_right");
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
      val wingFrame3: <unrepresentable> = new BiWingedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart leftWing;
         @NotNull
         private final ModelPart rightWing;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.leftWing = `$receiver`.getPart("wing_bottom_left");
            this.rightWing = `$receiver`.getPart("wing_bottom_right");
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
      var var6: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var10001: EnumSet = PoseType.Companion.getUI_POSES();
      val var20: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var20, var10002),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "hydreigon", "air_idle", null, 4, null),
               wingFrame1.wingFlap(
                  WaveFunctionKt.sineFunction$default(0.8F, 1.5F, 0.0F, -AngleExtensionsKt.toRadians(25.0F), 4, null), <unrepresentable>.INSTANCE, 1
               ),
               wingFrame2.wingFlap(
                  WaveFunctionKt.cosineFunction$default(0.65F, 2.0F, 0.0F, -AngleExtensionsKt.toRadians(25.0F), 4, null), <unrepresentable>.INSTANCE, 1
               ),
               wingFrame3.wingFlap(
                  WaveFunctionKt.sineFunction$default(0.5F, 2.0F, 0.0F, -AngleExtensionsKt.toRadians(25.0F), 4, null), <unrepresentable>.INSTANCE, 1
               )
            },
            null,
            var6,
            180,
            null
         )
      );
      var6 = new ModelQuirk[]{blink};
      val var13: EnumSet = PoseType.Companion.getMOVING_POSES();
      val var14: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "hydreigon", "air_fly", null, 4, null),
         wingFrame1.wingFlap(WaveFunctionKt.sineFunction$default(0.5F, 2.0F, 0.0F, -AngleExtensionsKt.toRadians(25.0F), 4, null), <unrepresentable>.INSTANCE, 1),
         wingFrame2.wingFlap(
            WaveFunctionKt.cosineFunction$default(0.65F, 2.0F, 0.0F, -AngleExtensionsKt.toRadians(25.0F), 4, null), <unrepresentable>.INSTANCE, 1
         ),
         wingFrame3.wingFlap(WaveFunctionKt.sineFunction$default(0.8F, 1.5F, 0.0F, -AngleExtensionsKt.toRadians(25.0F), 4, null), <unrepresentable>.INSTANCE, 1)
      };
      val var21: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var21, "walk", var13, null, 10, null, null, var14, null, var6, 180, null));
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
