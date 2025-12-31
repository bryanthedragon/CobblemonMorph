/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket
import net.minecraft.client.Minecraft
public final class DialogueOpenedHandler: ClientNetworkPacketHandler<DialogueOpenedPacket> {
    override fun handle(packet: DialogueOpenedPacket, Minecraft client) {
        val currentScreen = client.screen as? DialogueScreen
        if (currentScreen != null && currentScreen.dialogueId == packet.dialogueDTO.dialogueId) {
            currentScreen.update(packet.dialogueDTO)
        } else {
            client.setScreen(DialogueScreen(packet.dialogueDTO))
        }
    }
}