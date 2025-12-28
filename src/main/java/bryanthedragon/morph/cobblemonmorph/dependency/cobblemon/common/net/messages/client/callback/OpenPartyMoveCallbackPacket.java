/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartyMoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeText
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.MutableComponent

/**
 * Packet sent to the client to open a party select then move select GUI to get a selection.
 * Used as part of [PartyMoveSelectCallbacks].
 *
 * @author Hiroku
 * @since July 29th, 2023
 */
class OpenPartyMoveCallbackPacket(
    val uuid: UUID,
    val partyTitle: MutableComponent,
    val pokemonList: List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>>
) : NetworkPacket<OpenPartyMoveCallbackPacket> {
    companion object {
        val ID = cobblemonResource("open_party_move_callback")
        fun decode(buffer: RegistryFriendlyByteBuf): OpenPartyMoveCallbackPacket {
            val uuid = buffer.readUUID()
            val partyTitle = buffer.readText().copy()
            val pokemonList = mutableListOf<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>>()
            repeat(times = buffer.readSizedInt(IntSize.U_BYTE)) {
                val pkDTO = PartySelectPokemonDTO(buffer)
                val mvDTOs = mutableListOf<MoveSelectDTO>()
                repeat(times = buffer.readSizedInt(IntSize.U_BYTE)) {
                    mvDTOs.add(MoveSelectDTO(buffer))
                }
                pokemonList.add(pkDTO to mvDTOs)
            }

            return OpenPartyMoveCallbackPacket(
                uuid = uuid,
                partyTitle = partyTitle,
                pokemonList = pokemonList
            )
        }
    }

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(uuid)
        buffer.writeText(partyTitle)
        buffer.writeSizedInt(IntSize.U_BYTE, pokemonList.size)
        for ((pkDTO, mvDTOs) in pokemonList) {
            pkDTO.writeToBuffer(buffer)
            buffer.writeSizedInt(IntSize.U_BYTE, mvDTOs.size)
            for (mvDTO in mvDTOs) {
                mvDTO.writeToBuffer(buffer)
            }
        }
    }
}