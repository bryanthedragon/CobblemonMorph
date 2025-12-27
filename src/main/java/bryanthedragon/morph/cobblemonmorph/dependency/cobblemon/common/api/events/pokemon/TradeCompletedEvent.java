package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant

public class TradeCompletedEvent(tradeParticipant1: TradeParticipant,
   tradeParticipant1Pokemon: Pokemon,
   tradeParticipant2: TradeParticipant,
   tradeParticipant2Pokemon: Pokemon
) {
   public final val tradeParticipant1: TradeParticipant
   public final val tradeParticipant1Pokemon: Pokemon
   public final val tradeParticipant2: TradeParticipant
   public final val tradeParticipant2Pokemon: Pokemon

   init {
      this.tradeParticipant1 = tradeParticipant1;
      this.tradeParticipant1Pokemon = tradeParticipant1Pokemon;
      this.tradeParticipant2 = tradeParticipant2;
      this.tradeParticipant2Pokemon = tradeParticipant2Pokemon;
   }
}
