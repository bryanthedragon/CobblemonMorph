package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class PidgeotModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame {
   public open val cryAnimation: CryProvider
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var stand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   private final val wingClosedLeft: ModelPart
   private final val wingClosedRight: ModelPart
   private final val wingOpenLeft: ModelPart
   private final val wingOpenRight: ModelPart

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "pidgeot");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.head = this.getPart("neck");
      this.wingOpenRight = this.getPart("wing_open_right");
      this.wingOpenLeft = this.getPart("wing_open_left");
      this.wingClosedRight = this.getPart("wing_closed_right");
      this.wingClosedLeft = this.getPart("wing_closed_left");
      this.portraitScale = 2.2F;
      this.portraitTranslation = new Vec3(-0.6, 0.15, 0.0);
      this.profileScale = 0.9F;
      this.profileTranslation = new Vec3(0.0, 0.4, 0.0);
      this.cryAnimation = PidgeotModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgeot", "blink", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgeot", "air_fly_quirk", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgeot", "air_fly_quirk2", null, 4, null);
            }
         }) as Function1, 7, null
      );
      var var5: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)
      };
      var var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var26: java.util.Set = SetsKt.minus(SetsKt.minus(var10001, PoseType.HOVER), PoseType.FLOAT);
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
               PoseableEntityModel.bedrock$default(this, "pidgeot", "ground_idle_PLACEHOLDER", null, 4, null)
            },
            var5,
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      var5 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)
      };
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
               PoseableEntityModel.bedrock$default(this, "pidgeot", "ground_idle_PLACEHOLDER", null, 4, null),
               new BipedWalkAnimation(this, 0.0F, 0.0F, 6, null)
            },
            var5,
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
               PoseableEntityModel.bedrock$default(this, "pidgeot", "air_idle", null, 4, null)
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
            "flying",
            SetsKt.setOf(new PoseType[]{PoseType.FLY, PoseType.SWIM}),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgeot", "air_fly", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(true)
            },
            new ModelQuirk[]{blink, flyQuirk1, flyQuirk2},
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

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: PidgeotModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "pidgeot", "cry", null, 4, null);
   }
}
