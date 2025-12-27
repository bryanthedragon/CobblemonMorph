package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame
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

public class HappinyModel(root: ModelPart) : PokemonPoseableModel, BipedFrame {
   public final lateinit var battle_idle: Pose<PokemonEntity, ModelFrame>
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
      this.rootPart = this.registerChildWithAllChildren(root, "happiny");
      this.leftLeg = this.getPart("left_foot");
      this.rightLeg = this.getPart("right_foot");
      this.portraitScale = 2.09F;
      this.portraitTranslation = new Vec3(-0.1, -0.8, 0.0);
      this.profileScale = 0.76F;
      this.profileTranslation = new Vec3(0.0, 0.6, 0.0);
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "happiny", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val hairQuirk: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "happiny", "hairshake_quirk", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val happy: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "happiny", "happy_quirk", null, 4, null);
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
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "happiny", "sleep", null, 4, null)},
            null,
            null,
            444,
            null
         )
      );
      val var15: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var16: java.util.Set = var15;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var16, var10002),
            <unrepresentable>.INSTANCE,
            10,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "happiny", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, hairQuirk, happy},
            176,
            null
         )
      );
      var var9: EnumSet = PoseType.Companion.getMOVING_POSES();
      var var11: Array<ModelQuirk> = new ModelQuirk[]{blink};
      var var13: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "happiny", "ground_walk", null, 4, null)};
      val var17: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var17, "walk", var9, null, 10, null, null, var13, null, var11, 180, null));
      var9 = PoseType.Companion.getSTATIONARY_POSES();
      var11 = new ModelQuirk[]{blink, hairQuirk, happy};
      var13 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "happiny", "battle_idle", null, 4, null)};
      val var18: PoseableEntityModel = this;
      this.setBattle_idle(
         PoseableEntityModel.registerPose$default(var18, "battle_idle", var9, <unrepresentable>.INSTANCE, 10, null, null, var13, null, var11, 176, null)
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): BedrockStatefulAnimation<PokemonEntity>? {
      return if (state.isPosedIn(this.getStanding(), this.getWalk(), this.getSleep(), this.getBattle_idle()))
         PoseableEntityModel.bedrockStateful$default(this, "happiny", "faint", null, 4, null)
         else
         null;
   }
}
