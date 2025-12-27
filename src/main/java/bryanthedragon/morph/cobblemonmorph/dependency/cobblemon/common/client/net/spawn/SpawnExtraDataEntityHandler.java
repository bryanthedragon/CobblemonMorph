package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnExtraDataEntityPacket
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.Entity

public class SpawnExtraDataEntityHandler<T extends SpawnExtraDataEntityPacket<T, E>, E extends Entity> : ClientNetworkPacketHandler<T> {
   public open fun handle(packet: Any, client: Minecraft) {
      packet.spawnAndApply(client);
   }

   fun handleOnNettyThread(packet: T) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (T)packet);
   }
}
