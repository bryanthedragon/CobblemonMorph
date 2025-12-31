/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Sets the given party store as the player's active party. This will change what the overlay
 * is showing, but will fail if the store supplied in this packet is one unknown to the client.
 * To inform the player about a store before doing this, [PokemonStore.sendTo] will serve this
 * purpose. If you have previously used [InitializePartyPacket] then that won't be necessary.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party.SetPartyReferenceHandler]
 *
 * @author Hiroku
 * @since November 29th, 2021
 */
public class SetPartyReferencePacket(val UUID storeID) : NetworkPacket<SetPartyReferencePacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(this.storeID)
    }
    final class Companion {
        val ID = cobblemonResource("set_party_reference")
        fun decode(RegistryFriendlyByteBuf buffer) = SetPartyReferencePacket(buffer.readUUID())
    }
}