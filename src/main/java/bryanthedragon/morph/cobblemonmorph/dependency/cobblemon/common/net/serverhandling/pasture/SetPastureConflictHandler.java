/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.SetPastureConflictPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.UpdatePastureConflictFlagPacket
final class SetPastureConflictHandler : ServerNetworkPacketHandler<SetPastureConflictPacket> {
    override fun handle(packet: SetPastureConflictPacket, server: MinecraftServer, player: ServerPlayer) {
        val world = player.level()
        val entity = world.getEntitiesOfClass(PokemonEntity::class.java, player.boundingBox.inflate(64.0))
            .firstOrNull { it.pokemon.uuid == packet.pokemonId }
            ?: return

        entity.setBehaviourFlag(PokemonBehaviourFlag.PASTURE_CONFLICT, packet.enabled)
        entity.remakeBrain()

        // Send packet from the server to client GUI to update the toggle button
        UpdatePastureConflictFlagPacket(packet.pokemonId, packet.enabled).sendToPlayer(player)
    }
}