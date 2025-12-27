package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen8

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
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class ArrokudaModel(root: ModelPart) : PokemonPoseableModel {
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
      this.rootPart = this.registerChildWithAllChildren(root, "arrokuda");
      this.portraitScale = 3.0F;
      this.portraitTranslation = new Vec3(-0.7, -3.2, 0.0);
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(0.0, 0.5, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "arrokuda", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val flop: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "arrokuda", "ground_quirk", null, 4, null);
            }
         }) as Function1, 7, null
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "sleep", null, 4, null)},
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "water_sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      val var23: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var23, PoseType.STAND),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, flop},
            176,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.WALK,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "ground_walk", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      this.setFloating(
         PoseableEntityModel.registerPose$default(
            this,
            "floating",
            PoseType.FLOAT,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "water_idle", null, 4, null)},
            null,
            null,
            436,
            null
         )
      );
      this.setSwimming(
         PoseableEntityModel.registerPose$default(
            this,
            "swimming",
            PoseType.SWIM,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "water_swim", null, 4, null)},
            null,
            null,
            436,
            null
         )
      );
      var var12: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var17: Array<ModelQuirk> = new ModelQuirk[]{blink, flop};
      var var20: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "battle_idle", null, 4, null)};
      val var24: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var24, "battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var20, null, var17, 176, null)
      );
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var17 = new ModelQuirk[]{blink};
      var20 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arrokuda", "water_battle_idle", null, 4, null)};
      val var25: PoseableEntityModel = this;
      this.setWaterbattleidle(
         PoseableEntityModel.registerPose$default(var25, "water_battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var20, null, var17, 176, null)
      );
   }
}
