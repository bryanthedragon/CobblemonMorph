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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.yellow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.TeamMemberRemoveNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.client.Minecraft
public final class TeamMemberRemoveNotificationHandler : ClientNetworkPacketHandler<TeamMemberRemoveNotificationPacket> {
    override fun handle(packet: TeamMemberRemoveNotificationPacket, Minecraft client) {

        if(packet.teamMemberUUID == client.player?.uuid) {
            // Client removes itself from its team
            val members = CobblemonClient.teamData.multiBattleTeamMembers.map { it.uuid }
            val memberCount = members.count()
            CobblemonClient.teamData.multiBattleTeamMembers.clear()
            members.forEach { ClientPlayerIcon.update(it) }
            val langKey = if(memberCount > 1) "team.left.self" else "team.disband"
            client.player?.sendSystemMessage(
                lang(
                    langKey,
                ).red()
            )
        } else {
            // Client removes a member from the team
            val memberToRemove = CobblemonClient.teamData.multiBattleTeamMembers.find { it.uuid == packet.teamMemberUUID } ?: return
            CobblemonClient.teamData.multiBattleTeamMembers.remove(memberToRemove)
            ClientPlayerIcon.update(memberToRemove.uuid)

            client.player?.sendSystemMessage(
                lang(
                    "team.left.other",
                    memberToRemove.name.copy().aqua(),
                ).red()
            )
        }
    }
}