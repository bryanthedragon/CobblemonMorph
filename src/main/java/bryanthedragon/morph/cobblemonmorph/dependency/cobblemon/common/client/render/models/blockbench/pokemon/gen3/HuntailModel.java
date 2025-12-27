package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveSegment
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class HuntailModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var floating: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var swimming: Pose<PokemonEntity, ModelFrame>
   private final val tail: ModelPart
   private final val tail2: ModelPart
   public final val tail2Segment: WaveSegment
   private final val tail3: ModelPart
   public final val tail3Segment: WaveSegment
   private final val tail4: ModelPart
   public final val tail4Segment: WaveSegment
   private final val tail5: ModelPart
   public final val tail5Segment: WaveSegment
   private final val tail6: ModelPart
   public final val tail6Segment: WaveSegment
   public final val tailSegment: WaveSegment

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "huntail");
      this.head = this.getPart("head");
      this.portraitScale = 2.6F;
      this.portraitTranslation = new Vec3(-1.3, -2.5, 0.0);
      this.profileScale = 0.9F;
      this.profileTranslation = new Vec3(0.0, 0.0, 0.0);
      this.tail = this.getPart("tail");
      this.tail2 = this.getPart("tail2");
      this.tail3 = this.getPart("tail3");
      this.tail4 = this.getPart("tail4");
      this.tail5 = this.getPart("tail5");
      this.tail6 = this.getPart("tail6");
      this.tailSegment = new WaveSegment(this.tail, 5.0F);
      this.tail2Segment = new WaveSegment(this.tail2, 5.0F);
      this.tail3Segment = new WaveSegment(this.tail3, 5.0F);
      this.tail4Segment = new WaveSegment(this.tail4, 5.0F);
      this.tail5Segment = new WaveSegment(this.tail5, 5.0F);
      this.tail6Segment = new WaveSegment(this.tail6, 5.0F);
   }

   public override fun registerPoses() {
      var var10001: PoseableEntityModel = this;
      var var10003: EnumSet = PoseType.Companion.getSTANDING_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            var10001,
            "standing",
            var10003,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "huntail", "ground_idle", null, 4, null),
               new WaveAnimation(
                  this,
                  WaveFunctionKt.sineFunction$default(0.8F, 8.0F, 0.0F, 0.0F, 12, null),
                  5.0F,
                  this.getHead(),
                  16.0F,
                  false,
                  1,
                  0,
                  true,
                  new WaveSegment[]{this.tailSegment, this.tail2Segment, this.tail3Segment, this.tail4Segment, this.tail5Segment, this.tail6Segment}
               )
            },
            null,
            null,
            444,
            null
         )
      );
      var10001 = this;
      var10003 = PoseType.Companion.getUI_POSES();
      this.setFloating(
         PoseableEntityModel.registerPose$default(
            var10001,
            "floating",
            SetsKt.plus(var10003, PoseType.FLOAT),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "huntail", "water_idle", null, 4, null)
            },
            null,
            null,
            444,
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
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "huntail", "water_swim", null, 4, null)
            },
            null,
            null,
            444,
            null
         )
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
