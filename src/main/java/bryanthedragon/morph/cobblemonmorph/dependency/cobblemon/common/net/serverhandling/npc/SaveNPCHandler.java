/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.BehaviourEditingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.npc.SaveNPCPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class SaveNPCHandler : ServerNetworkPacketHandler<SaveNPCPacket> {
    override fun handle(packet: SaveNPCPacket, server: MinecraftServer, player: ServerPlayer) {
        val npcEntity = player.level().getEntity(packet.npcId) as? NPCEntity ?: return
        if (!BehaviourEditingTracker.isPlayerEditing(player, npcEntity)) {
            return
        }

        packet.npcConfigurationDTO.apply(npcEntity)
    }
}