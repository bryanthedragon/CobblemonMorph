/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Sent by the client to the server when they are providing some kind of input to the dialogue to progress it.
 * This could be a text input, or a button press, etc. depending on the input of the active dialogue.
 *
 * @author Hiroku
 * @since December 29th, 2023
 */
public class InputToDialoguePacket(val inputId: UUID, val input: String = ""): NetworkPacket<InputToDialoguePacket> {
    final class Companion {
        val ID = cobblemonResource("input_to_dialogue")
        fun decode(RegistryFriendlyByteBuf buffer) = InputToDialoguePacket(
            buffer.readUUID(),
            buffer.readString()
        )
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(inputId)
        buffer.writeString(input)
    }
}