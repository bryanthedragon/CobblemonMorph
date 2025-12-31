/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.npc.NPCEditorScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.npc.OpenNPCEditorPacket
import net.minecraft.client.Minecraft
public final class OpenNPCEditorHandler : ClientNetworkPacketHandler<OpenNPCEditorPacket> {
    override fun handle(packet: OpenNPCEditorPacket, Minecraft client) {
        client.setScreen(NPCEditorScreen(packet.npcId, packet.dto))
    }
}