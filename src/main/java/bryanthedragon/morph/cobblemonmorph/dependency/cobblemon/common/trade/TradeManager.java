package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.TradeCompletedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferExpiredPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.ArrayList;
import java.util.Arrays
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nTradeManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeManager.kt\ncom/cobblemon/mod/common/trade/TradeManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,110:1\n1#2:111\n800#3,11:112\n288#3,2:123\n800#3,11:125\n288#3,2:136\n14#4,5:138\n19#4:146\n13579#5:143\n13580#5:145\n14#6:144\n*S KotlinDebug\n*F\n+ 1 TradeManager.kt\ncom/cobblemon/mod/common/trade/TradeManager\n*L\n101#1:112,11\n101#1:123,2\n105#1:125,11\n105#1:136,2\n108#1:138,5\n108#1:146\n108#1:143\n108#1:145\n108#1:144\n*E\n"])
public object TradeManager {
   public final val activeTrades: MutableList<ActiveTrade> = (new ArrayList()) as java.util.List
   public final val requests: MutableList<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager.TradeRequest> = (new ArrayList()) as java.util.List

   public fun getExistingRequest(playerId: UUID): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager.TradeRequest? {
      val var3: java.util.Iterator = requests.iterator();

      var var10000: Any;
      while (true) {
         if (!var3.hasNext()) {
            var10000 = null;
            break;
         }

         val var4: Any = var3.next();
         if ((var4 as TradeManager.TradeRequest).getSenderId() == playerId || (var4 as TradeManager.TradeRequest).getReceiverId() == playerId) {
            var10000 = var4;
            break;
         }
      }

      return var10000 as TradeManager.TradeRequest;
   }

   public fun getActiveTrade(playerId: UUID): ActiveTrade? {
      val var3: java.util.Iterator = activeTrades.iterator();

      var var10000: Any;
      while (true) {
         if (!var3.hasNext()) {
            var10000 = null;
            break;
         }

         val var4: Any = var3.next();
         if ((var4 as ActiveTrade).getPlayer1().getUuid() == playerId || (var4 as ActiveTrade).getPlayer2().getUuid() == playerId) {
            var10000 = var4;
            break;
         }
      }

      return var10000 as ActiveTrade;
   }

   public fun offerTrade(player: ServerPlayer, otherPlayerEntity: ServerPlayer) {
      val var6: java.util.Iterator = requests.iterator();

      var var10000: Any;
      while (true) {
         if (var6.hasNext()) {
            val var7: Any = var6.next();
            if (!((var7 as TradeManager.TradeRequest).getSenderId() == player.m_20148_())) {
               continue;
            }

            var10000 = (CobblemonNetwork)var7;
            break;
         }

         var10000 = null;
         break;
      }

      val existingFromPlayer: TradeManager.TradeRequest = var10000 as TradeManager.TradeRequest;
      if (var10000 as TradeManager.TradeRequest != null) {
         var10000 = PlayerExtensionsKt.getPlayer(existingFromPlayer.getReceiverId());
         if (var10000 != null) {
            CobblemonNetwork.INSTANCE.sendPacket((ServerPlayer)var10000, new TradeOfferExpiredPacket(existingFromPlayer.getTradeOfferId()));
         }
      }

      val var10001: UUID = otherPlayerEntity.m_20148_();
      if (this.getActiveTrade(var10001) != null) {
         val request: Array<Any> = new Object[1];
         val var10004: Component = otherPlayerEntity.m_7755_();
         request[0] = var10004;
         player.m_5661_(LocalizationUtilsKt.lang("trade.occupied", request) as Component, true);
      } else {
         val var10002: UUID = UUID.randomUUID();
         val var10003: UUID = player.m_20148_();
         var var14: UUID = otherPlayerEntity.m_20148_();
         val var10: TradeManager.TradeRequest = new TradeManager.TradeRequest(var10002, var10003, var14);
         requests.add(var10);
         SchedulingFunctionsKt.afterOnServer$default(0, 60.0F, (new Function0<Unit>(var10, player, otherPlayerEntity) {
            {
               super(0);
               this.$request = `$request`;
               this.$player = `$player`;
               this.$otherPlayerEntity = `$otherPlayerEntity`;
            }

            public final void invoke() {
               if (TradeManager.INSTANCE.getRequests().remove(this.$request)) {
                  val var10000: ServerPlayer = this.$player;
                  val var1: Array<Any> = new Object[1];
                  val var10004: Component = this.$otherPlayerEntity.m_7755_();
                  var1[0] = var10004;
                  var10000.m_5661_(LocalizationUtilsKt.lang("trade.request_expired", var1) as Component, true);
               }
            }
         }) as Function0, 1, null);
         var10000 = CobblemonNetwork.INSTANCE;
         var14 = var10.getTradeOfferId();
         val var10005: UUID = player.m_20148_();
         val var10006: MutableComponent = player.m_7755_().m_6881_();
         var10000.sendPacket(otherPlayerEntity, new TradeOfferNotificationPacket(var14, var10005, var10006));
         val var11: Array<Any> = new Object[1];
         val var16: Component = otherPlayerEntity.m_7755_();
         var11[0] = var16;
         player.m_5661_(LocalizationUtilsKt.lang("trade.request_sent", var11) as Component, true);
      }
   }

