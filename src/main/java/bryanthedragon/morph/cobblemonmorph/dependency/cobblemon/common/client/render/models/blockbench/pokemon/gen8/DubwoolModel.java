package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen8

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.QuadrupedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import kotlin.jvm.functions.Function1
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class DubwoolModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, QuadrupedFrame {
   public open val cryAnimation: CryProvider
   public open val foreLeftLeg: ModelPart
   public open val foreRightLeg: ModelPart
   public open val head: ModelPart
   public open val hindLeftLeg: ModelPart
   public open val hindRightLeg: ModelPart
   public final val neckWool: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var shearedsleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var shearedstanding: Pose<PokemonEntity, ModelFrame>
   public final lateinit var shearedwalk: Pose<PokemonEntity, ModelFrame>
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>
   public final val wool: ModelPart

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "dubwool");
      this.head = this.getPart("head");
      this.foreLeftLeg = this.getPart("leg_front_left");
      this.foreRightLeg = this.getPart("leg_front_right");
      this.hindLeftLeg = this.getPart("leg_back_left");
      this.hindRightLeg = this.getPart("leg_back_right");
      this.wool = this.getPart("wool_shearable");
      this.neckWool = this.getPart("neck_wool_shearable");
      this.portraitScale = 3.1F;
      this.portraitTranslation = new Vec3(-1.2, -0.7, 0.0);
      this.profileScale = 0.9F;
      this.profileTranslation = new Vec3(0.0, 0.4, 0.0);
      this.cryAnimation = DubwoolModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dubwool", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dubwool", "sleep", null, 4, null)},
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(true)
            },
            null,
            152,
            null
         )
      );
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.setOf(new PoseType[]{PoseType.NONE, PoseType.STAND, PoseType.PORTRAIT, PoseType.PROFILE}),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "dubwool", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(true)
            },
            new ModelQuirk[]{blink},
            48,
            null
         )
      );
      this.setWalk(
         PoseableEntityModel.registerPose$default(
            this,
            "walking",
            SetsKt.setOf(new PoseType[]{PoseType.SWIM, PoseType.WALK}),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "dubwool", "ground_walk", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true),
               ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(true)
            },
            new ModelQuirk[]{blink},
            48,
            null
         )
      );
      this.setShearedsleep(
         PoseableEntityModel.registerPose$default(
            this,
            "shearedsleep",
            PoseType.SLEEP,
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dubwool", "sleep", null, 4, null)},
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(false)
            },
            null,
            304,
            null
         )
      );
      this.setShearedstanding(
         PoseableEntityModel.registerPose$default(
            this,
            "shearedstanding",
            SetsKt.setOf(new PoseType[]{PoseType.NONE, PoseType.STAND, PoseType.PORTRAIT, PoseType.PROFILE}),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "dubwool", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            48,
            null
         )
      );
      this.setShearedwalk(
         PoseableEntityModel.registerPose$default(
            this,
            "shearedwalking",
            SetsKt.setOf(new PoseType[]{PoseType.SWIM, PoseType.WALK}),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "dubwool", "ground_walk", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            48,
            null
         )
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getShearedwalk(), this.getShearedstanding(), this.getSleep()))
         PoseableEntityModel.bedrockStateful$default(this, "dubwool", "faint", null, 4, null)
         else
         null;
   }

   public open fun getEatAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isNotPosedIn(this.getSleep(), this.getShearedsleep()))
         PoseableEntityModel.bedrockStateful$default(this, "dubwool", "eat", null, 4, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: DubwoolModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "dubwool", "cry", null, 4, null);
   }
}
