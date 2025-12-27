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

public class CopperajahModel(root: ModelPart) : PokemonPoseableModel {
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
      this.rootPart = this.registerChildWithAllChildren(root, "copperajah");
      this.portraitScale = 0.6F;
      this.portraitTranslation = new Vec3(-0.67, 1.62, 0.0);
      this.profileScale = 0.3F;
      this.profileTranslation = new Vec3(-0.02, 1.2, 0.0);
   }

   public override fun registerPoses() {
      this.getAnimations()
         .put("physical", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('copperajah', 'physical', 'look', q.curve('symmetrical_wide'))"));
      this.getAnimations()
         .put("special", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('copperajah', 'special', 'look', q.curve('symmetrical_wide'))"));
      this.getAnimations().put("status", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('copperajah', 'status', q.curve('symmetrical_wide'))"));
      this.getAnimations().put("recoil", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('copperajah', 'recoil')"));
      this.getAnimations().put("cry", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('copperajah', 'cry')"));
      val faint: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('copperajah', 'faint', q.curve('one'))");
      val blink: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "copperajah", "blink", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "copperajah", "quirk_idle", null, 4, null);
            }
         }) as Function1, 6, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "copperajah", "sleep", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            86,
            null
         )
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var28: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var28, var10002),
            <unrepresentable>.INSTANCE,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "copperajah", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, quirk},
            168,
            null
         )
      );
      var var10: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var18: Array<ModelQuirk> = new ModelQuirk[]{blink, quirk};
      val var19: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var var26: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "copperajah", "ground_walk", null, 4, null)};
      val var29: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var29, "walk", var10, null, 0, var19, null, var26, null, var18, 172, null));
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var18 = new ModelQuirk[]{blink, quirk};
      val var21: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var26 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "copperajah", "battle_idle", null, 4, null)};
      val var30: PoseableEntityModel = this;
      this.setBattle_idle(
         PoseableEntityModel.registerPose$default(var30, "battle_idle", var10, <unrepresentable>.INSTANCE, 0, var21, null, var26, null, var18, 168, null)
      );
   }
}