   public fun acceptTradeRequest(player: ServerPlayer, tradeOfferId: UUID) {
      val var6: java.util.Iterator = requests.iterator();

      var var10000: Any;
      while (true) {
         if (var6.hasNext()) {
            val var7: Any = var6.next();
            if (!((var7 as TradeManager.TradeRequest).getTradeOfferId() == tradeOfferId)) {
               continue;
            }

            var10000 = (ServerPlayer)var7;
            break;
         }

         var10000 = null;
         break;
      }

      val request: TradeManager.TradeRequest = var10000 as TradeManager.TradeRequest;
      if (var10000 as TradeManager.TradeRequest == null) {
         player.m_5661_(LocalizationUtilsKt.lang("trade.request_already_expired") as Component, true);
      } else {
         requests.remove(request);
         var10000 = PlayerExtensionsKt.getPlayer(request.getSenderId());
         if (var10000 == null) {
            return;
         }

         val var10: ActiveTrade = new ActiveTrade(new PlayerTradeParticipant(player), new PlayerTradeParticipant(var10000));
         activeTrades.add(var10);
         val var12: CobblemonNetwork = CobblemonNetwork.INSTANCE;
         var var10004: UUID = var10000.m_20148_();
         var var10005: MutableComponent = var10000.m_7755_().m_6881_();
         var12.sendPacket(player, new TradeStartedPacket(var10004, var10005, var10.getPlayer2().getParty().mapNullPreserving(<unrepresentable>.INSTANCE)));
         val var13: CobblemonNetwork = CobblemonNetwork.INSTANCE;
         var10004 = player.m_20148_();
         var10005 = player.m_7755_().m_6881_();
         var13.sendPacket(var10000, new TradeStartedPacket(var10004, var10005, var10.getPlayer1().getParty().mapNullPreserving(<unrepresentable>.INSTANCE)));
      }
   }

   public fun onLogoff(player: ServerPlayer) {
      val oppositeParticipant: java.util.Iterator = requests.iterator();

      var var10000: Any;
      while (true) {
         if (!oppositeParticipant.hasNext()) {
            var10000 = null;
            break;
         }

         val var6: Any = oppositeParticipant.next();
         if ((var6 as TradeManager.TradeRequest).getSenderId() == player.m_20148_() || (var6 as TradeManager.TradeRequest).getReceiverId() == player.m_20148_()
            )
          {
            var10000 = var6;
            break;
         }
      }

      val request: TradeManager.TradeRequest = var10000 as TradeManager.TradeRequest;
      if (var10000 as TradeManager.TradeRequest != null) {
         val trade: ServerPlayer = if (request.getReceiverId() == player.m_20148_())
            PlayerExtensionsKt.getPlayer(request.getSenderId())
            else
            PlayerExtensionsKt.getPlayer(request.getReceiverId());
         if (trade != null) {
            CobblemonNetwork.INSTANCE.sendPacket(trade, new TradeOfferExpiredPacket(request.getTradeOfferId()));
         }

         requests.remove(request);
      }

      var var10001: UUID = player.m_20148_();
      val var9: ActiveTrade = this.getActiveTrade(var10001);
      if (var9 != null) {
         var10001 = player.m_20148_();
         var9.getOppositePlayer(var9.getTradeParticipant(var10001)).cancelTrade(var9);
         activeTrades.remove(var9);
      }
   }

   public fun performTrade(player1: TradeParticipant, pokemon1: Pokemon, player2: TradeParticipant, pokemon2: Pokemon) {
      val party1: PartyStore = player1.getParty();
      val party2: PartyStore = player2.getParty();
      party1.remove(pokemon1);
      party2.remove(pokemon2);
      Pokemon.setFriendship$default(pokemon1, 0, false, 2, null);
      Pokemon.setFriendship$default(pokemon2, 0, false, 2, null);
      party2.add(pokemon1);
      party1.add(pokemon2);
      var `$this$iv`: java.lang.Iterable = pokemon1.getLockedEvolutions();
      var `$i$f$post`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filterIsInstance$iv) {
         if (`element$iv$iv` is TradeEvolution) {
            `$i$f$post`.add(`element$iv$iv`);
         }
      }

      for (Object element$iv : $this$filterIsInstance$iv) {
         if ((var27 as TradeEvolution).attemptEvolution(pokemon1, (Object)pokemon2)) {
            break;
         }
      }

      `$this$iv` = pokemon2.getLockedEvolutions();
      `$i$f$post` = new ArrayList();

      for (Object element$iv$ivx : $this$filterIsInstance$iv) {
         if (`element$iv$ivx` is TradeEvolution) {
            `$i$f$post`.add(`element$iv$ivx`);
         }
      }

      for (Object element$ivx : $this$filterIsInstance$iv) {
         if ((`element$ivx` as TradeEvolution).attemptEvolution(pokemon2, (Object)pokemon1)) {
            break;
         }
      }

      val var21: EventObservable = CobblemonEvents.TRADE_COMPLETED;
      val var25: Array<TradeCompletedEvent> = new TradeCompletedEvent[]{new TradeCompletedEvent(player1, pokemon2, player2, pokemon1)};
      var21.emit(Arrays.copyOf(var25, var25.length));

      for (Object element$iv$ivxx : var25) {
         ;
      }
   }

   public class TradeRequest(tradeOfferId: UUID, senderId: UUID, receiverId: UUID) {
      public final val receiverId: UUID
      public final val senderId: UUID
      public final val tradeOfferId: UUID

      init {
         this.tradeOfferId = tradeOfferId;
         this.senderId = senderId;
         this.receiverId = receiverId;
      }
   }
}
