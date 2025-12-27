package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon

public interface TileAnimation {
   public abstract operator fun invoke(activeBattlePokemon: ActiveClientBattlePokemon, deltaTicks: Float): Boolean {
   }

   public abstract fun shouldHoldUntilNextAnimation(): Boolean {
   }
}
