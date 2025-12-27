package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

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

public class MareepModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var shearedbattleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var shearedsleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var shearedstanding: Pose<PokemonEntity, ModelFrame>
   public final lateinit var shearedwalk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final val wool: ModelPart

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "mareep");
      this.head = this.getPart("head");
      this.wool = this.getPart("wool");
      this.portraitScale = 2.3F;
      this.portraitTranslation = new Vec3(-0.5, -1.2, 0.0);
      this.profileScale = 0.9F;
      this.profileTranslation = new Vec3(0.0, 0.4, 0.0);
      this.cryAnimation = MareepModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "mareep", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "unsheared_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "mareep", "sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true)},
            null,
            312,
            null
         )
      );
      this.setShearedsleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sheared_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "mareep", "sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false)},
            null,
            312,
            null
         )
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var33: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var33, var10002),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "mareep", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true)},
            new ModelQuirk[]{blink},
            48,
            null
         )
      );
      var var9: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var16: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var23: Array<ModelPartTransformation> = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true)};
      var var28: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "mareep", "ground_walk", null, 4, null)
      };
      val var34: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var34, "walk", var9, <unrepresentable>.INSTANCE, 10, null, null, var28, var23, var16, 48, null));
      var9 = PoseType.Companion.getSTATIONARY_POSES();
      var16 = new ModelQuirk[]{blink};
      var23 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false)};
      var28 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "mareep", "ground_idle", null, 4, null)
      };
      val var35: PoseableEntityModel = this;
      this.setShearedstanding(
         PoseableEntityModel.registerPose$default(var35, "shearedstanding", var9, <unrepresentable>.INSTANCE, 0, null, null, var28, var23, var16, 48, null)
      );
      var9 = PoseType.Companion.getMOVING_POSES();
      var16 = new ModelQuirk[]{blink};
      var23 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false)};
      var28 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "mareep", "ground_walk", null, 4, null)
      };
      val var36: PoseableEntityModel = this;
      this.setShearedwalk(
         PoseableEntityModel.registerPose$default(var36, "shearedwalking", var9, <unrepresentable>.INSTANCE, 0, null, null, var28, var23, var16, 48, null)
      );
      var9 = PoseType.Companion.getSTATIONARY_POSES();
      var16 = new ModelQuirk[]{blink};
      var23 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true)};
      var28 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "mareep", "battle_idle", null, 4, null)
      };
      val var37: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var37, "battle_idle", var9, <unrepresentable>.INSTANCE, 10, null, null, var28, var23, var16, 48, null)
      );
      var9 = PoseType.Companion.getSTATIONARY_POSES();
      var16 = new ModelQuirk[]{blink};
      var23 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false)};
      var28 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "mareep", "battle_idle", null, 4, null)
      };
      val var38: PoseableEntityModel = this;
      this.setShearedbattleidle(
         PoseableEntityModel.registerPose$default(var38, "battle_idle_sheared", var9, <unrepresentable>.INSTANCE, 10, null, null, var28, var23, var16, 48, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: MareepModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "mareep", "cry", null, 4, null);
   }
}
