package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity

import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity

public interface EntitySideDelegate<T extends Entity> {
   public open fun initialize(entity: Any) {
   }

   public open fun tick(entity: Any) {
   }

   public open fun onTrackedDataSet(data: EntityDataAccessor<*>) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends Entity> initialize(`$this`: EntitySideDelegate<T>, entity: T) {
      }

      @JvmStatic
      fun <T extends Entity> tick(`$this`: EntitySideDelegate<T>, entity: T) {
      }

      @JvmStatic
      fun <T extends Entity> onTrackedDataSet(`$this`: EntitySideDelegate<T>, data: EntityDataAccessor<?>) {
      }
   }
}
