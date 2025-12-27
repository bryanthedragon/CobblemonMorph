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

public class HippowdonModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var battleidleredsand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var battleidlesand: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   private final val redsand: ModelPart
   public open val rootPart: ModelPart
   private final val sand: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var sleepredsand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var sleepsand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standingredsand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standingsand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walkredsand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walksand: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "hippowdon");
      this.head = this.getPart("head");
      this.sand = this.getPart("sand");
      this.redsand = this.getPart("redsand");
      this.portraitScale = 0.6F;
      this.portraitTranslation = new Vec3(-0.63, 0.73, 0.0);
      this.profileScale = 0.4F;
      this.profileTranslation = new Vec3(-0.1, 1.0, 0.0);
      this.cryAnimation = HippowdonModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "hippowdon", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val idlequirk: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "hippowdon", "quirk_idle", null, 4, null);
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
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "hippowdon", "sleep", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
            },
            null,
            312,
            null
         )
      );
      this.setSleepredsand(
         PoseableEntityModel.registerPose$default(
            this,
            "sleepsand",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_sleep", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
            },
            null,
            312,
            null
         )
      );
      val var10001: EnumSet = PoseType.Companion.getUI_POSES();
      val var46: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var46, var10002),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "hippowdon", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
            },
            new ModelQuirk[]{blink, idlequirk},
            56,
            null
         )
      );
      var var10: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var20: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var30: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
      };
      var var38: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "hippowdon", "ground_walk", null, 4, null)
      };
      val var47: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var47, "walk", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null));
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var20 = new ModelQuirk[]{blink, idlequirk};
      var30 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
      };
      var38 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_idle", null, 4, null)
      };
      val var48: PoseableEntityModel = this;
      this.setStandingsand(
         PoseableEntityModel.registerPose$default(var48, "standingsand", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null)
      );
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var20 = new ModelQuirk[]{blink, idlequirk};
      var30 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
      };
      var38 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_idle", null, 4, null)
      };
      val var49: PoseableEntityModel = this;
      this.setStandingredsand(
         PoseableEntityModel.registerPose$default(var49, "standingredsand", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null)
      );
      var10 = PoseType.Companion.getMOVING_POSES();
      var20 = new ModelQuirk[]{blink};
      var30 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
      };
      var38 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_swim", null, 4, null)};
      val var50: PoseableEntityModel = this;
      this.setWalksand(
         PoseableEntityModel.registerPose$default(var50, "walksand", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null)
      );
      var10 = PoseType.Companion.getMOVING_POSES();
      var20 = new ModelQuirk[]{blink};
      var30 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
      };
      var38 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_swim", null, 4, null)};
      val var51: PoseableEntityModel = this;
      this.setWalkredsand(
         PoseableEntityModel.registerPose$default(var51, "walkredsand", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null)
      );
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var20 = new ModelQuirk[]{blink};
      var30 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
      };
      var38 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "hippowdon", "battle_idle", null, 4, null)
      };
      val var52: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var52, "battleidle", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null)
      );
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var20 = new ModelQuirk[]{blink};
      var30 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)
      };
      var38 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_battle_idle", null, 4, null)
      };
      val var53: PoseableEntityModel = this;
      this.setBattleidlesand(
         PoseableEntityModel.registerPose$default(var53, "battleidlesand", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null)
      );
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var20 = new ModelQuirk[]{blink};
      var30 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(true)
      };
      var38 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_battle_idle", null, 4, null)
      };
      val var54: PoseableEntityModel = this;
      this.setBattleidleredsand(
         PoseableEntityModel.registerPose$default(var54, "battleidleredsand", var10, <unrepresentable>.INSTANCE, 0, null, null, var38, var30, var20, 56, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: HippowdonModel, var1: PokemonEntity, pose: PoseableEntityState): StatefulAnimation {
      return if (pose.isPosedIn(`this$0`.getStandingsand(), `this$0`.getWalksand()))
         PoseableEntityModel.bedrockStateful$default(`this$0`, "hippowdon", "sand_cry", null, 4, null)
         else
         (
            if (pose.isPosedIn(`this$0`.getBattleidle()))
               PoseableEntityModel.bedrockStateful$default(`this$0`, "hippowdon", "battle_cry", null, 4, null)
               else
               (
                  if (pose.isPosedIn(`this$0`.getBattleidlesand(), `this$0`.getBattleidleredsand()))
                     PoseableEntityModel.bedrockStateful$default(`this$0`, "hippowdon", "sand_battle_cry", null, 4, null)
                     else
                     PoseableEntityModel.bedrockStateful$default(`this$0`, "hippowdon", "cry", null, 4, null)
               )
         );
   }
}
