package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen8

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class CufantModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battle_idle: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "cufant");
      this.portraitScale = 1.5F;
      this.portraitTranslation = new Vec3(-0.32, 0.13, 0.0);
      this.profileScale = 0.62F;
      this.profileTranslation = new Vec3(0.05, 0.8, 0.0);
   }

   public override fun registerPoses() {
      this.getAnimations().put("physical", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('cufant', 'physical', 'look', q.curve('symmetrical_wide'))"));
      this.getAnimations().put("special", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('cufant', 'special', 'look', q.curve('symmetrical_wide'))"));
      this.getAnimations().put("status", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('cufant', 'status', q.curve('symmetrical_wide'))"));
      this.getAnimations().put("recoil", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('cufant', 'recoil')"));
      this.getAnimations().put("cry", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('cufant', 'cry')"));
      val faint: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('cufant', 'faint', q.curve('one'))");
      val blink: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "cufant", "blink", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cufant", "sleep", null, 4, null)},
            null,
            null,
            214,
            null
         )
      );
      val var23: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var24: java.util.Set = var23;
      val var27: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var24, var27),
            <unrepresentable>.INSTANCE,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cufant", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            168,
            null
         )
      );
      var var10: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var15: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var16: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var var21: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cufant", "ground_walk", null, 4, null)};
      val var25: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var25, "walk", var10, null, 0, var16, null, var21, null, var15, 172, null));
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var15 = new ModelQuirk[]{blink};
      val var18: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var21 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cufant", "battle_idle", null, 4, null)};
      val var26: PoseableEntityModel = this;
      this.setBattle_idle(
         PoseableEntityModel.registerPose$default(var26, "battle_idle", var10, <unrepresentable>.INSTANCE, 0, var18, null, var21, null, var15, 168, null)
      );
   }
}
