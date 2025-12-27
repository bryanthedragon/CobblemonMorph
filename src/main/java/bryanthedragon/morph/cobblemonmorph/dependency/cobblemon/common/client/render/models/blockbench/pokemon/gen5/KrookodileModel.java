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

public class KrookodileModel(root: ModelPart) : PokemonPoseableModel {
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
      this.rootPart = this.registerChildWithAllChildren(root, "krookodile");
      this.portraitScale = 1.26F;
      this.portraitTranslation = new Vec3(-0.57, 1.6, 0.0);
      this.profileScale = 0.52F;
      this.profileTranslation = new Vec3(0.06, 0.91, 0.0);
   }

   public override fun registerPoses() {
      this.getAnimations().put("cry", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('krookodile', 'cry')"));
      val faint: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('krookodile', 'faint', q.curve('one'))");
      val blink: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "krookodile", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val look: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "krookodile", "look_quirk", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krookodile", "sleep", null, 4, null)},
            null,
            null,
            214,
            null
         )
      );
      val var24: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var25: java.util.Set = var24;
      val var28: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var25, var28),
            <unrepresentable>.INSTANCE,
            0,
            MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)}),
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krookodile", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, look},
            168,
            null
         )
      );
      var var11: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var16: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var17: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var var22: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krookodile", "ground_walk", null, 4, null)};
      val var26: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var26, "walk", var11, null, 0, var17, null, var22, null, var16, 172, null));
      var11 = PoseType.Companion.getSTATIONARY_POSES();
      var16 = new ModelQuirk[]{blink, look};
      val var19: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("faint", faint)});
      var22 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krookodile", "battle_idle", null, 4, null)};
      val var27: PoseableEntityModel = this;
      this.setBattle_idle(
         PoseableEntityModel.registerPose$default(var27, "battle_idle", var11, <unrepresentable>.INSTANCE, 0, var19, null, var22, null, var16, 168, null)
      );
   }
}
