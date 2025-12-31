/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;

import com.cobblemon.mod.common.CobblemonNetwork;
import com.cobblemon.mod.common.util.server;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import java.util.function.Predicate;

/**
 * Platform abstract blueprint of a packet being sent out.
 * The handling of encoding, decoding and resolving the packet is done on the individual platform implementations.
 *
 * @author Hiroku, Licious
 * @since November 27th, 2021
 */
public interface NetworkPacket<T extends NetworkPacket<T>> extends CustomPacketPayload, Encodable {

    /**
     * Gets the unique identifier for this packet.
     *
     * @return The ResourceLocation ID.
     */
    @NotNull
    ResourceLocation getId();

    /**
     * Sends this packet to a specific player.
     *
     * @param player The player to receive the packet.
     */
    default void sendToPlayer(ServerPlayer player) {
        CobblemonNetwork.sendPacketToPlayer(player, this);
    }

    /**
     * Sends this packet to a collection of players.
     *
     * @param players The players to receive the packet.
     */
    default void sendToPlayers(Iterable<ServerPlayer> players) {
        if (players.iterator().hasNext()) {
            CobblemonNetwork.sendPacketToPlayers(players, this);
        }
    }

    /**
     * Sends this packet to all players currently online.
     */
    default void sendToAllPlayers() {
        CobblemonNetwork.sendToAllPlayers(this);
    }

    /**
     * Sends this packet from the client to the server.
     */
    default void sendToServer() {
        CobblemonNetwork.sendToServer(this);
    }

    /**
     * Overload for sendToPlayersAround without an exclusion condition.
     */
    default void sendToPlayersAround(double x, double y, double z, double distance, ResourceKey<Level> worldKey) {
        sendToPlayersAround(x, y, z, distance, worldKey, player -> false);
    }

    /**
     * Sends this packet to players within a certain distance of a coordinate.
     * 
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param z Z coordinate.
     * @param distance The maximum distance.
     * @param worldKey The dimension key.
     * @param exclusionCondition A condition to exclude specific players.
     */
    default void sendToPlayersAround(double x, double y, double z, double distance, ResourceKey<Level> worldKey, Predicate<ServerPlayer> exclusionCondition) {
        MinecraftServer server = ServerKt.server(); // Accessing the Kotlin top-level utility function
        if (server == null) {
            return;
        }

        double distSq = distance * distance;

        server.getPlayerList().getPlayers().stream()
            .filter(player -> {
                // Check exclusion condition
                if (exclusionCondition.test(player)) {
                    return false;
                }
                // Check dimension
                if (!player.level().dimension().equals(worldKey)) {
                    return false;
                }
                
                // Calculate distance squared
                double xDiff = x - player.getX();
                double yDiff = y - player.getY();
                double zDiff = z - player.getZ();
                
                // Note: Fixed the potential logic error in original snippet where zDiff was not squared
                return (xDiff * xDiff + yDiff * yDiff + zDiff * zDiff) < distSq;
            })
            .forEach(player -> CobblemonNetwork.sendPacketToPlayer(player, this));
    }

    @Override
    @NotNull
    default CustomPacketPayload.Type<T> type() {
        return new CustomPacketPayload.Type<>(getId());
    }
}
