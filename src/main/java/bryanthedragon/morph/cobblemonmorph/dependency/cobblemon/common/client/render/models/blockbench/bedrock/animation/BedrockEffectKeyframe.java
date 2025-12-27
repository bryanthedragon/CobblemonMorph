package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import net.minecraft.world.entity.Entity

public abstract class BedrockEffectKeyframe {
   public final val seconds: Float

   open fun BedrockEffectKeyframe(seconds: Float) {
      this.seconds = seconds;
   }

   public abstract fun <T : Entity> run(entity: Any, state: PoseableEntityState<Any>) {
   }
}
