/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.riding

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import org.joml.Vector3f

public class ServerboundUpdateDriverInputPacket internal constructor(
    val driverInput: Vector3f
) : NetworkPacket<ServerboundUpdateDriverInputPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeVector3f(driverInput)
    }

    final class Companion {
        val ID = cobblemonResource("c2s_update_driver_input")
        fun decode(RegistryFriendlyByteBuf buffer): ServerboundUpdateDriverInputPacket {
            val driverInput = buffer.readVector3f()
            return ServerboundUpdateDriverInputPacket(driverInput)
        }
    }
}
