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

public class BarboachModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public final lateinit var floating: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swimming: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var watersleep: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "barboach");
      this.portraitScale = 2.0F;
      this.portraitTranslation = new Vec3(-0.55, -1.2, 0.0);
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(0.0, 0.4, 0.0);
      this.cryAnimation = BarboachModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "barboach", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setWatersleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "barboach", "water_sleep", null, 4, null)},
            null,
            null,
            220,
            null
         )
      );
      var var19: EnumSet = PoseType.Companion.getSTANDING_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.minus(var19, PoseType.FLOAT),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "barboach", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            184,
            null
         )
      );
      var19 = PoseType.Companion.getMOVING_POSES();
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walking",
            SetsKt.minus(var19, PoseType.SWIM),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "barboach", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      var19 = PoseType.Companion.getUI_POSES();
      this.setFloating(
         PoseableEntityModel.registerPose$default(
            this,
            "floating",
            SetsKt.plus(var19, PoseType.FLOAT),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "barboach", "water_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      this.setSwimming(
         PoseableEntityModel.registerPose$default(
            this,
            "swimming",
            PoseType.SWIM,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "barboach", "water_swim", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            188,
            null
         )
      );
      val var10: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var14: Array<ModelQuirk> = new ModelQuirk[]{blink};
      val var18: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "barboach", "battle_idle", null, 4, null)};
      val var22: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var22, "battle_idle", var10, <unrepresentable>.INSTANCE, 10, null, null, var18, null, var14, 176, null)
      );
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: BarboachModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "barboach", "cry", null, 4, null);
   }
}
