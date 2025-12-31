/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class DialogueOpenedPacket(val dialogueDTO: DialogueDTO) : NetworkPacket<DialogueOpenedPacket> {
    final class Companion {
        val ID = cobblemonResource("dialogue_opened")
        fun decode(RegistryFriendlyByteBuf buffer) = DialogueOpenedPacket(DialogueDTO().apply { decode(buffer) })
    }

    constructor(ActiveDialogue activeDialogue, includeFaces: Boolean) : this(DialogueDTO(activeDialogue, includeFaces))

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        dialogueDTO.encode(buffer)
    }
}