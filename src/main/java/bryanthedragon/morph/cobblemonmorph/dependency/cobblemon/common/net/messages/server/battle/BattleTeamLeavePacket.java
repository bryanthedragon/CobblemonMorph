/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.TeamManager.MultiBattleTeam
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle.TeamLeaveHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent when a player requests to leave a [MultiBattleTeam].
 *
 * Handled by [TeamLeaveHandler].
 *
 * @author JazzMcNade
 * @since April 15th, 2024
 */
class BattleTeamLeavePacket() : NetworkPacket<BattleTeamLeavePacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
    }
    companion object {
        val ID = cobblemonResource("battle_team_leave")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleTeamLeavePacket()
    }
}