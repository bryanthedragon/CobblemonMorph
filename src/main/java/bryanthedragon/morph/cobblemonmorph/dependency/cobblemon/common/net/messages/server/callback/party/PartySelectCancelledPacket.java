/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the server when the player closed the party selection GUI.
 *
 * @author Hiroku
 * @since July 7th, 2023
 */
public class PartySelectCancelledPacket(val UUID uuid) : NetworkPacket<PartySelectCancelledPacket> {
    final class Companion {
        val ID = cobblemonResource("party_select_cancelled")
        fun decode(RegistryFriendlyByteBuf buffer) = PartySelectCancelledPacket(buffer.readUUID())
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(uuid)
    }
}