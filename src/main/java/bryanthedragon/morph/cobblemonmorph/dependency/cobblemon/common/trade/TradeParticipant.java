package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import net.minecraft.network.chat.Component

public interface TradeParticipant {
   public val name: Component
   public val party: PartyStore
   public val uuid: UUID

   public abstract fun updateOffer(trade: ActiveTrade, tradeParticipant: TradeParticipant, pokemon: Pokemon?) {
   }

   public abstract fun changeTradeAcceptance(trade: ActiveTrade, pokemonId: UUID, acceptance: Boolean) {
   }

   public abstract fun cancelTrade(trade: ActiveTrade) {
   }

   public abstract fun completeTrade(trade: ActiveTrade, pokemonId1: UUID, pokemonId2: UUID) {
   }
}
