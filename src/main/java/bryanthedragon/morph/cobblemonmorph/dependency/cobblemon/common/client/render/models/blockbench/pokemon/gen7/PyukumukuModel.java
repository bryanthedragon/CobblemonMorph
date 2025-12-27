package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nPyukumukuModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PyukumukuModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/PyukumukuModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,45:1\n26#2:46\n*S KotlinDebug\n*F\n+ 1 PyukumukuModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/PyukumukuModel\n*L\n37#1:46\n*E\n"])
public class PyukumukuModel(root: ModelPart) : PokemonPoseableModel {
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "pyukumuku");
      this.portraitScale = 1.65F;
      this.portraitTranslation = new Vec3(-0.1, -0.8, 0.0);
      this.profileScale = 1.0F;
      this.profileTranslation = new Vec3(0.0, 0.2, 0.0);
   }

   public override fun registerPoses() {
      val var10001: PoseableEntityModel = this;
      val var10003: EnumSet = PoseType.Companion.getALL_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(var10001, "standing", var10003, null, 0, null, null, new StatelessAnimation[0], null, null, 444, null)
      );
   }
}
