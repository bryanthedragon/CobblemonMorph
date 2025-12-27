package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class StaryuModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var float: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "staryu");
      this.portraitScale = 2.2F;
      this.portraitTranslation = new Vec3(-0.1, -1.25, 0.0);
      this.profileScale = 1.2F;
      this.profileTranslation = new Vec3(0.0, 0.0, 0.0);
   }

   public override fun registerPoses() {
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            PoseType.STAND,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "staryu", "ground_idle", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            PoseType.WALK,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "staryu", "ground_walk", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      val var7: PoseableEntityModel = this;
      val var11: EnumSet = PoseType.Companion.getUI_POSES();
      this.setFloat(
         PoseableEntityModel.registerPose$default(
            var7,
            "float",
            SetsKt.plus(var11, PoseType.FLOAT),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "staryu", "water_idle", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      this.setSwim(
         PoseableEntityModel.registerPose$default(
            this,
            "swim",
            PoseType.SWIM,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "staryu", "water_swim", null, 4, null)},
            null,
            null,
            444,
            null
         )
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "staryu", "sleep", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
   }
}
