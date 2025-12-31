/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePCPokemonPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class MovePCPokemonHandler : ServerNetworkPacketHandler<MovePCPokemonPacket> {
    override fun handle(packet: MovePCPokemonPacket, server: MinecraftServer, ServerPlayer player) {
        val pc = PCLinkManager.getPC(player) ?: return run { ClosePCPacket(null).sendToPlayer(player) }
        val pokemon = pc[packet.oldPosition] ?: return
        if (pokemon.uuid != packet.pokemonID) {
            return
        }
        if (!pc.isValidPosition(packet.newPosition)) {
            return
        }
        val existingPokemon = pc[packet.newPosition]
        if (existingPokemon == null) {
            pc.move(pokemon, packet.newPosition)
        } else {
            // This should've been a swap if the slot is already taken. Seems like desync?
        }
    }
}