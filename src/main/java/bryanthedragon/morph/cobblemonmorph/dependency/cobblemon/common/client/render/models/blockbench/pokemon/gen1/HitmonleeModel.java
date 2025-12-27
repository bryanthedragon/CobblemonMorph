package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
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

public class HitmonleeModel(root: ModelPart) : PokemonPoseableModel, BipedFrame, BimanualFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val leftArm: ModelPart
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightArm: ModelPart
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "hitmonlee");
      this.leftArm = this.getPart("arm_left");
      this.rightArm = this.getPart("arm_right");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.portraitScale = 2.2F;
      this.portraitTranslation = new Vec3(-0.2, 0.3, 0.0);
      this.profileScale = 0.9F;
      this.profileTranslation = new Vec3(0.0, 0.47, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "hitmonlee", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hitmonlee", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      val var13: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var14: java.util.Set = var13;
      val var17: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var14, var17),
            <unrepresentable>.INSTANCE,
            5,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hitmonlee", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      var var7: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var9: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var11: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hitmonlee", "ground_walk", null, 4, null)};
      val var15: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var15, "walk", var7, null, 10, null, null, var11, null, var9, 180, null));
      var7 = PoseType.Companion.getSTATIONARY_POSES();
      var9 = new ModelQuirk[]{blink};
      var11 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hitmonlee", "battle_idle", null, 4, null)};
      val var16: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var16, "battle_idle", var7, <unrepresentable>.INSTANCE, 10, null, null, var11, null, var9, 176, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity> {
      return PoseableEntityModel.bedrockStateful$default(this, "hitmonlee", "faint", null, 4, null);
   }
}
