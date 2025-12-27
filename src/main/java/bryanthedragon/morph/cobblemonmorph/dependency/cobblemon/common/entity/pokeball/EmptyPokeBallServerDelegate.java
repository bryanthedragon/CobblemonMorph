package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity

public class EmptyPokeBallServerDelegate : EntitySideDelegate<EmptyPokeBallEntity> {
   fun initialize(entity: EmptyPokeBallEntity) {
      EntitySideDelegate.DefaultImpls.initialize(this, entity as Entity);
   }

   fun tick(entity: EmptyPokeBallEntity) {
      EntitySideDelegate.DefaultImpls.tick(this, entity as Entity);
   }

   override fun onTrackedDataSet(data: EntityDataAccessor<?>) {
      EntitySideDelegate.DefaultImpls.onTrackedDataSet(this, data);
   }
}
