package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
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

public class CleffaModel(root: ModelPart) : PokemonPoseableModel {
   public open val cryAnimation: CryProvider
   public final lateinit var leftShoulder: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public final lateinit var rightShoulder: Pose<PokemonEntity, ModelFrame>
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "cleffa");
      this.portraitScale = 2.0F;
      this.portraitTranslation = new Vec3(0.0, -1.3, 0.0);
      this.profileScale = 1.15F;
      this.profileTranslation = new Vec3(0.0, 0.05, 0.0);
      this.cryAnimation = CleffaModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "cleffa", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var18: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var18, var10002),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cleffa", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      this.setLeftShoulder(
         PoseableEntityModel.registerPose$default(
            this,
            "left_shoulder",
            PoseType.SHOULDER_LEFT,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cleffa", "ground_idle", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, 4.0)},
            new ModelQuirk[]{blink},
            52,
            null
         )
      );
      this.setRightShoulder(
         PoseableEntityModel.registerPose$default(
            this,
            "right_shoulder",
            PoseType.SHOULDER_RIGHT,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cleffa", "ground_idle", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, -4.0)},
            new ModelQuirk[]{blink},
            52,
            null
         )
      );
      val var12: EnumSet = PoseType.Companion.getMOVING_POSES();
      val var14: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var16: Array<StatelessAnimation> = new StatelessAnimation[]{
         PoseableEntityModel.bedrock$default(this, "cleffa", "ground_idle", null, 4, null),
         PoseableEntityModel.bedrock$default(this, "cleffa", "ground_walk", null, 4, null)
      };
      val var19: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var19, "walk", var12, null, 10, null, null, var16, null, var14, 180, null));
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk()))
         PoseableEntityModel.bedrockStateful$default(this, "cleffa", "faint", null, 4, null)
         else
         null;
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: CleffaModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "cleffa", "cry", null, 4, null);
   }
}
