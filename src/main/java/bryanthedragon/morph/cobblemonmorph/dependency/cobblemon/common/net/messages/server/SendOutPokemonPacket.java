/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.SendOutPokemonHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent from the client to the server to send out the Pokémon in the specified
 * slot.
 *
 * Handled by [SendOutPokemonHandler]
 *
 * @author Hiroku
 * @since December 2nd, 2021
 */
class SendOutPokemonPacket(val slot: Int) : NetworkPacket<SendOutPokemonPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeSizedInt(IntSize.U_BYTE, slot)
    }

    companion object {
        val ID = cobblemonResource("send_out_pokemon")
        fun decode(buffer: RegistryFriendlyByteBuf) = SendOutPokemonPacket(buffer.readSizedInt(IntSize.U_BYTE))
    }
}