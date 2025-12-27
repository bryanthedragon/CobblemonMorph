package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
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

public class PidgeyModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val cryAnimation: CryProvider
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var stand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   private final val wingClosedLeft: ModelPart
   private final val wingClosedRight: ModelPart
   private final val wingOpenLeft: ModelPart
   private final val wingOpenRight: ModelPart

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "pidgey");
      this.head = this.getPart("head");
      this.wingOpenRight = this.getPart("wing_open_right");
      this.wingOpenLeft = this.getPart("wing_open_left");
      this.wingClosedRight = this.getPart("wing_closed_right");
      this.wingClosedLeft = this.getPart("wing_closed_left");
      this.portraitScale = 3.5F;
      this.portraitTranslation = new Vec3(-0.1, -2.1, 0.0);
      this.profileScale = 1.2F;
      this.profileTranslation = new Vec3(0.0, -0.01, 0.0);
      this.cryAnimation = PidgeyModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgey", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleeping",
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "pidgey", "sleep_PLACEHOLDER", null, 4, null)},
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
      var var10001: EnumSet = PoseType.Companion.getSHOULDER_POSES();
      val var22: java.util.Set = var10001;
      var var10002: EnumSet = PoseType.Companion.getUI_POSES();
      val var23: java.util.Set = SetsKt.plus(var22, var10002);
      var10002 = PoseType.Companion.getSTATIONARY_POSES();
      this.setStand(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.minus(SetsKt.plus(var23, var10002), PoseType.HOVER),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgey", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            52,
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
               PoseableEntityModel.bedrock$default(this, "pidgey", "air_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(true)
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
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgey", "air_fly", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(true)
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
            "walking",
            SetsKt.minus(var10001, PoseType.FLY),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "pidgey", "ground_walk", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)
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
   fun `cryAnimation$lambda$0`(`this$0`: PidgeyModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "pidgey", "cry", null, 4, null);
   }
}
