package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class RibombeeModel(root: ModelPart) : PokemonPoseableModel {
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "ribombee");
      this.portraitScale = 1.8F;
      this.portraitTranslation = new Vec3(0.0, 0.95, 0.0);
      this.profileScale = 0.5F;
      this.profileTranslation = new Vec3(0.1, 0.9, 0.0);
   }

   public override fun registerPoses() {
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "ribombee", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      var var4: PoseableEntityModel = this;
      var var10003: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var6: java.util.Set = var10003;
      val var10004: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            var4,
            "idle",
            SetsKt.plus(var6, var10004),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "ribombee", "ground_idle", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      var4 = this;
      var10003 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            var4,
            "walk",
            var10003,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "ribombee", "ground_walk", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getSleep()))
         PoseableEntityModel.bedrockStateful$default(this, "ribombee", "faint", null, 4, null)
         else
         null;
   }
}
