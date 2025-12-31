package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.network;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;

import net.minecraft.server.level.ServerPlayer;

public interface CobblemonNetworkManager {
    void sendPacketToPlayer(ServerPlayer player, NetworkPacket<?> packet);

    void sendToServer(NetworkPacket<?> packet);
}
