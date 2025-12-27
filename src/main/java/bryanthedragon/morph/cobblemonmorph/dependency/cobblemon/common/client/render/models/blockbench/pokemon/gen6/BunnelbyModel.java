package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen6

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
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

public class BunnelbyModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
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
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "bunnelby");
      this.head = this.getPart("head");
      this.portraitScale = 2.0F;
      this.portraitTranslation = new Vec3(-0.15, -0.3, 0.0);
      this.profileScale = 0.6F;
      this.profileTranslation = new Vec3(0.0, 0.81, 0.0);
      this.cryAnimation = BunnelbyModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "bunnelby", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "bunnelby", "sleep", null, 4, null)},
            null,
            new ModelQuirk[]{
               blink,
               PoseableEntityModel.quirk$default(
                  this, TuplesKt.to(60.0F, 120.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
                     {
                        super(1);
                        this.this$0 = `$receiver`;
                     }

                     @NotNull
                     public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                        return PoseableEntityModel.bedrockStateful$default(this.this$0, "bunnelby", "quirk_sleep", null, 4, null);
                     }
                  }) as Function1, 6, null
               ),
               PoseableEntityModel.quirk$default(
                  this, TuplesKt.to(30.0F, 120.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
                     {
                        super(1);
                        this.this$0 = `$receiver`;
                     }

                     @NotNull
                     public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                        return PoseableEntityModel.bedrockStateful$default(this.this$0, "bunnelby", "quirk_sleep2", null, 4, null);
                     }
                  }) as Function1, 6, null
               )
            },
            94,
            null
         )
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
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               PoseableEntityModel.bedrock$default(this, "bunnelby", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink},
            176,
            null
         )
      );
      var var9: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var12: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var15: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "bunnelby", "ground_walk", null, 4, null)
      };
      val var18: PoseableEntityModel = this;
      this.setWalking(PoseableEntityModel.registerPose$default(var18, "walking", var9, null, 10, null, null, var15, null, var12, 180, null));
      var9 = PoseType.Companion.getSTATIONARY_POSES();
      var12 = new ModelQuirk[]{blink};
      var15 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         PoseableEntityModel.bedrock$default(this, "bunnelby", "battle_idle", null, 4, null)
      };
      val var19: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var19, "battle_idle", var9, <unrepresentable>.INSTANCE, 10, null, null, var15, null, var12, 176, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: BunnelbyModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "bunnelby", "cry", null, 4, null);
   }
}
