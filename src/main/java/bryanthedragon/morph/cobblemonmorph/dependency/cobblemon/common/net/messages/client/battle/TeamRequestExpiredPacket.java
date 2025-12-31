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
 * Packet fired to tell the client that a team request expired.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.TeamRequestExpiredHandler]
 *
 * @param senderID The unique identifier of the party that sent the request.
 * @param expired Whether this cancellation is due to expiration.
 *
 * @author JazzMcNade
 * @since April 4th, 2024
 */
public class TeamRequestExpiredPacket(val senderID: UUID) : NetworkPacket<TeamRequestExpiredPacket> {
    final class Companion {
        val ID = cobblemonResource("team_request_expired")
        fun decode(RegistryFriendlyByteBuf buffer) = TeamRequestExpiredPacket(buffer.readUUID())
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(senderID)
    }

    constructor(request: TeamManager.TeamRequest) : this(request.senderID)
}