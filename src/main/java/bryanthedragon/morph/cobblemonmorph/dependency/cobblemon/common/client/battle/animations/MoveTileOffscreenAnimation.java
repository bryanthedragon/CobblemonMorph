package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon

public class MoveTileOffscreenAnimation(duration: Float = 0.75F) : TileAnimation {
   public final val duration: Float
   public final var passedSeconds: Float

   init {
      this.duration = duration;
   }

   public override fun shouldHoldUntilNextAnimation(): Boolean {
      return true;
   }

   public override operator fun invoke(activeBattlePokemon: ActiveClientBattlePokemon, deltaTicks: Float): Boolean {
      this.passedSeconds += deltaTicks / 20;
      this.passedSeconds = RangesKt.coerceAtMost(this.passedSeconds, this.duration);
      val ratio: Float = this.passedSeconds / this.duration;
      activeBattlePokemon.setXDisplacement(
         activeBattlePokemon.getXDisplacement() + (activeBattlePokemon.getInvisibleX() - activeBattlePokemon.getXDisplacement()) * ratio
      );
      return this.passedSeconds == this.duration;
   }

   fun MoveTileOffscreenAnimation() {
      this(0.0F, 1, null);
   }
}
