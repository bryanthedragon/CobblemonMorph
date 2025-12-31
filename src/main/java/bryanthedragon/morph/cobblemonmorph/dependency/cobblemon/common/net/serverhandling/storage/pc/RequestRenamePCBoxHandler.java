/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.RenamePCBoxEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.RenamePCBoxPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.RequestRenamePCBoxPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class RequestRenamePCBoxHandler : ServerNetworkPacketHandler<RequestRenamePCBoxPacket> {
    override fun handle(packet: RequestRenamePCBoxPacket, server: MinecraftServer, ServerPlayer player) {
        val pc = PCLinkManager.getPC(player) ?: return run { ClosePCPacket(null).sendToPlayer(player) }
        if (pc.boxes.size <= packet.boxNumber) {
            return
        }

        val box = pc.boxes[packet.boxNumber]
        if (packet.name != null && packet.name.length > 19) {
            RenamePCBoxPacket(pc.uuid, packet.boxNumber, box.name).sendToPlayer(player)
            return
        }

        CobblemonEvents.RENAME_PC_BOX_EVENT_PRE.postThenFinally(
            event = RenamePCBoxEvent.Pre(player, box, packet.name?: ""),
            ifSucceeded = { preEvent ->
                box.name = preEvent.name
                CobblemonEvents.RENAME_PC_BOX_EVENT_POST.post(RenamePCBoxEvent.Post(player, box, preEvent.name))
            },
            finally = {
                RenamePCBoxPacket(pc.uuid, packet.boxNumber, box.name).sendToPlayer(player)
            }
        )
    }
}