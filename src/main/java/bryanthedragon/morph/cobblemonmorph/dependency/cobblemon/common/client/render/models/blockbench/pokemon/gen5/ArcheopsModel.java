package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
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

public class ArcheopsModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleIdle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public final lateinit var float: Pose<PokemonEntity, ModelFrame>
   public final lateinit var flying: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hovering: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "archeops");
      this.head = this.getPart("neck");
      this.portraitTranslation = new Vec3(-1.19, 1.15, 0.0);
      this.portraitScale = 1.51F;
      this.profileTranslation = new Vec3(-0.04, 0.97, -6.0);
      this.profileScale = 0.49F;
      this.cryAnimation = ArcheopsModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "archeops", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val quirk1: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "archeops", "quirk", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val quirk2: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "archeops", "quirk2", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "archeops", "sleep", null, 4, null)},
            null,
            null,
            436,
            null
         )
      );
      var var27: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var28: java.util.Set = var27;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.minus(SetsKt.plus(var28, var10002), PoseType.HOVER),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "archeops", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink, quirk1, quirk2},
            176,
            null
         )
      );
      var27 = PoseType.Companion.getMOVING_POSES();
      this.setWalking(
         PoseableEntityModel.registerPose$default(
            this,
            "walking",
            SetsKt.minus(var27, PoseType.FLY),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "archeops", "ground_walk", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setHovering(
         PoseableEntityModel.registerPose$default(
            this,
            "hovering",
            PoseType.HOVER,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "archeops", "air_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink, quirk1, quirk2},
            180,
            null
         )
      );
      this.setFlying(
         PoseableEntityModel.registerPose$default(
            this,
            "flying",
            PoseType.FLY,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "archeops", "air_fly", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      var var12: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var18: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var24: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "archeops", "surfacewater_swim", null, 4, null)
      };
      val var30: PoseableEntityModel = this;
      this.setSwim(PoseableEntityModel.registerPose$default(var30, "swim", var12, <unrepresentable>.INSTANCE, 10, null, null, var24, null, var18, 176, null));
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var18 = new ModelQuirk[]{blink, quirk1};
      var24 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "archeops", "surfacewater_idle", null, 4, null)
      };
      val var31: PoseableEntityModel = this;
      this.setFloat(PoseableEntityModel.registerPose$default(var31, "float", var12, <unrepresentable>.INSTANCE, 10, null, null, var24, null, var18, 176, null));
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var18 = new ModelQuirk[]{blink, quirk2};
      var24 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "archeops", "battle_idle", null, 4, null)
      };
      val var32: PoseableEntityModel = this;
      this.setBattleIdle(
         PoseableEntityModel.registerPose$default(var32, "battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var24, null, var18, 176, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: ArcheopsModel, var1: PokemonEntity, pose: PoseableEntityState): StatefulAnimation {
      return if (pose.isPosedIn(`this$0`.getHovering(), `this$0`.getFlying()))
         PoseableEntityModel.bedrockStateful$default(`this$0`, "archeops", "air_cry", null, 4, null)
         else
         PoseableEntityModel.bedrockStateful$default(`this$0`, "archeops", "cry", null, 4, null);
   }
}
