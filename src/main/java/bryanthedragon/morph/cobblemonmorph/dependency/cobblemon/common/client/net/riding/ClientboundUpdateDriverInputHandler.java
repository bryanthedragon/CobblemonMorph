/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.riding

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.orientation.ClientboundUpdateDriverInputPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.player.RemotePlayer
public final class ClientboundUpdateDriverInputHandler : ClientNetworkPacketHandler<ClientboundUpdateDriverInputPacket> {
    override fun handle(packet: ClientboundUpdateDriverInputPacket, Minecraft client) {
        client.executeIfPossible {
            val level = client.level ?: return@executeIfPossible
            val entity = level.getEntity(packet.entityId)
            if (entity is RemotePlayer) {
                entity.xxa = packet.driverInput.x
                entity.zza = packet.driverInput.z
                entity.jumping = packet.driverInput.y == 1.0f
                entity.isShiftKeyDown = packet.driverInput.y == -1.0f
            }
        }
    }
}
