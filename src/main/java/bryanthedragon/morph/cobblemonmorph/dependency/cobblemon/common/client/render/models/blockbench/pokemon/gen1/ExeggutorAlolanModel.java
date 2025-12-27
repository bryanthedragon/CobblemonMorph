package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
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

public class ExeggutorAlolanModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame, BipedFrame {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val head: ModelPart
   public final val head2: HeadedFrame
   public final val head3: HeadedFrame
   public open val leftLeg: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rightLeg: ModelPart
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "exeggutor");
      this.head = this.getPart("head");
      this.leftLeg = this.getPart("leg_left");
      this.rightLeg = this.getPart("leg_right");
      this.head2 = new HeadedFrame(this) {
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
      this.head3 = new HeadedFrame(this) {
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
      this.portraitScale = 1.0F;
      this.portraitTranslation = new Vec3(-1.4, 16.55, 0.0);
      this.profileScale = 0.45F;
      this.profileTranslation = new Vec3(0.0, 1.0, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "exeggutor_alolan", "blink", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "exeggutor_alolan", "blink2", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "exeggutor_alolan", "blink3", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val blink4: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "exeggutor_alolan", "blink4", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "exeggutor_alolan", "sleep", null, 4, null)},
            null,
            null,
            222,
            null
         )
      );
      val var16: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var17: java.util.Set = var16;
      val var20: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var17, var20),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               new SingleBoneLookAnimation(this.head2, false, false, false, false, null, null, null, null, null, null, 2016, null),
               new SingleBoneLookAnimation(this.head3, false, false, false, false, null, null, null, null, null, null, 2016, null),
               PoseableEntityModel.bedrock$default(this, "exeggutor_alolan", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink1, blink2, blink3, blink4},
            184,
            null
         )
      );
      var var10: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var12: Array<ModelQuirk> = new ModelQuirk[]{blink1, blink2, blink3, blink4};
      var var14: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         new SingleBoneLookAnimation(this.head2, false, false, false, false, null, null, null, null, null, null, 2016, null),
         new SingleBoneLookAnimation(this.head3, false, false, false, false, null, null, null, null, null, null, 2016, null),
         PoseableEntityModel.bedrock$default(this, "exeggutor_alolan", "ground_walk", null, 4, null)
      };
      val var18: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var18, "walk", var10, null, 0, null, null, var14, null, var12, 188, null));
      var10 = PoseType.Companion.getSTATIONARY_POSES();
      var12 = new ModelQuirk[]{blink1, blink2, blink3, blink4};
      var14 = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         new SingleBoneLookAnimation(this.head2, false, false, false, false, null, null, null, null, null, null, 2016, null),
         new SingleBoneLookAnimation(this.head3, false, false, false, false, null, null, null, null, null, null, 2016, null),
         PoseableEntityModel.bedrock$default(this, "exeggutor_alolan", "battle_idle", null, 4, null)
      };
      val var19: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var19, "battle_idle", var10, <unrepresentable>.INSTANCE, 10, null, null, var14, null, var12, 176, null)
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
