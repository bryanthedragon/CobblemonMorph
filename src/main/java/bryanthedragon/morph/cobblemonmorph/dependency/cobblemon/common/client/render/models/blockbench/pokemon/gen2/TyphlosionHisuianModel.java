package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
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

public class TyphlosionHisuianModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final val spoopy_flame: ModelPart
   public final val spoopy_orb1: ModelPart
   public final val spoopy_orb2: ModelPart
   public final val spoopy_orb3: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "typhlosion_hisui");
      this.head = this.getPart("head");
      this.portraitScale = 1.4F;
      this.portraitTranslation = new Vec3(-0.45, 2.37, 0.0);
      this.profileScale = 0.5F;
      this.profileTranslation = new Vec3(0.0, 1.0, 0.0);
      this.spoopy_flame = this.getPart("fire_main");
      this.spoopy_orb1 = this.getPart("fire_orb_right1");
      this.spoopy_orb2 = this.getPart("fire_orb_middle1");
      this.spoopy_orb3 = this.getPart("fire_orb_left1");
      this.cryAnimation = TyphlosionHisuianModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "typhlosion_hisuian", "blink", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "sleep", null, 4, null)},
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(false)
            },
            null,
            158,
            null
         )
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var18: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var18, var10002),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, 0.0F, null, null, 895, null),
               PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "ground_idle", null, 4, null)
            },
            new ModelPartTransformation[]{
               ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(false),
               ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(false)
            },
            new ModelQuirk[]{blink},
            48,
            null
         )
      );
      var var8: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var11: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var14: Array<ModelPartTransformation> = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(false),
         ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(false)
      };
      var var16: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, 0.0F, null, null, 895, null),
         PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "ground_walk", null, 4, null)
      };
      val var19: PoseableEntityModel = this;
      this.setWalking(PoseableEntityModel.registerPose$default(var19, "walking", var8, null, 10, null, null, var16, var14, var11, 52, null));
      var8 = PoseType.Companion.getSTATIONARY_POSES();
      var11 = new ModelQuirk[]{blink};
      var14 = new ModelPartTransformation[]{
         ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(true),
         ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(true)
      };
      var16 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, 0.0F, null, null, 895, null),
         PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "battle_idle", null, 4, null)
      };
      val var20: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var20, "battle_idle", var8, <unrepresentable>.INSTANCE, 10, null, null, var16, var14, var11, 48, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: TyphlosionHisuianModel, entity: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return if (entity.isBattling())
         PoseableEntityModel.bedrockStateful$default(`this$0`, "typhlosion_hisuian", "battle_cry", null, 4, null)
         else
         PoseableEntityModel.bedrockStateful$default(`this$0`, "typhlosion_hisuian", "cry", null, 4, null);
   }
}
