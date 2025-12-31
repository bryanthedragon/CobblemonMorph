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
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID


/**
 * Packet sent when a player joins or leaves a team that the client is currently a member of.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.TeamMemberRemoveNotificationHandler].
 *
 * @author JazzMcNade
 * @since April 9th, 2024
 */
public class TeamMemberRemoveNotificationPacket(
        val teamMemberUUID uuid,
): NetworkPacket<TeamMemberRemoveNotificationPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(teamMemberUUID)
    }

    final class Companion {
        val ID = cobblemonResource("team_member_remove_notification")
        fun decode(RegistryFriendlyByteBuf buffer) = TeamMemberRemoveNotificationPacket(buffer.readUUID())
    }
}