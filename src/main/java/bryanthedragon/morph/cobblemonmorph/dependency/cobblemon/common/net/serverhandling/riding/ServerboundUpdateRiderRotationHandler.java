/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.riding

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.RidePassenger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.riding.ServerboundUpdateRiderRotationPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class ServerboundUpdateRiderRotationHandler : ServerNetworkPacketHandler<ServerboundUpdateRiderRotationPacket> {

    override fun handle(
        packet: ServerboundUpdateRiderRotationPacket,
        server: MinecraftServer,
        player: ServerPlayer
    ) {
        if (player is RidePassenger) {
            player.`cobblemon$setRideXRot`(packet.riderXRot)
            player.`cobblemon$setRideYRot`(packet.riderYRot)
            player.`cobblemon$setRideEyePos`(packet.rideEyePos)
        }
    }
}