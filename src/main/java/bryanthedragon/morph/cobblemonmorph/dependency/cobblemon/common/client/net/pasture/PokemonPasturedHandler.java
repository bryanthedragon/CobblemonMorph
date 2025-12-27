package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonPasturedPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

public object PokemonPasturedHandler : ClientNetworkPacketHandler<PokemonPasturedPacket> {
   public open fun handle(packet: PokemonPasturedPacket, client: Minecraft) {
      val var5: Screen = Minecraft.m_91087_().f_91080_;
      val var4: PCGUIConfiguration = if ((var5 as? PCGUI) != null) (var5 as? PCGUI).getConfiguration() else null;
      val pastureGuiConfiguration: PasturePCGUIConfiguration = var4 as? PasturePCGUIConfiguration;
      if ((var4 as? PasturePCGUIConfiguration) != null) {
         val var10000: SettableObservable = pastureGuiConfiguration.getPasturedPokemon();
         if (var10000 != null) {
            var10000.set(CollectionsKt.plus(pastureGuiConfiguration.getPasturedPokemon().get(), packet.getPasturePokemonDTO()));
         }
      }
   }

   fun handleOnNettyThread(packet: PokemonPasturedPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
