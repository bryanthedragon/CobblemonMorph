package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
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

public class QuaxlyModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public final lateinit var floating: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swimming: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_idle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_swim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var waterbattleidle: Pose<PokemonEntity, ModelFrame>
   public final val wateroffset: Int
   public final lateinit var watersleep: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "quaxly");
      this.head = this.getPart("head");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.portraitScale = 2.0F;
      this.portraitTranslation = new Vec3(-0.1, -0.5, 0.0);
      this.profileScale = 0.7F;
      this.profileTranslation = new Vec3(0.0, 0.71, 0.0);
      this.wateroffset = -10;
      this.cryAnimation = QuaxlyModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "quaxly", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleeping",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "quaxly", "sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      this.setWatersleep(
         PoseableEntityModel.registerPose$default(
            this,
            "water_sleeping",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "quaxly", "water_sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      val var32: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var32, PoseType.STAND),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaxly", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.WALK,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaxly", "ground_walk", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setFloating(
         PoseableEntityModel.registerPose$default(
            this,
            "floating",
            PoseType.FLOAT,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaxly", "water_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setSwimming(
         PoseableEntityModel.registerPose$default(
            this,
            "swimming",
            PoseType.SWIM,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaxly", "water_swim", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      var var12: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var19: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var26: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "quaxly", "battle_idle", null, 4, null)
      };
      val var33: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var33, "battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var26, null, var19, 176, null)
      );
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var19 = new ModelQuirk[]{blink};
      var26 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "quaxly", "surfacewater_battle_idle", null, 4, null)
      };
      val var34: PoseableEntityModel = this;
      this.setWaterbattleidle(
         PoseableEntityModel.registerPose$default(var34, "water_battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var26, null, var19, 176, null)
      );
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var19 = new ModelQuirk[]{blink};
      var26 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "quaxly", "surfacewater_idle", null, 4, null)
      };
      var var6: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)
      };
      val var35: PoseableEntityModel = this;
      this.setWater_surface_idle(
         PoseableEntityModel.registerPose$default(var35, "surface_idle", var12, <unrepresentable>.INSTANCE, 0, null, null, var26, var6, var19, 56, null)
      );
      var12 = PoseType.Companion.getMOVING_POSES();
      var19 = new ModelQuirk[]{blink};
      var26 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "quaxly", "surfacewater_swim", null, 4, null)
      };
      var6 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      val var36: PoseableEntityModel = this;
      this.setWater_surface_swim(
         PoseableEntityModel.registerPose$default(var36, "surface_swim", var12, <unrepresentable>.INSTANCE, 0, null, null, var26, var6, var19, 56, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getBattleidle(), this.getSleep()))
         PoseableEntityModel.bedrockStateful$default(this, "quaxly", "faint", null, 4, null)
         else
         (
            if (state.isPosedIn(this.getWater_surface_idle(), this.getWater_surface_swim(), this.getWaterbattleidle(), this.getWatersleep()))
               PoseableEntityModel.bedrockStateful$default(this, "quaxly", "faint", null, 4, null)
               else
               null
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
   fun `cryAnimation$lambda$0`(`this$0`: QuaxlyModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "quaxly", "cry", null, 4, null);
   }
}
