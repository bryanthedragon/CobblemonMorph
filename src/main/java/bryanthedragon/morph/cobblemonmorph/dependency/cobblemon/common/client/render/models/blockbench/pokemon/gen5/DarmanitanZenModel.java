package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5

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

public class DarmanitanZenModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleIdle: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "darmanitan");
      this.portraitScale = 1.05F;
      this.portraitTranslation = new Vec3(-0.28, 0.48, 0.0);
      this.profileScale = 0.63F;
      this.profileTranslation = new Vec3(0.0, 0.69, 0.0);
   }

   public override fun registerPoses() {
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleeping",
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "darmanitan_zen", "sleep", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      var var5: PoseableEntityModel = this;
      var var8: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var9: java.util.Set = var8;
      val var10004: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            var5,
            "standing",
            SetsKt.plus(var9, var10004),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "darmanitan_zen", "ground_idle", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      var5 = this;
      var8 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            var5,
            "walk",
            var8,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "darmanitan_zen", "ground_walk", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      var5 = this;
      var8 = PoseType.Companion.getSTATIONARY_POSES();
      this.setBattleIdle(
         PoseableEntityModel.registerPose$default(
            var5,
            "battle_idle",
            var8,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "darmanitan_zen", "battle_idle", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
   }
}
