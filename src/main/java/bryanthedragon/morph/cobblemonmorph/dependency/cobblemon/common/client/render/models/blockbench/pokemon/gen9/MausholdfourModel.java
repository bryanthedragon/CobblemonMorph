package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9

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
import org.jetbrains.annotations.Nullable

public class MausholdfourModel(root: ModelPart) : PokemonPoseableModel, HeadedFrame {
   public open val cryAnimation: CryProvider
   public open val head: ModelPart
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "maushold");
      this.head = this.getPart("head");
      this.portraitScale = 1.0F;
      this.portraitTranslation = new Vec3(0.1, 0.0, 0.0);
      this.profileScale = 0.8F;
      this.profileTranslation = new Vec3(0.0, 0.4, 0.0);
      this.cryAnimation = MausholdfourModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "maushold_four", "blink1", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "maushold_four", "blink2", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "maushold_four", "blink3", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "maushold_four", "blink4", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val head2: <unrepresentable> = new HeadedFrame(this) {
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
      val head3: <unrepresentable> = new HeadedFrame(this) {
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
      val head4: <unrepresentable> = new HeadedFrame(this) {
         @NotNull
         private final ModelPart rootPart;
         @NotNull
         private final ModelPart head;

         {
            this.rootPart = `$receiver`.getRootPart();
            this.head = `$receiver`.getPart("head4");
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
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var15: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var15, var10002),
            null,
            10,
            null,
            null,
            new StatelessAnimation[]{
               HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
               new SingleBoneLookAnimation(head2, false, false, false, false, null, null, null, null, null, null, 2016, null),
               new SingleBoneLookAnimation(head3, false, false, false, false, null, null, null, null, null, null, 2016, null),
               new SingleBoneLookAnimation(head4, false, false, false, false, null, null, null, null, null, null, 2016, null),
               PoseableEntityModel.bedrock$default(this, "maushold_four", "ground_idle", null, 4, null)
            },
            null,
            new ModelQuirk[]{blink1, blink2, blink3, blink4},
            180,
            null
         )
      );
      val var12: EnumSet = PoseType.Companion.getMOVING_POSES();
      val var13: Array<ModelQuirk> = new ModelQuirk[]{blink1, blink2, blink3, blink4};
      val var14: Array<StatelessAnimation> = new StatelessAnimation[]{
         HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null),
         new SingleBoneLookAnimation(head2, false, false, false, false, null, null, null, null, null, null, 2016, null),
         new SingleBoneLookAnimation(head3, false, false, false, false, null, null, null, null, null, null, 2016, null),
         new SingleBoneLookAnimation(head4, false, false, false, false, null, null, null, null, null, null, 2016, null),
         PoseableEntityModel.bedrock$default(this, "maushold_four", "ground_walk", null, 4, null)
      };
      val var16: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var16, "walk", var12, null, 10, null, null, var14, null, var13, 180, null));
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isNotPosedIn(this.getSleep())) PoseableEntityModel.bedrockStateful$default(this, "maushold_four", "faint", null, 4, null) else null;
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
   fun `cryAnimation$lambda$0`(`this$0`: MausholdfourModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "maushold_four", "cry", null, 4, null);
   }
}
