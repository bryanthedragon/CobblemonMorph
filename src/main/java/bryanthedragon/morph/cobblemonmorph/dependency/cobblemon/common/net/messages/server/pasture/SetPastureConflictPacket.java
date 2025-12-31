/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

public class SetPastureConflictPacket(val UUID pokemonId, val Boolean enabled) : NetworkPacket<SetPastureConflictPacket> {
    final class Companion {
        val ID = cobblemonResource("set_pasture_conflict")
        fun decode(RegistryFriendlyByteBuf buf) =
            SetPastureConflictPacket(buf.readUUID(), buf.readBoolean())
    }

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(pokemonId)
        buf.writeBoolean(enabled)
    }
}
