/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.move

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenMoveCallbackPacket
import net.minecraft.client.Minecraft
final class OpenMoveCallbackHandler : ClientNetworkPacketHandler<OpenMoveCallbackPacket> {
    override fun handle(packet: OpenMoveCallbackPacket, client: Minecraft) {
        client.setScreen(MoveSelectGUI(packet.title, packet.moves, packet.uuid))
    }
}