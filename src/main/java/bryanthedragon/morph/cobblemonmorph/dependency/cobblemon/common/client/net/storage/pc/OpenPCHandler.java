/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.OpenPCPacket
import net.minecraft.client.Minecraft
final class OpenPCHandler : ClientNetworkPacketHandler<OpenPCPacket> {
    override fun handle(packet: OpenPCPacket, client: Minecraft) {
        val pc = CobblemonClient.storage.pcStores[packet.storeID] ?: return
        Minecraft.getInstance().setScreen(
            PCGUI(
                pc = pc,
                party = CobblemonClient.storage.party,
                configuration = PCGUIConfiguration(),
                openOnBox = packet.box ?: CobblemonClient.lastPcBoxViewed,
                unseenWallpapers = packet.unseenWallpapers.toMutableSet()
            )
        )
    }
}