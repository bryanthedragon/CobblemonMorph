package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
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

public class ChinchouModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var floating: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swimming: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var waterbattleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var watersleep: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "chinchou");
      this.portraitScale = 1.75F;
      this.portraitTranslation = new Vec3(-0.3, -0.8, 0.0);
      this.profileScale = 0.65F;
      this.profileTranslation = new Vec3(-0.05, 0.45, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "chinchou", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val idleQuirk: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(60.0F, 120.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "chinchou", "ground_quirk", null, 4, null);
            }
         }) as Function1, 6, null
      );
      val waterQuirk: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(60.0F, 120.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "chinchou", "water_idle_quirk", null, 4, null);
            }
         }) as Function1, 6, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleeping",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      this.setWatersleep(
         PoseableEntityModel.registerPose$default(
            this,
            "water_sleeping",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "water_sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      val var26: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var26, PoseType.STAND),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, idleQuirk},
            176,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.WALK,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "ground_walk", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            176,
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
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "water_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, waterQuirk},
            176,
            null
         )
      );
      var var12: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var17: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var22: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "water_swim", null, 4, null)};
      val var27: PoseableEntityModel = this;
      this.setSwimming(
         PoseableEntityModel.registerPose$default(var27, "swimming", var12, <unrepresentable>.INSTANCE, 10, null, null, var22, null, var17, 176, null)
      );
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var17 = new ModelQuirk[]{blink};
      var22 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "battle_idle", null, 4, null)};
      val var28: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var28, "battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var22, null, var17, 176, null)
      );
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var17 = new ModelQuirk[]{blink};
      var22 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "chinchou", "water_battle_idle", null, 4, null)};
      val var29: PoseableEntityModel = this;
      this.setWaterbattleidle(
         PoseableEntityModel.registerPose$default(var29, "water_battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var22, null, var17, 176, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getBattleidle(), this.getSleep()))
         PoseableEntityModel.bedrockStateful$default(this, "chinchou", "faint", null, 4, null)
         else
         (
            if (state.isPosedIn(this.getWaterbattleidle(), this.getWatersleep(), this.getFloating(), this.getSwimming()))
               PoseableEntityModel.bedrockStateful$default(this, "chinchou", "water_faint", null, 4, null)
               else
               null
         );
   }
}
