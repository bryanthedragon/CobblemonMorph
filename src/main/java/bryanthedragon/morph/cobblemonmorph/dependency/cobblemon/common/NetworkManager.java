package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public interface NetworkManager {
   public abstract fun registerClientBound() {
   }

   public abstract fun registerServerBound() {
   }

   public abstract fun <T : NetworkPacket<Any>> createClientBound(identifier: ResourceLocation, kClass: KClass<Any>, encoder: (Any, FriendlyByteBuf) -> Unit, decoder: (FriendlyByteBuf) -> Any, handler: ClientNetworkPacketHandler<Any>) {
   }

   public abstract fun <T : NetworkPacket<Any>> createServerBound(identifier: ResourceLocation, kClass: KClass<Any>, encoder: (Any, FriendlyByteBuf) -> Unit, decoder: (FriendlyByteBuf) -> Any, handler: ServerNetworkPacketHandler<Any>) {
   }

   public abstract fun sendPacketToPlayer(player: ServerPlayer, packet: NetworkPacket<*>) {
   }

   public abstract fun sendPacketToServer(packet: NetworkPacket<*>) {
   }

   public abstract fun <T : NetworkPacket<*>> asVanillaClientBound(packet: Any): Packet<ClientGamePacketListener> {
   }
}
