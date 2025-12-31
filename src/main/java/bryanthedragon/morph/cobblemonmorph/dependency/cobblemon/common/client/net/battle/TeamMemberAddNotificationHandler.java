/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.aqua
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.green
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMultiBattleTeamMember
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.TeamMemberAddNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.client.Minecraft
public final class TeamMemberAddNotificationHandler : ClientNetworkPacketHandler<TeamMemberAddNotificationPacket> {
    override fun handle(packet: TeamMemberAddNotificationPacket, Minecraft client) {

        if (CobblemonClient.teamData.multiBattleTeamMembers.any { it.uuid == packet.teamMemberUUID }) {
            return //already knows about the new member
        }
        var newMember = ClientMultiBattleTeamMember(packet.teamMemberUUID, packet.teamMemberName)
        CobblemonClient.teamData.multiBattleTeamMembers.add(newMember)
        ClientPlayerIcon.update(newMember.uuid)

        client.player?.sendSystemMessage(
            lang(
                "team.join.other",
                newMember.name.copy().aqua(),
            ).green()
        )
    }
}