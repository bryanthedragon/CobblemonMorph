package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelGuiFactoryKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.InteractPokemonUIPacket
import net.minecraft.client.Minecraft

public object InteractPokemonUIPacketHandler : ClientNetworkPacketHandler<InteractPokemonUIPacket> {
   public open fun handle(packet: InteractPokemonUIPacket, client: Minecraft) {
      client.m_91152_(InteractWheelGuiFactoryKt.createPokemonInteractGui(packet.getPokemonID(), packet.getCanMountShoulder()));
   }

   fun handleOnNettyThread(packet: InteractPokemonUIPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
