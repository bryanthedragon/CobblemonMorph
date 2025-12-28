/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Creates a party on the client side with the given UUID and slot count.
 *
 * This can be used for immediately telling the client that this is their party to use
 * in overlay rendering, but generally is just necessary before sending Pokémon updates
 * targeting this store.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party.InitializePartyHandler]
 *
 * @author Hiroku
 * @since November 29th, 2021
 */
class InitializePartyPacket(val isThisPlayerParty: Boolean, val uuid: UUID, val slots: Int) : NetworkPacket<InitializePartyPacket> {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeBoolean(isThisPlayerParty)
        buffer.writeUUID(uuid)
        buffer.writeSizedInt(IntSize.U_BYTE, slots)
    }

    companion object {
        val ID = cobblemonResource("initialize_party")
        fun decode(buffer: RegistryFriendlyByteBuf) = InitializePartyPacket(buffer.readBoolean(), buffer.readUUID(), buffer.readSizedInt(IntSize.U_BYTE))
    }
}