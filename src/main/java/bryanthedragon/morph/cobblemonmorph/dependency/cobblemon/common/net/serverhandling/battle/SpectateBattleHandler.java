/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.yellow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.SpectateBattlePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.ClipContext
import org.apache.logging.log4j.LogManager
final class SpectateBattleHandler : ServerNetworkPacketHandler<SpectateBattlePacket> {
    val LOGGER = LogManager.getLogger()
    override fun handle(
        packet: SpectateBattlePacket,
        server: MinecraftServer,
        player: ServerPlayer
    ) {
        val battle = BattleRegistry.getBattleByParticipatingPlayerId(packet.targetedEntityId)
        if (battle != null && Cobblemon.config.allowSpectating) {
            val target = battle.actors.filterIsInstance<PlayerBattleActor>().firstOrNull { it.uuid == packet.targetedEntityId }

            // Check los and range
            val targetedPlayerEntity = packet.targetedEntityId.getPlayer() ?: return
            if (player.traceFirstEntityCollision(
                            entityClass = LivingEntity::class.java,
                            ignoreEntity = player,
                            maxDistance = Cobblemon.config.battleSpectateMaxDistance,
                            collideBlock = ClipContext.Fluid.NONE) != targetedPlayerEntity) {
                player.sendSystemMessage(lang("ui.interact.failed").yellow())
                return
            }

            this.spectateBattle(targetedPlayerEntity, player)

            // Handle music
            target?.battleTheme?.let { player.sendPacket(BattleMusicPacket(it)) }
        }
        else {
            LOGGER.error("Battle of player id ${packet.targetedEntityId} not found (${player.uuid} tried spectating)")
        }
    }

    fun spectateBattle(target: ServerPlayer, player: ServerPlayer) {
        if (player == target) {
            player.sendSystemMessage(lang("command.spectatebattle.self_spectate_disallowed").red())
            return
        }

        if (!target.isInBattle()) {
            player.sendSystemMessage(lang("command.spectatebattle.player_not_in_battle").red())
            return
        }

        if (player.isInBattle()) {
            player.sendSystemMessage(lang("command.spectatebattle.while_battling_disallowed").red())
            return
        }

        val battle = BattleRegistry.getBattleByParticipatingPlayer(target) ?: return

        battle.spectators.add(player.uuid)
        player.sendPacket(BattleInitializePacket(battle, null))
        player.sendPacket(BattleMessagePacket(battle.chatLog))
    }

}