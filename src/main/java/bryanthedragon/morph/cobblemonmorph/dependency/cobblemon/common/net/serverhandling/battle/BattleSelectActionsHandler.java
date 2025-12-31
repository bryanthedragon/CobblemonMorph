/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception.IllegalActionChoiceException
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.BattleSelectActionsPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class BattleSelectActionsHandler : ServerNetworkPacketHandler<BattleSelectActionsPacket> {
    override fun handle(packet: BattleSelectActionsPacket, server: MinecraftServer, ServerPlayer player) {
        val battle = BattleRegistry.getBattle(packet.battleId) ?: return
        val actor = battle.actors.find { player.uuid in it.getPlayerUUIDs() } ?: return
        if (!actor.mustChoose) {
            return
        }
        try {
            actor.setActionResponses(packet.showdownActionResponses)
        } catch (e: IllegalActionChoiceException) {
            player.sendSystemMessage(e.message!!.red())
            actor.sendUpdate(BattleQueueRequestPacket(actor.request!!))
            actor.sendUpdate(BattleMakeChoicePacket())
        }
    }
}