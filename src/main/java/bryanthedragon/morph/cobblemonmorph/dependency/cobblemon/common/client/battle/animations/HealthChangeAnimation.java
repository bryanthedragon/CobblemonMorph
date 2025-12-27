package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon

public class HealthChangeAnimation(newHealth: Float, duration: Float = 1.0F) : TileAnimation {
   private final var coercedNewHealth: Float
   private final val duration: Float
   private final var initialHealthRatio: Float
   private final val newHealth: Float
   private final var passedSeconds: Float
   private final var ratioDifference: Float

   init {
      this.newHealth = newHealth;
      this.duration = duration;
      this.initialHealthRatio = -1.0F;
      this.coercedNewHealth = -1.0F;
   }

   public override fun shouldHoldUntilNextAnimation(): Boolean {
      return false;
   }

   public override operator fun invoke(activeBattlePokemon: ActiveClientBattlePokemon, deltaTicks: Float): Boolean {
      val var10000: ClientBattlePokemon = activeBattlePokemon.getBattlePokemon();
      if (var10000 == null) {
         return true;
      } else {
         if (this.coercedNewHealth == -1.0F) {
            this.coercedNewHealth = if (!var10000.isHpFlat()) RangesKt.coerceAtMost(this.newHealth, 1.0F) else this.newHealth;
         }

         if (this.initialHealthRatio == -1.0F) {
            this.initialHealthRatio = var10000.getHpValue();
            this.ratioDifference = this.coercedNewHealth - this.initialHealthRatio;
         }

         this.passedSeconds += deltaTicks / 20;
         this.passedSeconds = RangesKt.coerceAtMost(this.passedSeconds, this.duration);
         var10000.setHpValue(this.initialHealthRatio + this.passedSeconds / this.duration * this.ratioDifference);
         return this.passedSeconds == this.duration;
      }
   }
}
