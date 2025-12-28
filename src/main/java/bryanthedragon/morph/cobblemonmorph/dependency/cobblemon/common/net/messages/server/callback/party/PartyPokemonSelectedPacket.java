/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the server when the player has responded to a party selection callback.
 *
 * @author Hiroku
 * @since July 7th, 2023
 */
class PartyPokemonSelectedPacket(val uuid: UUID, val index: Int) : NetworkPacket<PartyPokemonSelectedPacket> {
    companion object {
        val ID = cobblemonResource("party_pokemon_selected")
        fun decode(buffer: RegistryFriendlyByteBuf) = PartyPokemonSelectedPacket(buffer.readUUID(), buffer.readSizedInt(IntSize.U_BYTE))
    }

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(uuid)
        buffer.writeSizedInt(IntSize.U_BYTE, index)
    }
}