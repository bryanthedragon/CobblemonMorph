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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.SetMarkingsPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.pc
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class SetMarkingsHandler : ServerNetworkPacketHandler<SetMarkingsPacket> {

    override fun handle(packet: SetMarkingsPacket, server: MinecraftServer, player: ServerPlayer) {
        val pokemonStore: PokemonStore<*> = if (packet.isParty) player.party() else player.pc()
        val pokemon = pokemonStore[packet.uuid] ?: return

        pokemon.markings = packet.markings
    }
}
