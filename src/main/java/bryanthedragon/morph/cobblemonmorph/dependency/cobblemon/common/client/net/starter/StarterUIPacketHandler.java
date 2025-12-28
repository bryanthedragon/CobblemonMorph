/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.StarterSelectionScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.OpenStarterUIPacket
import net.minecraft.client.Minecraft
final class StarterUIPacketHandler : ClientNetworkPacketHandler<OpenStarterUIPacket> {
    override fun handle(packet: OpenStarterUIPacket, client: Minecraft) {
        CobblemonClient.checkedStarterScreen = true
        client.setScreen(StarterSelectionScreen(categories = packet.categories))
    }
}