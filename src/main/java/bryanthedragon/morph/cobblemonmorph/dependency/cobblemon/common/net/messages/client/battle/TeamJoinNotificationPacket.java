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
import java.util.UUID
import net.minecraft.network.chat.MutableComponent

/**
 * Packet sent when a player has joined a team. The responsibility
 * of this packet currently is to send a battle challenge message that includes
 * the keybind to challenge them back. In future this is likely to include information
 * about the battle.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.TeamJoinNotificationHandler].
 *
 * @author JazzMcNade
 * @since April 9th, 2024
 */
class TeamJoinNotificationPacket(
    val teamMemberUUIDs: List<UUID>,
    val teamMemberNames: List<MutableComponent>,
): NetworkPacket<TeamJoinNotificationPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeCollection(teamMemberUUIDs) { writer, value ->
            writer.writeUUID(value)
        }
        buffer.writeCollection(teamMemberNames) { _, v -> buffer.writeText(v) }
    }

    companion object {
        val ID = cobblemonResource("team_join_notification")
        fun decode(buffer: RegistryFriendlyByteBuf) = TeamJoinNotificationPacket(
            buffer.readList { pb -> pb.readUUID() },
            buffer.readList { buffer.readText().copy() }.toMutableList()

        )
    }
}