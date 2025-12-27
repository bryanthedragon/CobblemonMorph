package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import net.minecraft.world.entity.Entity

public open class QuirkData<T extends Entity> {
   public final val animations: MutableList<StatefulAnimation<Any, *>> = (new ArrayList()) as java.util.List
   public final var primaryAnimation: PrimaryAnimation<Any>?

   public open fun run(
      entity: Any?,
      model: PoseableEntityModel<Any>,
      state: PoseableEntityState<Any>,
      limbSwing: Float,
      limbSwingAmount: Float,
      ageInTicks: Float,
      headYaw: Float,
      headPitch: Float,
      intensity: Float
   ) {
      if (this.primaryAnimation != null && !(state.getPrimaryAnimation() == this.primaryAnimation)) {
         this.primaryAnimation = null;
      }

      this.animations.removeIf(QuirkData::run$lambda$0);
   }

   @JvmStatic
   fun `run$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
