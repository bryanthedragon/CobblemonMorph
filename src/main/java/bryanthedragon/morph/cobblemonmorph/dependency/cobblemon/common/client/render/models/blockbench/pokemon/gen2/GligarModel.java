package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
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

public class GligarModel(root: ModelPart) : PokemonPoseableModel, BipedFrame {
   public final lateinit var battling: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "gligar");
      this.portraitScale = 2.4F;
      this.portraitTranslation = new Vec3(-0.1, -0.8, 0.0);
      this.leftLeg = this.getPart("left_upper_leg");
      this.rightLeg = this.getPart("right_upper_leg");
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(0.0, 0.6, 0.0);
      this.cryAnimation = GligarModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "gligar", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var12: java.util.Set = var10001;
      var var10002: EnumSet = PoseType.Companion.getUI_POSES();
      val var13: java.util.Set = SetsKt.plus(var12, var10002);
      var10002 = PoseType.Companion.getFLYING_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var13, var10002),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "gligar", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      var var6: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var8: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var10: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "gligar", "battle_idle", null, 4, null)};
      val var14: PoseableEntityModel = this;
      this.setBattling(
         PoseableEntityModel.registerPose$default(var14, "battling", var6, <unrepresentable>.INSTANCE, 10, null, null, var10, null, var8, 176, null)
      );
      var6 = PoseType.Companion.getMOVING_POSES();
      var8 = new ModelQuirk[]{blink};
      var10 = new StatelessAnimation[]{
         PoseableEntityModel.bedrock$default(this, "gligar", "ground_idle", null, 4, null), new BipedWalkAnimation(this, 1.0F, 0.6F)
      };
      val var15: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var15, "walk", var6, null, 10, null, null, var10, null, var8, 180, null));
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: GligarModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "gligar", "cry", null, 4, null);
   }
}
