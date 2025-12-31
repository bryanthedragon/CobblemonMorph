/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import net.minecraft.client.Minecraft

/**
 * Handles the request to close the PC GUI.
 *
 * @author Deltric
 * @since May 17th, 2023
 */
public final class ClosePCHandler : ClientNetworkPacketHandler<ClosePCPacket> {
    override fun handle(packet: ClosePCPacket, Minecraft client) {
        if (client.screen !is PCGUI) {
            return
        }

        val pc = client.screen as PCGUI
        if (pc.pc.uuid != packet.storeID) {
            return
        }
        pc.configuration.exitFunction.invoke(pc)
    }
}