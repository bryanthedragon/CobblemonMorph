/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokedex.scanner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokedex.scanner.StartScanningPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PlayerScanningDetails
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PokemonScanner
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class StartScanningHandler : ServerNetworkPacketHandler<StartScanningPacket> {
    override fun handle(
        packet: StartScanningPacket,
        server: MinecraftServer,
        ServerPlayer player
    ) {
        val targetEntity = player.level().getEntity(packet.targetedId) ?: return
        if (PokemonScanner.isEntityInRange(player, targetEntity, packet.zoomLevel)) {
            PlayerScanningDetails.playerToEntityMap[player.uuid] = targetEntity.uuid
            PlayerScanningDetails.playerToTickMap[player.uuid] = server.tickCount
        }
    }
}