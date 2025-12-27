package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket
import net.minecraft.client.Minecraft

public object OpenPastureHandler : ClientNetworkPacketHandler<OpenPasturePacket> {
   public open fun handle(packet: OpenPasturePacket, client: Minecraft) {
      val pcConfiguration: PasturePCGUIConfiguration = new PasturePCGUIConfiguration(
         packet.getPastureId(), packet.getLimit(), new SettableObservable<>(packet.getTetheredPokemon()), packet.getPermissions()
      );
      val var10003: Any = CobblemonClient.INSTANCE.getStorage().getPcStores().get(packet.getPcId());
      client.m_91152_(new PCGUI(var10003 as ClientPC, CobblemonClient.INSTANCE.getStorage().getMyParty(), pcConfiguration));
   }

   fun handleOnNettyThread(packet: OpenPasturePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
