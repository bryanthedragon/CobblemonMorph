package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade

import java.util.UUID

public class ClientTradeOffer(tradeOfferId: UUID, traderId: UUID) {
   public final val tradeOfferId: UUID
   public final val traderId: UUID

   init {
      this.tradeOfferId = tradeOfferId;
      this.traderId = traderId;
   }
}
