package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import net.minecraft.network.syncher.EntityDataAccessor

public class GenericBedrockServerDelegate : EntitySideDelegate<GenericBedrockEntity> {
   fun initialize(entity: GenericBedrockEntity) {
      EntitySideDelegate.DefaultImpls.initialize(this, entity);
   }

   fun tick(entity: GenericBedrockEntity) {
      EntitySideDelegate.DefaultImpls.tick(this, entity);
   }

   override fun onTrackedDataSet(data: EntityDataAccessor<?>) {
      EntitySideDelegate.DefaultImpls.onTrackedDataSet(this, data);
   }
}
