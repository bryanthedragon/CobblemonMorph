/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.orientation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readMatrix3f
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeMatrix3f
import net.minecraft.network.RegistryFriendlyByteBuf
import org.joml.Matrix3f

public class ServerboundUpdateOrientationPacket internal constructor(
    val entity: Int,
    val orientation: Matrix3f?
) : NetworkPacket<ServerboundUpdateOrientationPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(entity)
        buffer.writeBoolean(orientation != null)
        if (orientation != null) {
            buffer.writeMatrix3f(orientation)
        }
    }

    final class Companion {
        val ID = cobblemonResource("c2s_update_orientation")
        fun decode(RegistryFriendlyByteBuf buffer): ServerboundUpdateOrientationPacket {
            val entity =  buffer.readInt()
            val orientation = if (buffer.readBoolean()) buffer.readMatrix3f() else null
            return ServerboundUpdateOrientationPacket(entity, orientation)
        }
    }
}
