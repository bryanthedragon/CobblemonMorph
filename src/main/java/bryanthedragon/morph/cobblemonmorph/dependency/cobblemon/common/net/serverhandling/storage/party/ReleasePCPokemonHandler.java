/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.ReleasePCPokemonPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class ReleasePCPokemonHandler : ServerNetworkPacketHandler<ReleasePCPokemonPacket> {
    override fun handle(packet: ReleasePCPokemonPacket, server: MinecraftServer, ServerPlayer player) {
        val pc = PCLinkManager.getPC(player) ?: return
        val pokemon = pc[packet.position] ?: return
        if (pokemon.uuid != packet.pokemonID) {
            return // Not what we expected
        }

        CobblemonEvents.POKEMON_RELEASED_EVENT_PRE.postThen(
                event = ReleasePokemonEvent.Pre(player, pokemon, pc),
                ifSucceeded = { preEvent ->
                    pc.remove(packet.position)
                    CobblemonEvents.POKEMON_RELEASED_EVENT_POST.post(ReleasePokemonEvent.Post(player, pokemon, pc))
                },
                ifCanceled = { preEvent ->
                    pc[packet.position] = pokemon
                }
        )
    }
}