package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveSegment
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
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

public class EkansModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   private final val body: ModelPart
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
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
      this.rootPart = this.registerChildWithAllChildren(root, "ekans");
      this.body = this.getPart("body");
      this.head = this.getPart("head");
      this.tail = this.getPart("tail");
      this.tail2 = this.getPart("tail2");
      this.tail3 = this.getPart("tail3");
      this.tail4 = this.getPart("tail4");
      this.tail5 = this.getPart("tail5");
      this.tail6 = this.getPart("tail6");
      this.tailSegment = new WaveSegment(this.tail, 9.0F);
      this.tail2Segment = new WaveSegment(this.tail2, 9.0F);
      this.tail3Segment = new WaveSegment(this.tail3, 9.0F);
      this.tail4Segment = new WaveSegment(this.tail4, 9.0F);
      this.tail5Segment = new WaveSegment(this.tail5, 10.0F);
      this.tail6Segment = new WaveSegment(this.tail6, 10.0F);
      this.portraitScale = 1.7F;
      this.portraitTranslation = new Vec3(-0.3, -0.45, 0.0);
      this.profileScale = 0.7F;
      this.profileTranslation = new Vec3(-0.05, 0.6, 0.0);
      this.cryAnimation = EkansModel::cryAnimation$lambda$0;
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "ekans", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      val var10: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "ekans", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val var10000: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var14: java.util.Set = var10000;
      val var16: EnumSet = PoseType.Companion.getMOVING_POSES();
      PoseableEntityModel.registerPose$default(
         this,
         "normal",
         SetsKt.plus(var14, var16),
         null,
         0,
         null,
         null,
         new StatelessAnimation[]{
            HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
            PoseableEntityModel.bedrock$default(this, "ekans", "ground_idle", null, 4, null),
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
         new ModelQuirk[]{var10},
         188,
         null
      );
      val var11: EnumSet = PoseType.Companion.getUI_POSES();
      val var12: Array<ModelQuirk> = new ModelQuirk[]{var10};
      val var13: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "ekans", "summary_idle", null, 4, null)};
      val var15: PoseableEntityModel = this;
      PoseableEntityModel.registerPose$default(var15, "portrait", var11, null, 0, null, null, var13, null, var12, 188, null);
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity> {
      return PoseableEntityModel.bedrockStateful$default(this, "ekans", "faint", null, 4, null);
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
   fun `cryAnimation$lambda$0`(`this$0`: EkansModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "ekans", "cry", null, 4, null);
   }
}
