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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueClosedPacket
import net.minecraft.client.Minecraft
public final class DialogueClosedHandler : ClientNetworkPacketHandler<DialogueClosedPacket> {
    override fun handle(packet: DialogueClosedPacket, Minecraft client) {
        val currentScreen = client.screen as? DialogueScreen ?: return
        if (packet.dialogueId == null || currentScreen.dialogueId == packet.dialogueId) {
            client.setScreen(null)
        }
    }
}