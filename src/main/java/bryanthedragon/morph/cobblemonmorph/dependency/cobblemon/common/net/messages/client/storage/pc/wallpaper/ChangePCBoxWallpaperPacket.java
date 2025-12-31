/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.wallpaper

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.UnsplittablePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import java.util.*

open public class ChangePCBoxWallpaperPacket internal constructor(val UUID storeID, val Int boxNumber, val wallpaper: ResourceLocation) : NetworkPacket<ChangePCBoxWallpaperPacket>, UnsplittablePacket {

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(storeID)
        buffer.writeSizedInt(IntSize.U_SHORT, boxNumber)
        buffer.writeString(wallpaper.toString())
    }

    final class Companion {
        val ID = cobblemonResource("change_pc_box_wallpaper")
        fun decode(RegistryFriendlyByteBuf buffer): ChangePCBoxWallpaperPacket {
            val storeID = buffer.readUUID()
            val boxNumber = buffer.readSizedInt(IntSize.U_SHORT)
            val wallpaper = ResourceLocation.parse(buffer.readString())
            return ChangePCBoxWallpaperPacket(storeID, boxNumber, wallpaper)
        }
    }
}
