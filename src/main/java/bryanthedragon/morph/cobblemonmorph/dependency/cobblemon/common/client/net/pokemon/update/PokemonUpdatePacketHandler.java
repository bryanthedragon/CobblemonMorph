package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import net.minecraft.client.Minecraft

public class PokemonUpdatePacketHandler<T extends PokemonUpdatePacket<T>> : ClientNetworkPacketHandler<T> {
   public open fun handle(packet: Any, client: Minecraft) {
      packet.applyToPokemon();
   }

   fun handleOnNettyThread(packet: T) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (T)packet);
   }
}
