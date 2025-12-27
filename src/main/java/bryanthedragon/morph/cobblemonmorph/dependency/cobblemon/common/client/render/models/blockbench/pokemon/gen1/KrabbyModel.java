package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
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
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

public class KrabbyModel(root: ModelPart) : PokemonPoseableModel {
   public final lateinit var battleidle: Pose<PokemonEntity, ModelFrame>
   public open val cryAnimation: CryProvider
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart
   public final lateinit var standing: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standingBubbles: Pose<PokemonEntity, ModelFrame>
   public final lateinit var standingRain: Pose<PokemonEntity, ModelFrame>
   public final lateinit var walk: Pose<PokemonEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "krabby");
      this.portraitScale = 2.5F;
      this.portraitTranslation = new Vec3(-0.15, -1.8, 0.0);
      this.profileScale = 1.0F;
      this.profileTranslation = new Vec3(0.0, 0.2, 0.0);
      this.cryAnimation = KrabbyModel::cryAnimation$lambda$0;
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
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "krabby", "blink", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val snipLeft: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "krabby", "snip_left", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val snipRight: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, null, null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "krabby", "snip_right", null, 4, null);
            }
         }) as Function1, 7, null
      );
      val bubble: SimpleQuirk = PoseableEntityModel.quirk$default(
         this, TuplesKt.to(10.0F, 20.0F), null, null, (new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
               return PoseableEntityModel.bedrockStateful$default(this.this$0, "krabby", "quirk_bubble", null, 4, null);
            }
         }) as Function1, 6, null
      );
      val var10001: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      val var21: java.util.Set = var10001;
      val var10002: EnumSet = PoseType.Companion.getUI_POSES();
      this.setStanding(
         PoseableEntityModel.registerPose$default(
            this,
            "standing",
            SetsKt.plus(var21, var10002),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krabby", "ground_idle", null, 4, null)},
            null,
            new ModelQuirk[]{blink, snipLeft, snipRight},
            184,
            null
         )
      );
      var var9: EnumSet = PoseType.Companion.getSTATIONARY_POSES();
      var var13: Array<ModelQuirk> = new ModelQuirk[]{blink, snipLeft, snipRight, bubble};
      var var17: Array<StatelessAnimation> = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krabby", "ground_idle", null, 4, null)};
      val var22: PoseableEntityModel = this;
      this.setStandingBubbles(
         PoseableEntityModel.registerPose$default(var22, "standing_bubbles", var9, <unrepresentable>.INSTANCE, 0, null, null, var17, null, var13, 184, null)
      );
      var9 = PoseType.Companion.getSTATIONARY_POSES();
      var13 = new ModelQuirk[]{blink, snipLeft, snipRight};
      var17 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krabby", "ground_idle", null, 4, null)};
      val var23: PoseableEntityModel = this;
      this.setStandingRain(
         PoseableEntityModel.registerPose$default(var23, "standing_rain", var9, <unrepresentable>.INSTANCE, 0, null, null, var17, null, var13, 184, null)
      );
      var9 = PoseType.Companion.getMOVING_POSES();
      var13 = new ModelQuirk[]{blink, snipLeft, snipRight};
      var17 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krabby", "ground_walk", null, 4, null)};
      val var24: PoseableEntityModel = this;
      this.setWalk(PoseableEntityModel.registerPose$default(var24, "walk", var9, null, 0, null, null, var17, null, var13, 188, null));
      var9 = PoseType.Companion.getSTATIONARY_POSES();
      var13 = new ModelQuirk[]{blink, snipLeft, snipRight};
      var17 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "krabby", "battle_idle", null, 4, null)};
      val var25: PoseableEntityModel = this;
      this.setBattleidle(
         PoseableEntityModel.registerPose$default(var25, "battle_idle", var9, <unrepresentable>.INSTANCE, 10, null, null, var17, null, var13, 176, null)
      );
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(`this$0`: KrabbyModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return PoseableEntityModel.bedrockStateful$default(`this$0`, "krabby", "cry", null, 4, null);
   }
}
