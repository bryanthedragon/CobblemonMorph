package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade.TradeGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nTradeStartedHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeStartedHandler.kt\ncom/cobblemon/mod/common/client/net/trade/TradeStartedHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1549#2:34\n1620#2,2:35\n1622#2:38\n1#3:37\n*S KotlinDebug\n*F\n+ 1 TradeStartedHandler.kt\ncom/cobblemon/mod/common/client/net/trade/TradeStartedHandler\n*L\n29#1:34\n29#1:35,2\n29#1:38\n*E\n"])
public object TradeStartedHandler : ClientNetworkPacketHandler<TradeStartedPacket> {
   public open fun handle(packet: TradeStartedPacket, client: Minecraft) {
      val trade: ClientTrade = new ClientTrade();
      CobblemonClient.INSTANCE.setTrade(trade);
      val var10000: Minecraft = Minecraft.m_91087_();
      val var10002: UUID = packet.getTraderId();
      val var10003: MutableComponent = packet.getTraderName();
      val var10004: java.util.List = CollectionsKt.toMutableList(packet.getTraderParty());
      val `$this$map$iv`: java.lang.Iterable = CobblemonClient.INSTANCE.getStorage().getMyParty();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(if (`item$iv$iv` as Pokemon != null) new TradeStartedPacket.TradeablePokemon(`item$iv$iv` as Pokemon) else null);
      }

      var10000.m_91152_(new TradeGUI(trade, var10002, var10003, var10004, CollectionsKt.toMutableList(`destination$iv$iv` as java.util.List)));
   }

   fun handleOnNettyThread(packet: TradeStartedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
