package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
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
import kotlin.jvm.functions.Function2
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class GimmighoulChestModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame, BimanualFrame {
   public final lateinit var battle: Pose<PokemonEntity, ModelFrame>
   public final lateinit var closed: Pose<PokemonEntity, ModelFrame>
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
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "gimmighoul_chest");
      this.head = this.getPart("head");
      this.leftArm = this.getPart("arm_left");
      this.rightArm = this.getPart("arm_right");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.portraitScale = 2.54F;
      this.portraitTranslation = new Vec3(-0.01, -1.6, 0.0);
      this.profileScale = 0.65F;
      this.profileTranslation = new Vec3(0.0, 0.76, 0.0);
      this.cryAnimation = GimmighoulChestModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "gimmighoul_chest", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val quirk: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(30.0F, 120.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "gimmighoul_chest", "idle_quirk", null, 4, null);
            }
         }) as Function1, 6, null
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var16: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var16, var10002),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "gimmighoul_chest", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink, quirk},
            184,
            null
         )
      );
      var var7: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var10: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var13: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "gimmighoul_chest", "mimic", null, 4, null)
      };
      val var17: PoseableEntityModel = this;
      this.setClosed(PoseableEntityModel.registerPose$default(var17, "closed", var7, <unrepresentable>.INSTANCE, 0, null, null, var13, null, var10, 184, null));
      var7 = PoseType.Companion.getMOVING_POSES();
      var10 = new ModelQuirk[]{blink};
      var13 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "gimmighoul_chest", "ground_walk", null, 4, null)
      };
      val var18: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var18, "walk", var7, null, 0, null, null, var13, null, var10, 188, null));
      var7 = PoseType.Companion.getSTATIONARY_POSES();
      var10 = new ModelQuirk[]{blink, quirk};
      var13 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "gimmighoul_chest", "battle_idle", null, 4, null)
      };
      val var19: PoseableEntityModel = this;
      this.setBattle(PoseableEntityModel.registerPose$default(var19, "battle", var7, <unrepresentable>.INSTANCE, 0, null, null, var13, null, var10, 184, null));
      this.getClosed()
         .getTransitions()
         .put(
            this.getBattle().getPoseName(),
            new Function2<Pose<PokemonEntity, ? extends ModelFrame>, Pose<PokemonEntity, ? extends ModelFrame>, BedrockStatefulAnimation<PokemonEntity>>(this) {
               {
                  super(2);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final BedrockStatefulAnimation<PokemonEntity> invoke(
                  @NotNull Pose<PokemonEntity, ? extends ModelFrame> var1, @NotNull Pose<PokemonEntity, ? extends ModelFrame> var2
               ) {
                  return PoseableEntityModel.bedrockStateful$default(this.this$0, "gimmighoul_chest", "surprise", null, 4, null);
               }
            }
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
   fun `cryAnimation$lambda$0`(`this$0`: GimmighoulChestModel, var1: PokemonEntity, pose: PoseableEntityState): StatefulAnimation {
      return if (pose.isPosedIn(`this$0`.getBattle()))
         PoseableEntityModel.bedrockStateful$default(`this$0`, "gimmighoul_chest", "battle_cry", null, 4, null)
         else
         PoseableEntityModel.bedrockStateful$default(`this$0`, "gimmighoul_chest", "cry", null, 4, null);
   }
}
