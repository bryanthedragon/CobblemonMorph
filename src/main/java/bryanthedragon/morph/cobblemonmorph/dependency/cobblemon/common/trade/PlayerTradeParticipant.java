package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeAcceptanceChangedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCompletedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeUpdatedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.UUID
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

public class PlayerTradeParticipant(player: ServerPlayer) : TradeParticipant {
   public open val name: Component
   public open val party: PlayerPartyStore
   public final val player: ServerPlayer
   public open val uuid: UUID

   init {
      this.player = player;
      this.name = this.player.m_7755_();
      this.uuid = this.player.m_20148_();
      this.party = PlayerExtensionsKt.party(this.player);
   }

   public override fun updateOffer(trade: ActiveTrade, tradeParticipant: TradeParticipant, pokemon: Pokemon?) {
      CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeUpdatedPacket(tradeParticipant.getUuid(), pokemon));
   }

   public override fun changeTradeAcceptance(trade: ActiveTrade, pokemonId: UUID, acceptance: Boolean) {
      CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeAcceptanceChangedPacket(pokemonId, acceptance));
   }

   public override fun cancelTrade(trade: ActiveTrade) {
      CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeCancelledPacket());
   }

   public override fun completeTrade(trade: ActiveTrade, pokemonId1: UUID, pokemonId2: UUID) {
      CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeCompletedPacket(pokemonId1, pokemonId2));
   }
}
