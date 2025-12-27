package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class WailmerModel(root: ModelPart) : PokemonPoseableModel {
   public final val finLeft: ModelPart
   public final val finRight: ModelPart
   public final val jaw: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "wailmer");
      this.finLeft = this.getPart("fin_left");
      this.finRight = this.getPart("fin_right");
      this.jaw = this.getPart("jaw");
      this.portraitScale = 1.2F;
      this.portraitTranslation = new Vec3(-0.15, -0.3, 0.0);
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(0.0, 0.3, 0.0);
   }

   public override fun registerPoses() {
      var var10001: PoseableEntityModel = this;
      var var10003: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var4: java.util.Set = var10003;
      val var10004: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            var10001,
            "standing",
            SetsKt.plus(var4, var10004),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               this.rotation(this.finLeft, WaveFunctionKt.sineFunction$default(0.25F, 4.0F, 0.0F, 0.0F, 12, null), 2, <unrepresentable>.INSTANCE),
               this.rotation(this.finRight, WaveFunctionKt.sineFunction$default(-0.25F, 4.0F, 0.0F, 0.0F, 12, null), 2, <unrepresentable>.INSTANCE),
               this.rotation(this.jaw, WaveFunctionKt.sineFunction$default(0.05F, 8.0F, 0.0F, 0.04F, 4, null), 0, <unrepresentable>.INSTANCE)
            },
            null,
            null,
            436,
            null
         )
      );
      var10001 = this;
      var10003 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            var10001,
            "walk",
            var10003,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               this.rotation(this.finLeft, WaveFunctionKt.sineFunction$default(0.33333334F, 3.0F, 0.0F, 0.0F, 12, null), 2, <unrepresentable>.INSTANCE),
               this.rotation(this.finRight, WaveFunctionKt.sineFunction$default(-0.33333334F, 3.0F, 0.0F, 0.0F, 12, null), 2, <unrepresentable>.INSTANCE),
               this.rotation(this.jaw, WaveFunctionKt.sineFunction$default(0.05F, 8.0F, 0.0F, 0.04F, 4, null), 0, <unrepresentable>.INSTANCE)
            },
            null,
            null,
            436,
            null
         )
      );
   }
}
