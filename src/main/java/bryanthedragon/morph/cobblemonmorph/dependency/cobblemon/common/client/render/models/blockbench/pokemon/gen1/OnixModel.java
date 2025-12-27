package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
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
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class OnixModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var ui: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "onix");
      this.head = this.getPart("head");
      this.portraitScale = 1.1F;
      this.portraitTranslation = new Vec3(-0.2, 1.4, 0.0);
      this.profileScale = 0.55F;
      this.profileTranslation = new Vec3(-0.1, 0.9, 0.0);
      this.cryAnimation = OnixModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "onix", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      var var2: EnumSet = PoseType.Companion.getUI_POSES();
      var var4: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var5: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "onix", "summary_idle", null, 4, null)};
      var var10001: PoseableEntityModel = this;
      this.setUi(PoseableEntityModel.registerPose$default(var10001, "ui", var2, null, 0, null, null, var5, null, var4, 188, null));
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{
               PoseableEntityModel.bedrock$default(this, "onix", "sleep", null, 4, null),
               PoseableEntityModel.bedrock$default(this, "onix", "slow_boulder_rotation", null, 4, null)
            },
            null,
            null,
            218,
            null
         )
      );
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "onix", "ground_idle", null, 4, null),
         PoseableEntityModel.bedrock$default(this, "onix", "boulder_rotation", null, 4, null)
      };
      var10001 = this;
      this.setStanding(
         PoseableEntityModel.registerPose$default(var10001, "standing", var2, <unrepresentable>.INSTANCE, 1, null, null, var5, null, var4, 176, null)
      );
      var2 = PoseType.Companion.getMOVING_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "onix", "ground_walk", null, 4, null),
         PoseableEntityModel.bedrock$default(this, "onix", "boulder_rotation", null, 4, null)
      };
      var10001 = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var10001, "walk", var2, null, 1, null, null, var5, null, var4, 180, null));
      var2 = PoseType.Companion.getSTATIONARY_POSES();
      var4 = new ModelQuirk[]{blink};
      var5 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "onix", "battle_idle", null, 4, null),
         PoseableEntityModel.bedrock$default(this, "onix", "boulder_rotation", null, 4, null)
      };
      var10001 = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var10001, "battle_idle", var2, <unrepresentable>.INSTANCE, 10, null, null, var5, null, var4, 176, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity> {
      return PoseableEntityModel.bedrockStateful$default(this, "onix", "faint", null, 4, null);
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
   fun `cryAnimation$lambda$0`(`this$0`: OnixModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "onix", "cry", null, 4, null);
   }
}
