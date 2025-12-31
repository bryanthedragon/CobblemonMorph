/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party.ReleasePCPokemonHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent when the player is releasing one of their Pokémon from their PC.
 *
 * Handled by [ReleasePCPokemonHandler].
 *
 * @author Hiroku
 * @since October 31st, 2022
 */
public class ReleasePCPokemonPacket(val UUID pokemonId, val position: PCPosition) : NetworkPacket<ReleasePCPokemonPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.
        writeUUID(pokemonID)
        buffer.writePCPosition(position)
    }
    final class Companion {
        val ID = cobblemonResource("release_pc_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = ReleasePCPokemonPacket(buffer.readUUID(), buffer.readPCPosition())
    }
}