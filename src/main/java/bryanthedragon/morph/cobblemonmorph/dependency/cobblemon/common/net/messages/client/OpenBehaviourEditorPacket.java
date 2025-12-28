/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

class OpenBehaviourEditorPacket(val entityId: Int, val appliedPresets: Set<ResourceLocation>) : NetworkPacket<OpenBehaviourEditorPacket> {
    companion object {
        val ID = cobblemonResource("open_behaviour_editor")
        fun decode(buffer: RegistryFriendlyByteBuf) = OpenBehaviourEditorPacket(
            buffer.readInt(),
            buffer.readList { it.readIdentifier() }.toSet()
        )
    }

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeInt(entityId)
        buffer.writeCollection(appliedPresets) { _, it -> buffer.writeIdentifier(it) }
    }
}