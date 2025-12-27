package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon

public class SwapAndMoveTileOnscreenAnimation(battlePokemon: ClientBattlePokemon, duration: Float = 0.75F) : TileAnimation {
   public final val battlePokemon: ClientBattlePokemon
   public final val duration: Float
   public final var passedSeconds: Float

   init {
      this.battlePokemon = battlePokemon;
      this.duration = duration;
   }

   public override fun shouldHoldUntilNextAnimation(): Boolean {
      return false;
   }

   public override operator fun invoke(activeBattlePokemon: ActiveClientBattlePokemon, deltaTicks: Float): Boolean {
      if (this.passedSeconds == 0.0F) {
         activeBattlePokemon.setBattlePokemon(this.battlePokemon);
      }

      this.passedSeconds += deltaTicks / 20;
      this.passedSeconds = RangesKt.coerceAtMost(this.passedSeconds, this.duration);
      val ratio: Float = this.passedSeconds / this.duration;
      activeBattlePokemon.setXDisplacement(
         activeBattlePokemon.getXDisplacement() + (activeBattlePokemon.getInvisibleX() - activeBattlePokemon.getXDisplacement()) * ((float)1 - ratio)
      );
      return this.passedSeconds == this.duration;
   }
}
