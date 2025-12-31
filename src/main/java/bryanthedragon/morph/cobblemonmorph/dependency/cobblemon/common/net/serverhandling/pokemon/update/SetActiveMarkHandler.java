/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.SetActiveMarkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class SetActiveMarkHandler : ServerNetworkPacketHandler<SetActiveMarkPacket> {

    override fun handle(packet: SetActiveMarkPacket, server: MinecraftServer, ServerPlayer player) {

        val pokemonStore: PokemonStore<*> = player.party()
        val pokemon = pokemonStore[packet.uuid] ?: return

        pokemon.activeMark = packet.mark
    }
}
