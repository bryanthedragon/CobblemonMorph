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

class DialogueOpenedPacket(val dialogueDTO: DialogueDTO) : NetworkPacket<DialogueOpenedPacket> {
    companion object {
        val ID = cobblemonResource("dialogue_opened")
        fun decode(buffer: RegistryFriendlyByteBuf) = DialogueOpenedPacket(DialogueDTO().apply { decode(buffer) })
    }

    constructor(activeDialogue: ActiveDialogue, includeFaces: Boolean) : this(DialogueDTO(activeDialogue, includeFaces))

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        dialogueDTO.encode(buffer)
    }
}