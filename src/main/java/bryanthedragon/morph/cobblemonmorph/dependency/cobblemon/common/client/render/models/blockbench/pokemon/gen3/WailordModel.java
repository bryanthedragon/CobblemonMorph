package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
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
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class WailordModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var floating: Pose<PokemonEntity, ModelFrame>
   public final val offsetY: Double
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swimming: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "wailord");
      this.portraitScale = 0.45F;
      this.portraitTranslation = new Vec3(-0.38, 0.8, 6.69);
      this.profileScale = 0.25F;
      this.profileTranslation = new Vec3(0.0, 1.2, -10.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "wailord", "blink", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wailord", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      var var22: EnumSet = PoseType.Companion.getSTANDING_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.minus(var22, PoseType.FLOAT),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wailord", "ground_idle", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0.0, this.offsetY, 0.0)},
            new ModelQuirk[]{blink},
            56,
            null
         )
      );
      var22 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walking",
            SetsKt.minus(var22, PoseType.SWIM),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               PoseableEntityModel.bedrock$default(this, "wailord", "ground_idle", null, 4, null),
               PoseableEntityModel.bedrock$default(this, "wailord", "ground_walk", null, 4, null)
            },
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0.0, this.offsetY, 0.0)},
            new ModelQuirk[]{blink},
            60,
            null
         )
      );
      var22 = PoseType.Companion.getUI_POSES();
      this.setFloating(
         PoseableEntityModel.registerPose$default(
            this,
            "floating",
            SetsKt.plus(var22, PoseType.FLOAT),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wailord", "water_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setSwimming(
         PoseableEntityModel.registerPose$default(
            this,
            "swimming",
            PoseType.SWIM,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wailord", "water_swim", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      val var11: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var15: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var19: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wailord", "battle_idle", null, 4, null)};
      val var21: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0.0, this.offsetY, 0.0)
      };
      val var25: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var25, "battle_idle", var11, <unrepresentable>.INSTANCE, 10, null, null, var19, var21, var15, 48, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getSleep(), this.getBattleidle()))
         PoseableEntityModel.bedrockStateful$default(this, "wailord", "faint", null, 4, null)
         else
         (
            if (state.isPosedIn(this.getFloating(), this.getSwimming()))
               PoseableEntityModel.bedrockStateful$default(this, "wailord", "faint_water", null, 4, null)
               else
               null
         );
   }
}
