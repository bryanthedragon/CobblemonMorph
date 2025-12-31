/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMultiBattleTeamMember
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.TeamJoinNotificationPacket
import net.minecraft.client.Minecraft
public final class TeamJoinNotificationHandler : ClientNetworkPacketHandler<TeamJoinNotificationPacket> {
    override fun handle(packet: TeamJoinNotificationPacket, Minecraft client) {
        CobblemonClient.teamData.multiBattleTeamMembers = packet.teamMemberUUIDs.mapIndexed { index, uuid -> ClientMultiBattleTeamMember(uuid, packet.teamMemberNames[index]) }.toMutableList()
        packet.teamMemberUUIDs.forEach { ClientPlayerIcon.update(it) }
    }
}