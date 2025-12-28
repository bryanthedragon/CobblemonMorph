/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeText
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.MutableComponent
import java.util.UUID

/**
 * Packet sent when a player joins or leaves a team that the client is currently a member of.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.TeamMemberAddNotificationHandler].
 *
 * @author JazzMcNade
 * @since April 9th, 2024
 */
class TeamMemberAddNotificationPacket(
        val teamMemberUUID: UUID,
        val teamMemberName: MutableComponent,
): NetworkPacket<TeamMemberAddNotificationPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(teamMemberUUID)
        buffer.writeText(teamMemberName)
    }

    companion object {
        val ID = cobblemonResource("team_member_add_notification")
        fun decode(buffer: RegistryFriendlyByteBuf) = TeamMemberAddNotificationPacket(buffer.readUUID(), buffer.readText().copy())
    }
}