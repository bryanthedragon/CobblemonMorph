package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public open class TradePokemonContext(traded: Pokemon, received: Pokemon) {
   public final val received: Pokemon
   public final val traded: Pokemon

   init {
      this.traded = traded;
      this.received = received;
   }
}
