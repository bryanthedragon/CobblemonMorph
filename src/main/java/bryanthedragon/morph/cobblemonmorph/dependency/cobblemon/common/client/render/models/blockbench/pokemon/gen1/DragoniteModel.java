package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
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

public class DragoniteModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surfacewateridle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surfacewaterswim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var wateridle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var waterswim: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "dragonite");
      this.head = this.getPart("head");
      this.portraitScale = 1.2F;
      this.portraitTranslation = new Vec3(-0.2, 2.6, 0.0);
      this.profileScale = 0.41F;
      this.profileTranslation = new Vec3(0.0, 1.1, -6.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dragonite", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonite", "sleep", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      val var31: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var31, PoseType.STAND),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
               PoseableEntityModel.bedrock$default(this, "dragonite", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.WALK,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
               PoseableEntityModel.bedrock$default(this, "dragonite", "ground_walk", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      var var8: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var16: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var24: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 10.0F, null, null, null, 959, null),
         PoseableEntityModel.bedrock$default(this, "dragonite", "battle_idle", null, 4, null)
      };
      val var32: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var32, "battleidle", var8, <unrepresentable>.INSTANCE, 0, null, null, var24, null, var16, 184, null)
      );
      this.setHover(
         PoseableEntityModel.registerPose$default(
            this,
            "hover",
            PoseType.HOVER,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
               PoseableEntityModel.bedrock$default(this, "dragonite", "air_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setFly(
         PoseableEntityModel.registerPose$default(
            this,
            "fly",
            PoseType.FLY,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
               PoseableEntityModel.bedrock$default(this, "dragonite", "air_fly", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setWateridle(
         PoseableEntityModel.registerPose$default(
            this,
            "wateridle",
            PoseType.FLOAT,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
               PoseableEntityModel.bedrock$default(this, "dragonite", "water_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      this.setWaterswim(
         PoseableEntityModel.registerPose$default(
            this,
            "waterswim",
            PoseType.SWIM,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
               PoseableEntityModel.bedrock$default(this, "dragonite", "water_swim", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      var8 = PoseType.Companion.getSTANDING_POSES();
      var16 = new ModelQuirk[]{blink};
      var24 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
         PoseableEntityModel.bedrock$default(this, "dragonite", "surfacewater_idle", null, 4, null)
      };
      val var33: PoseableEntityModel = this;
      this.setSurfacewateridle(
         PoseableEntityModel.registerPose$default(var33, "surfacewateridle", var8, <unrepresentable>.INSTANCE, 0, null, null, var24, null, var16, 184, null)
      );
      var8 = PoseType.Companion.getMOVING_POSES();
      var16 = new ModelQuirk[]{blink};
      var24 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, 15.0F, null, null, null, 959, null),
         PoseableEntityModel.bedrock$default(this, "dragonite", "surfacewater_swim", null, 4, null)
      };
      val var34: PoseableEntityModel = this;
      this.setSurfacewaterswim(
         PoseableEntityModel.registerPose$default(var34, "surfacewaterswim", var8, <unrepresentable>.INSTANCE, 0, null, null, var24, null, var16, 184, null)
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
}
