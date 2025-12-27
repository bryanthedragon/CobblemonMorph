package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.phys.Vec3

public class BoldoreModel(root: ModelPart) : PokemonPoseableModel, BipedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "boldore");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.portraitScale = 2.1F;
      this.portraitTranslation = new Vec3(-0.2, -1.1, 0.0);
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(0.0, 0.45, 0.0);
      this.cryAnimation = BoldoreModel::cryAnimation$lambda$0;
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "boldore", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      val var8: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var9: java.util.Set = var8;
      val var12: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var9, var12),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "boldore", "ground_idle", null, 4, null)},
            null,
            null,
            432,
            null
         )
      );
      val var10: PoseableEntityModel = this;
      val var10003: EnumSet = PoseType.Companion.getMOVING_POSES();
      this.setWalking(
         PoseableEntityModel.registerPose$default(
            var10,
            "walking",
            var10003,
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "boldore", "ground_walk", null, 4, null)},
            null,
            null,
            436,
            null
         )
      );
      val var6: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var7: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "boldore", "battle_idle", null, 4, null)};
      val var11: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var11, "battle_idle", var6, <unrepresentable>.INSTANCE, 10, null, null, var7, null, null, 432, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalking(), this.getBattleidle(), this.getSleep()))
         PoseableEntityModel.bedrockStateful$default(this, "boldore", "faint", null, 4, null)
         else
         null;
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: BoldoreModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "boldore", "cry", null, 4, null);
   }
}
