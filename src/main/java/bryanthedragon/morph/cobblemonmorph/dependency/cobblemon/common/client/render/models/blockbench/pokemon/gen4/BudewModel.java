package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class BudewModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleIdle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "budew");
      this.portraitScale = 2.57F;
      this.portraitTranslation = new Vec3(-0.24, -1.84, 0.0);
      this.profileScale = 0.71F;
      this.profileTranslation = new Vec3(0.0, 0.71, 0.0);
      this.cryAnimation = BudewModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "budew", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val quirk: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "budew", "quirk_idle", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "budew", "sleep", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      val var14: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var15: java.util.Set = var14;
      var var10002: EnumSet = PoseType.Companion.getUI_POSES();
      val var16: java.util.Set = SetsKt.plus(var15, var10002);
      var10002 = PoseType.Companion.getSHOULDER_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var16, var10002),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "budew", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, quirk},
            184,
            null
         )
      );
      var var8: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var10: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var12: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "budew", "ground_walk", null, 4, null)};
      val var17: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var17, "walk", var8, null, 0, null, null, var12, null, var10, 188, null));
      var8 = PoseType.Companion.getSTATIONARY_POSES();
      var10 = new ModelQuirk[]{blink};
      var12 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "budew", "battle_idle", null, 4, null)};
      val var18: PoseableEntityModel = this;
      this.setBattleIdle(
         PoseableEntityModel.registerPose$default(var18, "battle_idle", var8, <unrepresentable>.INSTANCE, 0, null, null, var12, null, var10, 184, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getSleep(), this.getBattleIdle()))
         PoseableEntityModel.bedrockStateful$default(this, "budew", "faint", null, 4, null)
         else
         null;
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: BudewModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "budew", "cry", null, 4, null);
   }
}
