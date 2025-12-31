/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution.AcceptEvolutionPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.isInBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class AcceptEvolutionHandler : ServerNetworkPacketHandler<AcceptEvolutionPacket> {
    override fun handle(packet: AcceptEvolutionPacket, server: MinecraftServer, ServerPlayer player) {
        val pokemon = player.party()[packet.pokemonUUID] ?: return
        if (player.isInBattle() || pokemon.entity?.isBusy == true) return
        val evolution = pokemon.evolutionProxy.server().firstOrNull { evolution -> evolution.id.equals(packet.evolutionId, true) } ?: return
        pokemon.evolutionProxy.server().start(evolution)
    }
}