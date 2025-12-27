package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveSegment
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.EnumSet
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class GyaradosModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final val seg1: ModelPart
   public final val seg10: ModelPart
   public final val seg11: ModelPart
   public final val seg12: ModelPart
   public final val seg2: ModelPart
   public final val seg3: ModelPart
   public final val seg4: ModelPart
   public final val seg5: ModelPart
   public final val seg6: ModelPart
   public final val seg7: ModelPart
   public final val seg8: ModelPart
   public final val seg9: ModelPart
   public final val wseg1: WaveSegment
   public final val wseg10: WaveSegment
   public final val wseg11: WaveSegment
   public final val wseg12: WaveSegment
   public final val wseg2: WaveSegment
   public final val wseg3: WaveSegment
   public final val wseg4: WaveSegment
   public final val wseg5: WaveSegment
   public final val wseg6: WaveSegment
   public final val wseg7: WaveSegment
   public final val wseg8: WaveSegment
   public final val wseg9: WaveSegment

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "gyarados");
      this.seg1 = this.getPart("segment1");
      this.seg2 = this.getPart("segment2");
      this.seg3 = this.getPart("segment3");
      this.seg4 = this.getPart("segment4");
      this.seg5 = this.getPart("segment5");
      this.seg6 = this.getPart("segment6");
      this.seg7 = this.getPart("segment7");
      this.seg8 = this.getPart("segment8");
      this.seg9 = this.getPart("segment9");
      this.seg10 = this.getPart("segment10");
      this.seg11 = this.getPart("segment11");
      this.seg12 = this.getPart("segment12");
      this.head = this.getPart("head");
      this.wseg1 = new WaveSegment(this.seg1, 7.0F);
      this.wseg2 = new WaveSegment(this.seg2, 5.0F);
      this.wseg3 = new WaveSegment(this.seg3, 6.0F);
      this.wseg4 = new WaveSegment(this.seg4, 6.0F);
      this.wseg5 = new WaveSegment(this.seg5, 6.0F);
      this.wseg6 = new WaveSegment(this.seg6, 6.0F);
      this.wseg7 = new WaveSegment(this.seg7, 6.0F);
      this.wseg8 = new WaveSegment(this.seg8, 6.0F);
      this.wseg9 = new WaveSegment(this.seg9, 6.0F);
      this.wseg10 = new WaveSegment(this.seg10, 5.0F);
      this.wseg11 = new WaveSegment(this.seg11, 5.0F);
      this.wseg12 = new WaveSegment(this.seg12, 4.0F);
      this.portraitScale = 1.8F;
      this.portraitTranslation = new Vec3(-1.55, 0.35, 0.0);
      this.profileScale = 0.7F;
      this.profileTranslation = new Vec3(-0.1, 0.65, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "gyarados", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      var var10000: EnumSet = PoseType.Companion.getSTANDING_POSES();
      val var23: java.util.Set = var10000;
      var var10001: EnumSet = PoseType.Companion.getUI_POSES();
      PoseableEntityModel.registerPose$default(
         this,
         "land",
         SetsKt.plus(var23, var10001),
         <unrepresentable>.INSTANCE,
         20,
         null,
         null,
         new StatelessAnimation[]{
            HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
            PoseableEntityModel.bedrock$default(this, "gyarados", "ground_idle", null, 4, null),
            new WaveAnimation(
               this,
               WaveFunctionKt.sineFunction$default(0.4F, 8.0F, 0.0F, 0.0F, 12, null),
               8.0F,
               this.seg5,
               0.1F,
               false,
               1,
               0,
               true,
               new WaveSegment[]{this.wseg6, this.wseg7, this.wseg8, this.wseg9, this.wseg10, this.wseg11, this.wseg12},
               32,
               null
            )
         },
         null,
         new ModelQuirk[]{blink},
         176,
         null
      );
      PoseableEntityModel.registerPose$default(
         this,
         "surface",
         SetsKt.setOf(new PoseType[]{PoseType.STAND, PoseType.WALK}),
         <unrepresentable>.INSTANCE,
         20,
         null,
         null,
         new StatelessAnimation[]{
            HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
            PoseableEntityModel.bedrock$default(this, "gyarados", "surface_idle", null, 4, null),
            new WaveAnimation(
               this,
               WaveFunctionKt.sineFunction$default(0.2F, 3.0F, 0.0F, 0.0F, 12, null),
               24.0F,
               this.seg6,
               0.0F,
               false,
               0,
               1,
               false,
               new WaveSegment[]{this.wseg7, this.wseg8, this.wseg9, this.wseg10, this.wseg11, this.wseg12}
            )
         },
         new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -6.0F)},
         new ModelQuirk[]{blink},
         48,
         null
      );
      var10000 = PoseType.Companion.getSWIMMING_POSES();
      val var25: java.util.Set = var10000;
      var10001 = PoseType.Companion.getFLYING_POSES();
      PoseableEntityModel.registerPose$default(
         this,
         "swim",
         SetsKt.plus(var25, var10001),
         null,
         20,
         null,
         null,
         new StatelessAnimation[]{
            HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
            new WaveAnimation(
               this,
               WaveFunctionKt.sineFunction$default(0.4F, 3.0F, 0.0F, 0.0F, 12, null),
               24.0F,
               this.getRootPart(),
               4.0F,
               true,
               0,
               1,
               false,
               new WaveSegment[]{
                  this.wseg1,
                  this.wseg2,
                  this.wseg3,
                  this.wseg4,
                  this.wseg5,
                  this.wseg6,
                  this.wseg7,
                  this.wseg8,
                  this.wseg9,
                  this.wseg10,
                  this.wseg11,
                  this.wseg12
               },
               256,
               null
            )
         },
         null,
         new ModelQuirk[]{blink},
         180,
         null
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
