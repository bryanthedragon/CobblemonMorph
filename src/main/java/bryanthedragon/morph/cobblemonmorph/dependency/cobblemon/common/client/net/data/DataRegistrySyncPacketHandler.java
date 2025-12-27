package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket
import java.util.ArrayList;
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf

public class DataRegistrySyncPacketHandler<P, T extends DataRegistrySyncPacket<P, T>> : ClientNetworkPacketHandler<T> {
   public open fun handle(packet: Any, client: Minecraft) {
      packet.getEntries$common().clear();
      val var10000: ArrayList = packet.getEntries$common();
      val var10001: FriendlyByteBuf = packet.getBuffer();
      val var4: java.util.List = var10001.m_236845_(packet::decodeEntry);
      var10000.addAll(CollectionsKt.filterNotNull(var4));
      val var3: FriendlyByteBuf = packet.getBuffer();
      var3.release();
      packet.synchronizeDecoded(packet.getEntries$common());
   }

   fun handleOnNettyThread(packet: T) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (T)packet);
   }
}
