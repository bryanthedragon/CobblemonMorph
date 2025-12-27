package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import net.minecraft.world.entity.Entity

public class GildedState : PoseableEntityState<Entity> {
   public open val schedulingTracker: SchedulingTracker

   public open fun getEntity(): Nothing? {
      return null;
   }

   public override fun updatePartialTicks(partialTicks: Float) {
      this.setCurrentPartialTicks(this.getCurrentPartialTicks() + partialTicks);
   }
}
