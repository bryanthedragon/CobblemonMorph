package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
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
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class ArmaldoModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BimanualFrame, BipedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
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
      this.rootPart = this.registerChildWithAllChildren(root, "armaldo");
      this.head = this.getPart("head");
      this.leftArm = this.getPart("arm1_left");
      this.rightArm = this.getPart("arm1_right");
      this.leftLeg = this.getPart("left_leg");
      this.rightLeg = this.getPart("right_leg");
      this.portraitTranslation = new Vec3(-0.46, 1.5, 0.0);
      this.portraitScale = 1.73F;
      this.profileTranslation = new Vec3(0.0, 0.77, 0.0);
      this.profileScale = 0.63F;
      this.wateroffset = -4.5;
      this.watersurfaceoffset = 10;
      this.cryAnimation = ArmaldoModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "armaldo", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val look: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "armaldo", "look_quirk", null, 4, null);
            }
         }) as Function1, 7, null
      );
      var var3: EnumSet = PoseType.Companion.getUI_POSES();
      var var5: Array<ModelQuirk> = new ModelQuirk[]{blink, look};
      var var6: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "ground_idle", null, 4, null)};
      var var10001: PoseableEntityModel = this;
      this.setUi_poses(PoseableEntityModel.registerPose$default(var10001, "ui_poses", var3, null, 0, null, null, var6, null, var5, 188, null));
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "sleep", null, 4, null)},
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_sleep", null, 4, null)},
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_sleep", null, 4, null)},
            new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)},
            null,
            312,
            null
         )
      );
      var3 = PoseType.Companion.getSTATIONARY_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "ground_idle", null, 4, null)};
      var10001 = this;
      this.setStanding(
         PoseableEntityModel.registerPose$default(var10001, "standing", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, null, var5, 176, null)
      );
      var3 = PoseType.Companion.getSTATIONARY_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_idle", null, 4, null)};
      var var7: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)
      };
      var10001 = this;
      this.setWater_surface_idle(
         PoseableEntityModel.registerPose$default(var10001, "water_surface_idle", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, var7, var5, 48, null)
      );
      var3 = PoseType.Companion.getSTATIONARY_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_idle", null, 4, null)};
      var7 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      var10001 = this;
      this.setWater_idle(
         PoseableEntityModel.registerPose$default(var10001, "water_idle", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, var7, var5, 48, null)
      );
      var3 = PoseType.Companion.getSTATIONARY_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "battle_idle", null, 4, null)};
      var10001 = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var10001, "battle_idle", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, null, var5, 176, null)
      );
      var3 = PoseType.Companion.getSTATIONARY_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_battle_idle", null, 4, null)};
      var7 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
      var10001 = this;
      this.setWater_surface_battleidle(
         PoseableEntityModel.registerPose$default(var10001, "surface_battle_idle", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, var7, var5, 48, null)
      );
      var3 = PoseType.Companion.getSTATIONARY_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_battle_idle", null, 4, null)};
      var7 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      var10001 = this;
      this.setWater_battleidle(
         PoseableEntityModel.registerPose$default(var10001, "water_battle_idle", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, var7, var5, 48, null)
      );
      var3 = PoseType.Companion.getMOVING_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "ground_walk", null, 4, null)};
      var10001 = this;
      this.setWalking(
         PoseableEntityModel.registerPose$default(var10001, "walking", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, null, var5, 176, null)
      );
      var3 = PoseType.Companion.getMOVING_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_swim", null, 4, null)};
      var7 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
      var10001 = this;
      this.setWater_surface_swim(
         PoseableEntityModel.registerPose$default(var10001, "surface_swim", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, var7, var5, 48, null)
      );
      var3 = PoseType.Companion.getMOVING_POSES();
      var5 = new ModelQuirk[]{blink, look};
      var6 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_swim", null, 4, null)};
      var7 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      var10001 = this;
      this.setWater_swim(
         PoseableEntityModel.registerPose$default(var10001, "water_swim", var3, <unrepresentable>.INSTANCE, 10, null, null, var6, var7, var5, 48, null)
      );
   }

   override fun <T extends Entity> singleBoneLook(
      invertX: Boolean,
      invertY: Boolean,
      disableX: Boolean,
      disableY: Boolean,
      pitchMultiplier: java.lang.Float?,
      yawMultiplier: java.lang.Float?,
      maxPitch: java.lang.Float?,
      minPitch: java.lang.Float?,
      maxYaw: java.lang.Float?,
      minYaw: java.lang.Float?
   ): SingleBoneLookAnimation<T> {
      return HeadedFrame.DefaultImpls.singleBoneLook(
         this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw
      );
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: ArmaldoModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "armaldo", "cry", null, 4, null);
   }
}
