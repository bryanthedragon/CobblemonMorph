/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.riding

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.PlayerDuck
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.riding.ServerboundUpdateDriverInputPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class DriverInputPacketHandler : ServerNetworkPacketHandler<ServerboundUpdateDriverInputPacket> {

    override fun handle(
        packet: ServerboundUpdateDriverInputPacket,
        server: MinecraftServer,
        ServerPlayer player
    ) {
        if (player is PlayerDuck) {
            player.setDriverInput(packet.driverInput)
        }
    }
}