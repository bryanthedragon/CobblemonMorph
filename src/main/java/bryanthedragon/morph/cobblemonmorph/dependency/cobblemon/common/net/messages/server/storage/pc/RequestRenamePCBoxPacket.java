/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.UnsplittablePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

public class RequestRenamePCBoxPacket internal constructor(val UUID storeID, val Int boxNumber, val String name?) : NetworkPacket<RequestRenamePCBoxPacket>, UnsplittablePacket {

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(storeID)
        buffer.writeSizedInt(IntSize.U_SHORT, boxNumber)
        buffer.writeString(name ?: "")
    }

    final class Companion {
        val ID = cobblemonResource("request_rename_pc_box")
        fun decode(RegistryFriendlyByteBuf buffer): RequestRenamePCBoxPacket {
            val storeID = buffer.readUUID()
            val boxNumber = buffer.readSizedInt(IntSize.U_SHORT)
            val name = buffer.readString()
            return RequestRenamePCBoxPacket(storeID, boxNumber, name)
        }
    }
}