package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class TradeOffer {
   public final var accepted: Boolean
   public final var pokemon: Pokemon?

   public fun updateOffer(pokemon: Pokemon?) {
      this.pokemon = pokemon;
      this.accepted = false;
   }
}
