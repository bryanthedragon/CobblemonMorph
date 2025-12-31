/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.npc.dto.NPCConfigurationDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the server to save an NPC's configuration. It will validate the
 * NPC being edited as one that the server is aware that the player is the editor of.
 *
 * @author Hiroku
 * @since February 10th, 2024
 */
public class SaveNPCPacket(
    val npcId: Int,
    val npcConfigurationDTO: NPCConfigurationDTO
) : NetworkPacket<SaveNPCPacket> {
    final class Companion {
        val ID = cobblemonResource("save_npc")
        fun decode(RegistryFriendlyByteBuf buffer) = SaveNPCPacket(
            npcId = buffer.readInt(),
            npcConfigurationDTO = NPCConfigurationDTO().also { it.decode(buffer) }
        )
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(npcId)
        npcConfigurationDTO.encode(buffer)
    }
}