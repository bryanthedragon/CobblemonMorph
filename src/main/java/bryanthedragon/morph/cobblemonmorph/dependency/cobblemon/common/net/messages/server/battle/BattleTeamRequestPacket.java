/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.ChallengeHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet fired when a player makes an interaction request to form a team with another player.
 *
 * Handled by [ChallengeHandler].
 *
 * @param targetedEntityId The ID of the player who's the target of this interaction request.
 *
 * @author JazzMcNade
 * @since April 15th, 2024
 */
class BattleTeamRequestPacket(val targetedEntityId: Int) : NetworkPacket<BattleTeamRequestPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeInt(this.targetedEntityId)
    }
    companion object {
        val ID = cobblemonResource("battle_team_request")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleTeamRequestPacket(buffer.readInt())
    }
}