/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.TeamManager.TeamRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.TeamRequestNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle.TeamRequestResponseHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.*

/**
 * Packet sent when a player responds to a [TeamRequest] after receiving the respective [TeamRequestNotificationPacket].
 *
 * Handled by [TeamRequestResponseHandler].
 *
 * @param requestID The unique identifier of the request that the player is responding to.
 * @param accept Whether the player accepted the team request.
 *
 * @author JazzMcNade
 * @since July 7th, 2024
 */
public class BattleTeamResponsePacket(val targetedEntityId: Int, val requestID: UUID, val accept: Boolean) : NetworkPacket<BattleTeamResponsePacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(targetedEntityId)
        buffer.writeUUID(requestID)
        buffer.writeBoolean(accept)
    }
    final class Companion {
        val ID = cobblemonResource("battle_team_request_response")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleTeamResponsePacket(buffer.readInt(), buffer.readUUID(), buffer.readBoolean())
    }
}