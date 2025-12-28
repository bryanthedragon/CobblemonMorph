/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Mark
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Marks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class MarkRegistrySyncPacket(marks: List<Mark>) : DataRegistrySyncPacket<Mark, MarkRegistrySyncPacket>(marks) {
    companion object {
        val ID = cobblemonResource("marks")
        fun decode(buffer: RegistryFriendlyByteBuf) = MarkRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }


    override val id = ID
    override fun encodeEntry(buffer: RegistryFriendlyByteBuf, entry: Mark) {
        entry.encode(buffer)
    }

    override fun decodeEntry(buffer: RegistryFriendlyByteBuf): Mark = Mark.decode(buffer)

    override fun synchronizeDecoded(entries: Collection<Mark>) {
        Marks.reload(entries.associateBy { it.identifier })
    }
}
