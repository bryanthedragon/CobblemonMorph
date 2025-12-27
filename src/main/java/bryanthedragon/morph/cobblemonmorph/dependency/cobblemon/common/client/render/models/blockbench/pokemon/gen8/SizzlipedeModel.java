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

public class SizzlipedeModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var shoulderLeft: Pose<PokemonEntity, ModelFrame>
   public final lateinit var shoulderRight: Pose<PokemonEntity, ModelFrame>
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "sizzlipede");
      this.head = this.getPart("head");
      this.portraitScale = 3.24F;
      this.portraitTranslation = new Vec3(-0.89, -3.35, 0.0);
      this.profileScale = 0.9F;
      this.profileTranslation = new Vec3(0.07, 0.21, 0.0);
      this.cryAnimation = SizzlipedeModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "sizzlipede", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "sizzlipede", "sleep", null, 4, null)},
            null,
            null,
            218,
            null
         )
      );
      val var21: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var22: java.util.Set = var21;
      val var25: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var22, var25),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "sizzlipede", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      var var8: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var12: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var16: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "sizzlipede", "battle_idle", null, 4, null)};
      val var23: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var23, "battle_idle", var8, <unrepresentable>.INSTANCE, 10, null, null, var16, null, var12, 176, null)
      );
      var8 = PoseType.Companion.getMOVING_POSES();
      var12 = new ModelQuirk[]{blink};
      var16 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "sizzlipede", "ground_walk", null, 4, null)
      };
      val var24: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var24, "walk", var8, null, 0, null, null, var16, null, var12, 188, null));
      this.setShoulderLeft(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SHOULDER_LEFT,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "sizzlipede", "shoulder_left", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, 1.3),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, 3.8),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(2, 1.1)
            },
            new ModelQuirk[]{blink},
            30,
            null
         )
      );
      this.setShoulderRight(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SHOULDER_RIGHT,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "sizzlipede", "shoulder_right", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, 1.3),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, -3.8),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(2, 1.1)
            },
            new ModelQuirk[]{blink},
            30,
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
   fun `cryAnimation$lambda$0`(`this$0`: SizzlipedeModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "sizzlipede", "cry", null, 4, null);
   }
}
