package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
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

public class SurskitModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final val wateroffset: Int
   public final lateinit var waterstand: Pose<PokemonEntity, ModelFrame>
   public final lateinit var waterwalk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "surskit");
      this.head = this.getPart("head");
      this.portraitScale = 2.2F;
      this.portraitTranslation = new Vec3(0.0, -0.9, 0.0);
      this.profileScale = 0.7F;
      this.profileTranslation = new Vec3(0.0, 0.6, 0.0);
      this.wateroffset = -8;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "surskit", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var17: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var17, var10002),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "surskit", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      var var7: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var10: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var13: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "surskit", "ground_walk", null, 4, null)};
      val var18: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var18, "walking", var7, <unrepresentable>.INSTANCE, 10, null, null, var13, null, var10, 176, null));
      var7 = PoseType.Companion.getSTATIONARY_POSES();
      var10 = new ModelQuirk[]{blink};
      var13 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "surskit", "ground_idle", null, 4, null)};
      var var6: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)
      };
      val var19: PoseableEntityModel = this;
      this.setWaterstand(
         PoseableEntityModel.registerPose$default(var19, "water_stand", var7, <unrepresentable>.INSTANCE, 10, null, null, var13, var6, var10, 48, null)
      );
      var7 = PoseType.Companion.getMOVING_POSES();
      var10 = new ModelQuirk[]{blink};
      var13 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "surskit", "ground_walk", null, 4, null)};
      var6 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
      val var20: PoseableEntityModel = this;
      this.setWaterwalk(
         PoseableEntityModel.registerPose$default(var20, "water_walk", var7, <unrepresentable>.INSTANCE, 10, null, null, var13, var6, var10, 48, null)
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
}
