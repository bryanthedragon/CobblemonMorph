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
import net.minecraft.resources.ResourceLocation
import java.util.UUID

class RequestChangePCBoxWallpaperPacket internal constructor(val storeID: UUID, val boxNumber: Int, val wallpaper: ResourceLocation, val altWallpaper: ResourceLocation?) : NetworkPacket<RequestChangePCBoxWallpaperPacket>, UnsplittablePacket {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(storeID)
        buffer.writeSizedInt(IntSize.U_SHORT, boxNumber)
        buffer.writeString(wallpaper.toString())
        buffer.writeNullable(altWallpaper) { _, value -> buffer.writeString(value.toString()) }
    }

    companion object {
        val ID = cobblemonResource("request_change_pc_box_wallpaper")
        fun decode(buffer: RegistryFriendlyByteBuf): RequestChangePCBoxWallpaperPacket {
            val storeID = buffer.readUUID()
            val boxNumber = buffer.readSizedInt(IntSize.U_SHORT)
            val wallpaper = ResourceLocation.parse(buffer.readString())
            val altWallpaper = (buffer.readNullable { buffer.readString() })?.let { ResourceLocation.parse(it) }
            return RequestChangePCBoxWallpaperPacket(storeID, boxNumber, wallpaper, altWallpaper)
        }
    }
}
