package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
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

public class MetagrossModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public final lateinit var fly: Pose<PokemonEntity, ModelFrame>
   public final lateinit var hover: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "metagross");
      this.portraitScale = 1.1F;
      this.portraitTranslation = new Vec3(-0.45, 0.5, 0.0);
      this.profileScale = 0.4F;
      this.profileTranslation = new Vec3(0.0, 1.0, 0.0);
      this.cryAnimation = MetagrossModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "metagross", "blink", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "metagross", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      var var20: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var21: java.util.Set = SetsKt.minus(SetsKt.minus(var20, PoseType.HOVER), PoseType.FLOAT);
      val var24: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "stand",
            SetsKt.plus(var21, var24),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "metagross", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      this.setHover(
         PoseableEntityModel.registerPose$default(
            this,
            "hover",
            SetsKt.setOf(new PoseType[]{PoseType.HOVER, PoseType.FLOAT}),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "metagross", "air_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setFly(
         PoseableEntityModel.registerPose$default(
            this,
            "fly",
            SetsKt.setOf(new PoseType[]{PoseType.FLY, PoseType.SWIM}),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "metagross", "air_fly", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      var20 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walk",
            SetsKt.minus(SetsKt.minus(var20, PoseType.FLY), PoseType.SWIM),
            null,
            5,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "metagross", "ground_walk", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            180,
            null
         )
      );
      val var10: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var15: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var19: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "metagross", "battle_idle", null, 4, null)};
      val var23: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var23, "battle_idle", var10, <unrepresentable>.INSTANCE, 10, null, null, var19, null, var15, 176, null)
      );
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: MetagrossModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "metagross", "cry", null, 4, null);
   }
}
