/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.behaviour

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.BehaviourEditingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.MoLangScriptingEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.npc.OpenNPCEditorPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.behaviour.SetEntityBehaviourPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
final class SetEntityBehaviourHandler : ServerNetworkPacketHandler<SetEntityBehaviourPacket> {
    override fun handle(packet: SetEntityBehaviourPacket, server: MinecraftServer, player: ServerPlayer) {
        val entity = player.serverLevel().getEntity(packet.entityId)
        if (entity == null || entity !is MoLangScriptingEntity || entity !is LivingEntity) {
            return player.closeContainer()
        }

        if (!BehaviourEditingTracker.isPlayerEditing(player, entity)) {
            return // Someone hacking maybe, or someone else got in and started editing while they were in here.
        }

        entity.updateBehaviours(packet.behaviours)

        if (entity is NPCEntity) {
            player.sendPacket(OpenNPCEditorPacket(entity))
        } else {
            BehaviourEditingTracker.stopEditing(player.uuid)
            player.closeContainer()
        }
    }
}