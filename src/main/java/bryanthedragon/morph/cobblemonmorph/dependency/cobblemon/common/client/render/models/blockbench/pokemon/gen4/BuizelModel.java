package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4

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

public class BuizelModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val cryAnimation: CryProvider
   public final lateinit var float: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surfaceWaterIdle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surfaceWaterSleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surfaceWaterSwim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>
   public final lateinit var waterSleep: Pose<PokemonEntity, ModelFrame>
   public final val wateroffset: Int

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "buizel");
      this.head = this.getPart("head");
      this.portraitScale = 2.3F;
      this.portraitTranslation = new Vec3(-0.2, 0.1, 0.0);
      this.profileScale = 0.7F;
      this.profileTranslation = new Vec3(0.0, 0.65, 0.0);
      this.wateroffset = -8;
      this.cryAnimation = BuizelModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "buizel", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "buizel", "sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      this.setWaterSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "water_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "buizel", "water_sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      this.setSurfaceWaterSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "surface_water_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "buizel", "surfacewater_sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)},
            null,
            312,
            null
         )
      );
      var var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var31: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.minus(SetsKt.plus(var31, var10002), PoseType.FLOAT),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "buizel", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      var10001 = PoseType.Companion.getMOVING_POSES();
      this.setWalking(
         PoseableEntityModel.registerPose$default(
            this,
            "walking",
            SetsKt.minus(var10001, PoseType.SWIM),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "buizel", "ground_walk", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setFloat(
         PoseableEntityModel.registerPose$default(
            this,
            "float",
            PoseType.FLOAT,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "buizel", "water_idle", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)},
            new ModelQuirk[]{blink},
            52,
            null
         )
      );
      this.setSwim(
         PoseableEntityModel.registerPose$default(
            this,
            "swim",
            PoseType.SWIM,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "buizel", "water_swim", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      var var13: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var21: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var27: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "buizel", "surfacewater_idle", null, 4, null)
      };
      var var29: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)
      };
      val var33: PoseableEntityModel = this;
      this.setSurfaceWaterIdle(
         PoseableEntityModel.registerPose$default(var33, "surface_water_idle", var13, <unrepresentable>.INSTANCE, 10, null, null, var27, var29, var21, 48, null)
      );
      var13 = PoseType.Companion.getMOVING_POSES();
      var21 = new ModelQuirk[]{blink};
      var27 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "buizel", "surfacewater_swim", null, 4, null)
      };
      var29 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      val var34: PoseableEntityModel = this;
      this.setSurfaceWaterSwim(
         PoseableEntityModel.registerPose$default(var34, "surface_water_swim", var13, <unrepresentable>.INSTANCE, 10, null, null, var27, var29, var21, 48, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: BuizelModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "buizel", "cry", null, 4, null);
   }
}
