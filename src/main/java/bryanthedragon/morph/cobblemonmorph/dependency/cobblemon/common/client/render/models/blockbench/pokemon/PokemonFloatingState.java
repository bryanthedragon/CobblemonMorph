package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity

public class PokemonFloatingState : PoseableEntityState<PokemonEntity> {
   public open val schedulingTracker: ClientTaskTracker = ClientTaskTracker.INSTANCE

   public open fun getEntity(): Nothing? {
      return null;
   }

   public override fun updatePartialTicks(partialTicks: Float) {
      this.setCurrentPartialTicks(this.getCurrentPartialTicks() + partialTicks);
   }
}
