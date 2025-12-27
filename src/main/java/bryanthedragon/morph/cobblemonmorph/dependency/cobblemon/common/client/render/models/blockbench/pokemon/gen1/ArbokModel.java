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

public class ArbokModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var summary: Pose<PokemonEntity, ModelFrame>
   public final val tail: ModelPart
   public final val tail2: ModelPart
   public final val tail2WaveSegment: WaveSegment
   public final val tail3: ModelPart
   public final val tail3WaveSegment: WaveSegment
   public final val tail4: ModelPart
   public final val tail4WaveSegment: WaveSegment
   public final val tail5: ModelPart
   public final val tail5WaveSegment: WaveSegment
   public final val tailWaveSegment: WaveSegment
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "arbok");
      this.head = this.getPart("head_ai");
      this.portraitScale = 1.5F;
      this.portraitTranslation = new Vec3(-0.4, 1.0, 0.0);
      this.profileScale = 0.67F;
      this.profileTranslation = new Vec3(0.0, 0.7, 0.0);
      this.tail = this.getPart("tail");
      this.tail2 = this.getPart("tail2");
      this.tail3 = this.getPart("tail3");
      this.tail4 = this.getPart("tail4");
      this.tail5 = this.getPart("tail5");
      this.tailWaveSegment = new WaveSegment(this.tail, 11.0F);
      this.tail2WaveSegment = new WaveSegment(this.tail2, 11.0F);
      this.tail3WaveSegment = new WaveSegment(this.tail3, 11.0F);
      this.tail4WaveSegment = new WaveSegment(this.tail4, 11.0F);
      this.tail5WaveSegment = new WaveSegment(this.tail5, 11.0F);
      this.cryAnimation = ArbokModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "arbok", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val wave: WaveAnimation = new WaveAnimation(
         this,
         WaveFunctionKt.sineFunction$default(0.5F, 10.0F, 0.0F, 0.0F, 12, null),
         8.0F,
         this.tail,
         0.1F,
         false,
         1,
         0,
         true,
         new WaveSegment[]{this.tailWaveSegment, this.tail2WaveSegment, this.tail3WaveSegment, this.tail4WaveSegment, this.tail5WaveSegment},
         32,
         null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arbok", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      var var8: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var5: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var13: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "arbok", "summary_idle", null, 4, null),
         wave
      };
      var var16: PoseableEntityModel = this;
      this.setStanding(PoseableEntityModel.registerPose$default(var16, "standing", var8, null, 10, null, null, var13, null, var5, 180, null));
      var8 = PoseType.Companion.getMOVING_POSES();
      var5 = new ModelQuirk[]{blink};
      var13 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "arbok", "ground_walk", null, 4, null),
         wave
      };
      var16 = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var16, "walk", var8, null, 10, null, null, var13, null, var5, 180, null));
      var8 = PoseType.Companion.getUI_POSES();
      var5 = new ModelQuirk[]{blink};
      var13 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "arbok", "summary_idle", null, 4, null)
      };
      var16 = this;
      this.setSummary(PoseableEntityModel.registerPose$default(var16, "summary", var8, null, 0, null, null, var13, null, var5, 188, null));
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getSleep()))
         PoseableEntityModel.bedrockStateful$default(this, "arbok", "faint", null, 4, null)
         else
         null;
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
   fun `cryAnimation$lambda$0`(`this$0`: ArbokModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "arbok", "cry", null, 4, null);
   }
}
