package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.PokeBallFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import kotlin.jvm.functions.Function2
import net.minecraft.client.model.geom.ModelPart
import org.jetbrains.annotations.NotNull

public open class PokeBallModel(root: ModelPart) : PoseableEntityModel(null, 1), PokeBallFrame {
   public open val base: ModelPart
   public open val isForLivingEntityRenderer: Boolean
   public open val lid: ModelPart
   public open lateinit var midair: Pose<EmptyPokeBallEntity, ModelFrame>
   public open lateinit var open: Pose<EmptyPokeBallEntity, ModelFrame>
   public open val rootPart: ModelPart
   public open lateinit var shut: Pose<EmptyPokeBallEntity, ModelFrame>

   init {
      this.rootPart = this.registerChildWithAllChildren(root, "poke_ball");
      this.base = this.getPart("bottom");
      this.lid = this.getPart("lid");
   }

   public open fun getState(entity: EmptyPokeBallEntity): EmptyPokeBallClientDelegate {
      val var10000: EntitySideDelegate = entity.getDelegate();
      return var10000 as EmptyPokeBallClientDelegate;
   }

   public override fun registerPoses() {
      this.setMidair(
         PoseableEntityModel.registerPose$default(
            this,
            "flying",
            SetsKt.setOf(PoseType.NONE),
            <unrepresentable>.INSTANCE,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "poke_ball", "throw", null, 4, null)},
            null,
            null,
            432,
            null
         )
      );
      this.setShut(
         PoseableEntityModel.registerPose$default(
            this,
            "shut",
            SetsKt.setOf(PoseType.NONE),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "poke_ball", "shut_idle", null, 4, null)},
            null,
            null,
            436,
            null
         )
      );
      this.setOpen(
         PoseableEntityModel.registerPose$default(
            this,
            "open",
            SetsKt.setOf(PoseType.NONE),
            null,
            0,
            null,
            null,
            new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "poke_ball", "open_idle", null, 4, null)},
            null,
            null,
            436,
            null
         )
      );
      this.getShut()
         .getTransitions()
         .put(
            this.getOpen().getPoseName(),
            new Function2<Pose<EmptyPokeBallEntity, ? extends ModelFrame>, Pose<EmptyPokeBallEntity, ? extends ModelFrame>, BedrockStatefulAnimation<EmptyPokeBallEntity>>(
               this
            ) {
               {
                  super(2);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final BedrockStatefulAnimation<EmptyPokeBallEntity> invoke(
                  @NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> var1, @NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> var2
               ) {
                  return PoseableEntityModel.bedrockStateful$default(this.this$0, "poke_ball", "open", null, 4, null);
               }
            }
         );
      this.getOpen()
         .getTransitions()
         .put(
            this.getShut().getPoseName(),
            new Function2<Pose<EmptyPokeBallEntity, ? extends ModelFrame>, Pose<EmptyPokeBallEntity, ? extends ModelFrame>, BedrockStatefulAnimation<EmptyPokeBallEntity>>(
               this
            ) {
               {
                  super(2);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final BedrockStatefulAnimation<EmptyPokeBallEntity> invoke(
                  @NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> var1, @NotNull Pose<EmptyPokeBallEntity, ? extends ModelFrame> var2
               ) {
                  return PoseableEntityModel.bedrockStateful$default(this.this$0, "poke_ball", "shut", null, 4, null);
               }
            }
         );
      val var10000: java.util.Map = this.getMidair().getTransitions();
      val var7: java.lang.String = this.getOpen().getPoseName();
      val var10002: Any = this.getShut().getTransitions().get(this.getOpen().getPoseName());
      var10000.put(var7, var10002);
   }
}
