package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

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
import org.jetbrains.annotations.Nullable

public class DodrioModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public final val lefthead: HeadedFrame
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public final val righthead: HeadedFrame
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walking: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "dodrio");
      this.head = this.getPart("head4");
      this.lefthead = new HeadedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart head;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.head = `$receiver`.getPart("head3");
         }

         @NotNull
         public ModelPart getRootPart() {
            return this.rootPart;
         }

         @NotNull
         public ModelPart getHead() {
            return this.head;
         }

         @NotNull
         @Override
         public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(
            boolean invertX,
            boolean invertY,
            boolean disableX,
            boolean disableY,
            @Nullable java.lang.Float pitchMultiplier,
            @Nullable java.lang.Float yawMultiplier,
            @Nullable java.lang.Float maxPitch,
            @Nullable java.lang.Float minPitch,
            @Nullable java.lang.Float maxYaw,
            @Nullable java.lang.Float minYaw
         ) {
            return HeadedFrame.DefaultImpls.singleBoneLook(
               this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw
            );
         }
      };
      this.righthead = new HeadedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart head;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.head = `$receiver`.getPart("head2");
         }

         @NotNull
         public ModelPart getRootPart() {
            return this.rootPart;
         }

         @NotNull
         public ModelPart getHead() {
            return this.head;
         }

         @NotNull
         @Override
         public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(
            boolean invertX,
            boolean invertY,
            boolean disableX,
            boolean disableY,
            @Nullable java.lang.Float pitchMultiplier,
            @Nullable java.lang.Float yawMultiplier,
            @Nullable java.lang.Float maxPitch,
            @Nullable java.lang.Float minPitch,
            @Nullable java.lang.Float maxYaw,
            @Nullable java.lang.Float minYaw
         ) {
            return HeadedFrame.DefaultImpls.singleBoneLook(
               this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw
            );
         }
      };
      this.portraitScale = 1.5F;
      this.portraitTranslation = new Vec3(-0.15, 0.9, 0.0);
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(0.0, 0.6, 0.0);
      this.cryAnimation = DodrioModel::cryAnimation$lambda$0;
   }

   public override fun registerPoses() {
      val blink1: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dodrio", "blink1", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val blink2: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dodrio", "blink2", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val blink3: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dodrio", "blink3", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val bite1: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(5.0F, 20.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dodrio", "bite_quirk1", null, 4, null);
            }
         }) as Function1, 6, null
      );
      val bite2: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(5.0F, 20.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dodrio", "bite_quirk2", null, 4, null);
            }
         }) as Function1, 6, null
      );
      val bite3: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(5.0F, 20.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "dodrio", "bite_quirk3", null, 4, null);
            }
         }) as Function1, 6, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dodrio", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      val var18: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var19: java.util.Set = var18;
      val var22: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var19, var22),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, 1.0F, 0.4F, null, null, null, null, 975, null),
               new SingleBoneLookAnimation(this.lefthead, false, false, false, false, 1.0F, 1.5F, 45.0F, -45.0F, 45.0F, 10.0F),
               new SingleBoneLookAnimation(this.righthead, false, false, false, false, 1.0F, 1.5F, 45.0F, -45.0F, 10.0F, -45.0F),
               PoseableEntityModel.bedrock$default(this, "dodrio", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink1, blink2, blink3},
            176,
            null
         )
      );
      var var12: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var14: Array<ModelQuirk> = new ModelQuirk[]{blink1, blink2, blink3};
      var var16: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dodrio", "ground_walk", null, 4, null)};
      val var20: PoseableEntityModel = this;
      this.setWalking(PoseableEntityModel.registerPose$default(var20, "walking", var12, null, 10, null, null, var16, null, var14, 180, null));
      var12 = PoseType.Companion.getSTATIONARY_POSES();
      var14 = new ModelQuirk[]{blink1, blink2, blink3, bite1, bite2, bite3};
      var16 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dodrio", "battle_idle", null, 4, null)};
      val var21: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var21, "battle_idle", var12, <unrepresentable>.INSTANCE, 10, null, null, var16, null, var14, 176, null)
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
   fun `cryAnimation$lambda$0`(`this$0`: DodrioModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "dodrio", "cry", null, 4, null);
   }
}
