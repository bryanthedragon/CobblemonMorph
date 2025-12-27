package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import net.minecraft.world.entity.Entity

public interface AnimationReferenceFactory {
   public abstract fun <T : Entity> stateless(model: PoseableEntityModel<Any>, animString: String): StatelessAnimation<Any, ModelFrame> {
   }

   public abstract fun <T : Entity> stateful(model: PoseableEntityModel<Any>, animString: String): StatefulAnimation<Any, ModelFrame> {
   }
}
