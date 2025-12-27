package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5

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

public class SandileModel(root: ModelPart) : PokemonPoseableModel {
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
      this.rootPart = this.registerChildWithAllChildren(root, "sandile");
      this.portraitScale = 2.54F;
      this.portraitTranslation = new Vec3(-0.5, -1.9, 0.0);
      this.profileScale = 0.84F;
      this.profileTranslation = new Vec3(0.05, 0.48, 0.0);
   }

   public override fun registerPoses() {
      this.getAnimations().put("cry", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('sandile', 'cry')"));
      val faint: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('sandile', 'faint', q.curve('one'))");
      val blink: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "sandile", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val bite: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "sandile", "bite_quirk", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "sandile", "sleep", null, 4, null)},
            null,
            new ModelQuirk[]{bite},
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "sandile", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, bite},
            168,
            null
         )
      );
      var var10: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var18: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var19: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var var26: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "sandile", "ground_walk", null, 4, null)};
      val var29: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var29, "walk", var10, null, 0, var19, null, var26, null, var18, 172, null));
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var18 = new ModelQuirk[]{blink, bite};
      val var21: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var26 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "sandile", "battle_idle", null, 4, null)};
      val var30: PoseableEntityModel = this;
      this.setBattle_idle(
         PoseableEntityModel.registerPose$default(var30, "battle_idle", var10, <unrepresentable>.INSTANCE, 0, var21, null, var26, null, var18, 168, null)
      );
   }
}
