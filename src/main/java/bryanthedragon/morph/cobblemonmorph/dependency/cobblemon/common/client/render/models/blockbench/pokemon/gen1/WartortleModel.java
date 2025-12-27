package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class WartortleModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var floating: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var shoulderLeft: Pose<PokemonEntity, ModelFrame>
   public final val shoulderOffset: Double
   public final lateinit var shoulderRight: Pose<PokemonEntity, ModelFrame>
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swimming: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_idle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_swim: Pose<PokemonEntity, ModelFrame>
   public final val wateroffset: Int

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "wartortle");
      this.head = this.getPart("head_ai");
      this.portraitScale = 1.57F;
      this.portraitTranslation = new Vec3(-0.05, 0.54, 0.0);
      this.profileScale = 0.69F;
      this.profileTranslation = new Vec3(-0.04, 0.69, 0.0);
      this.shoulderOffset = 5.5;
      this.wateroffset = -10;
   }

   public override fun registerPoses() {
      this.getAnimations()
         .put("physical", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'physical', 'look', q.curve('symmetrical_wide'))"));
      this.getAnimations()
         .put("special", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'special', 'look', q.curve('symmetrical_wide'))"));
      this.getAnimations().put("status", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'status', q.curve('symmetrical_wide'))"));
      this.getAnimations().put("recoil", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('wartortle', 'recoil')"));
      this.getAnimations().put("cry", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('wartortle', 'cry')"));
      val faint: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'faint', q.curve('one'))");
      val blink: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "wartortle", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val quirkidle: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "wartortle", "quirk_idle", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wartortle", "sleep", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            86,
            null
         )
      );
      var var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var51: java.util.Set = SetsKt.minus(var10001, PoseType.HOVER);
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var51, var10002),
            <unrepresentable>.INSTANCE,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "wartortle", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink, quirkidle},
            168,
            null
         )
      );
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(
            this,
            "battle_standing",
            SetsKt.setOf(PoseType.STAND),
            <unrepresentable>.INSTANCE,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "wartortle", "battle_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink, quirkidle},
            168,
            null
         )
      );
      var10001 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            SetsKt.minus(var10001, PoseType.FLY),
            <unrepresentable>.INSTANCE,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "wartortle", "ground_walk", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            168,
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
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "wartortle", "water_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            160,
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
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "wartortle", "water_swim", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            160,
            null
         )
      );
      var var15: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var23: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      val var41: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var48: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "wartortle", "surfacewater_idle", null, 4, null)
      };
      var var9: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)
      };
      val var53: PoseableEntityModel = this;
      this.setWater_surface_idle(
         PoseableEntityModel.registerPose$default(var53, "surface_idle", var15, <unrepresentable>.INSTANCE, 0, var23, null, var48, var9, var41, 40, null)
      );
      var15 = PoseType.Companion.getMOVING_POSES();
      val var34: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var35: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var48 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "wartortle", "surfacewater_swim", null, 4, null)
      };
      var9 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      val var54: PoseableEntityModel = this;
      this.setWater_surface_swim(
         PoseableEntityModel.registerPose$default(var54, "surface_swim", var15, <unrepresentable>.INSTANCE, 0, var35, null, var48, var9, var34, 40, null)
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
