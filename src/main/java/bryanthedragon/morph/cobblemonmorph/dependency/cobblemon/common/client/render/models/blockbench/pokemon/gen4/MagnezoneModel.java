package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4

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

public class MagnezoneModel(root: ModelPart) : PokemonPoseableModel {
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "magnezone");
      this.portraitScale = 1.4F;
      this.portraitTranslation = new Vec3(-0.35, -0.28, 0.0);
      this.profileScale = 0.6F;
      this.profileTranslation = new Vec3(-0.1, 0.6, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "magnezone", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10000: EnumSet = PoseType.Companion.getALL_POSES();
      PoseableEntityModel.registerPose$default(
         this,
         "hover",
         SetsKt.minus(SetsKt.minus(SetsKt.minus(var10000, PoseType.FLY), PoseType.SLEEP), PoseType.SWIM),
         null,
         10,
         null,
         null,
         new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "magnezone", "ground_idle", null, 4, null)},
         null,
         new ModelQuirk[]{blink},
         180,
         null
      );
      PoseableEntityModel.registerPose$default(
         this,
         "fly",
         SetsKt.setOf(new PoseType[]{PoseType.FLY, PoseType.SWIM}),
         null,
         10,
         null,
         null,
         new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "magnezone", "ground_idle", null, 4, null)},
         null,
         new ModelQuirk[]{blink},
         180,
         null
      );
   }
}
