package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

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

public class DusclopsModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "dusclops");
      this.portraitScale = 1.6F;
      this.portraitTranslation = new Vec3(-0.2, 1.1, 0.0);
      this.profileScale = 0.6F;
      this.profileTranslation = new Vec3(0.0, 0.8, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dusclops", "blink", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dusclops", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            PoseType.STAND,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dusclops", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walking",
            PoseType.WALK,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dusclops", "ground_walk", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      val var19: EnumSet = PoseType.Companion.getUI_POSES();
      this.setHover(
         PoseableEntityModel.registerPose$default(
            this,
            "hover",
            SetsKt.plus(SetsKt.plus(var19, PoseType.HOVER), PoseType.FLOAT),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dusclops", "air_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      this.setFly(
         PoseableEntityModel.registerPose$default(
            this,
            "fly",
            SetsKt.setOf(new PoseType[]{PoseType.FLY, PoseType.SWIM, PoseType.WALK}),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dusclops", "air_fly", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      val var10: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var14: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var18: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dusclops", "battle_idle", null, 4, null)};
      val var20: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var20, "battle_idle", var10, <unrepresentable>.INSTANCE, 10, null, null, var18, null, var14, 176, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getHover(), this.getFly(), this.getSleep(), this.getStanding(), this.getWalk(), this.getBattleidle()))
         PoseableEntityModel.bedrockStateful$default(this, "dusclops", "faint", null, 4, null)
         else
         null;
   }
}
