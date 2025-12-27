package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5

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

public class UnfezantModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BiWingedFrame {
   public final val closedWingLeft: ModelPart
   public final val closedWingRight: ModelPart
   public open val cryAnimation: CryProvider
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open val leftLeg: ModelPart
   public open val leftWing: ModelPart
   public final val openWingLeft: ModelPart
   public final val openWingRight: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rightWing: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var stand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "unfezant");
      this.leftWing = this.getPart("wing_left");
      this.rightWing = this.getPart("wing_right");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.head = this.getPart("head_ai");
      this.openWingLeft = this.getPart("open_left");
      this.openWingRight = this.getPart("open_right");
      this.closedWingLeft = this.getPart("closed_left");
      this.closedWingRight = this.getPart("closed_right");
      this.portraitScale = 2.1F;
      this.portraitTranslation = new Vec3(-0.5, 1.5, 0.0);
      this.profileScale = 0.7F;
      this.profileTranslation = new Vec3(0.0, 0.7, 0.0);
      this.cryAnimation = UnfezantModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "unfezant", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      var var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var22: java.util.Set = SetsKt.minus(SetsKt.minus(var10001, PoseType.HOVER), PoseType.FLOAT);
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStand(
         PoseableEntityModel.registerPose$default(
            this,
            "stand",
            SetsKt.plus(var22, var10002),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "unfezant", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.openWingLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.openWingRight).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.closedWingLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.closedWingRight).withVisibility(true)
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
            SetsKt.minus(SetsKt.minus(var10001, PoseType.FLY), PoseType.SWIM),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "unfezant", "ground_idle", null, 4, null),
               new BipedWalkAnimation(this, 0.0F, 0.0F, 6, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.openWingLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.openWingRight).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.closedWingLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.closedWingRight).withVisibility(true)
            },
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      this.setHover(
         PoseableEntityModel.registerPose$default(
            this,
            "floating",
            SetsKt.setOf(new PoseType[]{PoseType.FLOAT, PoseType.HOVER}),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "unfezant", "air_idle", null, 4, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.6F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(10.0F), 4, null), <unrepresentable>.INSTANCE, 2
               )
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.openWingLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.openWingRight).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.closedWingLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.closedWingRight).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      this.setFly(
         PoseableEntityModel.registerPose$default(
            this,
            "flying",
            SetsKt.setOf(new PoseType[]{PoseType.FLY, PoseType.SWIM}),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "unfezant", "air_idle", null, 4, null),
               new WingFlapIdleAnimation(
                  this, WaveFunctionKt.sineFunction$default(0.9F, 0.9F, 0.0F, -AngleExtensionsKt.toRadians(14.0F), 4, null), <unrepresentable>.INSTANCE, 2
               )
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.openWingLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.openWingRight).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.closedWingLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.closedWingRight).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            60,
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
   fun `cryAnimation$lambda$0`(`this$0`: UnfezantModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "unfezant", "cry", null, 4, null);
   }
}
