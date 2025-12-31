/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Mark
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Marks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update.SetActiveMarkHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Handled by [SetActiveMarkHandler].
 */
public class SetActiveMarkPacket(val UUID uuid, val Mark mark?) : NetworkPacket<SetActiveMarkPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(uuid)
        buffer.writeNullable(mark) { _, v -> buffer.writeIdentifier(v.identifier) }
    }

    final class Companion {
        val ID = cobblemonResource("set_active_mark")
        fun decode(RegistryFriendlyByteBuf buffer): SetActiveMarkPacket {
            val uuid = buffer.readUUID()
            val markIdentifier = buffer.readNullable { buffer.readIdentifier() }
            val mark = if (markIdentifier !== null) Marks.getByIdentifier(markIdentifier) else null
            return SetActiveMarkPacket(uuid, mark)
        }
    }
}
