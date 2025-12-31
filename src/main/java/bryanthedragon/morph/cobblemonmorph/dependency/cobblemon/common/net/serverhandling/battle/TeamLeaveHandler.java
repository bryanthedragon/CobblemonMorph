/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.TeamManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.TeamManager.MultiBattleTeam
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.BattleTeamLeavePacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

/**
 * Processes a player's request to leave an active [MultiBattleTeam].
 *
 * @author JazzMcNade
 * @since April 15th, 2024
 */
public final class TeamLeaveHandler : ServerNetworkPacketHandler<BattleTeamLeavePacket> {
    override fun handle(packet: BattleTeamLeavePacket, server: MinecraftServer, ServerPlayer player) {
        TeamManager.removeTeamMember(player)
    }

}