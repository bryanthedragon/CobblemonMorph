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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class SetPCBoxWallpapersPacket internal constructor(val wallpapers: Set<ResourceLocation>) : NetworkPacket<SetPCBoxWallpapersPacket>, UnsplittablePacket {
    override val id = ID

    final class Companion {
        val ID = cobblemonResource("set_pc_box_wallpapers")
        fun decode(RegistryFriendlyByteBuf buffer): SetPCBoxWallpapersPacket =
            SetPCBoxWallpapersPacket(buffer.readList { reader -> reader.readResourceLocation() }.toSet())
    }

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeCollection(wallpapers) { writer, value -> writer.writeResourceLocation(value) }
    }
}
