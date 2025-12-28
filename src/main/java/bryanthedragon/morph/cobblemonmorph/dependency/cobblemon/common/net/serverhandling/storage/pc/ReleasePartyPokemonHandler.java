/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.ReleasePartyPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class ReleasePartyPokemonHandler : ServerNetworkPacketHandler<ReleasePartyPokemonPacket> {
    override fun handle(packet: ReleasePartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
        val party = player.party()
        val pokemon = party[packet.position] ?: return
        if (pokemon.uuid != packet.pokemonID) {
            return // Desync
        }

        CobblemonEvents.POKEMON_RELEASED_EVENT_PRE.postThen(
                event = ReleasePokemonEvent.Pre(player, pokemon, party),
                ifSucceeded = { preEvent ->
                    if (ServerSettings.preventCompletePartyDeposit && party.filterNotNull().size <= 1) {
                        return // Don't allow empty party
                    }
                    party.remove(pokemon)
                    CobblemonEvents.POKEMON_RELEASED_EVENT_POST.post(ReleasePokemonEvent.Post(player, pokemon, party))
                },
                ifCanceled = { preEvent ->
                    party[packet.position] = pokemon
                }
        )
    }
}