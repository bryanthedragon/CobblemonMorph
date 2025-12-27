package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
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

public class AnorithModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var shoulderLeft: Pose<PokemonEntity, ModelFrame>
   public final val shoulderOffset: Double
   public final lateinit var shoulderRight: Pose<PokemonEntity, ModelFrame>
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var ui_poses: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_idle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_battleidle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_idle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_surface_swim: Pose<PokemonEntity, ModelFrame>
   public final lateinit var water_swim: Pose<PokemonEntity, ModelFrame>
   public final val wateroffset: Double
   public final val watersurfaceoffset: Int

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "anorith");
      this.portraitTranslation = new Vec3(-0.14, -1.56, 0.0);
      this.portraitScale = 1.8F;
      this.profileTranslation = new Vec3(-0.05, -0.19, 0.0);
      this.profileScale = 1.03F;
      this.shoulderOffset = 5.5;
      this.wateroffset = -4.5;
      this.watersurfaceoffset = 1;
      this.cryAnimation = AnorithModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "anorith", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      var var2: EnumSet = PoseType.Companion.getUI_POSES();
      var var4: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var5: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "summary_idle", null, 4, null)};
      var var10001: PoseableEntityModel = this;
      this.setUi_poses(PoseableEntityModel.registerPose$default(var10001, "ui_poses", var2, null, 0, null, null, var5, null, var4, 188, null));
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "sleep", null, 4, null)},
            null,
            null,
            440,
            null
         )
      );
      this.setWater_surface_sleep(
         PoseableEntityModel.registerPose$default(
            this,
            "water_surface_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)},
            null,
            312,
            null
         )
      );
      this.setWater_sleep(
         PoseableEntityModel.registerPose$default(
            this,
            "water_sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)},
            null,
            312,
            null
         )
      );
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "ground_idle", null, 4, null)};
      var10001 = this;
      this.setStanding(
         PoseableEntityModel.registerPose$default(var10001, "standing", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, null, var4, 176, null)
      );
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_idle", null, 4, null)};
      var var6: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)
      };
      var10001 = this;
      this.setWater_surface_idle(
         PoseableEntityModel.registerPose$default(var10001, "water_surface_idle", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, var6, var4, 48, null)
      );
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_idle", null, 4, null)};
      var6 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      var10001 = this;
      this.setWater_idle(
         PoseableEntityModel.registerPose$default(var10001, "water_idle", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, var6, var4, 48, null)
      );
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "battle_idle", null, 4, null)};
      var10001 = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var10001, "battle_idle", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, null, var4, 176, null)
      );
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_battle_idle", null, 4, null)};
      var6 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
      var10001 = this;
      this.setWater_surface_battleidle(
         PoseableEntityModel.registerPose$default(var10001, "surface_battle_idle", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, var6, var4, 48, null)
      );
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_battle_idle", null, 4, null)};
      var6 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      var10001 = this;
      this.setWater_battleidle(
         PoseableEntityModel.registerPose$default(var10001, "water_battle_idle", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, var6, var4, 48, null)
      );
      var2 = PoseType.Companion.getMOVING_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "ground_walk", null, 4, null)};
      var10001 = this;
      this.setWalking(
         PoseableEntityModel.registerPose$default(var10001, "walking", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, null, var4, 176, null)
      );
      var2 = PoseType.Companion.getMOVING_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_swim", null, 4, null)};
      var6 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
      var10001 = this;
      this.setWater_surface_swim(
         PoseableEntityModel.registerPose$default(var10001, "surface_swim", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, var6, var4, 48, null)
      );
      var2 = PoseType.Companion.getMOVING_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_swim", null, 4, null)};
      var6 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      var10001 = this;
      this.setWater_swim(
         PoseableEntityModel.registerPose$default(var10001, "water_swim", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, var6, var4, 48, null)
      );
      this.setShoulderLeft(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SHOULDER_LEFT,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "shoulder_left", null, 4, null)},
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, this.shoulderOffset),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, 2),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(2, 2)
            },
            new ModelQuirk[]{blink},
            30,
            null
         )
      );
      this.setShoulderRight(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SHOULDER_RIGHT,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "shoulder_right", null, 4, null)},
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, -this.shoulderOffset),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, 2),
               ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(2, 2)
            },
            new ModelQuirk[]{blink},
            30,
            null
         )
      );
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: AnorithModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "anorith", "cry", null, 4, null);
   }
}
