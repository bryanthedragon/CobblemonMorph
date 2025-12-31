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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

public class ServerboundUpdateRidingSettingsPacket(
    val entity: Int,
    val ridingStyle: RidingStyle,
    val variable: String,
    val expression: String
) : NetworkPacket<ServerboundUpdateRidingSettingsPacket> {
    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(entity)
        buffer.writeEnum(ridingStyle)
        buffer.writeString(variable)
        buffer.writeString(expression)
    }

    final class Companion {
        val ID = cobblemonResource("c2s_update_ride_settings")
        fun decode(RegistryFriendlyByteBuf buffer): ServerboundUpdateRidingSettingsPacket {
            return ServerboundUpdateRidingSettingsPacket(
                entity = buffer.readInt(),
                ridingStyle = buffer.readEnum(RidingStyle.class),
                variable = buffer.readString(),
                expression = buffer.readString()
            )
        }
    }
}
