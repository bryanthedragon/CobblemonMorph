/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Packet sent to the client to close the active dialogue.
 *
 * @param dialogueId The ID of the dialogue to close. If null, closes the active dialogue.
 * @author Hiroku
 * @since December 27th, 2023
 */
public class DialogueClosedPacket(val dialogueId: UUID? = null) : NetworkPacket<DialogueClosedPacket> {
    final class Companion {
        val ID = cobblemonResource("dialogue_closed")
        fun decode(RegistryFriendlyByteBuf buffer) = DialogueClosedPacket(buffer.readNullable { it.readUUID() })
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeNullable(dialogueId) { buff, value -> buff.writeUUID(value) }
    }
}