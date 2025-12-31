/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientTeamRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.TeamRequestNotificationPacket
import net.minecraft.client.Minecraft
public final class TeamRequestNotificationHandler : ClientNetworkPacketHandler<TeamRequestNotificationPacket> {
    override fun handle(packet: TeamRequestNotificationPacket, Minecraft client) {
        CobblemonClient.requests.multiBattleTeamRequests[packet.senderID] = ClientTeamRequest(packet.requestID, packet.senderID, packet.expiryTime)
        ClientPlayerIcon.update(packet.senderID)
    }
}