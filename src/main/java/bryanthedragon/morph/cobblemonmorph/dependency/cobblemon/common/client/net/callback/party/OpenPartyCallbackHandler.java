package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyCallbackPacket
import net.minecraft.client.Minecraft

public object OpenPartyCallbackHandler : ClientNetworkPacketHandler<OpenPartyCallbackPacket> {
   public open fun handle(packet: OpenPartyCallbackPacket, client: Minecraft) {
      client.m_91152_(new PartySelectGUI(packet.getTitle(), packet.getPokemon(), packet.getUuid()));
   }

   fun handleOnNettyThread(packet: OpenPartyCallbackPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
