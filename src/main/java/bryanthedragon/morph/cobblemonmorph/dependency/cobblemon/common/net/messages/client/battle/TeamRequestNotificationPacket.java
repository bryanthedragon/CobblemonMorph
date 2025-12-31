/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.TeamManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Packet sent when a player has requested to form or join a team.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.TeamRequestNotificationHandler].
 *
 * @param requestID The unique identifier of the request.
 * @param senderID The unique identifier of the party that sent the request.
 * @param expiryTime How long (in seconds) this request is active.
 *
 * @author JazzMcNade
 * @since April 15th, 2024
 */
public class TeamRequestNotificationPacket(
    val requestID: UUID,
    val senderID: UUID,
    val expiryTime: Int
): NetworkPacket<TeamRequestNotificationPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(requestID)
        buffer.writeUUID(senderID)
        buffer.writeInt(expiryTime)
    }

    final class Companion {
        val ID = cobblemonResource("team_request_notification")
        fun decode(RegistryFriendlyByteBuf buffer) = TeamRequestNotificationPacket(
            buffer.readUUID(),
            buffer.readUUID(),
            buffer.readInt()
        )
    }

    constructor(request: TeamManager.TeamRequest) : this(
        requestID = request.requestID,
        senderID = request.senderID,
        expiryTime = request.expiryTime
    )
}