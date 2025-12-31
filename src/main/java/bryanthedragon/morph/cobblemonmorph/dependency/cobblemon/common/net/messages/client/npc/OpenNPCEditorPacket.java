/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.npc.dto.NPCConfigurationDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class OpenNPCEditorPacket(
    val npcId: Int,
    val dto: NPCConfigurationDTO
) : NetworkPacket<OpenNPCEditorPacket> {
    final class Companion {
        val ID = cobblemonResource("open_npc_editor")
        fun decode(RegistryFriendlyByteBuf buffer) = OpenNPCEditorPacket(
            npcId = buffer.readInt(),
            dto = NPCConfigurationDTO().apply { decode(buffer) }
        )
    }

    override val id = ID

    constructor(npc: NPCEntity): this(npcId = npc.id, dto = NPCConfigurationDTO(npc))

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(npcId)
        dto.encode(buffer)
    }
}