package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen8

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

public class ArctozoltModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
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
   public final lateinit var surface_sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surfacefloat: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surfaceswim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_sleep: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "arctozolt");
      this.head = this.getPart("head_ai");
      this.portraitTranslation = new Vec3(-0.45, 2.15, 0.0);
      this.portraitScale = 0.85F;
      this.profileTranslation = new Vec3(0.02, 0.95, 0.0);
      this.profileScale = 0.33F;
      this.cryAnimation = ArctozoltModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "arctozolt", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val quirk: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(30.0F, 60.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "arctozolt", "quirk", null, 4, null);
            }
         }) as Function1, 6, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arctozolt", "sleep", null, 4, null)},
            null,
            null,
            432,
            null
         )
      );
      this.setSurface_sleep(
         PoseableEntityModel.registerPose$default(
            this,
            "surface_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arctozolt", "surfacewater_sleep", null, 4, null)},
            null,
            null,
            432,
            null
         )
      );
      this.setWater_sleep(
         PoseableEntityModel.registerPose$default(
            this,
            "water_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arctozolt", "surfacewater_sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -53)},
            null,
            304,
            null
         )
      );
      var var31: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var32: java.util.Set = var31;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.minus(SetsKt.plus(var32, var10002), PoseType.FLOAT),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "arctozolt", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink, quirk},
            176,
            null
         )
      );
      var31 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            SetsKt.minus(var31, PoseType.SWIM),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "arctozolt", "ground_walk", null, 4, null)
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
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "arctozolt", "water_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setSwim(
         PoseableEntityModel.registerPose$default(
            this,
            "swim",
            PoseType.SWIM,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "arctozolt", "water_swim", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setSurfacefloat(
         PoseableEntityModel.registerPose$default(
            this,
            "surfacefloat",
            PoseType.STAND,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "arctozolt", "surfacewater_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink, quirk},
            176,
            null
         )
      );
      this.setSurfaceswim(
         PoseableEntityModel.registerPose$default(
            this,
            "surfaceswim",
            PoseType.WALK,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "arctozolt", "surfacewater_swim", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      val var16: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var22: Array<ModelQuirk> = new ModelQuirk[]{blink, quirk};
      val var28: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "arctozolt", "battle_idle", null, 4, null)
      };
      val var34: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var34, "battleidle", var16, <unrepresentable>.INSTANCE, 10, null, null, var28, null, var22, 176, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: ArctozoltModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "arctozolt", "cry", null, 4, null);
   }
}
