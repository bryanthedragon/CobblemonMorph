package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BimanualSwingAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
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

public class SentretModel(root: ModelPart) : PokemonPoseableModel, BipedFrame, BimanualFrame {
   public open val leftArm: ModelPart
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightArm: ModelPart
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "sentret");
      this.leftArm = this.getPart("arm_left");
      this.rightArm = this.getPart("arm_right");
      this.leftLeg = this.getPart("foot_left");
      this.rightLeg = this.getPart("foot_right");
      this.portraitScale = 2.34F;
      this.portraitTranslation = new Vec3(-0.37, -0.67, 0.0);
      this.profileScale = 0.65F;
      this.profileTranslation = new Vec3(0.0, 0.76, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "sentret", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var9: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var9, var10002),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "sentret", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      val var6: EnumSet = PoseType.Companion.getMOVING_POSES();
      val var7: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var8: Array<StatelessAnimation> = new StatelessAnimation[]{
         PoseableEntityModel.bedrock$default(this, "sentret", "ground_idle", null, 4, null),
         new BipedWalkAnimation(this, 0.6F, 0.9F),
         new BimanualSwingAnimation(this, 0.6F, 0.9F)
      };
      val var10: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var10, "walk", var6, null, 0, null, null, var8, null, var7, 188, null));
   }
}
