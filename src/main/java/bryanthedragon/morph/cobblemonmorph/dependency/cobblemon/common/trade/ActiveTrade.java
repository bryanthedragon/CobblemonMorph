package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID

public class ActiveTrade(player1: TradeParticipant, player2: TradeParticipant) {
   public final val player1: TradeParticipant
   public final val player1Offer: TradeOffer
   public final val player2: TradeParticipant
   public final val player2Offer: TradeOffer

   init {
      this.player1 = player1;
      this.player2 = player2;
      this.player1Offer = new TradeOffer();
      this.player2Offer = new TradeOffer();
   }

   public fun getTradeParticipant(uuid: UUID): TradeParticipant {
      return if (this.player1.getUuid() == uuid) this.player1 else this.player2;
   }

   public fun getOffer(tradeParticipant: TradeParticipant): TradeOffer {
      return if (tradeParticipant == this.player1) this.player1Offer else this.player2Offer;
   }

   public fun getOpposingOffer(tradeParticipant: TradeParticipant): TradeOffer {
      return if (tradeParticipant == this.player1) this.player2Offer else this.player1Offer;
   }

   public fun updateOffer(tradeParticipant: TradeParticipant, pokemon: Pokemon?) {
      this.getOffer(tradeParticipant).updateOffer(pokemon);
      this.getOffer(this.getOppositePlayer(tradeParticipant)).setAccepted(false);
      this.player1.updateOffer(this, tradeParticipant, pokemon);
      this.player2.updateOffer(this, tradeParticipant, pokemon);
   }

   public fun updateAcceptance(tradeParticipant: TradeParticipant, acceptance: Boolean) {
      val offer: TradeOffer = this.getOpposingOffer(tradeParticipant);
      if (offer.getAccepted() != acceptance) {
         offer.setAccepted(acceptance);
         val var10000: TradeParticipant = this.getOppositePlayer(tradeParticipant);
         var var10002: Pokemon = offer.getPokemon();
         val var4: UUID = var10002.getUuid();
         var10000.changeTradeAcceptance(this, var4, acceptance);
         var10002 = offer.getPokemon();
         val var6: UUID = var10002.getUuid();
         tradeParticipant.changeTradeAcceptance(this, var6, acceptance);
      }

      if (offer.getAccepted() && this.getOffer(tradeParticipant).getAccepted()) {
         this.performTrade();
      }
   }

   public fun getOppositePlayer(tradeParticipant: TradeParticipant): TradeParticipant {
      return if (tradeParticipant == this.player1) this.player2 else this.player1;
   }

   public fun performTrade() {
      val var1: TradeManager = TradeManager.INSTANCE;
      val var2: TradeParticipant = this.player1;
      val var3: TradeParticipant = this.player2;
      val var10000: Pokemon = this.player1Offer.getPokemon();
      val var6: Pokemon = this.player2Offer.getPokemon();
      var1.performTrade(var2, var10000, var3, var6);
      this.completeTrade();
   }

   public fun cancelTrade() {
      this.player1.cancelTrade(this);
      this.player2.cancelTrade(this);
      TradeManager.INSTANCE.getActiveTrades().remove(this);
   }

   public fun completeTrade() {
      var var10000: TradeParticipant = this.player1;
      var var10002: Pokemon = this.player1Offer.getPokemon();
      val var2: UUID = var10002.getUuid();
      var var10003: Pokemon = this.player2Offer.getPokemon();
      val var5: UUID = var10003.getUuid();
      var10000.completeTrade(this, var2, var5);
      var10000 = this.player2;
      var10002 = this.player2Offer.getPokemon();
      val var4: UUID = var10002.getUuid();
      var10003 = this.player1Offer.getPokemon();
      val var7: UUID = var10003.getUuid();
      var10000.completeTrade(this, var4, var7);
      this.player1Offer.setPokemon(null);
      this.player1Offer.setAccepted(false);
      this.player2Offer.setPokemon(null);
      this.player2Offer.setAccepted(false);
   }
}
