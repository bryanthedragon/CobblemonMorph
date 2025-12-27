package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
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

public class QuaquavalModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame {
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
   public final lateinit var standing2: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surface_floating: Pose<PokemonEntity, ModelFrame>
   public final lateinit var surface_swimming: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swimming: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>
   public final val water_feathers: ModelPart
   public final val wateroffset: Int

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "quaquaval");
      this.head = this.getPart("head");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.water_feathers = this.getPart("water_feathers");
      this.portraitScale = 2.3F;
      this.portraitTranslation = new Vec3(-0.42, 3.7, 0.0);
      this.profileScale = 0.32F;
      this.profileTranslation = new Vec3(0.0, 1.24, 0.0);
      this.wateroffset = 19;
      this.cryAnimation = QuaquavalModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "quaquaval", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "quaquaval", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      val var36: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var37: java.util.Set = var36;
      val var41: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var37, var41),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaquaval", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(false)},
            new ModelQuirk[]{blink},
            48,
            null
         )
      );
      var var8: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var15: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(false)
      };
      var var22: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var29: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "quaquaval", "ground_idle2", null, 4, null)
      };
      val var38: PoseableEntityModel = this;
      this.setStanding2(
         PoseableEntityModel.registerPose$default(var38, "standing2", var8, <unrepresentable>.INSTANCE, 10, null, null, var29, var15, var22, 48, null)
      );
      var8 = PoseType.Companion.getMOVING_POSES();
      var15 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(false)};
      var22 = new ModelQuirk[]{blink};
      var29 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "quaquaval", "ground_walk", null, 4, null)
      };
      val var39: PoseableEntityModel = this;
      this.setWalking(
         PoseableEntityModel.registerPose$default(var39, "walking", var8, <unrepresentable>.INSTANCE, 10, null, null, var29, var15, var22, 48, null)
      );
      var8 = PoseType.Companion.getSTATIONARY_POSES();
      val var17: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var24: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(true)
      };
      var29 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "quaquaval", "battle_idle", null, 4, null)
      };
      val var40: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var40, "battle_idle", var8, <unrepresentable>.INSTANCE, 10, null, null, var29, var24, var17, 48, null)
      );
      this.setFloating(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.FLOAT,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaquaval", "water_idle", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(false)},
            new ModelQuirk[]{blink},
            24,
            null
         )
      );
      this.setSwimming(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SWIM,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaquaval", "water_swim", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(false)},
            new ModelQuirk[]{blink},
            24,
            null
         )
      );
      this.setSurface_floating(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.STAND,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaquaval", "water_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)
            },
            new ModelQuirk[]{blink},
            24,
            null
         )
      );
      this.setSurface_swimming(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.WALK,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "quaquaval", "water_swim", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.water_feathers).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)
            },
            new ModelQuirk[]{blink},
            24,
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
   fun `cryAnimation$lambda$0`(`this$0`: QuaquavalModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "quaquaval", "cry", null, 4, null);
   }
}
