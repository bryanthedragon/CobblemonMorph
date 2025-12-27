package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import net.minecraft.world.entity.Entity

public class FossilState(startAge: Int = -1, startPartialTicks: Float = 0.0F) : PoseableEntityState<Entity> {
   public final var growthState: String
   public open val schedulingTracker: SchedulingTracker
   public final var totalPartialTicks: Float

   init {
      this.setAge(if (startAge >= 0) startAge else (int)((double)200.0F * Math.random()));
      this.setCurrentPartialTicks(if ((float)startAge > 0.0F) startPartialTicks else 0.0F);
      this.growthState = "Embryo";
      this.schedulingTracker = new SchedulingTracker();
   }

   public open fun getEntity(): Nothing? {
      return null;
   }

   public fun peekAge(): Int {
      return this.getAge();
   }

   public override fun updatePartialTicks(partialTicks: Float) {
      this.setCurrentPartialTicks(this.getCurrentPartialTicks() + partialTicks / (float)2);
      this.totalPartialTicks += partialTicks / 2;
   }

   fun FossilState() {
      this(0, 0.0F, 3, null);
   }
}
