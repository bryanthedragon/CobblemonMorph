/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.debug

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.RidingStyle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class ServerboundUpdateRidingStatsPacket(
    val entity: Int,
    val ridingStyle: RidingStyle,
    val Double speed,
    val acceleration: Double,
    val skill: Double,
    val jump: Double,
    val stamina: Double,
) : NetworkPacket<ServerboundUpdateRidingStatsPacket> {
    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(entity)
        buffer.writeEnum(ridingStyle)
        buffer.writeDouble(speed)
        buffer.writeDouble(acceleration)
        buffer.writeDouble(skill)
        buffer.writeDouble(jump)
        buffer.writeDouble(stamina)
    }

    final class Companion {
        val ID = cobblemonResource("c2s_update_ride_stats")
        fun decode(RegistryFriendlyByteBuf buffer): ServerboundUpdateRidingStatsPacket {
            return ServerboundUpdateRidingStatsPacket(
                entity = buffer.readInt(),
                ridingStyle = buffer.readEnum(RidingStyle.class),
                speed = buffer.readDouble(),
                acceleration = buffer.readDouble(),
                skill = buffer.readDouble(),
                jump = buffer.readDouble(),
                stamina = buffer.readDouble()
            )
        }
    }
}
