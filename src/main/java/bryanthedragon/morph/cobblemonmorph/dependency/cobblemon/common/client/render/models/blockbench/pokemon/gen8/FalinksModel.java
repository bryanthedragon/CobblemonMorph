package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen8

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
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
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class FalinksModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battlestanding: Pose<PokemonEntity, ModelFrame>
   public final lateinit var battlestanding2: Pose<PokemonEntity, ModelFrame>
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var sleep: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var uipose: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "falinks");
      this.portraitScale = 1.9F;
      this.portraitTranslation = new Vec3(-1.0, -1.2, 0.0);
      this.profileScale = 0.5F;
      this.profileTranslation = new Vec3(0.1, 0.9, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "falinks", "blink", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "falinks", "blink2", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "falinks", "blink3", null, 4, null);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "falinks", "blink4", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val blink5: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "falinks", "blink5", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val blink6: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "falinks", "blink6", null, 4, null);
            }
         }) as Function1, 7, null
      );
      this.setSleep(
         PoseableEntityModel.registerPose$default(
            this,
            "sleep",
            PoseType.SLEEP,
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "falinks", "sleep", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      var var11: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var9: Array<ModelQuirk> = new ModelQuirk[]{blink, blink2, blink3, blink4, blink5, blink6};
      var var10: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "falinks", "ground_idle", null, 4, null)};
      var var24: PoseableEntityModel = this;
      this.setStanding(
         PoseableEntityModel.registerPose$default(var24, "standing", var11, <unrepresentable>.INSTANCE, 0, null, null, var10, null, var9, 184, null)
      );
      var11 = PoseType.Companion.getSTATIONARY_POSES();
      var9 = new ModelQuirk[]{blink, blink2, blink3, blink4, blink5, blink6};
      var10 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "falinks", "battle_idle2", null, 4, null)};
      var24 = this;
      this.setBattlestanding2(
         PoseableEntityModel.registerPose$default(var24, "battlestanding2", var11, <unrepresentable>.INSTANCE, 0, null, null, var10, null, var9, 184, null)
      );
      var11 = PoseType.Companion.getSTATIONARY_POSES();
      var9 = new ModelQuirk[]{blink, blink2, blink3, blink4, blink5, blink6};
      var10 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "falinks", "battle_idle", null, 4, null)};
      var24 = this;
      this.setBattlestanding(
         PoseableEntityModel.registerPose$default(var24, "battlestanding", var11, <unrepresentable>.INSTANCE, 0, null, null, var10, null, var9, 184, null)
      );
      var11 = PoseType.Companion.getMOVING_POSES();
      var9 = new ModelQuirk[]{blink, blink2, blink3, blink4, blink5, blink6};
      var10 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "falinks", "ground_walk", null, 4, null)};
      var24 = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var24, "walk", var11, <unrepresentable>.INSTANCE, 0, null, null, var10, null, var9, 184, null));
      var11 = PoseType.Companion.getUI_POSES();
      var9 = new ModelQuirk[]{blink, blink2, blink3, blink4, blink5, blink6};
      var10 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "falinks", "summary_idle", null, 4, null)};
      var24 = this;
      this.setUipose(PoseableEntityModel.registerPose$default(var24, "uipose", var11, null, 0, null, null, var10, null, var9, 188, null));
   }
}
