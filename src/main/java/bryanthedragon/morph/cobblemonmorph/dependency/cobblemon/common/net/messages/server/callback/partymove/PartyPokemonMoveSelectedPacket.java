/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the server when the player has responded to a party move selection callback.
 *
 * @author Hiroku
 * @since July 29th, 2023
 */
public class PartyPokemonMoveSelectedPacket(val UUID uuid, val pokemonInt index, val moveInt index) : NetworkPacket<PartyPokemonMoveSelectedPacket> {
    final class Companion {
        val ID = cobblemonResource("party_pokemon_move_selected")
        fun decode(RegistryFriendlyByteBuf buffer) = PartyPokemonMoveSelectedPacket(buffer.readUUID(), buffer.readSizedInt(IntSize.U_BYTE), buffer.readSizedInt(IntSize.U_BYTE))
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(uuid)
        buffer.writeSizedInt(IntSize.U_BYTE, pokemonIndex)
        buffer.writeSizedInt(IntSize.U_BYTE, moveIndex)
    }
}